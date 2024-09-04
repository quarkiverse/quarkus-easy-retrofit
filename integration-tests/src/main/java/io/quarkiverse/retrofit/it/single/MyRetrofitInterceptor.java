package io.quarkiverse.retrofit.it.single;

import java.io.IOException;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.extension.BaseInterceptor;
import io.github.easyretrofit.core.resource.RetrofitApiInterfaceBean;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

/**
 *
 * @author liuziyuan
 */
@ApplicationScoped
public class MyRetrofitInterceptor extends BaseInterceptor {

    @Inject
    private RetrofitResourceContext context;

    @Override
    protected Response executeIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        String clazzName = Objects.requireNonNull(request.tag(Invocation.class)).method().getDeclaringClass().getName();
        final RetrofitApiInterfaceBean currentServiceBean = context.getRetrofitApiInterfaceBean(clazzName);
        // TODO if you need
        return chain.proceed(request);
    }

    @Override
    protected RetrofitResourceContext getInjectedRetrofitResourceContext() {
        return context;
    }
}
