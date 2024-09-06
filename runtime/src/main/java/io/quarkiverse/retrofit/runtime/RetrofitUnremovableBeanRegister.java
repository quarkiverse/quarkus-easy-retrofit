package io.quarkiverse.retrofit.runtime;

import java.util.HashSet;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.builder.BaseCallAdapterFactoryBuilder;
import io.github.easyretrofit.core.builder.BaseCallBackExecutorBuilder;
import io.github.easyretrofit.core.builder.BaseCallFactoryBuilder;
import io.github.easyretrofit.core.builder.BaseConverterFactoryBuilder;
import io.github.easyretrofit.core.builder.BaseOkHttpClientBuilder;
import io.github.easyretrofit.core.extension.BaseInterceptor;
import io.github.easyretrofit.core.resource.RetrofitBuilderBean;
import io.github.easyretrofit.core.resource.RetrofitClientBean;
import io.github.easyretrofit.core.resource.RetrofitInterceptorBean;

public class RetrofitUnremovableBeanRegister {

    public Set<Class<?>> getUnremovableBeanClasses(RetrofitResourceContext retrofitResourceContext) {
        Set<Class<?>> unremovableBeanClasses = new HashSet<>();
        Set<RetrofitClientBean> retrofitClients = retrofitResourceContext.getRetrofitClients();
        for (RetrofitClientBean retrofitClient : retrofitClients) {
            RetrofitBuilderBean retrofitBuilder = retrofitClient.getRetrofitBuilder();
            Class<? extends BaseOkHttpClientBuilder> client = retrofitBuilder.getClient();
            addClazz(client, unremovableBeanClasses);
            Class<? extends BaseCallBackExecutorBuilder> callbackExecutor = retrofitBuilder.getCallbackExecutor();
            addClazz(callbackExecutor, unremovableBeanClasses);
            Class<? extends BaseCallFactoryBuilder> callFactory = retrofitBuilder.getCallFactory();
            addClazz(callFactory, unremovableBeanClasses);
            Class<? extends BaseCallAdapterFactoryBuilder>[] addCallAdapterFactory = retrofitBuilder.getAddCallAdapterFactory();
            for (Class<? extends BaseCallAdapterFactoryBuilder> addCallAdapterFactoryClazz : addCallAdapterFactory) {
                addClazz(addCallAdapterFactoryClazz, unremovableBeanClasses);
            }
            Class<? extends BaseConverterFactoryBuilder>[] addConverterFactory = retrofitBuilder.getAddConverterFactory();
            for (Class<? extends BaseConverterFactoryBuilder> addConverterFactoryClazz : addConverterFactory) {
                addClazz(addConverterFactoryClazz, unremovableBeanClasses);
            }
            Set<RetrofitInterceptorBean> interceptors = retrofitClient.getInterceptors();
            for (RetrofitInterceptorBean interceptor : interceptors) {
                Class<? extends BaseInterceptor> handlerClazz = interceptor.getHandler();
                //handlerClazz类上是否包含@ApplicationScoped或者@Singleton
                addClazz(handlerClazz, unremovableBeanClasses);
            }
        }
        return unremovableBeanClasses;
    }

    private static void addClazz(Class<?> handlerClazz, Set<Class<?>> unremovableBeanClasses) {
        if (handlerClazz.isAnnotationPresent(ApplicationScoped.class)
                || handlerClazz.isAnnotationPresent(Singleton.class)) {
            unremovableBeanClasses.add(handlerClazz);
        }
    }

}
