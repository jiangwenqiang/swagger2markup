/*
 * Copyright 2016 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.swagger2markup.internal.component;

import io.github.swagger2markup.assertions.DiffUtils;
import io.github.swagger2markup.internal.document.builder.OverviewDocumentBuilder;
import io.github.swagger2markup.markup.builder.MarkupDocBuilder;
import io.swagger.models.Info;
import io.swagger.models.License;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;


public class LicenseInfoComponentTest extends AbstractComponentTest{

    private static final String COMPONENT_NAME = "license_info";
    private Path outputDirectory;

    @Before
    public void setUp(){
        outputDirectory = getOutputFile(COMPONENT_NAME);
        FileUtils.deleteQuietly(outputDirectory.toFile());
    }

    @Test
    public void testLicenseInfoComponent() throws URISyntaxException {

        Info info = new Info()
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                .termsOfService("Bla bla bla");
        MarkupDocBuilder markupDocBuilder = new LicenseInfoComponent(getComponentContext(), info, OverviewDocumentBuilder.SECTION_TITLE_LEVEL).render();
        markupDocBuilder.writeToFileWithoutExtension(outputDirectory,  StandardCharsets.UTF_8);

        Path expectedFile = getExpectedFile(COMPONENT_NAME);
        DiffUtils.assertThatFileIsEqual(expectedFile, outputDirectory, getReportName(COMPONENT_NAME));

    }
}
