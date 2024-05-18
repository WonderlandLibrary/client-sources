package org.bouncycastle.jce.provider;

import java.security.*;
import org.bouncycastle.jcajce.provider.config.*;

class BouncyCastleProviderConfiguration implements ProviderConfiguration
{
    private static Permission BC_EC_LOCAL_PERMISSION;
    private static Permission BC_EC_PERMISSION;
    private static Permission BC_DH_LOCAL_PERMISSION;
    private static Permission BC_DH_PERMISSION;
    private ThreadLocal ecThreadSpec;
    private ThreadLocal dhThreadSpec;
    
    static {
        BouncyCastleProviderConfiguration.BC_EC_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalEcImplicitlyCa");
        BouncyCastleProviderConfiguration.BC_EC_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "ecImplicitlyCa");
        BouncyCastleProviderConfiguration.BC_DH_LOCAL_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalDhDefaultParams");
        BouncyCastleProviderConfiguration.BC_DH_PERMISSION = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "DhDefaultParams");
    }
    
    BouncyCastleProviderConfiguration() {
        this.ecThreadSpec = new ThreadLocal();
        this.dhThreadSpec = new ThreadLocal();
    }
}
