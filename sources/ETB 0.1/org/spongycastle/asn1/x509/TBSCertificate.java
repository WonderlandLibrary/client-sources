package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.x500.X500Name;

























public class TBSCertificate
  extends ASN1Object
{
  ASN1Sequence seq;
  ASN1Integer version;
  ASN1Integer serialNumber;
  AlgorithmIdentifier signature;
  X500Name issuer;
  Time startDate;
  Time endDate;
  X500Name subject;
  SubjectPublicKeyInfo subjectPublicKeyInfo;
  DERBitString issuerUniqueId;
  DERBitString subjectUniqueId;
  Extensions extensions;
  
  public static TBSCertificate getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static TBSCertificate getInstance(Object obj)
  {
    if ((obj instanceof TBSCertificate))
    {
      return (TBSCertificate)obj;
    }
    if (obj != null)
    {
      return new TBSCertificate(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private TBSCertificate(ASN1Sequence seq)
  {
    int seqStart = 0;
    
    this.seq = seq;
    



    if ((seq.getObjectAt(0) instanceof ASN1TaggedObject))
    {
      version = ASN1Integer.getInstance((ASN1TaggedObject)seq.getObjectAt(0), true);
    }
    else
    {
      seqStart = -1;
      version = new ASN1Integer(0L);
    }
    
    boolean isV1 = false;
    boolean isV2 = false;
    
    if (version.getValue().equals(BigInteger.valueOf(0L)))
    {
      isV1 = true;
    }
    else if (version.getValue().equals(BigInteger.valueOf(1L)))
    {
      isV2 = true;
    }
    else if (!version.getValue().equals(BigInteger.valueOf(2L)))
    {
      throw new IllegalArgumentException("version number not recognised");
    }
    
    serialNumber = ASN1Integer.getInstance(seq.getObjectAt(seqStart + 1));
    
    signature = AlgorithmIdentifier.getInstance(seq.getObjectAt(seqStart + 2));
    issuer = X500Name.getInstance(seq.getObjectAt(seqStart + 3));
    



    ASN1Sequence dates = (ASN1Sequence)seq.getObjectAt(seqStart + 4);
    
    startDate = Time.getInstance(dates.getObjectAt(0));
    endDate = Time.getInstance(dates.getObjectAt(1));
    
    subject = X500Name.getInstance(seq.getObjectAt(seqStart + 5));
    



    subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(seq.getObjectAt(seqStart + 6));
    
    int extras = seq.size() - (seqStart + 6) - 1;
    if ((extras != 0) && (isV1))
    {
      throw new IllegalArgumentException("version 1 certificate contains extra data");
    }
    
    while (extras > 0)
    {
      ASN1TaggedObject extra = (ASN1TaggedObject)seq.getObjectAt(seqStart + 6 + extras);
      
      switch (extra.getTagNo())
      {
      case 1: 
        issuerUniqueId = DERBitString.getInstance(extra, false);
        break;
      case 2: 
        subjectUniqueId = DERBitString.getInstance(extra, false);
        break;
      case 3: 
        if (isV2)
        {
          throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
        }
        extensions = Extensions.getInstance(ASN1Sequence.getInstance(extra, true));
      }
      extras--;
    }
  }
  
  public int getVersionNumber()
  {
    return version.getValue().intValue() + 1;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return serialNumber;
  }
  
  public AlgorithmIdentifier getSignature()
  {
    return signature;
  }
  
  public X500Name getIssuer()
  {
    return issuer;
  }
  
  public Time getStartDate()
  {
    return startDate;
  }
  
  public Time getEndDate()
  {
    return endDate;
  }
  
  public X500Name getSubject()
  {
    return subject;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return subjectPublicKeyInfo;
  }
  
  public DERBitString getIssuerUniqueId()
  {
    return issuerUniqueId;
  }
  
  public DERBitString getSubjectUniqueId()
  {
    return subjectUniqueId;
  }
  
  public Extensions getExtensions()
  {
    return extensions;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return seq;
  }
}
