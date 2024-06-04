package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.util.Pack;
















public class SPHINCS256Signer
  implements MessageSigner
{
  private final HashFunctions hashFunctions;
  private byte[] keyData;
  
  public SPHINCS256Signer(Digest nDigest, Digest twoNDigest)
  {
    if (nDigest.getDigestSize() != 32)
    {
      throw new IllegalArgumentException("n-digest needs to produce 32 bytes of output");
    }
    if (twoNDigest.getDigestSize() != 64)
    {
      throw new IllegalArgumentException("2n-digest needs to produce 64 bytes of output");
    }
    
    hashFunctions = new HashFunctions(nDigest, twoNDigest);
  }
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      keyData = ((SPHINCSPrivateKeyParameters)param).getKeyData();
    }
    else
    {
      keyData = ((SPHINCSPublicKeyParameters)param).getKeyData();
    }
  }
  
  public byte[] generateSignature(byte[] message)
  {
    return crypto_sign(hashFunctions, message, keyData);
  }
  
  public boolean verifySignature(byte[] message, byte[] signature)
  {
    return verify(hashFunctions, message, signature, keyData);
  }
  

  static void validate_authpath(HashFunctions hs, byte[] root, byte[] leaf, int leafidx, byte[] authpath, int auOff, byte[] masks, int height)
  {
    byte[] buffer = new byte[64];
    
    if ((leafidx & 0x1) != 0)
    {
      for (int j = 0; j < 32; j++)
      {
        buffer[(32 + j)] = leaf[j];
      }
      for (j = 0; j < 32; j++)
      {
        buffer[j] = authpath[(auOff + j)];
      }
    }
    

    for (int j = 0; j < 32; j++)
    {
      buffer[j] = leaf[j];
    }
    for (j = 0; j < 32; j++)
    {
      buffer[(32 + j)] = authpath[(auOff + j)];
    }
    
    int authOff = auOff + 32;
    
    for (int i = 0; i < height - 1; i++)
    {
      leafidx >>>= 1;
      if ((leafidx & 0x1) != 0)
      {
        hs.hash_2n_n_mask(buffer, 32, buffer, 0, masks, 2 * (7 + i) * 32);
        for (j = 0; j < 32; j++)
        {
          buffer[j] = authpath[(authOff + j)];
        }
      }
      

      hs.hash_2n_n_mask(buffer, 0, buffer, 0, masks, 2 * (7 + i) * 32);
      for (j = 0; j < 32; j++)
      {
        buffer[(j + 32)] = authpath[(authOff + j)];
      }
      
      authOff += 32;
    }
    hs.hash_2n_n_mask(root, 0, buffer, 0, masks, 2 * (7 + height - 1) * 32);
  }
  


  static void compute_authpath_wots(HashFunctions hs, byte[] root, byte[] authpath, int authOff, Tree.leafaddr a, byte[] sk, byte[] masks, int height)
  {
    Tree.leafaddr ta = new Tree.leafaddr(a);
    
    byte[] tree = new byte['ࠀ'];
    byte[] seed = new byte['Ѐ'];
    byte[] pk = new byte[68608];
    

    for (subleaf = 0L; subleaf < 32L; subleaf += 1L)
    {
      Seed.get_seed(hs, seed, (int)(subleaf * 32L), sk, ta);
    }
    
    Wots w = new Wots();
    
    for (subleaf = 0L; subleaf < 32L; subleaf += 1L)
    {
      w.wots_pkgen(hs, pk, (int)(subleaf * 67L * 32L), seed, (int)(subleaf * 32L), masks, 0);
    }
    
    for (subleaf = 0L; subleaf < 32L; subleaf += 1L)
    {
      Tree.l_tree(hs, tree, (int)(1024L + subleaf * 32L), pk, (int)(subleaf * 67L * 32L), masks, 0);
    }
    

    int level = 0;
    

    for (int i = 32; i > 0; i >>>= 1)
    {
      for (int j = 0; j < i; j += 2)
      {
        hs.hash_2n_n_mask(tree, (i >>> 1) * 32 + (j >>> 1) * 32, tree, i * 32 + j * 32, masks, 2 * (7 + level) * 32);
      }
      


      level++;
    }
    

    int idx = (int)subleaf;
    

    for (i = 0; i < height; i++)
    {
      System.arraycopy(tree, (32 >>> i) * 32 + (idx >>> i ^ 0x1) * 32, authpath, authOff + i * 32, 32);
    }
    

    System.arraycopy(tree, 32, root, 0, 32);
  }
  
  byte[] crypto_sign(HashFunctions hs, byte[] m, byte[] sk)
  {
    byte[] sm = new byte[41000];
    


    byte[] R = new byte[32];
    byte[] m_h = new byte[64];
    long[] rnd = new long[8];
    
    byte[] root = new byte[32];
    byte[] seed = new byte[32];
    byte[] masks = new byte['Ѐ'];
    
    byte[] tsk = new byte['р'];
    
    for (int i = 0; i < 1088; i++)
    {
      tsk[i] = sk[i];
    }
    



    int scratch = 40968;
    

    System.arraycopy(tsk, 1056, sm, scratch, 32);
    
    Digest d = hs.getMessageHash();
    byte[] bRnd = new byte[d.getDigestSize()];
    
    d.update(sm, scratch, 32);
    
    d.update(m, 0, m.length);
    
    d.doFinal(bRnd, 0);
    

    zerobytes(sm, scratch, 32);
    
    for (int j = 0; j != rnd.length; j++)
    {
      rnd[j] = Pack.littleEndianToLong(bRnd, j * 8);
    }
    long leafidx = rnd[0] & 0xFFFFFFFFFFFFFFF;
    
    System.arraycopy(bRnd, 16, R, 0, 32);
    

    scratch = 39912;
    

    System.arraycopy(R, 0, sm, scratch, 32);
    

    Tree.leafaddr b = new Tree.leafaddr();
    level = 11;
    subtree = 0L;
    subleaf = 0L;
    
    int pk = scratch + 32;
    
    System.arraycopy(tsk, 32, sm, pk, 1024);
    
    Tree.treehash(hs, sm, pk + 1024, 5, tsk, b, sm, pk);
    
    d = hs.getMessageHash();
    
    d.update(sm, scratch, 1088);
    d.update(m, 0, m.length);
    d.doFinal(m_h, 0);
    

    Tree.leafaddr a = new Tree.leafaddr();
    
    level = 12;
    subleaf = ((int)(leafidx & 0x1F));
    subtree = (leafidx >>> 5);
    
    for (i = 0; i < 32; i++)
    {
      sm[i] = R[i];
    }
    
    int smOff = 32;
    
    System.arraycopy(tsk, 32, masks, 0, 1024);
    for (i = 0; i < 8; i++)
    {
      sm[(smOff + i)] = ((byte)(int)(leafidx >>> 8 * i & 0xFF));
    }
    
    smOff += 8;
    
    Seed.get_seed(hs, seed, 0, tsk, a);
    Horst ht = new Horst();
    
    int horst_sigbytes = Horst.horst_sign(hs, sm, smOff, root, seed, masks, m_h);
    
    smOff += horst_sigbytes;
    
    Wots w = new Wots();
    
    for (i = 0; i < 12; i++)
    {
      level = i;
      
      Seed.get_seed(hs, seed, 0, tsk, a);
      
      w.wots_sign(hs, sm, smOff, root, seed, masks);
      
      smOff += 2144;
      
      compute_authpath_wots(hs, root, sm, smOff, a, tsk, masks, 5);
      smOff += 160;
      
      subleaf = ((int)(subtree & 0x1F));
      subtree >>>= 5;
    }
    
    zerobytes(tsk, 0, 1088);
    
    return sm;
  }
  
  private void zerobytes(byte[] tsk, int off, int cryptoSecretkeybytes)
  {
    for (int i = 0; i != cryptoSecretkeybytes; i++)
    {
      tsk[(off + i)] = 0;
    }
  }
  

  boolean verify(HashFunctions hs, byte[] m, byte[] sm, byte[] pk)
  {
    int smlen = sm.length;
    long leafidx = 0L;
    byte[] wots_pk = new byte['ࡠ'];
    byte[] pkhash = new byte[32];
    byte[] root = new byte[32];
    byte[] sig = new byte[41000];
    
    byte[] tpk = new byte['Р'];
    
    if (smlen != 41000)
    {
      throw new IllegalArgumentException("signature wrong size");
    }
    
    byte[] m_h = new byte[64];
    
    for (int i = 0; i < 1056; i++) {
      tpk[i] = pk[i];
    }
    

    byte[] R = new byte[32];
    
    for (i = 0; i < 32; i++) {
      R[i] = sm[i];
    }
    System.arraycopy(sm, 0, sig, 0, 41000);
    
    Digest mHash = hs.getMessageHash();
    

    mHash.update(R, 0, 32);
    

    mHash.update(tpk, 0, 1056);
    

    mHash.update(m, 0, m.length);
    
    mHash.doFinal(m_h, 0);
    

    int sigp = 0;
    
    sigp += 32;
    smlen -= 32;
    

    for (i = 0; i < 8; i++)
    {
      leafidx ^= (sig[(sigp + i)] & 0xFF) << 8 * i;
    }
    

    new Horst();Horst.horst_verify(hs, root, sig, sigp + 8, tpk, m_h);
    

    sigp += 8;
    smlen -= 8;
    
    sigp += 13312;
    smlen -= 13312;
    
    Wots w = new Wots();
    
    for (i = 0; i < 12; i++)
    {
      w.wots_verify(hs, wots_pk, sig, sigp, root, tpk);
      
      sigp += 2144;
      smlen -= 2144;
      
      Tree.l_tree(hs, pkhash, 0, wots_pk, 0, tpk, 0);
      validate_authpath(hs, root, pkhash, (int)(leafidx & 0x1F), sig, sigp, tpk, 5);
      leafidx >>= 5;
      
      sigp += 160;
      smlen -= 160;
    }
    
    boolean verified = true;
    for (i = 0; i < 32; i++)
    {
      if (root[i] != tpk[(i + 1024)])
      {
        verified = false;
      }
    }
    
    return verified;
  }
}
