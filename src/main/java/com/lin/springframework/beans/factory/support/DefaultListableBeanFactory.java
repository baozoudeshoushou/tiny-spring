package com.lin.springframework.beans.factory.support;

import com.lin.springframework.beans.BeansException;
import com.lin.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.lin.springframework.beans.factory.FactoryBean;
import com.lin.springframework.beans.factory.config.BeanDefinition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @Author linjiayi5
 * @Date 2023/4/4 17:23:42
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    /** List of names of manually registered singletons, in registration order. */
    private volatile Set<String> manualSingletonNames = new LinkedHashSet<>(16);

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        // TODO 不应该只从 BeanDefinition 判断，因为 AOP 代理对象并不在 beanDefinitionMap 中
        List<String> beanNames = new ArrayList<>();

        // Check all bean definitions.
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (requiredType.isAssignableFrom(beanClass)) {
                beanNames.add(entry.getKey());
            }
        }

        // Check manually registered singletons too.
//        for (String beanName : manualSingletonNames) {
//            Object beanInstance = getSingleton(beanName);
//            if (beanInstance != null) {
//                Class<?> beanClass;
//                if (beanInstance instanceof FactoryBean<?> factoryBean) {
//                    beanClass = factoryBean.getObjectType();
//                }
//                else {
//                    beanClass = beanInstance.getClass();
//                }
//                if (requiredType.isAssignableFrom(beanClass)) {
//                    beanNames.add(beanName);
//                }
//            }
//        }

        if (beanNames.size() == 1) {
            return getBean(beanNames.get(0), requiredType);
        }

        throw new BeansException(requiredType + " expected single bean but found " + beanNames.size() + ": " + beanNames);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' available");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        super.registerSingleton(beanName, singletonObject);
        updateManualSingletonNames(set -> set.add(beanName), set -> !this.beanDefinitionMap.containsKey(beanName));
    }

    /**
     * Update the factory's internal set of manual singleton names.
     * @param action the modification action
     * @param condition a precondition for the modification action
     * (if this condition does not apply, the action can be skipped)
     */
    private void updateManualSingletonNames(Consumer<Set<String>> action, Predicate<Set<String>> condition) {
        synchronized (this.beanDefinitionMap) {
            if (condition.test(this.manualSingletonNames)) {
                Set<String> updatedSingletons = new LinkedHashSet<>(this.manualSingletonNames);
                action.accept(updatedSingletons);
                this.manualSingletonNames = updatedSingletons;
            }
        }
    }

    //---------------------------------------------------------------------
    // Implementation of ListableBeanFactory interface
    //---------------------------------------------------------------------

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> beansOfType = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                beansOfType.put(beanName, getBean(beanName, type));
            }
        });
        return beansOfType;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

}

