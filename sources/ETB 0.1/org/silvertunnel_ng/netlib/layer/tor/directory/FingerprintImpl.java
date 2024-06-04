package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.Serializable;
import java.util.Arrays;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.util.Parsing;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;





































public final class FingerprintImpl
  implements Fingerprint, Cloneable, Serializable
{
  private static final long serialVersionUID = -1621113144294310736L;
  private byte[] bytes;
  private String hexCache;
  
  public FingerprintImpl(String identityKeyBase64)
  {
    String b64fp = identityKeyBase64;
    while (b64fp.length() % 4 != 0)
    {
      b64fp = b64fp + "=";
    }
    setIdentityKey(DatatypeConverter.parseBase64Binary(b64fp));
  }
  




  public FingerprintImpl(byte[] identityKey)
  {
    setIdentityKey(identityKey);
  }
  

  private void setIdentityKey(byte[] identityKey)
  {
    if (identityKey == null)
    {
      throw new NullPointerException();
    }
    if (identityKey.length < 4)
    {
      throw new IllegalArgumentException("invalid array length=" + identityKey.length);
    }
    


    bytes = identityKey;
  }
  




  public String getHex()
  {
    if (hexCache == null)
    {
      hexCache = Parsing.renderFingerprint(bytes, false);
    }
    return hexCache;
  }
  




  public String getHexWithSpaces()
  {
    return Parsing.renderFingerprint(bytes, true);
  }
  




  public byte[] getBytes()
  {
    byte[] result = new byte[bytes.length];
    System.arraycopy(bytes, 0, result, 0, bytes.length);
    return result;
  }
  

  public String toString()
  {
    return "fingerprintHexWithSpaces=" + getHexWithSpaces();
  }
  

  public int hashCode()
  {
    return Arrays.hashCode(bytes);
  }
  

  public boolean equals(Object obj)
  {
    if (!(obj instanceof FingerprintImpl))
    {
      return false;
    }
    FingerprintImpl o = (FingerprintImpl)obj;
    return Arrays.equals(bytes, bytes);
  }
  








  public int compareTo(Fingerprint other)
  {
    return getHex().compareTo(other.getHex());
  }
  







  public Fingerprint cloneReliable()
    throws RuntimeException
  {
    try
    {
      return (Fingerprint)clone();
    }
    catch (CloneNotSupportedException e)
    {
      throw new RuntimeException(e);
    }
  }
}
