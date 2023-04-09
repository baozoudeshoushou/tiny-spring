package com.lin.springframework.context;

import com.lin.springframework.beans.factory.ListableBeanFactory;

/**
 * Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 *
 * @Author linjiayi5
 * @Date 2023/4/7 21:12:42
 */
public interface ApplicationContext extends ListableBeanFactory {


}
