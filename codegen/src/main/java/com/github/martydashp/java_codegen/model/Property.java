package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Property {

    private Type type;
    private String name;
    private String javaDoc;
    private String initValue;

    @JacksonXmlElementWrapper(localName = "modifiers")
    @JacksonXmlProperty(localName = "modifier")
    private List<String> modifiers;

    @JacksonXmlElementWrapper(localName = "annotations")
    @JacksonXmlProperty(localName = "annotation")
    private List<Annotation> annotations;
}
