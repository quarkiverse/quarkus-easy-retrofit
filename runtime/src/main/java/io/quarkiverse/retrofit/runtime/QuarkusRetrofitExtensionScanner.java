package io.quarkiverse.retrofit.runtime;

import io.github.easyretrofit.core.util.PropertiesFileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Easy Retrofit 框架对其扩展的扫描器
 *
 * 核心是扫描 RETROFIT_EXTENSION_PROPERTIES 变量下的 retrofit.extension.name 属性,以获取扩展包名
 */
public class QuarkusRetrofitExtensionScanner {

    private static final String RETROFIT_EXTENSION_PROPERTIES = "META-INF/retrofit-extension.properties";
    private static final String RETROFIT_EXTENSION_CLASS_NAME = "retrofit.extension.name";

    /**
     * Scan packageName
     *
     * @return
     * @throws IOException
     */
    public Set<String> scan() throws IOException {
        Set<String> extensionNames = new HashSet<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(RETROFIT_EXTENSION_PROPERTIES);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            Set<String> propertiesKeys = PropertiesFileUtils.getPropertiesKeys(new InputStreamReader(resource.openStream()), RETROFIT_EXTENSION_CLASS_NAME);
            extensionNames.addAll(propertiesKeys);
        }
        return extensionNames;
    }

    private void setExtensionNames(String classname, Set<String> extensionNames) {
        int lastDotIndex = classname.lastIndexOf('.');
        String packageName = classname.substring(0, lastDotIndex);
        extensionNames.add(packageName);
    }
}
