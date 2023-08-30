package com.jsonschema.referenciel.repositories;

import com.jsonschema.referenciel.entities.SchemaDocumentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchemaDocumentRepository extends MongoRepository<SchemaDocumentEntity, String> {
}
