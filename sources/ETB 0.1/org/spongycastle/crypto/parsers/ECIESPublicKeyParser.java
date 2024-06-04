package org.spongycastle.crypto.parsers;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.KeyParser;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.io.Streams;

public class ECIESPublicKeyParser
  implements KeyParser
{
  private ECDomainParameters ecParams;
  
  public ECIESPublicKeyParser(ECDomainParameters ecParams)
  {
    this.ecParams = ecParams;
  }
  

  public AsymmetricKeyParameter readKey(InputStream stream)
    throws IOException
  {
    int first = stream.read();
    byte[] V;
    byte[] V;
    switch (first)
    {
    case 0: 
      throw new IOException("Sender's public key invalid.");
    
    case 2: 
    case 3: 
      V = new byte[1 + (ecParams.getCurve().getFieldSize() + 7) / 8];
      break;
    
    case 4: 
    case 6: 
    case 7: 
      V = new byte[1 + 2 * ((ecParams.getCurve().getFieldSize() + 7) / 8)];
      break;
    case 1: case 5: 
    default: 
      throw new IOException("Sender's public key has invalid point encoding 0x" + Integer.toString(first, 16));
    }
    byte[] V;
    V[0] = ((byte)first);
    Streams.readFully(stream, V, 1, V.length - 1);
    
    return new ECPublicKeyParameters(ecParams.getCurve().decodePoint(V), ecParams);
  }
}
