package org.spongycastle.pqc.crypto.sphincs;

class Horst
{
  static final int HORST_LOGT = 16;
  static final int HORST_T = 65536;
  static final int HORST_K = 32;
  static final int HORST_SKBYTES = 32;
  static final int HORST_SIGBYTES = 13312;
  static final int N_MASKS = 32;
  
  Horst() {}
  
  static void expand_seed(byte[] outseeds, byte[] inseed) {
    Seed.prg(outseeds, 0, 2097152L, inseed, 0);
  }
  




  static int horst_sign(HashFunctions hs, byte[] sig, int sigOff, byte[] pk, byte[] seed, byte[] masks, byte[] m_hash)
  {
    byte[] sk = new byte[2097152];
    

    int sigpos = sigOff;
    
    byte[] tree = new byte[4194272];
    
    expand_seed(sk, seed);
    



    for (int i = 0; i < 65536; i++)
    {
      hs.hash_n_n(tree, (65535 + i) * 32, sk, i * 32);
    }
    

    for (i = 0; i < 16; i++)
    {
      long offset_in = (1 << 16 - i) - 1;
      long offset_out = (1 << 16 - i - 1) - 1;
      for (int j = 0; j < 1 << 16 - i - 1; j++)
      {
        hs.hash_2n_n_mask(tree, (int)((offset_out + j) * 32L), tree, (int)((offset_in + 2 * j) * 32L), masks, 2 * i * 32);
      }
    }
    

    for (int j = 2016; j < 4064; j++)
    {
      sig[(sigpos++)] = tree[j];
    }
    

    for (i = 0; i < 32; i++)
    {
      int idx = (m_hash[(2 * i)] & 0xFF) + ((m_hash[(2 * i + 1)] & 0xFF) << 8);
      
      for (int k = 0; k < 32; k++) {
        sig[(sigpos++)] = sk[(idx * 32 + k)];
      }
      idx += 65535;
      for (j = 0; j < 10; j++)
      {
        idx = (idx & 0x1) != 0 ? idx + 1 : idx - 1;
        for (k = 0; k < 32; k++)
          sig[(sigpos++)] = tree[(idx * 32 + k)];
        idx = (idx - 1) / 2;
      }
    }
    
    for (i = 0; i < 32; i++)
    {
      pk[i] = tree[i];
    }
    
    return 13312;
  }
  
  static int horst_verify(HashFunctions hs, byte[] pk, byte[] sig, int sigOff, byte[] masks, byte[] m_hash)
  {
    byte[] buffer = new byte['Ѐ'];
    



    int sigOffset = sigOff + 2048;
    
    for (int i = 0; i < 32; i++)
    {
      int idx = (m_hash[(2 * i)] & 0xFF) + ((m_hash[(2 * i + 1)] & 0xFF) << 8);
      
      if ((idx & 0x1) == 0)
      {
        hs.hash_n_n(buffer, 0, sig, sigOffset);
        for (int k = 0; k < 32; k++) {
          buffer[(32 + k)] = sig[(sigOffset + 32 + k)];
        }
      }
      
      hs.hash_n_n(buffer, 32, sig, sigOffset);
      for (int k = 0; k < 32; k++) {
        buffer[k] = sig[(sigOffset + 32 + k)];
      }
      sigOffset += 64;
      
      for (int j = 1; j < 10; j++)
      {
        idx >>>= 1;
        
        if ((idx & 0x1) == 0)
        {
          hs.hash_2n_n_mask(buffer, 0, buffer, 0, masks, 2 * (j - 1) * 32);
          for (k = 0; k < 32; k++) {
            buffer[(32 + k)] = sig[(sigOffset + k)];
          }
        }
        

        hs.hash_2n_n_mask(buffer, 32, buffer, 0, masks, 2 * (j - 1) * 32);
        for (k = 0; k < 32; k++) {
          buffer[k] = sig[(sigOffset + k)];
        }
        sigOffset += 32;
      }
      
      idx >>>= 1;
      hs.hash_2n_n_mask(buffer, 0, buffer, 0, masks, 576);
      
      for (k = 0; k < 32; k++) {
        if (sig[(sigOff + idx * 32 + k)] != buffer[k])
        {
          for (k = 0; k < 32; k++)
            pk[k] = 0;
          return -1;
        }
      }
    }
    
    for (int j = 0; j < 32; j++)
    {
      hs.hash_2n_n_mask(buffer, j * 32, sig, sigOff + 2 * j * 32, masks, 640);
    }
    

    for (j = 0; j < 16; j++)
    {
      hs.hash_2n_n_mask(buffer, j * 32, buffer, 2 * j * 32, masks, 704);
    }
    

    for (j = 0; j < 8; j++)
    {
      hs.hash_2n_n_mask(buffer, j * 32, buffer, 2 * j * 32, masks, 768);
    }
    

    for (j = 0; j < 4; j++)
    {
      hs.hash_2n_n_mask(buffer, j * 32, buffer, 2 * j * 32, masks, 832);
    }
    

    for (j = 0; j < 2; j++)
    {
      hs.hash_2n_n_mask(buffer, j * 32, buffer, 2 * j * 32, masks, 896);
    }
    

    hs.hash_2n_n_mask(pk, 0, buffer, 0, masks, 960);
    
    return 0;
  }
}
