package org.javadbc.contract;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;
import org.springframework.util.StringUtils;

/**
 * Clase abstracta que define las operaciones basicas para poder 
 * recuperar las aserciones en una jerarquia de herencia.
 * 
 * La implementacion del metodo execute se basa en un metodo abstracto
 * getAssertions, dado a que segun sea el objeto en runtime recuperaremos
 * las aserciones de metodos o constructores.
 * 
 * 
 * @author jvelilla
 *
 */
public abstract class AbstractFeature implements Feature, ContractConstant {

	private static Log log = LogFactory.getLog(AbstractFeature.class);

	protected Map<String, String> assertions=new HashMap<String, String>();
	
	private String featureName = null;

	/* (non-Javadoc)
	 * @see org.javadbc.contract.ContractConstant#execute(java.lang.String, java.lang.String)
	 */
	public void execute(String targetClass, String targetFeature) {
		executeInternal(targetClass, targetFeature);
	}

	/* (non-Javadoc)
	 * @see org.javadbc.contract.ContractConstant#getFeatureName()
	 */
	public String getFeatureName() {
		return featureName;
	}

	/* (non-Javadoc)
	 * @see org.javadbc.contract.ContractConstant#getInvariants()
	 */
	public String getInvariants() {
		return assertions.get(invKey);
	}

	/* (non-Javadoc)
	 * @see org.javadbc.contract.ContractConstant#getPostconditions()
	 */
	public String getPostconditions() {
		return assertions.get(postKey);
	}

	/* (non-Javadoc)
	 * @see org.javadbc.contract.ContractConstant#getPreconditions()
	 */
	public String getPreconditions() {
		return assertions.get(preKey);
	}
	
	
	/**
	 * 
	 * @param targetClass
	 * @param targetFeature
	 */
	private void executeInternal(String targetClass, String targetFeature) {
		try {
			// A class can implement many interfaces and extend only one class
			java.lang.Class c = java.lang.Class.forName(targetClass);
			Type clase = c.getSuperclass();

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
						executeInternal(interfaceName, targetFeature);
						getAssertions(i, targetFeature);
					}

				}
				// Type clase = c.getSuperclass();
				if (clase != null
						&& !clase.toString().equals("class java.lang.Object")) {
					// Una Interface no puede heredar de una Clase
					String className = StringUtils.replace(clase.toString(),
							classPattern, "").trim();
					java.lang.Class i = java.lang.Class.forName(className);
					// One Class can extend one class, and implement many
					// interfaces
					executeInternal(className, targetFeature);
					getAssertions(i, targetFeature);

				}
				getAssertions(c, targetFeature);
			} else { // Interfaces only can extend other interfaces
				Type[] type = c.getInterfaces();
				if (type.length > 0) {
					for (Type type2 : type) {
						String interfaceName = StringUtils.replace(
								type2.toString(), interfacePattern, "").trim();
						java.lang.Class i = java.lang.Class
								.forName(interfaceName);
						executeInternal(interfaceName, targetFeature);
						getAssertions(i, targetFeature);
					}
				}
			}

		} catch (ClassNotFoundException e) {
			log.error(": WARNING: ClassNotFound : [caught exception: " + e
					+ "]");
		}
	}

	
	public void setFeatureName(String name){
		featureName=name;
	}
	protected String getInvariant(Method method) {
		Invariant invariant = method.getDeclaringClass().getAnnotation(
				Invariant.class);
		return invariant != null ? invariant.value() : null;
	}

	protected String getPostcondition(Method method) {
		Ensure ensure = method.getAnnotation(Ensure.class);
		return ensure != null ? ensure.value() : null;
	}

	protected String getPrecondition(Method method) {
		Require require = method.getAnnotation(Require.class);
		return require != null ? require.value() : null;
	}
	
	protected String getInvariant(java.lang.reflect.Constructor constructor) {
		Invariant invariant =(Invariant)constructor.getDeclaringClass().getAnnotation(Invariant.class);
		return invariant != null ? invariant.value() : null;
	}

	protected String getPostcondition(java.lang.reflect.Constructor constructor) {
		Ensure ensure = (Ensure)constructor.getAnnotation(Ensure.class);
		return ensure != null ? ensure.value() : null;
	}

	protected String getPrecondition(java.lang.reflect.Constructor constructor) {
		Require require = (Require)constructor.getAnnotation(Require.class);
		return require != null ? require.value() : null;
	}
	/**
	 * Almacena las precondiciones, postcondiciones e invariantes de clase del
	 * feature. Este metodo sera implementado por las subclases correspondientes
	 * Constructor y Operations respectivamente
	 * 
	 * @param classTarget
	 * @param targetFeature
	 */
	protected abstract void getAssertions(java.lang.Class classTarget,
			String targetFeature);

}
