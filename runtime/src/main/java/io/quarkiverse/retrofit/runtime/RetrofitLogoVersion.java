package io.quarkiverse.retrofit.runtime;

import org.jboss.logging.Logger;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.RetrofitResourceContextLog;
import io.github.easyretrofit.core.RetrofitWebFramewrokInfoBean;

public class RetrofitLogoVersion {
    private static final Logger LOG = Logger.getLogger(RetrofitLogoVersion.class);

    public RetrofitResourceContext context;

    public RetrofitLogoVersion(RetrofitResourceContext context) {
        this.context = context;
    }

    public void print() {
        RetrofitResourceContextLog retrofitResourceContextLog = new RetrofitResourceContextLog(context);
        retrofitResourceContextLog
                .showLog(new RetrofitWebFramewrokInfoBean(this.getClass().getPackage().getImplementationTitle(),
                        this.getClass().getPackage().getImplementationVersion()));
    }
}
