package io.quarkiverse.retrofit.runtime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import io.github.easyretrofit.core.resource.ext.ExtensionPropertiesBean;
import io.github.easyretrofit.core.util.PropertiesFileUtils;

/**
 * Easy Retrofit 框架对其扩展的扫描器
 * <p>
 * 核心是扫描 RETROFIT_EXTENSION_PROPERTIES 变量下的 retrofit.extension.name 属性,以获取扩展包名
 */
public class QuarkusRetrofitExtensionScanner {

    private static final String RETROFIT_EXTENSION_PROPERTIES = "META-INF/retrofit-extension.properties";
    private static final String RETROFIT_EXTENSION_CLASS_NAME = "retrofit.extension.name";
    private static final String RETROFIT_EXTENSION_PACKAGE_NAME = "retrofit.resource.package";

    /**
     * Scan packageName
     *
     * @return
     * @throws IOException
     */
    @Deprecated
    public Set<String> scan() throws IOException {
        Set<String> extensionNames = new HashSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(RETROFIT_EXTENSION_PROPERTIES);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            Set<String> propertiesKeys = PropertiesFileUtils.getPropertiesKeys(new InputStreamReader(resource.openStream()),
                    RETROFIT_EXTENSION_CLASS_NAME);
            extensionNames.addAll(propertiesKeys);
        }
        return extensionNames;
    }

    public Set<ExtensionPropertiesBean> scanExtensionProperties() {
        Set<ExtensionPropertiesBean> extensionProperties = new HashSet<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(RETROFIT_EXTENSION_PROPERTIES);
            ExtensionPropertiesBean extensionPropertiesBean;
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource != null) {
                    extensionPropertiesBean = new ExtensionPropertiesBean();
                    extensionPropertiesBean.setExtensionClassPaths(PropertiesFileUtils
                            .getPropertiesKeys(new InputStreamReader(resource.openStream()), RETROFIT_EXTENSION_CLASS_NAME));
                    extensionPropertiesBean.setResourcePackages(PropertiesFileUtils
                            .getPropertiesKeys(new InputStreamReader(resource.openStream()), RETROFIT_EXTENSION_PACKAGE_NAME));
                    extensionProperties.add(extensionPropertiesBean);
                }
            }
        } catch (IOException ignored) {
        }

        return extensionProperties;
    }

}
