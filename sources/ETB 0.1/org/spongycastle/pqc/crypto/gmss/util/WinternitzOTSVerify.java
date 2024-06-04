package org.spongycastle.pqc.crypto.gmss.util;

import org.spongycastle.crypto.Digest;



















public class WinternitzOTSVerify
{
  private Digest messDigestOTS;
  private int w;
  
  public WinternitzOTSVerify(Digest digest, int w)
  {
    this.w = w;
    
    messDigestOTS = digest;
  }
  



  public int getSignatureLength()
  {
    int mdsize = messDigestOTS.getDigestSize();
    int size = ((mdsize << 3) + (w - 1)) / w;
    int logs = getLog((size << w) + 1);
    size += (logs + w - 1) / w;
    
    return mdsize * size;
  }
  










  public byte[] Verify(byte[] message, byte[] signature)
  {
    int mdsize = messDigestOTS.getDigestSize();
    byte[] hash = new byte[mdsize];
    

    messDigestOTS.update(message, 0, message.length);
    hash = new byte[messDigestOTS.getDigestSize()];
    messDigestOTS.doFinal(hash, 0);
    
    int size = ((mdsize << 3) + (w - 1)) / w;
    int logs = getLog((size << w) + 1);
    int keysize = size + (logs + w - 1) / w;
    
    int testKeySize = mdsize * keysize;
    
    if (testKeySize != signature.length)
    {
      return null;
    }
    
    byte[] testKey = new byte[testKeySize];
    
    int c = 0;
    int counter = 0;
    

    if (8 % w == 0)
    {
      int d = 8 / w;
      int k = (1 << w) - 1;
      byte[] hlp = new byte[mdsize];
      

      for (int i = 0; i < hash.length; i++)
      {
        for (int j = 0; j < d; j++)
        {
          int test = hash[i] & k;
          c += test;
          
          System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
          
          while (test < k)
          {
            messDigestOTS.update(hlp, 0, hlp.length);
            hlp = new byte[messDigestOTS.getDigestSize()];
            messDigestOTS.doFinal(hlp, 0);
            test++;
          }
          
          System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
          hash[i] = ((byte)(hash[i] >>> w));
          counter++;
        }
      }
      
      c = (size << w) - c;
      for (int i = 0; i < logs; i += w)
      {
        int test = c & k;
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test++;
        }
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        c >>>= w;
        counter++;
      }
    }
    else if (w < 8)
    {
      int d = mdsize / w;
      int k = (1 << w) - 1;
      byte[] hlp = new byte[mdsize];
      
      int ii = 0;
      

      for (int i = 0; i < d; i++)
      {
        long big8 = 0L;
        for (int j = 0; j < w; j++)
        {
          big8 ^= (hash[ii] & 0xFF) << (j << 3);
          ii++;
        }
        for (int j = 0; j < 8; j++)
        {
          int test = (int)(big8 & k);
          c += test;
          
          System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
          
          while (test < k)
          {
            messDigestOTS.update(hlp, 0, hlp.length);
            hlp = new byte[messDigestOTS.getDigestSize()];
            messDigestOTS.doFinal(hlp, 0);
            test++;
          }
          
          System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
          big8 >>>= w;
          counter++;
        }
      }
      
      d = mdsize % w;
      long big8 = 0L;
      for (int j = 0; j < d; j++)
      {
        big8 ^= (hash[ii] & 0xFF) << (j << 3);
        ii++;
      }
      d <<= 3;
      for (int j = 0; j < d; j += w)
      {
        int test = (int)(big8 & k);
        c += test;
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test++;
        }
        
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        big8 >>>= w;
        counter++;
      }
      

      c = (size << w) - c;
      for (int i = 0; i < logs; i += w)
      {
        int test = c & k;
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test++;
        }
        
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        c >>>= w;
        counter++;
      }
    }
    else if (w < 57)
    {
      int d = (mdsize << 3) - w;
      int k = (1 << w) - 1;
      byte[] hlp = new byte[mdsize];
      
      int r = 0;
      


      while (r <= d)
      {
        int s = r >>> 3;
        int rest = r % 8;
        r += w;
        int f = r + 7 >>> 3;
        long big8 = 0L;
        int ii = 0;
        for (int j = s; j < f; j++)
        {
          big8 ^= (hash[j] & 0xFF) << (ii << 3);
          ii++;
        }
        
        big8 >>>= rest;
        long test8 = big8 & k;
        c = (int)(c + test8);
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test8 < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test8 += 1L;
        }
        
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        counter++;
      }
      

      int s = r >>> 3;
      if (s < mdsize)
      {
        int rest = r % 8;
        long big8 = 0L;
        int ii = 0;
        for (int j = s; j < mdsize; j++)
        {
          big8 ^= (hash[j] & 0xFF) << (ii << 3);
          ii++;
        }
        
        big8 >>>= rest;
        long test8 = big8 & k;
        c = (int)(c + test8);
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test8 < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test8 += 1L;
        }
        
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        counter++;
      }
      
      c = (size << w) - c;
      for (int i = 0; i < logs; i += w)
      {
        long test8 = c & k;
        
        System.arraycopy(signature, counter * mdsize, hlp, 0, mdsize);
        
        while (test8 < k)
        {
          messDigestOTS.update(hlp, 0, hlp.length);
          hlp = new byte[messDigestOTS.getDigestSize()];
          messDigestOTS.doFinal(hlp, 0);
          test8 += 1L;
        }
        
        System.arraycopy(hlp, 0, testKey, counter * mdsize, mdsize);
        c >>>= w;
        counter++;
      }
    }
    
    byte[] TKey = new byte[mdsize];
    messDigestOTS.update(testKey, 0, testKey.length);
    TKey = new byte[messDigestOTS.getDigestSize()];
    messDigestOTS.doFinal(TKey, 0);
    
    return TKey;
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
}
