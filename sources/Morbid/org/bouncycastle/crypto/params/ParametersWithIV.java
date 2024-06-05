package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.*;

public class ParametersWithIV implements CipherParameters
{
    private byte[] iv;
    private CipherParameters parameters;
    
    public ParametersWithIV(final CipherParameters par1CipherParameters, final byte[] par2ArrayOfByte, final int par3, final int par4) {
        this.iv = new byte[par4];
        this.parameters = par1CipherParameters;
        System.arraycopy(par2ArrayOfByte, par3, this.iv, 0, par4);
    }
    
    public byte[] getIV() {
        return this.iv;
    }
    
    public CipherParameters getParameters() {
        return this.parameters;
    }
}
