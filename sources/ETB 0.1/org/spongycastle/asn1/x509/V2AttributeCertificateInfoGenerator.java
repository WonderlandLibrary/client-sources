package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;




















public class V2AttributeCertificateInfoGenerator
{
  private ASN1Integer version;
  private Holder holder;
  private AttCertIssuer issuer;
  private AlgorithmIdentifier signature;
  private ASN1Integer serialNumber;
  private ASN1EncodableVector attributes;
  private DERBitString issuerUniqueID;
  private Extensions extensions;
  private ASN1GeneralizedTime startDate;
  private ASN1GeneralizedTime endDate;
  
  public V2AttributeCertificateInfoGenerator()
  {
    version = new ASN1Integer(1L);
    attributes = new ASN1EncodableVector();
  }
  
  public void setHolder(Holder holder)
  {
    this.holder = holder;
  }
  
  public void addAttribute(String oid, ASN1Encodable value)
  {
    attributes.add(new Attribute(new ASN1ObjectIdentifier(oid), new DERSet(value)));
  }
  



  public void addAttribute(Attribute attribute)
  {
    attributes.add(attribute);
  }
  

  public void setSerialNumber(ASN1Integer serialNumber)
  {
    this.serialNumber = serialNumber;
  }
  

  public void setSignature(AlgorithmIdentifier signature)
  {
    this.signature = signature;
  }
  

  public void setIssuer(AttCertIssuer issuer)
  {
    this.issuer = issuer;
  }
  

  public void setStartDate(ASN1GeneralizedTime startDate)
  {
    this.startDate = startDate;
  }
  

  public void setEndDate(ASN1GeneralizedTime endDate)
  {
    this.endDate = endDate;
  }
  

  public void setIssuerUniqueID(DERBitString issuerUniqueID)
  {
    this.issuerUniqueID = issuerUniqueID;
  }
  


  /**
   * @deprecated
   */
  public void setExtensions(X509Extensions extensions)
  {
    this.extensions = Extensions.getInstance(extensions.toASN1Primitive());
  }
  

  public void setExtensions(Extensions extensions)
  {
    this.extensions = extensions;
  }
  
  public AttributeCertificateInfo generateAttributeCertificateInfo()
  {
    if ((serialNumber == null) || (signature == null) || (issuer == null) || (startDate == null) || (endDate == null) || (holder == null) || (attributes == null))
    {


      throw new IllegalStateException("not all mandatory fields set in V2 AttributeCertificateInfo generator");
    }
    
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(holder);
    v.add(issuer);
    v.add(signature);
    v.add(serialNumber);
    



    AttCertValidityPeriod validity = new AttCertValidityPeriod(startDate, endDate);
    v.add(validity);
    

    v.add(new DERSequence(attributes));
    
    if (issuerUniqueID != null)
    {
      v.add(issuerUniqueID);
    }
    
    if (extensions != null)
    {
      v.add(extensions);
    }
    
    return AttributeCertificateInfo.getInstance(new DERSequence(v));
  }
}
