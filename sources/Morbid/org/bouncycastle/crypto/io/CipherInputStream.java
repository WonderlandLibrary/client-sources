package org.bouncycastle.crypto.io;

import org.bouncycastle.crypto.*;
import java.io.*;

public class CipherInputStream extends FilterInputStream
{
    private BufferedBlockCipher theBufferedBlockCipher;
    private StreamCipher theStreamCipher;
    private byte[] buf;
    private byte[] inBuf;
    private int bufOff;
    private int maxBuf;
    private boolean finalized;
    
    public CipherInputStream(final InputStream par1InputStream, final BufferedBlockCipher par2BufferedBlockCipher) {
        super(par1InputStream);
        this.theBufferedBlockCipher = par2BufferedBlockCipher;
        this.buf = new byte[par2BufferedBlockCipher.getOutputSize(2048)];
        this.inBuf = new byte[2048];
    }
    
    private int nextChunk() throws IOException {
        int var1 = super.available();
        if (var1 <= 0) {
            var1 = 1;
        }
        if (var1 > this.inBuf.length) {
            var1 = super.read(this.inBuf, 0, this.inBuf.length);
        }
        else {
            var1 = super.read(this.inBuf, 0, var1);
        }
        if (var1 < 0) {
            if (this.finalized) {
                return -1;
            }
            try {
                if (this.theBufferedBlockCipher != null) {
                    this.maxBuf = this.theBufferedBlockCipher.doFinal(this.buf, 0);
                }
                else {
                    this.maxBuf = 0;
                }
            }
            catch (Exception var2) {
                throw new IOException("error processing stream: " + var2.toString());
            }
            this.bufOff = 0;
            this.finalized = true;
            if (this.bufOff == this.maxBuf) {
                return -1;
            }
        }
        else {
            this.bufOff = 0;
            try {
                if (this.theBufferedBlockCipher != null) {
                    this.maxBuf = this.theBufferedBlockCipher.processByte(this.inBuf, 0, var1, this.buf, 0);
                }
                else {
                    this.theStreamCipher.processBytes(this.inBuf, 0, var1, this.buf, 0);
                    this.maxBuf = var1;
                }
            }
            catch (Exception var3) {
                throw new IOException("error processing stream: " + var3.toString());
            }
            if (this.maxBuf == 0) {
                return this.nextChunk();
            }
        }
        return this.maxBuf;
    }
    
    @Override
    public int read() throws IOException {
        return (this.bufOff == this.maxBuf && this.nextChunk() < 0) ? -1 : (this.buf[this.bufOff++] & 0xFF);
    }
    
    @Override
    public int read(final byte[] par1ArrayOfByte) throws IOException {
        return this.read(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }
    
    @Override
    public int read(final byte[] par1ArrayOfByte, final int par2, final int par3) throws IOException {
        if (this.bufOff == this.maxBuf && this.nextChunk() < 0) {
            return -1;
        }
        final int var4 = this.maxBuf - this.bufOff;
        if (par3 > var4) {
            System.arraycopy(this.buf, this.bufOff, par1ArrayOfByte, par2, var4);
            this.bufOff = this.maxBuf;
            return var4;
        }
        System.arraycopy(this.buf, this.bufOff, par1ArrayOfByte, par2, par3);
        this.bufOff += par3;
        return par3;
    }
    
    @Override
    public long skip(final long par1) throws IOException {
        if (par1 <= 0L) {
            return 0L;
        }
        final int var3 = this.maxBuf - this.bufOff;
        if (par1 > var3) {
            this.bufOff = this.maxBuf;
            return var3;
        }
        this.bufOff += (int)par1;
        return (int)par1;
    }
    
    @Override
    public int available() throws IOException {
        return this.maxBuf - this.bufOff;
    }
    
    @Override
    public void close() throws IOException {
        super.close();
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
}
