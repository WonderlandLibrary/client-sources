package org.spongycastle.pqc.jcajce.provider.mceliece;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyGenerationParameters;
import org.spongycastle.pqc.crypto.mceliece.McElieceKeyPairGenerator;
import org.spongycastle.pqc.crypto.mceliece.McElieceParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.spongycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.spongycastle.pqc.jcajce.spec.McElieceKeyGenParameterSpec;


public class McElieceKeyPairGeneratorSpi
  extends KeyPairGenerator
{
  McElieceKeyPairGenerator kpg;
  
  public McElieceKeyPairGeneratorSpi()
  {
    super("McEliece");
  }
  
  public void initialize(AlgorithmParameterSpec params)
    throws InvalidAlgorithmParameterException
  {
    kpg = new McElieceKeyPairGenerator();
    super.initialize(params);
    McElieceKeyGenParameterSpec ecc = (McElieceKeyGenParameterSpec)params;
    
    McElieceKeyGenerationParameters mccKGParams = new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters(ecc.getM(), ecc.getT()));
    kpg.init(mccKGParams);
  }
  
  public void initialize(int keySize, SecureRandom random)
  {
    McElieceKeyGenParameterSpec paramSpec = new McElieceKeyGenParameterSpec();
    

    try
    {
      initialize(paramSpec);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException) {}
  }
  


  public KeyPair generateKeyPair()
  {
    AsymmetricCipherKeyPair generateKeyPair = kpg.generateKeyPair();
    McEliecePrivateKeyParameters sk = (McEliecePrivateKeyParameters)generateKeyPair.getPrivate();
    McEliecePublicKeyParameters pk = (McEliecePublicKeyParameters)generateKeyPair.getPublic();
    
    return new KeyPair(new BCMcEliecePublicKey(pk), new BCMcEliecePrivateKey(sk));
  }
}
