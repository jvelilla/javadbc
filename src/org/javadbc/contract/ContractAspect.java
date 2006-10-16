package org.javadbc.contract;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ognl.MethodFailedException;
import ognl.Ognl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;
import org.springframework.util.StringUtils;

/**
 * Esta clase representa el Aspecto de Diseño por Contratos, aplicado a cada
 * (Clase|Interface|Metodo) segun corresponda, contengan metadata asociada, este
 * caso Annotations soportados por Java 5 Por ejemplo en el caso de una clase
 * 
 * @Invariant(expresion) public class Example{ }
 * 
 * Ejemplo de una interface
 * 
 * @Invariant(expresion) public interface IExample{ }
 * 
 * Ejemplo de metodos
 * @author jvelilla
 * @version 1. 0 based on Jon Tirsen Nanning AspectJ
 * http://nanning.codehaus.org/overview.html
 * 
 * Implementation Issue
 * ====================
 * Public methods need to check contracts (ok)
 * Contract execution needs to be synchronized (ok)
 * Public static methods need to check contract (ok)
 * No contracts in private methods(ok)
 * No checking in methods invoked from another method of the same class (not implemented)
 * Checking of contracts in constructor should occur before the call to the superclass constructor (not yet implemented (Critical))
 * 
 * Support old Expresion but implement semantic by reference -->(should be good if we can check if the class implements Cloneable, then we could 
 * warning the user when he/she is using semantic by copy or semantic by reference.
 * old only can be used in a postcondition
 * 
 * Support result, it permits us obtain the method return value and only can be used in a postcondition.
 * 
 * Actually, we don't support implies, but it can be emulated whit basic Prepositional operators.
 * Example a implies b 
 * (a -> b) 
 * Then we can transform this in ((!a) || b) is like(¬a) v b). 
 * 
 * Actually, we don't support existencial and universal quantifiers (exist and forAll) 
 * These can be emulated using function in the Expression, but this function should be 
 * free effect colateral
 *   
 *  
 */

@Aspect
public class ContractAspect {

	private static Log log = LogFactory.getLog(ContractAspect.class);

	private static final Pattern oldPattern = Pattern
			.compile("(.*)\\{old (.*?)}(.*)");

	private static final Pattern resultPattern = Pattern
			.compile("(.*)\\{result}(.*)");

	private static final String interfacePattern = "interface";

	private static final String classPattern = "class";

	private static final String preKey = "pre";

	private static final String postKey = "post";

	private static final String invKey = "inv";

	/**
	 * If this is non-null don't execute contracts, used when executing the
	 * expressions.
	 */
	private ThreadLocal checkContracts = new ThreadLocal();

	/**
	 * Matches the execution of any public method with the Contract annotation
	 * set. Los constructores son interceptados pero por el momento no esta implementado
	 * 
	 * @see org.javadbc.attributes.Ensure
	 * @see org.javadbc.attributes.Require
	 * @see org.javadbc.attributes.Invariant
	 */
	

