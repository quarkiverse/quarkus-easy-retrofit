package io.quarkiverse.retrofit.it.single;

import io.github.easyretrofit.core.OverrideRule;
import io.github.easyretrofit.core.annotation.RetrofitBuilder;
import io.github.easyretrofit.core.annotation.RetrofitInterceptor;
import io.quarkiverse.retrofit.it.common.GuavaCallAdapterFactoryBuilder;
import io.quarkiverse.retrofit.it.common.JacksonConvertFactoryBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * <p>
 * <b>Base URLs should always end in {@code /}.</b>
 * <p>
 * <b>Endpoint values which contain a leading {@code /} are absolute.</b>
 * <p>
 * <b>{@code ${app.hello.url}} need set in application.yml</b>
 * <p>
 * <b>Except baseUrl, other attributes are not required</b>
 *
 * @author liuziyuan
 */
@RetrofitBuilder(baseUrl = "${backend.base.url}", addConverterFactory = {
        JacksonConvertFactoryBuilder.class }, addCallAdapterFactory = {
                GuavaCallAdapterFactoryBuilder.class }, globalOverwriteRule = OverrideRule.LOCAL_ONLY)
@RetrofitInterceptor(handler = MyRetrofitInterceptor.class)
public interface HelloApiV2 {

    @GET("api/hello")
    Call<ResponseBody> hello();
}
