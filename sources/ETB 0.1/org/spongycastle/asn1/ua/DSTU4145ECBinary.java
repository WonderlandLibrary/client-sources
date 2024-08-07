package org.spongycastle.asn1.ua;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.field.Polynomial;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;

public class DSTU4145ECBinary extends ASN1Object
{
  BigInteger version = BigInteger.valueOf(0L);
  
  DSTU4145BinaryField f;
  ASN1Integer a;
  ASN1OctetString b;
  ASN1Integer n;
  ASN1OctetString bp;
  
  public DSTU4145ECBinary(ECDomainParameters params)
  {
    ECCurve curve = params.getCurve();
    if (!ECAlgorithms.isF2mCurve(curve))
    {
      throw new IllegalArgumentException("only binary domain is possible");
    }
    


    PolynomialExtensionField field = (PolynomialExtensionField)curve.getField();
    int[] exponents = field.getMinimalPolynomial().getExponentsPresent();
    if (exponents.length == 3)
    {
      f = new DSTU4145BinaryField(exponents[2], exponents[1]);
    }
    else if (exponents.length == 5)
    {
      f = new DSTU4145BinaryField(exponents[4], exponents[1], exponents[2], exponents[3]);
    }
    else
    {
      throw new IllegalArgumentException("curve must have a trinomial or pentanomial basis");
    }
    
    a = new ASN1Integer(curve.getA().toBigInteger());
    b = new DEROctetString(curve.getB().getEncoded());
    n = new ASN1Integer(params.getN());
    bp = new DEROctetString(DSTU4145PointEncoder.encodePoint(params.getG()));
  }
  
  private DSTU4145ECBinary(ASN1Sequence seq)
  {
    int index = 0;
    
    if ((seq.getObjectAt(index) instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject taggedVersion = (ASN1TaggedObject)seq.getObjectAt(index);
      if ((taggedVersion.isExplicit()) && (0 == taggedVersion.getTagNo()))
      {
        version = ASN1Integer.getInstance(taggedVersion.getLoadedObject()).getValue();
        index++;
      }
      else
      {
        throw new IllegalArgumentException("object parse error");
      }
    }
    f = DSTU4145BinaryField.getInstance(seq.getObjectAt(index));
    index++;
    a = ASN1Integer.getInstance(seq.getObjectAt(index));
    index++;
    b = ASN1OctetString.getInstance(seq.getObjectAt(index));
    index++;
    n = ASN1Integer.getInstance(seq.getObjectAt(index));
    index++;
    bp = ASN1OctetString.getInstance(seq.getObjectAt(index));
  }
  
  public static DSTU4145ECBinary getInstance(Object obj)
  {
    if ((obj instanceof DSTU4145ECBinary))
    {
      return (DSTU4145ECBinary)obj;
    }
    
    if (obj != null)
    {
      return new DSTU4145ECBinary(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public DSTU4145BinaryField getField()
  {
    return f;
  }
  
  public BigInteger getA()
  {
    return a.getValue();
  }
  
  public byte[] getB()
  {
    return Arrays.clone(b.getOctets());
  }
  
  public BigInteger getN()
  {
    return n.getValue();
  }
  
  public byte[] getG()
  {
    return Arrays.clone(bp.getOctets());
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (0 != version.compareTo(BigInteger.valueOf(0L)))
    {
      v.add(new DERTaggedObject(true, 0, new ASN1Integer(version)));
    }
    v.add(f);
    v.add(a);
    v.add(b);
    v.add(n);
    v.add(bp);
    
    return new DERSequence(v);
  }
}
