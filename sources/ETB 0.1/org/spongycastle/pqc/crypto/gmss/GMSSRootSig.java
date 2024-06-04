package org.spongycastle.pqc.crypto.gmss;

import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.encoders.Hex;










































































public class GMSSRootSig
{
  private Digest messDigestOTS;
  private int mdsize;
  private int keysize;
  private byte[] privateKeyOTS;
  private byte[] hash;
  private byte[] sign;
  private int w;
  private GMSSRandom gmssRandom;
  private int messagesize;
  private int k;
  private int r;
  private int test;
  private int counter;
  private int ii;
  private long test8;
  private long big8;
  private int steps;
  private int checksum;
  private int height;
  private byte[] seed;
  
  public GMSSRootSig(Digest digest, byte[][] statByte, int[] statInt)
  {
    messDigestOTS = digest;
    gmssRandom = new GMSSRandom(messDigestOTS);
    
    counter = statInt[0];
    test = statInt[1];
    ii = statInt[2];
    r = statInt[3];
    steps = statInt[4];
    keysize = statInt[5];
    height = statInt[6];
    w = statInt[7];
    checksum = statInt[8];
    
    mdsize = messDigestOTS.getDigestSize();
    
    k = ((1 << w) - 1);
    
    int mdsizeBit = mdsize << 3;
    messagesize = ((int)Math.ceil(mdsizeBit / w));
    
    privateKeyOTS = statByte[0];
    seed = statByte[1];
    hash = statByte[2];
    
    sign = statByte[3];
    
    test8 = (statByte[4][0] & 0xFF | (statByte[4][1] & 0xFF) << 8 | (statByte[4][2] & 0xFF) << 16 | (statByte[4][3] & 0xFF) << 24 | (statByte[4][4] & 0xFF) << 32 | (statByte[4][5] & 0xFF) << 40 | (statByte[4][6] & 0xFF) << 48 | (statByte[4][7] & 0xFF) << 56);
    







    big8 = (statByte[4][8] & 0xFF | (statByte[4][9] & 0xFF) << 8 | (statByte[4][10] & 0xFF) << 16 | (statByte[4][11] & 0xFF) << 24 | (statByte[4][12] & 0xFF) << 32 | (statByte[4][13] & 0xFF) << 40 | (statByte[4][14] & 0xFF) << 48 | (statByte[4][15] & 0xFF) << 56);
  }
  
















  public GMSSRootSig(Digest digest, int w, int height)
  {
    messDigestOTS = digest;
    gmssRandom = new GMSSRandom(messDigestOTS);
    
    mdsize = messDigestOTS.getDigestSize();
    this.w = w;
    this.height = height;
    
    k = ((1 << w) - 1);
    
    int mdsizeBit = mdsize << 3;
    messagesize = ((int)Math.ceil(mdsizeBit / w));
  }
  









