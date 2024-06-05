package net.minecraft.src;

import java.net.*;
import java.io.*;

class MusInputStream extends InputStream
{
    private int hash;
    private InputStream inputStream;
    byte[] buffer;
    final CodecMus codec;
    
    public MusInputStream(final CodecMus par1CodecMus, final URL par2URL, final InputStream par3InputStream) {
        this.codec = par1CodecMus;
        this.buffer = new byte[1];
        this.inputStream = par3InputStream;
        String var4 = par2URL.getPath();
        var4 = var4.substring(var4.lastIndexOf("/") + 1);
        this.hash = var4.hashCode();
    }
    
    @Override
    public int read() throws IOException {
        final int var1 = this.read(this.buffer, 0, 1);
        return (var1 < 0) ? var1 : this.buffer[0];
    }
    
    @Override
    public int read(final byte[] par1ArrayOfByte, final int par2, int par3) throws IOException {
        par3 = this.inputStream.read(par1ArrayOfByte, par2, par3);
        for (int var4 = 0; var4 < par3; ++var4) {
            final int n = par2 + var4;
            final byte b = (byte)(par1ArrayOfByte[par2 + var4] ^ this.hash >> 8);
            par1ArrayOfByte[n] = b;
            final byte var5 = b;
            this.hash = this.hash * 498729871 + 85731 * var5;
        }
        return par3;
    }
}
