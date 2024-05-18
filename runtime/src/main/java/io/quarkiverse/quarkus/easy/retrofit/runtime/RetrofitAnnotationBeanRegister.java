package io.quarkiverse.quarkus.easy.retrofit.runtime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.liuziyuan.retrofit.core.RetrofitResourceScanner;

public class RetrofitAnnotationBeanRegister {
    private RetrofitResourceScanner scanner;

    public RetrofitAnnotationBeanRegister() {
        scanner = new RetrofitResourceScanner();
    }

    public RetrofitAnnotationBean build(EnableRetrofitBean enableRetrofit) {
        RetrofitResourceScanner scanner = new RetrofitResourceScanner();
        List<String> basePackages = getBasePackages(enableRetrofit);
        Set<Class<?>> retrofitBuilderClassSet = scanner.doScan(basePackages.toArray(new String[0]));

        //scan adn set Retrofit extension packages
        QuarkusRetrofitExtensionScanner extensionScanner = new QuarkusRetrofitExtensionScanner();
        try {
            Set<String> extensionPackages = extensionScanner.scan();
            RetrofitResourceScanner.RetrofitExtension retrofitExtension = scanner
                    .doScanExtension(extensionPackages.toArray(new String[0]));
            return new RetrofitAnnotationBean(basePackages, retrofitBuilderClassSet, retrofitExtension);
        } catch (IOException ignored) {
        }
        return null;
    }

    public Set<Class<?>> scanRetrofitResource(EnableRetrofitBean enableRetrofit) {
        // scan RetrofitResource
        List<String> basePackages = getBasePackages(enableRetrofit);
        return scanner.doScan(basePackages.toArray(new String[0]));
    }

    public List<String> getBasePackages(EnableRetrofitBean enableRetrofit) {
        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(Arrays.stream(enableRetrofit.getValue()).filter(StringUtils::isNoneBlank).toList());
        basePackages.addAll(Arrays.stream(enableRetrofit.getBasePackages()).filter(StringUtils::isNoneBlank).toList());
        basePackages.addAll(Arrays.stream(enableRetrofit.getBasePackageClasses()).map(ClassUtils::getPackageName).toList());
        return basePackages;
    }
}
