package com.lin.springframework.context.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.lin.springframework.beans.factory.config.BeanDefinition;
import com.lin.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.lin.springframework.stereotype.Component;

import java.util.Set;

/**
 * A bean definition scanner that detects bean candidates on the classpath,
 * registering corresponding bean definitions with a given registry ({@code BeanFactory}
 * or {@code ApplicationContext}).
 *
 * @Author linjiayi5
 * @Date 2023/4/17 14:23:06
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        Assert.notNull(registry, "Registry must not be null");
        this.registry = registry;
    }

    /**
     * Perform a scan within the specified base packages.
     * @param basePackages the packages to check for annotated classes
     */
    public void scan(String... basePackages) {
        doScan(basePackages);

        AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
    }

    /**
     * Perform a scan within the specified base packages,
     * @param basePackages the packages to check for annotated classes
     */
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                String beanScope = resolveBeanScope(candidate);
                if (StrUtil.isNotBlank(beanScope)) {
                    candidate.setScope(beanScope);
                }
                String beanName = determineBeanName(candidate);
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
    }

    /**
     * 解析 bean 作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return BeanDefinition.SCOPE_DEFAULT;
    }

    /**
     * 决定 beanName，优先选用 Component.value，若为空，使用 beanClassName 的小驼峰命名
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

}
