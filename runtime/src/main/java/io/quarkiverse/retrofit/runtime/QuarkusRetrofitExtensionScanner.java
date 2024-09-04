package io.quarkiverse.retrofit.runtime;

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
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String finalStr = sb.toString().replaceAll("\\\\", "").trim();
                String[] split = finalStr.split("=");
                if (RETROFIT_EXTENSION_CLASS_NAME.equalsIgnoreCase(split[0].trim())) {
                    String className = split[1].trim();
                    if (className.contains(",")) {
                        String[] classNames = className.split(",");
                        for (String classname : classNames) {
                            setExtensionNames(classname.trim(), extensionNames);
                        }
                    } else {
                        setExtensionNames(className, extensionNames);
                    }
                }
            }
        }
        return extensionNames;
    }

    private void setExtensionNames(String classname, Set<String> extensionNames) {
        int lastDotIndex = classname.lastIndexOf('.');
        String packageName = classname.substring(0, lastDotIndex);
        extensionNames.add(packageName);
    }
}
