package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;




public class VMPCEngine
  implements StreamCipher
{
  protected byte n = 0;
  protected byte[] P = null;
  protected byte s = 0;
  protected byte[] workingIV;
  protected byte[] workingKey;
  
  public VMPCEngine() {}
  
  public String getAlgorithmName() {
    return "VMPC";
  }
  










  public void init(boolean forEncryption, CipherParameters params)
  {
    if (!(params instanceof ParametersWithIV))
    {
      throw new IllegalArgumentException("VMPC init parameters must include an IV");
    }
    

    ParametersWithIV ivParams = (ParametersWithIV)params;
    
    if (!(ivParams.getParameters() instanceof KeyParameter))
    {
      throw new IllegalArgumentException("VMPC init parameters must include a key");
    }
    

    KeyParameter key = (KeyParameter)ivParams.getParameters();
    
    workingIV = ivParams.getIV();
    
    if ((workingIV == null) || (workingIV.length < 1) || (workingIV.length > 768))
    {
      throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
    }
    
    workingKey = key.getKey();
    
    initKey(workingKey, workingIV);
  }
  
  protected void initKey(byte[] keyBytes, byte[] ivBytes)
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
  

  public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff)
  {
    if (inOff + len > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + len > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    for (int i = 0; i < len; i++)
    {
      s = P[(s + P[(n & 0xFF)] & 0xFF)];
      byte z = P[(P[(P[(s & 0xFF)] & 0xFF)] + 1 & 0xFF)];
      
      byte temp = P[(n & 0xFF)];
      P[(n & 0xFF)] = P[(s & 0xFF)];
      P[(s & 0xFF)] = temp;
      n = ((byte)(n + 1 & 0xFF));
      

      out[(i + outOff)] = ((byte)(in[(i + inOff)] ^ z));
    }
    
    return len;
  }
  
  public void reset()
  {
    initKey(workingKey, workingIV);
  }
  
  public byte returnByte(byte in)
  {
    s = P[(s + P[(n & 0xFF)] & 0xFF)];
    byte z = P[(P[(P[(s & 0xFF)] & 0xFF)] + 1 & 0xFF)];
    
    byte temp = P[(n & 0xFF)];
    P[(n & 0xFF)] = P[(s & 0xFF)];
    P[(s & 0xFF)] = temp;
    n = ((byte)(n + 1 & 0xFF));
    

    return (byte)(in ^ z);
  }
}
