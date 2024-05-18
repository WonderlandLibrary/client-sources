package org.bouncycastle.crypto;

import java.security.*;

public class CipherKeyGenerator
{
    protected SecureRandom random;
    protected int strength;
    
    public void init(final KeyGenerationParameters par1) {
        this.random = par1.getRandom();
        this.strength = (par1.getStrength() + 7) / 8;
    }
    
    public byte[] generateKey() {
        final byte[] var1 = new byte[this.strength];
        this.random.nextBytes(var1);
        return var1;
    }
}
