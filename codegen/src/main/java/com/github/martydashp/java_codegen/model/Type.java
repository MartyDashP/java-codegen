package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class Type {

    private String name;

    @JacksonXmlElementWrapper(localName = "arguments")
    @JacksonXmlProperty(localName = "argument")
    private List<Type> arguments;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }
}
