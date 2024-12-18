package org.spongycastle.crypto.tls;

import java.io.IOException;

public class AbstractTlsCipherFactory implements TlsCipherFactory
{
  public AbstractTlsCipherFactory() {}
  
  public TlsCipher createCipher(TlsContext context, int encryptionAlgorithm, int macAlgorithm) throws IOException
  {
    throw new TlsFatalAlert((short)80);
  }
}
