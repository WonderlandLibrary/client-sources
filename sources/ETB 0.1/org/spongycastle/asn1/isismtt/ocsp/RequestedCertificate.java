package org.spongycastle.asn1.isismtt.ocsp;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Certificate;



































public class RequestedCertificate
  extends ASN1Object
  implements ASN1Choice
{
  public static final int certificate = -1;
  public static final int publicKeyCertificate = 0;
  public static final int attributeCertificate = 1;
  private Certificate cert;
  private byte[] publicKeyCert;
  private byte[] attributeCert;
  
  public static RequestedCertificate getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof RequestedCertificate)))
    {
      return (RequestedCertificate)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new RequestedCertificate(Certificate.getInstance(obj));
    }
    if ((obj instanceof ASN1TaggedObject))
    {
      return new RequestedCertificate((ASN1TaggedObject)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  
  public static RequestedCertificate getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    if (!explicit)
    {
      throw new IllegalArgumentException("choice item must be explicitly tagged");
    }
    
    return getInstance(obj.getObject());
  }
  
  private RequestedCertificate(ASN1TaggedObject tagged)
  {
    if (tagged.getTagNo() == 0)
    {
      publicKeyCert = ASN1OctetString.getInstance(tagged, true).getOctets();
    }
    else if (tagged.getTagNo() == 1)
    {
      attributeCert = ASN1OctetString.getInstance(tagged, true).getOctets();
    }
    else
    {
      throw new IllegalArgumentException("unknown tag number: " + tagged.getTagNo());
    }
  }
  







  public RequestedCertificate(Certificate certificate)
  {
    cert = certificate;
  }
  
  public RequestedCertificate(int type, byte[] certificateOctets)
  {
    this(new DERTaggedObject(type, new DEROctetString(certificateOctets)));
  }
  
  public int getType()
  {
    if (cert != null)
    {
      return -1;
    }
    if (publicKeyCert != null)
    {
      return 0;
    }
    return 1;
  }
  
  public byte[] getCertificateBytes()
  {
    if (cert != null)
    {
      try
      {
        return cert.getEncoded();
      }
      catch (IOException e)
      {
        throw new IllegalStateException("can't decode certificate: " + e);
      }
    }
    if (publicKeyCert != null)
    {
      return publicKeyCert;
    }
    return attributeCert;
  }
  














  public ASN1Primitive toASN1Primitive()
  {
    if (publicKeyCert != null)
    {
      return new DERTaggedObject(0, new DEROctetString(publicKeyCert));
    }
    if (attributeCert != null)
    {
      return new DERTaggedObject(1, new DEROctetString(attributeCert));
    }
    return cert.toASN1Primitive();
  }
}
