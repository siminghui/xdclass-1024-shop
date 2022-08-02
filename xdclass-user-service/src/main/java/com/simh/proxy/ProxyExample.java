package com.simh.proxy;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @Author: 十七
 * @Date: 2022/8/1 8:16 下午
 * @Description:
 */
@Data
public class ProxyExample {

    static interface Car {
        void running();
    }

    static class Bus implements Car {

        @Override
        public void running() {
            System.out.println("this is bus running");
        }
    }

    static class Taxi implements Car {
        @Override
        public void running() {
            System.out.println("this is taxi running");
        }
    }

    static class JDKProxy implements InvocationHandler {
        // 代理对象
        private Object target;

        // 获取到代理对象
        public Object getInstance(Object target) {
            this.target = target;
            // 取得代理对象
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        }

        /**
         * 执行代理方法
         * @param proxy
         * @param method
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("动态代理之前的业务处理.");
            Object result = method.invoke(target,args);
            return result;
        }
    }

    public static void main(String[] args) {
        //执行JDKProxy
        JDKProxy jdkProxy = new JDKProxy();
        Car instance = (Car) jdkProxy.getInstance(new Taxi());
        instance.running();
    }

}
