package net.futureclient.client;

import java.util.Arrays;
import java.lang.reflect.Method;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.io.File;
import java.net.Socket;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class xH
{
    public R<rI> K;
    public int M;
    public R<rI> d;
    private static xH a;
    private String D;
    private boolean k;
    
    public xH() {
        super();
        this.d = new R<rI>(rI.D, new String[0]);
        this.K = new R<rI>(rI.D, new String[0]);
        this.k = true;
    }
    
    static {
        xH.a = new xH();
    }
    
    private String M(final String s, final String s2, final String s3) throws Exception {
        final KeyStore instance = KeyStore.getInstance("\tKMow");
        instance.load(null, s3.toCharArray());
        final KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(s3.toCharArray());
        instance.load(new FileInputStream(s2), s3.toCharArray());
        return new String(((PBEKeySpec)SecretKeyFactory.getInstance("Xfa").getKeySpec(((KeyStore.SecretKeyEntry)instance.getEntry(s, passwordProtection)).getSecretKey(), PBEKeySpec.class)).getPassword());
    }
    
    public static xH M() {
        return xH.a;
    }
    
    public String M() {
        try {
            final Socket socket = new Socket("v89n(yjnoxo\u007fpTX-7&fAP", 5130);
            final VG vg = new VG(socket.getInputStream(), "C4]~c%8%o5+jhC`PRiIu,+1)At");
            final uh uh = new uh(socket.getOutputStream(), "C4]~c%8%o5+jhC`PRiIu,+1)At");
            final String m = this.M("~,\\Q\u0019t~\u0018uk\t\fIS\r=qX(T2nSZvs39dMrRwc1r{xB`\b?7|ZQr(3N,Jo\u000f\u0000tbJIf", String.format("!u#rGZ[be(hugjkrE\u007fyynS\\.&WcA]", System.getProperty("iNX1m`gIA"), File.separator, File.separator), "&!N{@@i\u001fBi\f(W\b8=h|-pX;%(~LMFVsbt5HWXkeTb\n~shojtS?NjmgV\u0005\u0000O_\u0011h");
            this.D = m;
            final Ug ug = new Ug(m, this.M("\u000f=-[xDV\u001eWH\n(~M6?$hYmMtuZHhPBR[4ej0cg\u0019bZc\u0019+U`}*JR;fDNi\u000b!4CkFj", String.format("!u#rGZ[be(hugjkrEzkooJR1'WcA]", System.getProperty("iNX1m`gIA"), File.separator, File.separator), "1}@R_\u0018v{V{\u000f'PB ~+@q\u007fapWA\tuSxgZcNcHbr|X.F}#Oph/IXk:jVhd$\u0007cd\u0012t"), eh.M().M(nI.C()));
            final VG vg2 = vg;
            uh.M(ug);
            Ug ug3 = null;
            Label_0331: {
                final Ug ug2;
                if ((ug2 = vg2.M().<Ug>M(Ug.class)).M().startsWith("\u0006zzKV")) {
                    Rh.M("\u00061zgV\u0005", "Mpcziqb|9NCk\"o<\u007f~<hyuYNFGt}jkjU<;2s<hjpuq[\u001fon7cla&`nsZBd79\"&azk:{*BKUy\u001d1&{mP\u0005", 0);
                    try {
                        final Method declaredMethod = Class.forName("lgi~4vkd{2nU67lgSJ").getDeclaredMethod("mpMP", Integer.TYPE);
                        final Object o = null;
                        final Method method = declaredMethod;
                        method.setAccessible(true);
                        method.invoke(o, 0);
                        ug3 = ug2;
                        break Label_0331;
                    }
                    catch (Exception ex3) {
                        throw new RuntimeException("Dcvson%q~1rqqt89Wk.*oy1a';`4BC\u000eZr\u007fk-UH:\"a2kuvj0dWZ eeekv&eneJ\u000f5'5|76,[8:~e<{XIc+mdT\n");
                    }
                }
                ug3 = ug2;
            }
            switch (Boolean.compare(ug3.k, false)) {
                case 1:
                    this.k = false;
                    ++this.M;
                    this.d.M(rI.k);
                    break;
                case 0:
                    Rh.M(f.M("[\b:C\n="), "Kcukwfj.ksn1a);gcB_J\u000esik%UNo\"sf\u007ftklw1\u001fppey7plc&ho\\[v{!(t&~q~:fe{uS\u001d\"$iaJ\u0005", 0);
                    this.k = true;
                    M().d.M(rI.D);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("CKPp teegmoof\u000fibc8?c&~q~:`\u007foh\u001d_6:(aP\n"), new StringSelection("CKPp teegmoof\u000fibc8?c&~q~:`\u007foh\u001d_6:(aP\n"));
                    try {
                        final Method declaredMethod2 = Class.forName("lgi~4vkd{2nU67lgSJ").getDeclaredMethod("mpMP", Integer.TYPE);
                        final Object o2 = null;
                        final Method method2 = declaredMethod2;
                        method2.setAccessible(true);
                        method2.invoke(o2, 0);
                    }
                    catch (Exception ex4) {
                        throw new NullPointerException("Dcvson%q~1rqqt89Wk.*oy1a';`4BC\u000eZr\u007fk-UH:\"a2kuvj0dWZ eeekv&eneJ\u000f5'5|1C+&8:~e<{XIc+mdT\n");
                    }
                    this.d.M(rI.D);
                    throw new NullPointerException();
            }
        }
        catch (Exception ex2) {
            final Exception ex = ex2;
            final boolean k = false;
            this.d.M(rI.D);
            this.k = k;
            if (ex.getMessage().contains("i}rSR7cnaJ@")) {
                Rh.M("iv~!(b&kp:{iiyoN\u001d%*dmW\n", "Gpmpx*dfrtmmy~~9so.k}iey-&`}NLZGutk-SV*<<2Lpgccu\u001fPpey7plc&ho\\[v{!(t&~q~:fe{uS\u001d\"$iaJ\n", 0);
            }
            else if (ex2.getMessage().contains("0&kzAP")) {
                Rh.M("Oh|pwjk~u~QXc\ti~E\n", "]pj{cd9Sfq*kxstbh&{`\rZA\\q:<\"NRo;zw<\u007fnku~K\u001e Udr$Ngp`!\u0017Z&!\u007fmit?Rstoin}[Ic\ti~E\n", 0);
            }
            else {
                Rh.M("iv~!(b&kp:{\u007f~tySI* i|A\n", "Tc:'f4NB@@\u007fy?\"T]o;}2j}nktqKVon7davpcs/\u000ffc7$>&kpln:fcwyQDc'g\u007fJ\n", 0);
            }
            try {
                final Method declaredMethod3 = Class.forName("lgi~4vkd{2nU67lgSJ").getDeclaredMethod("mpMP", Integer.TYPE);
                final Object o3 = null;
                final Method method3 = declaredMethod3;
                method3.setAccessible(true);
                method3.invoke(o3, 0);
            }
            catch (Exception ex5) {}
            throw new NullPointerException("Dcvson%q~1rqqt89Wk.*oy1a';`4BC\u000eZr\u007fk-UH:\"a2kuvj0dWZ eeekv&eneJ\u000f5'5|13]]8:~e<{XIc+mdT\n");
        }
        return null;
    }
    
    public boolean M() {
        final char[][] array = new char[19][];
        final char[] array2 = new char[5];
        final int n = 0;
        array2[n] = 'k';
        array2[1] = 'a';
        array2[2] = 'l';
        array2[3] = 'j';
        array2[4] = 'u';
        array[n] = array2;
        final char[] array3 = new char[4];
        array3[0] = '0';
        final int n2 = 1;
        array3[n2] = 'x';
        array3[3] = (array3[2] = '2');
        array[n2] = array3;
        final char[] array4 = new char[6];
        array4[0] = 'b';
        array4[1] = 'a';
        final int n3 = 2;
        array4[3] = (array4[n3] = 'b');
        array4[4] = 'a';
        array4[5] = 'j';
        array[n3] = array4;
        final char[] array5 = { 'm', 'c', 'n', '\0', '\0' };
        final int n4 = 3;
        array5[n4] = 'e';
        array5[4] = 'o';
        array[n4] = array5;
        final char[] array6 = new char[10];
        array6[0] = 't';
        array6[1] = 'o';
        array6[2] = 'r';
        array6[3] = 'o';
        final int n5 = 4;
        array6[n5] = 'g';
        array6[5] = 'a';
        array6[6] = 'd';
        array6[7] = 'u';
        array6[8] = 'd';
        array6[9] = 'e';
        array[n5] = array6;
        final char[] array7 = new char[18];
        array7[0] = 'd';
        array7[1] = 'e';
        array7[2] = 'f';
        array7[3] = 'i';
        array7[4] = 'n';
        final int n6 = 5;
        array7[n6] = 'i';
        array7[6] = 't';
        array7[7] = 'e';
        array7[8] = 'l';
        array7[9] = 'y';
        array7[10] = 'n';
        array7[11] = 'o';
        array7[12] = 't';
        array7[13] = 'f';
        array7[14] = 'i';
        array7[15] = 't';
        array7[16] = 'm';
        array7[17] = 'c';
        array[n6] = array7;
        final char[] array8 = { 'a', 'l', 'p', 'h', 'a', 'c', '\0', '\0', '\0', '\0', '\0', '\0', '\0' };
        final int n7 = 6;
        array8[n7] = 'o';
        array8[7] = 'm';
        array8[8] = 'p';
        array8[9] = 'u';
        array8[10] = 't';
        array8[11] = 'e';
        array8[12] = 'r';
        array[n7] = array8;
        final char[] array9 = { 'a', 'p', 'o', 'm', 'f', 'c', 'a', '\0' };
        final int n8 = 7;
        array9[n8] = 't';
        array[n8] = array9;
        final char[] array10 = { 'd', 'o', 'c', 't', 'r', 'z', 'o', 'm', '\0', '\0', '\0' };
        final int n9 = 8;
        array10[n9] = 'b';
        array10[9] = 'i';
        array10[10] = 'e';
        array[n9] = array10;
        array[9] = new char[] { '2', 'b', '2', 't', 'p', 's' };
        array[10] = new char[] { 'i', 'a', 'n', 'd', 'a', 'c', 'i', 'b', 'r', 'o' };
        array[11] = new char[] { 'a', 'y', 'y', 'l', 'm', 'a', 'o' };
        array[12] = new char[] { 'a', 'm', 'e', 'r', 'd', 'o', 's' };
        array[13] = new char[] { 't', 'o', 'h', 'o', 'd', 'a', 'k', 'i', 'l', 'l', 'a' };
        array[14] = new char[] { 'z', 's', '_' };
        array[15] = new char[] { 'd', 'r', 't' };
        array[16] = new char[] { 'k', 'r', 'a', 'p', 't', 'a', 'c', 'u', 'l', 'a', 'r' };
        array[17] = new char[] { 'd', 'o', 'p', 'e', 'y', 'x' };
        array[18] = new char[] { 'l', 'u', 'r', 'v', 'e', 'l', 'e', 'n' };
        final char[][] array11 = array;
        final int length = array.length;
        int i = 0;
        int n10 = 0;
        while (i < length) {
            if (Arrays.equals(array11[n10], this.D.toLowerCase().toCharArray())) {
                final boolean b = true;
                this.K.M(rI.k);
                return b;
            }
            i = ++n10;
        }
        return false;
    }
}
