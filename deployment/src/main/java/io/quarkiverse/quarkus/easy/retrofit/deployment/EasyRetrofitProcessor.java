package io.quarkiverse.quarkus.easy.retrofit.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class EasyRetrofitProcessor {

    private static final String FEATURE = "easy-retrofit";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
