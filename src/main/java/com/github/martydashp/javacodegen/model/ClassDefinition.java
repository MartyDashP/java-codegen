package com.github.martydashp.javacodegen.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ClassDefinition extends InterfaceDefinition {

    private String superClassName;
}
