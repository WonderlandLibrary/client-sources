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
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class MarkerAttributeConverter
implements AttributeConverter<Marker, String> {
    public String convertToDatabaseColumn(Marker marker) {
        if (marker == null) {
            return null;
        }
        return marker.toString();
    }

    public Marker convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        int n = string.indexOf("[");
        return n < 1 ? MarkerManager.getMarker(string) : MarkerManager.getMarker(string.substring(0, n));
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((Marker)object);
    }
}

