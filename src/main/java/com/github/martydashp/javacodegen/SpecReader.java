package com.github.martydashp.javacodegen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

    static List<Source> ofJSON(File specFile) throws IOException {
        Objects.requireNonNull(specFile);

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValues(mapper.createParser(specFile), Source.class).readAll();
    }

    static List<Source> ofXML(File specFile) throws IOException {
        Objects.requireNonNull(specFile);

        final XmlMapper mapper = new XmlMapper();
        return mapper.readValues(mapper.createParser(specFile), Source.class).readAll();
    }

    static List<Source> of(File specFile, String specFormat) throws IOException {
        switch (specFormat.toLowerCase()) {
            case "json":
                return ofJSON(specFile);

            case "xml":
                return ofXML(specFile);

            case "yml":
            case "yaml":
                return ofYAML(specFile);
        }

        throw new RuntimeException(String.format("Spec format '%s' is unsupported", specFormat));
    }
}
