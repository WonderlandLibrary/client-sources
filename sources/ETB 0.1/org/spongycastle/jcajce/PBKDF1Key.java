package org.spongycastle.jcajce;

import org.spongycastle.crypto.CharToByteConverter;










public class PBKDF1Key
  implements PBKDFKey
{
  private final char[] password;
  private final CharToByteConverter converter;
  
  public PBKDF1Key(char[] password, CharToByteConverter converter)
  {
    this.password = new char[password.length];
    this.converter = converter;
    
    System.arraycopy(password, 0, this.password, 0, password.length);
  }
  





  public char[] getPassword()
  {
    return password;
  }
  





  public String getAlgorithm()
  {
    return "PBKDF1";
  }
  





  public String getFormat()
  {
    return converter.getType();
  }
  





  public byte[] getEncoded()
  {
    return converter.convert(password);
  }
}
