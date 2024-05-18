package io.quarkiverse.quarkus.easy.retrofit.runtime;

import java.util.List;

import io.github.liuziyuan.retrofit.core.*;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfigProperties;

public class RetrofitResourceContextRecorderRegister {

    public RetrofitResourceContext getRetrofitResourceContextInstance(RetrofitAnnotationBean retrofitAnnotationBean,
            RetrofitBuilderGlobalConfigProperties globalConfigProperties) {
        // get retrofitExtension
        RetrofitExtensionRegister retrofitExtensionRegister = new RetrofitExtensionRegister();
        RetrofitBuilderExtension retrofitBuilderExtension = retrofitExtensionRegister
                .getRetrofitBuilderExtension(retrofitAnnotationBean.getRetrofitExtension().getRetrofitBuilderClasses());
        // get interceptorExtensions
        List<RetrofitInterceptorExtension> retrofitInterceptorExtensions = retrofitExtensionRegister
                .getRetrofitInterceptorExtensions(
                        retrofitAnnotationBean.getRetrofitExtension().getRetrofitInterceptorClasses());
        //get globalConfig(BuilderExtension)
        RetrofitBuilderExtensionRegister retrofitBuilderExtensionRegister = new RetrofitBuilderExtensionRegister();
        RetrofitBuilderGlobalConfig globalConfig = retrofitBuilderExtensionRegister.getGlobalConfig(globalConfigProperties,
                retrofitBuilderExtension);
        // create RetrofitResourceContext
        Env env = new QuarkusEnv();
        RetrofitResourceContextBuilder contextBuilder = new RetrofitResourceContextBuilder(env);
        RetrofitResourceContext retrofitResourceContext = contextBuilder.buildContextInstance(
                retrofitAnnotationBean.getBasePackages().toArray(new String[0]),
                retrofitAnnotationBean.getRetrofitBuilderClassSet(),
                globalConfig,
                retrofitInterceptorExtensions, new QuarkusEnv());
        return retrofitResourceContext;
    }
}
