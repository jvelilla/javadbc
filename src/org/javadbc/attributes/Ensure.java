package org.javadbc.attributes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation que define la <strong>Poscondicion</strong> de metodos. La
 * postcondicion implica un beneficio para el cliente de una dada calse C,
 * usando una rutina r provista en C, la cual describe lo que el proveedor del
 * servicio debe hacer(asumiendo que la precondicion fue satisfecha).
 * 
 * <strong>Regla de la Redefinición de Postcondiciones:</strong> en la redefinición de una
 * rutina, la postcondición debe ser igual o más fuerte, como consecuencia de la
 * ligadura dinámica. La nueva notación es: postcondición and
 * nuevapostcondicion.
 * 
 * @example 1.1 --sin etiquetas
 * 
 * class Example(){
 *   @Ensure(expresion)
 *   public void someMethod(){
 *   }
 * }
 * 
 * 
 * @example 1.3  -- ejemplo con herencia ver Regla de Redefinicion de Postcondiciones
 *  class Example(){
 *   @Ensure(expresion)
 *   public void someMethod(){
 *   }
 *   
 *   class SubExample() extends Example {
 *   @Ensure(newExpresion)
 *   public void someMethod(){
 *   }
 *   
 * }
 * En este caso la postcondicion para el metodo someMethod es la conjuncion de "expresion and newExpresion"
 * 
 * @see org.javadbc.attributes.Require
 * @see org.javadbc.attributes.Invariant
 * @author Jvelilla
 */

@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ensure {
	String value() default "true";
}
