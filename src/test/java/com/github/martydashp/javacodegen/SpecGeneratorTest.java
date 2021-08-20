package com.github.martydashp.javacodegen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.martydashp.javacodegen.model.Annotation;
import com.github.martydashp.javacodegen.model.ClassDefinition;
import com.github.martydashp.javacodegen.model.Method;
import com.github.martydashp.javacodegen.model.Property;
import com.github.martydashp.javacodegen.model.Source;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class SpecGeneratorTest {

    private File getTargetFile(final String fileName) {
        final File file = new File("src/test/resources/generated-data");
        final String absolutePath = file.getAbsolutePath();

        return new File(String.format("%s/%s", absolutePath, fileName));
    }

    private Source getClassSpecModel() {
        final Method method = new Method();
        method.setName("main");
        method.setCode("System.out.println(\"Hello world\");");
        method.setModifiers(Arrays.asList("public", "static"));
        method.setReturnType("void");

        final Method constructor = new Method();
        constructor.setName(Method.constructorName);
        constructor.setModifiers(Collections.singletonList("private"));

        final Annotation annotation = new Annotation();
        annotation.setName("javax.annotation.processing.Generated");
        final Map<String, String> annotationValues = new HashMap<>();
        annotationValues.put("value", "\"JavaCodeGen\"");
        annotationValues.put("date", String.format("\"%s\"", LocalDateTime.now()));
        annotationValues.put("comments", "\"this is class was generated\"");
        annotation.setValues(annotationValues);

        final Property property1 = new Property();
        property1.setName("firstProperty");
        property1.setInitValue("\"string value\"");
        property1.setType("String");
        property1.setModifiers(Collections.singletonList("private"));

        final Property property2 = new Property();
        property2.setName("secondProperty");
        property2.setType("int");
        property2.setModifiers(Arrays.asList("private", "static"));

        final ClassDefinition classDefinition = new ClassDefinition();
        classDefinition.setJavaDoc("it's test java class");
        classDefinition.setName("TestClass");
        classDefinition.setModifiers(Arrays.asList("final", "public"));
        classDefinition.setAnnotations(Collections.singletonList(annotation));
        classDefinition.setMethods(Arrays.asList(method, constructor));
        classDefinition.setProperties(Arrays.asList(property1, property2));
        classDefinition.setSuperClassName("String");
        classDefinition.setSuperInterface(Collections.singletonList("java.io.Serializable"));

        final Source source = new Source();
        source.setPackageName("com.github.martydashp.javacodegen.test");
        source.setDefinition(classDefinition);
        source.setComment("it's test source file");

        return source;
    }

    @Test
    void generateJSONSpec() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("classSpec.json"), getClassSpecModel());
    }

    @Test
    void generateYAMLSpec() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("classSpec.yaml"), getClassSpecModel());
    }

    @Test
    void generateXMLSpec() throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("classSpec.xml"), getClassSpecModel());
    }

}
