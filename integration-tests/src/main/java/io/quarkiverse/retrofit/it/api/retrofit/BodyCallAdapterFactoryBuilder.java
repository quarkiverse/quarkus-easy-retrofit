package io.quarkiverse.retrofit.it.api.retrofit;

import jakarta.enterprise.context.ApplicationScoped;

import io.github.liuziyuan.retrofit.adapter.simple.body.BodyCallAdapterFactory;
import io.github.liuziyuan.retrofit.core.builder.BaseCallAdapterFactoryBuilder;
import retrofit2.CallAdapter;

@ApplicationScoped
public class BodyCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {
    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return BodyCallAdapterFactory.create();
    }
}
