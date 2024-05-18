package io.quarkiverse.quarkus.easy.retrofit.runtime;

import io.github.liuziyuan.retrofit.core.RetrofitBuilderExtension;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.quarkus.easy.retrofit.runtime.global.RetrofitBuilderGlobalConfigProperties;

public class RetrofitBuilderExtensionRegister {
    public RetrofitBuilderGlobalConfig getGlobalConfig(RetrofitBuilderGlobalConfigProperties globalConfigProperties,
            RetrofitBuilderExtension retrofitBuilderExtension) {
        return new RetrofitBuilderGlobalConfig(globalConfigProperties, retrofitBuilderExtension);
    }
}
