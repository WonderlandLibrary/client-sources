package org.spongycastle.asn1.crmf;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;


public class CertReqMsg
  extends ASN1Object
{
  private CertRequest certReq;
  private ProofOfPossession pop;
  private ASN1Sequence regInfo;
  
  private CertReqMsg(ASN1Sequence seq)
  {
    Enumeration en = seq.getObjects();
    
    certReq = CertRequest.getInstance(en.nextElement());
    while (en.hasMoreElements())
    {
      Object o = en.nextElement();
      
      if (((o instanceof ASN1TaggedObject)) || ((o instanceof ProofOfPossession)))
      {
        pop = ProofOfPossession.getInstance(o);
      }
      else
      {
        regInfo = ASN1Sequence.getInstance(o);
      }
    }
  }
  
  public static CertReqMsg getInstance(Object o)
  {
    if ((o instanceof CertReqMsg))
    {
      return (CertReqMsg)o;
    }
    if (o != null)
    {
      return new CertReqMsg(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  


  public static CertReqMsg getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  









  public CertReqMsg(CertRequest certReq, ProofOfPossession pop, AttributeTypeAndValue[] regInfo)
  {
    if (certReq == null)
    {
      throw new IllegalArgumentException("'certReq' cannot be null");
    }
    
    this.certReq = certReq;
    this.pop = pop;
    
    if (regInfo != null)
    {
      this.regInfo = new DERSequence(regInfo);
    }
  }
  
  public CertRequest getCertReq()
  {
    return certReq;
  }
  

  /**
   * @deprecated
   */
  public ProofOfPossession getPop()
  {
    return pop;
  }
  

  public ProofOfPossession getPopo()
  {
    return pop;
  }
  
  public AttributeTypeAndValue[] getRegInfo()
  {
    if (regInfo == null)
    {
      return null;
    }
    
    AttributeTypeAndValue[] results = new AttributeTypeAndValue[regInfo.size()];
    
    for (int i = 0; i != results.length; i++)
    {
      results[i] = AttributeTypeAndValue.getInstance(regInfo.getObjectAt(i));
    }
    
    return results;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certReq);
    
    addOptional(v, pop);
    addOptional(v, regInfo);
    
    return new DERSequence(v);
  }
  
  private void addOptional(ASN1EncodableVector v, ASN1Encodable obj)
  {
    if (obj != null)
    {
      v.add(obj);
    }
  }
}
