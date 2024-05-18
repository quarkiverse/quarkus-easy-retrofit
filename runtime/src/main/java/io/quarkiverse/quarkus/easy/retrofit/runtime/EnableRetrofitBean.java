package io.quarkiverse.quarkus.easy.retrofit.runtime;

public class EnableRetrofitBean {

    private String[] value;

    private String[] basePackages;

    private Class<?>[] basePackageClasses;

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }

    public Class<?>[] getBasePackageClasses() {
        return basePackageClasses;
    }

    public void setBasePackageClasses(Class<?>[] basePackageClasses) {
        this.basePackageClasses = basePackageClasses;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
