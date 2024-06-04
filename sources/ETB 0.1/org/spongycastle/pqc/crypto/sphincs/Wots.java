package org.spongycastle.pqc.crypto.sphincs;


class Wots
{
  static final int WOTS_LOGW = 4;
  static final int WOTS_W = 16;
  static final int WOTS_L1 = 64;
  static final int WOTS_L = 67;
  static final int WOTS_LOG_L = 7;
  static final int WOTS_SIGBYTES = 2144;
  
  Wots() {}
  
  static void expand_seed(byte[] outseeds, int outOff, byte[] inseed, int inOff)
  {
    clear(outseeds, outOff, 2144);
    
    Seed.prg(outseeds, outOff, 2144L, inseed, inOff);
  }
  
  private static void clear(byte[] bytes, int offSet, int length)
  {
    for (int i = 0; i != length; i++)
    {
      bytes[(i + offSet)] = 0;
    }
  }
  

  static void gen_chain(HashFunctions hs, byte[] out, int outOff, byte[] seed, int seedOff, byte[] masks, int masksOff, int chainlen)
  {
    for (int j = 0; j < 32; j++) {
      out[(j + outOff)] = seed[(j + seedOff)];
    }
    for (int i = 0; (i < chainlen) && (i < 16); i++) {
      hs.hash_n_n_mask(out, outOff, out, outOff, masks, masksOff + i * 32);
    }
  }
  

  void wots_pkgen(HashFunctions hs, byte[] pk, int pkOff, byte[] sk, int skOff, byte[] masks, int masksOff)
  {
    expand_seed(pk, pkOff, sk, skOff);
    for (int i = 0; i < 67; i++) {
      gen_chain(hs, pk, pkOff + i * 32, pk, pkOff + i * 32, masks, masksOff, 15);
    }
  }
  
  void wots_sign(HashFunctions hs, byte[] sig, int sigOff, byte[] msg, byte[] sk, byte[] masks)
  {
    int[] basew = new int[67];
    int c = 0;
    
    for (int i = 0; i < 64; i += 2)
    {
      basew[i] = (msg[(i / 2)] & 0xF);
      basew[(i + 1)] = ((msg[(i / 2)] & 0xFF) >>> 4);
      c += 15 - basew[i];
      c += 15 - basew[(i + 1)];
    }
    for (; 
        i < 67; i++)
    {
      basew[i] = (c & 0xF);
      c >>>= 4;
    }
    
    expand_seed(sig, sigOff, sk, 0);
    
    for (i = 0; i < 67; i++) {
      gen_chain(hs, sig, sigOff + i * 32, sig, sigOff + i * 32, masks, 0, basew[i]);
    }
  }
  
  void wots_verify(HashFunctions hs, byte[] pk, byte[] sig, int sigOff, byte[] msg, byte[] masks) {
    int[] basew = new int[67];
    int c = 0;
    
    for (int i = 0; i < 64; i += 2)
    {
      basew[i] = (msg[(i / 2)] & 0xF);
      basew[(i + 1)] = ((msg[(i / 2)] & 0xFF) >>> 4);
      c += 15 - basew[i];
      c += 15 - basew[(i + 1)];
    }
    for (; 
        i < 67; i++)
    {
      basew[i] = (c & 0xF);
      c >>>= 4;
    }
    
    for (i = 0; i < 67; i++) {
      gen_chain(hs, pk, i * 32, sig, sigOff + i * 32, masks, basew[i] * 32, 15 - basew[i]);
    }
  }
}
