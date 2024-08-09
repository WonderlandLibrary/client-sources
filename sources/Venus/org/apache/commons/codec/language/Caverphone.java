/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.language.Caverphone2;

@Deprecated
public class Caverphone
implements StringEncoder {
    private final Caverphone2 encoder = new Caverphone2();

    public String caverphone(String string) {
        return this.encoder.encode(string);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.caverphone((String)object);
    }

    @Override
    public String encode(String string) {
        return this.caverphone(string);
    }

    public boolean isCaverphoneEqual(String string, String string2) {
        return this.caverphone(string).equals(this.caverphone(string2));
    }
}

