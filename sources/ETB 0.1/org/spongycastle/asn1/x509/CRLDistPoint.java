package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Strings;

public class CRLDistPoint
  extends ASN1Object
{
  ASN1Sequence seq = null;
  


  public static CRLDistPoint getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static CRLDistPoint getInstance(Object obj)
  {
    if ((obj instanceof CRLDistPoint))
    {
      return (CRLDistPoint)obj;
    }
    if (obj != null)
    {
      return new CRLDistPoint(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private CRLDistPoint(ASN1Sequence seq)
  {
    this.seq = seq;
  }
  

  public CRLDistPoint(DistributionPoint[] points)
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    for (int i = 0; i != points.length; i++)
    {
      v.add(points[i]);
    }
    
    seq = new DERSequence(v);
  }
  





  public DistributionPoint[] getDistributionPoints()
  {
    DistributionPoint[] dp = new DistributionPoint[seq.size()];
    
    for (int i = 0; i != seq.size(); i++)
    {
      dp[i] = DistributionPoint.getInstance(seq.getObjectAt(i));
    }
    
    return dp;
  }
  






  public ASN1Primitive toASN1Primitive()
  {
    return seq;
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String sep = Strings.lineSeparator();
    
    buf.append("CRLDistPoint:");
    buf.append(sep);
    DistributionPoint[] dp = getDistributionPoints();
    for (int i = 0; i != dp.length; i++)
    {
      buf.append("    ");
      buf.append(dp[i]);
      buf.append(sep);
    }
    return buf.toString();
  }
}
