package com.github.martydashp.javacodegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.martydashp.javacodegen.model.Source;
import java.io.File;
import java.io.IOException;
import java.util.List;

final class SpecReader {

    static List<Source> ofYAML(String path) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return mapper.readValues(mapper.createParser(new File(path)), Source.class).readAll();
    }
}
