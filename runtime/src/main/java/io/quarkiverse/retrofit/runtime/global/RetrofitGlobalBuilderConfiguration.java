package io.quarkiverse.retrofit.runtime.global;

import java.util.List;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

/**
 * Quarkus配置文件中声明的全局配置
 */
@ConfigMapping(prefix = "retrofit.global")
@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public interface RetrofitGlobalBuilderConfiguration {

    /**
     * retrofit global builder configuration
     *
     * @return retrofit global builder configuration
     */
    RetrofitGlobalBuilderConfig builder();

    interface RetrofitGlobalBuilderConfig {
        /**
         * enable retrofit global builder
         *
         * @return String of Boolean, true false 1 0
         */
        @WithDefault("false")
        Optional<String> enable();

        /**
         * set global builder base url
         *
         * @return base url
         */
        Optional<String> baseUrl();

        /**
         * set global builder class collections of callAdapterFactoryBuilder which need extends BaseCallAdapterFactoryBuilder
         *
         * @return List , The full name of this class
         */
        Optional<List<String>> callAdapterFactoryBuilderClazz();

        /**
         * set global builder class collections of converterFactoryBuilder which need extends BaseConverterFactoryBuilder
         *
         * @return List, The full name of this class
         */
        Optional<List<String>> converterFactoryBuilderClazz();

        /**
         * set global builder class of okHttpClientBuilder which need extends BaseOkHttpClientBuilder
         *
         * @return The full name of this class
         */
        Optional<String> okHttpClientBuilderClazz();

        /**
         * set global builder class of callBackExecutorBuilder which need extends BaseCallBackExecutorBuilder
         *
         * @return The full name of this class
         */
        Optional<String> callBackExecutorBuilderClazz();

        /**
         * set global builder class of callFactoryBuilder which need extends BaseCallFactoryBuilder
         *
         * @return The full name of this class
         */
        Optional<String> callFactoryBuilderClazz();

        /**
         * validateEagerly
         *
         * @return validateEagerly
         */
        Optional<String> validateEagerly();
    }
}
