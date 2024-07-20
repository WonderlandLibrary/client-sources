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
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.persistence.PersistenceException;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextMapJsonAttributeConverter;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class ContextStackJsonAttributeConverter
implements AttributeConverter<ThreadContext.ContextStack, String> {
    public String convertToDatabaseColumn(ThreadContext.ContextStack contextStack) {
        if (contextStack == null) {
            return null;
        }
        try {
            return ContextMapJsonAttributeConverter.OBJECT_MAPPER.writeValueAsString(contextStack.asList());
        } catch (IOException e) {
            throw new PersistenceException("Failed to convert stack list to JSON string.", (Throwable)e);
        }
    }

    public ThreadContext.ContextStack convertToEntityAttribute(String s) {
        List<String> list;
        if (Strings.isEmpty(s)) {
            return null;
        }
        try {
            list = ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(s, new TypeReference<List<String>>(){});
        } catch (IOException e) {
            throw new PersistenceException("Failed to convert JSON string to list for stack.", (Throwable)e);
        }
        DefaultThreadContextStack result = new DefaultThreadContextStack(true);
        result.addAll((Collection<? extends String>)list);
        return result;
    }
}

