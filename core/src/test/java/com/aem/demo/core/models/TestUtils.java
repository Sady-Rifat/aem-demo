/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2017 Adobe
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.aem.demo.core.models;

import com.adobe.cq.wcm.core.components.internal.DataLayerConfig;
import com.adobe.cq.wcm.core.components.internal.jackson.DefaultMethodSkippingModuleProvider;
import com.adobe.cq.wcm.core.components.internal.jackson.PageModuleProvider;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.wcm.testing.mock.aem.junit5.AemContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.mockito.Mockito;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

/**
 * Testing utilities.
 */
public class TestUtils {

    /**
     * Provided a {@code model} object and an {@code expectedJsonResource} identifying a JSON file in the class path, this method will
     * test the JSON export of the model and compare it to the JSON object provided by the {@code expectedJsonResource}.
     *
     * @param model                the Sling Model
     * @param expectedJsonResource the class path resource providing the expected JSON object
     */
    public static void testJSONExport(Object model, String expectedJsonResource) {
        Writer writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        PageModuleProvider pageModuleProvider = new PageModuleProvider();
        mapper.registerModule(pageModuleProvider.getModule());
        DefaultMethodSkippingModuleProvider defaultMethodSkippingModuleProvider = new DefaultMethodSkippingModuleProvider();
        mapper.registerModule(defaultMethodSkippingModuleProvider.getModule());
        try {
            mapper.writer().writeValue(writer, model);
        } catch (IOException e) {
            fail(String.format("Unable to generate JSON export for model %s: %s", model.getClass().getName(), e.getMessage()));
        }
        JsonReader outputReader = Json.createReader(IOUtils.toInputStream(writer.toString(), StandardCharsets.UTF_8));
        InputStream is = TestUtils.class.getResourceAsStream(expectedJsonResource);
        if (is != null) {
            JsonReader expectedReader = Json.createReader(is);
            assertEquals(expectedReader.read(), outputReader.read());
        } else {
            fail("Unable to find test file " + expectedJsonResource + ".");
        }
        IOUtils.closeQuietly(is);
    }

    /**
     * Provided a {@link ComponentData} object and an {@code expectedJsonResource} identifying a JSON file in the class path, this method will
     * test the JSON of the data layer and compare it to the JSON object provided by the {@code expectedJsonResource}.
     *
     * @param data                 the component data
     * @param expectedJsonResource the class path resource providing the expected JSON object
     */
    public static void testJSONDataLayer(final ComponentData data, String expectedJsonResource) {
        InputStream is = TestUtils.class.getResourceAsStream(expectedJsonResource);
        try (JsonReader jsonReader = Json.createReader(new StringReader(Objects.requireNonNull(data.getJson())))) {
            if (is != null) {
                JsonStructure expected = Json.createReader(is).read();
                JsonObject actual = jsonReader.readObject();
                assertEquals(expected, actual);
            } else {
                fail("Unable to find test file " + expectedJsonResource + ".");
            }
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * Provided a test base folder ({@code testBase}) and a virtual resource path ({@code testResourcePath}), this method generates the
     * class path resource path for the JSON files that represent the expected exporter output for a component. The returned value is
     * generated using the following concatenation operation:
     *
     * <pre>
     *     testBase + '/exporter-' + fileName(testResourcePath) + '.json'
     * </pre>
     * <p>
     * For example:
     * <pre>
     *     testBase = '/form/button'
     *     testResourcePath = '/content/buttons/button'
     *     output = '/form/button/exporter-button.json'
     * </pre>
     *
     * @param testBase         the test base folder (under the {@code src/test/resources} folder)
     * @param testResourcePath the test resource path in the virtual repository
     * @return the expected class path location of the JSON exporter file
     */
    public static String getTestExporterJSONPath(String testBase, String testResourcePath) {
        return testBase + "/exporter-" + FilenameUtils.getName(testResourcePath) + ".json";
    }

    /**
     * Provided a test base folder ({@code testBase}) and a virtual resource path ({@code testResourcePath}), this method generates the
     * class path resource path for the JSON files that represent the expected data model output for a component. The returned value is
     * generated using the following concatenation operation:
     *
     * <pre>
     *     testBase + '/data-' + fileName(testResourcePath) + '.json'
     * </pre>
     * <p>
     * For example:
     * <pre>
     *     testBase = '/form/button'
     *     testResourcePath = '/content/buttons/button'
     *     output = '/form/button/data-button.json'
     * </pre>
     *
     * @param testBase         the test base folder (under the {@code src/test/resources} folder)
     * @param testResourcePath the test resource path in the virtual repository
     * @return the expected class path location of the JSON data model file
     */
    public static String getTestDataModelJSONPath(String testBase, String testResourcePath) {
        return testBase + "/data-" + FilenameUtils.getName(testResourcePath) + ".json";
    }

    /**
     * Sets the data layer context aware configuration of the AEM test context to enable the data layer.
     *
     * @param context The AEM test context
     * @param enabled {@code true} to enable the data layer, {@code false} to disable it
     */
    public static void enableDataLayer(AemContext context, boolean enabled) {
        configureDataLayer(context, enabled, false);
    }

    /**
     * Sets the data layer context aware configuration of the AEM test context to not include the data layer clientlib.
     *
     * @param context The AEM test context
     * @param skip    {@code true} to not include the data layer clientlib, {@code false} to include it
     */
    public static void skipDataLayerInclude(AemContext context, boolean skip) {
        configureDataLayer(context, true, skip);
    }

    private static void configureDataLayer(AemContext context, boolean enabled, boolean skip) {
        ConfigurationBuilder builder = Mockito.mock(ConfigurationBuilder.class);
        DataLayerConfig dataLayerConfig = Mockito.mock(DataLayerConfig.class);
        lenient().when(dataLayerConfig.enabled()).thenReturn(enabled);
        lenient().when(dataLayerConfig.skipClientlibInclude()).thenReturn(skip);
        lenient().when(builder.as(DataLayerConfig.class)).thenReturn(dataLayerConfig);
        context.registerAdapter(Resource.class, ConfigurationBuilder.class, builder);
    }

    public static Map<String, Object> getJsonObject(String path) {
        Gson gson = new Gson();
        Map<String, Object> objectMap = new HashMap<>();
        InputStream resourceAsStream = TestUtils.class.getResourceAsStream(path);
        if (resourceAsStream != null) {
            String jsonString = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            objectMap = gson.fromJson(jsonString, type);
        }
        return objectMap;
    }

}
