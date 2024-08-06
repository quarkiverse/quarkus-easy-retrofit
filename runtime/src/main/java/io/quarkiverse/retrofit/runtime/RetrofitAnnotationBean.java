package io.quarkiverse.retrofit.runtime;

import java.util.List;
import java.util.Set;

import io.github.liuziyuan.retrofit.core.RetrofitResourceScanner;

public class RetrofitAnnotationBean {

    private final Set<Class<?>> retrofitBuilderClassSet;

    private final List<String> basePackages;

    private final RetrofitResourceScanner.RetrofitExtension retrofitExtension;

    public RetrofitAnnotationBean(List<String> basePackages, Set<Class<?>> retrofitBuilderClassSet,
            RetrofitResourceScanner.RetrofitExtension retrofitExtension) {
        this.retrofitBuilderClassSet = retrofitBuilderClassSet;
        this.basePackages = basePackages;
        this.retrofitExtension = retrofitExtension;
    }

    public Set<Class<?>> getRetrofitBuilderClassSet() {
        return retrofitBuilderClassSet;
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public RetrofitResourceScanner.RetrofitExtension getRetrofitExtension() {
        return retrofitExtension;
    }
}
