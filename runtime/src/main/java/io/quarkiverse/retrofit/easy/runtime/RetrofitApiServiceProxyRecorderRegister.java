package io.quarkiverse.retrofit.easy.runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import io.github.liuziyuan.retrofit.core.CDIBeanManager;
import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.github.liuziyuan.retrofit.core.exception.RetrofitExtensionException;
import io.github.liuziyuan.retrofit.core.proxy.BaseExceptionDelegate;
import io.github.liuziyuan.retrofit.core.proxy.RetrofitServiceProxy;
import io.github.liuziyuan.retrofit.core.resource.RetrofitApiServiceBean;
import io.quarkus.runtime.RuntimeValue;
import retrofit2.Retrofit;

public class RetrofitApiServiceProxyRecorderRegister<T> {
    private final Class<T> interfaceType;
    private final CDIBeanManager cdiBeanManager;
    private final RetrofitApiServiceBean retrofitApiServiceBean;
    private final RuntimeValue<Retrofit> retrofitRuntimeValue;

    public RetrofitApiServiceProxyRecorderRegister(Class<T> interfaceType,
            RuntimeValue<Retrofit> retrofitRuntimeValue, CDIBeanManager cdiBeanManager) {
        this.interfaceType = interfaceType;
        this.retrofitApiServiceBean = cdiBeanManager.getBean(RetrofitResourceContext.class)
                .getRetrofitApiServiceBean(interfaceType);
        this.cdiBeanManager = cdiBeanManager;
        this.retrofitRuntimeValue = retrofitRuntimeValue;
    }

    public <T> T build() {
        Set<BaseExceptionDelegate<? extends RetrofitExtensionException>> exceptionDelegates = new HashSet<>();
        Set<Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>>> exceptionDelegateSet = retrofitApiServiceBean
                .getExceptionDelegates();
        if (exceptionDelegateSet != null) {
            for (Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>> entry : exceptionDelegateSet) {
                BaseExceptionDelegate<? extends RetrofitExtensionException> exceptionDelegate = cdiBeanManager.getBean(entry);
                exceptionDelegates.add(exceptionDelegate);
            }
        }
        Retrofit retrofit = retrofitRuntimeValue.getValue();
        InvocationHandler handler = new RetrofitServiceProxy<>(retrofit.create(interfaceType), exceptionDelegates);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[] { interfaceType }, handler);
    }
}
