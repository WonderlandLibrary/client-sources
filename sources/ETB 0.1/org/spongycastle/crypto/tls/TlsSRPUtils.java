package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Hashtable;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Integers;


public class TlsSRPUtils
{
  public static final Integer EXT_SRP = Integers.valueOf(12);
  
  public TlsSRPUtils() {}
  
  public static void addSRPExtension(Hashtable extensions, byte[] identity) throws IOException { extensions.put(EXT_SRP, createSRPExtension(identity)); }
  
  public static byte[] getSRPExtension(Hashtable extensions)
    throws IOException
  {
    byte[] extensionData = TlsUtils.getExtensionData(extensions, EXT_SRP);
    return extensionData == null ? null : readSRPExtension(extensionData);
  }
  
  public static byte[] createSRPExtension(byte[] identity) throws IOException
  {
    if (identity == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    return TlsUtils.encodeOpaque8(identity);
  }
  
  public static byte[] readSRPExtension(byte[] extensionData) throws IOException
  {
    if (extensionData == null)
    {
      throw new IllegalArgumentException("'extensionData' cannot be null");
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
    byte[] identity = TlsUtils.readOpaque8(buf);
    
    TlsProtocol.assertEmpty(buf);
    
    return identity;
  }
  
  public static BigInteger readSRPParameter(InputStream input) throws IOException
  {
    return new BigInteger(1, TlsUtils.readOpaque16(input));
  }
  
  public static void writeSRPParameter(BigInteger x, OutputStream output) throws IOException
  {
    TlsUtils.writeOpaque16(BigIntegers.asUnsignedByteArray(x), output);
  }
  
  public static boolean isSRPCipherSuite(int cipherSuite)
  {
    switch (cipherSuite)
    {
    case 49178: 
    case 49179: 
    case 49180: 
    case 49181: 
    case 49182: 
    case 49183: 
    case 49184: 
    case 49185: 
    case 49186: 
      return true;
    }
    
    return false;
  }
}
