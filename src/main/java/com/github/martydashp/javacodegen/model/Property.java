package com.github.martydashp.javacodegen.model;

import java.util.List;
import lombok.Data;

@Data
public class Property {

    private String type;
    private String name;
    private List<String> modifiers;
    private String initValue;
    private List<Annotation> annotations;
    private String javaDoc;
}
