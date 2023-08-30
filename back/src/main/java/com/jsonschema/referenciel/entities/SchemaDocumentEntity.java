package com.jsonschema.referenciel.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "schemas")
public class SchemaDocumentEntity {


    @Id
    @JsonProperty("$id")
    private String id;
    @JsonProperty("$schema")
    private String schema;
    private String type;
    private Map<String, Object> properties;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] required;
    private Boolean additionalProperties;


    public SchemaDocumentEntity() {
    }

    public SchemaDocumentEntity(String id, String schema, String type, Map<String, Object> properties, String[] required, Boolean additionalProperties) {
        this.id = id;
        this.schema = schema;
        this.type = type;
        this.properties = properties;
        this.required = required;
        this.additionalProperties = additionalProperties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String[] getRequired() {
        return required;
    }

    public void setRequired(String[] required) {
        this.required = required;
    }

    public Boolean getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Boolean additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}