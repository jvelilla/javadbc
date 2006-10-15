package org.dbc.numbers;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;


@DBC 
@Invariant("getValue() >= 0")
public interface PositiveNumber {
  
	
   @Require("#arg0 >= 0")	 
   @Ensure("{old getValue()} + #arg0 == getValue()")
   public void setValue(double val);

   public double getValue();
   
   @Ensure("Math@abs((Math@sqrt(getValue()) * @Math@sqrt(getValue())) - getValue()) <= 0.001")
   public double getSqrt();
   
   @Ensure("@Math@abs((getValue() + #arg0)  - {result})<=0.0001") 
   public double getSum(double e);
   
   @Require("getValue() + #arg0 >= 0")
   @Ensure("{old getValue()} + #arg0 == getValue()")
   public void add(double e);
   
   public double abs(double d);
}