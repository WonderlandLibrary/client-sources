package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class RSAKeyParameters
  extends AsymmetricKeyParameter
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  

  private BigInteger modulus;
  
  private BigInteger exponent;
  

  public RSAKeyParameters(boolean isPrivate, BigInteger modulus, BigInteger exponent)
  {
    super(isPrivate);
    
    if (!isPrivate)
    {
      if ((exponent.intValue() & 0x1) == 0)
      {
        throw new IllegalArgumentException("RSA publicExponent is even");
      }
    }
    
    this.modulus = validate(modulus);
    this.exponent = exponent;
  }
  
  private BigInteger validate(BigInteger modulus)
  {
    if ((modulus.intValue() & 0x1) == 0)
    {
      throw new IllegalArgumentException("RSA modulus is even");
    }
    





    if (!modulus.gcd(new BigInteger("1451887755777639901511587432083070202422614380984889313550570919659315177065956574359078912654149167643992684236991305777574330831666511589145701059710742276692757882915756220901998212975756543223550490431013061082131040808010565293748926901442915057819663730454818359472391642885328171302299245556663073719855")).equals(ONE))
    {
      throw new IllegalArgumentException("RSA modulus has a small prime factor");
    }
    


    return modulus;
  }
  
  public BigInteger getModulus()
  {
    return modulus;
  }
  
  public BigInteger getExponent()
  {
    return exponent;
  }
}
