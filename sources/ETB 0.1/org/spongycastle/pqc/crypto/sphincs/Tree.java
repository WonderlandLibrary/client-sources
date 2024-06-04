package org.spongycastle.pqc.crypto.sphincs;


class Tree
{
  Tree() {}
  
  static class leafaddr
  {
    int level;
    long subtree;
    long subleaf;
    
    public leafaddr() {}
    
    public leafaddr(leafaddr leafaddr)
    {
      level = level;
      subtree = subtree;
      subleaf = subleaf;
    }
  }
  
  static void l_tree(HashFunctions hs, byte[] leaf, int leafOff, byte[] wots_pk, int pkOff, byte[] masks, int masksOff)
  {
    int l = 67;
    int j = 0;
    for (int i = 0; i < 7; i++)
    {
      for (j = 0; j < l >>> 1; j++)
      {
        hs.hash_2n_n_mask(wots_pk, pkOff + j * 32, wots_pk, pkOff + j * 2 * 32, masks, masksOff + i * 2 * 32);
      }
      
      if ((l & 0x1) != 0)
      {
        System.arraycopy(wots_pk, pkOff + (l - 1) * 32, wots_pk, pkOff + (l >>> 1) * 32, 32);
        l = (l >>> 1) + 1;
      }
      else
      {
        l >>>= 1;
      }
    }
    System.arraycopy(wots_pk, pkOff, leaf, leafOff, 32);
  }
  
  static void treehash(HashFunctions hs, byte[] node, int nodeOff, int height, byte[] sk, leafaddr leaf, byte[] masks, int masksOff)
  {
    leafaddr a = new leafaddr(leaf);
    
    byte[] stack = new byte[(height + 1) * 32];
    int[] stacklevels = new int[height + 1];
    int stackoffset = 0;
    
    int lastnode = (int)(subleaf + (1 << height));
    for (; 
        subleaf < lastnode; subleaf += 1L)
    {
      gen_leaf_wots(hs, stack, stackoffset * 32, masks, masksOff, sk, a);
      stacklevels[stackoffset] = 0;
      stackoffset++;
      while ((stackoffset > 1) && (stacklevels[(stackoffset - 1)] == stacklevels[(stackoffset - 2)]))
      {

        int maskoffset = 2 * (stacklevels[(stackoffset - 1)] + 7) * 32;
        
        hs.hash_2n_n_mask(stack, (stackoffset - 2) * 32, stack, (stackoffset - 2) * 32, masks, masksOff + maskoffset);
        
        stacklevels[(stackoffset - 2)] += 1;
        stackoffset--;
      }
    }
    for (int i = 0; i < 32; i++)
    {
      node[(nodeOff + i)] = stack[i];
    }
  }
  
  static void gen_leaf_wots(HashFunctions hs, byte[] leaf, int leafOff, byte[] masks, int masksOff, byte[] sk, leafaddr a)
  {
    byte[] seed = new byte[32];
    byte[] pk = new byte['ࡠ'];
    
    Wots w = new Wots();
    
    Seed.get_seed(hs, seed, 0, sk, a);
    
    w.wots_pkgen(hs, pk, 0, seed, 0, masks, masksOff);
    
    l_tree(hs, leaf, leafOff, pk, 0, masks, masksOff);
  }
}
