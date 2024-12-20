package org.spongycastle.asn1.dvcs;

import java.util.Arrays;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.PolicyInformation;

















public class PathProcInput
  extends ASN1Object
{
  private PolicyInformation[] acceptablePolicySet;
  private boolean inhibitPolicyMapping = false;
  private boolean explicitPolicyReqd = false;
  private boolean inhibitAnyPolicy = false;
  
  public PathProcInput(PolicyInformation[] acceptablePolicySet)
  {
    this.acceptablePolicySet = acceptablePolicySet;
  }
  
  public PathProcInput(PolicyInformation[] acceptablePolicySet, boolean inhibitPolicyMapping, boolean explicitPolicyReqd, boolean inhibitAnyPolicy)
  {
    this.acceptablePolicySet = acceptablePolicySet;
    this.inhibitPolicyMapping = inhibitPolicyMapping;
    this.explicitPolicyReqd = explicitPolicyReqd;
    this.inhibitAnyPolicy = inhibitAnyPolicy;
  }
  
  private static PolicyInformation[] fromSequence(ASN1Sequence seq)
  {
    PolicyInformation[] tmp = new PolicyInformation[seq.size()];
    
    for (int i = 0; i != tmp.length; i++)
    {
      tmp[i] = PolicyInformation.getInstance(seq.getObjectAt(i));
    }
    
    return tmp;
  }
  
  public static PathProcInput getInstance(Object obj)
  {
    if ((obj instanceof PathProcInput))
    {
      return (PathProcInput)obj;
    }
    if (obj != null)
    {
      ASN1Sequence seq = ASN1Sequence.getInstance(obj);
      ASN1Sequence policies = ASN1Sequence.getInstance(seq.getObjectAt(0));
      PathProcInput result = new PathProcInput(fromSequence(policies));
      
      for (int i = 1; i < seq.size(); i++)
      {
        Object o = seq.getObjectAt(i);
        
        if ((o instanceof ASN1Boolean))
        {
          ASN1Boolean x = ASN1Boolean.getInstance(o);
          result.setInhibitPolicyMapping(x.isTrue());
        }
        else if ((o instanceof ASN1TaggedObject))
        {
          ASN1TaggedObject t = ASN1TaggedObject.getInstance(o);
          
          switch (t.getTagNo())
          {
          case 0: 
            ASN1Boolean x = ASN1Boolean.getInstance(t, false);
            result.setExplicitPolicyReqd(x.isTrue());
            break;
          case 1: 
            ASN1Boolean x = ASN1Boolean.getInstance(t, false);
            result.setInhibitAnyPolicy(x.isTrue());
            break;
          default: 
            throw new IllegalArgumentException("Unknown tag encountered: " + t.getTagNo());
          }
        }
      }
      return result;
    }
    
    return null;
  }
  


  public static PathProcInput getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    ASN1EncodableVector pV = new ASN1EncodableVector();
    
    for (int i = 0; i != acceptablePolicySet.length; i++)
    {
      pV.add(acceptablePolicySet[i]);
    }
    
    v.add(new DERSequence(pV));
    
    if (inhibitPolicyMapping)
    {
      v.add(ASN1Boolean.getInstance(inhibitPolicyMapping));
    }
    if (explicitPolicyReqd)
    {
      v.add(new DERTaggedObject(false, 0, ASN1Boolean.getInstance(explicitPolicyReqd)));
    }
    if (inhibitAnyPolicy)
    {
      v.add(new DERTaggedObject(false, 1, ASN1Boolean.getInstance(inhibitAnyPolicy)));
    }
    
    return new DERSequence(v);
  }
  
  public String toString()
  {
    return 
      "PathProcInput: {\nacceptablePolicySet: " + Arrays.asList(acceptablePolicySet) + "\ninhibitPolicyMapping: " + inhibitPolicyMapping + "\nexplicitPolicyReqd: " + explicitPolicyReqd + "\ninhibitAnyPolicy: " + inhibitAnyPolicy + "\n}\n";
  }
  




  public PolicyInformation[] getAcceptablePolicySet()
  {
    return acceptablePolicySet;
  }
  
  public boolean isInhibitPolicyMapping()
  {
    return inhibitPolicyMapping;
  }
  
  private void setInhibitPolicyMapping(boolean inhibitPolicyMapping)
  {
    this.inhibitPolicyMapping = inhibitPolicyMapping;
  }
  
  public boolean isExplicitPolicyReqd()
  {
    return explicitPolicyReqd;
  }
  
  private void setExplicitPolicyReqd(boolean explicitPolicyReqd)
  {
    this.explicitPolicyReqd = explicitPolicyReqd;
  }
  
  public boolean isInhibitAnyPolicy()
  {
    return inhibitAnyPolicy;
  }
  
  private void setInhibitAnyPolicy(boolean inhibitAnyPolicy)
  {
    this.inhibitAnyPolicy = inhibitAnyPolicy;
  }
}
