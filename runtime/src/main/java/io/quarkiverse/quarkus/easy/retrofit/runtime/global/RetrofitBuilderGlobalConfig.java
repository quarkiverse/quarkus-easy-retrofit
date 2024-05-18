package io.quarkiverse.quarkus.easy.retrofit.runtime.global;

import java.util.List;

import io.github.liuziyuan.retrofit.core.RetrofitBuilderExtension;
import io.github.liuziyuan.retrofit.core.builder.*;
import io.github.liuziyuan.retrofit.core.util.BooleanUtil;

/**
 * 这个类合并了自定义的配置和web的resources文件夹中配置文件中的配置
 * 合并原则是：以下优先级：resources文件夹中配置大于自定义配置，如果resources文件夹中没有配置，则使用自定义配置
 */
public class RetrofitBuilderGlobalConfig implements RetrofitBuilderExtension {

    private final RetrofitBuilderGlobalConfigProperties properties;
    private final RetrofitBuilderExtension customConfig;

    public RetrofitBuilderGlobalConfig(RetrofitBuilderGlobalConfigProperties properties,
            RetrofitBuilderExtension customConfig) {
        this.properties = properties;
        this.customConfig = customConfig;
    }

    @Override
    public boolean enable() {
        if (properties.enable.orElse(null) != null) {
            return BooleanUtil.transformToBoolean(properties.enable.get());
        }
        return customConfig != null && customConfig.enable();
    }

    private boolean propertiesEnable() {
        return BooleanUtil.transformToBoolean(properties.enable.orElse(null));
    }

    @Override
    public String globalBaseUrl() {
        if (propertiesEnable() && properties.baseUrl.orElse(null) != null) {
            return properties.baseUrl.get();
        }
        return customConfig == null ? null : customConfig.globalBaseUrl();
    }

    @Override
    public Class<? extends BaseCallAdapterFactoryBuilder>[] globalCallAdapterFactoryBuilderClazz() {
        List<String> strings = properties.callAdapterFactoryBuilderClazz.orElse(null);
        if (propertiesEnable() && strings != null) {
            Class<? extends BaseCallAdapterFactoryBuilder>[] classList = new Class[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                try {
                    classList[i] = (Class<? extends BaseCallAdapterFactoryBuilder>) Class.forName(strings.get(i));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return classList;
        }
        return customConfig == null ? null : customConfig.globalCallAdapterFactoryBuilderClazz();
    }

    @Override
    public Class<? extends BaseConverterFactoryBuilder>[] globalConverterFactoryBuilderClazz() {
        List<String> strings = properties.converterFactoryBuilderClazz.orElse(null);
        if (propertiesEnable() && strings != null) {
            Class<? extends BaseConverterFactoryBuilder>[] classList = new Class[strings.size()];
            for (int i = 0; i < strings.size(); i++) {
                try {
                    classList[i] = (Class<? extends BaseConverterFactoryBuilder>) Class.forName(strings.get(i));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return classList;
        }
        return customConfig == null ? null : customConfig.globalConverterFactoryBuilderClazz();
    }

    @Override
    public Class<? extends BaseOkHttpClientBuilder> globalOkHttpClientBuilderClazz() {
        String string = properties.okHttpClientBuilderClazz.orElse(null);
        if (propertiesEnable() && string != null) {
            Class<? extends BaseOkHttpClientBuilder> clazz;
            try {
                clazz = (Class<? extends BaseOkHttpClientBuilder>) Class.forName(string);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return clazz;
        }
        return customConfig == null ? null : customConfig.globalOkHttpClientBuilderClazz();
    }

    @Override
    public Class<? extends BaseCallBackExecutorBuilder> globalCallBackExecutorBuilderClazz() {
        String string = properties.callBackExecutorBuilderClazz.orElse(null);
        if (propertiesEnable() && string != null) {
            Class<? extends BaseCallBackExecutorBuilder> clazz;
            try {
                clazz = (Class<? extends BaseCallBackExecutorBuilder>) Class.forName(string);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return clazz;
        }
        return customConfig == null ? null : customConfig.globalCallBackExecutorBuilderClazz();
    }

    @Override
    public Class<? extends BaseCallFactoryBuilder> globalCallFactoryBuilderClazz() {
        String string = properties.callFactoryBuilderClazz.orElse(null);
        if (propertiesEnable() && string != null) {
            Class<? extends BaseCallFactoryBuilder> clazz;
            try {
                clazz = (Class<? extends BaseCallFactoryBuilder>) Class.forName(string);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return clazz;
        }
        return customConfig == null ? null : customConfig.globalCallFactoryBuilderClazz();
    }

    @Override
    public String globalValidateEagerly() {
        String string = properties.validateEagerly.orElse(null);
        if (propertiesEnable() && string != null) {
            return string;
        }
        return customConfig == null ? null : customConfig.globalValidateEagerly();
    }
}
