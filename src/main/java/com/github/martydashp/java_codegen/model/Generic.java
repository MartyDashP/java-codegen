package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Generic {

    private String name;

    @JacksonXmlElementWrapper(localName = "types")
    @JacksonXmlProperty(localName = "type")
    private List<String> types;
}
