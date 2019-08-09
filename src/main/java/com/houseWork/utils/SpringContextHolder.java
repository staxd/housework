package com.houseWork.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <pre>
 * SpringContextHolder
 * </pre>
 *
 * @author zjw
 * @date 2019/7/5 19.44
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获得spring上下文
     *
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取bean
     *
     * @param name service注解方式name为小驼峰格式
     * @return Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

}
