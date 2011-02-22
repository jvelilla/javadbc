package org.javadbc.contract;


public class Constructor extends AbstractFeature implements ContractConstant{
	
	@Override
	protected void getAssertions(java.lang.Class classTarget,	String targetMethod) {
		
		java.lang.reflect.Constructor[] constructors = classTarget.getConstructors();
		String pre = null;
		String post = null;
		String inv = null;
		for ( java.lang.reflect.Constructor constructor : constructors) {
			if (constructor.getName().equals(targetMethod)) {
				pre = getPrecondition(constructor);
				post = getPostcondition(constructor);
				inv = getInvariant(constructor);
				// if (log.isInfoEnabled()) {
				// log.info("Pre:" + pre);
				// log.info("Post:" + post);
				// log.info("Inv:" + inv);
				// }
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
