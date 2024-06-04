package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.engines.ChaChaEngine;

class ChaCha20
{
  ChaCha20() {}
  
  static void process(byte[] key, byte[] nonce, byte[] buf, int off, int len)
  {
    ChaChaEngine e = new ChaChaEngine(20);
    e.init(true, new org.spongycastle.crypto.params.ParametersWithIV(new org.spongycastle.crypto.params.KeyParameter(key), nonce));
    e.processBytes(buf, off, len, buf, off);
  }
}
