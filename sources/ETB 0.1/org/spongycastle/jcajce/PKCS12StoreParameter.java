package org.spongycastle.jcajce;

import java.io.OutputStream;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;







public class PKCS12StoreParameter
  implements KeyStore.LoadStoreParameter
{
  private final OutputStream out;
  private final KeyStore.ProtectionParameter protectionParameter;
  private final boolean forDEREncoding;
  
  public PKCS12StoreParameter(OutputStream out, char[] password)
  {
    this(out, password, false);
  }
  
  public PKCS12StoreParameter(OutputStream out, KeyStore.ProtectionParameter protectionParameter)
  {
    this(out, protectionParameter, false);
  }
  
  public PKCS12StoreParameter(OutputStream out, char[] password, boolean forDEREncoding)
  {
    this(out, new KeyStore.PasswordProtection(password), forDEREncoding);
  }
  
  public PKCS12StoreParameter(OutputStream out, KeyStore.ProtectionParameter protectionParameter, boolean forDEREncoding)
  {
    this.out = out;
    this.protectionParameter = protectionParameter;
    this.forDEREncoding = forDEREncoding;
  }
  
  public OutputStream getOutputStream()
  {
    return out;
  }
  
  public KeyStore.ProtectionParameter getProtectionParameter()
  {
    return protectionParameter;
  }
  





  public boolean isForDEREncoding()
  {
    return forDEREncoding;
  }
}
