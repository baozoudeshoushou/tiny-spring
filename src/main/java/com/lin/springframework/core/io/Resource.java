package com.lin.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for a resource descriptor that abstracts from the actual
 * type of underlying resource, such as a file or class path resource.
 *
 * @Author linjiayi5
 * @Date 2023/4/7 11:44:24
 *
 * @see FileSystemResource
 * @see ClassPathResource
 * @see UrlResource
 */
public interface Resource {


    /**
     * Return an {@link InputStream} for the content of an underlying resource.
     * @return the input stream for the underlying resource (must not be {@code null})
     * @throws IOException if the content stream could not be opened
     */
    InputStream getInputStream() throws IOException;

}
