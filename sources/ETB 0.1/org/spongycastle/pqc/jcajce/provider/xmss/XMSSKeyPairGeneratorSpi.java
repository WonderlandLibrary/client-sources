package org.spongycastle.pqc.jcajce.provider.xmss;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.digests.SHAKEDigest;
import org.spongycastle.pqc.crypto.xmss.XMSSKeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSKeyPairGenerator;
import org.spongycastle.pqc.crypto.xmss.XMSSParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.XMSSParameterSpec;

public class XMSSKeyPairGeneratorSpi
  extends KeyPairGenerator
{
  private XMSSKeyGenerationParameters param;
  private ASN1ObjectIdentifier treeDigest;
  private XMSSKeyPairGenerator engine = new XMSSKeyPairGenerator();
  
  private SecureRandom random = new SecureRandom();
  private boolean initialised = false;
  
  public XMSSKeyPairGeneratorSpi()
  {
    super("XMSS");
  }
  


  public void initialize(int strength, SecureRandom random)
  {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  


  public void initialize(AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidAlgorithmParameterException
  {
    if (!(params instanceof XMSSParameterSpec))
    {
      throw new InvalidAlgorithmParameterException("parameter object not a XMSSParameterSpec");
    }
    
    XMSSParameterSpec xmssParams = (XMSSParameterSpec)params;
    
    if (xmssParams.getTreeDigest().equals("SHA256"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha256;
      param = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.getHeight(), new SHA256Digest()), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHA512"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha512;
      param = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.getHeight(), new SHA512Digest()), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHAKE128"))
    {
      treeDigest = NISTObjectIdentifiers.id_shake128;
      param = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.getHeight(), new SHAKEDigest(128)), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHAKE256"))
    {
      treeDigest = NISTObjectIdentifiers.id_shake256;
      param = new XMSSKeyGenerationParameters(new XMSSParameters(xmssParams.getHeight(), new SHAKEDigest(256)), random);
    }
    
    engine.init(param);
    initialised = true;
  }
  
  public KeyPair generateKeyPair()
  {
    if (!initialised)
    {
      param = new XMSSKeyGenerationParameters(new XMSSParameters(10, new SHA512Digest()), random);
      
      engine.init(param);
      initialised = true;
    }
    
    AsymmetricCipherKeyPair pair = engine.generateKeyPair();
    XMSSPublicKeyParameters pub = (XMSSPublicKeyParameters)pair.getPublic();
    XMSSPrivateKeyParameters priv = (XMSSPrivateKeyParameters)pair.getPrivate();
    
    return new KeyPair(new BCXMSSPublicKey(treeDigest, pub), new BCXMSSPrivateKey(treeDigest, priv));
  }
}
