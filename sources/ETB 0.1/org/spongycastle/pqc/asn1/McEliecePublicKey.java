package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;


public class McEliecePublicKey
  extends ASN1Object
{
  private final int n;
  private final int t;
  private final GF2Matrix g;
  
  public McEliecePublicKey(int n, int t, GF2Matrix g)
  {
    this.n = n;
    this.t = t;
    this.g = new GF2Matrix(g);
  }
  
  private McEliecePublicKey(ASN1Sequence seq)
  {
    BigInteger bigN = ((ASN1Integer)seq.getObjectAt(0)).getValue();
    n = bigN.intValue();
    
    BigInteger bigT = ((ASN1Integer)seq.getObjectAt(1)).getValue();
    t = bigT.intValue();
    
    g = new GF2Matrix(((ASN1OctetString)seq.getObjectAt(2)).getOctets());
  }
  
  public int getN()
  {
    return n;
  }
  
  public int getT()
  {
    return t;
  }
  
  public GF2Matrix getG()
  {
    return new GF2Matrix(g);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    

    v.add(new ASN1Integer(n));
    

    v.add(new ASN1Integer(t));
    

    v.add(new DEROctetString(g.getEncoded()));
    
    return new DERSequence(v);
  }
  
  public static McEliecePublicKey getInstance(Object o)
  {
    if ((o instanceof McEliecePublicKey))
    {
      return (McEliecePublicKey)o;
    }
    if (o != null)
    {
      return new McEliecePublicKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
}
