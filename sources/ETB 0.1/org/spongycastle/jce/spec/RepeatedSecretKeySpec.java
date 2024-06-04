package org.spongycastle.jce.spec;


/**
 * @deprecated
 */
public class RepeatedSecretKeySpec
  extends org.spongycastle.jcajce.spec.RepeatedSecretKeySpec
{
  private String algorithm;
  

  public RepeatedSecretKeySpec(String algorithm)
  {
    super(algorithm);
  }
}
