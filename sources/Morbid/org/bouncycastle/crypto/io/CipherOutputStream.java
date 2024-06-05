package org.bouncycastle.crypto.io;

import org.bouncycastle.crypto.*;
import java.io.*;

public class CipherOutputStream extends FilterOutputStream
{
    private BufferedBlockCipher theBufferedBlockCipher;
    private StreamCipher theStreamCipher;
    private byte[] oneByte;
    private byte[] buf;
    
    public CipherOutputStream(final OutputStream par1OutputStream, final BufferedBlockCipher par2BufferedBlockCipher) {
        super(par1OutputStream);
        this.oneByte = new byte[1];
        this.theBufferedBlockCipher = par2BufferedBlockCipher;
        this.buf = new byte[par2BufferedBlockCipher.getBlockSize()];
    }
    
    @Override
    public void write(final int par1) throws IOException {
        this.oneByte[0] = (byte)par1;
        if (this.theBufferedBlockCipher != null) {
            final int var2 = this.theBufferedBlockCipher.processByte(this.oneByte, 0, 1, this.buf, 0);
            if (var2 != 0) {
                this.out.write(this.buf, 0, var2);
            }
        }
        else {
            this.out.write(this.theStreamCipher.returnByte((byte)par1));
        }
    }
    
    @Override
    public void write(final byte[] par1) throws IOException {
        this.write(par1, 0, par1.length);
    }
    
    @Override
    public void write(final byte[] par1, final int par2, final int par3) throws IOException {
        if (this.theBufferedBlockCipher != null) {
            final byte[] var4 = new byte[this.theBufferedBlockCipher.getOutputSize(par3)];
            final int var5 = this.theBufferedBlockCipher.processByte(par1, par2, par3, var4, 0);
            if (var5 != 0) {
                this.out.write(var4, 0, var5);
            }
        }
        else {
            final byte[] var4 = new byte[par3];
            this.theStreamCipher.processBytes(par1, par2, par3, var4, 0);
            this.out.write(var4, 0, par3);
        }
    }
    
    @Override
    public void flush() throws IOException {
        super.flush();
    }
    
    @Override
    public void close() throws IOException {
        try {
            if (this.theBufferedBlockCipher != null) {
                final byte[] var1 = new byte[this.theBufferedBlockCipher.getOutputSize(0)];
                final int var2 = this.theBufferedBlockCipher.doFinal(var1, 0);
                if (var2 != 0) {
                    this.out.write(var1, 0, var2);
                }
            }
        }
        catch (Exception var3) {
            throw new IOException("Error closing stream: " + var3.toString());
        }
        this.flush();
        super.close();
    }
}
