package org.spongycastle.crypto.tls;


public class ECBasisType
{
  public static final short ec_basis_trinomial = 1;
  public static final short ec_basis_pentanomial = 2;
  
  public ECBasisType() {}
  
  public static boolean isValid(short ecBasisType)
  {
    return (ecBasisType >= 1) && (ecBasisType <= 2);
  }
}
