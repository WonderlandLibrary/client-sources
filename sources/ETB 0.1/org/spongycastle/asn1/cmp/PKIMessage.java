package org.spongycastle.asn1.cmp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;


public class PKIMessage
  extends ASN1Object
{
  private PKIHeader header;
  private PKIBody body;
  private DERBitString protection;
  private ASN1Sequence extraCerts;
  
  private PKIMessage(ASN1Sequence seq)
  {
    Enumeration en = seq.getObjects();
    
    header = PKIHeader.getInstance(en.nextElement());
    body = PKIBody.getInstance(en.nextElement());
    
    while (en.hasMoreElements())
    {
      ASN1TaggedObject tObj = (ASN1TaggedObject)en.nextElement();
      
      if (tObj.getTagNo() == 0)
      {
        protection = DERBitString.getInstance(tObj, true);
      }
      else
      {
        extraCerts = ASN1Sequence.getInstance(tObj, true);
      }
    }
  }
  
  public static PKIMessage getInstance(Object o)
  {
    if ((o instanceof PKIMessage))
    {
      return (PKIMessage)o;
    }
    if (o != null)
    {
      return new PKIMessage(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  












  public PKIMessage(PKIHeader header, PKIBody body, DERBitString protection, CMPCertificate[] extraCerts)
  {
    this.header = header;
    this.body = body;
    this.protection = protection;
    if (extraCerts != null)
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      for (int i = 0; i < extraCerts.length; i++)
      {
        v.add(extraCerts[i]);
      }
      this.extraCerts = new DERSequence(v);
    }
  }
  



  public PKIMessage(PKIHeader header, PKIBody body, DERBitString protection)
  {
    this(header, body, protection, null);
  }
  


  public PKIMessage(PKIHeader header, PKIBody body)
  {
    this(header, body, null, null);
  }
  
  public PKIHeader getHeader()
  {
    return header;
  }
  
  public PKIBody getBody()
  {
    return body;
  }
  
  public DERBitString getProtection()
  {
    return protection;
  }
  
  public CMPCertificate[] getExtraCerts()
  {
    if (extraCerts == null)
    {
      return null;
    }
    
    CMPCertificate[] results = new CMPCertificate[extraCerts.size()];
    
    for (int i = 0; i < results.length; i++)
    {
      results[i] = CMPCertificate.getInstance(extraCerts.getObjectAt(i));
    }
    return results;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(header);
    v.add(body);
    
    addOptional(v, 0, protection);
    addOptional(v, 1, extraCerts);
    
    return new DERSequence(v);
  }
  
  private void addOptional(ASN1EncodableVector v, int tagNo, ASN1Encodable obj)
  {
    if (obj != null)
    {
      v.add(new DERTaggedObject(true, tagNo, obj));
    }
  }
}
