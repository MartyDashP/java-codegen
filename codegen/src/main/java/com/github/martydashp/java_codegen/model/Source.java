package com.github.martydashp.java_codegen.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "source")
public class Source {

    private String packageName;
    private String comment;

    @XmlElement
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "kind", visible = true)
    @JsonSubTypes(value = {
        @JsonSubTypes.Type(value = ClassDefinition.class, name = "class"),
        @JsonSubTypes.Type(value = EnumDefinition.class, name = "enum"),
        @JsonSubTypes.Type(value = InterfaceDefinition.class, name = "interface")
    })
    private InterfaceDefinition definition;
}
