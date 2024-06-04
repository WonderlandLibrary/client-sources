package org.spongycastle.crypto.tls;

import org.spongycastle.util.Arrays;

public class SecurityParameters
{
  int entity = -1;
  int cipherSuite = -1;
  short compressionAlgorithm = 0;
  int prfAlgorithm = -1;
  int verifyDataLength = -1;
  byte[] masterSecret = null;
  byte[] clientRandom = null;
  byte[] serverRandom = null;
  byte[] sessionHash = null;
  byte[] pskIdentity = null;
  byte[] srpIdentity = null;
  

  short maxFragmentLength = -1;
  boolean truncatedHMac = false;
  boolean encryptThenMAC = false;
  boolean extendedMasterSecret = false;
  
  public SecurityParameters() {}
  
  void clear() { if (masterSecret != null)
    {
      Arrays.fill(masterSecret, (byte)0);
      masterSecret = null;
    }
  }
  



  public int getEntity()
  {
    return entity;
  }
  



  public int getCipherSuite()
  {
    return cipherSuite;
  }
  



  public short getCompressionAlgorithm()
  {
    return compressionAlgorithm;
  }
  



  public int getPrfAlgorithm()
  {
    return prfAlgorithm;
  }
  
  public int getVerifyDataLength()
  {
    return verifyDataLength;
  }
  
  public byte[] getMasterSecret()
  {
    return masterSecret;
  }
  
  public byte[] getClientRandom()
  {
    return clientRandom;
  }
  
  public byte[] getServerRandom()
  {
    return serverRandom;
  }
  
  public byte[] getSessionHash()
  {
    return sessionHash;
  }
  
  /**
   * @deprecated
   */
  public byte[] getPskIdentity()
  {
    return pskIdentity;
  }
  
  public byte[] getPSKIdentity()
  {
    return pskIdentity;
  }
  
  public byte[] getSRPIdentity()
  {
    return srpIdentity;
  }
}
