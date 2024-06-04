package org.spongycastle.pqc.crypto.sphincs;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;

public class SPHINCS256KeyPairGenerator implements AsymmetricCipherKeyPairGenerator
{
  private SecureRandom random;
  private Digest treeDigest;
  
  public SPHINCS256KeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    random = param.getRandom();
    treeDigest = ((SPHINCS256KeyGenerationParameters)param).getTreeDigest();
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    Tree.leafaddr a = new Tree.leafaddr();
    
    byte[] sk = new byte['р'];
    
    random.nextBytes(sk);
    
    byte[] pk = new byte['Р'];
    
    System.arraycopy(sk, 32, pk, 0, 1024);
    

    level = 11;
    subtree = 0L;
    subleaf = 0L;
    
    HashFunctions hs = new HashFunctions(treeDigest);
    


    Tree.treehash(hs, pk, 1024, 5, sk, a, pk, 0);
    
    return new AsymmetricCipherKeyPair(new SPHINCSPublicKeyParameters(pk), new SPHINCSPrivateKeyParameters(sk));
  }
}
