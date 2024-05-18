package io.quarkiverse.quarkus.easy.retrofit.runtime;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.liuziyuan.retrofit.core.*;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfigProperties;

public class RetrofitResourceContextRecorderRegister {

    public RetrofitResourceContext getRetrofitResourceContextInstance(RetrofitAnnotationBean retrofitAnnotationBean,
            RetrofitBuilderGlobalConfigProperties globalConfigProperties) {
        // get retrofitExtension
        RetrofitExtensionRegister retrofitExtensionRegister = new RetrofitExtensionRegister();
        Set<Class<? extends RetrofitBuilderExtension>> retrofitBuilderClasses = retrofitAnnotationBean.getRetrofitExtension()
                .getRetrofitBuilderClasses();
        Set<Class<? extends RetrofitBuilderExtension>> filteredRetrofitBuilderClasses = retrofitBuilderClasses.stream()
                .filter(element -> {
                    RetrofitBuilderGlobalConfig.class.isAssignableFrom(element);
                    return false;
                }).collect(Collectors.toSet());
        RetrofitBuilderExtension retrofitBuilderExtension = retrofitExtensionRegister
                .getRetrofitBuilderExtension(filteredRetrofitBuilderClasses);
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
