// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import com.krazzzzymonkey.catalyst.managers.EnemyManager;
import com.krazzzzymonkey.catalyst.managers.FriendManager;
import net.minecraft.entity.EntityLiving;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.krazzzzymonkey.catalyst.module.modules.combat.AntiBot;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;

public class ValidUtils
{
    private static final /* synthetic */ int[] lIIIIlll;
    private static final /* synthetic */ String[] lIIIIIIl;
    
    public static boolean isBot(final EntityLivingBase llIIllIlllIlllI) {
        if (llIllIIIl((llIIllIlllIlllI instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llIIllIllllIIIl = (EntityPlayer)llIIllIlllIlllI;
            final Modules llIIllIllllIIII = HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[5]]);
            int n;
            if (llIllIIIl(llIIllIllllIIII.isToggled() ? 1 : 0) && llIllIIIl(AntiBot.isBot(llIIllIllllIIIl) ? 1 : 0)) {
                n = ValidUtils.lIIIIlll[1];
                "".length();
                if (-(0x46 ^ 0xB ^ (0x3B ^ 0x72)) >= 0) {
                    return ((41 + 40 + 138 + 8 ^ 73 + 130 - 51 + 14) & (176 + 15 - 35 + 49 ^ 104 + 59 - 121 + 94 ^ -" ".length())) != 0x0;
                }
            }
            else {
                n = ValidUtils.lIIIIlll[0];
            }
            return n != 0;
        }
        return ValidUtils.lIIIIlll[0] != 0;
    }
    
    private static boolean llIllIlll(final int llIIllIlIIlllll, final int llIIllIlIIllllI) {
        return llIIllIlIIlllll < llIIllIlIIllllI;
    }
    
