package kc.util;/*
 * Copyright 2018 flashhold.com All right reserved. This software is the
 * confidential and proprietary information of flashhold.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with flashhold.com.
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;


@Component("springBeanFactory")
public class SpringBeanFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 获取应用上下文
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        if (null == SpringBeanFactory.applicationContext) {
            SpringBeanFactory.applicationContext = applicationContext;
        }
    }

    /**
     * return application id
     *
     * @return application id
     */
    public static String getApplicationId() {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getId();
    }

    /**
     * 根据bean 名称获取bean实例
     *
     * @param name name
     * @return Object
     */
    public static Object getBean(String name) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name);
    }

    /**
     * 根据class类获取bean实例
     *
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据bean名称和class类获取bean实例
     *
     * @param name  name
     * @param clazz clazz
     * @param <T>   T
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}
