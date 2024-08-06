package io.quarkiverse.retrofit.runtime;

import org.eclipse.microprofile.config.ConfigProvider;

import io.github.liuziyuan.retrofit.core.Env;

/**
 * 实现Core的Env接口，由于Core中依赖SpringBoot Environment类的resolveRequiredPlaceholders方法处理在resources配置文件中的参数，例如
 * base.url:http:localhost:8080,
 * 需要在core代码中解析${base.url}。
 * 在以后的多种框架中，都可以使用其他框架来这个接口
 */
public class QuarkusEnv implements Env {

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
