package org.spongycastle.asn1.esf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.ocsp.BasicOCSPResponse;
import org.spongycastle.asn1.x509.CertificateList;











public class RevocationValues
  extends ASN1Object
{
  private ASN1Sequence crlVals;
  private ASN1Sequence ocspVals;
  private OtherRevVals otherRevVals;
  
  public static RevocationValues getInstance(Object obj)
  {
    if ((obj instanceof RevocationValues))
    {
      return (RevocationValues)obj;
    }
    if (obj != null)
    {
      return new RevocationValues(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  private RevocationValues(ASN1Sequence seq)
  {
    if (seq.size() > 3)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    Enumeration e = seq.getObjects();
    while (e.hasMoreElements())
    {
      ASN1TaggedObject o = (ASN1TaggedObject)e.nextElement();
      switch (o.getTagNo())
      {
      case 0: 
        ASN1Sequence crlValsSeq = (ASN1Sequence)o.getObject();
        Enumeration crlValsEnum = crlValsSeq.getObjects();
        while (crlValsEnum.hasMoreElements())
        {
          CertificateList.getInstance(crlValsEnum.nextElement());
        }
        crlVals = crlValsSeq;
        break;
      case 1: 
        ASN1Sequence ocspValsSeq = (ASN1Sequence)o.getObject();
        Enumeration ocspValsEnum = ocspValsSeq.getObjects();
        while (ocspValsEnum.hasMoreElements())
        {
          BasicOCSPResponse.getInstance(ocspValsEnum.nextElement());
        }
        ocspVals = ocspValsSeq;
        break;
      case 2: 
        otherRevVals = OtherRevVals.getInstance(o.getObject());
        break;
      
      default: 
        throw new IllegalArgumentException("invalid tag: " + o.getTagNo());
      }
      
    }
  }
  
  public RevocationValues(CertificateList[] crlVals, BasicOCSPResponse[] ocspVals, OtherRevVals otherRevVals)
  {
    if (null != crlVals)
    {
      this.crlVals = new DERSequence(crlVals);
    }
    if (null != ocspVals)
    {
      this.ocspVals = new DERSequence(ocspVals);
    }
    this.otherRevVals = otherRevVals;
  }
  
  public CertificateList[] getCrlVals()
  {
    if (null == crlVals)
    {
      return new CertificateList[0];
    }
    CertificateList[] result = new CertificateList[crlVals.size()];
    for (int idx = 0; idx < result.length; idx++)
    {
      result[idx] = CertificateList.getInstance(crlVals
        .getObjectAt(idx));
    }
    return result;
  }
  
  public BasicOCSPResponse[] getOcspVals()
  {
    if (null == ocspVals)
    {
      return new BasicOCSPResponse[0];
    }
    BasicOCSPResponse[] result = new BasicOCSPResponse[ocspVals.size()];
    for (int idx = 0; idx < result.length; idx++)
    {
      result[idx] = BasicOCSPResponse.getInstance(ocspVals
        .getObjectAt(idx));
    }
    return result;
  }
  
  public OtherRevVals getOtherRevVals()
  {
    return otherRevVals;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    if (null != crlVals)
    {
      v.add(new DERTaggedObject(true, 0, crlVals));
    }
    if (null != ocspVals)
    {
      v.add(new DERTaggedObject(true, 1, ocspVals));
    }
    if (null != otherRevVals)
    {
      v.add(new DERTaggedObject(true, 2, otherRevVals.toASN1Primitive()));
    }
    return new DERSequence(v);
  }
}
