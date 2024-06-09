// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import java.util.Arrays;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.ArrayList;

public class PlayerFinder extends Command
{
    private static final /* synthetic */ int[] llIlIl;
    private static final /* synthetic */ String[] lIllll;
    
    static {
        llIIIIlI();
        lIlllllI();
    }
    
    public PlayerFinder() {
        super(PlayerFinder.lIllll[PlayerFinder.llIlIl[0]]);
    }
    
    @Override
    public void runCommand(final String lllIIlIlllIIIll, final String[] lllIIlIlllIIIII) {
        try {
            final ArrayList<String> lllIIlIlllIIllI = new ArrayList<String>();
            if (llIIIIll(lllIIlIlllIIIII[PlayerFinder.llIlIl[0]].equalsIgnoreCase(PlayerFinder.lIllll[PlayerFinder.llIlIl[1]]) ? 1 : 0)) {
                final byte lllIIlIllIllllI = (byte)Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap().iterator();
                while (llIIIIll(((Iterator)lllIIlIllIllllI).hasNext() ? 1 : 0)) {
                    final NetworkPlayerInfo lllIIlIlllIlIII = ((Iterator<NetworkPlayerInfo>)lllIIlIllIllllI).next();
                    lllIIlIlllIIllI.add(String.valueOf(new StringBuilder().append(PlayerFinder.lIllll[PlayerFinder.llIlIl[2]]).append(lllIIlIlllIlIII.getGameProfile().getName())));
                    "".length();
                    "".length();
                    if (" ".length() > " ".length()) {
                        return;
                    }
                }
                "".length();
                if (-" ".length() == "  ".length()) {
                    return;
                }
            }
            else if (llIIIIll(lllIIlIlllIIIII[PlayerFinder.llIlIl[0]].equalsIgnoreCase(PlayerFinder.lIllll[PlayerFinder.llIlIl[3]]) ? 1 : 0)) {
                final byte lllIIlIllIllllI = (byte)Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap().iterator();
                while (llIIIIll(((Iterator)lllIIlIllIllllI).hasNext() ? 1 : 0)) {
                    final NetworkPlayerInfo lllIIlIlllIIlll = ((Iterator<NetworkPlayerInfo>)lllIIlIllIllllI).next();
                    if (llIIIIll(lllIIlIlllIIlll.getGameType().isCreative() ? 1 : 0)) {
                        lllIIlIlllIIllI.add(String.valueOf(new StringBuilder().append(PlayerFinder.lIllll[PlayerFinder.llIlIl[4]]).append(lllIIlIlllIIlll.getGameProfile().getName())));
                        "".length();
                    }
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
            }
            if (llIIIIll(lllIIlIlllIIllI.isEmpty() ? 1 : 0)) {
                ChatUtils.error(PlayerFinder.lIllll[PlayerFinder.llIlIl[5]]);
                "".length();
                if (-"  ".length() >= 0) {
                    return;
                }
            }
            else {
                Wrapper.INSTANCE.copy(lllIIlIlllIIllI.toString());
                ChatUtils.message(PlayerFinder.lIllll[PlayerFinder.llIlIl[6]]);
            }
            "".length();
            if ((0xE3 ^ 0xA6 ^ (0x3A ^ 0x7A)) == 0x0) {
                return;
            }
        }
        catch (Exception lllIIlIlllIIlIl) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(PlayerFinder.lIllll[PlayerFinder.llIlIl[7]]).append(this.getSyntax())));
        }
    }
    
    private static boolean llIIIlII(final int lllIIlIlIlIIllI, final int lllIIlIlIlIIlIl) {
        return lllIIlIlIlIIllI < lllIIlIlIlIIlIl;
    }
    
    private static boolean llIIIIll(final int lllIIlIlIlIIIll) {
        return lllIIlIlIlIIIll != 0;
    }
    
    @Override
    public String getDescription() {
        return PlayerFinder.lIllll[PlayerFinder.llIlIl[8]];
    }
    
    private static String lIlllIlI(String lllIIlIlIlllllI, final String lllIIlIlIllllIl) {
        lllIIlIlIlllllI = (long)new String(Base64.getDecoder().decode(((String)lllIIlIlIlllllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllIIlIllIIIIIl = new StringBuilder();
        final char[] lllIIlIllIIIIII = lllIIlIlIllllIl.toCharArray();
        int lllIIlIlIllllll = PlayerFinder.llIlIl[0];
        final Exception lllIIlIlIlllIIl = (Object)((String)lllIIlIlIlllllI).toCharArray();
        final int lllIIlIlIlllIII = lllIIlIlIlllIIl.length;
        short lllIIlIlIllIlll = (short)PlayerFinder.llIlIl[0];
        while (llIIIlII(lllIIlIlIllIlll, lllIIlIlIlllIII)) {
            final char lllIIlIllIIIlII = lllIIlIlIlllIIl[lllIIlIlIllIlll];
            lllIIlIllIIIIIl.append((char)(lllIIlIllIIIlII ^ lllIIlIllIIIIII[lllIIlIlIllllll % lllIIlIllIIIIII.length]));
            "".length();
            ++lllIIlIlIllllll;
            ++lllIIlIlIllIlll;
            "".length();
            if (((0x57 ^ 0x77) & ~(0xD ^ 0x2D)) != 0x0) {
                return null;
            }
        }
        return String.valueOf(lllIIlIllIIIIIl);
    }
    
    @Override
    public String getSyntax() {
        return PlayerFinder.lIllll[PlayerFinder.llIlIl[9]];
    }
    
    private static void lIlllllI() {
        (lIllll = new String[PlayerFinder.llIlIl[10]])[PlayerFinder.llIlIl[0]] = lIlllIII("CLCigEWuArM=", "fZicw");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[1]] = lIlllIII("if4ql8nc2iE=", "QWckj");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[2]] = lIlllIIl("MqMwi45u0Nc=", "HdPEe");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[3]] = lIlllIIl("5Q8Qo0/ZKGEH5QLZwbDFDw==", "LRWbW");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[4]] = lIlllIIl("spP16eQhGuQ=", "GUAGM");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[5]] = lIlllIlI("IQ0mB3oEF3UWNx0QLF0=", "mdUsZ");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[6]] = lIlllIII("wHJtxaSFYqJAEhKakx/Rc/kMaVoXA35JTnvRQhVSYAI=", "sanHj");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[7]] = lIlllIII("pe72dBW6zio=", "wAJar");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[8]] = lIlllIIl("mbxo8lLwSM3lzWnRhm5OeJr6WTXPLKNp", "LLLTQ");
        PlayerFinder.lIllll[PlayerFinder.llIlIl[9]] = lIlllIIl("ctX4M3h1BnGQ+xGA2eNcUFXxh5bp21Wi", "Kcsxo");
    }
    
    private static String lIlllIII(final String lllIIlIlIlIlllI, final String lllIIlIlIlIllIl) {
        try {
            final SecretKeySpec lllIIlIlIllIIIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIIlIlIlIllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lllIIlIlIllIIII = Cipher.getInstance("Blowfish");
            lllIIlIlIllIIII.init(PlayerFinder.llIlIl[2], lllIIlIlIllIIIl);
            return new String(lllIIlIlIllIIII.doFinal(Base64.getDecoder().decode(lllIIlIlIlIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIlIlIllll) {
            lllIIlIlIlIllll.printStackTrace();
            return null;
        }
    }
    
    private static String lIlllIIl(final String lllIIlIllIlIIIl, final String lllIIlIllIlIIlI) {
        try {
            final SecretKeySpec lllIIlIllIlIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lllIIlIllIlIIlI.getBytes(StandardCharsets.UTF_8)), PlayerFinder.llIlIl[8]), "DES");
            final Cipher lllIIlIllIlIlIl = Cipher.getInstance("DES");
            lllIIlIllIlIlIl.init(PlayerFinder.llIlIl[2], lllIIlIllIlIllI);
            return new String(lllIIlIllIlIlIl.doFinal(Base64.getDecoder().decode(lllIIlIllIlIIIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIIlIllIlIlII) {
            lllIIlIllIlIlII.printStackTrace();
            return null;
        }
    }
    
    private static void llIIIIlI() {
        (llIlIl = new int[11])[0] = ((0x2A ^ 0x31) & ~(0xF ^ 0x14));
        PlayerFinder.llIlIl[1] = " ".length();
        PlayerFinder.llIlIl[2] = "  ".length();
        PlayerFinder.llIlIl[3] = "   ".length();
        PlayerFinder.llIlIl[4] = (71 + 36 - 47 + 85 ^ 86 + 73 - 58 + 48);
        PlayerFinder.llIlIl[5] = (0x6F ^ 0x74 ^ (0x64 ^ 0x7A));
        PlayerFinder.llIlIl[6] = (0x3E ^ 0x38);
        PlayerFinder.llIlIl[7] = (0xB4 ^ 0xB3);
        PlayerFinder.llIlIl[8] = (0x94 ^ 0x9C);
        PlayerFinder.llIlIl[9] = (0x6C ^ 0x60 ^ (0x42 ^ 0x47));
        PlayerFinder.llIlIl[10] = (0x4C ^ 0x46);
    }
}
