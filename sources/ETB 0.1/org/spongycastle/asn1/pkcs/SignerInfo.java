package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;






public class SignerInfo
  extends ASN1Object
{
  private ASN1Integer version;
  private IssuerAndSerialNumber issuerAndSerialNumber;
  private AlgorithmIdentifier digAlgorithm;
  private ASN1Set authenticatedAttributes;
  private AlgorithmIdentifier digEncryptionAlgorithm;
  private ASN1OctetString encryptedDigest;
  private ASN1Set unauthenticatedAttributes;
  
  public static SignerInfo getInstance(Object o)
  {
    if ((o instanceof SignerInfo))
    {
      return (SignerInfo)o;
    }
    if ((o instanceof ASN1Sequence))
    {
      return new SignerInfo((ASN1Sequence)o);
    }
    
    throw new IllegalArgumentException("unknown object in factory: " + o.getClass().getName());
  }
  







  public SignerInfo(ASN1Integer version, IssuerAndSerialNumber issuerAndSerialNumber, AlgorithmIdentifier digAlgorithm, ASN1Set authenticatedAttributes, AlgorithmIdentifier digEncryptionAlgorithm, ASN1OctetString encryptedDigest, ASN1Set unauthenticatedAttributes)
  {
    this.version = version;
    this.issuerAndSerialNumber = issuerAndSerialNumber;
    this.digAlgorithm = digAlgorithm;
    this.authenticatedAttributes = authenticatedAttributes;
    this.digEncryptionAlgorithm = digEncryptionAlgorithm;
    this.encryptedDigest = encryptedDigest;
    this.unauthenticatedAttributes = unauthenticatedAttributes;
  }
  

  public SignerInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    version = ((ASN1Integer)e.nextElement());
    issuerAndSerialNumber = IssuerAndSerialNumber.getInstance(e.nextElement());
    digAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
    
    Object obj = e.nextElement();
    
    if ((obj instanceof ASN1TaggedObject))
    {
      authenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject)obj, false);
      
      digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
    }
    else
    {
      authenticatedAttributes = null;
      digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(obj);
    }
    
    encryptedDigest = DEROctetString.getInstance(e.nextElement());
    
    if (e.hasMoreElements())
    {
      unauthenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject)e.nextElement(), false);
    }
    else
    {
      unauthenticatedAttributes = null;
    }
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public IssuerAndSerialNumber getIssuerAndSerialNumber()
  {
    return issuerAndSerialNumber;
  }
  
  public ASN1Set getAuthenticatedAttributes()
  {
    return authenticatedAttributes;
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return digAlgorithm;
  }
  
  public ASN1OctetString getEncryptedDigest()
  {
    return encryptedDigest;
  }
  
  public AlgorithmIdentifier getDigestEncryptionAlgorithm()
  {
    return digEncryptionAlgorithm;
  }
  
  public ASN1Set getUnauthenticatedAttributes()
  {
    return unauthenticatedAttributes;
  }
  




















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(issuerAndSerialNumber);
    v.add(digAlgorithm);
    
    if (authenticatedAttributes != null)
    {
      v.add(new DERTaggedObject(false, 0, authenticatedAttributes));
    }
    
    v.add(digEncryptionAlgorithm);
    v.add(encryptedDigest);
    
    if (unauthenticatedAttributes != null)
    {
      v.add(new DERTaggedObject(false, 1, unauthenticatedAttributes));
    }
    
    return new DERSequence(v);
  }
}
