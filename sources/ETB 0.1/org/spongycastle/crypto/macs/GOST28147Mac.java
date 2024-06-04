package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithSBox;




public class GOST28147Mac
  implements Mac
{
  private int blockSize = 8;
  private int macSize = 4;
  private int bufOff;
  private byte[] buf;
  private byte[] mac;
  private boolean firstStep = true;
  private int[] workingKey = null;
  private byte[] macIV = null;
  


  private byte[] S = { 9, 6, 3, 2, 8, 11, 1, 7, 10, 4, 14, 15, 12, 0, 13, 5, 3, 7, 14, 9, 8, 10, 15, 0, 5, 2, 6, 12, 11, 4, 13, 1, 14, 4, 6, 2, 11, 3, 13, 8, 12, 15, 5, 10, 0, 7, 1, 9, 14, 7, 10, 12, 13, 1, 3, 9, 0, 2, 11, 4, 15, 8, 5, 6, 11, 5, 1, 9, 8, 13, 15, 0, 14, 4, 2, 3, 12, 7, 10, 6, 3, 10, 13, 12, 1, 2, 0, 11, 7, 5, 9, 4, 8, 15, 14, 6, 1, 13, 2, 9, 7, 10, 6, 0, 8, 12, 4, 5, 15, 3, 11, 14, 11, 10, 15, 5, 0, 12, 14, 8, 6, 2, 3, 9, 1, 7, 13, 4 };
  









  public GOST28147Mac()
  {
    mac = new byte[blockSize];
    
    buf = new byte[blockSize];
    bufOff = 0;
  }
  

  private int[] generateWorkingKey(byte[] userKey)
  {
    if (userKey.length != 32)
    {
      throw new IllegalArgumentException("Key length invalid. Key needs to be 32 byte - 256 bit!!!");
    }
    
    int[] key = new int[8];
    for (int i = 0; i != 8; i++)
    {
      key[i] = bytesToint(userKey, i * 4);
    }
    
    return key;
  }
  

  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    reset();
    buf = new byte[blockSize];
    macIV = null;
    if ((params instanceof ParametersWithSBox))
    {
      ParametersWithSBox param = (ParametersWithSBox)params;
      



      System.arraycopy(param.getSBox(), 0, S, 0, param.getSBox().length);
      



      if (param.getParameters() != null)
      {
        workingKey = generateWorkingKey(((KeyParameter)param.getParameters()).getKey());
      }
    }
    else if ((params instanceof KeyParameter))
    {
      workingKey = generateWorkingKey(((KeyParameter)params).getKey());
    }
    else if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV p = (ParametersWithIV)params;
      
      workingKey = generateWorkingKey(((KeyParameter)p.getParameters()).getKey());
      System.arraycopy(p.getIV(), 0, mac, 0, mac.length);
      macIV = p.getIV();
    }
    else
    {
      throw new IllegalArgumentException("invalid parameter passed to GOST28147 init - " + params.getClass().getName());
    }
  }
  
  public String getAlgorithmName()
  {
    return "GOST28147Mac";
  }
  
  public int getMacSize()
  {
    return macSize;
  }
  
  private int gost28147_mainStep(int n1, int key)
  {
    int cm = key + n1;
    


    int om = S[(0 + (cm >> 0 & 0xF))] << 0;
    om += (S[(16 + (cm >> 4 & 0xF))] << 4);
    om += (S[(32 + (cm >> 8 & 0xF))] << 8);
    om += (S[(48 + (cm >> 12 & 0xF))] << 12);
    om += (S[(64 + (cm >> 16 & 0xF))] << 16);
    om += (S[(80 + (cm >> 20 & 0xF))] << 20);
    om += (S[(96 + (cm >> 24 & 0xF))] << 24);
    om += (S[(112 + (cm >> 28 & 0xF))] << 28);
    
    return om << 11 | om >>> 21;
  }
  






  private void gost28147MacFunc(int[] workingKey, byte[] in, int inOff, byte[] out, int outOff)
  {
    int N1 = bytesToint(in, inOff);
    int N2 = bytesToint(in, inOff + 4);
    
    for (int k = 0; k < 2; k++)
    {
      for (int j = 0; j < 8; j++)
      {
        int tmp = N1;
        N1 = N2 ^ gost28147_mainStep(N1, workingKey[j]);
        N2 = tmp;
      }
    }
    
    intTobytes(N1, out, outOff);
    intTobytes(N2, out, outOff + 4);
  }
  



  private int bytesToint(byte[] in, int inOff)
  {
    return (in[(inOff + 3)] << 24 & 0xFF000000) + (in[(inOff + 2)] << 16 & 0xFF0000) + (in[(inOff + 1)] << 8 & 0xFF00) + (in[inOff] & 0xFF);
  }
  





  private void intTobytes(int num, byte[] out, int outOff)
  {
    out[(outOff + 3)] = ((byte)(num >>> 24));
    out[(outOff + 2)] = ((byte)(num >>> 16));
    out[(outOff + 1)] = ((byte)(num >>> 8));
    out[outOff] = ((byte)num);
  }
  
  private byte[] CM5func(byte[] buf, int bufOff, byte[] mac)
  {
    byte[] sum = new byte[buf.length - bufOff];
    
    System.arraycopy(buf, bufOff, sum, 0, mac.length);
    
    for (int i = 0; i != mac.length; i++)
    {
      sum[i] = ((byte)(sum[i] ^ mac[i]));
    }
    
    return sum;
  }
  
  public void update(byte in)
    throws IllegalStateException
  {
    if (bufOff == buf.length)
    {
      byte[] sumbuf = new byte[buf.length];
      System.arraycopy(buf, 0, sumbuf, 0, mac.length);
      
      if (firstStep)
      {
        firstStep = false;
        if (macIV != null)
        {
          sumbuf = CM5func(buf, 0, macIV);
        }
      }
      else
      {
        sumbuf = CM5func(buf, 0, mac);
      }
      
      gost28147MacFunc(workingKey, sumbuf, 0, mac, 0);
      bufOff = 0;
    }
    
    buf[(bufOff++)] = in;
  }
  
  public void update(byte[] in, int inOff, int len)
    throws DataLengthException, IllegalStateException
  {
    if (len < 0)
    {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    
    int gapLen = blockSize - bufOff;
    
    if (len > gapLen)
    {
      System.arraycopy(in, inOff, buf, bufOff, gapLen);
      
      byte[] sumbuf = new byte[buf.length];
      System.arraycopy(buf, 0, sumbuf, 0, mac.length);
      
      if (firstStep)
      {
        firstStep = false;
        if (macIV != null)
        {
          sumbuf = CM5func(buf, 0, macIV);
        }
      }
      else
      {
        sumbuf = CM5func(buf, 0, mac);
      }
      
      gost28147MacFunc(workingKey, sumbuf, 0, mac, 0);
      
      bufOff = 0;
      len -= gapLen;
      inOff += gapLen;
      
      while (len > blockSize)
      {
        sumbuf = CM5func(in, inOff, mac);
        gost28147MacFunc(workingKey, sumbuf, 0, mac, 0);
        
        len -= blockSize;
        inOff += blockSize;
      }
    }
    
    System.arraycopy(in, inOff, buf, bufOff, len);
    
    bufOff += len;
  }
  

  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    while (bufOff < blockSize)
    {
      buf[bufOff] = 0;
      bufOff += 1;
    }
    
    byte[] sumbuf = new byte[buf.length];
    System.arraycopy(buf, 0, sumbuf, 0, mac.length);
    
    if (firstStep)
    {
      firstStep = false;
    }
    else
    {
      sumbuf = CM5func(buf, 0, mac);
    }
    
    gost28147MacFunc(workingKey, sumbuf, 0, mac, 0);
    
    System.arraycopy(mac, mac.length / 2 - macSize, out, outOff, macSize);
    
    reset();
    
    return macSize;
  }
  



  public void reset()
  {
    for (int i = 0; i < buf.length; i++)
    {
      buf[i] = 0;
    }
    
    bufOff = 0;
    
    firstStep = true;
  }
}
