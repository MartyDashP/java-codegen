package com.github.martydashp.java_codegen.model;

import java.util.Map;
import lombok.Data;

@Data
public class Annotation {

    private String name;
    private Map<String, String> values;
}
