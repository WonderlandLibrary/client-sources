/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.AttributeConverter
 *  javax.persistence.Converter
 */
package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Converter(autoApply=false)
public class ContextDataAttributeConverter
implements AttributeConverter<ReadOnlyStringMap, String> {
    public String convertToDatabaseColumn(ReadOnlyStringMap readOnlyStringMap) {
        if (readOnlyStringMap == null) {
            return null;
        }
        return readOnlyStringMap.toString();
    }

    public ReadOnlyStringMap convertToEntityAttribute(String string) {
        throw new UnsupportedOperationException("Log events can only be persisted, not extracted.");
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((ReadOnlyStringMap)object);
    }
}

