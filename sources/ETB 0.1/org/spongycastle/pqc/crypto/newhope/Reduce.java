package org.spongycastle.pqc.crypto.newhope;

class Reduce {
  static final int QInv = 12287;
  static final int RLog = 18;
  static final int RMask = 262143;
  
  Reduce() {}
  
  static short montgomery(int a) {
    int u = a * 12287;
    u &= 0x3FFFF;
    u *= 12289;
    u += a;
    return (short)(u >>> 18);
  }
  
  static short barrett(short a)
  {
    int t = a & 0xFFFF;
    int u = t * 5 >>> 16;
    u *= 12289;
    return (short)(t - u);
  }
}
