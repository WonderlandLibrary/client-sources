package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ElGamalKeyParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.BigIntegers;





public class ElGamalEngine
  implements AsymmetricBlockCipher
{
  private ElGamalKeyParameters key;
  private SecureRandom random;
  private boolean forEncryption;
  private int bitSize;
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  



  public ElGamalEngine() {}
  



  public void init(boolean forEncryption, CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom p = (ParametersWithRandom)param;
      
      key = ((ElGamalKeyParameters)p.getParameters());
      random = p.getRandom();
    }
    else
    {
      key = ((ElGamalKeyParameters)param);
      random = new SecureRandom();
    }
    
    this.forEncryption = forEncryption;
    
    BigInteger p = key.getParameters().getP();
    
    bitSize = p.bitLength();
    
    if (forEncryption)
    {
      if (!(key instanceof ElGamalPublicKeyParameters))
      {
        throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
      }
      

    }
    else if (!(key instanceof ElGamalPrivateKeyParameters))
    {
      throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
    }
  }
  








  public int getInputBlockSize()
  {
    if (forEncryption)
    {
      return (bitSize - 1) / 8;
    }
    
    return 2 * ((bitSize + 7) / 8);
  }
  







  public int getOutputBlockSize()
  {
    if (forEncryption)
    {
      return 2 * ((bitSize + 7) / 8);
    }
    
    return (bitSize - 1) / 8;
  }
  












  public byte[] processBlock(byte[] in, int inOff, int inLen)
  {
    if (key == null)
    {
      throw new IllegalStateException("ElGamal engine not initialised");
    }
    


    int maxLength = forEncryption ? (bitSize - 1 + 7) / 8 : getInputBlockSize();
    
    if (inLen > maxLength)
    {
      throw new DataLengthException("input too large for ElGamal cipher.\n");
    }
    
    BigInteger p = key.getParameters().getP();
    
    if ((key instanceof ElGamalPrivateKeyParameters))
    {
      byte[] in1 = new byte[inLen / 2];
      byte[] in2 = new byte[inLen / 2];
      
      System.arraycopy(in, inOff, in1, 0, in1.length);
      System.arraycopy(in, inOff + in1.length, in2, 0, in2.length);
      
      BigInteger gamma = new BigInteger(1, in1);
      BigInteger phi = new BigInteger(1, in2);
      
      ElGamalPrivateKeyParameters priv = (ElGamalPrivateKeyParameters)key;
      

      BigInteger m = gamma.modPow(p.subtract(ONE).subtract(priv.getX()), p).multiply(phi).mod(p);
      
      return BigIntegers.asUnsignedByteArray(m);
    }
    
    byte[] block;
    
    if ((inOff != 0) || (inLen != in.length))
    {
      byte[] block = new byte[inLen];
      
      System.arraycopy(in, inOff, block, 0, inLen);
    }
    else
    {
      block = in;
    }
    
    BigInteger input = new BigInteger(1, block);
    
    if (input.compareTo(p) >= 0)
    {
      throw new DataLengthException("input too large for ElGamal cipher.\n");
    }
    
    ElGamalPublicKeyParameters pub = (ElGamalPublicKeyParameters)key;
    
    int pBitLength = p.bitLength();
    BigInteger k = new BigInteger(pBitLength, random);
    
    while ((k.equals(ZERO)) || (k.compareTo(p.subtract(TWO)) > 0))
    {
      k = new BigInteger(pBitLength, random);
    }
    
    BigInteger g = key.getParameters().getG();
    BigInteger gamma = g.modPow(k, p);
    BigInteger phi = input.multiply(pub.getY().modPow(k, p)).mod(p);
    
    byte[] out1 = gamma.toByteArray();
    byte[] out2 = phi.toByteArray();
    byte[] output = new byte[getOutputBlockSize()];
    
    if (out1.length > output.length / 2)
    {
      System.arraycopy(out1, 1, output, output.length / 2 - (out1.length - 1), out1.length - 1);
    }
    else
    {
      System.arraycopy(out1, 0, output, output.length / 2 - out1.length, out1.length);
    }
    
    if (out2.length > output.length / 2)
    {
      System.arraycopy(out2, 1, output, output.length - (out2.length - 1), out2.length - 1);
    }
    else
    {
      System.arraycopy(out2, 0, output, output.length - out2.length, out2.length);
    }
    
    return output;
  }
}
