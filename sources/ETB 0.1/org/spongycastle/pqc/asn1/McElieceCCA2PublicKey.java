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
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;


public class McElieceCCA2PublicKey
  extends ASN1Object
{
  private final int n;
  private final int t;
  private final GF2Matrix g;
  private final AlgorithmIdentifier digest;
  
  public McElieceCCA2PublicKey(int n, int t, GF2Matrix g, AlgorithmIdentifier digest)
  {
    this.n = n;
    this.t = t;
    this.g = new GF2Matrix(g.getEncoded());
    this.digest = digest;
  }
  
  private McElieceCCA2PublicKey(ASN1Sequence seq)
  {
    BigInteger bigN = ((ASN1Integer)seq.getObjectAt(0)).getValue();
    n = bigN.intValue();
    
    BigInteger bigT = ((ASN1Integer)seq.getObjectAt(1)).getValue();
    t = bigT.intValue();
    
    g = new GF2Matrix(((ASN1OctetString)seq.getObjectAt(2)).getOctets());
    
    digest = AlgorithmIdentifier.getInstance(seq.getObjectAt(3));
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
    return g;
  }
  
  public AlgorithmIdentifier getDigest()
  {
    return digest;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    

    v.add(new ASN1Integer(n));
    

    v.add(new ASN1Integer(t));
    

    v.add(new DEROctetString(g.getEncoded()));
    
    v.add(digest);
    
    return new DERSequence(v);
  }
  
  public static McElieceCCA2PublicKey getInstance(Object o)
  {
    if ((o instanceof McElieceCCA2PublicKey))
    {
      return (McElieceCCA2PublicKey)o;
    }
    if (o != null)
    {
      return new McElieceCCA2PublicKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
}
