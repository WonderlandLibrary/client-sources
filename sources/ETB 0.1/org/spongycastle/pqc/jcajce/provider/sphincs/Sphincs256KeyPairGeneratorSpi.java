package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.digests.SHA3Digest;
import org.spongycastle.crypto.digests.SHA512tDigest;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256KeyGenerationParameters;
import org.spongycastle.pqc.crypto.sphincs.SPHINCS256KeyPairGenerator;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.SPHINCS256KeyGenParameterSpec;

public class Sphincs256KeyPairGeneratorSpi
  extends KeyPairGenerator
{
  ASN1ObjectIdentifier treeDigest = NISTObjectIdentifiers.id_sha512_256;
  
  SPHINCS256KeyGenerationParameters param;
  SPHINCS256KeyPairGenerator engine = new SPHINCS256KeyPairGenerator();
  
  SecureRandom random = new SecureRandom();
  boolean initialised = false;
  
  public Sphincs256KeyPairGeneratorSpi()
  {
    super("SPHINCS256");
  }
  


  public void initialize(int strength, SecureRandom random)
  {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  


  public void initialize(AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidAlgorithmParameterException
  {
    if (!(params instanceof SPHINCS256KeyGenParameterSpec))
    {
      throw new InvalidAlgorithmParameterException("parameter object not a SPHINCS256KeyGenParameterSpec");
    }
    
    SPHINCS256KeyGenParameterSpec sphincsParams = (SPHINCS256KeyGenParameterSpec)params;
    
    if (sphincsParams.getTreeDigest().equals("SHA512-256"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha512_256;
      param = new SPHINCS256KeyGenerationParameters(random, new SHA512tDigest(256));
    }
    else if (sphincsParams.getTreeDigest().equals("SHA3-256"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha3_256;
      param = new SPHINCS256KeyGenerationParameters(random, new SHA3Digest(256));
    }
    
    engine.init(param);
    initialised = true;
  }
  
  public KeyPair generateKeyPair()
  {
    if (!initialised)
    {
      param = new SPHINCS256KeyGenerationParameters(random, new SHA512tDigest(256));
      
      engine.init(param);
      initialised = true;
    }
    
    AsymmetricCipherKeyPair pair = engine.generateKeyPair();
    SPHINCSPublicKeyParameters pub = (SPHINCSPublicKeyParameters)pair.getPublic();
    SPHINCSPrivateKeyParameters priv = (SPHINCSPrivateKeyParameters)pair.getPrivate();
    
    return new KeyPair(new BCSphincs256PublicKey(treeDigest, pub), new BCSphincs256PrivateKey(treeDigest, priv));
  }
}
