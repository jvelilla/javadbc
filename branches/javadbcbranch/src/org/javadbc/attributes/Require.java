package org.javadbc.attributes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation que define la <strong>Precondicion</strong> de un metodo. Las
 * precondiciones son las obligaciones del cliente, lo cual provee un beneficio
 * al proveedor del servicio. Define lo que el cliente debe satisfacer antes de
 * llamar al servicio
 * 
 * Regla de la Redefinición de Precondiciones: en la redefinición de una rutina,
 * la precondición debe ser más débil  como
 * consecuencia de la ligadura dinámica. 
 * La nueva notación es:
 * 
 * precondición or newPrecondition
 * 
 * @example 1.1 --sin etiquetas
 * 
 * class Example(){
 *   @Require(expresion)
 *   public void someMethod(){
 *   }
 * }
 * 
 * 
 * @example 1.2  -- ejemplo con herencia ver Regla de Redefinicion de Postcondiciones
 *  class Example(){
 *   @Require(expresion)
 *   public void someMethod(){
 *   }
 *   
 *   class SubExample() extends Example {
 *   @Require(newExpresion)
 *   public void someMethod(){
 *   }
 *   
 * }
 * En este caso la precondition para el metodo someMethod es la diyuncion de "expresion or newExpresion"
 * 
 * @see org.javadbc.attributes.Ensure
 * @see org.javadbc.attributes.Invariant
 * @author Jvelilla
 */

@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Require {
	String value() default "true";
}