  public void initSign(byte[] seed0, byte[] message)
  {
    hash = new byte[mdsize];
    messDigestOTS.update(message, 0, message.length);
    hash = new byte[messDigestOTS.getDigestSize()];
    messDigestOTS.doFinal(hash, 0);
    

    byte[] messPart = new byte[mdsize];
    System.arraycopy(hash, 0, messPart, 0, mdsize);
    int checkPart = 0;
    int sumH = 0;
    int checksumsize = getLog((messagesize << w) + 1);
    

    if (8 % w == 0)
    {
      int dt = 8 / w;
      
      for (int a = 0; a < mdsize; a++)
      {

        for (int b = 0; b < dt; b++)
        {
          sumH += (messPart[a] & k);
          messPart[a] = ((byte)(messPart[a] >>> w));
        }
      }
      
      checksum = ((messagesize << w) - sumH);
      checkPart = checksum;
      
      for (int b = 0; b < checksumsize; b += w)
      {
        sumH += (checkPart & k);
        checkPart >>>= w;
      }
    }
    else if (w < 8)
    {

      int ii = 0;
      int dt = mdsize / w;
      

      for (int i = 0; i < dt; i++)
      {
        long big8 = 0L;
        for (int j = 0; j < w; j++)
        {
          big8 ^= (messPart[ii] & 0xFF) << (j << 3);
          ii++;
        }
        
        for (int j = 0; j < 8; j++)
        {
          sumH += (int)(big8 & k);
          big8 >>>= w;
        }
      }
      
      dt = mdsize % w;
      long big8 = 0L;
      for (int j = 0; j < dt; j++)
      {
        big8 ^= (messPart[ii] & 0xFF) << (j << 3);
        ii++;
      }
      dt <<= 3;
      
      for (int j = 0; j < dt; j += w)
      {
        sumH += (int)(big8 & k);
        big8 >>>= w;
      }
      
      checksum = ((messagesize << w) - sumH);
      checkPart = checksum;
      
      for (int i = 0; i < checksumsize; i += w)
      {
        sumH += (checkPart & k);
        checkPart >>>= w;
      }
    }
    else if (w < 57)
    {

      int r = 0;
      



      while (r <= (mdsize << 3) - w)
      {
        int s = r >>> 3;
        int rest = r % 8;
        r += w;
        int f = r + 7 >>> 3;
        long big8 = 0L;
        int ii = 0;
        for (int j = s; j < f; j++)
        {
          big8 ^= (messPart[j] & 0xFF) << (ii << 3);
          ii++;
        }
        big8 >>>= rest;
        
        sumH = (int)(sumH + (big8 & k));
      }
      

      int s = r >>> 3;
      if (s < mdsize)
      {
        int rest = r % 8;
        long big8 = 0L;
        int ii = 0;
        for (int j = s; j < mdsize; j++)
        {
          big8 ^= (messPart[j] & 0xFF) << (ii << 3);
          ii++;
        }
        
        big8 >>>= rest;
        
        sumH = (int)(sumH + (big8 & k));
      }
      
      checksum = ((messagesize << w) - sumH);
      checkPart = checksum;
      
      for (int i = 0; i < checksumsize; i += w)
      {
        sumH += (checkPart & k);
        checkPart >>>= w;
      }
    }
    


    keysize = (messagesize + (int)Math.ceil(checksumsize / w));
    


    steps = ((int)Math.ceil((keysize + sumH) / (1 << height)));
    



    sign = new byte[keysize * mdsize];
    counter = 0;
    test = 0;
    this.ii = 0;
    test8 = 0L;
    this.r = 0;
    
    privateKeyOTS = new byte[mdsize];
    
    seed = new byte[mdsize];
    System.arraycopy(seed0, 0, seed, 0, mdsize);
  }
  









  public boolean updateSign()
  {
    for (int s = 0; s < steps; s++)
    {

      if (counter < keysize)
      {

        oneStep();
      }
      if (counter == keysize)
      {
        return true;
      }
    }
    
    return false;
  }
  




  public byte[] getSig()
  {
    return sign;
  }
  




