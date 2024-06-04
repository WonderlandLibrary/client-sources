package org.spongycastle.asn1.ua;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;

public class DSTU4145Params
  extends ASN1Object
{
  private static final byte[] DEFAULT_DKE = { -87, -42, -21, 69, -15, 60, 112, -126, Byte.MIN_VALUE, -60, -106, 123, 35, 31, 94, -83, -10, 88, -21, -92, -64, 55, 41, 29, 56, -39, 107, -16, 37, -54, 78, 23, -8, -23, 114, 13, -58, 21, -76, 58, 40, -105, 95, 11, -63, -34, -93, 100, 56, -75, 100, -22, 44, 23, -97, -48, 18, 62, 109, -72, -6, -59, 121, 4 };
  



  private ASN1ObjectIdentifier namedCurve;
  


  private DSTU4145ECBinary ecbinary;
  


  private byte[] dke = DEFAULT_DKE;
  
  public DSTU4145Params(ASN1ObjectIdentifier namedCurve)
  {
    this.namedCurve = namedCurve;
  }
  
  public DSTU4145Params(ASN1ObjectIdentifier namedCurve, byte[] dke)
  {
    this.namedCurve = namedCurve;
    this.dke = Arrays.clone(dke);
  }
  
  public DSTU4145Params(DSTU4145ECBinary ecbinary)
  {
    this.ecbinary = ecbinary;
  }
  
  public boolean isNamedCurve()
  {
    return namedCurve != null;
  }
  
  public DSTU4145ECBinary getECBinary()
  {
    return ecbinary;
  }
  
  public byte[] getDKE()
  {
    return dke;
  }
  
  public static byte[] getDefaultDKE()
  {
    return DEFAULT_DKE;
  }
  
  public ASN1ObjectIdentifier getNamedCurve()
  {
    return namedCurve;
  }
  
  public static DSTU4145Params getInstance(Object obj)
  {
    if ((obj instanceof DSTU4145Params))
    {
      return (DSTU4145Params)obj;
    }
    
    if (obj != null)
    {
      ASN1Sequence seq = ASN1Sequence.getInstance(obj);
      DSTU4145Params params;
      DSTU4145Params params;
      if ((seq.getObjectAt(0) instanceof ASN1ObjectIdentifier))
      {
        params = new DSTU4145Params(ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0)));
      }
      else
      {
        params = new DSTU4145Params(DSTU4145ECBinary.getInstance(seq.getObjectAt(0)));
      }
      
      if (seq.size() == 2)
      {
        dke = ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets();
        if (dke.length != DEFAULT_DKE.length)
        {
          throw new IllegalArgumentException("object parse error");
        }
      }
      
      return params;
    }
    
    throw new IllegalArgumentException("object parse error");
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (namedCurve != null)
    {
      v.add(namedCurve);
    }
    else
    {
      v.add(ecbinary);
    }
    
    if (!Arrays.areEqual(dke, DEFAULT_DKE))
    {
      v.add(new DEROctetString(dke));
    }
    
    return new DERSequence(v);
  }
}
