package io.quarkiverse.retrofit.it.quick;

import io.github.easyretrofit.core.OverrideRule;
import io.github.easyretrofit.core.annotation.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

@RetrofitBuilder(baseUrl = "${backend.base.url}", globalOverwriteRule = OverrideRule.GLOBAL_FIRST)
public interface BaseApi {

    @GET("api/hello")
    Call<ResponseBody> hello();
}
