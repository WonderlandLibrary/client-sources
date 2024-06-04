package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;











public class XMSSMTKeyParams
  extends ASN1Object
{
  private final ASN1Integer version;
  private final int height;
  private final int layers;
  private final AlgorithmIdentifier treeDigest;
  
  public XMSSMTKeyParams(int height, int layers, AlgorithmIdentifier treeDigest)
  {
    version = new ASN1Integer(0L);
    this.height = height;
    this.layers = layers;
    this.treeDigest = treeDigest;
  }
  
  private XMSSMTKeyParams(ASN1Sequence sequence)
  {
    version = ASN1Integer.getInstance(sequence.getObjectAt(0));
    height = ASN1Integer.getInstance(sequence.getObjectAt(1)).getValue().intValue();
    layers = ASN1Integer.getInstance(sequence.getObjectAt(2)).getValue().intValue();
    treeDigest = AlgorithmIdentifier.getInstance(sequence.getObjectAt(3));
  }
  
  public static XMSSMTKeyParams getInstance(Object o)
  {
    if ((o instanceof XMSSMTKeyParams))
    {
      return (XMSSMTKeyParams)o;
    }
    if (o != null)
    {
      return new XMSSMTKeyParams(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  public int getLayers()
  {
    return layers;
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
    v.add(new ASN1Integer(layers));
    v.add(treeDigest);
    
    return new DERSequence(v);
  }
}
