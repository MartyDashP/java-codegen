package com.github.martydashp.javacodegen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.github.martydashp.javacodegen.model.Annotation;
import com.github.martydashp.javacodegen.model.ClassDefinition;
import com.github.martydashp.javacodegen.model.EnumConstant;
import com.github.martydashp.javacodegen.model.EnumDefinition;
import com.github.martydashp.javacodegen.model.InterfaceDefinition;
import com.github.martydashp.javacodegen.model.Method;
import com.github.martydashp.javacodegen.model.Parameter;
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
        classDefinition.setSuperInterfaces(Collections.singletonList("java.io.Serializable"));

        final Source source = new Source();
        source.setPackageName("com.github.martydashp.javacodegen.test");
        source.setDefinition(classDefinition);
        source.setComment("it's test source file");

        return source;
    }

    private Source getEnumSpecModel() {
        final Parameter parameter1 = new Parameter();
        parameter1.setName("stringValue");
        parameter1.setType("String");
        parameter1.setModifiers(Collections.singletonList("final"));

        final Parameter parameter2 = new Parameter();
        parameter2.setName("numberValue");
        parameter2.setType("int");
        parameter2.setModifiers(Collections.singletonList("final"));

        final Method constructor = new Method();
        constructor.setName(Method.constructorName);
        constructor.setParameters(Arrays.asList(parameter1, parameter2));
        constructor.setModifiers(Collections.singletonList("private"));
        constructor.setCode("this.stringValue = stringValue;\nthis.numberValue = numberValue;");

        final Annotation annotation = new Annotation();
        annotation.setName("javax.annotation.processing.Generated");
        final Map<String, String> annotationValues = new HashMap<>();
        annotationValues.put("value", "\"JavaCodeGen\"");
        annotationValues.put("date", String.format("\"%s\"", LocalDateTime.now()));
        annotationValues.put("comments", "\"this is class was generated\"");
        annotation.setValues(annotationValues);

        final Property property1 = new Property();
        property1.setName("stringValue");
        property1.setType("String");

        final Property property2 = new Property();
        property2.setName("numberValue");
        property2.setType("Integer");

        final EnumConstant enumConstant1 = new EnumConstant();
        enumConstant1.setName("ENUM1");
        enumConstant1.setParameters(Arrays.asList("\"val1\"", "1"));

        final EnumConstant enumConstant2 = new EnumConstant();
        enumConstant2.setName("ENUM2");
        enumConstant2.setParameters(Arrays.asList("\"val2\"", "2"));

        final EnumDefinition enumDefinition = new EnumDefinition();
        enumDefinition.setJavaDoc("it's test java enum");
        enumDefinition.setName("TestEnum");
        enumDefinition.setModifiers(Collections.singletonList("public"));
        enumDefinition.setAnnotations(Collections.singletonList(annotation));
        enumDefinition.setMethods(Collections.singletonList(constructor));
        enumDefinition.setProperties(Arrays.asList(property1, property2));
        enumDefinition.setSuperInterfaces(Collections.singletonList("java.io.Serializable"));
        enumDefinition.setConstants(Arrays.asList(enumConstant1, enumConstant2));

        final Source source = new Source();
        source.setPackageName("com.github.martydashp.javacodegen.test");
        source.setDefinition(enumDefinition);

        return source;
    }

    private Source getInterfaceSpecModel() {
        final Parameter parameter1 = new Parameter();
        parameter1.setName("stringValue");
        parameter1.setType("String");
        parameter1.setModifiers(Collections.singletonList("final"));

        final Parameter parameter2 = new Parameter();
        parameter2.setName("numberValue");
        parameter2.setType("int");
        parameter2.setModifiers(Collections.singletonList("final"));

        final Method method1 = new Method();
        method1.setName("defaultMethod");
        method1.setReturnType("void");
        method1.setParameters(Arrays.asList(parameter1, parameter2));
        method1.setModifiers(Arrays.asList("public", "default"));
        method1.setCode("System.out.println(\"it's default method\");");

        final Method method2 = new Method();
        method2.setName("method");
        method2.setReturnType("void");
        method2.setParameters(Arrays.asList(parameter1, parameter2));
        method2.setModifiers(Arrays.asList("public", "abstract"));

        final Annotation annotation = new Annotation();
        annotation.setName("javax.annotation.processing.Generated");
        final Map<String, String> annotationValues = new HashMap<>();
        annotationValues.put("value", "\"JavaCodeGen\"");
        annotationValues.put("date", String.format("\"%s\"", LocalDateTime.now()));
        annotationValues.put("comments", "\"this is class was generated\"");
        annotation.setValues(annotationValues);

        final Property property1 = new Property();
        property1.setName("stringValue");
        property1.setType("String");
        property1.setModifiers(Arrays.asList("PUBLIC", "STATIC", "FINAL"));
        property1.setInitValue("\"FINAL VALUE\"");

        final Property property2 = new Property();
        property2.setName("numberValue");
        property2.setType("Integer");
        property2.setInitValue("100");
        property2.setModifiers(Arrays.asList("PUBLIC", "STATIC", "FINAL"));

        final InterfaceDefinition interfaceDefinition = new InterfaceDefinition();
        interfaceDefinition.setJavaDoc("it's test java interface");
        interfaceDefinition.setName("TestInterface");
        interfaceDefinition.setModifiers(Collections.singletonList("public"));
        interfaceDefinition.setAnnotations(Collections.singletonList(annotation));
        interfaceDefinition.setMethods(Arrays.asList(method1, method2));
        interfaceDefinition.setProperties(Arrays.asList(property1, property2));
        interfaceDefinition.setSuperInterfaces(Collections.singletonList("java.io.Serializable"));

        final Source source = new Source();
        source.setPackageName("com.github.martydashp.javacodegen.test");
        source.setDefinition(interfaceDefinition);

        return source;
    }

    @Test
    void generateJSONSpec() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("class.s.p.e.c.json"), getClassSpecModel());
        mapper.writeValue(getTargetFile("enumSpec.json"), getEnumSpecModel());
        mapper.writeValue(getTargetFile("interfaceSpec.json"), getInterfaceSpecModel());
    }

    @Test
    void generateYAMLSpec() throws IOException {
        final ObjectMapper mapper = new ObjectMapper(
            new YAMLFactory().disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("classSpec.yaml"), getClassSpecModel());
        mapper.writeValue(getTargetFile("enumSpec.yaml"), getEnumSpecModel());
        mapper.writeValue(getTargetFile("interfaceSpec.yaml"), getInterfaceSpecModel());
    }

    @Test
    void generateXMLSpec() throws IOException {
        final XmlMapper mapper = new XmlMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(getTargetFile("classSpec.xml"), getClassSpecModel());
        mapper.writeValue(getTargetFile("enumSpec.xml"), getEnumSpecModel());
        mapper.writeValue(getTargetFile("interfaceSpec.xml"), getInterfaceSpecModel());
    }

}
