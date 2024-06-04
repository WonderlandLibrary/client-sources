package org.spongycastle.crypto.signers;

import java.io.IOException;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;



public class RSADigestSigner
  implements Signer
{
  private final AsymmetricBlockCipher rsaEngine = new PKCS1Encoding(new RSABlindedEngine());
  
  private final AlgorithmIdentifier algId;
  private final Digest digest;
  private boolean forSigning;
  private static final Hashtable oidMap = new Hashtable();
  



  static
  {
    oidMap.put("RIPEMD128", TeleTrusTObjectIdentifiers.ripemd128);
    oidMap.put("RIPEMD160", TeleTrusTObjectIdentifiers.ripemd160);
    oidMap.put("RIPEMD256", TeleTrusTObjectIdentifiers.ripemd256);
    
    oidMap.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
    oidMap.put("SHA-224", NISTObjectIdentifiers.id_sha224);
    oidMap.put("SHA-256", NISTObjectIdentifiers.id_sha256);
    oidMap.put("SHA-384", NISTObjectIdentifiers.id_sha384);
    oidMap.put("SHA-512", NISTObjectIdentifiers.id_sha512);
    oidMap.put("SHA-512/224", NISTObjectIdentifiers.id_sha512_224);
    oidMap.put("SHA-512/256", NISTObjectIdentifiers.id_sha512_256);
    
    oidMap.put("SHA3-224", NISTObjectIdentifiers.id_sha3_224);
    oidMap.put("SHA3-256", NISTObjectIdentifiers.id_sha3_256);
    oidMap.put("SHA3-384", NISTObjectIdentifiers.id_sha3_384);
    oidMap.put("SHA3-512", NISTObjectIdentifiers.id_sha3_512);
    
    oidMap.put("MD2", PKCSObjectIdentifiers.md2);
    oidMap.put("MD4", PKCSObjectIdentifiers.md4);
    oidMap.put("MD5", PKCSObjectIdentifiers.md5);
  }
  

  public RSADigestSigner(Digest digest)
  {
    this(digest, (ASN1ObjectIdentifier)oidMap.get(digest.getAlgorithmName()));
  }
  


  public RSADigestSigner(Digest digest, ASN1ObjectIdentifier digestOid)
  {
    this.digest = digest;
    algId = new AlgorithmIdentifier(digestOid, DERNull.INSTANCE);
  }
  
  /**
   * @deprecated
   */
  public String getAlgorithmName()
  {
    return digest.getAlgorithmName() + "withRSA";
  }
  










  public void init(boolean forSigning, CipherParameters parameters)
  {
    this.forSigning = forSigning;
    AsymmetricKeyParameter k;
    AsymmetricKeyParameter k;
    if ((parameters instanceof ParametersWithRandom))
    {
      k = (AsymmetricKeyParameter)((ParametersWithRandom)parameters).getParameters();
    }
    else
    {
      k = (AsymmetricKeyParameter)parameters;
    }
    
    if ((forSigning) && (!k.isPrivate()))
    {
      throw new IllegalArgumentException("signing requires private key");
    }
    
    if ((!forSigning) && (k.isPrivate()))
    {
      throw new IllegalArgumentException("verification requires public key");
    }
    
    reset();
    
    rsaEngine.init(forSigning, parameters);
  }
  




  public void update(byte input)
  {
    digest.update(input);
  }
  






  public void update(byte[] input, int inOff, int length)
  {
    digest.update(input, inOff, length);
  }
  




  public byte[] generateSignature()
    throws CryptoException, DataLengthException
  {
    if (!forSigning)
    {
      throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
    }
    
    byte[] hash = new byte[digest.getDigestSize()];
    digest.doFinal(hash, 0);
    
    try
    {
      byte[] data = derEncode(hash);
      return rsaEngine.processBlock(data, 0, data.length);
    }
    catch (IOException e)
    {
      throw new CryptoException("unable to encode signature: " + e.getMessage(), e);
    }
  }
  





  public boolean verifySignature(byte[] signature)
  {
    if (forSigning)
    {
      throw new IllegalStateException("RSADigestSigner not initialised for verification");
    }
    
    byte[] hash = new byte[digest.getDigestSize()];
    
    digest.doFinal(hash, 0);
    



    try
    {
      byte[] sig = rsaEngine.processBlock(signature, 0, signature.length);
      expected = derEncode(hash);
    }
    catch (Exception e) {
      byte[] expected;
      return false; }
    byte[] expected;
    byte[] sig;
    if (sig.length == expected.length)
    {
      return Arrays.constantTimeAreEqual(sig, expected);
    }
    if (sig.length == expected.length - 2)
    {
      int sigOffset = sig.length - hash.length - 2;
      int expectedOffset = expected.length - hash.length - 2; int 
      
        tmp116_115 = 1; byte[] tmp116_113 = expected;tmp116_113[tmp116_115] = ((byte)(tmp116_113[tmp116_115] - 2)); int 
        tmp125_124 = 3; byte[] tmp125_122 = expected;tmp125_122[tmp125_124] = ((byte)(tmp125_122[tmp125_124] - 2));
      
      int nonEqual = 0;
      
      for (int i = 0; i < hash.length; i++)
      {
        nonEqual |= sig[(sigOffset + i)] ^ expected[(expectedOffset + i)];
      }
      
      for (int i = 0; i < sigOffset; i++)
      {
        nonEqual |= sig[i] ^ expected[i];
      }
      
      return nonEqual == 0;
    }
    

    Arrays.constantTimeAreEqual(expected, expected);
    
    return false;
  }
  

  public void reset()
  {
    digest.reset();
  }
  

  private byte[] derEncode(byte[] hash)
    throws IOException
  {
    DigestInfo dInfo = new DigestInfo(algId, hash);
    
    return dInfo.getEncoded("DER");
  }
}