  private void oneStep()
  {
    if (8 % w == 0)
    {
      if (test == 0)
      {

        privateKeyOTS = gmssRandom.nextSeed(seed);
        

        if (ii < mdsize)
        {
          test = (hash[ii] & k);
          hash[ii] = ((byte)(hash[ii] >>> w));
        }
        else
        {
          test = (checksum & k);
          checksum >>>= w;
        }
      }
      else if (test > 0)
      {

        messDigestOTS.update(privateKeyOTS, 0, privateKeyOTS.length);
        privateKeyOTS = new byte[messDigestOTS.getDigestSize()];
        messDigestOTS.doFinal(privateKeyOTS, 0);
        test -= 1;
      }
      if (test == 0)
      {

        System.arraycopy(privateKeyOTS, 0, sign, counter * mdsize, mdsize);
        
        counter += 1;
        
        if (counter % (8 / w) == 0)
        {

          ii += 1;
        }
        
      }
      
    }
    else if (w < 8)
    {

      if (test == 0)
      {
        if ((counter % 8 == 0) && (ii < mdsize))
        {

          big8 = 0L;
          if (counter < mdsize / w << 3)
          {


            for (int j = 0; j < w; j++)
            {
              big8 ^= (hash[ii] & 0xFF) << (j << 3);
              ii += 1;
            }
            
          }
          else {
            for (int j = 0; j < mdsize % w; j++)
            {
              big8 ^= (hash[ii] & 0xFF) << (j << 3);
              ii += 1;
            }
          }
        }
        if (counter == messagesize)
        {
          big8 = checksum;
        }
        
        test = ((int)(big8 & k));
        
        privateKeyOTS = gmssRandom.nextSeed(seed);


      }
      else if (test > 0)
      {

        messDigestOTS.update(privateKeyOTS, 0, privateKeyOTS.length);
        privateKeyOTS = new byte[messDigestOTS.getDigestSize()];
        messDigestOTS.doFinal(privateKeyOTS, 0);
        test -= 1;
      }
      if (test == 0)
      {

        System.arraycopy(privateKeyOTS, 0, sign, counter * mdsize, mdsize);
        
        big8 >>>= w;
        counter += 1;
      }
      

    }
    else if (w < 57)
    {

      if (test8 == 0L)
      {

        big8 = 0L;
        ii = 0;
        int rest = r % 8;
        int s = r >>> 3;
        
        if (s < mdsize) { int f;
          int f;
          if (r <= (mdsize << 3) - w)
          {
            r += w;
            f = r + 7 >>> 3;
          }
          else
          {
            f = mdsize;
            r += w;
          }
          

          for (int i = s; i < f; i++)
          {
            big8 ^= (hash[i] & 0xFF) << (ii << 3);
            ii += 1;
          }
          

          big8 >>>= rest;
          test8 = (big8 & k);

        }
        else
        {
          test8 = (checksum & k);
          checksum >>>= w;
        }
        
        privateKeyOTS = gmssRandom.nextSeed(seed);


      }
      else if (test8 > 0L)
      {

        messDigestOTS.update(privateKeyOTS, 0, privateKeyOTS.length);
        privateKeyOTS = new byte[messDigestOTS.getDigestSize()];
        messDigestOTS.doFinal(privateKeyOTS, 0);
        test8 -= 1L;
      }
      if (test8 == 0L)
      {

        System.arraycopy(privateKeyOTS, 0, sign, counter * mdsize, mdsize);
        
        counter += 1;
      }
    }
  }
  









  public int getLog(int intValue)
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
    byte[][] statByte = new byte[5][mdsize];
    statByte[0] = privateKeyOTS;
    statByte[1] = seed;
    statByte[2] = hash;
    statByte[3] = sign;
    statByte[4] = getStatLong();
    
    return statByte;
  }
  





  public int[] getStatInt()
  {
    int[] statInt = new int[9];
    statInt[0] = counter;
    statInt[1] = test;
    statInt[2] = ii;
    statInt[3] = r;
    statInt[4] = steps;
    statInt[5] = keysize;
    statInt[6] = height;
    statInt[7] = w;
    statInt[8] = checksum;
    return statInt;
  }
  




  public byte[] getStatLong()
  {
    byte[] bytes = new byte[16];
    
    bytes[0] = ((byte)(int)(test8 & 0xFF));
    bytes[1] = ((byte)(int)(test8 >> 8 & 0xFF));
    bytes[2] = ((byte)(int)(test8 >> 16 & 0xFF));
    bytes[3] = ((byte)(int)(test8 >> 24 & 0xFF));
    bytes[4] = ((byte)(int)(test8 >> 32 & 0xFF));
    bytes[5] = ((byte)(int)(test8 >> 40 & 0xFF));
    bytes[6] = ((byte)(int)(test8 >> 48 & 0xFF));
    bytes[7] = ((byte)(int)(test8 >> 56 & 0xFF));
    
    bytes[8] = ((byte)(int)(big8 & 0xFF));
    bytes[9] = ((byte)(int)(big8 >> 8 & 0xFF));
    bytes[10] = ((byte)(int)(big8 >> 16 & 0xFF));
    bytes[11] = ((byte)(int)(big8 >> 24 & 0xFF));
    bytes[12] = ((byte)(int)(big8 >> 32 & 0xFF));
    bytes[13] = ((byte)(int)(big8 >> 40 & 0xFF));
    bytes[14] = ((byte)(int)(big8 >> 48 & 0xFF));
    bytes[15] = ((byte)(int)(big8 >> 56 & 0xFF));
    
    return bytes;
  }
  





  public String toString()
  {
    String out = "" + big8 + "  ";
    int[] statInt = new int[9];
    statInt = getStatInt();
    byte[][] statByte = new byte[5][mdsize];
    statByte = getStatByte();
    for (int i = 0; i < 9; i++)
    {
      out = out + statInt[i] + " ";
    }
    for (int i = 0; i < 5; i++)
    {
      out = out + new String(Hex.encode(statByte[i])) + " ";
    }
    
    return out;
  }
}
