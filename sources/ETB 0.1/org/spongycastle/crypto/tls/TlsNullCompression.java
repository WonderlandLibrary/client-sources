package org.spongycastle.crypto.tls;

import java.io.OutputStream;

public class TlsNullCompression implements TlsCompression
{
  public TlsNullCompression() {}
  
  public OutputStream compress(OutputStream output) {
    return output;
  }
  
  public OutputStream decompress(OutputStream output)
  {
    return output;
  }
}
