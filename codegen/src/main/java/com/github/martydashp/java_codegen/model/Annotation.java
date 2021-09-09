package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Annotation {

    private String name;

    @JacksonXmlElementWrapper(localName = "elements")
    @JacksonXmlProperty(localName = "element")
    private List<Entry> elements;
}
