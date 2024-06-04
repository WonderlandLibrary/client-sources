package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.MacDerivationFunction;
import org.spongycastle.crypto.params.KDFCounterParameters;
import org.spongycastle.crypto.params.KeyParameter;































public class KDFCounterBytesGenerator
  implements MacDerivationFunction
{
  private static final BigInteger INTEGER_MAX = BigInteger.valueOf(2147483647L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  

  private final Mac prf;
  

  private final int h;
  

  private byte[] fixedInputDataCtrPrefix;
  
  private byte[] fixedInputData_afterCtr;
  
  private int maxSizeExcl;
  
  private byte[] ios;
  
  private int generatedBytes;
  
  private byte[] k;
  

  public KDFCounterBytesGenerator(Mac prf)
  {
    this.prf = prf;
    h = prf.getMacSize();
    k = new byte[h];
  }
  

  public void init(DerivationParameters param)
  {
    if (!(param instanceof KDFCounterParameters))
    {
      throw new IllegalArgumentException("Wrong type of arguments given");
    }
    
    KDFCounterParameters kdfParams = (KDFCounterParameters)param;
    


    prf.init(new KeyParameter(kdfParams.getKI()));
    


    fixedInputDataCtrPrefix = kdfParams.getFixedInputDataCounterPrefix();
    fixedInputData_afterCtr = kdfParams.getFixedInputDataCounterSuffix();
    
    int r = kdfParams.getR();
    ios = new byte[r / 8];
    
    BigInteger maxSize = TWO.pow(r).multiply(BigInteger.valueOf(h));
    
    maxSizeExcl = (maxSize.compareTo(INTEGER_MAX) == 1 ? Integer.MAX_VALUE : maxSize.intValue());
    


    generatedBytes = 0;
  }
  

  public Mac getMac()
  {
    return prf;
  }
  

  public int generateBytes(byte[] out, int outOff, int len)
    throws DataLengthException, IllegalArgumentException
  {
    int generatedBytesAfter = generatedBytes + len;
    if ((generatedBytesAfter < 0) || (generatedBytesAfter >= maxSizeExcl))
    {
      throw new DataLengthException("Current KDFCTR may only be used for " + maxSizeExcl + " bytes");
    }
    

    if (generatedBytes % h == 0)
    {
      generateNext();
    }
    

    int toGenerate = len;
    int posInK = generatedBytes % h;
    int leftInK = h - generatedBytes % h;
    int toCopy = Math.min(leftInK, toGenerate);
    System.arraycopy(k, posInK, out, outOff, toCopy);
    generatedBytes += toCopy;
    toGenerate -= toCopy;
    outOff += toCopy;
    
    while (toGenerate > 0)
    {
      generateNext();
      toCopy = Math.min(h, toGenerate);
      System.arraycopy(k, 0, out, outOff, toCopy);
      generatedBytes += toCopy;
      toGenerate -= toCopy;
      outOff += toCopy;
    }
    
    return len;
  }
  
  private void generateNext()
  {
    int i = generatedBytes / h + 1;
    

    switch (ios.length)
    {
    case 4: 
      ios[0] = ((byte)(i >>> 24));
    
    case 3: 
      ios[(ios.length - 3)] = ((byte)(i >>> 16));
    
    case 2: 
      ios[(ios.length - 2)] = ((byte)(i >>> 8));
    
    case 1: 
      ios[(ios.length - 1)] = ((byte)i);
      break;
    default: 
      throw new IllegalStateException("Unsupported size of counter i");
    }
    
    

    prf.update(fixedInputDataCtrPrefix, 0, fixedInputDataCtrPrefix.length);
    prf.update(ios, 0, ios.length);
    prf.update(fixedInputData_afterCtr, 0, fixedInputData_afterCtr.length);
    prf.doFinal(k, 0);
  }
}
