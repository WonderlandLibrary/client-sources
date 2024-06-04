package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;










public class XMSSKeyParams
  extends ASN1Object
{
  private final ASN1Integer version;
  private final int height;
  private final AlgorithmIdentifier treeDigest;
  
  public XMSSKeyParams(int height, AlgorithmIdentifier treeDigest)
  {
    version = new ASN1Integer(0L);
    this.height = height;
    this.treeDigest = treeDigest;
  }
  
  private XMSSKeyParams(ASN1Sequence sequence)
  {
    version = ASN1Integer.getInstance(sequence.getObjectAt(0));
    height = ASN1Integer.getInstance(sequence.getObjectAt(1)).getValue().intValue();
    treeDigest = AlgorithmIdentifier.getInstance(sequence.getObjectAt(2));
  }
  
  public static XMSSKeyParams getInstance(Object o)
  {
    if ((o instanceof XMSSKeyParams))
    {
      return (XMSSKeyParams)o;
    }
    if (o != null)
    {
      return new XMSSKeyParams(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public AlgorithmIdentifier getTreeDigest()
  {
    return treeDigest;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(new ASN1Integer(height));
    v.add(treeDigest);
    
    return new DERSequence(v);
  }
}
