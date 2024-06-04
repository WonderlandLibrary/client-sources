package org.spongycastle.pqc.crypto.sphincs;

import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;


public class SPHINCS256KeyGenerationParameters
  extends KeyGenerationParameters
{
  private final Digest treeDigest;
  
  public SPHINCS256KeyGenerationParameters(SecureRandom random, Digest treeDigest)
  {
    super(random, 8448);
    this.treeDigest = treeDigest;
  }
  
  public Digest getTreeDigest()
  {
    return treeDigest;
  }
}
