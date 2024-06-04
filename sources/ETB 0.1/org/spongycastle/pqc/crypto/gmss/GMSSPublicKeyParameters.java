package org.spongycastle.pqc.crypto.gmss;






public class GMSSPublicKeyParameters
  extends GMSSKeyParameters
{
  private byte[] gmssPublicKey;
  





  public GMSSPublicKeyParameters(byte[] key, GMSSParameters gmssParameterSet)
  {
    super(false, gmssParameterSet);
    gmssPublicKey = key;
  }
  





  public byte[] getPublicKey()
  {
    return gmssPublicKey;
  }
}
