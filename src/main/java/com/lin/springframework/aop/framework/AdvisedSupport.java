package com.lin.springframework.aop.framework;

import com.lin.springframework.aop.Advisor;
import com.lin.springframework.aop.MethodMatcher;
import com.lin.springframework.aop.TargetSource;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for AOP proxy configuration managers.
 * These are not themselves AOP proxies, but subclasses of this class are
 * normally factories from which AOP proxy instances are obtained directly.
 *
 * @Author linjiayi5
 * @Date 2023/4/13 14:27:39
 * @see com.lin.springframework.aop.framework.AopProxy
 */
public class AdvisedSupport implements Advised {

    /** Proxy Config 是否开启 cglib 代理 */
    private boolean proxyTargetClass = false;

    /** 被代理的目标对象 */
    private TargetSource targetSource;

    /** The AdvisorChainFactory to use. */
    AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    /** Cache with Method as key and advisor chain List as value. */
    private transient Map<MethodCacheKey, List<Object>> methodCache;

    /**
     * List of Advisors. If an Advice is added, it will be wrapped
     * in an Advisor before being added to this List.
     */
    private List<Advisor> advisors = new ArrayList<>();

    public AdvisedSupport() {
        this.methodCache = new ConcurrentHashMap<>(32);
    }

    /**
     * Determine a list of {@link org.aopalliance.intercept.MethodInterceptor} objects
     * for the given method, based on this configuration.
     * @param method the proxied method
     * @param targetClass the target class
     * @return a List of MethodInterceptors (may also include InterceptorAndDynamicMethodMatchers)
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        MethodCacheKey cacheKey = new MethodCacheKey(method);
        List<Object> cached = this.methodCache.get(cacheKey);
        if (cached == null) {
            cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(
                    this, method, targetClass);
            this.methodCache.put(cacheKey, cached);
        }
        return cached;
    }

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    @Override
    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    @Override
    public TargetSource getTargetSource() {
        return targetSource;
    }

    /**
     * Simple wrapper class around a Method. Used as the key when
     * caching methods, for efficient equals and hashCode comparisons.
     */
    private static final class MethodCacheKey implements Comparable<MethodCacheKey> {

        private final Method method;

        private final int hashCode;

        public MethodCacheKey(Method method) {
            this.method = method;
            this.hashCode = method.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return (this == other || (other instanceof MethodCacheKey methodCacheKey &&
                    this.method == methodCacheKey.method));
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return this.method.toString();
        }

        @Override
        public int compareTo(MethodCacheKey other) {
            int result = this.method.getName().compareTo(other.method.getName());
            if (result == 0) {
                result = this.method.toString().compareTo(other.method.toString());
            }
            return result;
        }
    }

}
