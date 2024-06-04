package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;


















public class DomainParameters
  extends ASN1Object
{
  private final ASN1Integer p;
  private final ASN1Integer g;
  private final ASN1Integer q;
  private final ASN1Integer j;
  private final ValidationParams validationParams;
  
  public static DomainParameters getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  






  public static DomainParameters getInstance(Object obj)
  {
    if ((obj instanceof DomainParameters))
    {
      return (DomainParameters)obj;
    }
    if (obj != null)
    {
      return new DomainParameters(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  










  public DomainParameters(BigInteger p, BigInteger g, BigInteger q, BigInteger j, ValidationParams validationParams)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("'p' cannot be null");
    }
    if (g == null)
    {
      throw new IllegalArgumentException("'g' cannot be null");
    }
    if (q == null)
    {
      throw new IllegalArgumentException("'q' cannot be null");
    }
    
    this.p = new ASN1Integer(p);
    this.g = new ASN1Integer(g);
    this.q = new ASN1Integer(q);
    
    if (j != null)
    {
      this.j = new ASN1Integer(j);
    }
    else
    {
      this.j = null;
    }
    this.validationParams = validationParams;
  }
  
  private DomainParameters(ASN1Sequence seq)
  {
    if ((seq.size() < 3) || (seq.size() > 5))
    {
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    Enumeration e = seq.getObjects();
    p = ASN1Integer.getInstance(e.nextElement());
    g = ASN1Integer.getInstance(e.nextElement());
    q = ASN1Integer.getInstance(e.nextElement());
    
    ASN1Encodable next = getNext(e);
    
    if ((next != null) && ((next instanceof ASN1Integer)))
    {
      j = ASN1Integer.getInstance(next);
      next = getNext(e);
    }
    else
    {
      j = null;
    }
    
    if (next != null)
    {
      validationParams = ValidationParams.getInstance(next.toASN1Primitive());
    }
    else
    {
      validationParams = null;
    }
  }
  
  private static ASN1Encodable getNext(Enumeration e)
  {
    return e.hasMoreElements() ? (ASN1Encodable)e.nextElement() : null;
  }
  





  public BigInteger getP()
  {
    return p.getPositiveValue();
  }
  





  public BigInteger getG()
  {
    return g.getPositiveValue();
  }
  





  public BigInteger getQ()
  {
    return q.getPositiveValue();
  }
  





  public BigInteger getJ()
  {
    if (j == null)
    {
      return null;
    }
    
    return j.getPositiveValue();
  }
  





  public ValidationParams getValidationParams()
  {
    return validationParams;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(p);
    v.add(g);
    v.add(q);
    
    if (j != null)
    {
      v.add(j);
    }
    
    if (validationParams != null)
    {
      v.add(validationParams);
    }
    
    return new DERSequence(v);
  }
}
