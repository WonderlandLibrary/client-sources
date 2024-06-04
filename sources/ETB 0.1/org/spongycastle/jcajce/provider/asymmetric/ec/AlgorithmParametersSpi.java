package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;

public class AlgorithmParametersSpi
  extends java.security.AlgorithmParametersSpi
{
  private java.security.spec.ECParameterSpec ecParameterSpec;
  private String curveName;
  
  public AlgorithmParametersSpi() {}
  
  protected boolean isASN1FormatString(String format)
  {
    return (format == null) || (format.equals("ASN.1"));
  }
  

  protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec)
    throws InvalidParameterSpecException
  {
    if ((algorithmParameterSpec instanceof ECGenParameterSpec))
    {
      ECGenParameterSpec ecGenParameterSpec = (ECGenParameterSpec)algorithmParameterSpec;
      X9ECParameters params = ECUtils.getDomainParametersFromGenSpec(ecGenParameterSpec);
      
      if (params == null)
      {
        throw new InvalidParameterSpecException("EC curve name not recognized: " + ecGenParameterSpec.getName());
      }
      curveName = ecGenParameterSpec.getName();
      ecParameterSpec = EC5Util.convertToSpec(params);
    }
    else if ((algorithmParameterSpec instanceof java.security.spec.ECParameterSpec))
    {
      if ((algorithmParameterSpec instanceof ECNamedCurveSpec))
      {
        curveName = ((ECNamedCurveSpec)algorithmParameterSpec).getName();
      }
      else
      {
        curveName = null;
      }
      ecParameterSpec = ((java.security.spec.ECParameterSpec)algorithmParameterSpec);
    }
    else
    {
      throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + algorithmParameterSpec.getClass().getName());
    }
  }
  

  protected void engineInit(byte[] bytes)
    throws IOException
  {
    engineInit(bytes, "ASN.1");
  }
  

  protected void engineInit(byte[] bytes, String format)
    throws IOException
  {
    if (isASN1FormatString(format))
    {
      X962Parameters params = X962Parameters.getInstance(bytes);
      
      ECCurve curve = EC5Util.getCurve(BouncyCastleProvider.CONFIGURATION, params);
      
      if (params.isNamedCurve())
      {
        ASN1ObjectIdentifier curveId = ASN1ObjectIdentifier.getInstance(params.getParameters());
        
        curveName = ECNamedCurveTable.getName(curveId);
        if (curveName == null)
        {
          curveName = curveId.getId();
        }
      }
      
      ecParameterSpec = EC5Util.convertToSpec(params, curve);
    }
    else
    {
      throw new IOException("Unknown encoded parameters format in AlgorithmParameters object: " + format);
    }
  }
  

  protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> paramSpec)
    throws InvalidParameterSpecException
  {
    if ((java.security.spec.ECParameterSpec.class.isAssignableFrom(paramSpec)) || (paramSpec == AlgorithmParameterSpec.class))
    {
      return ecParameterSpec;
    }
    if (ECGenParameterSpec.class.isAssignableFrom(paramSpec))
    {
      if (curveName != null)
      {
        ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(curveName);
        
        if (namedCurveOid != null)
        {
          return new ECGenParameterSpec(namedCurveOid.getId());
        }
        return new ECGenParameterSpec(curveName);
      }
      

      ASN1ObjectIdentifier namedCurveOid = ECUtil.getNamedCurveOid(EC5Util.convertSpec(ecParameterSpec, false));
      
      if (namedCurveOid != null)
      {
        return new ECGenParameterSpec(namedCurveOid.getId());
      }
    }
    
    throw new InvalidParameterSpecException("EC AlgorithmParameters cannot convert to " + paramSpec.getName());
  }
  

  protected byte[] engineGetEncoded()
    throws IOException
  {
    return engineGetEncoded("ASN.1");
  }
  

  protected byte[] engineGetEncoded(String format)
    throws IOException
  {
    if (isASN1FormatString(format))
    {
      X962Parameters params;
      X962Parameters params;
      if (ecParameterSpec == null)
      {
        params = new X962Parameters(DERNull.INSTANCE);
      } else { X962Parameters params;
        if (curveName != null)
        {
          params = new X962Parameters(ECUtil.getNamedCurveOid(curveName));
        }
        else
        {
          org.spongycastle.jce.spec.ECParameterSpec ecSpec = EC5Util.convertSpec(ecParameterSpec, false);
          




          X9ECParameters ecP = new X9ECParameters(ecSpec.getCurve(), ecSpec.getG(), ecSpec.getN(), ecSpec.getH(), ecSpec.getSeed());
          
          params = new X962Parameters(ecP);
        }
      }
      return params.getEncoded();
    }
    
    throw new IOException("Unknown parameters format in AlgorithmParameters object: " + format);
  }
  

  protected String engineToString()
  {
    return "EC AlgorithmParameters ";
  }
}
