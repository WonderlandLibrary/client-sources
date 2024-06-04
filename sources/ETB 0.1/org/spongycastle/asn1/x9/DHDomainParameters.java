package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

/**
 * @deprecated
 */
public class DHDomainParameters extends ASN1Object
{
  private ASN1Integer p;
  private ASN1Integer g;
  private ASN1Integer q;
  private ASN1Integer j;
  private DHValidationParms validationParms;
  
  public static DHDomainParameters getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  
  public static DHDomainParameters getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof DHDomainParameters)))
    {
      return (DHDomainParameters)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new DHDomainParameters((ASN1Sequence)obj);
    }
    

    throw new IllegalArgumentException("Invalid DHDomainParameters: " + obj.getClass().getName());
  }
  

  public DHDomainParameters(BigInteger p, BigInteger g, BigInteger q, BigInteger j, DHValidationParms validationParms)
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
    this.j = new ASN1Integer(j);
    this.validationParms = validationParms;
  }
  

  public DHDomainParameters(ASN1Integer p, ASN1Integer g, ASN1Integer q, ASN1Integer j, DHValidationParms validationParms)
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
    
    this.p = p;
    this.g = g;
    this.q = q;
    this.j = j;
    this.validationParms = validationParms;
  }
  
  private DHDomainParameters(ASN1Sequence seq)
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
    
    if (next != null)
    {
      validationParms = DHValidationParms.getInstance(next.toASN1Primitive());
    }
  }
  
  private static ASN1Encodable getNext(Enumeration e)
  {
    return e.hasMoreElements() ? (ASN1Encodable)e.nextElement() : null;
  }
  
  public ASN1Integer getP()
  {
    return p;
  }
  
  public ASN1Integer getG()
  {
    return g;
  }
  
  public ASN1Integer getQ()
  {
    return q;
  }
  
  public ASN1Integer getJ()
  {
    return j;
  }
  
  public DHValidationParms getValidationParms()
  {
    return validationParms;
  }
  
  public org.spongycastle.asn1.ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(p);
    v.add(g);
    v.add(q);
    
    if (j != null)
    {
      v.add(j);
    }
    
    if (validationParms != null)
    {
      v.add(validationParms);
    }
    
    return new DERSequence(v);
  }
}
