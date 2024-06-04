package org.spongycastle.jce.interfaces;

import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract interface GOST3410Params
{
  public abstract String getPublicKeyParamSetOID();
  
  public abstract String getDigestParamSetOID();
  
  public abstract String getEncryptionParamSetOID();
  
  public abstract GOST3410PublicKeyParameterSetSpec getPublicKeyParameters();
}
