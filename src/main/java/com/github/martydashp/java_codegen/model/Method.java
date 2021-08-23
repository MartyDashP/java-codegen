package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Method {

    @JsonIgnore
    static public String constructorName = "#constructor";

    private String name;
    private String returnType;
    private String code;
    private String javaDoc;

    @JacksonXmlElementWrapper(localName = "modifiers")
    @JacksonXmlProperty(localName = "modifier")
    private List<String> modifiers;

    @JacksonXmlElementWrapper(localName = "exceptions")
    @JacksonXmlProperty(localName = "exception")
    private List<String> exceptions;

    @JacksonXmlElementWrapper(localName = "annotations")
    @JacksonXmlProperty(localName = "annotation")
    private List<Annotation> annotations;

    @JacksonXmlElementWrapper(localName = "parameters")
    @JacksonXmlProperty(localName = "parameter")
    private List<Parameter> parameters;

    @JacksonXmlElementWrapper(localName = "generics")
    @JacksonXmlProperty(localName = "generic")
    private List<Generic> generics;

    @JsonIgnore
    public boolean isConstructor() {
        return constructorName.equals(name);
    }
}
