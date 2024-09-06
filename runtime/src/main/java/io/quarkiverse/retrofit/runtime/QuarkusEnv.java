package io.quarkiverse.retrofit.runtime;

import org.eclipse.microprofile.config.ConfigProvider;

import io.github.easyretrofit.core.EnvManager;

/**
 * 实现easy-retrofit core模块的Env接口
 * base.url: <a href="http://localhost:8080">http://localhost:8080</a>,
 * 需要在core代码中解析${base.url}。
 * 在以后的多种框架中，都可以使用其他框架来实现这个接口
 */
public class QuarkusEnv implements EnvManager {

    @Override
    public String resolveRequiredPlaceholders(String text) {
        if (text != null && text.contains("${")) {
            String input = text.replaceAll("\\$\\{(.+?)\\}", "$1");
            return ConfigProvider.getConfig().getValue(input, String.class);
        } else {
            return text;
        }
    }
}
