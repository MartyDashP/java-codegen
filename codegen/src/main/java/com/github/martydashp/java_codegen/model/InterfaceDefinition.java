package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterfaceDefinition {

    private String kind;
    private String name;
    private String javaDoc;

    @JacksonXmlElementWrapper(localName = "modifiers")
    @JacksonXmlProperty(localName = "modifier")
    private List<String> modifiers;

    @JacksonXmlElementWrapper(localName = "superInterfaces")
    @JacksonXmlProperty(localName = "superInterface")
    private List<Type> superInterfaces;

    @JacksonXmlElementWrapper(localName = "annotations")
    @JacksonXmlProperty(localName = "annotation")
    private List<Annotation> annotations;

    @JacksonXmlElementWrapper(localName = "generics")
    @JacksonXmlProperty(localName = "generic")
    private List<Generic> generics;

    @JacksonXmlElementWrapper(localName = "methods")
    @JacksonXmlProperty(localName = "method")
    private List<Method> methods;

    @JacksonXmlElementWrapper(localName = "properties")
    @JacksonXmlProperty(localName = "property")
    private List<Property> properties;
}
