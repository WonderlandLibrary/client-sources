package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;

public class RevDetails
  extends ASN1Object
{
  private CertTemplate certDetails;
  private Extensions crlEntryDetails;
  
  private RevDetails(ASN1Sequence seq)
  {
    certDetails = CertTemplate.getInstance(seq.getObjectAt(0));
    if (seq.size() > 1)
    {
      crlEntryDetails = Extensions.getInstance(seq.getObjectAt(1));
    }
  }
  
  public static RevDetails getInstance(Object o)
  {
    if ((o instanceof RevDetails))
    {
      return (RevDetails)o;
    }
    
    if (o != null)
    {
      return new RevDetails(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public RevDetails(CertTemplate certDetails)
  {
    this.certDetails = certDetails;
  }
  


  /**
   * @deprecated
   */
  public RevDetails(CertTemplate certDetails, X509Extensions crlEntryDetails)
  {
    this.certDetails = certDetails;
    this.crlEntryDetails = Extensions.getInstance(crlEntryDetails.toASN1Primitive());
  }
  
  public RevDetails(CertTemplate certDetails, Extensions crlEntryDetails)
  {
    this.certDetails = certDetails;
    this.crlEntryDetails = crlEntryDetails;
  }
  
  public CertTemplate getCertDetails()
  {
    return certDetails;
  }
  
  public Extensions getCrlEntryDetails()
  {
    return crlEntryDetails;
  }
  













  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certDetails);
    
    if (crlEntryDetails != null)
    {
      v.add(crlEntryDetails);
    }
    
    return new DERSequence(v);
  }
}
