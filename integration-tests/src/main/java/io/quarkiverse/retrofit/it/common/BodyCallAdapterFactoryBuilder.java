package io.quarkiverse.retrofit.it.common;

import jakarta.enterprise.context.ApplicationScoped;

import io.github.easyretrofit.core.builder.BaseCallAdapterFactoryBuilder;
import io.github.liuziyuan.retrofit.adapter.simple.body.BodyCallAdapterFactory;
import retrofit2.CallAdapter;

@ApplicationScoped
public class BodyCallAdapterFactoryBuilder extends BaseCallAdapterFactoryBuilder {
    @Override
    public CallAdapter.Factory buildCallAdapterFactory() {
        return BodyCallAdapterFactory.create();
    }
}
