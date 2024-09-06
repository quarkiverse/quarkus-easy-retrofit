package io.quarkiverse.retrofit.runtime.recorder;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.generator.RetrofitBuilderGenerator;
import io.github.easyretrofit.core.resource.RetrofitClientBean;
import io.quarkiverse.retrofit.runtime.QuarkusCDIBeanManager;
import retrofit2.Retrofit;

public class RetrofitInstanceRecorderRegister {

    public Retrofit.Builder getRetrofitBuilderInstance(QuarkusCDIBeanManager cdiBeanManager, String clientBeanInstanceName) {
        RetrofitResourceContext context = cdiBeanManager.getBean(RetrofitResourceContext.class);
        RetrofitClientBean retrofitClientBean = context.getRetrofitClients().stream()
                .filter(clientBean -> clientBean.getRetrofitInstanceName().equals(clientBeanInstanceName)).findFirst()
                .orElse(null);
        RetrofitBuilderGenerator retrofitBuilderGenerator = new RetrofitBuilderGenerator(retrofitClientBean, context,
                cdiBeanManager);
        return retrofitBuilderGenerator.generate();
    }
}
