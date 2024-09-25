package io.quarkiverse.retrofit.runtime.recorder;

import java.lang.reflect.InvocationHandler;
import java.util.Set;
import java.util.function.Function;

import io.github.easyretrofit.core.CDIBeanManager;
import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.delegate.BaseExceptionDelegate;
import io.github.easyretrofit.core.delegate.ExceptionDelegateSetGenerator;
import io.github.easyretrofit.core.exception.RetrofitExtensionException;
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
        //        Set<BaseExceptionDelegate<? extends RetrofitExtensionException>> exceptionDelegates = new HashSet<>();
        //        Set<Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>>> exceptionDelegateSet = retrofitApiServiceBean
        //                .getExceptionDelegates();
        //        if (exceptionDelegateSet != null) {
        //            for (Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>> entry : exceptionDelegateSet) {
        //                BaseExceptionDelegate<? extends RetrofitExtensionException> exceptionDelegate = cdiBeanManager.getBean(entry);
        //                exceptionDelegates.add(exceptionDelegate);
        //            }
        //        }
        Function<Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>>, BaseExceptionDelegate<? extends RetrofitExtensionException>> function = t -> cdiBeanManager
                .getBean(t);
        Set<BaseExceptionDelegate<? extends RetrofitExtensionException>> exceptionDelegates = ExceptionDelegateSetGenerator
                .generate(retrofitApiServiceBean.getExceptionDelegates(), function);
        Retrofit retrofit = retrofitRuntimeValue.getValue();
        InvocationHandler handler = new RetrofitApiInterfaceInvocationHandler<>(retrofit.create(interfaceType),
                exceptionDelegates);
        return JdkDynamicProxy.create(interfaceType.getClassLoader(), new Class[] { interfaceType }, handler);
    }
}
