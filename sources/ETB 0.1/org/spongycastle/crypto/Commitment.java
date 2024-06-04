package org.spongycastle.crypto;




public class Commitment
{
  private final byte[] secret;
  


  private final byte[] commitment;
  



  public Commitment(byte[] secret, byte[] commitment)
  {
    this.secret = secret;
    this.commitment = commitment;
  }
  





  public byte[] getSecret()
  {
    return secret;
  }
  





  public byte[] getCommitment()
  {
    return commitment;
  }
}
