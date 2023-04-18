package com.lin.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Annotation used at the field or method/constructor parameter level
 * that indicates a default value expression for the annotated element.
 *
 * @Author linjiayi5
 * @Date 2023/4/18 11:26:25
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    /**
     * The actual value expression such as <code>#{systemProperties.myProp}</code>
     * or property placeholder such as <code>${my.app.myProp}</code>.
     */
    String value();

}
