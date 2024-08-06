package io.quarkiverse.retrofit.runtime;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.github.liuziyuan.retrofit.core.generator.RetrofitBuilderGenerator;
import io.github.liuziyuan.retrofit.core.resource.RetrofitClientBean;
import retrofit2.Retrofit;

public class RetrofitInstanceRecorderRegister {

    public Retrofit.Builder getRetrofitBuilderInstance(QuarkusCDIBeanManager cdiBeanManager, String clientBeanInstanceName) {
        RetrofitResourceContext context = cdiBeanManager.getBean(RetrofitResourceContext.class);
        RetrofitClientBean retrofitClientBean = context.getRetrofitClients().stream()
                .filter(clientBean -> clientBean.getRetrofitInstanceName().equals(clientBeanInstanceName)).findFirst()
                .orElse(null);
        RetrofitBuilderGenerator retrofitBuilderGenerator = new RetrofitBuilderGenerator(retrofitClientBean, context,
                cdiBeanManager);
        final Retrofit.Builder retrofitBuilder = retrofitBuilderGenerator.generate();
        return retrofitBuilder;
    }
}
