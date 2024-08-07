package io.quarkiverse.retrofit.easy.runtime;

import io.github.liuziyuan.retrofit.core.RetrofitBuilderExtension;
import io.quarkiverse.retrofit.easy.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.retrofit.easy.runtime.global.RetrofitBuilderGlobalConfigProperties;

public class RetrofitBuilderExtensionRegister {
    public RetrofitBuilderGlobalConfig getGlobalConfig(RetrofitBuilderGlobalConfigProperties globalConfigProperties,
            RetrofitBuilderExtension retrofitBuilderExtension) {
        return new RetrofitBuilderGlobalConfig(globalConfigProperties, retrofitBuilderExtension);
    }
}
