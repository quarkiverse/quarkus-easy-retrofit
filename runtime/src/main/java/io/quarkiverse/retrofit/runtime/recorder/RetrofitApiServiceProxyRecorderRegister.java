package io.quarkiverse.retrofit.runtime.recorder;

import java.lang.reflect.InvocationHandler;

import io.github.easyretrofit.core.CDIBeanManager;
import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.proxy.JdkDynamicProxy;
import io.github.easyretrofit.core.proxy.RetrofitApiInterfaceInvocationHandler;
import io.github.easyretrofit.core.resource.RetrofitApiInterfaceBean;
import io.quarkus.runtime.RuntimeValue;
import retrofit2.Retrofit;

public class RetrofitApiServiceProxyRecorderRegister<T> {
    private final Class<T> interfaceType;
    private final CDIBeanManager cdiBeanManager;
    private final RetrofitApiInterfaceBean retrofitApiServiceBean;
    private final RuntimeValue<Retrofit> retrofitRuntimeValue;

    public RetrofitApiServiceProxyRecorderRegister(Class<T> interfaceType,
            RuntimeValue<Retrofit> retrofitRuntimeValue, CDIBeanManager cdiBeanManager) {
        this.interfaceType = interfaceType;
        this.retrofitApiServiceBean = cdiBeanManager.getBean(RetrofitResourceContext.class)
                .getRetrofitApiInterfaceBean(interfaceType);
        this.cdiBeanManager = cdiBeanManager;
        this.retrofitRuntimeValue = retrofitRuntimeValue;
    }

    public <T> T build() {
        Retrofit retrofit = retrofitRuntimeValue.getValue();
        Class<?> fallBackClazz = retrofitApiServiceBean.getFallBackClazz();
        Object fallBackBean = null;
        if (fallBackClazz != null) {
            fallBackBean = cdiBeanManager.getBean(fallBackClazz);
        }
        InvocationHandler handler = new RetrofitApiInterfaceInvocationHandler<>(retrofit.create(interfaceType),
                fallBackBean);
        return JdkDynamicProxy.create(interfaceType.getClassLoader(), new Class[] { interfaceType }, handler);
    }
}
