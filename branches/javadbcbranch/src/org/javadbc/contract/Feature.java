package org.javadbc.contract;


public interface Feature {

	/**
	 * Return the feature name 
	 * @return
	 */
	String getFeatureName();
	
	/**
	 * Set featureName to name
	 * @param name
	 */
	void setFeatureName(String name);
	
	/**
	 * Get preconditions
	 * @return
	 */
	String getPreconditions();
	
	/**
	 * Get postconditions
	 * @return
	 */
	String getPostconditions();
	
	/**
	 * Get invariants
	 * @return
	 */
	String getInvariants();
	
	/**
	 * Evaluate assertions
	 * 
	 * @param targetClass
	 * @param targetFeature
	 */
	void execute(String targetClass,String targetFeature);
}
