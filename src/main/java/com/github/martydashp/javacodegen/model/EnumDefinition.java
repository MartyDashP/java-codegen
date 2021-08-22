package com.github.martydashp.javacodegen.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnumDefinition extends InterfaceDefinition {

    @JacksonXmlElementWrapper(localName = "constants")
    @JacksonXmlProperty(localName = "constant")
    private List<EnumConstant> constants;
}
