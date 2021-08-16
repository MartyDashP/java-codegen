package com.github.martydashp.javacodegen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private List<String> modifiers;
    private List<String> exceptions;
    private List<Annotation> annotations;
    private List<Parameter> parameters;
    private List<Generic> generics;

    @JsonIgnore
    public boolean isConstructor() {
        return constructorName.equals(name);
    }
}
