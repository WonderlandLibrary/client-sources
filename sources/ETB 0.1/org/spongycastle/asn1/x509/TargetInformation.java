package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;





















public class TargetInformation
  extends ASN1Object
{
  private ASN1Sequence targets;
  
  public static TargetInformation getInstance(Object obj)
  {
    if ((obj instanceof TargetInformation))
    {
      return (TargetInformation)obj;
    }
    if (obj != null)
    {
      return new TargetInformation(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  







  private TargetInformation(ASN1Sequence seq)
  {
    targets = seq;
  }
  





  public Targets[] getTargetsObjects()
  {
    Targets[] copy = new Targets[targets.size()];
    int count = 0;
    for (Enumeration e = targets.getObjects(); e.hasMoreElements();)
    {
      copy[(count++)] = Targets.getInstance(e.nextElement());
    }
    return copy;
  }
  






  public TargetInformation(Targets targets)
  {
    this.targets = new DERSequence(targets);
  }
  







  public TargetInformation(Target[] targets)
  {
    this(new Targets(targets));
  }
  

















  public ASN1Primitive toASN1Primitive()
  {
    return targets;
  }
}
