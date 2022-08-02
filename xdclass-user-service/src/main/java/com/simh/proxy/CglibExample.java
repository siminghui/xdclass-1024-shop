package com.simh.proxy;


import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: 十七
 * @Date: 2022/8/1 9:03 下午
 * @Description:
 */
public class CglibExample {

    static class Car {
        public void running() {
            System.out.println("this is car running");
        }
    }

    static class CglibProxy implements MethodInterceptor {
        // 代理对象
        public Object target;

        public Object getInstance(Object target) {
            this.target = target;

            Enhancer enhancer = new Enhancer();
            // 设置父类为实例类
            enhancer.setSuperclass(this.target.getClass());
            // 回调方法
            enhancer.setCallback(this);

            // 创建代理对象
            return enhancer.create();

        }


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("方法调用前业务处理.");
            Object result = methodProxy.invokeSuper(o, objects);
            return result;
        }
    }

    // 执行 CGLib 的方法调用
    public static void main(String[] args) {
        // 创建 CGLib 代理类
        CglibProxy cglibProxy = new CglibProxy();

        Car instance = (Car) cglibProxy.getInstance(new Car());

        instance.running();
    }

}
