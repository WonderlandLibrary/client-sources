package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.engines.ChaChaEngine;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;

class Seed
{
  Seed() {}
  
  static void get_seed(HashFunctions hs, byte[] seed, int seedOff, byte[] sk, Tree.leafaddr a)
  {
    byte[] buffer = new byte[40];
    


    for (int i = 0; i < 32; i++)
    {
      buffer[i] = sk[i];
    }
    

    long t = level;
    
    t |= subtree << 4;
    
    t |= subleaf << 59;
    
    Pack.longToLittleEndian(t, buffer, 32);
    
    hs.varlen_hash(seed, seedOff, buffer, buffer.length);
  }
  


  static void prg(byte[] r, int rOff, long rlen, byte[] key, int keyOff)
  {
    byte[] nonce = new byte[8];
    
    StreamCipher cipher = new ChaChaEngine(12);
    
    cipher.init(true, new org.spongycastle.crypto.params.ParametersWithIV(new KeyParameter(key, keyOff, 32), nonce));
    
    cipher.processBytes(r, rOff, (int)rlen, r, rOff);
  }
}
