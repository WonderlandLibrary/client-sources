package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;


public class AttributeCertificateInfo
  extends ASN1Object
{
  private ASN1Integer version;
  private Holder holder;
  private AttCertIssuer issuer;
  private AlgorithmIdentifier signature;
  private ASN1Integer serialNumber;
  private AttCertValidityPeriod attrCertValidityPeriod;
  private ASN1Sequence attributes;
  private DERBitString issuerUniqueID;
  private Extensions extensions;
  
  public static AttributeCertificateInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static AttributeCertificateInfo getInstance(Object obj)
  {
    if ((obj instanceof AttributeCertificateInfo))
    {
      return (AttributeCertificateInfo)obj;
    }
    if (obj != null)
    {
      return new AttributeCertificateInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private AttributeCertificateInfo(ASN1Sequence seq)
  {
    if ((seq.size() < 6) || (seq.size() > 9))
    {
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    int start;
    int start;
    if ((seq.getObjectAt(0) instanceof ASN1Integer))
    {
      version = ASN1Integer.getInstance(seq.getObjectAt(0));
      start = 1;
    }
    else
    {
      version = new ASN1Integer(0L);
      start = 0;
    }
    
    holder = Holder.getInstance(seq.getObjectAt(start));
    issuer = AttCertIssuer.getInstance(seq.getObjectAt(start + 1));
    signature = AlgorithmIdentifier.getInstance(seq.getObjectAt(start + 2));
    serialNumber = ASN1Integer.getInstance(seq.getObjectAt(start + 3));
    attrCertValidityPeriod = AttCertValidityPeriod.getInstance(seq.getObjectAt(start + 4));
    attributes = ASN1Sequence.getInstance(seq.getObjectAt(start + 5));
    
    for (int i = start + 6; i < seq.size(); i++)
    {
      ASN1Encodable obj = seq.getObjectAt(i);
      
      if ((obj instanceof DERBitString))
      {
        issuerUniqueID = DERBitString.getInstance(seq.getObjectAt(i));
      }
      else if (((obj instanceof ASN1Sequence)) || ((obj instanceof Extensions)))
      {
        extensions = Extensions.getInstance(seq.getObjectAt(i));
      }
    }
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public Holder getHolder()
  {
    return holder;
  }
  
  public AttCertIssuer getIssuer()
  {
    return issuer;
  }
  
  public AlgorithmIdentifier getSignature()
  {
    return signature;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return serialNumber;
  }
  
  public AttCertValidityPeriod getAttrCertValidityPeriod()
  {
    return attrCertValidityPeriod;
  }
  
  public ASN1Sequence getAttributes()
  {
    return attributes;
  }
  
  public DERBitString getIssuerUniqueID()
  {
    return issuerUniqueID;
  }
  
  public Extensions getExtensions()
  {
    return extensions;
  }
  


















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (version.getValue().intValue() != 0)
    {
      v.add(version);
    }
    v.add(holder);
    v.add(issuer);
    v.add(signature);
    v.add(serialNumber);
    v.add(attrCertValidityPeriod);
    v.add(attributes);
    
    if (issuerUniqueID != null)
    {
      v.add(issuerUniqueID);
    }
    
    if (extensions != null)
    {
      v.add(extensions);
    }
    
    return new DERSequence(v);
  }
}
