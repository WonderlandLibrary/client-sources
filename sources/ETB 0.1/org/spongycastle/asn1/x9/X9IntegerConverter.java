package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;











public class X9IntegerConverter
{
  public X9IntegerConverter() {}
  
  public int getByteLength(ECCurve c)
  {
    return (c.getFieldSize() + 7) / 8;
  }
  







  public int getByteLength(ECFieldElement fe)
  {
    return (fe.getFieldSize() + 7) / 8;
  }
  









  public byte[] integerToBytes(BigInteger s, int qLength)
  {
    byte[] bytes = s.toByteArray();
    
    if (qLength < bytes.length)
    {
      byte[] tmp = new byte[qLength];
      
      System.arraycopy(bytes, bytes.length - tmp.length, tmp, 0, tmp.length);
      
      return tmp;
    }
    if (qLength > bytes.length)
    {
      byte[] tmp = new byte[qLength];
      
      System.arraycopy(bytes, 0, tmp, tmp.length - bytes.length, bytes.length);
      
      return tmp;
    }
    
    return bytes;
  }
}
