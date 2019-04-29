package com.iloveqyc.daemon.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 使用类加载器来同时加载2个版本的第三方包
 *
 * 注：第三方包不能用pom引入到类路径中，否则系统会用application class加载包中的类名，而不是用自定义加载器
 */
public class ClassLoader {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException,
                                                  InvocationTargetException, IllegalAccessException,
                                                  InstantiationException {
        java.lang.ClassLoader classLoaderA = new URLClassLoader(new URL[]{
                new URL("file:/Users/qiuyongchen/.m2/repository/com/dianping/overseas-openplatform-api/0.2.54-qyc1-SNAPSHOT/overseas-openplatform-api-0.2.54-qyc1-SNAPSHOT.jar")
        }, ClassLoader.class.getClassLoader());
        java.lang.ClassLoader classLoaderB = new URLClassLoader(new URL[]{
                new URL("file:/Users/qiuyongchen/.m2/repository/com/dianping/overseas-openplatform-api/0.2.54-qyc2-SNAPSHOT/overseas-openplatform-api-0.2.54-qyc2-SNAPSHOT.jar")
        }, ClassLoader.class.getClassLoader());

        Class clazz1 = classLoaderA.loadClass("com.dianping.overseas.openplatform.api.dto.Test");
        Method m = clazz1.getMethod("m", String.class);
        System.out.println(m.invoke(clazz1.newInstance(), "x"));

        Class clazz2 = classLoaderB.loadClass("com.dianping.overseas.openplatform.api.dto.Test");
        Method m2 = clazz2.getMethod("m", String.class);
        System.out.println(m2.invoke(clazz2.newInstance(), "x"));

    }
}
