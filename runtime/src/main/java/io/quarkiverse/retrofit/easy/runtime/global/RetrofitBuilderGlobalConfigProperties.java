package io.quarkiverse.retrofit.easy.runtime.global;

import java.util.List;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

/**
 * Spring boot web配置文件中声明的全局配置
 */
@ConfigRoot(name = "retrofit.global", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class RetrofitBuilderGlobalConfigProperties {

    /**
     * enable
     */
    @ConfigItem
    public Optional<String> enable;

    /**
     * baseUrl
     */
    @ConfigItem
    public Optional<String> baseUrl;

    /**
     * callAdapterFactoryBuilderClazz
     */
    @ConfigItem
    public Optional<List<String>> callAdapterFactoryBuilderClazz;

    /**
     * converterFactoryBuilderClazz
     */
    @ConfigItem
    public Optional<List<String>> converterFactoryBuilderClazz;

    /**
     * okHttpClientBuilderClazz
     */
    @ConfigItem
    public Optional<String> okHttpClientBuilderClazz;

    /**
     * callBackExecutorBuilderClazz
     */
    @ConfigItem
    public Optional<String> callBackExecutorBuilderClazz;

    /**
     * callFactoryBuilderClazz
     */
    @ConfigItem
    public Optional<String> callFactoryBuilderClazz;

    /**
     * validateEagerly
     */
    @ConfigItem
    public Optional<String> validateEagerly;

}
