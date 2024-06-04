package org.spongycastle.math.ec.endo;

import org.spongycastle.math.ec.ECPointMap;

public abstract interface ECEndomorphism
{
  public abstract ECPointMap getPointMap();
  
  public abstract boolean hasEfficientPointMap();
}
