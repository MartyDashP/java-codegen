package com.github.martydashp.java_codegen;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.martydashp.java_codegen.model.Source;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

final class SpecReader {

    enum SpecFormat {
        YML, YAML, JSON, XML
    }

    private static List<Source> of(ObjectMapper mapper, File specFile) throws IOException {
        try (JsonParser parser = mapper.createParser(specFile)) {
            return mapper.readValues(parser, Source.class).readAll();
        }
    }

    static List<Source> of(File specFile, String specFormat) throws IOException {
        return of(specFile, SpecFormat.valueOf(specFormat.toUpperCase()));
    }

    static List<Source> of(File specFile, SpecFormat specFormat) throws IOException {
        Objects.requireNonNull(specFile);

        switch (specFormat) {
            case JSON:
                return of(new ObjectMapper(), specFile);

            case XML:
                return of(new XmlMapper(), specFile);

            case YML:
            case YAML:
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.findAndRegisterModules();
                return of(mapper, specFile);
        }
        return null;
    }
}
