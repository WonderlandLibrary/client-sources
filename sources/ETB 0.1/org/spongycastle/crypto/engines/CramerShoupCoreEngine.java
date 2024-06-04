package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.CramerShoupKeyParameters;
import org.spongycastle.crypto.params.CramerShoupParameters;
import org.spongycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.spongycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.BigIntegers;






public class CramerShoupCoreEngine
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  
  private CramerShoupKeyParameters key;
  private SecureRandom random;
  private boolean forEncryption;
  private String label = null;
  



  public CramerShoupCoreEngine() {}
  


  public void init(boolean forEncryption, CipherParameters param, String label)
  {
    init(forEncryption, param);
    
    this.label = label;
  }
  






  public void init(boolean forEncryption, CipherParameters param)
  {
    SecureRandom providedRandom = null;
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      key = ((CramerShoupKeyParameters)rParam.getParameters());
      providedRandom = rParam.getRandom();
    }
    else
    {
      key = ((CramerShoupKeyParameters)param);
    }
    
    random = initSecureRandom(forEncryption, providedRandom);
    this.forEncryption = forEncryption;
  }
  







  public int getInputBlockSize()
  {
    int bitSize = key.getParameters().getP().bitLength();
    
    if (forEncryption)
    {
      return (bitSize + 7) / 8 - 1;
    }
    

    return (bitSize + 7) / 8;
  }
  








  public int getOutputBlockSize()
  {
    int bitSize = key.getParameters().getP().bitLength();
    
    if (forEncryption)
    {
      return (bitSize + 7) / 8;
    }
    

    return (bitSize + 7) / 8 - 1;
  }
  

  public BigInteger convertInput(byte[] in, int inOff, int inLen)
  {
    if (inLen > getInputBlockSize() + 1)
    {
      throw new DataLengthException("input too large for Cramer Shoup cipher.");
    }
    if ((inLen == getInputBlockSize() + 1) && (forEncryption))
    {
      throw new DataLengthException("input too large for Cramer Shoup cipher.");
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
    
    BigInteger res = new BigInteger(1, block);
    if (res.compareTo(key.getParameters().getP()) >= 0)
    {
      throw new DataLengthException("input too large for Cramer Shoup cipher.");
    }
    
    return res;
  }
  
  public byte[] convertOutput(BigInteger result)
  {
    byte[] output = result.toByteArray();
    
    if (!forEncryption)
    {
      if ((output[0] == 0) && (output.length > getOutputBlockSize()))
      {
        byte[] tmp = new byte[output.length - 1];
        
        System.arraycopy(output, 1, tmp, 0, tmp.length);
        
        return tmp;
      }
      
      if (output.length < getOutputBlockSize())
      {
        byte[] tmp = new byte[getOutputBlockSize()];
        
        System.arraycopy(output, 0, tmp, tmp.length - output.length, output.length);
        
        return tmp;
      }
      

    }
    else if (output[0] == 0)
    {
      byte[] tmp = new byte[output.length - 1];
      
      System.arraycopy(output, 1, tmp, 0, tmp.length);
      
      return tmp;
    }
    

    return output;
  }
  

  public CramerShoupCiphertext encryptBlock(BigInteger input)
  {
    CramerShoupCiphertext result = null;
    
    if ((!key.isPrivate()) && (forEncryption) && ((key instanceof CramerShoupPublicKeyParameters)))
    {
      CramerShoupPublicKeyParameters pk = (CramerShoupPublicKeyParameters)key;
      BigInteger p = pk.getParameters().getP();
      BigInteger g1 = pk.getParameters().getG1();
      BigInteger g2 = pk.getParameters().getG2();
      
      BigInteger h = pk.getH();
      
      if (!isValidMessage(input, p))
      {
        return result;
      }
      
      BigInteger r = generateRandomElement(p, random);
      


      BigInteger u1 = g1.modPow(r, p);
      BigInteger u2 = g2.modPow(r, p);
      BigInteger e = h.modPow(r, p).multiply(input).mod(p);
      
      Digest digest = pk.getParameters().getH();
      byte[] u1Bytes = u1.toByteArray();
      digest.update(u1Bytes, 0, u1Bytes.length);
      byte[] u2Bytes = u2.toByteArray();
      digest.update(u2Bytes, 0, u2Bytes.length);
      byte[] eBytes = e.toByteArray();
      digest.update(eBytes, 0, eBytes.length);
      if (label != null)
      {
        byte[] lBytes = label.getBytes();
        digest.update(lBytes, 0, lBytes.length);
      }
      byte[] out = new byte[digest.getDigestSize()];
      digest.doFinal(out, 0);
      BigInteger a = new BigInteger(1, out);
      
      BigInteger v = pk.getC().modPow(r, p).multiply(pk.getD().modPow(r.multiply(a), p)).mod(p);
      
      result = new CramerShoupCiphertext(u1, u2, e, v);
    }
    return result;
  }
  

  public BigInteger decryptBlock(CramerShoupCiphertext input)
    throws CramerShoupCoreEngine.CramerShoupCiphertextException
  {
    BigInteger result = null;
    
    if ((key.isPrivate()) && (!forEncryption) && ((key instanceof CramerShoupPrivateKeyParameters)))
    {
      CramerShoupPrivateKeyParameters sk = (CramerShoupPrivateKeyParameters)key;
      
      BigInteger p = sk.getParameters().getP();
      
      Digest digest = sk.getParameters().getH();
      byte[] u1Bytes = input.getU1().toByteArray();
      digest.update(u1Bytes, 0, u1Bytes.length);
      byte[] u2Bytes = input.getU2().toByteArray();
      digest.update(u2Bytes, 0, u2Bytes.length);
      byte[] eBytes = input.getE().toByteArray();
      digest.update(eBytes, 0, eBytes.length);
      if (label != null)
      {
        byte[] lBytes = label.getBytes();
        digest.update(lBytes, 0, lBytes.length);
      }
      byte[] out = new byte[digest.getDigestSize()];
      digest.doFinal(out, 0);
      
      BigInteger a = new BigInteger(1, out);
      
      BigInteger v = u1.modPow(sk.getX1().add(sk.getY1().multiply(a)), p).multiply(u2.modPow(sk.getX2().add(sk.getY2().multiply(a)), p)).mod(p);
      

      if (v.equals(v))
      {
        result = e.multiply(u1.modPow(sk.getZ(), p).modInverse(p)).mod(p);
      }
      else
      {
        throw new CramerShoupCiphertextException("Sorry, that ciphertext is not correct");
      }
    }
    return result;
  }
  
  private BigInteger generateRandomElement(BigInteger p, SecureRandom random)
  {
    return BigIntegers.createRandomInRange(ONE, p.subtract(ONE), random);
  }
  



  private boolean isValidMessage(BigInteger m, BigInteger p)
  {
    return m.compareTo(p) < 0;
  }
  
  protected SecureRandom initSecureRandom(boolean needed, SecureRandom provided)
  {
    return provided != null ? provided : !needed ? null : new SecureRandom();
  }
  


  public static class CramerShoupCiphertextException
    extends Exception
  {
    private static final long serialVersionUID = -6360977166495345076L;
    

    public CramerShoupCiphertextException(String msg)
    {
      super();
    }
  }
}
