package com.github.martydashp.javacodegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.martydashp.javacodegen.model.Source;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

final class SpecReader {

    static List<Source> ofYAML(File specFile) throws IOException {
        Objects.requireNonNull(specFile);
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValues(mapper.createParser(specFile), Source.class).readAll();
    }
}
