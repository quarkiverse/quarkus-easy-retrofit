package io.quarkiverse.quarkus.easy.retrofit.it.api;

import io.github.liuziyuan.retrofit.core.annotation.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@RetrofitBuilder(baseUrl = "${backend.base.url}")
public interface BaseApi {

    @GET("api/hello")
    Call<ResponseBody> hello();
}
