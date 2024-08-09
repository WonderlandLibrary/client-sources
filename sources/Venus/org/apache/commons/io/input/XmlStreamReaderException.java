/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;

public class XmlStreamReaderException
extends IOException {
    private static final long serialVersionUID = 1L;
    private final String bomEncoding;
    private final String xmlGuessEncoding;
    private final String xmlEncoding;
    private final String contentTypeMime;
    private final String contentTypeEncoding;

    public XmlStreamReaderException(String string, String string2, String string3, String string4) {
        this(string, null, null, string2, string3, string4);
    }

    public XmlStreamReaderException(String string, String string2, String string3, String string4, String string5, String string6) {
        super(string);
        this.contentTypeMime = string2;
        this.contentTypeEncoding = string3;
        this.bomEncoding = string4;
        this.xmlGuessEncoding = string5;
        this.xmlEncoding = string6;
    }

    public String getBomEncoding() {
        return this.bomEncoding;
    }

    public String getXmlGuessEncoding() {
        return this.xmlGuessEncoding;
    }

    public String getXmlEncoding() {
        return this.xmlEncoding;
    }

    public String getContentTypeMime() {
        return this.contentTypeMime;
    }

    public String getContentTypeEncoding() {
        return this.contentTypeEncoding;
    }
}

