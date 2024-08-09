/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringType
extends Type<String> {
    private static final int maxJavaCharUtf8Length = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
    private final int maxLength;

    public StringType() {
        this(Short.MAX_VALUE);
    }

    public StringType(int n) {
        super(String.class);
        this.maxLength = n;
    }

    @Override
    public String read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        Preconditions.checkArgument(n <= this.maxLength * maxJavaCharUtf8Length, "Cannot receive string longer than Short.MAX_VALUE * " + maxJavaCharUtf8Length + " bytes (got %s bytes)", new Object[]{n});
        String string = byteBuf.toString(byteBuf.readerIndex(), n, StandardCharsets.UTF_8);
        byteBuf.skipBytes(n);
        Preconditions.checkArgument(string.length() <= this.maxLength, "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", new Object[]{string.length()});
        return string;
    }

    @Override
    public void write(ByteBuf byteBuf, String string) throws Exception {
        Preconditions.checkArgument(string.length() <= this.maxLength, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", new Object[]{string.length()});
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        Type.VAR_INT.writePrimitive(byteBuf, byArray.length);
        byteBuf.writeBytes(byArray);
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (String)object);
    }

    public static final class OptionalStringType
    extends OptionalType<String> {
        public OptionalStringType() {
            super(Type.STRING);
        }
    }
}