	@Around("execution(public * ((@org.javadbc.attributes.DBC *)+).*(..))") 
	// TODO revisar la expresion para poder 
	//+
	//		" && (@annotation(org.dbc4java.attributes.Require ) || @annotation(org.dbc4java.attributes.Ensure)" +
	//		"    || @annotation(org.dbc4java.attributes.Invariant))") 
    // || "+
	//		 "execution(new(..)) && !within(org.dbc4java.contract.ContractAspect)")
	public Object doAssert(ProceedingJoinPoint pjp) throws Throwable {
		// start assert
		// evaluate preconditions and invariants
		// calculate old values

		// TODO Contemplar el problema de interceptar constructores
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		
		Map<String, String> assertions = new HashMap<String, String>();
		addAssertions(methodSignature.getDeclaringTypeName(), methodSignature
				.getName(), assertions);

		String require = assertions.get(preKey);
		String ensure = assertions.get(postKey);
		String invariant = assertions.get(invKey);

		StringBuffer parsedEnsure = null;
		List oldValues = null;
		List resultValues = null;

		// log.info("require: [" + require + "]");
		// log.info("ensure: [" + ensure + "]");
		// log.info("invariant: [" + invariant + "]");
		log.info("Precondition:" + require);

		// Evaluate Preconditions
		// Invariants && Preconditions 
		if (checkContracts.get() == null) {
			assertExpressionTrue(pjp, invariant, "invariant violated: {0}");
			assertExpressionTrue(pjp, require, "precondition violated: {0}");
			
			// execute and remove the old-references
			// TODO: revisar 
			if (ensure != null) {
				oldValues = new ArrayList();
				parsedEnsure = new StringBuffer();
				Matcher matcher = oldPattern.matcher(ensure);
				while (matcher.matches()) {
					String head = matcher.group(1);
					log.info("head:" + head);
					String old = matcher.group(2);
					String tail = matcher.group(3);
					oldValues.add(executeExpression(pjp, old));
					String oldRef = "#" + getOldReference(oldValues.size() - 1);
					parsedEnsure.append(head + oldRef + tail);
					StringBuffer newParsedEnsure = new StringBuffer();
					newParsedEnsure.append(head + oldRef + tail);
					parsedEnsure=newParsedEnsure;
					matcher = oldPattern.matcher(newParsedEnsure.toString());
				}
				// if there wasn't any old-references just addLink all of the
				// expression
				if (oldValues.size() == 0) {
					parsedEnsure.append(ensure);
				}
				// old Expression
				ensure = parsedEnsure.toString();
			}
		}

		Object retVal = pjp.proceed();
		
		
		if (checkContracts.get() == null){
		// execute and remove the result-references
		// TODO revisar
		if (ensure != null) {
			resultValues = new ArrayList();
			parsedEnsure = new StringBuffer();
			Matcher matcher = resultPattern.matcher(ensure);
			while (matcher.matches()) {
				String head = matcher.group(1);
				log.info("head:" + head);
				String tail = matcher.group(2);
				resultValues.add(retVal);
				String resultRef = "#"
						+ getResultReference(resultValues.size() - 1);
				parsedEnsure.append(head + resultRef + tail);
				StringBuffer newParsedEnsure = new StringBuffer();
				newParsedEnsure.append(head + resultRef + tail);
				parsedEnsure=newParsedEnsure;
				matcher = resultPattern.matcher(newParsedEnsure.toString());
			}
			// if there wasn't any result-references just addLink all of the
			// expression
			if (resultValues.size() == 0) {
				parsedEnsure.append(ensure);
			}
		}
		// evaluate postconditions && invariants
		// check ensures with old-references
		if (parsedEnsure != null) {
			Map context = createContext(pjp);
			if (oldValues != null) {
				for (ListIterator iterator = oldValues.listIterator(); iterator
						.hasNext();) {
					Object oldValue = iterator.next();
					context.put(getOldReference(iterator.previousIndex()),
							oldValue);
				}
			}
			if (resultValues != null) {
				for (ListIterator iterator = resultValues.listIterator(); iterator
						.hasNext();) {
					Object resultValue = iterator.next();
					context.put(getResultReference(iterator.previousIndex()),
							resultValue);
				}
			}
			log.info("Postcondition:" +parsedEnsure.toString() );
			
			assertExpressionTrue(parsedEnsure.toString(), pjp.getThis(),
					context, "postcondition violated: " + ensure);
		}

		log.info("Invariant:" +invariant );
		assertExpressionTrue(pjp, invariant, "invariant violated: {0}");
		}
		return retVal;
	}

	private static String getOldReference(int i) {
		return "old" + i;
	}

	private static String getResultReference(int i) {
		return "result" + i;
	}

	private Object executeExpression(ProceedingJoinPoint invocation,
			String expression) {
		Map context = createContext(invocation);
		try {
			return executeExpression(expression, invocation.getTarget(), context);
		} catch (Exception e) {
			throw new RuntimeException("Could not execute: " + expression, e);
		}
	}

	private void assertExpressionTrue(ProceedingJoinPoint invocation,
			String expression, String message) {
		if (expression != null) {
			Map context = createContext(invocation);
			assertExpressionTrue(expression, invocation.getTarget(), context,
					MessageFormat.format(message, new Object[] { expression }));
		}
	}

