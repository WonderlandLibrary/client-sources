package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

public class ServerSRPParams
{
  protected BigInteger N;
  protected BigInteger g;
  protected BigInteger B;
  protected byte[] s;
  
  public ServerSRPParams(BigInteger N, BigInteger g, byte[] s, BigInteger B)
  {
    this.N = N;
    this.g = g;
    this.s = org.spongycastle.util.Arrays.clone(s);
    this.B = B;
  }
  
  public BigInteger getB()
  {
    return B;
  }
  
  public BigInteger getG()
  {
    return g;
  }
  
  public BigInteger getN()
  {
    return N;
  }
  
  public byte[] getS()
  {
    return s;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    TlsSRPUtils.writeSRPParameter(N, output);
    TlsSRPUtils.writeSRPParameter(g, output);
    TlsUtils.writeOpaque8(s, output);
    TlsSRPUtils.writeSRPParameter(B, output);
  }
  







  public static ServerSRPParams parse(InputStream input)
    throws IOException
  {
    BigInteger N = TlsSRPUtils.readSRPParameter(input);
    BigInteger g = TlsSRPUtils.readSRPParameter(input);
    byte[] s = TlsUtils.readOpaque8(input);
    BigInteger B = TlsSRPUtils.readSRPParameter(input);
    
    return new ServerSRPParams(N, g, s, B);
  }
}
