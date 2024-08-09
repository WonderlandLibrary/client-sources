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
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class MessageAttributeConverter
implements AttributeConverter<Message, String> {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    public String convertToDatabaseColumn(Message message) {
        if (message == null) {
            return null;
        }
        return message.getFormattedMessage();
    }

    public Message convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        return LOGGER.getMessageFactory().newMessage(string);
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((Message)object);
    }
}

