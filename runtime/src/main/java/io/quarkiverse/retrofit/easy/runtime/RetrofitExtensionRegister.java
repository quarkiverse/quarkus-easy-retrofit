package io.quarkiverse.retrofit.easy.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;

import io.github.liuziyuan.retrofit.core.RetrofitBuilderExtension;
import io.github.liuziyuan.retrofit.core.RetrofitInterceptorExtension;

public class RetrofitExtensionRegister {
    private static final Logger LOG = Logger.getLogger(RetrofitExtensionRegister.class);

    public List<RetrofitInterceptorExtension> getRetrofitInterceptorExtensions(
            Set<Class<? extends RetrofitInterceptorExtension>> retrofitInterceptorClasses) {
        List<RetrofitInterceptorExtension> retrofitInterceptorExtensions = new ArrayList<>();
        for (Class<? extends RetrofitInterceptorExtension> retrofitInterceptorClass : retrofitInterceptorClasses) {
            try {
                retrofitInterceptorExtensions.add(retrofitInterceptorClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return retrofitInterceptorExtensions;
    }

    public RetrofitBuilderExtension getRetrofitBuilderExtension(
            Set<Class<? extends RetrofitBuilderExtension>> retrofitBuilderClasses) {
        if (retrofitBuilderClasses.size() > 1) {
            LOG.warn("There are multiple RetrofitBuilderExtension class, please check your configuration");
            return null;
        } else if (retrofitBuilderClasses.size() == 1) {
            Class<? extends RetrofitBuilderExtension> clazz = new ArrayList<>(retrofitBuilderClasses).get(0);
            try {
                return clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

}
