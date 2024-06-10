package dev.jnic.eEZCNM;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public class JNICLoader extends InputStream {
  public JNICLoader(InputStream paramInputStream) {
    this(paramInputStream, 67108864, null);
  }
  
  public JNICLoader(InputStream paramInputStream, int paramInt, byte[] paramArrayOfbyte) {
    this(paramInputStream, paramInt, paramArrayOfbyte, (byte)0);
  }
  
  private JNICLoader(InputStream paramInputStream, int paramInt, byte[] paramArrayOfbyte, byte paramByte) {
    if (paramInputStream == null)
      throw new NullPointerException(); 
    this.b = new DataInputStream(paramInputStream);
    this.d = new B();
    int i;
    if ((i = paramInt) < 4096 || i > 2147483632)
      throw new IllegalArgumentException(); 
    this.c = new g(i + 15 & 0xFFFFFFF0, paramArrayOfbyte);
    if (paramArrayOfbyte != null && paramArrayOfbyte.length > 0)
      this.h = false; 
  }
  
  public int read() {
    return (read(this.l, 0, 1) == -1) ? -1 : (this.l[0] & 0xFF);
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfbyte.length)
      throw new IndexOutOfBoundsException(); 
    if (paramInt2 == 0)
      return 0; 
    if (this.b == null)
      throw new IOException(); 
    if (this.k != null)
      throw this.k; 
    if (this.j)
      return -1; 
    try {
      int i = 0;
      while (paramInt2 > 0) {
        if (this.f == 0) {
          JNICLoader jNICLoader;
          int i1;
          if ((i1 = (jNICLoader = this).b.readUnsignedByte()) == 0) {
            jNICLoader.j = true;
            jNICLoader.b();
          } else {
            DataInputStream dataInputStream;
            if (i1 >= 224 || i1 == 1) {
              jNICLoader.i = true;
              jNICLoader.h = false;
              g g2;
              (g2 = jNICLoader.c).o = 0;
              g2.p = 0;
              g2.q = 0;
              g2.r = 0;
              g2.m[g2.n - 1] = 0;
            } else if (jNICLoader.h) {
              throw new IOException();
            } 
            if (i1 >= 128) {
              jNICLoader.g = true;
              jNICLoader.f = (i1 & 0x1F) << 16;
              jNICLoader.f += jNICLoader.b.readUnsignedShort() + 1;
              int i2 = jNICLoader.b.readUnsignedShort() + 1;
              if (i1 >= 192) {
                jNICLoader.i = false;
                JNICLoader jNICLoader1;
                if ((i1 = (jNICLoader1 = jNICLoader).b.readUnsignedByte()) > 224)
                  throw new IOException(); 
                int i4 = i1 / 45;
                int i5 = (i1 -= i4 * 9 * 5) / 9;
                if ((i1 -= i5 * 9) + i5 > 4)
                  throw new IOException(); 
                jNICLoader1.e = new U(jNICLoader1.c, jNICLoader1.d, i1, i5, i4);
              } else {
                if (jNICLoader.i)
                  throw new IOException(); 
                if (i1 >= 160)
                  jNICLoader.e.c(); 
              } 
              int i3 = i2;
              dataInputStream = jNICLoader.b;
              B b1 = jNICLoader.d;
              if (i3 < 5)
                throw new IOException(); 
              if (dataInputStream.readUnsignedByte() != 0)
                throw new IOException(); 
              b1.X = dataInputStream.readInt();
              b1.W = -1;
              i3 -= 5;
              b1.p = b1.m.length - i3;
              dataInputStream.readFully(b1.m, b1.p, i3);
            } else {
              if (dataInputStream > 2)
                throw new IOException(); 
              jNICLoader.g = false;
              jNICLoader.f = jNICLoader.b.readUnsignedShort() + 1;
            } 
          } 
          if (this.j)
            return !i ? -1 : i; 
        } 
        int k = Math.min(this.f, paramInt2);
        if (!this.g) {
          int i1 = k;
          DataInputStream dataInputStream = this.b;
          int i2 = Math.min((g1 = this.c).n - g1.p, i1);
          dataInputStream.readFully(g1.m, g1.p, i2);
          g1.p += i2;
          if (g1.q < g1.p)
            g1.q = g1.p; 
        } else {
          g g2 = g1;
          if ((g1 = this.c).n - g1.p <= g2) {
            g1.r = g1.n;
          } else {
            g1.r = g1.p + g2;
          } 
          this.e.d();
        } 
        int m = paramInt1;
        byte[] arrayOfByte = paramArrayOfbyte;
        g g1;
        int n = (g1 = this.c).p - g1.o;
        if (g1.p == g1.n)
          g1.p = 0; 
        System.arraycopy(g1.m, g1.o, arrayOfByte, m, n);
        g1.o = g1.p;
        int j = n;
        paramInt1 += j;
        paramInt2 -= j;
        i += j;
        this.f -= j;
        B b;
        if (this.f == 0 && (!(((b = this.d).p == b.m.length && b.X == 0) ? 1 : 0) || ((this.c.s > 0))))
          throw new IOException(); 
      } 
      return i;
    } catch (IOException iOException) {
      this.k = iOException;
      throw iOException;
    } 
  }
  
  public int available() {
    if (this.b == null)
      throw new IOException("closed"); 
    if (this.k != null)
      throw this.k; 
    return this.g ? this.f : Math.min(this.f, this.b.available());
  }
  
  private void b() {
    if (this.c != null) {
      this.c = null;
      this.d = null;
    } 
  }
  
  public void close() {
    if (this.b != null) {
      b();
      try {
        this.b.close();
        return;
      } finally {
        this.b = null;
      } 
    } 
  }
  
  public static void init() {}
  
  static {
    String str1 = System.getProperty("os.name").toLowerCase();
    String str2 = System.getProperty("os.arch").toLowerCase();
    long l1 = 0L;
    long l2 = 0L;
    if (str1.contains("lin") && str2.equals("aarch64")) {
      l1 = 2195169L;
      l2 = 2601697L;
      z.putInt(-1739203567);
      z.putInt(-1221769068);
      z.putInt(-818223556);
      z.putInt(249356673);
      z.putInt(95460743);
      z.putInt(1369706730);
      z.putInt(-1711471416);
      z.putInt(1230940403);
    } 
    if (str1.contains("mac") && str2.equals("aarch64")) {
      l1 = 1303073L;
      l2 = 1768409L;
      z.putInt(-2053639116);
      z.putInt(-512384502);
      z.putInt(1402667649);
      z.putInt(1508135856);
      z.putInt(-373450738);
      z.putInt(-1112828427);
      z.putInt(1077661664);
      z.putInt(-173200546);
    } 
    if (str1.contains("win") && str2.equals("aarch64")) {
      l1 = 438784L;
      l2 = 850944L;
      z.putInt(-560048536);
      z.putInt(1908293667);
      z.putInt(1719162842);
      z.putInt(1559918570);
      z.putInt(-1526661453);
      z.putInt(850185991);
      z.putInt(120053386);
      z.putInt(-176349317);
    } 
    if (str1.contains("win") && (str2.equals("x86_64") || str2.equals("amd64"))) {
      l1 = 0L;
      l2 = 438784L;
      z.putInt(1283488225);
      z.putInt(1128080112);
      z.putInt(1247939317);
      z.putInt(-1687249498);
      z.putInt(-626907579);
      z.putInt(873771639);
      z.putInt(302318851);
      z.putInt(318926644);
    } 
    if (str1.contains("mac") && (str2.equals("x86_64") || str2.equals("amd64"))) {
      l1 = 850944L;
      l2 = 1303073L;
      z.putInt(-535478042);
      z.putInt(-468500535);
      z.putInt(593869182);
      z.putInt(222255571);
      z.putInt(-514281798);
      z.putInt(165722897);
      z.putInt(-876964852);
      z.putInt(-2069023748);
    } 
    if (str1.contains("lin") && (str2.equals("x86_64") || str2.equals("amd64"))) {
      l1 = 1768409L;
      l2 = 2195169L;
      z.putInt(-1309210080);
      z.putInt(-1452076313);
      z.putInt(985491821);
      z.putInt(-885552082);
      z.putInt(860907892);
      z.putInt(1106861755);
      z.putInt(-1240904588);
      z.putInt(-2106676411);
    } 
    if (l1 == l2)
      throw new UnsatisfiedLinkError("Platform not supported: " + str1 + "/" + str2); 
    try {
      file = File.createTempFile("lib", null);
      file.deleteOnExit();
      if (!file.exists())
        throw new IOException(); 
    } catch (IOException iOException) {
      throw new UnsatisfiedLinkError("Failed to create temp file");
    } 
    byte[] arrayOfByte = new byte[2048];
    try {
      JNICLoader jNICLoader = new JNICLoader(Objects.<InputStream>requireNonNull(JNICLoader.class.getResourceAsStream("/dev/jnic/lib/b379e298-b917-4576-8327-1bce8e9f5b87.dat")));
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
          long l;
          for (l = 0L; l < l1; l += l3) {
            long l3 = jNICLoader.skip(l1 - l);
            if (l3 <= 0L)
              throw new IOException("failed to skip: " + l); 
          } 
          while (l < l2) {
            int i = jNICLoader.read(arrayOfByte, 0, (int)Math.min(arrayOfByte.length, l2 - l));
            fileOutputStream.write(arrayOfByte, 0, i);
            l += i;
          } 
          fileOutputStream.close();
        } catch (Throwable throwable) {
          try {
            fileOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
        jNICLoader.close();
      } catch (Throwable throwable) {
        try {
          jNICLoader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      throw new UnsatisfiedLinkError("Failed to extract file: " + iOException.getMessage());
    } 
    z.putInt(-1077541384);
    z.putInt(931359123);
    z.putInt(333495865);
    z.putInt(1864836165);
    z.putInt(25092250);
    z.putInt(921097196);
    System.load(file.getAbsolutePath());
  }
  
  static {
    File file;
  }
  
  public static ByteBuffer z = ByteBuffer.allocateDirect(19097).order(ByteOrder.LITTLE_ENDIAN);
  
  private String V = "3.6.0";
  
  private DataInputStream b;
  
  private g c;
  
  private B d;
  
  private U e;
  
  private int f = 0;
  
  private boolean g = false;
  
  private boolean h = true;
  
  private boolean i = true;
  
  private boolean j = false;
  
  private IOException k = null;
  
  private final byte[] l = new byte[1];
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\JNICLoader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */