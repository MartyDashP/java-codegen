package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Parameter {

    private String name;
    private String type;
    private String javaDoc;

    @JacksonXmlElementWrapper(localName = "modifiers")
    @JacksonXmlProperty(localName = "modifier")
    private List<String> modifiers;

    @JacksonXmlElementWrapper(localName = "annotations")
    @JacksonXmlProperty(localName = "annotation")
    private List<Annotation> annotations;
}
