package com.github.martydashp.javacodegen.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.Data;

@Data
public class Source {

    private String packageName;
    private String comment;
    private List<String> imports;
    private String indent;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind")
    @JsonSubTypes(value = {
        @JsonSubTypes.Type(value = ClassDefinition.class, name = "class"),
        @JsonSubTypes.Type(value = EnumDefinition.class, name = "enum")
    })
    private Definition definition;
}
