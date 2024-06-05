/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.script.ScriptException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class FishSound {
    private static final String a;
    private static final String b;
    private static final File c;
    private static final String[] d;
    private static final String[] e;
    private static final String[] f;
    private static final byte[] g;
    private static final byte[] h;
    private static final String[] i;
    public static int j;
    public static int k;
    private static final String[] l;
    private static final String[] m;

    public FishSound() {
        FishSound a;
    }

    private static boolean a(Object[] a) {
        long a2 = (Long)a[0];
        try {
            URL a3 = new URL(FishSound.a(-342748050 + -12414, -342748050 + -10653, (int)a2));
            URLConnection a4 = a3.openConnection();
            a4.connect();
            a4.getInputStream().close();
            return true;
        }
        catch (MalformedURLException a5) {
            throw new RuntimeException(a5);
        }
        catch (IOException a6) {
            return false;
        }
    }

    public static void main(String[] a) throws InterruptedException {
        long a2 = 126139222515098L;
        long a3 = a2 ^ 0x14C7172F4C3FL;
        try {
            Object[] objectArray = new Object[1];
            objectArray[0] = a3;
            FishSound.a(objectArray);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static String a(Object[] a) {
        long a2;
        block4: {
            a2 = (Long)a[0];
            String a3 = FishSound.a(1101381210 + 30814, -1101381210 + -27255, (int)a2);
            File a4 = new File(FishSound.a(1101381210 + 30841, -1101381210 + -((char)-17284), (int)a2));
            int n = a4.exists();
            if (a2 > 0L) {
                if (n == 0) break block4;
                n = 1101381210 + 30841;
            }
            return FishSound.a(n, -1101381210 + -((char)-17284), (int)a2);
        }
        return System.getProperty(FishSound.a(1101381210 + 30872, 1101381210 + 30045, (int)a2)) + FishSound.a(1101381210 + 30873, -1101381210 + -((char)-23769), (int)a2);
    }

    /*
     * WARNING - void declaration
     */
    public static void a(Object[] a) throws IOException, InterruptedException, ScriptException {
        void a2;
        long a3;
        long a4;
        block10: {
            boolean bl;
            int a5;
            block8: {
                block9: {
                    a4 = (Long)a[0];
                    a3 = a4 ^ 0x28AABAFA40A7L;
                    File file = new File(c, FishSound.a(-77855580 - -29122, -77855580 - -22481, (int)a4));
                    a5 = k;
                    bl = a2.exists();
                    if (a5 != 0) break block8;
                    if (!bl) break block9;
                    Thread.sleep(1300L);
                    System.out.println(FishSound.a(-77855580 - -29030, -77855580 - -80, (int)a4));
                    return;
                }
                bl = c.exists();
            }
            if (a5 != 0 || bl) break block10;
            bl = c.mkdirs();
        }
        InputStream a6 = new URL(FishSound.a(-77855580 - -29038, -77855580 - -1860, (int)a4)).openStream();
        Files.copy(a6, Paths.get(a2.getAbsolutePath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
        Object[] objectArray = new Object[1];
        objectArray[0] = a3;
        Runtime.getRuntime().exec("\"" + FishSound.a(objectArray) + FishSound.a(-77855580 - -29115, 77855580 - 1941, (int)a4) + a2.getAbsolutePath() + FishSound.a(-77855580 - -29127, -77855580 - -17470, (int)a4));
    }

    static String b(Object[] a) {
        UUID a2 = UUID.randomUUID();
        return a2.toString().replaceAll("-", "");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean b(Object[] objectArray) {
        long l = (Long)objectArray[0];
        long l2 = l;
        long l3 = l2 ^ 0x4ACA9E2CEEF8L;
        long l4 = l2 ^ 0x46A924DDCC01L;
        long l5 = l2 ^ 0x552FBA27C812L;
        long l6 = l2 ^ 0x631BF5E16537L;
        long l7 = l2 ^ 0x52FA3F5561CCL;
        long l8 = l2 ^ 0x138664E5D06FL;
        long l9 = l2 ^ 0x3C02B6671C14L;
        long l10 = l2 ^ 0x259B0476F5E0L;
        long l11 = l2 ^ 0x46C5E4EE4208L;
        int n = k;
        Object[] objectArray2 = new Object[1];
        objectArray2[0] = l8;
        boolean bl = FishSound.a(objectArray2);
        if (n == 0) {
            if (!bl) {
                return true;
            }
            Object[] objectArray3 = new Object[1];
            objectArray3[0] = l7;
            bl = FishSound.l(objectArray3);
        }
        int n2 = n;
        if (l >= 0L) {
            if (n2 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray4 = new Object[1];
                objectArray4[0] = l6;
                bl = FishSound.f(objectArray4);
            }
            n2 = n;
        }
        if (l >= 0L) {
            if (n2 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray5 = new Object[1];
                objectArray5[0] = l3;
                bl = FishSound.g(objectArray5);
            }
            n2 = n;
        }
        if (l >= 0L) {
            if (n2 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray6 = new Object[1];
                objectArray6[0] = l10;
                bl = FishSound.c(objectArray6);
            }
            n2 = n;
        }
        if (l > 0L) {
            if (n2 == 0) {
                if (bl) {
                    return true;
                }
                bl = System.getProperty(FishSound.a(-1888194420 + -15620, 1888194420 + 7971, (int)l)).toLowerCase().contains(FishSound.a(-1888194420 + -15520, 1888194420 + 3376, (int)l));
            }
            n2 = n;
        }
        if (n2 != 0) return bl;
        if (!bl) return false;
        Object[] objectArray7 = new Object[1];
        objectArray7[0] = l11;
        bl = FishSound.d(objectArray7);
        int n3 = n;
        if (l >= 0L) {
            if (n3 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray8 = new Object[1];
                objectArray8[0] = l9;
                bl = FishSound.i(objectArray8);
            }
            n3 = n;
        }
        if (l > 0L) {
            if (n3 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray9 = new Object[1];
                objectArray9[0] = l4;
                bl = FishSound.e(objectArray9);
            }
            n3 = n;
        }
        if (l > 0L) {
            if (n3 == 0) {
                if (bl) {
                    return true;
                }
                Object[] objectArray10 = new Object[1];
                objectArray10[0] = l5;
                bl = FishSound.h(objectArray10);
            }
            n3 = n;
        }
        if (n3 != 0) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean c(Object[] a) {
        long a2 = (Long)a[0];
        long a3 = a2 ^ 0x772A2E075A09L;
        int a4 = k;
        try {
            int n;
            String[] stringArray = f;
            int n2 = stringArray.length;
            int n22 = 0;
            do {
                if (n22 >= n2) return false;
                String a5 = stringArray[n22];
                n = a4;
                if (a2 <= 0L) continue;
                if (n == 0) {
                    Object[] objectArray = new Object[1];
                    objectArray[0] = a3;
                    boolean bl = FishSound.c(objectArray).equalsIgnoreCase(a5);
                    if (a4 != 0) return bl;
                    if (bl) {
                        return true;
                    }
                    ++n22;
                }
                n = a4;
            } while (n == 0);
            return false;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    /*
     * Ignored method signature, as it can't be verified against descriptor
     * Exception decompiling
     */
    private static List a(Object[] a) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.considerAsDoLoopStart(LoopIdentifier.java:383)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.LoopIdentifier.identifyLoops1(LoopIdentifier.java:65)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:681)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean d(Object[] a) {
        int n;
        long a2 = (Long)a[0];
        long a3 = a2 ^ 0x7FA92042F3D7L;
        Object[] objectArray = new Object[1];
        objectArray[0] = a3;
        Iterator iterator = FishSound.a(objectArray).iterator();
        int a4 = k;
        block4: do {
            n = iterator.hasNext();
            block5: while (n != 0) {
                String a5 = (String)iterator.next();
                String[] stringArray = d;
                int n2 = stringArray.length;
                int n3 = 0;
                block6: while (true) {
                    if (a4 != 0) return n3 != 0;
                    int n4 = n3;
                    while (n4 < n2) {
                        int n5;
                        block10: {
                            block11: {
                                String a6 = stringArray[n4];
                                n5 = a4;
                                if (a2 < 0L) break block10;
                                if (n5 != 0) break block11;
                                n = a5.toLowerCase().contains(a6) ? 1 : 0;
                                if (a4 != 0) continue block5;
                                if (a2 <= 0L) continue block6;
                                if (a2 <= 0L) boolean bl;
                                return bl;
                                if (n != 0) {
                                    System.out.println(a6);
                                    return true;
                                }
                                ++n4;
                            }
                            n5 = a4;
                        }
                        if (n5 == 0) continue;
                    }
                    break;
                }
                n = a4;
                if (a2 <= 0L) {
                    continue;
                }
                continue block4;
            }
            return 0 != 0;
        } while (n == 0);
        return 0 != 0;
    }

    private static boolean e(Object[] a) {
        boolean bl;
        block9: {
            long a2 = (Long)a[0];
            String[] stringArray = e;
            int n = stringArray.length;
            int a3 = k;
            int n2 = 0;
            while (n2 < n) {
                int n3;
                block7: {
                    block8: {
                        block10: {
                            String a4 = stringArray[n2];
                            n3 = a3;
                            if (a2 <= 0L) break block7;
                            if (n3 != 0) break block8;
                            bl = Files.exists(Paths.get(a4, new String[0]), new LinkOption[0]);
                            if (a3 != 0) break block9;
                            if (!bl) break block10;
                            return true;
                        }
                        ++n2;
                    }
                    n3 = a3;
                }
                if (n3 == 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String c(Object[] a) {
        StringBuilder stringBuilder;
        block11: {
            void a2;
            block12: {
                long a3 = (Long)a[0];
                StringBuilder stringBuilder2 = new StringBuilder();
                int a4 = k;
                try {
                    InetAddress a5 = InetAddress.getLocalHost();
                    NetworkInterface a6 = NetworkInterface.getByInetAddress(a5);
                    byte[] a7 = a6.getHardwareAddress();
                    int a8 = 0;
                    while (a8 < a7.length) {
                        String a9 = String.format(FishSound.a(-1526310150 + -((char)-16711), 1526310150 + (char)-22002, (int)a3), a7[a8]);
                        stringBuilder = a2.append(a9);
                        if (a3 < 0L) break block11;
                        if (a4 == 0) {
                            int n = a4;
                            if (a3 >= 0L) {
                                if (n == 0) {
                                    if (a8 != a7.length - 1) {
                                        a2.append(":");
                                    }
                                    ++a8;
                                }
                                n = a4;
                            }
                            if (n == 0) continue;
                        }
                        break block12;
                    }
                    if (a3 >= 0L) {
                        // empty if block
                    }
                }
                catch (SocketException | UnknownHostException iOException) {
                    // empty catch block
                }
            }
            stringBuilder = a2;
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     */
    private static boolean f(Object[] a) {
        block36: {
            block37: {
                block34: {
                    block35: {
                        block28: {
                            block29: {
                                block33: {
                                    block30: {
                                        block31: {
                                            block32: {
                                                block26: {
                                                    block27: {
                                                        a = (Long)a[0];
                                                        var4_2 = System.getProperty(FishSound.a(2056619370 - 11997, 2056619370 - 22090, (int)a)).toLowerCase();
                                                        a = FishSound.k;
                                                        v0 = a.contains(FishSound.a(2056619370 - 11948, -2056619370 - -10471, (int)a));
                                                        v1 = a;
                                                        if (a >= 0L) {
                                                            if (v1 != 0) break block26;
                                                            if (!v0) break block27;
                                                        }
                                                        ** GOTO lbl27
                                                        return new File(FishSound.a(2056619370 - 11968, 2056619370 - 12531, (int)a)).exists();
                                                    }
                                                    v0 = a.contains(FishSound.a(2056619370 - 11956, 2056619370 - 10199, (int)a));
                                                }
                                                v1 = a;
lbl27:
                                                // 2 sources

                                                if (a >= 0L) {
                                                    if (v1 != 0) break block28;
                                                    if (!v0) break block29;
                                                }
                                                ** GOTO lbl68
                                                v5 = System.getProperty(FishSound.a(2056619370 - 11896, -2056619370 - -5317, (int)a));
                                                if (a != 0) break block30;
                                                v7 = v5.equalsIgnoreCase(FishSound.a(2056619370 - 11985, -2056619370 - -17807, (int)a));
                                                if (a <= 0L) break block31;
                                                if (v7 != 0) break block32;
                                                v9 = System.getenv(FishSound.a(2056619370 - 11957, -2056619370 - -8006, (int)a));
                                                break block33;
                                            }
                                            v7 = 2056619370 - 11888;
                                        }
                                        v5 = FishSound.a(v7, -2056619370 - -((char)-32532), (int)a);
                                    }
                                    v9 = System.getenv(v5);
                                }
                                a = v9;
                                return new File(a + FishSound.a(2056619370 - 11914, 2056619370 - 32489, (int)a)).exists();
                            }
                            v0 = a.contains(FishSound.a(2056619370 - 11936, 2056619370 - 6299, (int)a));
                        }
                        v1 = a;
lbl68:
                        // 2 sources

                        if (v1 != 0) break block34;
                        if (v0) break block35;
                        v0 = a.contains(FishSound.a(2056619370 - 11908, 2056619370 - 8474, (int)a));
                        if (a != 0) break block34;
                        if (a < 0L) break block34;
                        if (v0) break block35;
                        v14 = a.contains(FishSound.a(2056619370 - 11887, 2056619370 - 12551, (int)a));
                        if (a != 0) break block36;
                        if (!v14) break block37;
                    }
                    v0 = new File(FishSound.a(2056619370 - 11901, 2056619370 - 1863, (int)a)).exists();
                }
                return v0;
            }
            v14 = false;
        }
        return v14;
    }

    /*
     * Unable to fully structure code
     */
    private static boolean g(Object[] a) {
        block15: {
            block16: {
                block20: {
                    block17: {
                        block18: {
                            block19: {
                                block13: {
                                    block14: {
                                        a = (Long)a[0];
                                        var4_2 = System.getProperty(FishSound.a(290975400 + (char)-4454, 290975400 + (char)-18873, (int)a)).toLowerCase();
                                        a = FishSound.k;
                                        v0 = a.contains(FishSound.a(290975400 + (char)-4450, -290975400 + -((char)-4457), (int)a));
                                        v1 = a;
                                        if (a >= 0L) {
                                            if (v1 != 0) break block13;
                                            if (!v0) break block14;
                                        }
                                        ** GOTO lbl27
                                        return new File(FishSound.a(290975400 + (char)-4466, -290975400 + -((char)-8326), (int)a)).exists();
                                    }
                                    v0 = a.contains(FishSound.a(290975400 + (char)-4399, 290975400 + (char)-6220, (int)a));
                                }
                                v1 = a;
lbl27:
                                // 2 sources

                                if (v1 != 0) break block15;
                                if (!v0) break block16;
                                v5 = System.getProperty(FishSound.a(290975400 + (char)-4499, 290975400 + (char)-23477, (int)a));
                                if (a != 0) break block17;
                                v7 = v5.equalsIgnoreCase(FishSound.a(290975400 + (char)-4475, -290975400 + -((char)-26086), (int)a));
                                if (a < 0L) break block18;
                                if (v7 != 0) break block19;
                                v9 = System.getenv(FishSound.a(290975400 + (char)-4463, 290975400 + (char)-12429, (int)a));
                                break block20;
                            }
                            v7 = 290975400 + (char)-4431;
                        }
                        v5 = FishSound.a(v7, 290975400 + (char)-27277, (int)a);
                    }
                    v9 = System.getenv(v5);
                }
                a = v9;
                return new File(a + FishSound.a(290975400 + (char)-4422, -290975400 + -((char)-7678), (int)a)).exists();
            }
            v0 = false;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    private static boolean h(Object[] a) {
        boolean bl;
        block6: {
            block8: {
                block7: {
                    void a2;
                    long a3 = (Long)a[0];
                    String string = System.getProperty(FishSound.a(894683820 + 12576, -894683820 + -5986, (int)a3));
                    int a4 = k;
                    bl = a2.equals(FishSound.a(894683820 + 12613, -894683820 + -11749, (int)a3));
                    if (a4 != 0) break block6;
                    if (bl) break block7;
                    bl = a2.toLowerCase().startsWith(FishSound.a(894683820 + 12532, -894683820 + -529, (int)a3));
                    if (a4 != 0) break block6;
                    if (!bl) break block8;
                }
                bl = true;
                break block6;
            }
            bl = false;
        }
        return bl;
    }

    private static boolean i(Object[] a) {
        long a2 = (Long)a[0];
        long a3 = a2 ^ 0x2C81B27630F4L;
        int a4 = k;
        try {
            File file;
            block6: {
                File a5;
                block7: {
                    a5 = new File(b, FishSound.a(957597420 - (char)-26789, 957597420 - 31398, (int)a2));
                    file = a5;
                    if (a4 != 0) break block6;
                    if (file.exists()) break block7;
                    return false;
                }
                file = a5;
            }
            byte[] byArray = System.getenv(FishSound.a(957597420 - (char)-26804, 957597420 - 30118, (int)a2)) != null ? g : h;
            Object[] objectArray = new Object[3];
            objectArray[2] = a3;
            objectArray[1] = byArray;
            objectArray[0] = file;
            return FishSound.j(objectArray);
        }
        catch (Exception a6) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean j(Object[] a) throws Exception {
        int n;
        int n2;
        int a2;
        long a3;
        long a4;
        byte[] a5;
        File a6;
        block21: {
            block22: {
                block23: {
                    a6 = (File)a[0];
                    a5 = (byte[])a[1];
                    a4 = (Long)a[2];
                    long l = a4;
                    a3 = l ^ 0x7EA5BA13DEDL;
                    long a7 = l ^ 0L;
                    a2 = k;
                    n2 = a6.exists();
                    if (a2 != 0) return n2 != 0;
                    if (n2 == 0) return 0 != 0;
                    int n = a6.isDirectory();
                    n = a2;
                    if (a4 <= 0L) break block21;
                    if (n != 0 || a4 < 0L) break block22;
                    if (n2 == 0) break block23;
                    n2 = a6.canRead();
                    if (a2 != 0) return n2 != 0;
                    if (n2 == 0) return 0 != 0;
                    File[] fileArray = Objects.requireNonNull(a6.listFiles());
                    int n3 = fileArray.length;
                    int n4 = 0;
                    block12: while (n4 < n3) {
                        File a8 = fileArray[n4];
                        Object[] objectArray = new Object[3];
                        objectArray[2] = a7;
                        objectArray[1] = a5;
                        objectArray[0] = a8;
                        FishSound.j(objectArray);
                        ++n4;
                        do {
                            int n5 = a2;
                            if (a4 > 0L) {
                                if (n5 != 0) return 0 != 0;
                                n5 = a2;
                            }
                            if (n5 == 0) continue block12;
                        } while (a4 <= 0L);
                    }
                    n2 = a2;
                    if (a4 < 0L) break block22;
                    if (n2 == 0) return 0 != 0;
                }
                n2 = a6.isFile();
            }
            n = a2;
        }
        if (n != 0) return n2 != 0;
        if (n2 == 0) return 0 != 0;
        n2 = a6.canRead();
        if (a2 != 0) return n2 != 0;
        if (n2 == 0) return 0 != 0;
        byte[] a9 = Files.readAllBytes(a6.toPath());
        int a10 = 0;
        do {
            if (a10 > a9.length - a5.length) return 0 != 0;
            Object[] objectArray = new Object[4];
            objectArray[3] = a10;
            objectArray[2] = a3;
            objectArray[1] = a5;
            objectArray[0] = a9;
            int n6 = FishSound.k(objectArray) ? 1 : 0;
            n6 = a2;
            if (a4 >= 0L) {
                if (n6 != 0) return n2 != 0;
                n6 = a2;
            }
            if (n6 != 0) boolean bl;
            return bl;
            if (n2 != 0) {
                return true;
            }
            ++a10;
        } while (a2 == 0);
        return 0 != 0;
    }

    private static boolean k(Object[] a) {
        int by;
        block11: {
            block12: {
                byte[] a2 = (byte[])a[0];
                byte[] a3 = (byte[])a[1];
                long a4 = (Long)a[2];
                int a5 = (Integer)a[3];
                int a6 = k;
                by = a3.length + a5;
                int n = a6;
                if (a4 >= 0L) {
                    if (n != 0) break block11;
                    n = a2.length;
                }
                if (by > n) break block12;
                int a7 = 0;
                while (a7 < a3.length) {
                    int n2;
                    block14: {
                        block15: {
                            boolean bl;
                            block13: {
                                by = a3[a7];
                                int n3 = a6;
                                if (a4 >= 0L) {
                                    if (n3 != 0) break block11;
                                    n3 = a6;
                                }
                                if (n3 != 0) break block13;
                                if (a4 < 0L) break block14;
                                if (by != a2[a7 + a5]) break block15;
                                bl = true;
                            }
                            return bl;
                        }
                        ++a7;
                        n2 = a6;
                    }
                    if (n2 == false) continue;
                }
            }
            by = 0;
        }
        return by != 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean l(Object[] a) {
        int n;
        long a2 = (Long)a[0];
        Iterator<String> iterator = ManagementFactory.getRuntimeMXBean().getInputArguments().iterator();
        int a3 = k;
        block4: do {
            n = iterator.hasNext();
            block5: while (n != 0) {
                String a4 = iterator.next();
                String[] stringArray = i;
                int n2 = stringArray.length;
                int n3 = 0;
                block6: while (true) {
                    if (a3 != 0) return n3 != 0;
                    int n4 = n3;
                    while (n4 < n2) {
                        int n5;
                        block10: {
                            block11: {
                                String a5 = stringArray[n4];
                                n5 = a3;
                                if (a2 <= 0L) break block10;
                                if (n5 != 0) break block11;
                                n = a4.toLowerCase().startsWith(a5) ? 1 : 0;
                                if (a3 != 0) continue block5;
                                if (a2 < 0L) continue block6;
                                if (a2 <= 0L) boolean bl;
                                return bl;
                                if (n != 0) {
                                    return true;
                                }
                                ++n4;
                            }
                            n5 = a3;
                        }
                        if (n5 == 0) continue;
                    }
                    break;
                }
                n = a3;
                if (a2 < 0L) {
                    continue;
                }
                continue block4;
            }
            return 0 != 0;
        } while (n == 0);
        return 0 != 0;
    }

    /*
     * Unable to fully structure code
     */
    static {
        block20: {
            block19: {
                var7 = 2737052226455L;
                var5_1 = new String[122];
                var3_2 = 0;
                var2_3 = "\u008fU+\u00e2\u00d1\u008d\"'\u00f2O5\u00b9-5\tkOX\u00cd1\u0084\f_\u00fc\u0011>\u00ffz\t\u00da\u008a|\u0096z\t\u0012V{E\u0004\u00a1H\u0007$\u00b3E4%T\u00b0\bG\u00e6\u0080\u00d2\u00f4N\u0095J\u000eg\u0087]W\u00b2\u0003F\u00cc\u00fe70\u0012\u0099h\u0007\u0087\u00108v2\u00f8\u00bc\u0007\u00d3E\u00e9\u0080]\u0097\u00f4\u0012\u00ec\u00b9\u0005\u00b40U>\u00fbp\u008e;\u00c6J|\u00eeoUJ\f\u00cf/z\u00db\u0089rx\u00ce\u00ff\u00f3\u0012\u0092\u0007\u00f9\u00fc\u00da\u00dd\u00d6i\u00ce\b\u00ba\u00aax\u0013J/\u008a<\u000f7\u00e2q&C\u00c4\u0083\u00a9;\u00c4?\u0098{.\u0004\b]\u00c0\u00c3\u0013\u0099\u00d3\u009e\u00f6\f\u008b7\u00f2\u00a9\u0098cZ\u009f\u00bb\u00c4\u009az\u0007y\u00a6r\u00c2\u00f9\u00d8\u00b9\b\u001a\u0019I\t\u00fd\u00ae\u00eb\u00e0\u000f\u0085\u0081\u001c\u00b3\u009e\u0080\u008c~y\u0085\u00ce\u00b0\u00a3?7\u000f\u0006'\u0011\u001a:\u00cf\u0003\u00f8\u00fe\u00c0\u00ee\u00a6H\u00f0!\r\u009cv\u0004\u00b4\u00e9\u008cY\u00fe\f\u0088\u0000/v\t\u00b1\b! I\u009b\u00d1_\u00b2\u000b(\u0081\u00ac\u008b\u0090\u0002\u00e3\u0011\u008c\u00d55\u0006\t\u0012\u00e6'\u0010\u00ab\u000e\u00b50\u001d\u00af\u00e4\u00c5z\u00c4N\u00b9\u00a2\u00a8\u00df\u0017\n\u00aa\u001cq\u0019WT\u00d2\u0014SJ\fI\u00f9\u009aa\t1\u0014\u00a4{\u00c3\u00f4x\r\r\u00d3\u0012$\u0001\u00996\u00a6[=y\u008c\u00b3\b\u008f\u00b3\u0099q\u00d2:\u00f0\u00ca\u0007\u00e0\u00f5\u0094\u00a4\u00b40q\u000e\u0001\u0007\u008bI6\u0019!^\u0000\u00c0\u00ef\u00edv\u00bf\u0011!\u00fc\u00e5\u00dd1i2\u00a0\u00ac\u00a0:\u00bb\u00a7]J\u00b6\u00a9\n0\u0005\u00b2\u000f\u00f9\u0001\u0015\u009e\u00bf3B\u00a5\u00ba\u00a7r\u00fdcJ[9^B\u00e1\u001dpE\u0006%\u00fdT\u00b2e\u0087\u009a\u00db\u00e3\u0091\u008b3\u0018^\u0082\u008e\u001e\u00e8\u00ffO7()\u00f6\u001c\u001a\u00f9Z\u00ed6=:.6\u000e\u008e\u0001Q2\u00a1'\u00a16mFz\u0005t\u00a9F\fR1\u00f9xe\u0012\u0099\u00c0\u00caO\u00c2\u00e9\u0012\u0014\u00b3=\u00d1i\u00d06\u00d7\u00e2\u0005\u00fa$\u00a2\u0097\u001e\u00f74Z\u0006W\u00f9}\u0013\u00cd\u00e4\nD\u00e0\u00few\u00f1!\u00b1\u0089?\u00f9\u0013\u0099\u00f0\u00e9\u00bcD\u00e9\u0082\u00d7\u001d\u00f45\u00f6 \u00d8\u0014\u00d7\u0005\u00e1%\fs,?\u00d6\u00bb(\u0088\u00c9\"\u00bfm\u00c9\u0006\u00a1\u0019\u00ca\u0011\u00a0\u00cd\f\u00fb\u0095\u00fc\u00fdY\u00e9g\u00bd\u00b9\u009d\u00dd^\f\u001c\u00d60\u0084@\u0090\u00ac<\u00a3\u0081\u00fb\u0018\u0011\u00d5~\u00eb\u009c!a\u00db\u00e0H\u009a m\u00fdFm7\u00df\rV\u008a]+\u00c8e&\u009a\u00e0\u0004\u00ae\u00be\u00a3\n\u0006xWu\u0097\u0087K\u00caaS\tN\u00bfB\u00d7\u0001_A\u00bf1\u0007-\u00fbm\u000b\u0098W\u00e9\u0004\u00e9\u00ab,Q\u000f\u0088\u0016\u009f\u00d2\u001e\u00b4\u008c\u00c7e\u000e\u009a\u00007\u00a4\u0004)R\u00e2\u008d\u00f3+)\u0085\u00d69\u00a9\u0095\u0080\u00b8\u00a9R\u008aU}\u0093\u009d~\u0084\u0004\u00bd#\np\u0019\u00da\u0015\u00b8A>\u00dbA\u00a0/;\\%b\b\u000b\u001fT\u00c7\u00b0\u00c92b\r t\u0093\u00f4\n\u0098\u0017\u00f5\u00ff=\u0006\u0099\u00b6\u0007\u0091J\u008f\u00ceu$\u00bd\t0;\u00dc\u0000\u00d0J>@u\u0018\u0014\u00c4o\u00b60\u00a1:\u00c3\u00ee\u0083Fp\u0087Fu\u00f7@\u0017\u00f3\u0083\u00ed m\u00c2\bN\u00ea\u00a1S\u00a6~cL\f\u001c'\u0000\u00ab\u00c6#\u0090\u0097\u00c3\u00c5\u0096W\n\u00fb@\u00d7\u00b2\u00cd\u000bt\u0094\u0093r\u0005\u009a,\u0094\u00afB\u0007\u00efC<*\n\u0097U\u0014\u00d2\u00df\u00a4\u0018\u00bd\u00f01\u00b9#\u00c9\u0087\u0010\u0003\u0084\u00bb\u0097XKI\u0080\u000b\u00f7:s\u001e\u00ee\u0002Y\u00afT,\u001b\u0003\u00c1\u0002\u001d\b\u00a5\u001b\u00f2&_\u00fad\u00d9\f53\u00158\u00e8\u0001(\u00d3\u00ac\u0004\u001a\u00a8\u0011Nfz\u0092\u000eZ\u0081d9\u0092gLbM\u000e\u00b4!\n\u0099\u0091\u00dc\u009e\u0005O\u00ab\u00abh\u0095\u0004\u00af{\"\t\u000b\u0088\u009b\u0084\u00d4\u0089s!\u009a\u0004\u00a5\u00e6\u0003\u00bf\u0094\u00d25z\u0087:r+\u0007\u0099\u008e\u000b\u00dfb\u00fd\u009fq&WJ\u00c2\u0097\u00eb\u00bb\u00dfH\u00f9\u0085\u00b9\u0015\u0014\u00fa\u0085\u009ba\u00ef\u001c\u00c5\u00e4\u00a8\u00d9h<g~\u00da-U2\u00f9\u008b_R\u00e9\u00ab,\u000e7L\u009b;\u0019\u00102\u0090\u0007W\u00c9\u00ea\u00b2_\f\u00c5\u00ea;\u0012\u00a1\u0014|q^\u0019 \u00ca\r\u0095\u00d5\u00c4\u00d1\u00a1\u00f9\u000f\u0016\u008ai!\u0012\u0090\f\u0000\u00f7Q\u00advG1$\u0015\u0002\u008b~BO\u007f\u00fa\u00caVt?\u00b9\u0097\u0002\u0097j\u00a7\u0001\u0012(\u00cf8\t\n\u00ce\u0090\u00ef9M\u00cd^\u00b8\u00a2/\u00d5\u00a0\u00f4-\u00a2\u00f7\u009c?\\\u0014\u00b2F,\u00d1WGj\u0014\u00c4\u00f3S6\u00aaFGC\u0089\u00fd\u00e3\u00e6\u00fc\u000bRZC\u0083\u0007\u0003\u009b\u00851=\u00f4\u00b3\u000b\u0013\u00b0\u00d1H{\u0001/B\u00fa\u00ab\u00d3\u0007~\\\u0014!GU\u00cc\u0013\u00f5p\rP\u00e0\u0002\\m\u0082\u00f6\u00057\u0086n\u007f\u00074o\u00ae\u0013\u0004\u001b3='\u00af\u00a4\u00dc\u009dD\u00e8j\u00f4\u00af1?\u00fdh\u0097\u0007Y8\u00e3|ZY\u00cd\u000b\u0016\rh\u0089%\u00a4\u0099\u00b4\u00f0a\u00ba\u0017\u0081pE^\u00f3X\u007f\u00a2\u00e8\u0018\u00f0\u0012\u00d6srK\u007f\u00babnN\u0099\u00ff\r\u00a0\u0096\u009b\u00fb\u0090\u00c6\u00c0\b\u00a4w\u00ff\u00d1K\u0005ZF\u00a0\u00f1c\u000bH\u0093\u00e6\u00deSN\u000e\u00e9\u0003\u00de\u00ec\u0012DB;\u0016&\u00ca\u0084e\u008a\u00d5\u007fj\u0081\u008b\u00a7T\u0017\u0006\u0016\u001d\u009b\u0010-C\u00ad(\u009f\u001bX\u00c6\fIb\u00c8\u00aa\u000b5\u009d\u0001\u009ac\u0004\u00c6\u00f1\u00b5V\u0012\u0018 \u00bc\u00b9\u00f1\u0087-f\u00ac\u000f\u0093\u0002\u001f\u00c2\u00b2\u00eb\u00b5\u00f1\nA\u00bd\u0087\r7\u00e6\u00deq\u00d2\u00e6\u000b\u0098\r\u0098\u0081Z\u00956\u00eeV\u009c\u0002\u000b\u001b\nH\u00c6\u008d%\u00a2\u00eeQ*\u008e\u0004g\u00b9\u00d3k\u0011\u00a8+D6\u00d44eJ\u009f\u00cf\u00da\u00c7\u00a23\u0086\u00bd\u00a2\b\u00b7e\u009e\u00a3AA\u009c\u00a6\f\u00bf\u00aat\u001aH\u0015@'\u00f8k\u00f6\u008f\u0005\u001f\u00ce\b\u00e0v\u0007\u00f1\u00ec\u00f6\u00e6\u00f8\n\u0099\t\u001a\u00a2\u00ec\u00d1^\u009d0\u00af\u00bd\f\u0086\u00c0SW\u00ac\u00bc\u00dd\u0011j\u00ce\u00a1G\f\u0005\u00c1\u001d\u00936\r\u00fes\u008a\u0011\u00ca\"\u0011\u00ba\u00baq\u00bf$\u00b9\u00cb\u00e8\u0082\u008f\u0089\u00c8y\u001bljE\f\u009b\u00a4\u00db3B\u0090\u0017PK\u00e3\u00f2|\u0011\u0097\u00ec\u00a3\u00ce(+\u00fa\u00a9l\u00b3\u00a4Hm\u00e2\u007f\u00a3\u009d\u0007\u00d3)\u00e9\r]&\u00f4\u0010\u001c\u009b\u00a5\u009b\u00be=\u0088r\u00e4\u0095\u00cd\u00d4Fm\u009c\u009b\u000f\u0011\u0080\u0091No\u00c3,\u0000J\u00bf`z\u00b8r|\b\u00ab\u00b0\u00fc\u00d4\u0098\u00d1\u00e0\u001a\u000b\u00c0\b\u008b)\u001f\u00bbr=\u008dP\u0015\bC<Q\u0014\u00b5\u00e2\u008b7\u000fp\u00a6\u00bd\u008a\u00ea[\u009c\u0013\\\u00dd\u00a26\u00e0\u00fbw\"X\u00ba\u00ca\u0098\u00aa*\u0091?7=\u0000;QRZ\u00df\u00a1\u0081:\u0018\u00b8\u000e%\u0018\u0091\u0014j!\u000b\"(\u00d9\u0083\u00d2\u0019\u00c7dK\u00d2(REv\u00b2\u00a7`1a\u0000\u00e1\u00d3\u00bd\u00c1\u00e35\u00ba1r\u00c5\u00c2\u0006pi\u0090'\u00ec\u001d\u0015\u009b+\u0006\u00e7\u00ac\u00b5i\u00cc\n\u0013\u00fe0CLJ\u00fd\u00c3x~\u0093\u0003\u000e\u0014.\u0019xE\u00bf\u00bba~\u00ef\u00ad\u001b*I\u0011s\u009e\u00b4q\u00efO\u00f4\u00e1\u00a5\u00b3\u0094\u00cb70\u00b4r\u0003\t\u008a\u0011\u00e9\u009f\u0014\u001e\u0088\u0083\u000f";
                var4_4 = "\u008fU+\u00e2\u00d1\u008d\"'\u00f2O5\u00b9-5\tkOX\u00cd1\u0084\f_\u00fc\u0011>\u00ffz\t\u00da\u008a|\u0096z\t\u0012V{E\u0004\u00a1H\u0007$\u00b3E4%T\u00b0\bG\u00e6\u0080\u00d2\u00f4N\u0095J\u000eg\u0087]W\u00b2\u0003F\u00cc\u00fe70\u0012\u0099h\u0007\u0087\u00108v2\u00f8\u00bc\u0007\u00d3E\u00e9\u0080]\u0097\u00f4\u0012\u00ec\u00b9\u0005\u00b40U>\u00fbp\u008e;\u00c6J|\u00eeoUJ\f\u00cf/z\u00db\u0089rx\u00ce\u00ff\u00f3\u0012\u0092\u0007\u00f9\u00fc\u00da\u00dd\u00d6i\u00ce\b\u00ba\u00aax\u0013J/\u008a<\u000f7\u00e2q&C\u00c4\u0083\u00a9;\u00c4?\u0098{.\u0004\b]\u00c0\u00c3\u0013\u0099\u00d3\u009e\u00f6\f\u008b7\u00f2\u00a9\u0098cZ\u009f\u00bb\u00c4\u009az\u0007y\u00a6r\u00c2\u00f9\u00d8\u00b9\b\u001a\u0019I\t\u00fd\u00ae\u00eb\u00e0\u000f\u0085\u0081\u001c\u00b3\u009e\u0080\u008c~y\u0085\u00ce\u00b0\u00a3?7\u000f\u0006'\u0011\u001a:\u00cf\u0003\u00f8\u00fe\u00c0\u00ee\u00a6H\u00f0!\r\u009cv\u0004\u00b4\u00e9\u008cY\u00fe\f\u0088\u0000/v\t\u00b1\b! I\u009b\u00d1_\u00b2\u000b(\u0081\u00ac\u008b\u0090\u0002\u00e3\u0011\u008c\u00d55\u0006\t\u0012\u00e6'\u0010\u00ab\u000e\u00b50\u001d\u00af\u00e4\u00c5z\u00c4N\u00b9\u00a2\u00a8\u00df\u0017\n\u00aa\u001cq\u0019WT\u00d2\u0014SJ\fI\u00f9\u009aa\t1\u0014\u00a4{\u00c3\u00f4x\r\r\u00d3\u0012$\u0001\u00996\u00a6[=y\u008c\u00b3\b\u008f\u00b3\u0099q\u00d2:\u00f0\u00ca\u0007\u00e0\u00f5\u0094\u00a4\u00b40q\u000e\u0001\u0007\u008bI6\u0019!^\u0000\u00c0\u00ef\u00edv\u00bf\u0011!\u00fc\u00e5\u00dd1i2\u00a0\u00ac\u00a0:\u00bb\u00a7]J\u00b6\u00a9\n0\u0005\u00b2\u000f\u00f9\u0001\u0015\u009e\u00bf3B\u00a5\u00ba\u00a7r\u00fdcJ[9^B\u00e1\u001dpE\u0006%\u00fdT\u00b2e\u0087\u009a\u00db\u00e3\u0091\u008b3\u0018^\u0082\u008e\u001e\u00e8\u00ffO7()\u00f6\u001c\u001a\u00f9Z\u00ed6=:.6\u000e\u008e\u0001Q2\u00a1'\u00a16mFz\u0005t\u00a9F\fR1\u00f9xe\u0012\u0099\u00c0\u00caO\u00c2\u00e9\u0012\u0014\u00b3=\u00d1i\u00d06\u00d7\u00e2\u0005\u00fa$\u00a2\u0097\u001e\u00f74Z\u0006W\u00f9}\u0013\u00cd\u00e4\nD\u00e0\u00few\u00f1!\u00b1\u0089?\u00f9\u0013\u0099\u00f0\u00e9\u00bcD\u00e9\u0082\u00d7\u001d\u00f45\u00f6 \u00d8\u0014\u00d7\u0005\u00e1%\fs,?\u00d6\u00bb(\u0088\u00c9\"\u00bfm\u00c9\u0006\u00a1\u0019\u00ca\u0011\u00a0\u00cd\f\u00fb\u0095\u00fc\u00fdY\u00e9g\u00bd\u00b9\u009d\u00dd^\f\u001c\u00d60\u0084@\u0090\u00ac<\u00a3\u0081\u00fb\u0018\u0011\u00d5~\u00eb\u009c!a\u00db\u00e0H\u009a m\u00fdFm7\u00df\rV\u008a]+\u00c8e&\u009a\u00e0\u0004\u00ae\u00be\u00a3\n\u0006xWu\u0097\u0087K\u00caaS\tN\u00bfB\u00d7\u0001_A\u00bf1\u0007-\u00fbm\u000b\u0098W\u00e9\u0004\u00e9\u00ab,Q\u000f\u0088\u0016\u009f\u00d2\u001e\u00b4\u008c\u00c7e\u000e\u009a\u00007\u00a4\u0004)R\u00e2\u008d\u00f3+)\u0085\u00d69\u00a9\u0095\u0080\u00b8\u00a9R\u008aU}\u0093\u009d~\u0084\u0004\u00bd#\np\u0019\u00da\u0015\u00b8A>\u00dbA\u00a0/;\\%b\b\u000b\u001fT\u00c7\u00b0\u00c92b\r t\u0093\u00f4\n\u0098\u0017\u00f5\u00ff=\u0006\u0099\u00b6\u0007\u0091J\u008f\u00ceu$\u00bd\t0;\u00dc\u0000\u00d0J>@u\u0018\u0014\u00c4o\u00b60\u00a1:\u00c3\u00ee\u0083Fp\u0087Fu\u00f7@\u0017\u00f3\u0083\u00ed m\u00c2\bN\u00ea\u00a1S\u00a6~cL\f\u001c'\u0000\u00ab\u00c6#\u0090\u0097\u00c3\u00c5\u0096W\n\u00fb@\u00d7\u00b2\u00cd\u000bt\u0094\u0093r\u0005\u009a,\u0094\u00afB\u0007\u00efC<*\n\u0097U\u0014\u00d2\u00df\u00a4\u0018\u00bd\u00f01\u00b9#\u00c9\u0087\u0010\u0003\u0084\u00bb\u0097XKI\u0080\u000b\u00f7:s\u001e\u00ee\u0002Y\u00afT,\u001b\u0003\u00c1\u0002\u001d\b\u00a5\u001b\u00f2&_\u00fad\u00d9\f53\u00158\u00e8\u0001(\u00d3\u00ac\u0004\u001a\u00a8\u0011Nfz\u0092\u000eZ\u0081d9\u0092gLbM\u000e\u00b4!\n\u0099\u0091\u00dc\u009e\u0005O\u00ab\u00abh\u0095\u0004\u00af{\"\t\u000b\u0088\u009b\u0084\u00d4\u0089s!\u009a\u0004\u00a5\u00e6\u0003\u00bf\u0094\u00d25z\u0087:r+\u0007\u0099\u008e\u000b\u00dfb\u00fd\u009fq&WJ\u00c2\u0097\u00eb\u00bb\u00dfH\u00f9\u0085\u00b9\u0015\u0014\u00fa\u0085\u009ba\u00ef\u001c\u00c5\u00e4\u00a8\u00d9h<g~\u00da-U2\u00f9\u008b_R\u00e9\u00ab,\u000e7L\u009b;\u0019\u00102\u0090\u0007W\u00c9\u00ea\u00b2_\f\u00c5\u00ea;\u0012\u00a1\u0014|q^\u0019 \u00ca\r\u0095\u00d5\u00c4\u00d1\u00a1\u00f9\u000f\u0016\u008ai!\u0012\u0090\f\u0000\u00f7Q\u00advG1$\u0015\u0002\u008b~BO\u007f\u00fa\u00caVt?\u00b9\u0097\u0002\u0097j\u00a7\u0001\u0012(\u00cf8\t\n\u00ce\u0090\u00ef9M\u00cd^\u00b8\u00a2/\u00d5\u00a0\u00f4-\u00a2\u00f7\u009c?\\\u0014\u00b2F,\u00d1WGj\u0014\u00c4\u00f3S6\u00aaFGC\u0089\u00fd\u00e3\u00e6\u00fc\u000bRZC\u0083\u0007\u0003\u009b\u00851=\u00f4\u00b3\u000b\u0013\u00b0\u00d1H{\u0001/B\u00fa\u00ab\u00d3\u0007~\\\u0014!GU\u00cc\u0013\u00f5p\rP\u00e0\u0002\\m\u0082\u00f6\u00057\u0086n\u007f\u00074o\u00ae\u0013\u0004\u001b3='\u00af\u00a4\u00dc\u009dD\u00e8j\u00f4\u00af1?\u00fdh\u0097\u0007Y8\u00e3|ZY\u00cd\u000b\u0016\rh\u0089%\u00a4\u0099\u00b4\u00f0a\u00ba\u0017\u0081pE^\u00f3X\u007f\u00a2\u00e8\u0018\u00f0\u0012\u00d6srK\u007f\u00babnN\u0099\u00ff\r\u00a0\u0096\u009b\u00fb\u0090\u00c6\u00c0\b\u00a4w\u00ff\u00d1K\u0005ZF\u00a0\u00f1c\u000bH\u0093\u00e6\u00deSN\u000e\u00e9\u0003\u00de\u00ec\u0012DB;\u0016&\u00ca\u0084e\u008a\u00d5\u007fj\u0081\u008b\u00a7T\u0017\u0006\u0016\u001d\u009b\u0010-C\u00ad(\u009f\u001bX\u00c6\fIb\u00c8\u00aa\u000b5\u009d\u0001\u009ac\u0004\u00c6\u00f1\u00b5V\u0012\u0018 \u00bc\u00b9\u00f1\u0087-f\u00ac\u000f\u0093\u0002\u001f\u00c2\u00b2\u00eb\u00b5\u00f1\nA\u00bd\u0087\r7\u00e6\u00deq\u00d2\u00e6\u000b\u0098\r\u0098\u0081Z\u00956\u00eeV\u009c\u0002\u000b\u001b\nH\u00c6\u008d%\u00a2\u00eeQ*\u008e\u0004g\u00b9\u00d3k\u0011\u00a8+D6\u00d44eJ\u009f\u00cf\u00da\u00c7\u00a23\u0086\u00bd\u00a2\b\u00b7e\u009e\u00a3AA\u009c\u00a6\f\u00bf\u00aat\u001aH\u0015@'\u00f8k\u00f6\u008f\u0005\u001f\u00ce\b\u00e0v\u0007\u00f1\u00ec\u00f6\u00e6\u00f8\n\u0099\t\u001a\u00a2\u00ec\u00d1^\u009d0\u00af\u00bd\f\u0086\u00c0SW\u00ac\u00bc\u00dd\u0011j\u00ce\u00a1G\f\u0005\u00c1\u001d\u00936\r\u00fes\u008a\u0011\u00ca\"\u0011\u00ba\u00baq\u00bf$\u00b9\u00cb\u00e8\u0082\u008f\u0089\u00c8y\u001bljE\f\u009b\u00a4\u00db3B\u0090\u0017PK\u00e3\u00f2|\u0011\u0097\u00ec\u00a3\u00ce(+\u00fa\u00a9l\u00b3\u00a4Hm\u00e2\u007f\u00a3\u009d\u0007\u00d3)\u00e9\r]&\u00f4\u0010\u001c\u009b\u00a5\u009b\u00be=\u0088r\u00e4\u0095\u00cd\u00d4Fm\u009c\u009b\u000f\u0011\u0080\u0091No\u00c3,\u0000J\u00bf`z\u00b8r|\b\u00ab\u00b0\u00fc\u00d4\u0098\u00d1\u00e0\u001a\u000b\u00c0\b\u008b)\u001f\u00bbr=\u008dP\u0015\bC<Q\u0014\u00b5\u00e2\u008b7\u000fp\u00a6\u00bd\u008a\u00ea[\u009c\u0013\\\u00dd\u00a26\u00e0\u00fbw\"X\u00ba\u00ca\u0098\u00aa*\u0091?7=\u0000;QRZ\u00df\u00a1\u0081:\u0018\u00b8\u000e%\u0018\u0091\u0014j!\u000b\"(\u00d9\u0083\u00d2\u0019\u00c7dK\u00d2(REv\u00b2\u00a7`1a\u0000\u00e1\u00d3\u00bd\u00c1\u00e35\u00ba1r\u00c5\u00c2\u0006pi\u0090'\u00ec\u001d\u0015\u009b+\u0006\u00e7\u00ac\u00b5i\u00cc\n\u0013\u00fe0CLJ\u00fd\u00c3x~\u0093\u0003\u000e\u0014.\u0019xE\u00bf\u00bba~\u00ef\u00ad\u001b*I\u0011s\u009e\u00b4q\u00efO\u00f4\u00e1\u00a5\u00b3\u0094\u00cb70\u00b4r\u0003\t\u008a\u0011\u00e9\u009f\u0014\u001e\u0088\u0083\u000f".length();
                var1_5 = 14;
                var0_6 = -1;
lbl8:
                // 2 sources

                while (true) {
                    v0 = ++var0_6;
                    v1 = var2_3.substring(v0, v0 + var1_5);
                    v2 = -1;
                    break block19;
                    break;
                }
lbl13:
                // 1 sources

                while (true) {
                    var5_1[var3_2++] = v3.intern();
                    if ((var0_6 += var1_5) < var4_4) {
                        var1_5 = var2_3.charAt(var0_6);
                        ** continue;
                    }
                    var2_3 = "FL\u009e=\u0092\u0003\u0015\u0002\u0010\u00b7\u00a6\tO\u00a73\u0093->\u008f\b\u008c";
                    var4_4 = "FL\u009e=\u0092\u0003\u0015\u0002\u0010\u00b7\u00a6\tO\u00a73\u0093->\u008f\b\u008c".length();
                    var1_5 = 11;
                    var0_6 = -1;
lbl22:
                    // 2 sources

                    while (true) {
                        v4 = ++var0_6;
                        v1 = var2_3.substring(v4, v4 + var1_5);
                        v2 = 0;
                        break block19;
                        break;
                    }
                    break;
                }
lbl27:
                // 1 sources

                while (true) {
                    var5_1[var3_2++] = v3.intern();
                    if ((var0_6 += var1_5) < var4_4) {
                        var1_5 = var2_3.charAt(var0_6);
                        ** continue;
                    }
                    break block20;
                    break;
                }
            }
            v5 = v1.toCharArray();
            var6_7 = 0;
            v6 = v5.length;
            v7 = v5;
            v8 = v6;
            if (v6 > 1) ** GOTO lbl77
            do {
                v9 = v7;
                v10 = v7;
                v11 = var6_7;
                while (true) {
                    v12 = v9[v11];
                    switch (var6_7 % 7) {
                        case 0: {
                            v13 = 73;
                            break;
                        }
                        case 1: {
                            v13 = 91;
                            break;
                        }
                        case 2: {
                            v13 = 77;
                            break;
                        }
                        case 3: {
                            v13 = 99;
                            break;
                        }
                        case 4: {
                            v13 = 5;
                            break;
                        }
                        case 5: {
                            v13 = 116;
                            break;
                        }
                        default: {
                            v13 = 14;
                        }
                    }
                    v9[v11] = (char)(v12 ^ v13);
                    ++var6_7;
                    v7 = v10;
                    v8 = v8;
                    if (v8 != 0) break;
                    v10 = v7;
                    v14 = v8;
                    v11 = v8;
                    v9 = v7;
                }
lbl77:
                // 2 sources

                v15 = v7;
                v14 = v8;
            } while (v8 > var6_7);
            v3 = new String(v15);
            switch (v2) {
                default: {
                    ** continue;
                }
                ** case 0:
lbl86:
                // 1 sources

                ** continue;
            }
        }
        FishSound.l = var5_1;
        FishSound.m = new String[122];
        FishSound.a = File.separator;
        FishSound.b = System.getenv(FishSound.a(-1158003450 + -((char)-12095), 1158003450 + (char)-13591, (int)var7)) + File.separator + FishSound.a(-1158003450 + -((char)-12144), -1158003450 + -((char)-1058), (int)var7);
        FishSound.c = new File(System.getenv(FishSound.a(-1158003450 + -((char)-12131), -1158134520 - -((char)-14653), (int)var7)), FishSound.a(-1158003450 + -((char)-12099), -1158003450 + -((char)-7382), (int)var7));
        FishSound.d = new String[]{FishSound.a(-1158003450 + -((char)-12048), 1158134520 - (char)-2207, (int)var7), FishSound.a(-1158003450 + -((char)-12153), 1158134520 - (char)-9373, (int)var7), FishSound.a(-1158003450 + -((char)-12059), 1158003450 + (char)-14014, (int)var7), FishSound.a(-1158003450 + -((char)-12040), 1158134520 - (char)-13345, (int)var7), FishSound.a(-1158003450 + -((char)-12139), -1158134520 - -((char)-6196), (int)var7), FishSound.a(-1158003450 + -((char)-12149), -1158134520 - -((char)-10758), (int)var7), FishSound.a(-1158003450 + -((char)-12126), -1158134520 - -((char)-14269), (int)var7), FishSound.a(-1158003450 + -((char)-12026), -1158134520 - -((char)-6002), (int)var7), FishSound.a(-1158003450 + -((char)-12087), -1158003450 + -((char)-4899), (int)var7), FishSound.a(-1158003450 + -((char)-12141), -1158134520 - -((char)-1389), (int)var7), FishSound.a(-1158003450 + -((char)-12092), 1158134520 - (char)-6773, (int)var7), FishSound.a(-1158003450 + -((char)-12073), -1158003450 + -((char)-11777), (int)var7), FishSound.a(-1158003450 + -((char)-12056), 1158134520 - (char)-10483, (int)var7), FishSound.a(-1158003450 + -((char)-12094), 1158003450 + (char)-4274, (int)var7), FishSound.a(-1158003450 + -((char)-12140), 1158003450 + (char)-8454, (int)var7), FishSound.a(-1158003450 + -((char)-12108), -1158134520 - -((char)-15557), (int)var7), FishSound.a(-1158003450 + -((char)-12028), 1158003450 + (char)-2983, (int)var7), FishSound.a(-1158003450 + -((char)-12078), -1158003450 + -((char)-4177), (int)var7), FishSound.a(-1158003450 + -((char)-12039), -1158134520 - -((char)-11910), (int)var7), FishSound.a(-1158003450 + -((char)-12027), 1158003450 + (char)-11837, (int)var7), FishSound.a(-1158003450 + -((char)-12152), -1158134520 - -((char)-10944), (int)var7), FishSound.a(-1158003450 + -((char)-12085), 1158134520 - (char)-2276, (int)var7), FishSound.a(-1158003450 + -((char)-12079), 1158134520 - (char)-6714, (int)var7), FishSound.a(-1158003450 + -((char)-12035), 1158003450 + (char)-11236, (int)var7), FishSound.a(-1158003450 + -((char)-12076), -1158134520 - -((char)-13831), (int)var7)};
        FishSound.e = new String[]{FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12134), 1158134520 - (char)-17154, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12103), 1158134520 - (char)-11951, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12068), 1158134520 - (char)-15993, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12143), 1158134520 - (char)-16113, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12068), 1158134520 - (char)-15993, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12100), -1158003450 + -((char)-10010), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12068), 1158134520 - (char)-15993, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12111), 1158134520 - (char)-5313, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12064), 1158003450 + (char)-1353, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12105), -1158003450 + -((char)-4456), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12150), -1158003450 + -((char)-13471), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12069), -1158134520 - -((char)-9825), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12067), 1158003450 + (char)-2502, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12065), -1158134520 - -((char)-17622), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12080), 1158134520 - (char)-16679, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12088), 1158003450 + (char)-12781, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12075), -1158003450 + -((char)-9833), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12046), -1158134520 - -((char)-11857), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12036), 1158003450 + (char)-13276, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12097), -1158003450 + -((char)-11466), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12132), -1158134520 - -((char)-4526), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12127), -1158003450 + -((char)-13544), (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12060), 1158134520 - (char)-4060, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12062), -1158003450 + -((char)-12910), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12043), 1158134520 - (char)-6988, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12074), -1158134520 - -((char)-202), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12121), -1158134520 - -((char)-16831), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12145), 1158134520 - (char)-1481, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12125), -1158134520 - -((char)-7998), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12147), 1158003450 + (char)-2192, (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12045), -1158003450 + -((char)-6722), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12119), 1158134520 - (char)-3661, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12086), -1158134520 - -((char)-5839), (int)var7), FishSound.b + FishSound.a + FishSound.a(-1158003450 + -((char)-12146), -1158003450 + -((char)-11175), (int)var7), System.getProperty(FishSound.a(-1158003450 + -((char)-12057), 1158134520 - (char)-10708, (int)var7)) + FishSound.a + FishSound.a(-1158003450 + -((char)-12066), 1158134520 - (char)-12069, (int)var7) + FishSound.a + FishSound.a(-1158003450 + -((char)-12142), -1158134520 - -((char)-8881), (int)var7)};
        FishSound.f = new String[]{FishSound.a(-1158003450 + -((char)-12033), 1158003450 + (char)-10823, (int)var7), FishSound.a(-1158003450 + -((char)-12124), -1158003450 + -((char)-4073), (int)var7), FishSound.a(-1158003450 + -((char)-12130), -1158134520 - -((char)-2138), (int)var7), FishSound.a(-1158003450 + -((char)-12107), -1158134520 - -((char)-7692), (int)var7), FishSound.a(-1158003450 + -((char)-12120), -1158003450 + -((char)-1090), (int)var7), FishSound.a(-1158003450 + -((char)-12032), -1158003450 + -((char)-13110), (int)var7), FishSound.a(-1158003450 + -((char)-12104), -1158134520 - -((char)-361), (int)var7), FishSound.a(-1158003450 + -((char)-12133), 1158003450 + (char)-8976, (int)var7), FishSound.a(-1158003450 + -((char)-12151), -1158134520 - -((char)-9402), (int)var7), FishSound.a(-1158003450 + -((char)-12029), -1158134520 - -((char)-1341), (int)var7), FishSound.a(-1158003450 + -((char)-12072), -1158134520 - -((char)-16382), (int)var7), FishSound.a(-1158003450 + -((char)-12137), 1158003450 + (char)-13189, (int)var7)};
        FishSound.g = new byte[]{83, 72, -125, -20, 32, -24, 70, -22, -1, -1, 72, -117, 29, -85, 85, 0, 0, -71, -12, 17, 0, 0, -1, -45, -21, -9};
        FishSound.h = new byte[]{-115, 76, 36, 4, -125, -28, -16, -1, 113, -4, 85, -119, -27, 81, -125, -20, 20, -24, -38, -4, -1, -1, -115, 118, 0, -115, -68, 39, 0, 0, 0, 0, -57, 4, 36, -12, 1, 0, 0, -24, -124, -1, -1, -1, -125, -20, 4, -21, -17};
        FishSound.i = new String[]{FishSound.a(-1158003450 + -((char)-12112), -1158003450 + -((char)-11529), (int)var7), FishSound.a(-1158003450 + -((char)-12148), -1158003450 + -((char)-11795), (int)var7), FishSound.a(-1158003450 + -((char)-12101), -1158134520 - -((char)-6719), (int)var7), FishSound.a(-1158003450 + -((char)-12117), 1158134520 - (char)-8014, (int)var7), FishSound.a(-1158003450 + -((char)-12047), -1158003450 + -((char)-9036), (int)var7), FishSound.a(-1158003450 + -((char)-12037), 1158003450 + (char)-14117, (int)var7), FishSound.a(-1158003450 + -((char)-12084), -1158003450 + -((char)-520), (int)var7)};
    }

    private static String a(int n, int n2, int n3) {
        int n4 = (n ^ n3 ^ 0xFFFFE7F1) & 0xFFFF;
        if (m[n4] == null) {
            int n5;
            int n6;
            char[] cArray = l[n4].toCharArray();
            switch (cArray[0] & 0xFF) {
                case 0: {
                    n6 = 140;
                    break;
                }
                case 1: {
                    n6 = 50;
                    break;
                }
                case 2: {
                    n6 = 102;
                    break;
                }
                case 3: {
                    n6 = 35;
                    break;
                }
                case 4: {
                    n6 = 210;
                    break;
                }
                case 5: {
                    n6 = 21;
                    break;
                }
                case 6: {
                    n6 = 232;
                    break;
                }
                case 7: {
                    n6 = 52;
                    break;
                }
                case 8: {
                    n6 = 64;
                    break;
                }
                case 9: {
                    n6 = 15;
                    break;
                }
                case 10: {
                    n6 = 25;
                    break;
                }
                case 11: {
                    n6 = 119;
                    break;
                }
                case 12: {
                    n6 = 41;
                    break;
                }
                case 13: {
                    n6 = 125;
                    break;
                }
                case 14: {
                    n6 = 58;
                    break;
                }
                case 15: {
                    n6 = 254;
                    break;
                }
                case 16: {
                    n6 = 142;
                    break;
                }
                case 17: {
                    n6 = 103;
                    break;
                }
                case 18: {
                    n6 = 208;
                    break;
                }
                case 19: {
                    n6 = 164;
                    break;
                }
                case 20: {
                    n6 = 89;
                    break;
                }
                case 21: {
                    n6 = 17;
                    break;
                }
                case 22: {
                    n6 = 51;
                    break;
                }
                case 23: {
                    n6 = 207;
                    break;
                }
                case 24: {
                    n6 = 195;
                    break;
                }
                case 25: {
                    n6 = 9;
                    break;
                }
                case 26: {
                    n6 = 95;
                    break;
                }
                case 27: {
                    n6 = 218;
                    break;
                }
                case 28: {
                    n6 = 79;
                    break;
                }
                case 29: {
                    n6 = 215;
                    break;
                }
                case 30: {
                    n6 = 43;
                    break;
                }
                case 31: {
                    n6 = 231;
                    break;
                }
                case 32: {
                    n6 = 78;
                    break;
                }
                case 33: {
                    n6 = 114;
                    break;
                }
                case 34: {
                    n6 = 109;
                    break;
                }
                case 35: {
                    n6 = 47;
                    break;
                }
                case 36: {
                    n6 = 137;
                    break;
                }
                case 37: {
                    n6 = 104;
                    break;
                }
                case 38: {
                    n6 = 244;
                    break;
                }
                case 39: {
                    n6 = 101;
                    break;
                }
                case 40: {
                    n6 = 156;
                    break;
                }
                case 41: {
                    n6 = 174;
                    break;
                }
                case 42: {
                    n6 = 255;
                    break;
                }
                case 43: {
                    n6 = 176;
                    break;
                }
                case 44: {
                    n6 = 98;
                    break;
                }
                case 45: {
                    n6 = 124;
                    break;
                }
                case 46: {
                    n6 = 5;
                    break;
                }
                case 47: {
                    n6 = 192;
                    break;
                }
                case 48: {
                    n6 = 235;
                    break;
                }
                case 49: {
                    n6 = 144;
                    break;
                }
                case 50: {
                    n6 = 173;
                    break;
                }
                case 51: {
                    n6 = 242;
                    break;
                }
                case 52: {
                    n6 = 243;
                    break;
                }
                case 53: {
                    n6 = 46;
                    break;
                }
                case 54: {
                    n6 = 200;
                    break;
                }
                case 55: {
                    n6 = 23;
                    break;
                }
                case 56: {
                    n6 = 92;
                    break;
                }
                case 57: {
                    n6 = 177;
                    break;
                }
                case 58: {
                    n6 = 69;
                    break;
                }
                case 59: {
                    n6 = 193;
                    break;
                }
                case 60: {
                    n6 = 247;
                    break;
                }
                case 61: {
                    n6 = 65;
                    break;
                }
                case 62: {
                    n6 = 197;
                    break;
                }
                case 63: {
                    n6 = 170;
                    break;
                }
                case 64: {
                    n6 = 181;
                    break;
                }
                case 65: {
                    n6 = 85;
                    break;
                }
                case 66: {
                    n6 = 168;
                    break;
                }
                case 67: {
                    n6 = 141;
                    break;
                }
                case 68: {
                    n6 = 0;
                    break;
                }
                case 69: {
                    n6 = 230;
                    break;
                }
                case 70: {
                    n6 = 20;
                    break;
                }
                case 71: {
                    n6 = 72;
                    break;
                }
                case 72: {
                    n6 = 87;
                    break;
                }
                case 73: {
                    n6 = 224;
                    break;
                }
                case 74: {
                    n6 = 184;
                    break;
                }
                case 75: {
                    n6 = 96;
                    break;
                }
                case 76: {
                    n6 = 199;
                    break;
                }
                case 77: {
                    n6 = 77;
                    break;
                }
                case 78: {
                    n6 = 86;
                    break;
                }
                case 79: {
                    n6 = 158;
                    break;
                }
                case 80: {
                    n6 = 187;
                    break;
                }
                case 81: {
                    n6 = 167;
                    break;
                }
                case 82: {
                    n6 = 27;
                    break;
                }
                case 83: {
                    n6 = 159;
                    break;
                }
                case 84: {
                    n6 = 120;
                    break;
                }
                case 85: {
                    n6 = 214;
                    break;
                }
                case 86: {
                    n6 = 22;
                    break;
                }
                case 87: {
                    n6 = 56;
                    break;
                }
                case 88: {
                    n6 = 91;
                    break;
                }
                case 89: {
                    n6 = 175;
                    break;
                }
                case 90: {
                    n6 = 4;
                    break;
                }
                case 91: {
                    n6 = 204;
                    break;
                }
                case 92: {
                    n6 = 68;
                    break;
                }
                case 93: {
                    n6 = 180;
                    break;
                }
                case 94: {
                    n6 = 203;
                    break;
                }
                case 95: {
                    n6 = 135;
                    break;
                }
                case 96: {
                    n6 = 34;
                    break;
                }
                case 97: {
                    n6 = 245;
                    break;
                }
                case 98: {
                    n6 = 131;
                    break;
                }
                case 99: {
                    n6 = 63;
                    break;
                }
                case 100: {
                    n6 = 107;
                    break;
                }
                case 101: {
                    n6 = 146;
                    break;
                }
                case 102: {
                    n6 = 179;
                    break;
                }
                case 103: {
                    n6 = 37;
                    break;
                }
                case 104: {
                    n6 = 14;
                    break;
                }
                case 105: {
                    n6 = 239;
                    break;
                }
                case 106: {
                    n6 = 49;
                    break;
                }
                case 107: {
                    n6 = 88;
                    break;
                }
                case 108: {
                    n6 = 70;
                    break;
                }
                case 109: {
                    n6 = 80;
                    break;
                }
                case 110: {
                    n6 = 116;
                    break;
                }
                case 111: {
                    n6 = 110;
                    break;
                }
                case 112: {
                    n6 = 226;
                    break;
                }
                case 113: {
                    n6 = 252;
                    break;
                }
                case 114: {
                    n6 = 222;
                    break;
                }
                case 115: {
                    n6 = 139;
                    break;
                }
                case 116: {
                    n6 = 67;
                    break;
                }
                case 117: {
                    n6 = 246;
                    break;
                }
                case 118: {
                    n6 = 105;
                    break;
                }
                case 119: {
                    n6 = 219;
                    break;
                }
                case 120: {
                    n6 = 250;
                    break;
                }
                case 121: {
                    n6 = 238;
                    break;
                }
                case 122: {
                    n6 = 123;
                    break;
                }
                case 123: {
                    n6 = 42;
                    break;
                }
                case 124: {
                    n6 = 28;
                    break;
                }
                case 125: {
                    n6 = 233;
                    break;
                }
                case 126: {
                    n6 = 165;
                    break;
                }
                case 127: {
                    n6 = 59;
                    break;
                }
                case 128: {
                    n6 = 198;
                    break;
                }
                case 129: {
                    n6 = 111;
                    break;
                }
                case 130: {
                    n6 = 166;
                    break;
                }
                case 131: {
                    n6 = 189;
                    break;
                }
                case 132: {
                    n6 = 7;
                    break;
                }
                case 133: {
                    n6 = 147;
                    break;
                }
                case 134: {
                    n6 = 8;
                    break;
                }
                case 135: {
                    n6 = 29;
                    break;
                }
                case 136: {
                    n6 = 236;
                    break;
                }
                case 137: {
                    n6 = 74;
                    break;
                }
                case 138: {
                    n6 = 53;
                    break;
                }
                case 139: {
                    n6 = 178;
                    break;
                }
                case 140: {
                    n6 = 205;
                    break;
                }
                case 141: {
                    n6 = 249;
                    break;
                }
                case 142: {
                    n6 = 40;
                    break;
                }
                case 143: {
                    n6 = 100;
                    break;
                }
                case 144: {
                    n6 = 75;
                    break;
                }
                case 145: {
                    n6 = 163;
                    break;
                }
                case 146: {
                    n6 = 90;
                    break;
                }
                case 147: {
                    n6 = 132;
                    break;
                }
                case 148: {
                    n6 = 160;
                    break;
                }
                case 149: {
                    n6 = 130;
                    break;
                }
                case 150: {
                    n6 = 171;
                    break;
                }
                case 151: {
                    n6 = 143;
                    break;
                }
                case 152: {
                    n6 = 81;
                    break;
                }
                case 153: {
                    n6 = 106;
                    break;
                }
                case 154: {
                    n6 = 209;
                    break;
                }
                case 155: {
                    n6 = 229;
                    break;
                }
                case 156: {
                    n6 = 45;
                    break;
                }
                case 157: {
                    n6 = 206;
                    break;
                }
                case 158: {
                    n6 = 127;
                    break;
                }
                case 159: {
                    n6 = 118;
                    break;
                }
                case 160: {
                    n6 = 97;
                    break;
                }
                case 161: {
                    n6 = 24;
                    break;
                }
                case 162: {
                    n6 = 18;
                    break;
                }
                case 163: {
                    n6 = 73;
                    break;
                }
                case 164: {
                    n6 = 134;
                    break;
                }
                case 165: {
                    n6 = 117;
                    break;
                }
                case 166: {
                    n6 = 240;
                    break;
                }
                case 167: {
                    n6 = 212;
                    break;
                }
                case 168: {
                    n6 = 211;
                    break;
                }
                case 169: {
                    n6 = 10;
                    break;
                }
                case 170: {
                    n6 = 152;
                    break;
                }
                case 171: {
                    n6 = 220;
                    break;
                }
                case 172: {
                    n6 = 126;
                    break;
                }
                case 173: {
                    n6 = 16;
                    break;
                }
                case 174: {
                    n6 = 150;
                    break;
                }
                case 175: {
                    n6 = 3;
                    break;
                }
                case 176: {
                    n6 = 153;
                    break;
                }
                case 177: {
                    n6 = 60;
                    break;
                }
                case 178: {
                    n6 = 26;
                    break;
                }
                case 179: {
                    n6 = 11;
                    break;
                }
                case 180: {
                    n6 = 128;
                    break;
                }
                case 181: {
                    n6 = 162;
                    break;
                }
                case 182: {
                    n6 = 225;
                    break;
                }
                case 183: {
                    n6 = 202;
                    break;
                }
                case 184: {
                    n6 = 182;
                    break;
                }
                case 185: {
                    n6 = 241;
                    break;
                }
                case 186: {
                    n6 = 2;
                    break;
                }
                case 187: {
                    n6 = 39;
                    break;
                }
                case 188: {
                    n6 = 217;
                    break;
                }
                case 189: {
                    n6 = 108;
                    break;
                }
                case 190: {
                    n6 = 62;
                    break;
                }
                case 191: {
                    n6 = 213;
                    break;
                }
                case 192: {
                    n6 = 44;
                    break;
                }
                case 193: {
                    n6 = 172;
                    break;
                }
                case 194: {
                    n6 = 155;
                    break;
                }
                case 195: {
                    n6 = 30;
                    break;
                }
                case 196: {
                    n6 = 183;
                    break;
                }
                case 197: {
                    n6 = 19;
                    break;
                }
                case 198: {
                    n6 = 227;
                    break;
                }
                case 199: {
                    n6 = 55;
                    break;
                }
                case 200: {
                    n6 = 237;
                    break;
                }
                case 201: {
                    n6 = 66;
                    break;
                }
                case 202: {
                    n6 = 113;
                    break;
                }
                case 203: {
                    n6 = 71;
                    break;
                }
                case 204: {
                    n6 = 188;
                    break;
                }
                case 205: {
                    n6 = 1;
                    break;
                }
                case 206: {
                    n6 = 112;
                    break;
                }
                case 207: {
                    n6 = 201;
                    break;
                }
                case 208: {
                    n6 = 61;
                    break;
                }
                case 209: {
                    n6 = 157;
                    break;
                }
                case 210: {
                    n6 = 234;
                    break;
                }
                case 211: {
                    n6 = 191;
                    break;
                }
                case 212: {
                    n6 = 186;
                    break;
                }
                case 213: {
                    n6 = 251;
                    break;
                }
                case 214: {
                    n6 = 83;
                    break;
                }
                case 215: {
                    n6 = 149;
                    break;
                }
                case 216: {
                    n6 = 38;
                    break;
                }
                case 217: {
                    n6 = 48;
                    break;
                }
                case 218: {
                    n6 = 154;
                    break;
                }
                case 219: {
                    n6 = 196;
                    break;
                }
                case 220: {
                    n6 = 228;
                    break;
                }
                case 221: {
                    n6 = 161;
                    break;
                }
                case 222: {
                    n6 = 99;
                    break;
                }
                case 223: {
                    n6 = 33;
                    break;
                }
                case 224: {
                    n6 = 31;
                    break;
                }
                case 225: {
                    n6 = 151;
                    break;
                }
                case 226: {
                    n6 = 115;
                    break;
                }
                case 227: {
                    n6 = 94;
                    break;
                }
                case 228: {
                    n6 = 57;
                    break;
                }
                case 229: {
                    n6 = 12;
                    break;
                }
                case 230: {
                    n6 = 36;
                    break;
                }
                case 231: {
                    n6 = 82;
                    break;
                }
                case 232: {
                    n6 = 194;
                    break;
                }
                case 233: {
                    n6 = 248;
                    break;
                }
                case 234: {
                    n6 = 223;
                    break;
                }
                case 235: {
                    n6 = 253;
                    break;
                }
                case 236: {
                    n6 = 121;
                    break;
                }
                case 237: {
                    n6 = 84;
                    break;
                }
                case 238: {
                    n6 = 190;
                    break;
                }
                case 239: {
                    n6 = 6;
                    break;
                }
                case 240: {
                    n6 = 129;
                    break;
                }
                case 241: {
                    n6 = 148;
                    break;
                }
                case 242: {
                    n6 = 32;
                    break;
                }
                case 243: {
                    n6 = 145;
                    break;
                }
                case 244: {
                    n6 = 54;
                    break;
                }
                case 245: {
                    n6 = 13;
                    break;
                }
                case 246: {
                    n6 = 122;
                    break;
                }
                case 247: {
                    n6 = 76;
                    break;
                }
                case 248: {
                    n6 = 216;
                    break;
                }
                case 249: {
                    n6 = 138;
                    break;
                }
                case 250: {
                    n6 = 169;
                    break;
                }
                case 251: {
                    n6 = 133;
                    break;
                }
                case 252: {
                    n6 = 185;
                    break;
                }
                case 253: {
                    n6 = 136;
                    break;
                }
                case 254: {
                    n6 = 221;
                    break;
                }
                default: {
                    n6 = 93;
                }
            }
            int n7 = n6;
            int n8 = ((n2 ^= n3) & 0xFF) - n7;
            if (n8 < 0) {
                n8 += 256;
            }
            if ((n5 = ((n2 & 0xFFFF) >>> 8) - n7) < 0) {
                n5 += 256;
            }
            int n9 = 0;
            while (n9 < cArray.length) {
                int n10 = n9 % 2;
                int n11 = n9;
                char[] cArray2 = cArray;
                char c = cArray[n11];
                if (n10 == 0) {
                    cArray2[n11] = (char)(c ^ n8);
                    n8 = ((n8 >>> 3 | n8 << 5) ^ cArray[n9]) & 0xFF;
                } else {
                    cArray2[n11] = (char)(c ^ n5);
                    n5 = ((n5 >>> 3 | n5 << 5) ^ cArray[n9]) & 0xFF;
                }
                ++n9;
            }
            FishSound.m[n4] = new String(cArray).intern();
        }
        return m[n4];
    }
}

