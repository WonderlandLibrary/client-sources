package org.spongycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;

public class EC5Util
{
  private static Map customCurves = new HashMap();
  
  static
  {
    Enumeration e = CustomNamedCurves.getNames();
    while (e.hasMoreElements())
    {
      String name = (String)e.nextElement();
      
      X9ECParameters curveParams = ECNamedCurveTable.getByName(name);
      if (curveParams != null)
      {
        customCurves.put(curveParams.getCurve(), CustomNamedCurves.getByName(name).getCurve());
      }
    }
    
    X9ECParameters c25519 = CustomNamedCurves.getByName("Curve25519");
    
    customCurves.put(new ECCurve.Fp(c25519
      .getCurve().getField().getCharacteristic(), c25519
      .getCurve().getA().toBigInteger(), c25519
      .getCurve().getB().toBigInteger()), c25519.getCurve());
  }
  



  public static ECCurve getCurve(ProviderConfiguration configuration, X962Parameters params)
  {
    Set acceptableCurves = configuration.getAcceptableNamedCurves();
    ECCurve curve;
    if (params.isNamedCurve())
    {
      ASN1ObjectIdentifier oid = ASN1ObjectIdentifier.getInstance(params.getParameters());
      ECCurve curve;
      if ((acceptableCurves.isEmpty()) || (acceptableCurves.contains(oid)))
      {
        X9ECParameters ecP = ECUtil.getNamedCurveByOid(oid);
        
        if (ecP == null)
        {
          ecP = (X9ECParameters)configuration.getAdditionalECParameters().get(oid);
        }
        
        curve = ecP.getCurve();
      }
      else
      {
        throw new IllegalStateException("named curve not acceptable");
      }
    } else { ECCurve curve;
      if (params.isImplicitlyCA())
      {
        curve = configuration.getEcImplicitlyCa().getCurve();
      } else { ECCurve curve;
        if (acceptableCurves.isEmpty())
        {
          X9ECParameters ecP = X9ECParameters.getInstance(params.getParameters());
          
          curve = ecP.getCurve();
        }
        else
        {
          throw new IllegalStateException("encoded parameters not acceptable");
        } } }
    ECCurve curve;
    return curve;
  }
  

  public static ECDomainParameters getDomainParameters(ProviderConfiguration configuration, java.security.spec.ECParameterSpec params)
  {
    ECDomainParameters domainParameters;
    
    ECDomainParameters domainParameters;
    if (params == null)
    {
      org.spongycastle.jce.spec.ECParameterSpec iSpec = configuration.getEcImplicitlyCa();
      
      domainParameters = new ECDomainParameters(iSpec.getCurve(), iSpec.getG(), iSpec.getN(), iSpec.getH(), iSpec.getSeed());
    }
    else
    {
      domainParameters = ECUtil.getDomainParameters(configuration, convertSpec(params, false));
    }
    
    return domainParameters;
  }
  

