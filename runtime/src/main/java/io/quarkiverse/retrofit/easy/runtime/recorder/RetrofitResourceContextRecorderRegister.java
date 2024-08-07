package io.quarkiverse.retrofit.easy.runtime.recorder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.liuziyuan.retrofit.core.*;
import io.quarkiverse.retrofit.easy.runtime.QuarkusEnv;
import io.quarkiverse.retrofit.easy.runtime.RetrofitAnnotationBean;
import io.quarkiverse.retrofit.easy.runtime.RetrofitBuilderExtensionRegister;
import io.quarkiverse.retrofit.easy.runtime.RetrofitExtensionRegister;
import io.quarkiverse.retrofit.easy.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.retrofit.easy.runtime.global.RetrofitBuilderGlobalConfigProperties;

/**
 * RetrofitResourceContext object recorder register
 * the RetrofitResourceContext object is easy-retrofit core. through the RetrofitResourceContext object, you can get the retrofit instance or other information.
 */
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
        return contextBuilder.buildContextInstance(
                retrofitAnnotationBean.getBasePackages().toArray(new String[0]),
                retrofitAnnotationBean.getRetrofitBuilderClassSet(),
                globalConfig,
                retrofitInterceptorExtensions, new QuarkusEnv());
    }
}