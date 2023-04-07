package com.lin.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 11:46:19
 */
public class UrlResource implements Resource {

    private URL url;

    public UrlResource(URL url) {
        Assert.notNull(url,"URL must not be null");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        try {
            return urlConnection.getInputStream();
        }
        catch (IOException e) {
            if (urlConnection instanceof HttpURLConnection hr) {
                hr.disconnect();
            }
            throw e;
        }
    }

}
