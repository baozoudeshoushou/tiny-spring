package com.lin.springframework.context.annotation;

import java.lang.annotation.*;
import com.lin.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * When used as a type-level annotation in conjunction with
 * {@link com.lin.springframework.stereotype.Component @Component},
 * {@code @Scope} indicates the name of a scope to use for instances of
 * the annotated type.
 *
 * @Author linjiayi5
 * @Date 2023/4/14 15:28:31
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    /**
     * Specifies the name of the scope to use for the annotated component/bean.
     * <p>Defaults to an empty string ({@code ""}) which implies
     * {@link ConfigurableBeanFactory#SCOPE_SINGLETON SCOPE_SINGLETON}.
     * @see ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * @see ConfigurableBeanFactory#SCOPE_SINGLETON
     */
    String value() default "singleton";

}
