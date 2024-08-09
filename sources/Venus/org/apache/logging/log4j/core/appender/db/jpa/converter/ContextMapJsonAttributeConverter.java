/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.AttributeConverter
 *  javax.persistence.Converter
 *  javax.persistence.PersistenceException
 */
package org.apache.logging.log4j.core.appender.db.jpa.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class ContextMapJsonAttributeConverter
implements AttributeConverter<Map<String, String>, String> {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String convertToDatabaseColumn(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(map);
        } catch (IOException iOException) {
            throw new PersistenceException("Failed to convert map to JSON string.", (Throwable)iOException);
        }
    }

    public Map<String, String> convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(string, new TypeReference<Map<String, String>>(this){
                final ContextMapJsonAttributeConverter this$0;
                {
                    this.this$0 = contextMapJsonAttributeConverter;
                }
            });
        } catch (IOException iOException) {
            throw new PersistenceException("Failed to convert JSON string to map.", (Throwable)iOException);
        }
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((Map)object);
    }
}

