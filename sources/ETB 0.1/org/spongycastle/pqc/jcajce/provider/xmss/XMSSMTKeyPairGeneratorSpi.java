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
import org.spongycastle.pqc.crypto.xmss.XMSSMTKeyGenerationParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTKeyPairGenerator;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.XMSSMTParameterSpec;


public class XMSSMTKeyPairGeneratorSpi
  extends KeyPairGenerator
{
  private XMSSMTKeyGenerationParameters param;
  private XMSSMTKeyPairGenerator engine = new XMSSMTKeyPairGenerator();
  
  private ASN1ObjectIdentifier treeDigest;
  private SecureRandom random = new SecureRandom();
  private boolean initialised = false;
  
  public XMSSMTKeyPairGeneratorSpi()
  {
    super("XMSSMT");
  }
  


  public void initialize(int strength, SecureRandom random)
  {
    throw new IllegalArgumentException("use AlgorithmParameterSpec");
  }
  


  public void initialize(AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidAlgorithmParameterException
  {
    if (!(params instanceof XMSSMTParameterSpec))
    {
      throw new InvalidAlgorithmParameterException("parameter object not a XMSSMTParameterSpec");
    }
    
    XMSSMTParameterSpec xmssParams = (XMSSMTParameterSpec)params;
    
    if (xmssParams.getTreeDigest().equals("SHA256"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha256;
      param = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.getHeight(), xmssParams.getLayers(), new SHA256Digest()), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHA512"))
    {
      treeDigest = NISTObjectIdentifiers.id_sha512;
      param = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.getHeight(), xmssParams.getLayers(), new SHA512Digest()), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHAKE128"))
    {
      treeDigest = NISTObjectIdentifiers.id_shake128;
      param = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.getHeight(), xmssParams.getLayers(), new SHAKEDigest(128)), random);
    }
    else if (xmssParams.getTreeDigest().equals("SHAKE256"))
    {
      treeDigest = NISTObjectIdentifiers.id_shake256;
      param = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(xmssParams.getHeight(), xmssParams.getLayers(), new SHAKEDigest(256)), random);
    }
    
    engine.init(param);
    initialised = true;
  }
  
  public KeyPair generateKeyPair()
  {
    if (!initialised)
    {
      param = new XMSSMTKeyGenerationParameters(new XMSSMTParameters(10, 20, new SHA512Digest()), random);
      
      engine.init(param);
      initialised = true;
    }
    
    AsymmetricCipherKeyPair pair = engine.generateKeyPair();
    XMSSMTPublicKeyParameters pub = (XMSSMTPublicKeyParameters)pair.getPublic();
    XMSSMTPrivateKeyParameters priv = (XMSSMTPrivateKeyParameters)pair.getPrivate();
    
    return new KeyPair(new BCXMSSMTPublicKey(treeDigest, pub), new BCXMSSMTPrivateKey(treeDigest, priv));
  }
}
