package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.PolicyInformation;


public class SigningCertificate
  extends ASN1Object
{
  ASN1Sequence certs;
  ASN1Sequence policies;
  
  public static SigningCertificate getInstance(Object o)
  {
    if ((o instanceof SigningCertificate))
    {
      return (SigningCertificate)o;
    }
    if (o != null)
    {
      return new SigningCertificate(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  



  private SigningCertificate(ASN1Sequence seq)
  {
    if ((seq.size() < 1) || (seq.size() > 2))
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    certs = ASN1Sequence.getInstance(seq.getObjectAt(0));
    
    if (seq.size() > 1)
    {
      policies = ASN1Sequence.getInstance(seq.getObjectAt(1));
    }
  }
  

  public SigningCertificate(ESSCertID essCertID)
  {
    certs = new DERSequence(essCertID);
  }
  
  public ESSCertID[] getCerts()
  {
    ESSCertID[] cs = new ESSCertID[certs.size()];
    
    for (int i = 0; i != certs.size(); i++)
    {
      cs[i] = ESSCertID.getInstance(certs.getObjectAt(i));
    }
    
    return cs;
  }
  
  public PolicyInformation[] getPolicies()
  {
    if (policies == null)
    {
      return null;
    }
    
    PolicyInformation[] ps = new PolicyInformation[policies.size()];
    
    for (int i = 0; i != policies.size(); i++)
    {
      ps[i] = PolicyInformation.getInstance(policies.getObjectAt(i));
    }
    
    return ps;
  }
  












  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certs);
    
    if (policies != null)
    {
      v.add(policies);
    }
    
    return new DERSequence(v);
  }
}
