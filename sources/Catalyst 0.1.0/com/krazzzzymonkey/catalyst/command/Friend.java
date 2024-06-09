// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

import java.util.Iterator;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import com.krazzzzymonkey.catalyst.managers.FriendManager;
import com.krazzzzymonkey.catalyst.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Friend extends Command
{
    private static final /* synthetic */ int[] llIIIIlI;
    private static final /* synthetic */ String[] lIlllllI;
    
    private static String lIIIlllllI(String lIlllllIIlllllI, final String lIlllllIIllllIl) {
        lIlllllIIlllllI = (Exception)new String(Base64.getDecoder().decode(((String)lIlllllIIlllllI).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lIlllllIlIIIIIl = new StringBuilder();
        final char[] lIlllllIlIIIIII = lIlllllIIllllIl.toCharArray();
        int lIlllllIIllllll = Friend.llIIIIlI[0];
        final float lIlllllIIlllIIl = (Object)((String)lIlllllIIlllllI).toCharArray();
        final short lIlllllIIlllIII = (short)lIlllllIIlllIIl.length;
        float lIlllllIIllIlll = Friend.llIIIIlI[0];
        while (lIIlIIlIlI((int)lIlllllIIllIlll, lIlllllIIlllIII)) {
            final char lIlllllIlIIIlII = lIlllllIIlllIIl[lIlllllIIllIlll];
            lIlllllIlIIIIIl.append((char)(lIlllllIlIIIlII ^ lIlllllIlIIIIII[lIlllllIIllllll % lIlllllIlIIIIII.length]));
            "".length();
            ++lIlllllIIllllll;
            ++lIlllllIIllIlll;
            "".length();
            if ((0x46 ^ 0x49 ^ (0xA2 ^ 0xA9)) < 0) {
                return null;
            }
        }
        return String.valueOf(lIlllllIlIIIIIl);
    }
    
    @Override
    public String getDescription() {
        return Friend.lIlllllI[Friend.llIIIIlI[6]];
    }
    
    private static boolean lIIlIIlIlI(final int lIlllllIIllIIll, final int lIlllllIIllIIlI) {
        return lIlllllIIllIIll < lIlllllIIllIIlI;
    }
    
    private static boolean lIIlIIlIIl(final int lIlllllIIlIlllI) {
        return lIlllllIIlIlllI == 0;
    }
    
    private static void lIIIllllll() {
        (lIlllllI = new String[Friend.llIIIIlI[8]])[Friend.llIIIIlI[0]] = lIIIlllIII("pPKuLLqoYLo=", "medmk");
        Friend.lIlllllI[Friend.llIIIIlI[1]] = lIIIlllIII("fIcaYmRMLf8=", "tvjze");
        Friend.lIlllllI[Friend.llIIIIlI[2]] = lIIIlllIll("dhWT6MbjujA=", "cOpbX");
        Friend.lIlllllI[Friend.llIIIIlI[3]] = lIIIlllllI("AwYCNQUU", "qcoZs");
        Friend.lIlllllI[Friend.llIIIIlI[4]] = lIIIlllIll("PF9HzkAtitU=", "qzRvE");
        Friend.lIlllllI[Friend.llIIIIlI[5]] = lIIIlllIll("T5SsKWQZD2Y=", "qKQxp");
        Friend.lIlllllI[Friend.llIIIIlI[6]] = lIIIlllllI("CDYDPzkqZAc7OS8jDyh5", "NDjZW");
        Friend.lIlllllI[Friend.llIIIIlI[7]] = lIIIlllIll("DKgywuB6JmeHOvK9TINddkvGl/NM6khRuj+JU2R/+LUcAeeP9OMAIw==", "nBilK");
    }
    
    @Override
    public void runCommand(final String lIlllllIlllIIII, final String[] lIlllllIllIllIl) {
        try {
            if (lIIlIIlIII(lIlllllIllIllIl[Friend.llIIIIlI[0]].equalsIgnoreCase(Friend.lIlllllI[Friend.llIIIIlI[1]]) ? 1 : 0)) {
                if (lIIlIIlIII(lIlllllIllIllIl[Friend.llIIIIlI[1]].equalsIgnoreCase(Friend.lIlllllI[Friend.llIIIIlI[2]]) ? 1 : 0)) {
                    final boolean lIlllllIllIllII = (boolean)Wrapper.INSTANCE.world().loadedEntityList.iterator();
                    while (lIIlIIlIII(((Iterator)lIlllllIllIllII).hasNext() ? 1 : 0)) {
                        final Object lIlllllIlllIIll = ((Iterator<Object>)lIlllllIllIllII).next();
                        if (lIIlIIlIII((lIlllllIlllIIll instanceof EntityPlayer) ? 1 : 0)) {
                            final EntityPlayer lIlllllIlllIlII = (EntityPlayer)lIlllllIlllIIll;
                            if (lIIlIIlIIl(lIlllllIlllIlII.isInvisible() ? 1 : 0)) {
                                FriendManager.addFriend(Utils.getPlayerName(lIlllllIlllIlII));
                            }
                        }
                        "".length();
                        if (" ".length() < ("  ".length() & ~"  ".length())) {
                            return;
                        }
                    }
                    "".length();
                    if (-(85 + 91 - 111 + 113 ^ 14 + 159 + 1 + 8) >= 0) {
                        return;
                    }
                }
                else {
                    FriendManager.addFriend(lIlllllIllIllIl[Friend.llIIIIlI[1]]);
                    "".length();
                    if ((0x33 ^ 0x4 ^ (0xBE ^ 0x8D)) > (0x1F ^ 0x70 ^ (0x61 ^ 0xA))) {
                        return;
                    }
                }
            }
            else if (lIIlIIlIII(lIlllllIllIllIl[Friend.llIIIIlI[0]].equalsIgnoreCase(Friend.lIlllllI[Friend.llIIIIlI[3]]) ? 1 : 0)) {
                FriendManager.removeFriend(lIlllllIllIllIl[Friend.llIIIIlI[1]]);
                "".length();
                if (-(0x6C ^ 0x68) > 0) {
                    return;
                }
            }
            else if (lIIlIIlIII(lIlllllIllIllIl[Friend.llIIIIlI[0]].equalsIgnoreCase(Friend.lIlllllI[Friend.llIIIIlI[4]]) ? 1 : 0)) {
                FriendManager.clear();
            }
            "".length();
            if (" ".length() > "  ".length()) {
                return;
            }
        }
        catch (Exception lIlllllIlllIIlI) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(Friend.lIlllllI[Friend.llIIIIlI[5]]).append(this.getSyntax())));
        }
    }
    
    public Friend() {
        super(Friend.lIlllllI[Friend.llIIIIlI[0]]);
    }
    
    @Override
    public String getSyntax() {
        return Friend.lIlllllI[Friend.llIIIIlI[7]];
    }
    
    static {
        lIIlIIIlll();
        lIIIllllll();
    }
    
    private static void lIIlIIIlll() {
        (llIIIIlI = new int[9])[0] = ((86 + 54 - 36 + 47 ^ 62 + 73 - 111 + 117) & (146 + 49 - 116 + 69 ^ 31 + 13 - 29 + 127 ^ -" ".length()));
        Friend.llIIIIlI[1] = " ".length();
        Friend.llIIIIlI[2] = "  ".length();
        Friend.llIIIIlI[3] = "   ".length();
        Friend.llIIIIlI[4] = (0x63 ^ 0x67);
        Friend.llIIIIlI[5] = (0x9D ^ 0x98);
        Friend.llIIIIlI[6] = (0xA3 ^ 0x9C ^ (0x8C ^ 0xB5));
        Friend.llIIIIlI[7] = (0xAB ^ 0xAC);
        Friend.llIIIIlI[8] = (0xF ^ 0x4E ^ (0x1E ^ 0x57));
    }
    
    private static String lIIIlllIll(final String lIlllllIlIlIIll, final String lIlllllIlIlIIII) {
        try {
            final SecretKeySpec lIlllllIlIlIllI = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(lIlllllIlIlIIII.getBytes(StandardCharsets.UTF_8)), Friend.llIIIIlI[8]), "DES");
            final Cipher lIlllllIlIlIlIl = Cipher.getInstance("DES");
            lIlllllIlIlIlIl.init(Friend.llIIIIlI[2], lIlllllIlIlIllI);
            return new String(lIlllllIlIlIlIl.doFinal(Base64.getDecoder().decode(lIlllllIlIlIIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIlIlIlII) {
            lIlllllIlIlIlII.printStackTrace();
            return null;
        }
    }
    
    private static String lIIIlllIII(final String lIlllllIllIIIII, final String lIlllllIlIlllIl) {
        try {
            final SecretKeySpec lIlllllIllIIIll = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllllIlIlllIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIlllllIllIIIlI = Cipher.getInstance("Blowfish");
            lIlllllIllIIIlI.init(Friend.llIIIIlI[2], lIlllllIllIIIll);
            return new String(lIlllllIllIIIlI.doFinal(Base64.getDecoder().decode(lIlllllIllIIIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllllIllIIIIl) {
            lIlllllIllIIIIl.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIlIIlIII(final int lIlllllIIllIIII) {
        return lIlllllIIllIIII != 0;
    }
}
