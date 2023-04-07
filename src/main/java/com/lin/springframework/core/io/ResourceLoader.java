package com.lin.springframework.core.io;

/**
 * Strategy interface for loading resources (e.g., class path or file system resources)
 *
 * @Author linjiayi5
 * @Date 2023/4/7 11:46:49
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * Return a {@code Resource} handle for the specified resource location.
     * @param location the resource location
     * @return a corresponding {@code Resource} handle (never {@code null})
     */
    Resource getResource(String location);

}
