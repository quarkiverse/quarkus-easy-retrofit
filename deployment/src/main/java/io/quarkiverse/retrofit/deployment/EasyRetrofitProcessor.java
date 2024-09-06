package io.quarkiverse.retrofit.deployment;

import static io.quarkus.deployment.annotations.ExecutionTime.RUNTIME_INIT;
import static io.quarkus.deployment.annotations.ExecutionTime.STATIC_INIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;
import org.jboss.jandex.Type;
import org.jboss.logging.Logger;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.resource.RetrofitApiInterfaceBean;
import io.github.easyretrofit.core.resource.RetrofitClientBean;
import io.quarkiverse.retrofit.runtime.*;
import io.quarkiverse.retrofit.runtime.global.RetrofitGlobalBuilderConfiguration;
import io.quarkiverse.retrofit.runtime.recorder.RetrofitRecorder;
import io.quarkiverse.retrofit.runtime.recorder.RetrofitResourceContextRecorderRegister;
import io.quarkus.arc.deployment.BeanArchiveIndexBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.arc.deployment.UnremovableBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageProxyDefinitionBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;
import io.quarkus.runtime.RuntimeValue;
import retrofit2.Retrofit;

class EasyRetrofitProcessor {
    private static final Logger LOG = Logger.getLogger(EasyRetrofitProcessor.class);

    private static final String FEATURE = "easy-retrofit";

    static DotName ENABLE_RETROFIT_ANNOTATION = DotName.createSimple(EnableRetrofit.class.getName());

    RetrofitGlobalBuilderConfiguration builderConfiguration;

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void reflectiveClasses(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
        reflectiveClass.produce(
                ReflectiveClassBuildItem.builder(RetrofitResourceContext.class).methods(true).fields(true).build());
    }

    @BuildStep
    @Record(STATIC_INIT)
    void registerRetrofitResource(RetrofitRecorder recorder,
            BeanArchiveIndexBuildItem beanArchiveIndex,
            RetrofitGlobalBuilderConfiguration globalConfigProperties,
            BuildProducer<RetrofitResourceContextBuildItem> producer,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanBuildItemBuildProducer) throws IOException {
        IndexView indexView = beanArchiveIndex.getIndex();
        Collection<AnnotationInstance> annotations = indexView.getAnnotations(ENABLE_RETROFIT_ANNOTATION);
        if (annotations.size() > 1) {
            throw new RuntimeException("@EnableRetrofit can only be used once");
        }
        if (annotations.size() == 1) {
            AnnotationInstance annotationInstance = annotations.iterator().next();
            EnableRetrofitBean enableRetrofitBean = new EnableRetrofitBean();
            enableRetrofitBean.setValue(annotationInstance.value("value") == null ? new String[0]
                    : annotationInstance.value("value").asStringArray());
            enableRetrofitBean.setBasePackages(annotationInstance.value("basePackages") == null ? new String[0]
                    : annotationInstance.value("basePackages").asStringArray());
            enableRetrofitBean.setBasePackageClasses(
                    getBasePackageClasses(annotationInstance.value("basePackageClasses") == null ? new Type[0]
                            : annotationInstance.value("basePackageClasses").asClassArray()));
            RetrofitAnnotationBeanRegister retrofitAnnotationBeanRegister = new RetrofitAnnotationBeanRegister();
            RetrofitAnnotationBean retrofitAnnotationBean = retrofitAnnotationBeanRegister.build(enableRetrofitBean);

            RetrofitResourceContextRecorderRegister register = new RetrofitResourceContextRecorderRegister();
            RetrofitResourceContext retrofitResourceContextInstance = register
                    .getRetrofitResourceContextInstance(retrofitAnnotationBean, globalConfigProperties);

            SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator = SyntheticBeanBuildItem
                    .configure(RetrofitResourceContext.class)
                    .scope(Singleton.class)
                    .unremovable()
                    .runtimeValue(recorder.getRetrofitResourceContextInstance(retrofitAnnotationBean, globalConfigProperties));
            syntheticBeanBuildItemBuildProducer.produce(configurator.done());

            producer.produce(new RetrofitResourceContextBuildItem(retrofitResourceContextInstance));
        }
    }

    @BuildStep
    NativeImageProxyDefinitionBuildItem dynamicProxies(RetrofitResourceContextBuildItem retrofitResourceContextBuildItem) {
        if (retrofitResourceContextBuildItem != null) {
            List<String> interfaces = new ArrayList<>();
            Set<RetrofitClientBean> retrofitClientBeanList = retrofitResourceContextBuildItem.getContext()
                    .getRetrofitClients();
            for (RetrofitClientBean clientBean : retrofitClientBeanList) {
                for (RetrofitApiInterfaceBean serviceBean : clientBean.getRetrofitApiInterfaceBeans()) {
                    interfaces.add(serviceBean.getSelfClazz().getName());
                }
            }
            return new NativeImageProxyDefinitionBuildItem(interfaces);
        }
        return null;
    }

    @BuildStep
    @Record(RUNTIME_INIT)
    void registerRetrofitResource(
            RetrofitRecorder recorder,
            RetrofitResourceContextBuildItem retrofitResourceContextBuildItem,
            BuildProducer<UnremovableBeanBuildItem> unremovableBeanBuildItemBuildProducer,
            BuildProducer<SyntheticBeanBuildItem> syntheticBeanBuildItemBuildProducer) {
        if (retrofitResourceContextBuildItem != null) {
            RetrofitResourceContext context = retrofitResourceContextBuildItem.getContext();
            //set unremovable bean
            RetrofitUnremovableBeanRegister unremovableBeanRegister = new RetrofitUnremovableBeanRegister();
            Set<Class<?>> unremovableBeanClasses = unremovableBeanRegister.getUnremovableBeanClasses(context);
            if (!unremovableBeanClasses.isEmpty()) {
                unremovableBeanBuildItemBuildProducer
                        .produce(UnremovableBeanBuildItem.beanTypes(unremovableBeanClasses.toArray(new Class<?>[0])));
            }

            Set<RetrofitClientBean> retrofitClientBeanList = context.getRetrofitClients();
            for (RetrofitClientBean clientBean : retrofitClientBeanList) {
                RuntimeValue<Retrofit> retrofitInstance = recorder.getRetrofitInstance(clientBean.getRetrofitInstanceName());
                for (RetrofitApiInterfaceBean serviceBean : clientBean.getRetrofitApiInterfaceBeans()) {
                    SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator = SyntheticBeanBuildItem
                            .configure(serviceBean.getSelfClazz())
                            .setRuntimeInit()
                            .scope(ApplicationScoped.class)
                            .unremovable()
                            .runtimeValue(
                                    recorder.getRetrofitApiInstance(serviceBean.getSelfClazz(), retrofitInstance))
                            .addQualifier().annotation(Named.class).addValue("value", serviceBean.getSelfClazz().getName())
                            .done();
                    syntheticBeanBuildItemBuildProducer.produce(configurator.done());
                }
            }

            RetrofitLogoVersion retrofitLogoVersion = new RetrofitLogoVersion(context);
            retrofitLogoVersion.print();

        }
    }

    private Class<?>[] getBasePackageClasses(Type[] type) {
        Class<?>[] classes = new Class[type.length];
        for (int i = 0; i < type.length; i++) {
            classes[i] = (Class<?>) type[i].getClass();
        }
        return classes;
    }
}
