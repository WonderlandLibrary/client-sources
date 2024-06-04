package org.spongycastle.crypto;

import org.spongycastle.util.Strings;




















public abstract class PBEParametersGenerator
{
  protected byte[] password;
  protected byte[] salt;
  protected int iterationCount;
  
  protected PBEParametersGenerator() {}
  
  public void init(byte[] password, byte[] salt, int iterationCount)
  {
    this.password = password;
    this.salt = salt;
    this.iterationCount = iterationCount;
  }
  





  public byte[] getPassword()
  {
    return password;
  }
  





  public byte[] getSalt()
  {
    return salt;
  }
  





  public int getIterationCount()
  {
    return iterationCount;
  }
  








  public abstract CipherParameters generateDerivedParameters(int paramInt);
  







  public abstract CipherParameters generateDerivedParameters(int paramInt1, int paramInt2);
  







  public abstract CipherParameters generateDerivedMacParameters(int paramInt);
  







  public static byte[] PKCS5PasswordToBytes(char[] password)
  {
    if (password != null)
    {
      byte[] bytes = new byte[password.length];
      
      for (int i = 0; i != bytes.length; i++)
      {
        bytes[i] = ((byte)password[i]);
      }
      
      return bytes;
    }
    

    return new byte[0];
  }
  









  public static byte[] PKCS5PasswordToUTF8Bytes(char[] password)
  {
    if (password != null)
    {
      return Strings.toUTF8ByteArray(password);
    }
    

    return new byte[0];
  }
  









  public static byte[] PKCS12PasswordToBytes(char[] password)
  {
    if ((password != null) && (password.length > 0))
    {

      byte[] bytes = new byte[(password.length + 1) * 2];
      
      for (int i = 0; i != password.length; i++)
      {
        bytes[(i * 2)] = ((byte)(password[i] >>> '\b'));
        bytes[(i * 2 + 1)] = ((byte)password[i]);
      }
      
      return bytes;
    }
    

    return new byte[0];
  }
}
