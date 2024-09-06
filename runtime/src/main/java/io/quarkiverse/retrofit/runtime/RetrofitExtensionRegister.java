package io.quarkiverse.retrofit.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import io.github.easyretrofit.core.RetrofitBuilderExtension;
import io.github.easyretrofit.core.RetrofitInterceptorExtension;
import io.quarkiverse.retrofit.runtime.global.RetrofitBuilderGlobalConfig;

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
        retrofitBuilderClasses = retrofitBuilderClasses.stream()
                .filter(clazz -> !clazz.equals(RetrofitBuilderGlobalConfig.class)).collect(Collectors.toSet());
        if (retrofitBuilderClasses.size() > 1) {
            LOG.warn("There are multiple RetrofitBuilderExtension class, please check your configuration");
            return null;
        } else if (retrofitBuilderClasses.size() == 1) {
            Class<? extends RetrofitBuilderExtension> clazz = new ArrayList<>(retrofitBuilderClasses).get(0);
            try {
                Constructor<? extends RetrofitBuilderExtension> declaredConstructor = clazz.getDeclaredConstructor();
                return declaredConstructor.newInstance();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

}
