// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Say extends Command
{
    private static final /* synthetic */ int[] llIlIllI;
    private static final /* synthetic */ String[] llIlIIll;
    
    private static void lIIlllllIl() {
        (llIlIIll = new String[Say.llIlIllI[6]])[Say.llIlIllI[0]] = lIIlllIllI("y53Ls3F5L60=", "PEEoh");
        Say.llIlIIll[Say.llIlIllI[1]] = lIIlllIllI("4F2MS/teKhM=", "dFBsd");
        Say.llIlIIll[Say.llIlIllI[2]] = lIIlllIllI("2Pazr1Ccg1o=", "hEubp");
        Say.llIlIIll[Say.llIlIllI[3]] = lIIllllIIl("HgAxAj9xUw==", "KsPeZ");
        Say.llIlIIll[Say.llIlIllI[4]] = lIIllllIIl("PyInAGcBIjoXJgsiaRAoTCQhBTNC", "lGIdG");
        Say.llIlIIll[Say.llIlIllI[5]] = lIIlllIllI("+D8HhM72OFVGVj+RnQs5HQ==", "VJlFa");
    }
    
    @Override
    public String getSyntax() {
        return Say.llIlIIll[Say.llIlIllI[5]];
    }
    
    private static void lIIllllllI() {
        (llIlIllI = new int[8])[0] = ((0x4 ^ 0x38) & ~(0x47 ^ 0x7B));
        Say.llIlIllI[1] = " ".length();
        Say.llIlIllI[2] = "  ".length();
        Say.llIlIllI[3] = "   ".length();
        Say.llIlIllI[4] = (0xCF ^ 0xA7 ^ (0x24 ^ 0x48));
        Say.llIlIllI[5] = (0x51 ^ 0x54);
        Say.llIlIllI[6] = (0x76 ^ 0x70);
        Say.llIlIllI[7] = (0x3A ^ 0x32);
    }
    
    private static String lIIlllIllI(final String lIllIlllIIlllII, final String lIllIlllIIlllIl) {
        try {
            final SecretKeySpec lIllIlllIlIIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlllIIlllIl.getBytes(StandardCharsets.UTF_8)), Say.llIlIllI[7]), "DES");
            final Cipher lIllIlllIlIIIII = Cipher.getInstance("DES");
            lIllIlllIlIIIII.init(Say.llIlIllI[2], lIllIlllIlIIIIl);
            return new String(lIllIlllIlIIIII.doFinal(Base64.getDecoder().decode(lIllIlllIIlllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIlllIIlllll) {
            lIllIlllIIlllll.printStackTrace();
            return null;
        }
    }
    
    static {
        lIIllllllI();
        lIIlllllIl();
    }
    
    private static String lIIllllIIl(String lIllIlllIlIlllI, final String lIllIlllIlIllIl) {
        lIllIlllIlIlllI = (short)new String(Base64.getDecoder().decode(((String)lIllIlllIlIlllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllIlllIllIIIl = new StringBuilder();
        final char[] lIllIlllIllIIII = lIllIlllIlIllIl.toCharArray();
        int lIllIlllIlIllll = Say.llIlIllI[0];
        final boolean lIllIlllIlIlIIl = (Object)((String)lIllIlllIlIlllI).toCharArray();
        final double lIllIlllIlIlIII = lIllIlllIlIlIIl.length;
        long lIllIlllIlIIlll = Say.llIlIllI[0];
        while (lIIlllllll((int)lIllIlllIlIIlll, (int)lIllIlllIlIlIII)) {
            final char lIllIlllIllIlII = lIllIlllIlIlIIl[lIllIlllIlIIlll];
            lIllIlllIllIIIl.append((char)(lIllIlllIllIlII ^ lIllIlllIllIIII[lIllIlllIlIllll % lIllIlllIllIIII.length]));
            "".length();
            ++lIllIlllIlIllll;
            ++lIllIlllIlIIlll;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return String.valueOf(lIllIlllIllIIIl);
    }
    
    @Override
    public void runCommand(final String lIllIllllIIIlIl, final String[] lIllIllllIIIlII) {
        try {
            String lIllIllllIIlIII = Say.llIlIIll[Say.llIlIllI[1]];
            int lIllIllllIIlIIl = Say.llIlIllI[0];
            while (lIIlllllll(lIllIllllIIlIIl, lIllIllllIIIlII.length)) {
                lIllIllllIIlIII = String.valueOf(new StringBuilder().append(lIllIllllIIlIII).append(Say.llIlIIll[Say.llIlIllI[2]]).append(lIllIllllIIIlII[lIllIllllIIlIIl]));
                ++lIllIllllIIlIIl;
                "".length();
                if ((0x81 ^ 0x89 ^ (0x60 ^ 0x6C)) == 0x0) {
                    return;
                }
            }
            Wrapper.INSTANCE.sendPacket((Packet)new CPacketChatMessage(lIllIllllIIlIII));
            "".length();
            if ("  ".length() < ((184 + 184 - 260 + 142 ^ 91 + 87 - 59 + 58) & (11 + 202 - 72 + 69 ^ 6 + 28 - 6 + 125 ^ -" ".length()))) {
                return;
            }
        }
        catch (Exception lIllIllllIIIlll) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Say.llIlIIll[Say.llIlIllI[3]]).append(this.getSyntax())));
        }
    }
    
    @Override
    public String getDescription() {
        return Say.llIlIIll[Say.llIlIllI[4]];
    }
    
    private static boolean lIIlllllll(final int lIllIlllIIlIllI, final int lIllIlllIIlIlIl) {
        return lIllIlllIIlIllI < lIllIlllIIlIlIl;
    }
    
    public Say() {
        super(Say.llIlIIll[Say.llIlIllI[0]]);
    }
}
