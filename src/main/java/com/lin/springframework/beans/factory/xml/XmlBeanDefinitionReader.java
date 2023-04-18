package com.lin.springframework.beans.factory.xml;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.PropertyValue;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.config.BeanReference;
import com.lin.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.lin.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.lin.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import com.lin.springframework.core.io.Resource;
import com.lin.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author linjiayi5
 * @Date 2023/4/7 19:53:26
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public int loadBeanDefinitions(Resource resource) throws BeansException {
        Assert.notNull(resource, "Resource must not be null");
        try {
            InputStream inputStream = resource.getInputStream();
            return doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) throws BeansException {
        Assert.notNull(resources, "Resources must not be null");
        int count = 0;
        for (Resource resource : resources) {
            count += loadBeanDefinitions(resource);
        }
        return count;
    }

    @Override
    public int loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        return loadBeanDefinitions(resource);
    }

    @Override
    public int loadBeanDefinitions(String... locations) throws BeansException {
        Assert.notNull(locations, "Locations must not be null");
        int count = 0;
        for (String location : locations) {
            count += loadBeanDefinitions(location);
        }
        return count;
    }

    /**
     * Actually load bean definitions from the specified XML file.
     *
     * @param inputStream resource inputStream
     * @return the number of bean definitions found
     * @throws ClassNotFoundException
     */
    protected int doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan
        Element componentScan = root.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }

        // 解析 bean
        List<Element> beanList = root.elements("bean");
        int count = 0;
        for (Element bean : beanList) {
            // 解析标签
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethodName = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义 Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 读取属性并填充
            List<Element> propertyList = bean.elements("property");
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
            count ++;
        }

        return count;
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }

}
