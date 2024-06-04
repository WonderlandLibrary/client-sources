package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.CipherParameters;

public class NHAgreement {
  private NHPrivateKeyParameters privKey;
  
  public NHAgreement() {}
  
  public void init(CipherParameters param) {
    privKey = ((NHPrivateKeyParameters)param);
  }
  
  public byte[] calculateAgreement(CipherParameters otherPublicKey)
  {
    NHPublicKeyParameters pubKey = (NHPublicKeyParameters)otherPublicKey;
    
    byte[] sharedValue = new byte[32];
    
    NewHope.sharedA(sharedValue, privKey.secData, pubData);
    
    return sharedValue;
  }
}
