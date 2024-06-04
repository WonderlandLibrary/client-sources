package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;

class DigestInputBuffer extends java.io.ByteArrayOutputStream
{
  DigestInputBuffer() {}
  
  void updateDigest(Digest d)
  {
    d.update(buf, 0, count);
  }
}
