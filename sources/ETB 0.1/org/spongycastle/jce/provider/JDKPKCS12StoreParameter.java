package org.spongycastle.jce.provider;

import java.io.OutputStream;
import java.security.KeyStore.ProtectionParameter;

/**
 * @deprecated
 */
public class JDKPKCS12StoreParameter implements java.security.KeyStore.LoadStoreParameter
{
  private OutputStream outputStream;
  private KeyStore.ProtectionParameter protectionParameter;
  private boolean useDEREncoding;
  
  public JDKPKCS12StoreParameter() {}
  
  public OutputStream getOutputStream()
  {
    return outputStream;
  }
  
  public KeyStore.ProtectionParameter getProtectionParameter()
  {
    return protectionParameter;
  }
  
  public boolean isUseDEREncoding()
  {
    return useDEREncoding;
  }
  
  public void setOutputStream(OutputStream outputStream)
  {
    this.outputStream = outputStream;
  }
  
  public void setPassword(char[] password)
  {
    protectionParameter = new java.security.KeyStore.PasswordProtection(password);
  }
  
  public void setProtectionParameter(KeyStore.ProtectionParameter protectionParameter)
  {
    this.protectionParameter = protectionParameter;
  }
  
  public void setUseDEREncoding(boolean useDEREncoding)
  {
    this.useDEREncoding = useDEREncoding;
  }
}
