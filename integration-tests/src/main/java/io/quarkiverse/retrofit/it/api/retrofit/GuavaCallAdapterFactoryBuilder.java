package io.quarkiverse.retrofit.it.api.retrofit;

import jakarta.enterprise.context.ApplicationScoped;

import io.github.liuziyuan.retrofit.core.builder.BaseCallAdapterFactoryBuilder;
import retrofit2.CallAdapter;
import retrofit2.adapter.guava.GuavaCallAdapterFactory;

/**
 * @author liuziyuan
 */
@ApplicationScoped
public class GuavaCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {

    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return GuavaCallAdapterFactory.create();
    }
}
