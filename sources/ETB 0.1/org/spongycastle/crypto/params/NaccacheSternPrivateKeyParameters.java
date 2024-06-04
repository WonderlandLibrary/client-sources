package org.spongycastle.crypto.params;

import java.math.BigInteger;
import java.util.Vector;























public class NaccacheSternPrivateKeyParameters
  extends NaccacheSternKeyParameters
{
  private BigInteger phi_n;
  private Vector smallPrimes;
  
  public NaccacheSternPrivateKeyParameters(BigInteger g, BigInteger n, int lowerSigmaBound, Vector smallPrimes, BigInteger phi_n)
  {
    super(true, g, n, lowerSigmaBound);
    this.smallPrimes = smallPrimes;
    this.phi_n = phi_n;
  }
  
  public BigInteger getPhi_n()
  {
    return phi_n;
  }
  
  public Vector getSmallPrimes()
  {
    return smallPrimes;
  }
}
