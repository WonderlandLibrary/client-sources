package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;


public class ECNamedDomainParameters
  extends ECDomainParameters
{
  private ASN1ObjectIdentifier name;
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier name, ECCurve curve, ECPoint G, BigInteger n)
  {
    this(name, curve, G, n, null, null);
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier name, ECCurve curve, ECPoint G, BigInteger n, BigInteger h)
  {
    this(name, curve, G, n, h, null);
  }
  
  public ECNamedDomainParameters(ASN1ObjectIdentifier name, ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed)
  {
    super(curve, G, n, h, seed);
    
    this.name = name;
  }
  
  public ASN1ObjectIdentifier getName()
  {
    return name;
  }
}
