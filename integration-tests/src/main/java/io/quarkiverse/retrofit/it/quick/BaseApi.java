package io.quarkiverse.retrofit.it.quick;

import io.github.easyretrofit.core.annotation.RetrofitBuilder;
import io.quarkiverse.retrofit.it.common.BodyCallAdapterFactoryBuilder;
import io.quarkiverse.retrofit.it.common.GuavaCallAdapterFactoryBuilder;
import io.quarkiverse.retrofit.it.common.JacksonConvertFactoryBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@RetrofitBuilder(baseUrl = "${backend.base.url}", addConverterFactory = {
        JacksonConvertFactoryBuilder.class }, addCallAdapterFactory = { BodyCallAdapterFactoryBuilder.class,
                GuavaCallAdapterFactoryBuilder.class })
public interface BaseApi {

    @GET("api/hello")
    Call<ResponseBody> hello();
}
