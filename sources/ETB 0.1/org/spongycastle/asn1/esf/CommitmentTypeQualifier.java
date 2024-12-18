package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
















public class CommitmentTypeQualifier
  extends ASN1Object
{
  private ASN1ObjectIdentifier commitmentTypeIdentifier;
  private ASN1Encodable qualifier;
  
  public CommitmentTypeQualifier(ASN1ObjectIdentifier commitmentTypeIdentifier)
  {
    this(commitmentTypeIdentifier, null);
  }
  








  public CommitmentTypeQualifier(ASN1ObjectIdentifier commitmentTypeIdentifier, ASN1Encodable qualifier)
  {
    this.commitmentTypeIdentifier = commitmentTypeIdentifier;
    this.qualifier = qualifier;
  }
  







  private CommitmentTypeQualifier(ASN1Sequence as)
  {
    commitmentTypeIdentifier = ((ASN1ObjectIdentifier)as.getObjectAt(0));
    
    if (as.size() > 1)
    {
      qualifier = as.getObjectAt(1);
    }
  }
  
  public static CommitmentTypeQualifier getInstance(Object as)
  {
    if ((as instanceof CommitmentTypeQualifier))
    {
      return (CommitmentTypeQualifier)as;
    }
    if (as != null)
    {
      return new CommitmentTypeQualifier(ASN1Sequence.getInstance(as));
    }
    
    return null;
  }
  
  public ASN1ObjectIdentifier getCommitmentTypeIdentifier()
  {
    return commitmentTypeIdentifier;
  }
  
  public ASN1Encodable getQualifier()
  {
    return qualifier;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector dev = new ASN1EncodableVector();
    dev.add(commitmentTypeIdentifier);
    if (qualifier != null)
    {
      dev.add(qualifier);
    }
    
    return new DERSequence(dev);
  }
}
