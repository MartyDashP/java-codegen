package com.github.martydashp.javacodegen.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnumDefinition extends Definition {

    private String superClassName;
    private List<Method> methods;
    private List<Property> properties;
}
