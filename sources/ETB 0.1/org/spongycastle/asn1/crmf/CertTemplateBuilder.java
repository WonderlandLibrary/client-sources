package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Extensions;

public class CertTemplateBuilder
{
  private ASN1Integer version;
  private ASN1Integer serialNumber;
  private AlgorithmIdentifier signingAlg;
  private X500Name issuer;
  private OptionalValidity validity;
  private X500Name subject;
  private SubjectPublicKeyInfo publicKey;
  private DERBitString issuerUID;
  private DERBitString subjectUID;
  private Extensions extensions;
  
  public CertTemplateBuilder() {}
  
  public CertTemplateBuilder setVersion(int ver)
  {
    version = new ASN1Integer(ver);
    
    return this;
  }
  
  public CertTemplateBuilder setSerialNumber(ASN1Integer ser)
  {
    serialNumber = ser;
    
    return this;
  }
  
  public CertTemplateBuilder setSigningAlg(AlgorithmIdentifier aid)
  {
    signingAlg = aid;
    
    return this;
  }
  
  public CertTemplateBuilder setIssuer(X500Name name)
  {
    issuer = name;
    
    return this;
  }
  
  public CertTemplateBuilder setValidity(OptionalValidity v)
  {
    validity = v;
    
    return this;
  }
  
  public CertTemplateBuilder setSubject(X500Name name)
  {
    subject = name;
    
    return this;
  }
  
  public CertTemplateBuilder setPublicKey(SubjectPublicKeyInfo spki)
  {
    publicKey = spki;
    
    return this;
  }
  

  public CertTemplateBuilder setIssuerUID(DERBitString uid)
  {
    issuerUID = uid;
    
    return this;
  }
  

  public CertTemplateBuilder setSubjectUID(DERBitString uid)
  {
    subjectUID = uid;
    
    return this;
  }
  
  /**
   * @deprecated
   */
  public CertTemplateBuilder setExtensions(X509Extensions extens)
  {
    return setExtensions(Extensions.getInstance(extens));
  }
  
  public CertTemplateBuilder setExtensions(Extensions extens)
  {
    extensions = extens;
    
    return this;
  }
  
















  public CertTemplate build()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    addOptional(v, 0, false, version);
    addOptional(v, 1, false, serialNumber);
    addOptional(v, 2, false, signingAlg);
    addOptional(v, 3, true, issuer);
    addOptional(v, 4, false, validity);
    addOptional(v, 5, true, subject);
    addOptional(v, 6, false, publicKey);
    addOptional(v, 7, false, issuerUID);
    addOptional(v, 8, false, subjectUID);
    addOptional(v, 9, false, extensions);
    
    return CertTemplate.getInstance(new DERSequence(v));
  }
  
  private void addOptional(ASN1EncodableVector v, int tagNo, boolean isExplicit, ASN1Encodable obj)
  {
    if (obj != null)
    {
      v.add(new org.spongycastle.asn1.DERTaggedObject(isExplicit, tagNo, obj));
    }
  }
}
