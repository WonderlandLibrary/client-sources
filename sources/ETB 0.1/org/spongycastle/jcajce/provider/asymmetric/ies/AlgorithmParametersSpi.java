package org.spongycastle.jcajce.provider.asymmetric.ies;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.jce.spec.IESParameterSpec;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi
{
  IESParameterSpec currentSpec;
  
  public AlgorithmParametersSpi() {}
  
  protected boolean isASN1FormatString(String format)
  {
    return (format == null) || (format.equals("ASN.1"));
  }
  

  protected AlgorithmParameterSpec engineGetParameterSpec(Class paramSpec)
    throws InvalidParameterSpecException
  {
    if (paramSpec == null)
    {
      throw new NullPointerException("argument to getParameterSpec must not be null");
    }
    
    return localEngineGetParameterSpec(paramSpec);
  }
  






  protected byte[] engineGetEncoded()
  {
    try
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      
      if (currentSpec.getDerivationV() != null)
      {
        v.add(new org.spongycastle.asn1.DERTaggedObject(false, 0, new DEROctetString(currentSpec.getDerivationV())));
      }
      if (currentSpec.getEncodingV() != null)
      {
        v.add(new org.spongycastle.asn1.DERTaggedObject(false, 1, new DEROctetString(currentSpec.getEncodingV())));
      }
      v.add(new ASN1Integer(currentSpec.getMacKeySize()));
      if (currentSpec.getNonce() != null)
      {
        ASN1EncodableVector cV = new ASN1EncodableVector();
        
        cV.add(new ASN1Integer(currentSpec.getCipherKeySize()));
        cV.add(new ASN1Integer(currentSpec.getNonce()));
        
        v.add(new DERSequence(cV));
      }
      return new DERSequence(v).getEncoded("DER");
    }
    catch (IOException e)
    {
      throw new RuntimeException("Error encoding IESParameters");
    }
  }
  

  protected byte[] engineGetEncoded(String format)
  {
    if ((isASN1FormatString(format)) || (format.equalsIgnoreCase("X.509")))
    {
      return engineGetEncoded();
    }
    
    return null;
  }
  

  protected AlgorithmParameterSpec localEngineGetParameterSpec(Class paramSpec)
    throws InvalidParameterSpecException
  {
    if ((paramSpec == IESParameterSpec.class) || (paramSpec == AlgorithmParameterSpec.class))
    {
      return currentSpec;
    }
    
    throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
  }
  

  protected void engineInit(AlgorithmParameterSpec paramSpec)
    throws InvalidParameterSpecException
  {
    if (!(paramSpec instanceof IESParameterSpec))
    {
      throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
    }
    
    currentSpec = ((IESParameterSpec)paramSpec);
  }
  

  protected void engineInit(byte[] params)
    throws IOException
  {
    try
    {
      ASN1Sequence s = (ASN1Sequence)org.spongycastle.asn1.ASN1Primitive.fromByteArray(params);
      
      if (s.size() == 1)
      {
        currentSpec = new IESParameterSpec(null, null, ASN1Integer.getInstance(s.getObjectAt(0)).getValue().intValue());
      }
      else if (s.size() == 2)
      {
        ASN1TaggedObject tagged = ASN1TaggedObject.getInstance(s.getObjectAt(0));
        
        if (tagged.getTagNo() == 0)
        {
          currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(tagged, false).getOctets(), null, ASN1Integer.getInstance(s.getObjectAt(1)).getValue().intValue());
        }
        else
        {
          currentSpec = new IESParameterSpec(null, ASN1OctetString.getInstance(tagged, false).getOctets(), ASN1Integer.getInstance(s.getObjectAt(1)).getValue().intValue());
        }
      }
      else if (s.size() == 3)
      {
        ASN1TaggedObject tagged1 = ASN1TaggedObject.getInstance(s.getObjectAt(0));
        ASN1TaggedObject tagged2 = ASN1TaggedObject.getInstance(s.getObjectAt(1));
        
        currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(tagged1, false).getOctets(), ASN1OctetString.getInstance(tagged2, false).getOctets(), ASN1Integer.getInstance(s.getObjectAt(2)).getValue().intValue());
      }
      else if (s.size() == 4)
      {
        ASN1TaggedObject tagged1 = ASN1TaggedObject.getInstance(s.getObjectAt(0));
        ASN1TaggedObject tagged2 = ASN1TaggedObject.getInstance(s.getObjectAt(1));
        ASN1Sequence cipherDet = ASN1Sequence.getInstance(s.getObjectAt(3));
        


        currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(tagged1, false).getOctets(), ASN1OctetString.getInstance(tagged2, false).getOctets(), ASN1Integer.getInstance(s.getObjectAt(2)).getValue().intValue(), ASN1Integer.getInstance(cipherDet.getObjectAt(0)).getValue().intValue(), ASN1OctetString.getInstance(cipherDet.getObjectAt(1)).getOctets());
      }
    }
    catch (ClassCastException e)
    {
      throw new IOException("Not a valid IES Parameter encoding.");
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      throw new IOException("Not a valid IES Parameter encoding.");
    }
  }
  


  protected void engineInit(byte[] params, String format)
    throws IOException
  {
    if ((isASN1FormatString(format)) || (format.equalsIgnoreCase("X.509")))
    {
      engineInit(params);
    }
    else
    {
      throw new IOException("Unknown parameter format " + format);
    }
  }
  
  protected String engineToString()
  {
    return "IES Parameters";
  }
}
