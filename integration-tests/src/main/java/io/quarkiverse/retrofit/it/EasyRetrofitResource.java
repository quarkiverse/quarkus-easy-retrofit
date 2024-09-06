/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.quarkiverse.retrofit.it;

import java.io.IOException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import io.github.easyretrofit.core.RetrofitResourceContext;
import io.quarkiverse.retrofit.it.common.BodyCallAdapterFactoryBuilder;
import io.quarkiverse.retrofit.it.quick.BaseApi;
import io.quarkiverse.retrofit.it.single.HelloApi;
import io.quarkiverse.retrofit.it.single.HelloApiV2;
import io.quarkiverse.retrofit.runtime.EnableRetrofit;
import io.quarkiverse.retrofit.runtime.QuarkusCDIBeanManager;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;
import okhttp3.ResponseBody;
import retrofit2.Call;

@EnableRetrofit("io.quarkiverse.retrofit.it")
@Path("/easy-retrofit")
@ApplicationScoped
public class EasyRetrofitResource {
    // add some rest methods here

    @Inject
    RetrofitResourceContext context;

    @Inject
    BaseApi baseApi;

    @Inject
    HelloApi helloApi;

    @Inject
    HelloApiV2 helloApiV2;

    @GET
    @Path("/hello")
    public String hello() throws IOException {
        Call<ResponseBody> hello = baseApi.hello();
        Call<ResponseBody> hello1 = helloApi.hello();
        Call<ResponseBody> hello2 = helloApiV2.hello();
        ArcContainer container = Arc.container();
        QuarkusCDIBeanManager beanManager = new QuarkusCDIBeanManager(container);
        //confirm maybe unused bean unremovable
        assert beanManager.getBean(BodyCallAdapterFactoryBuilder.class) != null;
        String string = hello.execute().body().string();
        String string1 = hello1.execute().body().string();
        String string2 = hello2.execute().body().string();
        assert string.equals(string1) && string.equals(string2);
        return string;
    }

    @GET
    @Path("/context")
    public String getContext() {
        Class<? extends RetrofitResourceContext> clazz = context.getClass();
        return clazz.getName();
    }
}
