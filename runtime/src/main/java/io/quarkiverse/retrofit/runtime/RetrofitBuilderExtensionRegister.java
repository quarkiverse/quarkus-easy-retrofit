package io.quarkiverse.retrofit.runtime;

import io.github.easyretrofit.core.RetrofitBuilderExtension;
import io.quarkiverse.retrofit.runtime.global.RetrofitBuilderGlobalConfig;
import io.quarkiverse.retrofit.runtime.global.RetrofitGlobalBuilderConfiguration;

public class RetrofitBuilderExtensionRegister {
    public RetrofitBuilderGlobalConfig getGlobalConfig(RetrofitGlobalBuilderConfiguration globalConfigProperties,
            RetrofitBuilderExtension retrofitBuilderExtension) {
        return new RetrofitBuilderGlobalConfig(globalConfigProperties, retrofitBuilderExtension);
    }
}
