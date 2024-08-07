package io.quarkiverse.retrofit.easy.it.api.retrofit;

import jakarta.enterprise.context.ApplicationScoped;

import io.github.liuziyuan.retrofit.core.builder.BaseConverterFactoryBuilder;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author liuziyuan
 */
@ApplicationScoped
public class JacksonConvertFactoryBuilder extends BaseConverterFactoryBuilder {

    @Override
    public Converter.Factory buildConverterFactory() {
        return JacksonConverterFactory.create();
    }
}
