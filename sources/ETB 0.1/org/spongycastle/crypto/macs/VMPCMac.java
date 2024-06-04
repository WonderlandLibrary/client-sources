package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class VMPCMac
  implements Mac
{
  private byte g;
  private byte n = 0;
  private byte[] P = null;
  private byte s = 0;
  private byte[] T;
  private byte[] workingIV;
  private byte[] workingKey;
  private byte x1;
  private byte x2;
  private byte x3;
  private byte x4;
  
  public VMPCMac() {}
  
  public int doFinal(byte[] out, int outOff) throws DataLengthException, IllegalStateException
  {
    for (int r = 1; r < 25; r++)
    {
      s = P[(s + P[(n & 0xFF)] & 0xFF)];
      
      x4 = P[(x4 + x3 + r & 0xFF)];
      x3 = P[(x3 + x2 + r & 0xFF)];
      x2 = P[(x2 + x1 + r & 0xFF)];
      x1 = P[(x1 + s + r & 0xFF)];
      T[(g & 0x1F)] = ((byte)(T[(g & 0x1F)] ^ x1));
      T[(g + 1 & 0x1F)] = ((byte)(T[(g + 1 & 0x1F)] ^ x2));
      T[(g + 2 & 0x1F)] = ((byte)(T[(g + 2 & 0x1F)] ^ x3));
      T[(g + 3 & 0x1F)] = ((byte)(T[(g + 3 & 0x1F)] ^ x4));
      g = ((byte)(g + 4 & 0x1F));
      
      byte temp = P[(n & 0xFF)];
      P[(n & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
      n = ((byte)(n + 1 & 0xFF));
    }
    

    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + T[(m & 0x1F)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    

    byte[] M = new byte[20];
    for (int i = 0; i < 20; i++)
    {
      s = P[(s + P[(i & 0xFF)] & 0xFF)];
      M[i] = P[(P[(P[(s & 0xFF)] & 0xFF)] + 1 & 0xFF)];
      
      byte temp = P[(i & 0xFF)];
      P[(i & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    
    System.arraycopy(M, 0, out, outOff, M.length);
    reset();
    
    return M.length;
  }
  
  public String getAlgorithmName()
  {
    return "VMPC-MAC";
  }
  
  public int getMacSize()
  {
    return 20;
  }
  
  public void init(CipherParameters params) throws IllegalArgumentException
  {
    if (!(params instanceof ParametersWithIV))
    {
      throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
    }
    

    ParametersWithIV ivParams = (ParametersWithIV)params;
    KeyParameter key = (KeyParameter)ivParams.getParameters();
    
    if (!(ivParams.getParameters() instanceof KeyParameter))
    {
      throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
    }
    

    workingIV = ivParams.getIV();
    
    if ((workingIV == null) || (workingIV.length < 1) || (workingIV.length > 768))
    {
      throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
    }
    

    workingKey = key.getKey();
    
    reset();
  }
  

  private void initKey(byte[] keyBytes, byte[] ivBytes)
  {
    s = 0;
    P = new byte['Ā'];
    for (int i = 0; i < 256; i++)
    {
      P[i] = ((byte)i);
    }
    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + keyBytes[(m % keyBytes.length)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    for (int m = 0; m < 768; m++)
    {
      s = P[(s + P[(m & 0xFF)] + ivBytes[(m % ivBytes.length)] & 0xFF)];
      byte temp = P[(m & 0xFF)];
      P[(m & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
    }
    n = 0;
  }
  
  public void reset()
  {
    initKey(workingKey, workingIV);
    g = (this.x1 = this.x2 = this.x3 = this.x4 = this.n = 0);
    T = new byte[32];
    for (int i = 0; i < 32; i++)
    {
      T[i] = 0;
    }
  }
  
  public void update(byte in) throws IllegalStateException
  {
    s = P[(s + P[(n & 0xFF)] & 0xFF)];
    byte c = (byte)(in ^ P[(P[(P[(s & 0xFF)] & 0xFF)] + 1 & 0xFF)]);
    
    x4 = P[(x4 + x3 & 0xFF)];
    x3 = P[(x3 + x2 & 0xFF)];
    x2 = P[(x2 + x1 & 0xFF)];
    x1 = P[(x1 + s + c & 0xFF)];
    T[(g & 0x1F)] = ((byte)(T[(g & 0x1F)] ^ x1));
    T[(g + 1 & 0x1F)] = ((byte)(T[(g + 1 & 0x1F)] ^ x2));
    T[(g + 2 & 0x1F)] = ((byte)(T[(g + 2 & 0x1F)] ^ x3));
    T[(g + 3 & 0x1F)] = ((byte)(T[(g + 3 & 0x1F)] ^ x4));
    g = ((byte)(g + 4 & 0x1F));
    
    byte temp = P[(n & 0xFF)];
    P[(n & 0xFF)] = P[(s & 0xFF)];
    P[(s & 0xFF)] = temp;
    n = ((byte)(n + 1 & 0xFF));
  }
  
  public void update(byte[] in, int inOff, int len)
    throws DataLengthException, IllegalStateException
  {
    if (inOff + len > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    for (int i = 0; i < len; i++)
    {
      update(in[(inOff + i)]);
    }
  }
}
