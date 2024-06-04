package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;







public class ECNamedCurveSpec
  extends ECParameterSpec
{
  private String name;
  
  private static EllipticCurve convertCurve(ECCurve curve, byte[] seed)
  {
    ECField field = convertField(curve.getField());
    BigInteger a = curve.getA().toBigInteger();BigInteger b = curve.getB().toBigInteger();
    return new EllipticCurve(field, a, b, seed);
  }
  
  private static ECField convertField(FiniteField field)
  {
    if (ECAlgorithms.isFpField(field))
    {
      return new ECFieldFp(field.getCharacteristic());
    }
    

    Polynomial poly = ((PolynomialExtensionField)field).getMinimalPolynomial();
    int[] exponents = poly.getExponentsPresent();
    int[] ks = Arrays.reverse(Arrays.copyOfRange(exponents, 1, exponents.length - 1));
    return new ECFieldF2m(poly.getDegree(), ks);
  }
  


  private static java.security.spec.ECPoint convertPoint(org.spongycastle.math.ec.ECPoint g)
  {
    g = g.normalize();
    return new java.security.spec.ECPoint(g.getAffineXCoord().toBigInteger(), g.getAffineYCoord().toBigInteger());
  }
  




  public ECNamedCurveSpec(String name, ECCurve curve, org.spongycastle.math.ec.ECPoint g, BigInteger n)
  {
    super(convertCurve(curve, null), convertPoint(g), n, 1);
    
    this.name = name;
  }
  




  public ECNamedCurveSpec(String name, EllipticCurve curve, java.security.spec.ECPoint g, BigInteger n)
  {
    super(curve, g, n, 1);
    
    this.name = name;
  }
  





  public ECNamedCurveSpec(String name, ECCurve curve, org.spongycastle.math.ec.ECPoint g, BigInteger n, BigInteger h)
  {
    super(convertCurve(curve, null), convertPoint(g), n, h.intValue());
    
    this.name = name;
  }
  





  public ECNamedCurveSpec(String name, EllipticCurve curve, java.security.spec.ECPoint g, BigInteger n, BigInteger h)
  {
    super(curve, g, n, h.intValue());
    
    this.name = name;
  }
  






  public ECNamedCurveSpec(String name, ECCurve curve, org.spongycastle.math.ec.ECPoint g, BigInteger n, BigInteger h, byte[] seed)
  {
    super(convertCurve(curve, seed), convertPoint(g), n, h.intValue());
    
    this.name = name;
  }
  



  public String getName()
  {
    return name;
  }
}
