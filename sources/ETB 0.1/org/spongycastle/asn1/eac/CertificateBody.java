package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ApplicationSpecific;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;






























public class CertificateBody
  extends ASN1Object
{
  ASN1InputStream seq;
  private DERApplicationSpecific certificateProfileIdentifier;
  private DERApplicationSpecific certificationAuthorityReference;
  private PublicKeyDataObject publicKey;
  private DERApplicationSpecific certificateHolderReference;
  private CertificateHolderAuthorization certificateHolderAuthorization;
  private DERApplicationSpecific certificateEffectiveDate;
  private DERApplicationSpecific certificateExpirationDate;
  private int certificateType = 0;
  
  private static final int CPI = 1;
  private static final int CAR = 2;
  private static final int PK = 4;
  private static final int CHR = 8;
  private static final int CHA = 16;
  private static final int CEfD = 32;
  private static final int CExD = 64;
  public static final int profileType = 127;
  public static final int requestType = 13;
  
  private void setIso7816CertificateBody(ASN1ApplicationSpecific appSpe)
    throws IOException
  {
    byte[] content;
    if (appSpe.getApplicationTag() == 78)
    {
      content = appSpe.getContents();
    }
    else
    {
      throw new IOException("Bad tag : not an iso7816 CERTIFICATE_CONTENT_TEMPLATE"); }
    byte[] content;
    ASN1InputStream aIS = new ASN1InputStream(content);
    ASN1Primitive obj;
    while ((obj = aIS.readObject()) != null)
    {
      DERApplicationSpecific aSpe;
      
      if ((obj instanceof DERApplicationSpecific))
      {
        aSpe = (DERApplicationSpecific)obj;
      }
      else
      {
        throw new IOException("Not a valid iso7816 content : not a DERApplicationSpecific Object :" + EACTags.encodeTag(appSpe) + obj.getClass()); }
      DERApplicationSpecific aSpe;
      switch (aSpe.getApplicationTag())
      {
      case 41: 
        setCertificateProfileIdentifier(aSpe);
        break;
      case 2: 
        setCertificationAuthorityReference(aSpe);
        break;
      case 73: 
        setPublicKey(PublicKeyDataObject.getInstance(aSpe.getObject(16)));
        break;
      case 32: 
        setCertificateHolderReference(aSpe);
        break;
      case 76: 
        setCertificateHolderAuthorization(new CertificateHolderAuthorization(aSpe));
        break;
      case 37: 
        setCertificateEffectiveDate(aSpe);
        break;
      case 36: 
        setCertificateExpirationDate(aSpe);
        break;
      default: 
        certificateType = 0;
        throw new IOException("Not a valid iso7816 DERApplicationSpecific tag " + aSpe.getApplicationTag());
      }
    }
    aIS.close();
  }
  




















  public CertificateBody(DERApplicationSpecific certificateProfileIdentifier, CertificationAuthorityReference certificationAuthorityReference, PublicKeyDataObject publicKey, CertificateHolderReference certificateHolderReference, CertificateHolderAuthorization certificateHolderAuthorization, PackedDate certificateEffectiveDate, PackedDate certificateExpirationDate)
  {
    setCertificateProfileIdentifier(certificateProfileIdentifier);
    setCertificationAuthorityReference(new DERApplicationSpecific(2, certificationAuthorityReference
      .getEncoded()));
    setPublicKey(publicKey);
    setCertificateHolderReference(new DERApplicationSpecific(32, certificateHolderReference
      .getEncoded()));
    setCertificateHolderAuthorization(certificateHolderAuthorization);
    try
    {
      setCertificateEffectiveDate(new DERApplicationSpecific(false, 37, new DEROctetString(certificateEffectiveDate
        .getEncoding())));
      setCertificateExpirationDate(new DERApplicationSpecific(false, 36, new DEROctetString(certificateExpirationDate
        .getEncoding())));
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("unable to encode dates: " + e.getMessage());
    }
  }
  






  private CertificateBody(ASN1ApplicationSpecific obj)
    throws IOException
  {
    setIso7816CertificateBody(obj);
  }
  






  private ASN1Primitive profileToASN1Object()
    throws IOException
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certificateProfileIdentifier);
    v.add(certificationAuthorityReference);
    v.add(new DERApplicationSpecific(false, 73, publicKey));
    v.add(certificateHolderReference);
    v.add(certificateHolderAuthorization);
    v.add(certificateEffectiveDate);
    v.add(certificateExpirationDate);
    return new DERApplicationSpecific(78, v);
  }
  
  private void setCertificateProfileIdentifier(DERApplicationSpecific certificateProfileIdentifier)
    throws IllegalArgumentException
  {
    if (certificateProfileIdentifier.getApplicationTag() == 41)
    {
      this.certificateProfileIdentifier = certificateProfileIdentifier;
      certificateType |= 0x1;
    }
    else
    {
      throw new IllegalArgumentException("Not an Iso7816Tags.INTERCHANGE_PROFILE tag :" + EACTags.encodeTag(certificateProfileIdentifier));
    }
  }
  
  private void setCertificateHolderReference(DERApplicationSpecific certificateHolderReference)
    throws IllegalArgumentException
  {
    if (certificateHolderReference.getApplicationTag() == 32)
    {
      this.certificateHolderReference = certificateHolderReference;
      certificateType |= 0x8;
    }
    else
    {
      throw new IllegalArgumentException("Not an Iso7816Tags.CARDHOLDER_NAME tag");
    }
  }
  








  private void setCertificationAuthorityReference(DERApplicationSpecific certificationAuthorityReference)
    throws IllegalArgumentException
  {
    if (certificationAuthorityReference.getApplicationTag() == 2)
    {
      this.certificationAuthorityReference = certificationAuthorityReference;
      certificateType |= 0x2;
    }
    else
    {
      throw new IllegalArgumentException("Not an Iso7816Tags.ISSUER_IDENTIFICATION_NUMBER tag");
    }
  }
  






  private void setPublicKey(PublicKeyDataObject publicKey)
  {
    this.publicKey = PublicKeyDataObject.getInstance(publicKey);
    certificateType |= 0x4;
  }
  






  private ASN1Primitive requestToASN1Object()
    throws IOException
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certificateProfileIdentifier);
    v.add(new DERApplicationSpecific(false, 73, publicKey));
    v.add(certificateHolderReference);
    return new DERApplicationSpecific(78, v);
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    try
    {
      if (certificateType == 127)
      {
        return profileToASN1Object();
      }
      if (certificateType == 13)
      {
        return requestToASN1Object();
      }
    }
    catch (IOException e)
    {
      return null;
    }
    return null;
  }
  





  public int getCertificateType()
  {
    return certificateType;
  }
  







  public static CertificateBody getInstance(Object obj)
    throws IOException
  {
    if ((obj instanceof CertificateBody))
    {
      return (CertificateBody)obj;
    }
    if (obj != null)
    {
      return new CertificateBody(ASN1ApplicationSpecific.getInstance(obj));
    }
    
    return null;
  }
  



  public PackedDate getCertificateEffectiveDate()
  {
    if ((certificateType & 0x20) == 32)
    {

      return new PackedDate(certificateEffectiveDate.getContents());
    }
    return null;
  }
  






  private void setCertificateEffectiveDate(DERApplicationSpecific ced)
    throws IllegalArgumentException
  {
    if (ced.getApplicationTag() == 37)
    {
      certificateEffectiveDate = ced;
      certificateType |= 0x20;
    }
    else
    {
      throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EFFECTIVE_DATE tag :" + EACTags.encodeTag(ced));
    }
  }
  



  public PackedDate getCertificateExpirationDate()
    throws IOException
  {
    if ((certificateType & 0x40) == 64)
    {

      return new PackedDate(certificateExpirationDate.getContents());
    }
    throw new IOException("certificate Expiration Date not set");
  }
  






  private void setCertificateExpirationDate(DERApplicationSpecific ced)
    throws IllegalArgumentException
  {
    if (ced.getApplicationTag() == 36)
    {
      certificateExpirationDate = ced;
      certificateType |= 0x40;
    }
    else
    {
      throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EXPIRATION_DATE tag");
    }
  }
  








  public CertificateHolderAuthorization getCertificateHolderAuthorization()
    throws IOException
  {
    if ((certificateType & 0x10) == 16)
    {

      return certificateHolderAuthorization;
    }
    throw new IOException("Certificate Holder Authorisation not set");
  }
  






  private void setCertificateHolderAuthorization(CertificateHolderAuthorization cha)
  {
    certificateHolderAuthorization = cha;
    certificateType |= 0x10;
  }
  





  public CertificateHolderReference getCertificateHolderReference()
  {
    return new CertificateHolderReference(certificateHolderReference.getContents());
  }
  





  public DERApplicationSpecific getCertificateProfileIdentifier()
  {
    return certificateProfileIdentifier;
  }
  






  public CertificationAuthorityReference getCertificationAuthorityReference()
    throws IOException
  {
    if ((certificateType & 0x2) == 2)
    {

      return new CertificationAuthorityReference(certificationAuthorityReference.getContents());
    }
    throw new IOException("Certification authority reference not set");
  }
  



  public PublicKeyDataObject getPublicKey()
  {
    return publicKey;
  }
}
