package org.spongycastle.crypto;

public enum PasswordConverter
  implements CharToByteConverter
{
  ASCII,  UTF8,  PKCS12;
  
  private PasswordConverter() {}
}
