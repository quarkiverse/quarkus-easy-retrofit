package io.quarkiverse.retrofit.easy.deployment;

import io.github.liuziyuan.retrofit.core.RetrofitResourceContext;
import io.quarkus.builder.item.SimpleBuildItem;

public final class RetrofitResourceContextBuildItem extends SimpleBuildItem {

    private final RetrofitResourceContext context;

    public RetrofitResourceContextBuildItem(RetrofitResourceContext context) {
        this.context = context;
    }

    public RetrofitResourceContext getContext() {
        return context;
    }
}
