package io.quarkiverse.retrofit.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.easyretrofit.core.RetrofitResourceScanner;
import io.github.easyretrofit.core.resource.ext.ExtensionPropertiesBean;

public class RetrofitAnnotationBeanRegister {
    private RetrofitResourceScanner scanner;

    public RetrofitAnnotationBeanRegister() {
        scanner = new RetrofitResourceScanner();
    }

    public RetrofitAnnotationBean build(EnableRetrofitBean enableRetrofit) {
        //scan retrofit extension properties file
        QuarkusRetrofitExtensionScanner extensionScanner = new QuarkusRetrofitExtensionScanner();
        Set<ExtensionPropertiesBean> extensionPropertiesBeans = extensionScanner.scanExtensionProperties();
        Set<String> extensionPackages = extensionPropertiesBeans.stream()
                .flatMap(extensionPropertiesBean -> extensionPropertiesBean.getExtensionClassPaths().stream())
                .collect(Collectors.toSet());
        Set<String> resourcePackages = extensionPropertiesBeans.stream()
                .flatMap(extensionPropertiesBean -> extensionPropertiesBean.getResourcePackages().stream())
                .collect(Collectors.toSet());
        //scan and set Retrofit resource packages
        RetrofitResourceScanner scanner = new RetrofitResourceScanner();
        List<String> basePackages = getBasePackages(enableRetrofit);
        // merge basePackages and resourcePackages
        basePackages.addAll(resourcePackages);
        // get retrofit builder classes
        Set<Class<?>> retrofitBuilderClassSet = scanner.doScan(basePackages.toArray(new String[0]));
        // get retrofit extension object
        RetrofitResourceScanner.RetrofitExtension retrofitExtension = scanner
                .doScanExtension(extensionPackages.toArray(new String[0]));
        // new RetrofitAnnotationBean
        return new RetrofitAnnotationBean(basePackages, retrofitBuilderClassSet, retrofitExtension);
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
