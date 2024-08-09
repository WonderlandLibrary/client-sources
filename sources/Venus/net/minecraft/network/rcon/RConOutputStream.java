/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RConOutputStream {
    private final ByteArrayOutputStream byteArrayOutput;
    private final DataOutputStream output;

    public RConOutputStream(int n) {
        this.byteArrayOutput = new ByteArrayOutputStream(n);
        this.output = new DataOutputStream(this.byteArrayOutput);
    }

    public void writeByteArray(byte[] byArray) throws IOException {
        this.output.write(byArray, 0, byArray.length);
    }

    public void writeString(String string) throws IOException {
        this.output.writeBytes(string);
        this.output.write(0);
    }

    public void writeInt(int n) throws IOException {
        this.output.write(n);
    }

    public void writeShort(short s) throws IOException {
        this.output.writeShort(Short.reverseBytes(s));
    }

    public byte[] toByteArray() {
        return this.byteArrayOutput.toByteArray();
    }

    public void reset() {
        this.byteArrayOutput.reset();
    }
}

