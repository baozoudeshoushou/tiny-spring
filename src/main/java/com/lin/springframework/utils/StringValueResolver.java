package com.lin.springframework.utils;

/**
 * Simple strategy interface for resolving a String value.
 * Used by {@link com.lin.springframework.beans.factory.config.ConfigurableBeanFactory}.
 *
 * @Author linjiayi5
 * @Date 2023/4/18 11:31:54
 */
public interface StringValueResolver {

    /**
     * Resolve the given String value, for example parsing placeholders.
     * @param strVal the original String value (never {@code null})
     * @return the resolved String value (may be {@code null} when resolved to a null
     * value), possibly the original String value itself (in case of no placeholders
     * to resolve or when ignoring unresolvable placeholders)
     * @throws IllegalArgumentException in case of an unresolvable String value
     */
    String resolveStringValue(String strVal);

}
