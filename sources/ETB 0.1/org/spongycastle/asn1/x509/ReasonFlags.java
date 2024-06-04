package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.DERBitString;






















public class ReasonFlags
  extends DERBitString
{
  /**
   * @deprecated
   */
  public static final int UNUSED = 128;
  /**
   * @deprecated
   */
  public static final int KEY_COMPROMISE = 64;
  /**
   * @deprecated
   */
  public static final int CA_COMPROMISE = 32;
  /**
   * @deprecated
   */
  public static final int AFFILIATION_CHANGED = 16;
  /**
   * @deprecated
   */
  public static final int SUPERSEDED = 8;
  /**
   * @deprecated
   */
  public static final int CESSATION_OF_OPERATION = 4;
  /**
   * @deprecated
   */
  public static final int CERTIFICATE_HOLD = 2;
  /**
   * @deprecated
   */
  public static final int PRIVILEGE_WITHDRAWN = 1;
  /**
   * @deprecated
   */
  public static final int AA_COMPROMISE = 32768;
  public static final int unused = 128;
  public static final int keyCompromise = 64;
  public static final int cACompromise = 32;
  public static final int affiliationChanged = 16;
  public static final int superseded = 8;
  public static final int cessationOfOperation = 4;
  public static final int certificateHold = 2;
  public static final int privilegeWithdrawn = 1;
  public static final int aACompromise = 32768;
  
  public ReasonFlags(int reasons)
  {
    super(getBytes(reasons), getPadBits(reasons));
  }
  

  public ReasonFlags(DERBitString reasons)
  {
    super(reasons.getBytes(), reasons.getPadBits());
  }
}
