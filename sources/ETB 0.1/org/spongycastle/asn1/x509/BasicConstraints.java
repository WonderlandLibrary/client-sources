package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;


public class BasicConstraints
  extends ASN1Object
{
  ASN1Boolean cA = ASN1Boolean.getInstance(false);
  ASN1Integer pathLenConstraint = null;
  


  public static BasicConstraints getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static BasicConstraints getInstance(Object obj)
  {
    if ((obj instanceof BasicConstraints))
    {
      return (BasicConstraints)obj;
    }
    if ((obj instanceof X509Extension))
    {
      return getInstance(X509Extension.convertValueToObject((X509Extension)obj));
    }
    if (obj != null)
    {
      return new BasicConstraints(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public static BasicConstraints fromExtensions(Extensions extensions)
  {
    return getInstance(extensions.getExtensionParsedValue(Extension.basicConstraints));
  }
  

  private BasicConstraints(ASN1Sequence seq)
  {
    if (seq.size() == 0)
    {
      cA = null;
      pathLenConstraint = null;
    }
    else
    {
      if ((seq.getObjectAt(0) instanceof ASN1Boolean))
      {
        cA = ASN1Boolean.getInstance(seq.getObjectAt(0));
      }
      else
      {
        cA = null;
        pathLenConstraint = ASN1Integer.getInstance(seq.getObjectAt(0));
      }
      if (seq.size() > 1)
      {
        if (cA != null)
        {
          pathLenConstraint = ASN1Integer.getInstance(seq.getObjectAt(1));
        }
        else
        {
          throw new IllegalArgumentException("wrong sequence in constructor");
        }
      }
    }
  }
  

  public BasicConstraints(boolean cA)
  {
    if (cA)
    {
      this.cA = ASN1Boolean.getInstance(true);
    }
    else
    {
      this.cA = null;
    }
    pathLenConstraint = null;
  }
  






  public BasicConstraints(int pathLenConstraint)
  {
    cA = ASN1Boolean.getInstance(true);
    this.pathLenConstraint = new ASN1Integer(pathLenConstraint);
  }
  
  public boolean isCA()
  {
    return (cA != null) && (cA.isTrue());
  }
  
  public BigInteger getPathLenConstraint()
  {
    if (pathLenConstraint != null)
    {
      return pathLenConstraint.getValue();
    }
    
    return null;
  }
  









  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (cA != null)
    {
      v.add(cA);
    }
    
    if (pathLenConstraint != null)
    {
      v.add(pathLenConstraint);
    }
    
    return new DERSequence(v);
  }
  
  public String toString()
  {
    if (pathLenConstraint == null)
    {
      if (cA == null)
      {
        return "BasicConstraints: isCa(false)";
      }
      return "BasicConstraints: isCa(" + isCA() + ")";
    }
    return "BasicConstraints: isCa(" + isCA() + "), pathLenConstraint = " + pathLenConstraint.getValue();
  }
}
