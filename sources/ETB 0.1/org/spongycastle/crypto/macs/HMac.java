package org.spongycastle.crypto.macs;

import java.util.Hashtable;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Memoable;














public class HMac
  implements Mac
{
  private static final byte IPAD = 54;
  private static final byte OPAD = 92;
  private Digest digest;
  private int digestSize;
  private int blockLength;
  private Memoable ipadState;
  private Memoable opadState;
  private byte[] inputPad;
  private byte[] outputBuf;
  private static Hashtable blockLengths = new Hashtable();
  
  static { blockLengths.put("GOST3411", Integers.valueOf(32));
    
    blockLengths.put("MD2", Integers.valueOf(16));
    blockLengths.put("MD4", Integers.valueOf(64));
    blockLengths.put("MD5", Integers.valueOf(64));
    
    blockLengths.put("RIPEMD128", Integers.valueOf(64));
    blockLengths.put("RIPEMD160", Integers.valueOf(64));
    
    blockLengths.put("SHA-1", Integers.valueOf(64));
    blockLengths.put("SHA-224", Integers.valueOf(64));
    blockLengths.put("SHA-256", Integers.valueOf(64));
    blockLengths.put("SHA-384", Integers.valueOf(128));
    blockLengths.put("SHA-512", Integers.valueOf(128));
    
    blockLengths.put("Tiger", Integers.valueOf(64));
    blockLengths.put("Whirlpool", Integers.valueOf(64));
  }
  

  private static int getByteLength(Digest digest)
  {
    if ((digest instanceof ExtendedDigest))
    {
      return ((ExtendedDigest)digest).getByteLength();
    }
    
    Integer b = (Integer)blockLengths.get(digest.getAlgorithmName());
    
    if (b == null)
    {
      throw new IllegalArgumentException("unknown digest passed: " + digest.getAlgorithmName());
    }
    
    return b.intValue();
  }
  







  public HMac(Digest digest)
  {
    this(digest, getByteLength(digest));
  }
  


  private HMac(Digest digest, int byteLength)
  {
    this.digest = digest;
    digestSize = digest.getDigestSize();
    blockLength = byteLength;
    inputPad = new byte[blockLength];
    outputBuf = new byte[blockLength + digestSize];
  }
  
  public String getAlgorithmName()
  {
    return digest.getAlgorithmName() + "/HMAC";
  }
  
  public Digest getUnderlyingDigest()
  {
    return digest;
  }
  

  public void init(CipherParameters params)
  {
    digest.reset();
    
    byte[] key = ((KeyParameter)params).getKey();
    int keyLength = key.length;
    
    if (keyLength > blockLength)
    {
      digest.update(key, 0, keyLength);
      digest.doFinal(inputPad, 0);
      
      keyLength = digestSize;
    }
    else
    {
      System.arraycopy(key, 0, inputPad, 0, keyLength);
    }
    
    for (int i = keyLength; i < inputPad.length; i++)
    {
      inputPad[i] = 0;
    }
    
    System.arraycopy(inputPad, 0, outputBuf, 0, blockLength);
    
    xorPad(inputPad, blockLength, (byte)54);
    xorPad(outputBuf, blockLength, (byte)92);
    
    if ((digest instanceof Memoable))
    {
      opadState = ((Memoable)digest).copy();
      
      ((Digest)opadState).update(outputBuf, 0, blockLength);
    }
    
    digest.update(inputPad, 0, inputPad.length);
    
    if ((digest instanceof Memoable))
    {
      ipadState = ((Memoable)digest).copy();
    }
  }
  
  public int getMacSize()
  {
    return digestSize;
  }
  

  public void update(byte in)
  {
    digest.update(in);
  }
  



  public void update(byte[] in, int inOff, int len)
  {
    digest.update(in, inOff, len);
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    digest.doFinal(outputBuf, blockLength);
    
    if (opadState != null)
    {
      ((Memoable)digest).reset(opadState);
      digest.update(outputBuf, blockLength, digest.getDigestSize());
    }
    else
    {
      digest.update(outputBuf, 0, outputBuf.length);
    }
    
    int len = digest.doFinal(out, outOff);
    
    for (int i = blockLength; i < outputBuf.length; i++)
    {
      outputBuf[i] = 0;
    }
    
    if (ipadState != null)
    {
      ((Memoable)digest).reset(ipadState);
    }
    else
    {
      digest.update(inputPad, 0, inputPad.length);
    }
    
    return len;
  }
  






  public void reset()
  {
    digest.reset();
    



    digest.update(inputPad, 0, inputPad.length);
  }
  
  private static void xorPad(byte[] pad, int len, byte n)
  {
    for (int i = 0; i < len; i++)
    {
      int tmp9_8 = i;pad[tmp9_8] = ((byte)(pad[tmp9_8] ^ n));
    }
  }
}
