/*
 * Copyright (c) 2020 Richard Hauswald - https://quantummaid.de/.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package de.quantummaid.tutorials.basic.step4;

//Showcase start webservice4

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.quantummaid.httpmaid.HttpMaid;
import de.quantummaid.quantummaid.QuantumMaid;

import static de.quantummaid.httpmaid.events.EventConfigurators.toEnrichTheIntermediateMapWithAllPathParameters;
import static de.quantummaid.httpmaid.usecases.UseCaseConfigurators.toCreateUseCaseInstancesUsing;
import static de.quantummaid.quantummaid.integrations.guice.QuantumMaidGuiceBindings.bindToSinglePublicConstructor;

public final class WebService {
    private static final int PORT = 8080;

    private WebService() {
    }

    public static QuantumMaid createQuantumMaid(final int port) {
        final Injector injector = Guice.createInjector(
                bindToSinglePublicConstructor(GreetingUseCase.class)
        );
        final HttpMaid httpMaid = HttpMaid.anHttpMaid()
                .get("/hello/<name>", GreetingUseCase.class)
                .configured(toEnrichTheIntermediateMapWithAllPathParameters())
                .configured(toCreateUseCaseInstancesUsing(injector::getInstance))
                .build();
        return QuantumMaid.quantumMaid()
                .withHttpMaid(httpMaid)
                .withLocalHostEndpointOnPort(port);
    }

    public static void main(final String[] args) {
        createQuantumMaid(PORT).run();
    }
}
//Showcase end webservice4
