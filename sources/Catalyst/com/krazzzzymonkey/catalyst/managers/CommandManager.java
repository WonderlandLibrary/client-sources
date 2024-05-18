// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.utils.visual.ChatUtils;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.command.Command;
import java.util.ArrayList;

public class CommandManager
{
    private static volatile /* synthetic */ CommandManager instance;
    private static final /* synthetic */ String[] lIIlII;
    private static final /* synthetic */ int[] lIIlIl;
    public static /* synthetic */ char cmdPrefix;
    public static /* synthetic */ ArrayList<Command> commands;
    
    private static boolean lIIllIlI(final int lllIlIIIIlIIIll) {
        return lllIlIIIIlIIIll == 0;
    }
    
    private static boolean lIIlllII(final int lllIlIIIIlIlIlI, final int lllIlIIIIlIlIIl) {
        return lllIlIIIIlIlIlI < lllIlIIIIlIlIIl;
    }
    
    public static CommandManager getInstance() {
        if (lIIllIll(CommandManager.instance)) {
            CommandManager.instance = new CommandManager();
        }
        return CommandManager.instance;
    }
    
    private static void lIIllIII() {
        (lIIlIl = new int[6])[0] = ((33 + 97 - 127 + 137 ^ 59 + 134 - 127 + 100) & (166 + 144 - 166 + 88 ^ 35 + 71 + 88 + 0 ^ -" ".length()));
        CommandManager.lIIlIl[1] = " ".length();
        CommandManager.lIIlIl[2] = "  ".length();
        CommandManager.lIIlIl[3] = "   ".length();
        CommandManager.lIIlIl[4] = (0x1A ^ 0x2F ^ (0xA9 ^ 0xB2));
        CommandManager.lIIlIl[5] = (155 + 69 - 192 + 165 ^ 19 + 134 + 4 + 36);
    }
    
    static {
        lIIllIII();
        lIIlIlll();
        CommandManager.commands = new ArrayList<Command>();
        CommandManager.cmdPrefix = (char)CommandManager.lIIlIl[4];
    }
    
    public CommandManager() {
        this.addCommands();
    }
    
    private static boolean lIIllIll(final Object lllIlIIIIlIIlll) {
        return lllIlIIIIlIIlll == null;
    }
    
