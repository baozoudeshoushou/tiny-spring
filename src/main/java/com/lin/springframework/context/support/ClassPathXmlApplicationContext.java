package com.lin.springframework.context.support;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 21:13:52
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {

    }

    public ClassPathXmlApplicationContext(String configLocation) {
        this(new String[] {configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }


    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

}
