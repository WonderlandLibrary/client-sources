package org.spongycastle.asn1;

import java.math.BigInteger;


/**
 * @deprecated
 */
public class DEREnumerated
  extends ASN1Enumerated
{
  /**
   * @deprecated
   */
  DEREnumerated(byte[] bytes)
  {
    super(bytes);
  }
  

  /**
   * @deprecated
   */
  public DEREnumerated(BigInteger value)
  {
    super(value);
  }
  

  /**
   * @deprecated
   */
  public DEREnumerated(int value)
  {
    super(value);
  }
}
