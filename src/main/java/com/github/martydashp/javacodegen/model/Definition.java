package com.github.martydashp.javacodegen.model;

import java.util.List;
import lombok.Data;

@Data
public abstract class Definition {

    private String kind;
    private String name;
    private String javaDoc;
    private List<String> modifiers;
    private List<String> superInterface;
    private List<Annotation> annotations;
    private List<Generic> generics;
}
