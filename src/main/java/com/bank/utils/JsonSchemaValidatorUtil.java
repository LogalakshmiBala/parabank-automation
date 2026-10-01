package com.bank.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public final class JsonSchemaValidatorUtil {
    private JsonSchemaValidatorUtil() {
    }

    public static void validate(String json, String schemaFileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        InputStream schemaStream = JsonSchemaValidatorUtil.class.getClassLoader()
                .getResourceAsStream("schemas/" + schemaFileName);

        if (schemaStream == null) {
            throw new IllegalArgumentException("Schema not found: " + schemaFileName);
        }

        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012).getSchema(schemaStream);
        Set<ValidationMessage> errors = schema.validate(jsonNode);
        if (!errors.isEmpty()) {
            throw new AssertionError("JSON schema validation failed for " + schemaFileName + ": " + errors);
        }
    }
}
