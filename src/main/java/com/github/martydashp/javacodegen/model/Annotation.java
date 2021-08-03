package com.github.martydashp.javacodegen.model;

import java.util.Map;
import lombok.Data;

@Data
public class Annotation {

    private String name;
    private Map<String, String> values;
}
