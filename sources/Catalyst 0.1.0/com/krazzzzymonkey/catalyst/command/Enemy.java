// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.managers.EnemyManager;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Arrays;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Enemy extends Command
{
    private static final /* synthetic */ String[] llIlIlII;
    private static final /* synthetic */ int[] llIlllII;
    
    static {
        lIlIIIIlII();
        lIlIIIIIII();
    }
    
    private static void lIlIIIIlII() {
        (llIlllII = new int[9])[0] = ((0x61 ^ 0x6F) & ~(0x70 ^ 0x7E));
        Enemy.llIlllII[1] = " ".length();
        Enemy.llIlllII[2] = "  ".length();
        Enemy.llIlllII[3] = "   ".length();
        Enemy.llIlllII[4] = (0x31 ^ 0x73 ^ (0x40 ^ 0x6));
        Enemy.llIlllII[5] = (0x3 ^ 0x6);
        Enemy.llIlllII[6] = (0x32 ^ 0x54 ^ (0xD2 ^ 0xB2));
        Enemy.llIlllII[7] = (0x10 ^ 0x47 ^ (0x6F ^ 0x3F));
        Enemy.llIlllII[8] = (163 + 162 - 160 + 36 ^ 92 + 19 + 51 + 31);
    }
    
    private static String lIIlllIlll(final String lIllIllIIlIIlIl, final String lIllIllIIlIIlII) {
        try {
            final SecretKeySpec lIllIllIIlIlIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIllIllIIlIIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIllIllIIlIlIIl = Cipher.getInstance("Blowfish");
            lIllIllIIlIlIIl.init(Enemy.llIlllII[2], lIllIllIIlIlIlI);
            return new String(lIllIllIIlIlIIl.doFinal(Base64.getDecoder().decode(lIllIllIIlIIlIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIllIIlIlIII) {
            lIllIllIIlIlIII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIlIIIIlll(final int lIllIlIlllllIlI, final int lIllIlIlllllIIl) {
        return lIllIlIlllllIlI < lIllIlIlllllIIl;
    }
    
    private static String lIIllllIII(String lIllIllIIIlIIlI, final String lIllIllIIIlIllI) {
        lIllIllIIIlIIlI = (double)new String(Base64.getDecoder().decode(((String)lIllIllIIIlIIlI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIllIllIIIlIlIl = new StringBuilder();
        final char[] lIllIllIIIlIlII = lIllIllIIIlIllI.toCharArray();
        int lIllIllIIIlIIll = Enemy.llIlllII[0];
        final float lIllIllIIIIllIl = (Object)((String)lIllIllIIIlIIlI).toCharArray();
        final byte lIllIllIIIIllII = (byte)lIllIllIIIIllIl.length;
        Exception lIllIllIIIIlIll = (Exception)Enemy.llIlllII[0];
        while (lIlIIIIlll((int)lIllIllIIIIlIll, lIllIllIIIIllII)) {
            final char lIllIllIIIllIII = lIllIllIIIIllIl[lIllIllIIIIlIll];
            lIllIllIIIlIlIl.append((char)(lIllIllIIIllIII ^ lIllIllIIIlIlII[lIllIllIIIlIIll % lIllIllIIIlIlII.length]));
            "".length();
            ++lIllIllIIIlIIll;
            ++lIllIllIIIIlIll;
            "".length();
            if ("   ".length() >= (48 + 60 - 13 + 42 ^ 1 + 100 + 9 + 31)) {
                return null;
            }
        }
        return String.valueOf(lIllIllIIIlIlIl);
    }
    
    private static String lIIlllllII(final String lIllIllIIIIIIII, final String lIllIlIllllllll) {
        try {
            final SecretKeySpec lIllIllIIIIIlIl = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIllIlIllllllll.getBytes(StandardCharsets.UTF_8)), Enemy.llIlllII[8]), "DES");
            final Cipher lIllIllIIIIIlII = Cipher.getInstance("DES");
            lIllIllIIIIIlII.init(Enemy.llIlllII[2], lIllIllIIIIIlIl);
            return new String(lIllIllIIIIIlII.doFinal(Base64.getDecoder().decode(lIllIllIIIIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIllIllIIIIIIll) {
            lIllIllIIIIIIll.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIlIIIIlIl(final int lIllIlIllllIlll) {
        return lIllIlIllllIlll != 0;
    }
    
    private static void lIlIIIIIII() {
        (llIlIlII = new String[Enemy.llIlllII[8]])[Enemy.llIlllII[0]] = lIIlllIlll("QDBhy+jikjA=", "OTykN");
        Enemy.llIlIlII[Enemy.llIlllII[1]] = lIIllllIII("KDcl", "ISAOk");
        Enemy.llIlIlII[Enemy.llIlllII[2]] = lIIlllIlll("WmQ8hKF1yrY=", "Zpayv");
        Enemy.llIlIlII[Enemy.llIlllII[3]] = lIIlllllII("uN4PDw0JweY=", "ohiPO");
        Enemy.llIlIlII[Enemy.llIlllII[4]] = lIIlllIlll("FETe8YjTz/I=", "JPWBx");
        Enemy.llIlIlII[Enemy.llIlllII[5]] = lIIlllIlll("EDlqCEowT+M=", "TIgFw");
        Enemy.llIlIlII[Enemy.llIlllII[6]] = lIIlllllII("x8rrZD0cabqIb0rgABNLog==", "dsjTv");
        Enemy.llIlIlII[Enemy.llIlllII[7]] = lIIlllIlll("tcLMmw7ZVPAwlW0Jx617PW8iIIlU9oOB0CrPIXVBk2s=", "OeNMQ");
    }
    
    @Override
    public void runCommand(final String lIllIllIIllIlll, final String[] lIllIllIIllIlII) {
        try {
            if (lIlIIIIlIl(lIllIllIIllIlII[Enemy.llIlllII[0]].equalsIgnoreCase(Enemy.llIlIlII[Enemy.llIlllII[1]]) ? 1 : 0)) {
                if (lIlIIIIlIl(lIllIllIIllIlII[Enemy.llIlllII[1]].equalsIgnoreCase(Enemy.llIlIlII[Enemy.llIlllII[2]]) ? 1 : 0)) {
                    final byte lIllIllIIllIIll = (byte)Wrapper.INSTANCE.world().loadedEntityList.iterator();
                    while (lIlIIIIlIl(((Iterator)lIllIllIIllIIll).hasNext() ? 1 : 0)) {
                        final Object lIllIllIIlllIlI = ((Iterator<Object>)lIllIllIIllIIll).next();
                        if (lIlIIIIlIl((lIllIllIIlllIlI instanceof EntityPlayer) ? 1 : 0)) {
                            final EntityPlayer lIllIllIIlllIll = (EntityPlayer)lIllIllIIlllIlI;
                            if (lIlIIIIllI(lIllIllIIlllIll.isInvisible() ? 1 : 0)) {
                                EnemyManager.addEnemy(Utils.getPlayerName(lIllIllIIlllIll));
                            }
                        }
                        "".length();
                        if (((0xC3 ^ 0x95) & ~(0xE7 ^ 0xB1)) != 0x0) {
                            return;
                        }
                    }
                    "".length();
                    if (null != null) {
                        return;
                    }
                }
                else {
                    EnemyManager.addEnemy(lIllIllIIllIlII[Enemy.llIlllII[1]]);
                    "".length();
                    if ((0x12 ^ 0x16) <= 0) {
                        return;
                    }
                }
            }
            else if (lIlIIIIlIl(lIllIllIIllIlII[Enemy.llIlllII[0]].equalsIgnoreCase(Enemy.llIlIlII[Enemy.llIlllII[3]]) ? 1 : 0)) {
                EnemyManager.removeEnemy(lIllIllIIllIlII[Enemy.llIlllII[1]]);
                "".length();
                if (((0x4C ^ 0x4) & ~(0x77 ^ 0x3F)) != 0x0) {
                    return;
                }
            }
            else if (lIlIIIIlIl(lIllIllIIllIlII[Enemy.llIlllII[0]].equalsIgnoreCase(Enemy.llIlIlII[Enemy.llIlllII[4]]) ? 1 : 0)) {
                EnemyManager.clear();
            }
            "".length();
            if ("  ".length() == " ".length()) {
                return;
            }
        }
        catch (Exception lIllIllIIlllIIl) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Enemy.llIlIlII[Enemy.llIlllII[5]]).append(this.getSyntax())));
        }
    }
    
    private static boolean lIlIIIIllI(final int lIllIlIllllIlIl) {
        return lIllIlIllllIlIl == 0;
    }
    
    @Override
    public String getDescription() {
        return Enemy.llIlIlII[Enemy.llIlllII[6]];
    }
    
    public Enemy() {
        super(Enemy.llIlIlII[Enemy.llIlllII[0]]);
    }
    
    @Override
    public String getSyntax() {
        return Enemy.llIlIlII[Enemy.llIlllII[7]];
    }
}
