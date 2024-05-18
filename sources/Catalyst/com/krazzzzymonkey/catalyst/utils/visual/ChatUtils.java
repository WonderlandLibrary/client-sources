// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.visual;

import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ChatUtils
{
    private static final /* synthetic */ String[] lIllIIIl;
    private static final /* synthetic */ int[] lIlllIII;
    
    public static void message(final String llIIIIIllIllIIl) {
        component((ITextComponent)new TextComponentTranslation(String.valueOf(new StringBuilder().append(ChatUtils.lIllIIIl[ChatUtils.lIlllIII[1]]).append(llIIIIIllIllIIl)), new Object[ChatUtils.lIlllIII[0]]));
    }
    
    private static boolean lIIIlIlIII(final Object llIIIIIllIIIIlI) {
        return llIIIIIllIIIIlI == null;
    }
    
    public static void warning(final String llIIIIIllIlIllI) {
        message(String.valueOf(new StringBuilder().append(ChatUtils.lIllIIIl[ChatUtils.lIlllIII[2]]).append(llIIIIIllIlIllI)));
    }
    
    private static void lIIIlIIllI() {
        (lIlllIII = new int[5])[0] = ((0x72 ^ 0x32 ^ (0x8 ^ 0x5D)) & (0x78 ^ 0x3D ^ (0xE5 ^ 0xB5) ^ -" ".length()));
        ChatUtils.lIlllIII[1] = " ".length();
        ChatUtils.lIlllIII[2] = "  ".length();
        ChatUtils.lIlllIII[3] = "   ".length();
        ChatUtils.lIlllIII[4] = (0x3F ^ 0x3B);
    }
    
    public static void error(final String llIIIIIllIlIIll) {
        message(String.valueOf(new StringBuilder().append(ChatUtils.lIllIIIl[ChatUtils.lIlllIII[3]]).append(llIIIIIllIlIIll)));
    }
    
    private static void lIIIlIIlII() {
        (lIllIIIl = new String[ChatUtils.lIlllIII[4]])[ChatUtils.lIlllIII[0]] = lIIIIlllII("jELcr5V5JIs=", "OgMUj");
        ChatUtils.lIllIIIl[ChatUtils.lIlllIII[1]] = lIIIIlllII("0GYKkXOj4cEaFtGP4CHNRg==", "DTgZy");
        ChatUtils.lIllIIIl[ChatUtils.lIlllIII[2]] = lIIIIlllII("Vu+gwHnrIbR8JEy2tA1mEFz/AQGYoTWt", "vGwzX");
        ChatUtils.lIllIIIl[ChatUtils.lIlllIII[3]] = lIIIIlllII("vfIkTnHwDVOm5QuOUAy2GxnxlhSNTbK+", "eTJNL");
    }
    
    private static String lIIIIlllII(final String llIIIIIllIIlIll, final String llIIIIIllIIlIII) {
        try {
            final SecretKeySpec llIIIIIllIIlllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIIIIllIIlIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIIIIllIIllIl = Cipher.getInstance("Blowfish");
            llIIIIIllIIllIl.init(ChatUtils.lIlllIII[2], llIIIIIllIIlllI);
            return new String(llIIIIIllIIllIl.doFinal(Base64.getDecoder().decode(llIIIIIllIIlIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIIIIllIIllII) {
            llIIIIIllIIllII.printStackTrace();
            return null;
        }
    }
    
    private static boolean lIIIlIIlll(final Object llIIIIIllIIIlII) {
        return llIIIIIllIIIlII != null;
    }
    
    static {
        lIIIlIIllI();
        lIIIlIIlII();
    }
    
    public static void component(final ITextComponent llIIIIIllIlllII) {
        if (!lIIIlIIlll(Wrapper.INSTANCE.player()) || lIIIlIlIII(Wrapper.INSTANCE.mc().ingameGUI.getChatGUI())) {
            return;
        }
        Wrapper.INSTANCE.mc().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation(ChatUtils.lIllIIIl[ChatUtils.lIlllIII[0]], new Object[ChatUtils.lIlllIII[0]]).appendSibling(llIIIIIllIlllII));
    }
}
