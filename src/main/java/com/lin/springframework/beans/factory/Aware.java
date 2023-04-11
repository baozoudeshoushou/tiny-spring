package com.lin.springframework.beans.factory;

/**
 * A marker superinterface indicating that a bean is eligible to be notified by the
 * Spring container of a particular framework object through a callback-style method.
 * The actual method signature is determined by individual subinterfaces but should
 * typically consist of just one void-returning method that accepts a single argument.
 *
 * @Author linjiayi5
 * @Date 2023/4/10 20:14:19
 */
public interface Aware {

}
