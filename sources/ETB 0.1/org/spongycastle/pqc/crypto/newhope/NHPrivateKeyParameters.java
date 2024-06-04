package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class NHPrivateKeyParameters
  extends AsymmetricKeyParameter
{
  final short[] secData;
  
  public NHPrivateKeyParameters(short[] secData)
  {
    super(true);
    
    this.secData = Arrays.clone(secData);
  }
  
  public short[] getSecData()
  {
    return Arrays.clone(secData);
  }
}
