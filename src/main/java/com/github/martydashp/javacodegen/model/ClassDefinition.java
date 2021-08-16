package com.github.martydashp.javacodegen.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClassDefinition extends Definition {

    private String superClassName;

    private List<Property> properties;
}
