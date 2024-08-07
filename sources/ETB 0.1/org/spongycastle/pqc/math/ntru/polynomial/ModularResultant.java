package org.spongycastle.pqc.math.ntru.polynomial;

import java.math.BigInteger;
import org.spongycastle.pqc.math.ntru.euclid.BigIntEuclidean;





public class ModularResultant
  extends Resultant
{
  BigInteger modulus;
  
  ModularResultant(BigIntPolynomial rho, BigInteger res, BigInteger modulus)
  {
    super(rho, res);
    this.modulus = modulus;
  }
  









  static ModularResultant combineRho(ModularResultant modRes1, ModularResultant modRes2)
  {
    BigInteger mod1 = modulus;
    BigInteger mod2 = modulus;
    BigInteger prod = mod1.multiply(mod2);
    BigIntEuclidean er = BigIntEuclidean.calculate(mod2, mod1);
    
    BigIntPolynomial rho1 = (BigIntPolynomial)rho.clone();
    rho1.mult(x.multiply(mod2));
    BigIntPolynomial rho2 = (BigIntPolynomial)rho.clone();
    rho2.mult(y.multiply(mod1));
    rho1.add(rho2);
    rho1.mod(prod);
    
    return new ModularResultant(rho1, null, prod);
  }
}
