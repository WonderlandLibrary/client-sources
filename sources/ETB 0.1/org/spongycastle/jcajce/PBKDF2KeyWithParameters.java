package org.spongycastle.jcajce;

import javax.crypto.interfaces.PBEKey;
import org.spongycastle.crypto.CharToByteConverter;
import org.spongycastle.util.Arrays;













public class PBKDF2KeyWithParameters
  extends PBKDF2Key
  implements PBEKey
{
  private final byte[] salt;
  private final int iterationCount;
  
  public PBKDF2KeyWithParameters(char[] password, CharToByteConverter converter, byte[] salt, int iterationCount)
  {
    super(password, converter);
    
    this.salt = Arrays.clone(salt);
    this.iterationCount = iterationCount;
  }
  





  public byte[] getSalt()
  {
    return salt;
  }
  





  public int getIterationCount()
  {
    return iterationCount;
  }
}
