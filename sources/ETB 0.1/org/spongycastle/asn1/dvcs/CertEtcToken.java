package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.cms.ContentInfo;
import org.spongycastle.asn1.ess.ESSCertID;
import org.spongycastle.asn1.ocsp.CertID;
import org.spongycastle.asn1.ocsp.CertStatus;
import org.spongycastle.asn1.ocsp.OCSPResponse;
import org.spongycastle.asn1.smime.SMIMECapabilities;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extension;


















public class CertEtcToken
  extends ASN1Object
  implements ASN1Choice
{
  public static final int TAG_CERTIFICATE = 0;
  public static final int TAG_ESSCERTID = 1;
  public static final int TAG_PKISTATUS = 2;
  public static final int TAG_ASSERTION = 3;
  public static final int TAG_CRL = 4;
  public static final int TAG_OCSPCERTSTATUS = 5;
  public static final int TAG_OCSPCERTID = 6;
  public static final int TAG_OCSPRESPONSE = 7;
  public static final int TAG_CAPABILITIES = 8;
  private static final boolean[] explicit = { false, true, false, true, false, true, false, false, true };
  
  private int tagNo;
  
  private ASN1Encodable value;
  
  private Extension extension;
  

  public CertEtcToken(int tagNo, ASN1Encodable value)
  {
    this.tagNo = tagNo;
    this.value = value;
  }
  
  public CertEtcToken(Extension extension)
  {
    tagNo = -1;
    this.extension = extension;
  }
  
  private CertEtcToken(ASN1TaggedObject choice)
  {
    tagNo = choice.getTagNo();
    
    switch (tagNo)
    {
    case 0: 
      value = Certificate.getInstance(choice, false);
      break;
    case 1: 
      value = ESSCertID.getInstance(choice.getObject());
      break;
    case 2: 
      value = PKIStatusInfo.getInstance(choice, false);
      break;
    case 3: 
      value = ContentInfo.getInstance(choice.getObject());
      break;
    case 4: 
      value = CertificateList.getInstance(choice, false);
      break;
    case 5: 
      value = CertStatus.getInstance(choice.getObject());
      break;
    case 6: 
      value = CertID.getInstance(choice, false);
      break;
    case 7: 
      value = OCSPResponse.getInstance(choice, false);
      break;
    case 8: 
      value = SMIMECapabilities.getInstance(choice.getObject());
      break;
    default: 
      throw new IllegalArgumentException("Unknown tag: " + tagNo);
    }
  }
  
  public static CertEtcToken getInstance(Object obj)
  {
    if ((obj instanceof CertEtcToken))
    {
      return (CertEtcToken)obj;
    }
    if ((obj instanceof ASN1TaggedObject))
    {
      return new CertEtcToken((ASN1TaggedObject)obj);
    }
    if (obj != null)
    {
      return new CertEtcToken(Extension.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (extension == null)
    {
      return new DERTaggedObject(explicit[tagNo], tagNo, value);
    }
    

    return extension.toASN1Primitive();
  }
  

  public int getTagNo()
  {
    return tagNo;
  }
  
  public ASN1Encodable getValue()
  {
    return value;
  }
  
  public Extension getExtension()
  {
    return extension;
  }
  
  public String toString()
  {
    return "CertEtcToken {\n" + value + "}\n";
  }
  
  public static CertEtcToken[] arrayFromSequence(ASN1Sequence seq)
  {
    CertEtcToken[] tmp = new CertEtcToken[seq.size()];
    
    for (int i = 0; i != tmp.length; i++)
    {
      tmp[i] = getInstance(seq.getObjectAt(i));
    }
    
    return tmp;
  }
}
