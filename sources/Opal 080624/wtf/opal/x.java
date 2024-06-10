package wtf.opal;

import java.io.IOException;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

class x extends Writer {
  private final Writer r;
  
  private final char[] X;
  
  private int w = 0;
  
  private static final long a = on.a(1407119008437218099L, 8311137957828538934L, MethodHandles.lookup().lookupClass()).a(232351067305185L);
  
  private static final long b;
  
  x(long paramLong, Writer paramWriter) {
    this(paramWriter, (int)b);
  }
  
  x(Writer paramWriter, int paramInt) {
    this.r = paramWriter;
    this.X = new char[paramInt];
  }
  
  public void write(int paramInt) throws IOException {
    long l = a ^ 0x60AD6A967D96L;
    int i = lq.O();
    try {
      if (i != 0)
        try {
          if (this.w > this.X.length - 1)
            flush(); 
        } catch (IOException iOException) {
          throw a(null);
        }  
    } catch (IOException iOException) {
      throw a(null);
    } 
    this.X[this.w++] = (char)paramInt;
  }
  
  public void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
    long l = a ^ 0x7E01E9AECED5L;
    int i = lq.T();
    try {
      if (i == 0) {
        try {
          if (this.w > this.X.length - paramInt2)
            try {
              flush();
              if (i == 0) {
                try {
                  if (paramInt2 > this.X.length) {
                    this.r.write(paramArrayOfchar, paramInt1, paramInt2);
                    return;
                  } 
                } catch (IOException iOException) {
                  throw a(null);
                } 
              } else {
                return;
              } 
            } catch (IOException iOException) {
              throw a(null);
            }  
        } catch (IOException iOException) {
          throw a(null);
        } 
        System.arraycopy(paramArrayOfchar, paramInt1, this.X, this.w, paramInt2);
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
    this.w += paramInt2;
  }
  
  public void write(String paramString, int paramInt1, int paramInt2) throws IOException {
    long l = a ^ 0x1FD3DDE01A94L;
    int i = lq.T();
    try {
      if (i == 0) {
        try {
          if (this.w > this.X.length - paramInt2)
            try {
              flush();
              if (i == 0) {
                try {
                  if (paramInt2 > this.X.length) {
                    this.r.write(paramString, paramInt1, paramInt2);
                    return;
                  } 
                } catch (IOException iOException) {
                  throw a(null);
                } 
              } else {
                return;
              } 
            } catch (IOException iOException) {
              throw a(null);
            }  
        } catch (IOException iOException) {
          throw a(null);
        } 
        paramString.getChars(paramInt1, paramInt1 + paramInt2, this.X, this.w);
      } 
    } catch (IOException iOException) {
      throw a(null);
    } 
    this.w += paramInt2;
  }
  
  public void flush() throws IOException {
    this.r.write(this.X, 0, this.w);
    this.w = 0;
  }
  
  public void close() throws IOException {}
  
  static {
    long l = a ^ 0x5989E366E8F3L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static IOException a(IOException paramIOException) {
    return paramIOException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\x.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */