package io.quarkiverse.quarkus.easy.retrofit.it.api;

import io.github.liuziyuan.retrofit.core.annotation.RetrofitBuilder;
import io.quarkiverse.quarkus.easy.retrofit.it.api.retrofit.BodyCallAdapterFactoryBuilder;
import io.quarkiverse.quarkus.easy.retrofit.it.api.retrofit.GuavaCallAdapterFactoryBuilder;
import io.quarkiverse.quarkus.easy.retrofit.it.api.retrofit.JacksonConvertFactoryBuilder;
import io.quarkus.runtime.annotations.RegisterForProxy;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@RegisterForProxy
@RetrofitBuilder(baseUrl = "${backend.base.url}", addConverterFactory = {
        JacksonConvertFactoryBuilder.class }, addCallAdapterFactory = { BodyCallAdapterFactoryBuilder.class,
                GuavaCallAdapterFactoryBuilder.class })
public interface BaseApi {

    @GET("api/hello")
    Call<ResponseBody> hello();
}
