package io.quarkiverse.retrofit.easy.runtime;

import java.lang.annotation.*;

/**
 * Enable application to use retrofit-spring-boot-starter
 *
 * @author liuziyuan
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface EnableRetrofit {
    /**
     * Scan base package paths of retrofit resources
     * Same as basePackages method
     *
     * @return basePackages
     */
    String[] value() default {};

    /**
     * Scan base package paths of retrofit resources
     *
     * @return basePackages
     */
    String[] basePackages() default {};

    /**
     * Scan base package paths by classes of retrofit resources
     *
     * @return basePackages
     */
    Class<?>[] basePackageClasses() default {};
}
