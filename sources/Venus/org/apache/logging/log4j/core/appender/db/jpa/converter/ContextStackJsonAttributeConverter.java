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
        } catch (IOException iOException) {
            throw new PersistenceException("Failed to convert stack list to JSON string.", (Throwable)iOException);
        }
    }

    public ThreadContext.ContextStack convertToEntityAttribute(String string) {
        List<String> list;
        if (Strings.isEmpty(string)) {
            return null;
        }
        try {
            list = ContextMapJsonAttributeConverter.OBJECT_MAPPER.readValue(string, new TypeReference<List<String>>(this){
                final ContextStackJsonAttributeConverter this$0;
                {
                    this.this$0 = contextStackJsonAttributeConverter;
                }
            });
        } catch (IOException iOException) {
            throw new PersistenceException("Failed to convert JSON string to list for stack.", (Throwable)iOException);
        }
        DefaultThreadContextStack defaultThreadContextStack = new DefaultThreadContextStack(true);
        defaultThreadContextStack.addAll((Collection<? extends String>)list);
        return defaultThreadContextStack;
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((ThreadContext.ContextStack)object);
    }
}

