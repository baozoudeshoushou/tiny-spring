package com.lin.springframework.core.io;

import com.lin.springframework.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:44:57
 */
public class ClassPathResource implements Resource {

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream stream = classLoader.getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return stream;
    }

}
