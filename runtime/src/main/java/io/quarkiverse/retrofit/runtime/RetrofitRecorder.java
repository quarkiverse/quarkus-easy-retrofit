package io.quarkiverse.retrofit.runtime;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.quarkiverse.retrofit.runtime.global.RetrofitBuilderGlobalConfigProperties;
import io.quarkus.arc.Arc;
import io.quarkus.runtime.RuntimeValue;
import io.quarkus.runtime.annotations.Recorder;
import retrofit2.Retrofit;

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
