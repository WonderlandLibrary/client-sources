package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;


























































public class GMSSLeaf
{
  private Digest messDigestOTS;
  private int mdsize;
  private int keysize;
  private GMSSRandom gmssRandom;
  private byte[] leaf;
  private byte[] concHashs;
  private int i;
  private int j;
  private int two_power_w;
  private int w;
  private int steps;
  private byte[] seed;
  byte[] privateKeyOTS;
  
  public GMSSLeaf(Digest digest, byte[][] otsIndex, int[] numLeafs)
  {
    i = numLeafs[0];
    j = numLeafs[1];
    steps = numLeafs[2];
    w = numLeafs[3];
    
    messDigestOTS = digest;
    
    gmssRandom = new GMSSRandom(messDigestOTS);
    

    mdsize = messDigestOTS.getDigestSize();
    int mdsizeBit = mdsize << 3;
    int messagesize = (int)Math.ceil(mdsizeBit / w);
    int checksumsize = getLog((messagesize << w) + 1);
    
    keysize = (messagesize + (int)Math.ceil(checksumsize / w));
    two_power_w = (1 << w);
    




    privateKeyOTS = otsIndex[0];
    seed = otsIndex[1];
    concHashs = otsIndex[2];
    leaf = otsIndex[3];
  }
  












  GMSSLeaf(Digest digest, int w, int numLeafs)
  {
    this.w = w;
    
    messDigestOTS = digest;
    
    gmssRandom = new GMSSRandom(messDigestOTS);
    

    mdsize = messDigestOTS.getDigestSize();
    int mdsizeBit = mdsize << 3;
    int messagesize = (int)Math.ceil(mdsizeBit / w);
    int checksumsize = getLog((messagesize << w) + 1);
    
    keysize = (messagesize + (int)Math.ceil(checksumsize / w));
    two_power_w = (1 << w);
    



    steps = ((int)Math.ceil((((1 << w) - 1) * keysize + 1 + keysize) / numLeafs));
    


    seed = new byte[mdsize];
    leaf = new byte[mdsize];
    privateKeyOTS = new byte[mdsize];
    concHashs = new byte[mdsize * keysize];
  }
  
  public GMSSLeaf(Digest digest, int w, int numLeafs, byte[] seed0)
  {
    this.w = w;
    
    messDigestOTS = digest;
    
    gmssRandom = new GMSSRandom(messDigestOTS);
    

    mdsize = messDigestOTS.getDigestSize();
    int mdsizeBit = mdsize << 3;
    int messagesize = (int)Math.ceil(mdsizeBit / w);
    int checksumsize = getLog((messagesize << w) + 1);
    
    keysize = (messagesize + (int)Math.ceil(checksumsize / w));
    two_power_w = (1 << w);
    



    steps = ((int)Math.ceil((((1 << w) - 1) * keysize + 1 + keysize) / numLeafs));
    


    seed = new byte[mdsize];
    leaf = new byte[mdsize];
    privateKeyOTS = new byte[mdsize];
    concHashs = new byte[mdsize * keysize];
    
    initLeafCalc(seed0);
  }
  
  private GMSSLeaf(GMSSLeaf original)
  {
    messDigestOTS = messDigestOTS;
    mdsize = mdsize;
    keysize = keysize;
    gmssRandom = gmssRandom;
    leaf = Arrays.clone(leaf);
    concHashs = Arrays.clone(concHashs);
    i = i;
    j = j;
    two_power_w = two_power_w;
    w = w;
    steps = steps;
    seed = Arrays.clone(seed);
    privateKeyOTS = Arrays.clone(privateKeyOTS);
  }
  







  void initLeafCalc(byte[] seed0)
  {
    i = 0;
    j = 0;
    byte[] dummy = new byte[mdsize];
    System.arraycopy(seed0, 0, dummy, 0, seed.length);
    seed = gmssRandom.nextSeed(dummy);
  }
  
  GMSSLeaf nextLeaf()
  {
    GMSSLeaf nextLeaf = new GMSSLeaf(this);
    
    nextLeaf.updateLeafCalc();
    
    return nextLeaf;
  }
  





  private void updateLeafCalc()
  {
    byte[] buf = new byte[messDigestOTS.getDigestSize()];
    



    for (int s = 0; s < steps + 10000; s++)
    {
      if ((i == keysize) && (j == two_power_w - 1))
      {

        messDigestOTS.update(concHashs, 0, concHashs.length);
        leaf = new byte[messDigestOTS.getDigestSize()];
        messDigestOTS.doFinal(leaf, 0);
        return;
      }
      if ((i == 0) || (j == two_power_w - 1))
      {





        i += 1;
        j = 0;
        
        privateKeyOTS = gmssRandom.nextSeed(seed);
      }
      else
      {
        messDigestOTS.update(privateKeyOTS, 0, privateKeyOTS.length);
        privateKeyOTS = buf;
        messDigestOTS.doFinal(privateKeyOTS, 0);
        j += 1;
        if (j == two_power_w - 1)
        {

          System.arraycopy(privateKeyOTS, 0, concHashs, mdsize * (i - 1), mdsize);
        }
      }
    }
    

    throw new IllegalStateException("unable to updateLeaf in steps: " + steps + " " + i + " " + j);
  }
  





  public byte[] getLeaf()
  {
    return Arrays.clone(leaf);
  }
  








  private int getLog(int intValue)
  {
    int log = 1;
    int i = 2;
    while (i < intValue)
    {
      i <<= 1;
      log++;
    }
    return log;
  }
  






  public byte[][] getStatByte()
  {
    byte[][] statByte = new byte[4][];
    statByte[0] = new byte[mdsize];
    statByte[1] = new byte[mdsize];
    statByte[2] = new byte[mdsize * keysize];
    statByte[3] = new byte[mdsize];
    statByte[0] = privateKeyOTS;
    statByte[1] = seed;
    statByte[2] = concHashs;
    statByte[3] = leaf;
    
    return statByte;
  }
  






  public int[] getStatInt()
  {
    int[] statInt = new int[4];
    statInt[0] = i;
    statInt[1] = j;
    statInt[2] = steps;
    statInt[3] = w;
    return statInt;
  }
  





  public String toString()
  {
    String out = "";
    
    for (int i = 0; i < 4; i++)
    {
      out = out + getStatInt()[i] + " ";
    }
    out = out + " " + mdsize + " " + keysize + " " + two_power_w + " ";
    

    byte[][] temp = getStatByte();
    for (int i = 0; i < 4; i++)
    {
      if (temp[i] != null)
      {
        out = out + new String(Hex.encode(temp[i])) + " ";
      }
      else
      {
        out = out + "null ";
      }
    }
    return out;
  }
}
