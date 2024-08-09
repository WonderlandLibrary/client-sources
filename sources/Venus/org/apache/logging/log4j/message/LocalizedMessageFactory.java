/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.util.ResourceBundle;
import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.LocalizedMessage;
import org.apache.logging.log4j.message.Message;

public class LocalizedMessageFactory
extends AbstractMessageFactory {
    private static final long serialVersionUID = -1996295808703146741L;
    private final transient ResourceBundle resourceBundle;
    private final String baseName;

    public LocalizedMessageFactory(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.baseName = null;
    }

    public LocalizedMessageFactory(String string) {
        this.resourceBundle = null;
        this.baseName = string;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    @Override
    public Message newMessage(String string) {
        if (this.resourceBundle == null) {
            return new LocalizedMessage(this.baseName, (Object)string);
        }
        return new LocalizedMessage(this.resourceBundle, string);
    }

    @Override
    public Message newMessage(String string, Object ... objectArray) {
        if (this.resourceBundle == null) {
            return new LocalizedMessage(this.baseName, string, objectArray);
        }
        return new LocalizedMessage(this.resourceBundle, string, objectArray);
    }
}

