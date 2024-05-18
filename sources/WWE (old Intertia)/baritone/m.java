package baritone;

import baritone.api.cache.*;
import java.nio.file.attribute.*;
import java.nio.file.*;
import java.util.zip.*;
import java.util.*;
import java.io.*;

public final class m implements ICachedRegion
{
    private final l[][] a;
    private final int a;
    private final int b;
    private final int c;
    private boolean a;
    
    m(final int a, final int b, final int c) {
        super();
        this.a = new l[32][32];
        this.a = a;
        this.b = b;
        this.a = false;
        this.c = c;
    }
    
    @Override
    public final awt getBlock(final int n, int n2, int n3) {
        final l l;
        if ((l = this.a[n >> 4][n3 >> 4]) == null) {
            return null;
        }
        final l i = l;
        final int n4 = n & 0xF;
        final int n5 = n2;
        final int n6 = n3 & 0xF;
        final int c = this.c;
        final int n7 = n6;
        n3 = n5;
        n2 = n4;
        final l j = i;
        final int a = baritone.l.a(n2, n3, n7);
        final l k = j;
        final int n8 = a;
        final dj a2 = dj.a(k.a.get(n8), k.a.get(n8 + 1));
        n2 |= n7 << 4;
        if (j.a[n2] == n3 && a2 != dj.c) {
            return j.a[n2];
        }
        final String s;
        if (j.a != null && (s = (String)j.a.get(a)) != null) {
            return p.a(s).t();
        }
        if (a2 == dj.d && n3 == 127 && c == -1) {
            return aox.h.t();
        }
        return p.a(a2, c);
    }
    
    @Override
    public final boolean isCached(final int n, final int n2) {
        return this.a[n >> 4][n2 >> 4] != null;
    }
    
