package org.bouncycastle.jce.provider;

import java.security.*;

class BouncyCastleProviderAction implements PrivilegedAction
{
    final BouncyCastleProvider theBouncyCastleProvider;
    
    BouncyCastleProviderAction(final BouncyCastleProvider par1BouncyCastleProvider) {
        this.theBouncyCastleProvider = par1BouncyCastleProvider;
    }
    
    @Override
    public Object run() {
        BouncyCastleProvider.doSetup(this.theBouncyCastleProvider);
        return null;
    }
}
