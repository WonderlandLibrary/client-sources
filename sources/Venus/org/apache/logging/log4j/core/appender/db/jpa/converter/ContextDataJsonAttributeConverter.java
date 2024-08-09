/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.AttributeConverter
 *  javax.persistence.Converter
 *  javax.persistence.PersistenceException
 */
package org.apache.logging.log4j.core.appender.db.jpa.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.util.BiConsumer;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class ContextDataJsonAttributeConverter
implements AttributeConverter<ReadOnlyStringMap, String> {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String convertToDatabaseColumn(ReadOnlyStringMap readOnlyStringMap) {
        if (readOnlyStringMap == null) {
            return null;
        }
        try {
            JsonNodeFactory jsonNodeFactory = OBJECT_MAPPER.getNodeFactory();
            ObjectNode objectNode = jsonNodeFactory.objectNode();
            readOnlyStringMap.forEach(new BiConsumer<String, Object>(this, objectNode){
                final ObjectNode val$root;
                final ContextDataJsonAttributeConverter this$0;
                {
                    this.this$0 = contextDataJsonAttributeConverter;
                    this.val$root = objectNode;
                }

                @Override
                public void accept(String string, Object object) {
                    this.val$root.put(string, String.valueOf(object));
                }

                @Override
                public void accept(Object object, Object object2) {
                    this.accept((String)object, object2);
                }
            });
            return OBJECT_MAPPER.writeValueAsString(objectNode);
        } catch (Exception exception) {
            throw new PersistenceException("Failed to convert contextData to JSON string.", (Throwable)exception);
        }
    }

    public ReadOnlyStringMap convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        try {
            StringMap stringMap = ContextDataFactory.createContextData();
            ObjectNode objectNode = (ObjectNode)OBJECT_MAPPER.readTree(string);
            Iterator<Map.Entry<String, JsonNode>> iterator2 = objectNode.fields();
            while (iterator2.hasNext()) {
                Map.Entry<String, JsonNode> entry = iterator2.next();
                String string2 = entry.getValue().textValue();
                stringMap.putValue(entry.getKey(), string2);
            }
            return stringMap;
        } catch (IOException iOException) {
            throw new PersistenceException("Failed to convert JSON string to map.", (Throwable)iOException);
        }
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((ReadOnlyStringMap)object);
    }
}

