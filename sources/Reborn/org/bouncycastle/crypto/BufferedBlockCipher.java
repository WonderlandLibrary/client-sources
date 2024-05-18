package org.bouncycastle.crypto;

public class BufferedBlockCipher
{
    protected byte[] buf;
    protected int bufOff;
    protected boolean forEncryption;
    protected BlockCipher cipher;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;
    
    protected BufferedBlockCipher() {
    }
    
    public BufferedBlockCipher(final BlockCipher par1BlockCipher) {
        this.cipher = par1BlockCipher;
        this.buf = new byte[par1BlockCipher.getBlockSize()];
        this.bufOff = 0;
        final String var2 = par1BlockCipher.getAlgorithmName();
        final int var3 = var2.indexOf(47) + 1;
        this.pgpCFB = (var3 > 0 && var2.startsWith("PGP", var3));
        if (this.pgpCFB) {
            this.partialBlockOkay = true;
        }
        else {
            this.partialBlockOkay = (var3 > 0 && (var2.startsWith("CFB", var3) || var2.startsWith("OFB", var3) || var2.startsWith("OpenPGP", var3) || var2.startsWith("SIC", var3) || var2.startsWith("GCTR", var3)));
        }
    }
    
    public void init(final boolean par1, final CipherParameters par2CipherParameters) throws IllegalArgumentException {
        this.forEncryption = par1;
        this.reset();
        this.cipher.init(par1, par2CipherParameters);
    }
    
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }
    
    public int getUpdateOutputSize(final int par1) {
        final int var2 = par1 + this.bufOff;
        int var3;
        if (this.pgpCFB) {
            var3 = var2 % this.buf.length - (this.cipher.getBlockSize() + 2);
        }
        else {
            var3 = var2 % this.buf.length;
        }
        return var2 - var3;
    }
    
    public int getOutputSize(final int par1) {
        return par1 + this.bufOff;
    }
    
    public int processByte(final byte[] par1ArrayOfByte, int par2, int par3, final byte[] par4ArrayOfByte, final int par5) throws DataLengthException, IllegalStateException {
        if (par3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        final int var6 = this.getBlockSize();
        final int var7 = this.getUpdateOutputSize(par3);
        if (var7 > 0 && par5 + var7 > par4ArrayOfByte.length) {
            throw new DataLengthException("output buffer too short");
        }
        int var8 = 0;
        final int var9 = this.buf.length - this.bufOff;
        if (par3 > var9) {
            System.arraycopy(par1ArrayOfByte, par2, this.buf, this.bufOff, var9);
            var8 += this.cipher.processBlock(this.buf, 0, par4ArrayOfByte, par5);
            this.bufOff = 0;
            for (par3 -= var9, par2 += var9; par3 > this.buf.length; par3 -= var6, par2 += var6) {
                var8 += this.cipher.processBlock(par1ArrayOfByte, par2, par4ArrayOfByte, par5 + var8);
            }
        }
        System.arraycopy(par1ArrayOfByte, par2, this.buf, this.bufOff, par3);
        this.bufOff += par3;
        if (this.bufOff == this.buf.length) {
            var8 += this.cipher.processBlock(this.buf, 0, par4ArrayOfByte, par5 + var8);
            this.bufOff = 0;
        }
        return var8;
    }
    
    public int doFinal(final byte[] par1ArrayOfByte, final int par2) throws DataLengthException, IllegalStateException {
        int var4;
        try {
            int var3 = 0;
            if (par2 + this.bufOff > par1ArrayOfByte.length) {
                throw new DataLengthException("output buffer too short for doFinal()");
            }
            if (this.bufOff != 0) {
                if (!this.partialBlockOkay) {
                    throw new DataLengthException("data not block size aligned");
                }
                this.cipher.processBlock(this.buf, 0, this.buf, 0);
                var3 = this.bufOff;
                this.bufOff = 0;
                System.arraycopy(this.buf, 0, par1ArrayOfByte, par2, var3);
            }
            var4 = var3;
        }
        finally {
            this.reset();
        }
        this.reset();
        return var4;
    }
    
    public void reset() {
        for (int var1 = 0; var1 < this.buf.length; ++var1) {
            this.buf[var1] = 0;
        }
        this.bufOff = 0;
        this.cipher.reset();
    }
}
