package org.javadbc.attributes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation que describe <strong>Invariante de Clase</strong>
 * 
 * El Invariante de clase especifica las propiedades que cualquier instancia de
 * la clase debe satisfacer en cada instante.
 * 
 * Para especificar el invariante asociado a una clase, se introduce éste con la
 * annotation ‘@Invariant’, como un String que contiene una expresion de
 * condiciones, y una etiqueta, la cual es opcional. En caso de querer definir
 * mas de un simple invariante ver annotation '@Invariants'
 * 
 * @see org.javadbc.attributes.Invariants
 * 
 * 
 * <strong>Regla del Invariante:</stronh> los invariantes de todos las
 * superclases de una clase son añadidos al invariante de dicha clase.
 * 
 * @example 1.1
 * @Invariant(expresion) --sin etiquetas class Example { ... } expresion: es
 *                       cualequier expresion booleana valida en el lenguaje
 *                       Java
 * 
 * @example 1.2
 * @Invariant({expresion,eqiqueta}) class ExampleWithLabel{ -- con etiquetas ... }
 * 
 * @example 1.3 --Ejemplo usando herencia (@see Regla del Invariante)
 * @Invariant(expresion) class Example{ ... }
 * 
 * @Invariant(expresion1) class SubExample extends Example { }
 * 
 * Para SubExample la evaluacion es equivalente a la seiguiente expresion:
 * "expresion and expresion1"
 * 
 * 
 * <TABLE border=1>
 * <TR>
 * <TD>proveer servicio</TD>
 * <TD><strong>Obligaciones</strong></TD>
 * <TD><strong>Beneficiones</strong></TD>
 * </TR>
 * <TR>
 * <TD>Cliente</TD>
 * <TD>Satisfacer la precondicion</TD>
 * <TD>Desde la poscondicion/es</TD>
 * </TR>
 * <TR>
 * <TD>Proveedor</TD>
 * <TD>Satisfacer las postcondiciones</TD>
 * <TD>Desde la precondicion/es</TD>
 * </TR>
 * </TABLE>
 * 
 * @see org.javadbc.attributes.Ensure
 * @see org.javadbc.attributes.Require
 * @author JVelilla
 * 
 */
@Target( { ElementType.TYPE, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Invariant {
	String value() default "true";
}