    private static String llIIlllII(String llIIllIlIlIllll, final String llIIllIlIlIlllI) {
        llIIllIlIlIllll = new String(Base64.getDecoder().decode(llIIllIlIlIllll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIIllIlIlIllIl = new StringBuilder();
        final char[] llIIllIlIlIllII = llIIllIlIlIlllI.toCharArray();
        int llIIllIlIlIlIll = ValidUtils.lIIIIlll[0];
        final double llIIllIlIlIIlIl = (Object)llIIllIlIlIllll.toCharArray();
        final long llIIllIlIlIIlII = llIIllIlIlIIlIl.length;
        float llIIllIlIlIIIll = ValidUtils.lIIIIlll[0];
        while (llIllIlll((int)llIIllIlIlIIIll, (int)llIIllIlIlIIlII)) {
            final char llIIllIlIllIIII = llIIllIlIlIIlIl[llIIllIlIlIIIll];
            llIIllIlIlIllIl.append((char)(llIIllIlIllIIII ^ llIIllIlIlIllII[llIIllIlIlIlIll % llIIllIlIlIllII.length]));
            "".length();
            ++llIIllIlIlIlIll;
            ++llIIllIlIlIIIll;
            "".length();
            if ("  ".length() != "  ".length()) {
                return null;
            }
        }
        return String.valueOf(llIIllIlIlIllIl);
    }
    
    private static String llIIllllI(final String llIIllIlIllllll, final String llIIllIlIlllllI) {
        try {
            final SecretKeySpec llIIllIllIIIIlI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIllIlIlllllI.getBytes(StandardCharsets.UTF_8)), ValidUtils.lIIIIlll[8]), "DES");
            final Cipher llIIllIllIIIIIl = Cipher.getInstance("DES");
            llIIllIllIIIIIl.init(ValidUtils.lIIIIlll[2], llIIllIllIIIIlI);
            return new String(llIIllIllIIIIIl.doFinal(Base64.getDecoder().decode(llIIllIlIllllll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIllIIIIII) {
            llIIllIllIIIIII.printStackTrace();
            return null;
        }
    }
    
    static {
        llIllIIII();
        llIlIlIlI();
    }
    
    private static boolean llIllIIIl(final int llIIllIlIIlIllI) {
        return llIIllIlIIlIllI != 0;
    }
    
    private static void llIlIlIlI() {
        (lIIIIIIl = new String[ValidUtils.lIIIIlll[14]])[ValidUtils.lIIIIlll[0]] = llIIlllII("GzAQJgA7Ig==", "OQbAe");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[1]] = llIIllllI("g1x5AHW4pb4=", "SjLGQ");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[2]] = llIlIIIIl("D03ShXnQNr0=", "oyJmL");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[3]] = llIlIIIIl("sU5714P2EuY=", "DzLUo");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[4]] = llIlIIIIl("TlBnWWdBryo03NqfFbUy6w==", "CWYiQ");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[5]] = llIlIIIIl("xAl/pUFa4R8=", "CcIVN");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[6]] = llIlIIIIl("xDu2OpV2tU4=", "HYcwu");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[7]] = llIlIIIIl("UGTV3mwHrJw=", "Tveoq");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[8]] = llIIllllI("r1CbNlqu2Fw=", "PumpK");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[9]] = llIIlllII("DgImFSEMHycVIQ==", "OpKzS");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[10]] = llIIlllII("HigCFAE/JQAD", "PIoqB");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[11]] = llIlIIIIl("mAxzx12BAms=", "CLCiu");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[12]] = llIIlllII("GTsXOhk5Nw02GQ==", "PUaSj");
        ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[13]] = llIlIIIIl("TMrN5FnQVwg4LgRVTOSMlg==", "reuxu");
    }
    
    public static boolean isInvisible(final EntityLivingBase llIIllIllIlIlIl) {
        final Modules llIIllIllIlIllI = HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[11]]);
        if (llIllIllI(llIIllIllIlIllI.isToggledValue(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[12]]) ? 1 : 0) && llIllIIIl(llIIllIllIlIlIl.isInvisible() ? 1 : 0)) {
            return ValidUtils.lIIIIlll[0] != 0;
        }
        return ValidUtils.lIIIIlll[1] != 0;
    }
    
    public static boolean pingCheck(final EntityLivingBase llIIllIllllIllI) {
        final Modules llIIllIllllIlll = HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[3]]);
        if (!llIllIIIl(llIIllIllllIlll.isToggled() ? 1 : 0) || !llIllIIIl(llIIllIllllIlll.isToggledValue(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[4]]) ? 1 : 0) || !llIllIIIl((llIIllIllllIllI instanceof EntityPlayer) ? 1 : 0)) {
            return ValidUtils.lIIIIlll[1] != 0;
        }
        if (llIllIIlI(Wrapper.INSTANCE.mc().getConnection().getPlayerInfo(llIIllIllllIllI.getUniqueID())) && llIllIlII(Wrapper.INSTANCE.mc().getConnection().getPlayerInfo(llIIllIllllIllI.getUniqueID()).getResponseTime(), ValidUtils.lIIIIlll[5])) {
            return ValidUtils.lIIIIlll[1] != 0;
        }
        return ValidUtils.lIIIIlll[0] != 0;
    }
    
    private static boolean llIllIlII(final int llIIllIlIIllIll, final int llIIllIlIIllIlI) {
        return llIIllIlIIllIll > llIIllIlIIllIlI;
    }
    
    private static void llIllIIII() {
        (lIIIIlll = new int[15])[0] = ((0x9A ^ 0x85 ^ "  ".length()) & (0x2 ^ 0x44 ^ (0xF9 ^ 0xA2) ^ -" ".length()));
        ValidUtils.lIIIIlll[1] = " ".length();
        ValidUtils.lIIIIlll[2] = "  ".length();
        ValidUtils.lIIIIlll[3] = "   ".length();
        ValidUtils.lIIIIlll[4] = (0x4 ^ 0x0);
        ValidUtils.lIIIIlll[5] = (0x74 ^ 0x2C ^ (0x13 ^ 0x4E));
        ValidUtils.lIIIIlll[6] = (0x72 ^ 0x1F ^ (0x1A ^ 0x71));
        ValidUtils.lIIIIlll[7] = (0xF9 ^ 0x87 ^ (0x13 ^ 0x6A));
        ValidUtils.lIIIIlll[8] = (0x72 ^ 0x7A);
        ValidUtils.lIIIIlll[9] = (0x3 ^ 0xA);
        ValidUtils.lIIIIlll[10] = (120 + 58 - 92 + 67 ^ 28 + 140 - 153 + 132);
        ValidUtils.lIIIIlll[11] = (0xB5 ^ 0xBE);
        ValidUtils.lIIIIlll[12] = (0x15 ^ 0x19);
        ValidUtils.lIIIIlll[13] = (0x4 ^ 0x1A ^ (0x2A ^ 0x39));
        ValidUtils.lIIIIlll[14] = (0x30 ^ 0x3E);
    }
    
    public static boolean isValidEntity(final EntityLivingBase llIIllIllllllII) {
        final Modules llIIllIllllllIl = HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[0]]);
        if (llIllIIIl(llIIllIllllllIl.isToggled() ? 1 : 0)) {
            if (llIllIIIl(llIIllIllllllIl.isToggledValue(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[1]]) ? 1 : 0) && llIllIIIl((llIIllIllllllII instanceof EntityPlayer) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
            if (llIllIIIl(llIIllIllllllIl.isToggledValue(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[2]]) ? 1 : 0) && llIllIIIl((llIIllIllllllII instanceof EntityLiving) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
        }
        return ValidUtils.lIIIIlll[1] != 0;
    }
    
    public static boolean isFriendEnemy(final EntityLivingBase llIIllIlllIIlIl) {
        if (llIllIIIl((llIIllIlllIIlIl instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llIIllIlllIlIII = (EntityPlayer)llIIllIlllIIlIl;
            final String llIIllIlllIIlll = Utils.getPlayerName(llIIllIlllIlIII);
            if (llIllIIIl(FriendManager.friendsList.contains(llIIllIlllIIlll) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
            if (llIllIIIl(HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[6]]).isToggled() ? 1 : 0) && llIllIllI(EnemyManager.enemysList.contains(llIIllIlllIIlll) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
        }
        return ValidUtils.lIIIIlll[1] != 0;
    }
    
    private static String llIlIIIIl(final String llIIllIllIIllII, final String llIIllIllIIlIIl) {
        try {
            final SecretKeySpec llIIllIllIIllll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIllIllIIlIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIllIllIIlllI = Cipher.getInstance("Blowfish");
            llIIllIllIIlllI.init(ValidUtils.lIIIIlll[2], llIIllIllIIllll);
            return new String(llIIllIllIIlllI.doFinal(Base64.getDecoder().decode(llIIllIllIIllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIllIllIIllIl) {
            llIIllIllIIllIl.printStackTrace();
            return null;
        }
    }
    
    public static boolean isTeam(final EntityLivingBase llIIllIllIlllII) {
        final Modules llIIllIllIlllIl = HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[7]]);
        if (llIllIIIl(llIIllIllIlllIl.isToggled() ? 1 : 0) && llIllIIIl((llIIllIllIlllII instanceof EntityPlayer) ? 1 : 0)) {
            final EntityPlayer llIIllIllIlllll = (EntityPlayer)llIIllIllIlllII;
            if (llIllIIIl(llIIllIllIlllIl.isToggledMode(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[8]]) ? 1 : 0) && llIllIIlI(llIIllIllIlllll.getTeam()) && llIllIIlI(Wrapper.INSTANCE.player().getTeam()) && llIllIIIl(llIIllIllIlllll.getTeam().isSameTeam(Wrapper.INSTANCE.player().getTeam()) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
            if (llIllIIIl(llIIllIllIlllIl.isToggledMode(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[9]]) ? 1 : 0) && llIllIllI(Utils.checkEnemyColor(llIIllIllIlllll) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
            if (llIllIIIl(llIIllIllIlllIl.isToggledMode(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[10]]) ? 1 : 0) && llIllIllI(Utils.checkEnemyNameColor((EntityLivingBase)llIIllIllIlllll) ? 1 : 0)) {
                return ValidUtils.lIIIIlll[0] != 0;
            }
        }
        return ValidUtils.lIIIIlll[1] != 0;
    }
    
    private static boolean llIllIIlI(final Object llIIllIlIIllIII) {
        return llIIllIlIIllIII != null;
    }
    
    public static boolean isNoScreen() {
        if (llIllIIIl(HackManager.getHack(ValidUtils.lIIIIIIl[ValidUtils.lIIIIlll[13]]).isToggled() ? 1 : 0) && llIllIllI(Utils.checkScreen() ? 1 : 0)) {
            return ValidUtils.lIIIIlll[0] != 0;
        }
        return ValidUtils.lIIIIlll[1] != 0;
    }
    
    private static boolean llIllIllI(final int llIIllIlIIlIlII) {
        return llIIllIlIIlIlII == 0;
    }
}
