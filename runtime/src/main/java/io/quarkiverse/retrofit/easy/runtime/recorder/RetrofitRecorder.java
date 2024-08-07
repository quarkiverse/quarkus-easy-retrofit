package io.quarkiverse.retrofit.easy.runtime.recorder;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.quarkiverse.retrofit.easy.runtime.*;
import io.quarkiverse.retrofit.easy.runtime.global.RetrofitBuilderGlobalConfigProperties;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import retrofit2.Retrofit;

/**
 * easy-retrofit 字节码记录器的实现
 * 获取getRetrofitResourceContextInstance, getRetrofitBuilderInstance, getRetrofitApiInstance,这三个实例需要注入到CDI中的RuntimeValue
 *
 */
@Recorder
public class RetrofitRecorder {

    public RuntimeValue<RetrofitResourceContext> getRetrofitResourceContextInstance(
            RetrofitAnnotationBean retrofitAnnotationBean, RetrofitBuilderGlobalConfigProperties globalConfigProperties) {
        RetrofitResourceContextRecorderRegister register = new RetrofitResourceContextRecorderRegister();
        return new RuntimeValue<>(register.getRetrofitResourceContextInstance(retrofitAnnotationBean, globalConfigProperties));
    }

    public RuntimeValue<Retrofit> getRetrofitInstance(String clientBeanInstanceName) {
        RetrofitInstanceRecorderRegister retrofitInstanceRegister = new RetrofitInstanceRecorderRegister();
        Retrofit.Builder retrofitBuilderInstance = retrofitInstanceRegister
                .getRetrofitBuilderInstance(new QuarkusCDIBeanManager(Arc.container()), clientBeanInstanceName);
        return new RuntimeValue<>(retrofitBuilderInstance.build());
    }

    public RuntimeValue<?> getRetrofitApiInstance(Class<?> interfaceType, RuntimeValue<Retrofit> retrofitRuntimeValue) {

        RetrofitApiServiceProxyRecorderRegister<?> retrofitApiServiceProxyBuilder = new RetrofitApiServiceProxyRecorderRegister<>(
                interfaceType,
                retrofitRuntimeValue, new QuarkusCDIBeanManager(Arc.container()));

        return new RuntimeValue<>(retrofitApiServiceProxyBuilder.build());
    }

}
