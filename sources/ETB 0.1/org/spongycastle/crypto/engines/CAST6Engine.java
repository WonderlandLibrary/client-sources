package org.spongycastle.crypto.engines;








public final class CAST6Engine
  extends CAST5Engine
{
  protected static final int ROUNDS = 12;
  






  protected static final int BLOCK_SIZE = 16;
  





  protected int[] _Kr = new int[48];
  protected int[] _Km = new int[48];
  



  protected int[] _Tr = new int['À'];
  protected int[] _Tm = new int['À'];
  
  private int[] _workingKey = new int[8];
  

  public CAST6Engine() {}
  

  public String getAlgorithmName()
  {
    return "CAST6";
  }
  

  public void reset() {}
  

  public int getBlockSize()
  {
    return 16;
  }
  










  protected void setKey(byte[] key)
  {
    int Cm = 1518500249;
    int Mm = 1859775393;
    int Cr = 19;
    int Mr = 17;
    







    for (int i = 0; i < 24; i++)
    {
      for (int j = 0; j < 8; j++)
      {
        _Tm[(i * 8 + j)] = Cm;
        Cm += Mm;
        
        _Tr[(i * 8 + j)] = Cr;
        Cr = Cr + Mr & 0x1F;
      }
    }
    
    byte[] tmpKey = new byte[64];
    int length = key.length;
    System.arraycopy(key, 0, tmpKey, 0, length);
    

    for (int i = 0; i < 8; i++)
    {
      _workingKey[i] = BytesTo32bits(tmpKey, i * 4);
    }
    

    for (int i = 0; i < 12; i++)
    {

      int i2 = i * 2 * 8;
      _workingKey[6] ^= F1(_workingKey[7], _Tm[i2], _Tr[i2]);
      _workingKey[5] ^= F2(_workingKey[6], _Tm[(i2 + 1)], _Tr[(i2 + 1)]);
      _workingKey[4] ^= F3(_workingKey[5], _Tm[(i2 + 2)], _Tr[(i2 + 2)]);
      _workingKey[3] ^= F1(_workingKey[4], _Tm[(i2 + 3)], _Tr[(i2 + 3)]);
      _workingKey[2] ^= F2(_workingKey[3], _Tm[(i2 + 4)], _Tr[(i2 + 4)]);
      _workingKey[1] ^= F3(_workingKey[2], _Tm[(i2 + 5)], _Tr[(i2 + 5)]);
      _workingKey[0] ^= F1(_workingKey[1], _Tm[(i2 + 6)], _Tr[(i2 + 6)]);
      _workingKey[7] ^= F2(_workingKey[0], _Tm[(i2 + 7)], _Tr[(i2 + 7)]);
      

      i2 = (i * 2 + 1) * 8;
      _workingKey[6] ^= F1(_workingKey[7], _Tm[i2], _Tr[i2]);
      _workingKey[5] ^= F2(_workingKey[6], _Tm[(i2 + 1)], _Tr[(i2 + 1)]);
      _workingKey[4] ^= F3(_workingKey[5], _Tm[(i2 + 2)], _Tr[(i2 + 2)]);
      _workingKey[3] ^= F1(_workingKey[4], _Tm[(i2 + 3)], _Tr[(i2 + 3)]);
      _workingKey[2] ^= F2(_workingKey[3], _Tm[(i2 + 4)], _Tr[(i2 + 4)]);
      _workingKey[1] ^= F3(_workingKey[2], _Tm[(i2 + 5)], _Tr[(i2 + 5)]);
      _workingKey[0] ^= F1(_workingKey[1], _Tm[(i2 + 6)], _Tr[(i2 + 6)]);
      _workingKey[7] ^= F2(_workingKey[0], _Tm[(i2 + 7)], _Tr[(i2 + 7)]);
      

      _Kr[(i * 4)] = (_workingKey[0] & 0x1F);
      _Kr[(i * 4 + 1)] = (_workingKey[2] & 0x1F);
      _Kr[(i * 4 + 2)] = (_workingKey[4] & 0x1F);
      _Kr[(i * 4 + 3)] = (_workingKey[6] & 0x1F);
      


      _Km[(i * 4)] = _workingKey[7];
      _Km[(i * 4 + 1)] = _workingKey[5];
      _Km[(i * 4 + 2)] = _workingKey[3];
      _Km[(i * 4 + 3)] = _workingKey[1];
    }
  }
  















  protected int encryptBlock(byte[] src, int srcIndex, byte[] dst, int dstIndex)
  {
    int[] result = new int[4];
    



    int A = BytesTo32bits(src, srcIndex);
    int B = BytesTo32bits(src, srcIndex + 4);
    int C = BytesTo32bits(src, srcIndex + 8);
    int D = BytesTo32bits(src, srcIndex + 12);
    
    CAST_Encipher(A, B, C, D, result);
    

    Bits32ToBytes(result[0], dst, dstIndex);
    Bits32ToBytes(result[1], dst, dstIndex + 4);
    Bits32ToBytes(result[2], dst, dstIndex + 8);
    Bits32ToBytes(result[3], dst, dstIndex + 12);
    
    return 16;
  }
  













  protected int decryptBlock(byte[] src, int srcIndex, byte[] dst, int dstIndex)
  {
    int[] result = new int[4];
    


    int A = BytesTo32bits(src, srcIndex);
    int B = BytesTo32bits(src, srcIndex + 4);
    int C = BytesTo32bits(src, srcIndex + 8);
    int D = BytesTo32bits(src, srcIndex + 12);
    
    CAST_Decipher(A, B, C, D, result);
    

    Bits32ToBytes(result[0], dst, dstIndex);
    Bits32ToBytes(result[1], dst, dstIndex + 4);
    Bits32ToBytes(result[2], dst, dstIndex + 8);
    Bits32ToBytes(result[3], dst, dstIndex + 12);
    
    return 16;
  }
  










  protected final void CAST_Encipher(int A, int B, int C, int D, int[] result)
  {
    for (int i = 0; i < 6; i++)
    {
      int x = i * 4;
      
      C ^= F1(D, _Km[x], _Kr[x]);
      B ^= F2(C, _Km[(x + 1)], _Kr[(x + 1)]);
      A ^= F3(B, _Km[(x + 2)], _Kr[(x + 2)]);
      D ^= F1(A, _Km[(x + 3)], _Kr[(x + 3)]);
    }
    

    for (int i = 6; i < 12; i++)
    {
      int x = i * 4;
      
      D ^= F1(A, _Km[(x + 3)], _Kr[(x + 3)]);
      A ^= F3(B, _Km[(x + 2)], _Kr[(x + 2)]);
      B ^= F2(C, _Km[(x + 1)], _Kr[(x + 1)]);
      C ^= F1(D, _Km[x], _Kr[x]);
    }
    

    result[0] = A;
    result[1] = B;
    result[2] = C;
    result[3] = D;
  }
  










  protected final void CAST_Decipher(int A, int B, int C, int D, int[] result)
  {
    for (int i = 0; i < 6; i++)
    {
      int x = (11 - i) * 4;
      
      C ^= F1(D, _Km[x], _Kr[x]);
      B ^= F2(C, _Km[(x + 1)], _Kr[(x + 1)]);
      A ^= F3(B, _Km[(x + 2)], _Kr[(x + 2)]);
      D ^= F1(A, _Km[(x + 3)], _Kr[(x + 3)]);
    }
    

    for (int i = 6; i < 12; i++)
    {
      int x = (11 - i) * 4;
      
      D ^= F1(A, _Km[(x + 3)], _Kr[(x + 3)]);
      A ^= F3(B, _Km[(x + 2)], _Kr[(x + 2)]);
      B ^= F2(C, _Km[(x + 1)], _Kr[(x + 1)]);
      C ^= F1(D, _Km[x], _Kr[x]);
    }
    

    result[0] = A;
    result[1] = B;
    result[2] = C;
    result[3] = D;
  }
}
