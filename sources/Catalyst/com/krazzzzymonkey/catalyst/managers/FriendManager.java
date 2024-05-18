// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import java.util.ArrayList;

public class FriendManager
{
    public static /* synthetic */ ArrayList<String> friendsList;
    private static final /* synthetic */ int[] lllIlIl;
    private static final /* synthetic */ String[] llIIIII;
    
    public static void removeFriend(final String llIlIIlIlIlllIl) {
        if (llIIIIIIl(FriendManager.friendsList.contains(llIlIIlIlIlllIl) ? 1 : 0)) {
            FriendManager.friendsList.remove(llIlIIlIlIlllIl);
            "".length();
            FileManager.saveFriends();
            ChatUtils.message(String.valueOf(new StringBuilder().append(llIlIIlIlIlllIl).append(FriendManager.llIIIII[FriendManager.lllIlIl[1]])));
        }
    }
    
    private static boolean llIIIIIlI(final int llIlIIlIIllIlIl, final int llIlIIlIIllIlII) {
        return llIlIIlIIllIlIl < llIlIIlIIllIlII;
    }
    
    private static String lIIllIIII(String llIlIIlIlIlIIlI, final String llIlIIlIlIIllII) {
        llIlIIlIlIlIIlI = new String(Base64.getDecoder().decode(llIlIIlIlIlIIlI.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIlIIlIlIlIIII = new StringBuilder();
        final char[] llIlIIlIlIIllll = llIlIIlIlIIllII.toCharArray();
        int llIlIIlIlIIlllI = FriendManager.lllIlIl[0];
        final boolean llIlIIlIlIIlIII = (Object)llIlIIlIlIlIIlI.toCharArray();
        final char llIlIIlIlIIIlll = (char)llIlIIlIlIIlIII.length;
        float llIlIIlIlIIIllI = FriendManager.lllIlIl[0];
        while (llIIIIIlI((int)llIlIIlIlIIIllI, llIlIIlIlIIIlll)) {
            final char llIlIIlIlIlIIll = llIlIIlIlIIlIII[llIlIIlIlIIIllI];
            llIlIIlIlIlIIII.append((char)(llIlIIlIlIlIIll ^ llIlIIlIlIIllll[llIlIIlIlIIlllI % llIlIIlIlIIllll.length]));
            "".length();
            ++llIlIIlIlIIlllI;
            ++llIlIIlIlIIIllI;
            "".length();
            if (" ".length() <= 0) {
                return null;
            }
        }
        return String.valueOf(llIlIIlIlIlIIII);
    }
    
    public static void addFriend(final String llIlIIlIllIIIIl) {
        if (llIIIIIII(FriendManager.friendsList.contains(llIlIIlIllIIIIl) ? 1 : 0)) {
            FriendManager.friendsList.add(llIlIIlIllIIIIl);
            "".length();
            FileManager.saveFriends();
            ChatUtils.message(String.valueOf(new StringBuilder().append(llIlIIlIllIIIIl).append(FriendManager.llIIIII[FriendManager.lllIlIl[0]])));
        }
    }
    
    public static void clear() {
        if (llIIIIIII(FriendManager.friendsList.isEmpty() ? 1 : 0)) {
            FriendManager.friendsList.clear();
            FileManager.saveFriends();
            ChatUtils.message(FriendManager.llIIIII[FriendManager.lllIlIl[2]]);
        }
    }
    
    private static String lIIlIllIl(final String llIlIIlIIllllIl, final String llIlIIlIIllllII) {
        try {
            final SecretKeySpec llIlIIlIlIIIIII = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIlIIlIIllllII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIlIIlIIllllll = Cipher.getInstance("Blowfish");
            llIlIIlIIllllll.init(FriendManager.lllIlIl[2], llIlIIlIlIIIIII);
            return new String(llIlIIlIIllllll.doFinal(Base64.getDecoder().decode(llIlIIlIIllllIl.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIlIIlIIlllllI) {
            llIlIIlIIlllllI.printStackTrace();
            return null;
        }
    }
    
    private static boolean llIIIIIIl(final int llIlIIlIIllIIlI) {
        return llIlIIlIIllIIlI != 0;
    }
    
    private static void lIIllIIIl() {
        (llIIIII = new String[FriendManager.lllIlIl[3]])[FriendManager.lllIlIl[0]] = lIIlIllIl("JFQSbQJ4MQknhxG6Gyt6DsiH/B38NXcLddKIWuUvxsg=", "Qtzcw");
        FriendManager.llIIIII[FriendManager.lllIlIl[1]] = lIIlIllIl("fF9Y0n9pZ6E6dQqPFXKJk2AboxWyEOJmxCWcaLP+4A+hePiT+H7irQ==", "qZGUi");
        FriendManager.llIIIII[FriendManager.lllIlIl[2]] = lIIllIIII("w7AWKyUaMhoJJFPDsEMBPgAjVA47FjYGQw==", "WtmWs");
    }
    
    private static void lIlllllll() {
        (lllIlIl = new int[4])[0] = ((0xFD ^ 0xBC) & ~(0xFD ^ 0xBC));
        FriendManager.lllIlIl[1] = " ".length();
        FriendManager.lllIlIl[2] = "  ".length();
        FriendManager.lllIlIl[3] = "   ".length();
    }
    
    static {
        lIlllllll();
        lIIllIIIl();
        FriendManager.friendsList = new ArrayList<String>();
    }
    
    private static boolean llIIIIIII(final int llIlIIlIIllIIII) {
        return llIlIIlIIllIIII == 0;
    }
}
