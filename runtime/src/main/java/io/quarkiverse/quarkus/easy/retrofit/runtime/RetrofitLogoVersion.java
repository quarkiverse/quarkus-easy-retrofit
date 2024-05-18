package io.quarkiverse.quarkus.easy.retrofit.runtime;

import org.jboss.logging.Logger;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.github.liuziyuan.retrofit.core.RetrofitResourceContextLog;

public class RetrofitLogoVersion {
    private static final Logger LOG = Logger.getLogger(RetrofitLogoVersion.class);

    public RetrofitResourceContext context;

    public RetrofitLogoVersion(RetrofitResourceContext context) {
        this.context = context;
    }

    public void print() {
        LOG.info("\n" +
                "__________        __                 _____.__  __   \n" +
                "\\______   \\ _____/  |________  _____/ ____\\__|/  |_ \n" +
                " |       _// __ \\   __\\_  __ \\/  _ \\   __\\|  \\   __\\\n" +
                " |    |   \\  ___/|  |  |  | \\(  <_> )  |  |  ||  |  \n" +
                " |____|_  /\\___  >__|  |__|   \\____/|__|  |__||__|  \n" +
                "        \\/     \\/                                   \n" +
                "::Quarkus Easy Retrofit Client ::          (" + this.getClass().getPackage().getImplementationVersion()
                + ")\n" +
                "::Retrofit ::                              (v2.9.0)\n");

        RetrofitResourceContextLog retrofitResourceContextLog = new RetrofitResourceContextLog(context);
        retrofitResourceContextLog.showLog();
    }
}
