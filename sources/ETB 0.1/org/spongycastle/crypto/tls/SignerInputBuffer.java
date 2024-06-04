package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Signer;

class SignerInputBuffer extends java.io.ByteArrayOutputStream
{
  SignerInputBuffer() {}
  
  void updateSigner(Signer s)
  {
    s.update(buf, 0, count);
  }
}
