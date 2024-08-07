package org.spongycastle.jcajce.provider.asymmetric.util;

import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.util.Strings;

public class DESUtil
{
  private static final Set<String> des = new HashSet();
  
  static
  {
    des.add("DES");
    des.add("DESEDE");
    des.add(OIWObjectIdentifiers.desCBC.getId());
    des.add(PKCSObjectIdentifiers.des_EDE3_CBC.getId());
    des.add(PKCSObjectIdentifiers.des_EDE3_CBC.getId());
    des.add(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId());
  }
  
  public static boolean isDES(String algorithmID)
  {
    String name = Strings.toUpperCase(algorithmID);
    
    return des.contains(name);
  }
  







  public static void setOddParity(byte[] bytes)
  {
    for (int i = 0; i < bytes.length; i++)
    {
      int b = bytes[i];
      bytes[i] = ((byte)(b & 0xFE | (b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0x1) & 0x1));
    }
  }
  
  public DESUtil() {}
}
