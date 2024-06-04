package org.spongycastle.crypto.tls;

import org.spongycastle.util.Arrays;

public final class SessionParameters
{
  private int cipherSuite;
  private short compressionAlgorithm;
  private byte[] masterSecret;
  private Certificate peerCertificate;
  
  public static final class Builder
  {
    private int cipherSuite = -1;
    private short compressionAlgorithm = -1;
    private byte[] masterSecret = null;
    private Certificate peerCertificate = null;
    private byte[] pskIdentity = null;
    private byte[] srpIdentity = null;
    private byte[] encodedServerExtensions = null;
    

    public Builder() {}
    

    public SessionParameters build()
    {
      validate(cipherSuite >= 0, "cipherSuite");
      validate(compressionAlgorithm >= 0, "compressionAlgorithm");
      validate(masterSecret != null, "masterSecret");
      return new SessionParameters(cipherSuite, compressionAlgorithm, masterSecret, peerCertificate, pskIdentity, srpIdentity, encodedServerExtensions, null);
    }
    

    public Builder setCipherSuite(int cipherSuite)
    {
      this.cipherSuite = cipherSuite;
      return this;
    }
    
    public Builder setCompressionAlgorithm(short compressionAlgorithm)
    {
      this.compressionAlgorithm = compressionAlgorithm;
      return this;
    }
    
    public Builder setMasterSecret(byte[] masterSecret)
    {
      this.masterSecret = masterSecret;
      return this;
    }
    
    public Builder setPeerCertificate(Certificate peerCertificate)
    {
      this.peerCertificate = peerCertificate;
      return this;
    }
    
    /**
     * @deprecated
     */
    public Builder setPskIdentity(byte[] pskIdentity)
    {
      this.pskIdentity = pskIdentity;
      return this;
    }
    
    public Builder setPSKIdentity(byte[] pskIdentity)
    {
      this.pskIdentity = pskIdentity;
      return this;
    }
    
    public Builder setSRPIdentity(byte[] srpIdentity)
    {
      this.srpIdentity = srpIdentity;
      return this;
    }
    
    public Builder setServerExtensions(java.util.Hashtable serverExtensions) throws java.io.IOException
    {
      if (serverExtensions == null)
      {
        encodedServerExtensions = null;
      }
      else
      {
        java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
        TlsProtocol.writeExtensions(buf, serverExtensions);
        encodedServerExtensions = buf.toByteArray();
      }
      return this;
    }
    
    private void validate(boolean condition, String parameter)
    {
      if (!condition)
      {
        throw new IllegalStateException("Required session parameter '" + parameter + "' not configured");
      }
    }
  }
  




  private byte[] pskIdentity = null;
  private byte[] srpIdentity = null;
  
  private byte[] encodedServerExtensions;
  
  private SessionParameters(int cipherSuite, short compressionAlgorithm, byte[] masterSecret, Certificate peerCertificate, byte[] pskIdentity, byte[] srpIdentity, byte[] encodedServerExtensions)
  {
    this.cipherSuite = cipherSuite;
    this.compressionAlgorithm = compressionAlgorithm;
    this.masterSecret = Arrays.clone(masterSecret);
    this.peerCertificate = peerCertificate;
    this.pskIdentity = Arrays.clone(pskIdentity);
    this.srpIdentity = Arrays.clone(srpIdentity);
    this.encodedServerExtensions = encodedServerExtensions;
  }
  
  public void clear()
  {
    if (masterSecret != null)
    {
      Arrays.fill(masterSecret, (byte)0);
    }
  }
  
  public SessionParameters copy()
  {
    return new SessionParameters(cipherSuite, compressionAlgorithm, masterSecret, peerCertificate, pskIdentity, srpIdentity, encodedServerExtensions);
  }
  

  public int getCipherSuite()
  {
    return cipherSuite;
  }
  
  public short getCompressionAlgorithm()
  {
    return compressionAlgorithm;
  }
  
  public byte[] getMasterSecret()
  {
    return masterSecret;
  }
  
  public Certificate getPeerCertificate()
  {
    return peerCertificate;
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
  
  public java.util.Hashtable readServerExtensions() throws java.io.IOException
  {
    if (encodedServerExtensions == null)
    {
      return null;
    }
    
    java.io.ByteArrayInputStream buf = new java.io.ByteArrayInputStream(encodedServerExtensions);
    return TlsProtocol.readExtensions(buf);
  }
}
