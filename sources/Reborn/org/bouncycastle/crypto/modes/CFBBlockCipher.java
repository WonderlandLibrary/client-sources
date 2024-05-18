package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.*;

public class CFBBlockCipher implements BlockCipher
{
    private byte[] IV;
    private byte[] cfbV;
    private byte[] cfbOutV;
    private int blockSize;
    private BlockCipher cipher;
    private boolean encrypting;
    
    public CFBBlockCipher(final BlockCipher par1BlockCipher, final int par2) {
        this.cipher = null;
        this.cipher = par1BlockCipher;
        this.blockSize = par2 / 8;
        this.IV = new byte[par1BlockCipher.getBlockSize()];
        this.cfbV = new byte[par1BlockCipher.getBlockSize()];
        this.cfbOutV = new byte[par1BlockCipher.getBlockSize()];
    }
    
    @Override
    public void init(final boolean par1, final CipherParameters par2CipherParameters) throws IllegalArgumentException {
        this.encrypting = par1;
        if (par2CipherParameters instanceof ParametersWithIV) {
            final ParametersWithIV var3 = (ParametersWithIV)par2CipherParameters;
            final byte[] var4 = var3.getIV();
            if (var4.length < this.IV.length) {
                System.arraycopy(var4, 0, this.IV, this.IV.length - var4.length, var4.length);
                for (int var5 = 0; var5 < this.IV.length - var4.length; ++var5) {
                    this.IV[var5] = 0;
                }
            }
            else {
                System.arraycopy(var4, 0, this.IV, 0, this.IV.length);
            }
            this.reset();
            if (var3.getParameters() != null) {
                this.cipher.init(true, var3.getParameters());
            }
        }
        else {
            this.reset();
            this.cipher.init(true, par2CipherParameters);
        }
    }
    
    @Override
    public String getAlgorithmName() {
        return String.valueOf(this.cipher.getAlgorithmName()) + "/CFB" + this.blockSize * 8;
    }
    
    @Override
    public int getBlockSize() {
        return this.blockSize;
    }
    
    @Override
    public int processBlock(final byte[] par1ArrayOfByte, final int par2, final byte[] par3ArrayOfByte, final int par4) throws DataLengthException, IllegalStateException {
        return this.encrypting ? this.encryptBlock(par1ArrayOfByte, par2, par3ArrayOfByte, par4) : this.decryptBlock(par1ArrayOfByte, par2, par3ArrayOfByte, par4);
    }
    
    public int encryptBlock(final byte[] par1ArrayOfByte, final int par2, final byte[] par3ArrayOfByte, final int par4) throws DataLengthException, IllegalStateException {
        if (par2 + this.blockSize > par1ArrayOfByte.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (par4 + this.blockSize > par3ArrayOfByte.length) {
            throw new DataLengthException("output buffer too short");
        }
        this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        for (int var5 = 0; var5 < this.blockSize; ++var5) {
            par3ArrayOfByte[par4 + var5] = (byte)(this.cfbOutV[var5] ^ par1ArrayOfByte[par2 + var5]);
        }
        System.arraycopy(this.cfbV, this.blockSize, this.cfbV, 0, this.cfbV.length - this.blockSize);
        System.arraycopy(par3ArrayOfByte, par4, this.cfbV, this.cfbV.length - this.blockSize, this.blockSize);
        return this.blockSize;
    }
    
    public int decryptBlock(final byte[] par1ArrayOfByte, final int par2, final byte[] par3ArrayOfByte, final int par4) throws DataLengthException, IllegalStateException {
        if (par2 + this.blockSize > par1ArrayOfByte.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (par4 + this.blockSize > par3ArrayOfByte.length) {
            throw new DataLengthException("output buffer too short");
        }
        this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        System.arraycopy(this.cfbV, this.blockSize, this.cfbV, 0, this.cfbV.length - this.blockSize);
        System.arraycopy(par1ArrayOfByte, par2, this.cfbV, this.cfbV.length - this.blockSize, this.blockSize);
        for (int var5 = 0; var5 < this.blockSize; ++var5) {
            par3ArrayOfByte[par4 + var5] = (byte)(this.cfbOutV[var5] ^ par1ArrayOfByte[par2 + var5]);
        }
        return this.blockSize;
    }
    
    @Override
    public void reset() {
        System.arraycopy(this.IV, 0, this.cfbV, 0, this.IV.length);
        this.cipher.reset();
    }
}
