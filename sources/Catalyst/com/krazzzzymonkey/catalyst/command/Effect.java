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
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;

public class Effect extends Command
{
    private static final /* synthetic */ String[] lllIIIII;
    private static final /* synthetic */ int[] lllIIIll;
    
    void addEffect(final int lIllIlIIlIIllII, final int lIllIlIIlIIlIII, final int lIllIlIIlIIlIlI) {
        Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.getPotionById(lIllIlIIlIIllII), lIllIlIIlIIlIII, lIllIlIIlIIlIlI));
    }
    
    void clearEffects() {
        final char lIllIlIIIlllllI = (char)Wrapper.INSTANCE.player().getActivePotionEffects().iterator();
        while (lIlIIlllll(((Iterator)lIllIlIIIlllllI).hasNext() ? 1 : 0)) {
            final PotionEffect lIllIlIIlIIIIII = ((Iterator<PotionEffect>)lIllIlIIIlllllI).next();
            Wrapper.INSTANCE.player().removePotionEffect(lIllIlIIlIIIIII.getPotion());
            "".length();
            if ((0x95 ^ 0x91) < 0) {
                return;
            }
        }
    }
    
    void removeEffect(final int lIllIlIIlIIIIll) {
        Wrapper.INSTANCE.player().removePotionEffect(Potion.getPotionById(lIllIlIIlIIIIll));
    }
    
    private static String lIlIIlIlIl(String lIllIlIIIIllllI, final String lIllIlIIIIlllIl) {
        lIllIlIIIIllllI = (short)new String(Base64.getDecoder().decode(((String)lIllIlIIIIllllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllIlIIIlIIIIl = new StringBuilder();
        final char[] lIllIlIIIlIIIII = lIllIlIIIIlllIl.toCharArray();
        int lIllIlIIIIlllll = Effect.lllIIIll[0];
        final byte lIllIlIIIIllIIl = (Object)((String)lIllIlIIIIllllI).toCharArray();
        final Exception lIllIlIIIIllIII = (Exception)lIllIlIIIIllIIl.length;
        boolean lIllIlIIIIlIlll = Effect.lllIIIll[0] != 0;
        while (lIlIlIIIIl(lIllIlIIIIlIlll ? 1 : 0, (int)lIllIlIIIIllIII)) {
            final char lIllIlIIIlIIlII = lIllIlIIIIllIIl[lIllIlIIIIlIlll];
            lIllIlIIIlIIIIl.append((char)(lIllIlIIIlIIlII ^ lIllIlIIIlIIIII[lIllIlIIIIlllll % lIllIlIIIlIIIII.length]));
            "".length();
            ++lIllIlIIIIlllll;
            ++lIllIlIIIIlIlll;
            "".length();
            if (((0x8F ^ 0x9B) & ~(0x6E ^ 0x7A)) != 0x0) {
                return null;
            }
        }
        return String.valueOf(lIllIlIIIlIIIIl);
    }
    
    private static boolean lIlIlIIIIl(final int lIllIlIIIIIIllI, final int lIllIlIIIIIIlIl) {
        return lIllIlIIIIIIllI < lIllIlIIIIIIlIl;
    }
    
    private static void lIlIIllIll() {
        (lllIIIII = new String[Effect.lllIIIll[9]])[Effect.lllIIIll[0]] = lIlIIlIIlI("aTEG6XySgrY=", "lyuvR");
        Effect.lllIIIII[Effect.lllIIIll[1]] = lIlIIlIIll("CN+F3iCW8BQ=", "ESLgx");
        Effect.lllIIIII[Effect.lllIIIll[2]] = lIlIIlIIlI("fRkkncyOH6tW9rTUbnLOSw==", "NHacT");
        Effect.lllIIIII[Effect.lllIIIll[3]] = lIlIIlIlIl("JQorGTAy", "WoFvF");
        Effect.lllIIIII[Effect.lllIIIll[4]] = lIlIIlIlIl("PggEIxgARxk5VwASHCY=", "ngpJw");
        Effect.lllIIIII[Effect.lllIIIll[5]] = lIlIIlIIlI("9RIfNthIcS4=", "ydsbJ");
        Effect.lllIIIII[Effect.lllIIIll[6]] = lIlIIlIIlI("RzHGSwk0l3E=", "gJXUc");
        Effect.lllIIIII[Effect.lllIIIll[7]] = lIlIIlIIlI("D/DkOewxQ++ULFqZOwJBrw==", "GcvDb");
        Effect.lllIIIII[Effect.lllIIIll[8]] = lIlIIlIIll("EVomPwxstmWqJRg5+PNAjmHVFo/t+Xfejw9clIPGkZpH4vLOit9+WSwyO0GiwITkOAOv4ypguU4=", "dzTZG");
    }
    
    static {
        lIlIIllllI();
        lIlIIllIll();
    }
    
    @Override
    public void runCommand(final String lIllIlIIlIlIlll, final String[] lIllIlIIlIlIllI) {
        try {
            if (lIlIIlllll(lIllIlIIlIlIllI[Effect.lllIIIll[0]].equalsIgnoreCase(Effect.lllIIIII[Effect.lllIIIll[1]]) ? 1 : 0)) {
                final int lIllIlIIlIlllIl = Integer.parseInt(lIllIlIIlIlIllI[Effect.lllIIIll[1]]);
                final int lIllIlIIlIlllII = Integer.parseInt(lIllIlIIlIlIllI[Effect.lllIIIll[2]]);
                final int lIllIlIIlIllIll = Integer.parseInt(lIllIlIIlIlIllI[Effect.lllIIIll[3]]);
                if (lIlIlIIIII(Potion.getPotionById(lIllIlIIlIlllIl))) {
                    ChatUtils.error(Effect.lllIIIII[Effect.lllIIIll[2]]);
                    return;
                }
                this.addEffect(lIllIlIIlIlllIl, lIllIlIIlIlllII, lIllIlIIlIllIll);
                "".length();
                if (-" ".length() >= (102 + 88 - 113 + 52 ^ 84 + 55 - 72 + 66)) {
                    return;
                }
            }
            else if (lIlIIlllll(lIllIlIIlIlIllI[Effect.lllIIIll[0]].equalsIgnoreCase(Effect.lllIIIII[Effect.lllIIIll[3]]) ? 1 : 0)) {
                final int lIllIlIIlIllIlI = Integer.parseInt(lIllIlIIlIlIllI[Effect.lllIIIll[1]]);
                if (lIlIlIIIII(Potion.getPotionById(lIllIlIIlIllIlI))) {
                    ChatUtils.error(Effect.lllIIIII[Effect.lllIIIll[4]]);
                    return;
                }
                this.removeEffect(lIllIlIIlIllIlI);
                "".length();
                if ((0x90 ^ 0x94) < 0) {
                    return;
                }
            }
            else if (lIlIIlllll(lIllIlIIlIlIllI[Effect.lllIIIll[0]].equalsIgnoreCase(Effect.lllIIIII[Effect.lllIIIll[5]]) ? 1 : 0)) {
                this.clearEffects();
            }
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (Exception lIllIlIIlIllIIl) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Effect.lllIIIII[Effect.lllIIIll[6]]).append(this.getSyntax())));
        }
    }
    
    private static void lIlIIllllI() {
        (lllIIIll = new int[10])[0] = ((0x8E ^ 0xA4 ^ (0xBE ^ 0xB9)) & (0x22 ^ 0x14 ^ (0x5D ^ 0x46) ^ -" ".length()));
        Effect.lllIIIll[1] = " ".length();
        Effect.lllIIIll[2] = "  ".length();
        Effect.lllIIIll[3] = "   ".length();
        Effect.lllIIIll[4] = (0x8 ^ 0xC);
        Effect.lllIIIll[5] = (0x74 ^ 0x71);
        Effect.lllIIIll[6] = (0xA0 ^ 0xA6);
        Effect.lllIIIll[7] = (0x46 ^ 0x41);
        Effect.lllIIIll[8] = (0xA1 ^ 0xA8 ^ " ".length());
        Effect.lllIIIll[9] = (0x27 ^ 0x2E);
    }
    
    private static String lIlIIlIIlI(final String lIllIlIIIllIIll, final String lIllIlIIIllIIII) {
        try {
            final SecretKeySpec lIllIlIIIllIllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIlIIIllIIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIllIlIIIllIlIl = Cipher.getInstance("Blowfish");
            lIllIlIIIllIlIl.init(Effect.lllIIIll[2], lIllIlIIIllIllI);
            return new String(lIllIlIIIllIlIl.doFinal(Base64.getDecoder().decode(lIllIlIIIllIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIlIIIllIlII) {
            lIllIlIIIllIlII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIlIIlllll(final int lIllIlIIIIIIIIl) {
        return lIllIlIIIIIIIIl != 0;
    }
    
    @Override
    public String getSyntax() {
        return Effect.lllIIIII[Effect.lllIIIll[8]];
    }
    
    private static boolean lIlIlIIIII(final Object lIllIlIIIIIIIll) {
        return lIllIlIIIIIIIll == null;
    }
    
    public Effect() {
        super(Effect.lllIIIII[Effect.lllIIIll[0]]);
    }
    
    @Override
    public String getDescription() {
        return Effect.lllIIIII[Effect.lllIIIll[7]];
    }
    
    private static String lIlIIlIIll(final String lIllIlIIIIIlllI, final String lIllIlIIIIIlIll) {
        try {
            final SecretKeySpec lIllIlIIIIlIIIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIIIIIlIll.getBytes(StandardCharsets.UTF_8)), Effect.lllIIIll[8]), "DES");
            final Cipher lIllIlIIIIlIIII = Cipher.getInstance("DES");
            lIllIlIIIIlIIII.init(Effect.lllIIIll[2], lIllIlIIIIlIIIl);
            return new String(lIllIlIIIIlIIII.doFinal(Base64.getDecoder().decode(lIllIlIIIIIlllI.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIlIIIIIllll) {
            lIllIlIIIIIllll.printStackTrace();
            return null;
        }
    }
}