	private static Map createContext(ProceedingJoinPoint invocation) {

		Map variables = Ognl.createDefaultContext(invocation.getTarget());
		variables.put("this", invocation.getTarget());
		Object[] args = invocation.getArgs();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				variables.put("arg" + i, arg);
			}
		}
		return variables;
	}

	private void assertExpressionTrue(String expression, Object root,
			Map context, String message) {
		// disable execution of contracts when contracts are executed (to avoid
		// looping)
		if (checkContracts.get() == null) {
			checkContracts.set(checkContracts);
			try {
				boolean result;
				Boolean aBoolean = (Boolean) executeExpression(expression,
						root, context);
				result = aBoolean.booleanValue();
				if (!result) {
					throw new AssertionError(message);
				}
			} catch (MethodFailedException e) {
				if (e.getReason() instanceof Error) {
					throw (Error) e.getReason();
				} else {
					log.error("Could not execute expression: " + expression);
					throw new AssertionError("Could not execute expression: "
							+ expression);
				}
			} catch (Exception e) {
				log.error("Could not execute expression: " + expression);
				throw new AssertionError("Could not execute expression: "
						+ expression);
			} finally {
				checkContracts.set(null);
			}
		}
	}

	private static Object executeExpression(String expression, Object root,
			Map context) throws Exception {
		return Ognl.getValue(Ognl.parseExpression(expression), context, root);
	}

	private String getInvariant(Method method) {
		Invariant invariant = method.getDeclaringClass().getAnnotation(
				Invariant.class);
		return invariant != null ? invariant.value() : null;
	}

	private String getPostcondition(Method method) {
		Ensure ensure = method.getAnnotation(Ensure.class);
		return ensure != null ? ensure.value() : null;
	}

	private String getPrecondition(Method method) {
		Require require = method.getAnnotation(Require.class);
		return require != null ? require.value() : null;
	}

	/**
	 * assertion is a map that contains pre:OrExpresion or null pos:AndExpresion
	 * or null inv:AndExpresion or null
	 * 
	 * @param targetClass
	 * @param targetMethod
	 * @param assertions
	 */
	private void addAssertions(String targetClass, String targetMethod,
			Map<String, String> assertions) {
		try {
			// A class can implement many interfaces and extend only one class
		java.lang.Class c = java.lang.Class.forName(targetClass);

			if (c.toString().contains(classPattern)) {
				Type[] type = c.getInterfaces();
				if (type.length > 0) { // Class or Interface has one or more
					// Inerfaces
					for (Type type2 : type) {
						String interfaceName = StringUtils.replace(
								type2.toString(), interfacePattern, "").trim();
						java.lang.Class i = java.lang.Class
								.forName(interfaceName);
						// An Interface can Extends Many Interfaces
						addAssertions(interfaceName, targetMethod, assertions);
						getAssertions(i, targetMethod, assertions);
					}
				}

				Type clase = c.getSuperclass();
				if (clase != null
						&& !clase.toString().equals("class java.lang.Object")) {
					// Una Interface no puede heredar de una Clase
					String className = StringUtils.replace(clase.toString(),
							classPattern, "").trim();
					java.lang.Class i = java.lang.Class.forName(className);
					// One Class can extend one class, and implement many
					// interfaces
					addAssertions(className, targetMethod, assertions);
					getAssertions(i, targetMethod, assertions);

				} else {
					getAssertions(c, targetMethod, assertions);
				}
			} else { // Interfaces only can extend other interfaces 
				Type[] type = c.getInterfaces();
				if (type.length > 0) { 
					for (Type type2 : type) {
						String interfaceName = StringUtils.replace(
								type2.toString(), interfacePattern, "").trim();
						java.lang.Class i = java.lang.Class
								.forName(interfaceName);
						addAssertions(interfaceName, targetMethod, assertions);
						getAssertions(i, targetMethod, assertions);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			log.error(": WARNING: ClassNotFound : [caught exception: " + e
					+ "]");
		}
	}

	/**
	 * Recupera las aserciones asociadas con una clase, Precondiciones,
	 * Postcondiciones e Invariantes
	 * 
	 * @param classTarget
	 * @param targetMethod
	 * @param assertions
	 *            TODO: mejorar el lookup de assertions
	 */
	private void getAssertions(java.lang.Class classTarget,
			String targetMethod, Map<String, String> assertions) {
		Method[] methods = classTarget.getMethods();
		String pre = null;
		String post = null;
		String inv = null;
		for (Method method : methods) {
			if (method.getName().equals(targetMethod)) {
				pre = getPrecondition(method);
				post = getPostcondition(method);
				inv = getInvariant(method);
				if (log.isInfoEnabled()) {
					log.info("Pre:" + pre);
					log.info("Post:" + post);
					log.info("Inv:" + inv);
				}
			}
		}
		if (assertions.containsKey(preKey) && pre != null) {
			String newPre = assertions.get(preKey) + " || " + pre;
			assertions.put(preKey, newPre);
		} else {
			if (!assertions.containsKey(preKey) && pre != null)
				assertions.put(preKey, pre);
		}
		if (assertions.containsKey(postKey) && post != null) {
			String newPost = assertions.get(postKey) + " && " + post;
			assertions.put(postKey, newPost);
		} else {
			if (!assertions.containsKey(postKey) && post != null)
				assertions.put(postKey, post);
		}

		if (assertions.containsKey(invKey) && post != null) {
			String newPost = assertions.get(invKey) + " && " + inv;
			assertions.put(invKey, newPost);
		} else {
			if (!assertions.containsKey(invKey) && inv != null)
				assertions.put(invKey, inv);
		}

	}

}