  public static java.security.spec.ECParameterSpec convertToSpec(X962Parameters params, ECCurve curve)
  {
    java.security.spec.ECParameterSpec ecSpec;
    
    java.security.spec.ECParameterSpec ecSpec;
    if (params.isNamedCurve())
    {
      ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)params.getParameters();
      X9ECParameters ecP = ECUtil.getNamedCurveByOid(oid);
      if (ecP == null)
      {
        Map additionalECParameters = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
        if (!additionalECParameters.isEmpty())
        {
          ecP = (X9ECParameters)additionalECParameters.get(oid);
        }
      }
      
      EllipticCurve ellipticCurve = convertCurve(curve, ecP.getSeed());
      







      ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(oid), ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH());
    } else { java.security.spec.ECParameterSpec ecSpec;
      if (params.isImplicitlyCA())
      {
        ecSpec = null;
      }
      else
      {
        X9ECParameters ecP = X9ECParameters.getInstance(params.getParameters());
        
        EllipticCurve ellipticCurve = convertCurve(curve, ecP.getSeed());
        java.security.spec.ECParameterSpec ecSpec;
        if (ecP.getH() != null)
        {






          ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), ecP.getH().intValue());



        }
        else
        {


          ecSpec = new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(ecP.getG().getAffineXCoord().toBigInteger(), ecP.getG().getAffineYCoord().toBigInteger()), ecP.getN(), 1);
        }
      }
    }
    return ecSpec;
  }
  

  public static java.security.spec.ECParameterSpec convertToSpec(X9ECParameters domainParameters)
  {
    return new java.security.spec.ECParameterSpec(
      convertCurve(domainParameters.getCurve(), null), new java.security.spec.ECPoint(domainParameters
      
      .getG().getAffineXCoord().toBigInteger(), domainParameters
      .getG().getAffineYCoord().toBigInteger()), domainParameters
      .getN(), domainParameters
      .getH().intValue());
  }
  


  public static EllipticCurve convertCurve(ECCurve curve, byte[] seed)
  {
    ECField field = convertField(curve.getField());
    BigInteger a = curve.getA().toBigInteger();BigInteger b = curve.getB().toBigInteger();
    


    return new EllipticCurve(field, a, b, null);
  }
  

  public static ECCurve convertCurve(EllipticCurve ec)
  {
    ECField field = ec.getField();
    BigInteger a = ec.getA();
    BigInteger b = ec.getB();
    
    if ((field instanceof ECFieldFp))
    {
      ECCurve.Fp curve = new ECCurve.Fp(((ECFieldFp)field).getP(), a, b);
      
      if (customCurves.containsKey(curve))
      {
        return (ECCurve)customCurves.get(curve);
      }
      
      return curve;
    }
    

    ECFieldF2m fieldF2m = (ECFieldF2m)field;
    int m = fieldF2m.getM();
    int[] ks = ECUtil.convertMidTerms(fieldF2m.getMidTermsOfReductionPolynomial());
    return new ECCurve.F2m(m, ks[0], ks[1], ks[2], a, b);
  }
  

  public static ECField convertField(FiniteField field)
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
  



  public static java.security.spec.ECParameterSpec convertSpec(EllipticCurve ellipticCurve, org.spongycastle.jce.spec.ECParameterSpec spec)
  {
    if ((spec instanceof ECNamedCurveParameterSpec))
    {
      return new ECNamedCurveSpec(((ECNamedCurveParameterSpec)spec)
        .getName(), ellipticCurve, new java.security.spec.ECPoint(spec
        

        .getG().getAffineXCoord().toBigInteger(), spec
        .getG().getAffineYCoord().toBigInteger()), spec
        .getN(), spec
        .getH());
    }
    

    return new java.security.spec.ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(spec
    

      .getG().getAffineXCoord().toBigInteger(), spec
      .getG().getAffineYCoord().toBigInteger()), spec
      .getN(), spec
      .getH().intValue());
  }
  



  public static org.spongycastle.jce.spec.ECParameterSpec convertSpec(java.security.spec.ECParameterSpec ecSpec, boolean withCompression)
  {
    ECCurve curve = convertCurve(ecSpec.getCurve());
    
    return new org.spongycastle.jce.spec.ECParameterSpec(curve, 
    
      convertPoint(curve, ecSpec.getGenerator(), withCompression), ecSpec
      .getOrder(), 
      BigInteger.valueOf(ecSpec.getCofactor()), ecSpec
      .getCurve().getSeed());
  }
  



  public static org.spongycastle.math.ec.ECPoint convertPoint(java.security.spec.ECParameterSpec ecSpec, java.security.spec.ECPoint point, boolean withCompression)
  {
    return convertPoint(convertCurve(ecSpec.getCurve()), point, withCompression);
  }
  



  public static org.spongycastle.math.ec.ECPoint convertPoint(ECCurve curve, java.security.spec.ECPoint point, boolean withCompression)
  {
    return curve.createPoint(point.getAffineX(), point.getAffineY());
  }
  
  public EC5Util() {}
}
