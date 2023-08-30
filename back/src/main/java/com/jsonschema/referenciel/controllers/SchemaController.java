package com.jsonschema.referenciel.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.jsonschema.referenciel.entities.SchemaDocumentEntity;
import com.jsonschema.referenciel.repositories.SchemaDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schemas")
@CrossOrigin("*")
public class SchemaController {


    @Value("${schemas.baseUri}")
    private String SCHEMA_BASE_URI;


    @Autowired
    SchemaDocumentRepository schemaDocumentRepository;

    @GetMapping(value = "/{schemaName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SchemaDocumentEntity getSchemaByType(@PathVariable String schemaName) throws IOException {

        // verify that it's a json file
        SchemaDocumentEntity schemaDocumentEntity = schemaDocumentRepository.findById(SCHEMA_BASE_URI+schemaName).get();


        return schemaDocumentEntity ;


    }



    @PostMapping(value = "/{schemaName}")
    public void saveNewSchemaByName(@PathVariable String schemaName, @RequestBody String jsonData) throws IOException {



        // verify that it's a json file
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode schemaNode = objectMapper.readTree(jsonData);


        SchemaDocumentEntity schemaToSave = new SchemaDocumentEntity();

        schemaToSave.setId(SCHEMA_BASE_URI+schemaName);

        schemaToSave.setSchema(schemaNode.get("$schema").asText());

        // Convert JSON properties to Map<String, Object>
        JsonNode propertiesNode = schemaNode.get("properties");
        Map<String, Object> propertiesMap = objectMapper.convertValue(propertiesNode, Map.class);
        schemaToSave.setProperties(propertiesMap);

        // Convert JSON required array to String[], or initialize as empty array
        JsonNode requiredNode = schemaNode.get("required");

        String[] requiredArray = objectMapper.convertValue(requiredNode, String[].class);
        schemaToSave.setRequired(requiredArray);


        schemaToSave.setAdditionalProperties(schemaNode.get("additionalProperties").asBoolean(true));

        schemaToSave.setType(schemaNode.get("type").asText());

        schemaDocumentRepository.save(schemaToSave);

    }







    @PostMapping(value = "/validate/{schemaName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateJsonAgainstSchema(@PathVariable String schemaName, @RequestBody JsonNode jsonData) throws Exception {
        SchemaDocumentEntity schemaDocumentEntity = schemaDocumentRepository.findById(SCHEMA_BASE_URI + schemaName).orElse(null);

        if (schemaDocumentEntity == null) {
            return ResponseEntity.badRequest().body("Schema not found for name: " + schemaName);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode schemaNode = objectMapper.valueToTree(schemaDocumentEntity);


        JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaNode);

        ProcessingReport report = schema.validate(jsonData);

        List<String> errorMessages = new ArrayList<>();
        Iterator<ProcessingMessage> iterator = report.iterator();

        while (iterator.hasNext()) {
            ProcessingMessage message = iterator.next();
            if (message.getLogLevel() == LogLevel.ERROR) {
                errorMessages.add(message.getMessage());
            }
        }

        if (errorMessages.isEmpty()) {
            return ResponseEntity.ok("JSON is valid against the schema.");
        } else {
            String errorMessage = String.join("\n", errorMessages);
            return ResponseEntity.badRequest().body("JSON is not valid against the schema.\n" + errorMessage);
        }
    }





}
