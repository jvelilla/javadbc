package org.javadbc.attributes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBC {
 
	/**
	 * The check level type.
	 * <p>Defaults to {@link CheckLevel#PRECONDITION}.
	 */
	CheckLevel level() default CheckLevel.PRECONDITION;

}
