package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;



public class SignaturePolicyIdentifier
  extends ASN1Object
{
  private SignaturePolicyId signaturePolicyId;
  private boolean isSignaturePolicyImplied;
  
  public static SignaturePolicyIdentifier getInstance(Object obj)
  {
    if ((obj instanceof SignaturePolicyIdentifier))
    {
      return (SignaturePolicyIdentifier)obj;
    }
    if (((obj instanceof ASN1Null)) || (hasEncodedTagValue(obj, 5)))
    {
      return new SignaturePolicyIdentifier();
    }
    if (obj != null)
    {
      return new SignaturePolicyIdentifier(SignaturePolicyId.getInstance(obj));
    }
    
    return null;
  }
  
  public SignaturePolicyIdentifier()
  {
    isSignaturePolicyImplied = true;
  }
  

  public SignaturePolicyIdentifier(SignaturePolicyId signaturePolicyId)
  {
    this.signaturePolicyId = signaturePolicyId;
    isSignaturePolicyImplied = false;
  }
  
  public SignaturePolicyId getSignaturePolicyId()
  {
    return signaturePolicyId;
  }
  
  public boolean isSignaturePolicyImplied()
  {
    return isSignaturePolicyImplied;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    if (isSignaturePolicyImplied)
    {
      return DERNull.INSTANCE;
    }
    

    return signaturePolicyId.toASN1Primitive();
  }
}
