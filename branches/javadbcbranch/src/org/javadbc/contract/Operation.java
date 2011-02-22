package org.javadbc.contract;

import java.lang.reflect.Method;

public class Operation extends AbstractFeature implements ContractConstant {

	
	@Override
	protected void getAssertions(java.lang.Class classTarget,	String targetMethod) {
		
		Method[] methods = classTarget.getMethods();
		String pre = null;
		String post = null;
		String inv = null;
		for (Method method : methods) {
			if (method.getName().equals(targetMethod)) {
				pre = getPrecondition(method);
				post = getPostcondition(method);
				inv = getInvariant(method);
		
			}
		}
		if (assertions.containsKey(preKey) && pre != null) {
			String newPre = "(" + assertions.get(preKey) + " || " + pre + ")";
			assertions.put(preKey, newPre);
		} else {
			if (!assertions.containsKey(preKey) && pre != null)
				assertions.put(preKey, pre);
		}
		if (assertions.containsKey(postKey) && post != null) {
			String newPost = "(" + assertions.get(postKey) + " && " + post
					+ ")";
			assertions.put(postKey, newPost);
		} else {
			if (!assertions.containsKey(postKey) && post != null)
				assertions.put(postKey, post);
		}

		if (assertions.containsKey(invKey) && post != null) {
			String newPost = "(" + assertions.get(invKey) + " && " + inv + ")";
			assertions.put(invKey, newPost);
		} else {
			if (!assertions.containsKey(invKey) && inv != null)
				assertions.put(invKey, inv);
		}

	}
}
