package com.github.martydashp.javacodegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class EnumConstant {

    private String name;

    @JacksonXmlElementWrapper(localName = "parameters")
    @JacksonXmlProperty(localName = "parameter")
    private List<String> parameters;
}
