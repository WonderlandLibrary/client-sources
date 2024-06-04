package org.spongycastle.crypto.engines;

public class VMPCKSA3Engine extends VMPCEngine {
  public VMPCKSA3Engine() {}
  
  public String getAlgorithmName() {
    return "VMPC-KSA3";
  }
  
  protected void initKey(byte[] keyBytes, byte[] ivBytes)
  {
    s = 0;
    P = new byte['Ā'];
    for (int i = 0; i < 256; i++)
    {
      P[i] = ((byte)i);
    }
    
    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + keyBytes[(m % keyBytes.length)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    
    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + ivBytes[(m % ivBytes.length)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    
    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + keyBytes[(m % keyBytes.length)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    
    n = 0;
  }
}
