// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import java.util.ArrayList;

public class EnemyManager
{
    public static /* synthetic */ ArrayList<String> detects;
    public static /* synthetic */ ArrayList<String> murders;
    private static final /* synthetic */ String[] lIIllll;
    private static final /* synthetic */ int[] lIlIIll;
    public static /* synthetic */ ArrayList<String> enemysList;
    
    private static boolean lIIIIlIll(final int llIlllIIllIlIlI, final int llIlllIIllIlIIl) {
        return llIlllIIllIlIlI < llIlllIIllIlIIl;
    }
    
    private static boolean lIIIIlIIl(final int llIlllIIllIIlIl) {
        return llIlllIIllIIlIl == 0;
    }
    
    private static boolean lIIIIlIlI(final int llIlllIIllIIlll) {
        return llIlllIIllIIlll != 0;
    }
    
    private static void lIIIIlIII() {
        (lIlIIll = new int[5])[0] = ((0x50 ^ 0x70 ^ (0x77 ^ 0x7)) & (0x8E ^ 0xA2 ^ (0xCF ^ 0xB3) ^ -" ".length()));
        EnemyManager.lIlIIll[1] = " ".length();
        EnemyManager.lIlIIll[2] = "  ".length();
        EnemyManager.lIlIIll[3] = "   ".length();
        EnemyManager.lIlIIll[4] = (119 + 19 - 37 + 44 ^ 103 + 26 - 46 + 70);
    }
    
    public static void removeEnemy(final String llIlllIlIIlIIlI) {
        if (lIIIIlIlI(EnemyManager.enemysList.contains(llIlllIlIIlIIlI) ? 1 : 0)) {
            EnemyManager.enemysList.remove(llIlllIlIIlIIlI);
            "".length();
            FileManager.saveEnemys();
            ChatUtils.message(String.valueOf(new StringBuilder().append(llIlllIlIIlIIlI).append(EnemyManager.lIIllll[EnemyManager.lIlIIll[1]])));
        }
    }
    
    static {
        lIIIIlIII();
        lIIIIIIlI();
        EnemyManager.enemysList = new ArrayList<String>();
        EnemyManager.murders = new ArrayList<String>();
        EnemyManager.detects = new ArrayList<String>();
    }
    
    public static void clear() {
        if (lIIIIlIIl(EnemyManager.enemysList.isEmpty() ? 1 : 0)) {
            EnemyManager.enemysList.clear();
            FileManager.saveEnemys();
            ChatUtils.message(EnemyManager.lIIllll[EnemyManager.lIlIIll[2]]);
        }
    }
    
    public static void addEnemy(final String llIlllIlIIlIlIl) {
        if (lIIIIlIIl(EnemyManager.enemysList.contains(llIlllIlIIlIlIl) ? 1 : 0)) {
            EnemyManager.enemysList.add(llIlllIlIIlIlIl);
            "".length();
            FileManager.saveEnemys();
            ChatUtils.message(String.valueOf(new StringBuilder().append(llIlllIlIIlIlIl).append(EnemyManager.lIIllll[EnemyManager.lIlIIll[0]])));
        }
    }
    
    private static void lIIIIIIlI() {
        (lIIllll = new String[EnemyManager.lIlIIll[3]])[EnemyManager.lIlIIll[0]] = lllllllI("RwMNAzMDYh0IdsOAIQwJMwo7GkfDsVAuABQiSQ==", "gBigV");
        EnemyManager.lIIllll[EnemyManager.lIlIIll[1]] = lllllllI("cSAhCiAnFyBHKSMdKUfDqDIXKgIiKAFkw4B4PRs3E2E=", "QrDgO");
        EnemyManager.lIIllll[EnemyManager.lIlIIll[2]] = lIIIIIIIl("ZkbMSt39xM8XMS3gkVL42cXWqaSxra2yre3dIydr/GM=", "iviSY");
    }
    
    private static String lIIIIIIIl(final String llIlllIIlllIIII, final String llIlllIIlllIIIl) {
        try {
            final SecretKeySpec llIlllIIlllIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIlllIIlllIIIl.getBytes(StandardCharsets.UTF_8)), EnemyManager.lIlIIll[4]), "DES");
            final Cipher llIlllIIlllIlII = Cipher.getInstance("DES");
            llIlllIIlllIlII.init(EnemyManager.lIlIIll[2], llIlllIIlllIlIl);
            return new String(llIlllIIlllIlII.doFinal(Base64.getDecoder().decode(llIlllIIlllIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlllIIlllIIll) {
            llIlllIIlllIIll.printStackTrace();
            return null;
        }
    }
    
    private static String lllllllI(String llIlllIlIIIIIlI, final String llIlllIlIIIIllI) {
        llIlllIlIIIIIlI = (short)new String(Base64.getDecoder().decode(((String)llIlllIlIIIIIlI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlllIlIIIIlIl = new StringBuilder();
        final char[] llIlllIlIIIIlII = llIlllIlIIIIllI.toCharArray();
        int llIlllIlIIIIIll = EnemyManager.lIlIIll[0];
        final boolean llIlllIIlllllIl = (Object)((String)llIlllIlIIIIIlI).toCharArray();
        final boolean llIlllIIlllllII = llIlllIIlllllIl.length != 0;
        short llIlllIIllllIll = (short)EnemyManager.lIlIIll[0];
        while (lIIIIlIll(llIlllIIllllIll, llIlllIIlllllII ? 1 : 0)) {
            final char llIlllIlIIIlIII = llIlllIIlllllIl[llIlllIIllllIll];
            llIlllIlIIIIlIl.append((char)(llIlllIlIIIlIII ^ llIlllIlIIIIlII[llIlllIlIIIIIll % llIlllIlIIIIlII.length]));
            "".length();
            ++llIlllIlIIIIIll;
            ++llIlllIIllllIll;
            "".length();
            if (" ".length() < ((0x2D ^ 0x15) & ~(0xB4 ^ 0x8C))) {
                return null;
            }
        }
        return String.valueOf(llIlllIlIIIIlIl);
    }
}
