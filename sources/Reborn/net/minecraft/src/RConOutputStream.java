package net.minecraft.src;

import java.io.*;

public class RConOutputStream
{
    private ByteArrayOutputStream byteArrayOutput;
    private DataOutputStream output;
    
    public RConOutputStream(final int par1) {
        this.byteArrayOutput = new ByteArrayOutputStream(par1);
        this.output = new DataOutputStream(this.byteArrayOutput);
    }
    
    public void writeByteArray(final byte[] par1ArrayOfByte) throws IOException {
        this.output.write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }
    
    public void writeString(final String par1Str) throws IOException {
        this.output.writeBytes(par1Str);
        this.output.write(0);
    }
    
    public void writeInt(final int par1) throws IOException {
        this.output.write(par1);
    }
    
    public void writeShort(final short par1) throws IOException {
        this.output.writeShort(Short.reverseBytes(par1));
    }
    
    public byte[] toByteArray() {
        return this.byteArrayOutput.toByteArray();
    }
    
    public void reset() {
        this.byteArrayOutput.reset();
    }
}
