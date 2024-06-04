package org.spongycastle.jcajce;

import org.spongycastle.crypto.CharToByteConverter;
import org.spongycastle.util.Arrays;









public class PBKDF2Key
  implements PBKDFKey
{
  private final char[] password;
  private final CharToByteConverter converter;
  
  public PBKDF2Key(char[] password, CharToByteConverter converter)
  {
    this.password = Arrays.clone(password);
    this.converter = converter;
  }
  





  public char[] getPassword()
  {
    return password;
  }
  





  public String getAlgorithm()
  {
    return "PBKDF2";
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
