package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.*;

public class KeyParameter implements CipherParameters
{
    private byte[] key;
    
    public KeyParameter(final byte[] par1ArrayOfByte) {
        this(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }
    
    public KeyParameter(final byte[] par1ArrayOfByte, final int par2, final int par3) {
        System.arraycopy(par1ArrayOfByte, par2, this.key = new byte[par3], 0, par3);
    }
    
    public byte[] getKey() {
        return this.key;
    }
}
