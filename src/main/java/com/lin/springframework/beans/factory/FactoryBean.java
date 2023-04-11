package com.lin.springframework.beans.factory;

/**
 * Interface to be implemented by objects used within a {@link BeanFactory} which
 * are themselves factories for individual objects. If a bean implements this
 * interface, it is used as a factory for an object to expose, not directly as a
 * bean instance that will be exposed itself.
 *
 * @Author linjiayi5
 * @Date 2023/4/11 12:29:38
 */
public interface FactoryBean<T> {

    /**
     * Return an instance (possibly shared or independent) of the object
     * managed by this factory.
     * @return an instance of the bean (can be {@code null})
     * @throws Exception in case of creation errors
     */
    T getObject() throws Exception;

    /**
     * Return the type of object that this FactoryBean creates,
     * or {@code null} if not known in advance.
     * @return the type of object that this FactoryBean creates,
     * or {@code null} if not known at the time of the call
     */
    Class<?> getObjectType();

    /**
     * Is the object managed by this factory a singleton? That is,
     * will {@link #getObject()} always return the same object
     * (a reference that can be cached)?
     * @return whether the exposed object is a singleton
     */
    default boolean isSingleton() {
        return true;
    }

}