    private static String lIIlIllI(final String lllIlIIIlIIlIII, final String lllIlIIIlIIlIIl) {
        try {
            final SecretKeySpec lllIlIIIlIIllIl = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lllIlIIIlIIlIIl.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lllIlIIIlIIllII = Cipher.getInstance("Blowfish");
            lllIlIIIlIIllII.init(CommandManager.lIIlIl[2], lllIlIIIlIIllIl);
            return new String(lllIlIIIlIIllII.doFinal(Base64.getDecoder().decode(lllIlIIIlIIlIII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lllIlIIIlIIlIll) {
            lllIlIIIlIIlIll.printStackTrace();
            return null;
        }
    }
    
    private static String lIIlIlIl(String lllIlIIIIllIlIl, final String lllIlIIIIllIlII) {
        lllIlIIIIllIlIl = (Exception)new String(Base64.getDecoder().decode(((String)lllIlIIIIllIlIl).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder lllIlIIIIlllIII = new StringBuilder();
        final char[] lllIlIIIIllIlll = lllIlIIIIllIlII.toCharArray();
        int lllIlIIIIllIllI = CommandManager.lIIlIl[0];
        final char lllIlIIIIllIIII = (Object)((String)lllIlIIIIllIlIl).toCharArray();
        final char lllIlIIIIlIllll = (char)lllIlIIIIllIIII.length;
        char lllIlIIIIlIlllI = (char)CommandManager.lIIlIl[0];
        while (lIIlllII(lllIlIIIIlIlllI, lllIlIIIIlIllll)) {
            final char lllIlIIIIlllIll = lllIlIIIIllIIII[lllIlIIIIlIlllI];
            lllIlIIIIlllIII.append((char)(lllIlIIIIlllIll ^ lllIlIIIIllIlll[lllIlIIIIllIllI % lllIlIIIIllIlll.length]));
            "".length();
            ++lllIlIIIIllIllI;
            ++lllIlIIIIlIlllI;
            "".length();
            if (-" ".length() >= ((0xC6 ^ 0x96 ^ (0x20 ^ 0x2F)) & (27 + 165 - 123 + 183 ^ 122 + 112 - 91 + 20 ^ -" ".length()))) {
                return null;
            }
        }
        return String.valueOf(lllIlIIIIlllIII);
    }
    
    private static void lIIlIlll() {
        (lIIlII = new String[CommandManager.lIIlIl[5]])[CommandManager.lIIlIl[0]] = lIIlIlIl("SA==", "hlYmN");
        CommandManager.lIIlII[CommandManager.lIIlIl[1]] = lIIlIllI("qmOc9r/7NwE=", "DVNLo");
        CommandManager.lIIlII[CommandManager.lIIlIl[2]] = lIIlIlIl("Tg==", "nrNMO");
        CommandManager.lIIlII[CommandManager.lIIlIl[3]] = lIIlIllI("jkwtt/FbYR/bTtSRpNLrCo8lOejQQazs6L9Ijp5OVx7slMMTm9r3LQ==", "KXTXn");
    }
    
    public void addCommands() {
    }
    
    private static boolean lIIllIIl(final int lllIlIIIIlIIlIl) {
        return lllIlIIIIlIIlIl != 0;
    }
    
    public void runCommands(final String lllIlIIIlIlllll) {
        final String lllIlIIIlIllllI = lllIlIIIlIlllll.trim().substring(Character.toString(CommandManager.cmdPrefix).length()).trim();
        boolean lllIlIIIlIlllIl = CommandManager.lIIlIl[0] != 0;
        final boolean lllIlIIIlIlllII = lllIlIIIlIllllI.trim().contains(CommandManager.lIIlII[CommandManager.lIIlIl[0]]);
        String trim;
        if (lIIllIIl(lllIlIIIlIlllII ? 1 : 0)) {
            trim = lllIlIIIlIllllI.split(CommandManager.lIIlII[CommandManager.lIIlIl[1]])[CommandManager.lIIlIl[0]];
            "".length();
            if (-" ".length() >= " ".length()) {
                return;
            }
        }
        else {
            trim = lllIlIIIlIllllI.trim();
        }
        final String lllIlIIIlIllIll = trim;
        String[] split;
        if (lIIllIIl(lllIlIIIlIlllII ? 1 : 0)) {
            split = lllIlIIIlIllllI.substring(lllIlIIIlIllIll.length()).trim().split(CommandManager.lIIlII[CommandManager.lIIlIl[2]]);
            "".length();
            if (null != null) {
                return;
            }
        }
        else {
            split = new String[CommandManager.lIIlIl[0]];
        }
        final String[] lllIlIIIlIllIlI = split;
        final char lllIlIIIlIlIIll = (char)CommandManager.commands.iterator();
        while (lIIllIIl(((Iterator)lllIlIIIlIlIIll).hasNext() ? 1 : 0)) {
            final Command lllIlIIIllIIIIl = ((Iterator<Command>)lllIlIIIlIlIIll).next();
            if (lIIllIIl(lllIlIIIllIIIIl.getCommand().trim().equalsIgnoreCase(lllIlIIIlIllIll.trim()) ? 1 : 0)) {
                lllIlIIIllIIIIl.runCommand(lllIlIIIlIllllI, lllIlIIIlIllIlI);
                lllIlIIIlIlllIl = (CommandManager.lIIlIl[1] != 0);
                "".length();
                if (-"  ".length() >= 0) {
                    return;
                }
                break;
            }
            else {
                "".length();
                if (null != null) {
                    return;
                }
                continue;
            }
        }
        if (lIIllIlI(lllIlIIIlIlllIl ? 1 : 0)) {
            ChatUtils.error(String.valueOf(new StringBuilder().append(CommandManager.lIIlII[CommandManager.lIIlIl[3]]).append(lllIlIIIlIllIll)));
        }
    }
}