    public final ArrayList<et> a(final String s) {
        final ArrayList<et> list = new ArrayList<et>();
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                final ArrayList<et> a;
                if (this.a[i][j] != null && (a = this.a[i][j].a(s)) != null) {
                    list.addAll(a);
                }
            }
        }
        return list;
    }
    
    public final synchronized void a(final int n, final int n2, final l l) {
        this.a[n][n2] = l;
        this.a = true;
    }
    
    public final synchronized void a(String s) {
        if (!this.a) {
            return;
        }
        this.a();
        try {
            final Path value;
            if (!Files.exists(value = Paths.get(s, new String[0]), new LinkOption[0])) {
                Files.createDirectories(value, (FileAttribute<?>[])new FileAttribute[0]);
            }
            System.out.println("Saving region " + this.a + "," + this.b + " to disk " + value);
            final Path a;
            if (!Files.exists(a = a(value, this.a, this.b), new LinkOption[0])) {
                Files.createFile(a, (FileAttribute<?>[])new FileAttribute[0]);
            }
            s = (String)new FileOutputStream(a.toFile());
            Throwable t = null;
            try {
                final GZIPOutputStream gzipOutputStream = new GZIPOutputStream((OutputStream)s, 16384);
                Throwable t2 = null;
                try {
                    final DataOutputStream dataOutputStream = new DataOutputStream(gzipOutputStream);
                    Throwable t3 = null;
                    try {
                        dataOutputStream.writeInt(456022910);
                        for (int i = 0; i < 32; ++i) {
                            for (int j = 0; j < 32; ++j) {
                                final l l;
                                if ((l = this.a[i][j]) == null) {
                                    dataOutputStream.write(0);
                                }
                                else {
                                    dataOutputStream.write(1);
                                    final byte[] byteArray = l.a.toByteArray();
                                    dataOutputStream.write(byteArray);
                                    dataOutputStream.write(new byte[16384 - byteArray.length]);
                                }
                            }
                        }
                        for (int k = 0; k < 32; ++k) {
                            for (int n = 0; n < 32; ++n) {
                                if (this.a[k][n] != null) {
                                    for (int n2 = 0; n2 < 256; ++n2) {
                                        dataOutputStream.writeUTF(p.a(this.a[k][n].a[n2].u()));
                                    }
                                }
                            }
                        }
                        for (int n3 = 0; n3 < 32; ++n3) {
                            for (int n4 = 0; n4 < 32; ++n4) {
                                if (this.a[n3][n4] != null) {
                                    final Map<String, List<et>> a2 = this.a[n3][n4].a;
                                    dataOutputStream.writeShort(a2.entrySet().size());
                                    for (final Map.Entry<String, List<et>> entry : a2.entrySet()) {
                                        dataOutputStream.writeUTF(entry.getKey());
                                        dataOutputStream.writeShort(entry.getValue().size());
                                        for (final et et : entry.getValue()) {
                                            dataOutputStream.writeByte((byte)(et.r() << 4 | et.p()));
                                            dataOutputStream.writeByte((byte)et.q());
                                        }
                                    }
                                }
                            }
                        }
                        for (int n5 = 0; n5 < 32; ++n5) {
                            for (int n6 = 0; n6 < 32; ++n6) {
                                if (this.a[n5][n6] != null) {
                                    dataOutputStream.writeLong(this.a[n5][n6].a);
                                }
                            }
                        }
                        dataOutputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataOutputStream.close();
                            }
                            catch (Throwable t6) {
                                t3.addSuppressed(t6);
                            }
                        }
                        else {
                            dataOutputStream.close();
                        }
                    }
                    gzipOutputStream.close();
                }
                catch (Throwable t8) {
                    final Throwable t7 = t8;
                    t2 = t8;
                    throw t7;
                }
                finally {
                    if (t2 != null) {
                        try {
                            gzipOutputStream.close();
                        }
                        catch (Throwable t9) {
                            t2.addSuppressed(t9);
                        }
                    }
                    else {
                        gzipOutputStream.close();
                    }
                }
                ((FileOutputStream)s).close();
            }
            catch (Throwable t11) {
                final Throwable t10 = t11;
                t = t11;
                throw t10;
            }
            finally {
                if (t != null) {
                    try {
                        ((FileOutputStream)s).close();
                    }
                    catch (Throwable t12) {
                        t.addSuppressed(t12);
                    }
                }
                else {
                    ((FileOutputStream)s).close();
                }
            }
            this.a = false;
            System.out.println("Saved region successfully");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public final synchronized void b(String s) {
        try {
            final Path value;
            if (!Files.exists(value = Paths.get(s, new String[0]), new LinkOption[0])) {
                Files.createDirectories(value, (FileAttribute<?>[])new FileAttribute[0]);
            }
            final Path a;
            if (!Files.exists(a = a(value, this.a, this.b), new LinkOption[0])) {
                return;
            }
            System.out.println("Loading region " + this.a + "," + this.b + " from disk " + value);
            final long n = System.nanoTime() / 1000000L;
            final FileInputStream fileInputStream = new FileInputStream(a.toFile());
            Throwable t = null;
            try {
                s = (String)new GZIPInputStream(fileInputStream, 32768);
                Throwable t2 = null;
                try {
                    final DataInputStream dataInputStream = new DataInputStream((InputStream)s);
                    Throwable t3 = null;
                    try {
                        final int int1;
                        if ((int1 = dataInputStream.readInt()) != 456022910) {
                            throw new IOException("Bad magic value ".concat(String.valueOf(int1)));
                        }
                        final boolean[][] array = new boolean[32][32];
                        final BitSet[][] array2 = new BitSet[32][32];
                        final Map[][] array3 = new Map[32][32];
                        final awt[][][] array4 = new awt[32][32][];
                        final long[][] array5 = new long[32][32];
                        for (int i = 0; i < 32; ++i) {
                            for (int j = 0; j < 32; ++j) {
                                switch (dataInputStream.read()) {
                                    case 1: {
                                        final byte[] array6 = new byte[16384];
                                        dataInputStream.readFully(array6);
                                        array2[i][j] = BitSet.valueOf(array6);
                                        array3[i][j] = new HashMap();
                                        array4[i][j] = new awt[256];
                                        array[i][j] = true;
                                        break;
                                    }
                                    case 0: {
                                        break;
                                    }
                                    default: {
                                        throw new IOException("Malformed stream");
                                    }
                                }
                            }
                        }
                        for (int k = 0; k < 32; ++k) {
                            for (int l = 0; l < 32; ++l) {
                                if (array[k][l]) {
                                    for (int n2 = 0; n2 < 256; ++n2) {
                                        array4[k][l][n2] = p.a(dataInputStream.readUTF()).t();
                                    }
                                }
                            }
                        }
                        for (int n3 = 0; n3 < 32; ++n3) {
                            for (int n4 = 0; n4 < 32; ++n4) {
                                if (array[n3][n4]) {
                                    for (int n5 = dataInputStream.readShort() & 0xFFFF, n6 = 0; n6 < n5; ++n6) {
                                        final String utf;
                                        p.a(utf = dataInputStream.readUTF());
                                        final ArrayList<et> list = new ArrayList<et>();
                                        array3[n3][n4].put(utf, list);
                                        int n7;
                                        if ((n7 = (dataInputStream.readShort() & 0xFFFF)) == 0) {
                                            n7 = 65536;
                                        }
                                        for (int n8 = 0; n8 < n7; ++n8) {
                                            final byte byte1 = dataInputStream.readByte();
                                            list.add(new et(byte1 & 0xF, dataInputStream.readByte() & 0xFF, byte1 >>> 4 & 0xF));
                                        }
                                    }
                                }
                            }
                        }
                        for (int n9 = 0; n9 < 32; ++n9) {
                            for (int n10 = 0; n10 < 32; ++n10) {
                                if (array[n9][n10]) {
                                    array5[n9][n10] = dataInputStream.readLong();
                                }
                            }
                        }
                        for (int n11 = 0; n11 < 32; ++n11) {
                            for (int n12 = 0; n12 < 32; ++n12) {
                                if (array[n11][n12]) {
                                    this.a[n11][n12] = new l(n11 + (this.a << 5), n12 + (this.b << 5), array2[n11][n12], array4[n11][n12], array3[n11][n12], array5[n11][n12]);
                                }
                            }
                        }
                        dataInputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (Throwable t6) {
                                t3.addSuppressed(t6);
                            }
                        }
                        else {
                            dataInputStream.close();
                        }
                    }
                    ((GZIPInputStream)s).close();
                }
                catch (Throwable t8) {
                    final Throwable t7 = t8;
                    t2 = t8;
                    throw t7;
                }
                finally {
                    if (t2 != null) {
                        try {
                            ((GZIPInputStream)s).close();
                        }
                        catch (Throwable t9) {
                            t2.addSuppressed(t9);
                        }
                    }
                    else {
                        ((GZIPInputStream)s).close();
                    }
                }
                fileInputStream.close();
            }
            catch (Throwable t11) {
                final Throwable t10 = t11;
                t = t11;
                throw t10;
            }
            finally {
                if (t != null) {
                    try {
                        fileInputStream.close();
                    }
                    catch (Throwable t12) {
                        t.addSuppressed(t12);
                    }
                }
                else {
                    fileInputStream.close();
                }
            }
            this.a();
            this.a = false;
            final long n13 = System.nanoTime() / 1000000L;
            final PrintStream out = System.out;
            final StringBuilder sb = new StringBuilder("Loaded region successfully in ");
            final long n14 = n13;
            final long n15 = n;
            out.println(sb.append(n14 - n15).append("ms").toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public final synchronized void a() {
        final long longValue;
        if ((longValue = baritone.a.a().cachedChunksExpirySeconds.value) < 0L) {
            return;
        }
        final long currentTimeMillis;
        final long n = (currentTimeMillis = System.currentTimeMillis()) - longValue * 1000L;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                if (this.a[i][j] != null && this.a[i][j].a < n) {
                    System.out.println("Removing chunk " + (i + 32 * this.a) + "," + (j + 32 * this.b) + " because it was cached " + (currentTimeMillis - this.a[i][j].a) / 1000L + " seconds ago, and max age is " + longValue);
                    this.a[i][j] = null;
                }
            }
        }
    }
    
    public final synchronized l a() {
        l l = null;
        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                if (this.a[i][j] != null && (l == null || this.a[i][j].a > l.a)) {
                    l = this.a[i][j];
                }
            }
        }
        return l;
    }
    
    @Override
    public final int getX() {
        return this.a;
    }
    
    @Override
    public final int getZ() {
        return this.b;
    }
    
    private static Path a(final Path path, final int n, final int n2) {
        return Paths.get(path.toString(), "r." + n + "." + n2 + ".bcr");
    }
}
