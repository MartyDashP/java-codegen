package com.github.martydashp.javacodegen.model;

import java.util.List;
import lombok.Data;

@Data
public class Parameter {

    private String name;
    private String type;
    private String javaDoc;
    private List<String> modifiers;
    private List<Annotation> annotations;
}
