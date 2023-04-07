package com.lin.springframework.core.io;

import java.io.*;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:45:33
 */
public class FileSystemResource implements Resource {

    private final File file;

    private final String path;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

}
