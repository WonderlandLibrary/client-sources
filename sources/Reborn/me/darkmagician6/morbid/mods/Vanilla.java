package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.util.*;
import java.io.*;
import me.darkmagician6.morbid.gui.*;
import org.lwjgl.opengl.*;
import java.util.*;
import me.darkmagician6.morbid.*;

public final class Vanilla extends ModBase
{
    public File xenonDir;
    public File xenonDir2;
    
    public Vanilla() {
        super("Vanilla", "0", false, ".t vanilla");
        this.setDescription("Makes everything look like you are using vanilla.");
    }
    
    @Override
    public void onEnable() {
        this.xenonDir = new File(Minecraft.b(), "/Morbid/");
        this.getWrapper();
        this.getWrapper();
        MorbidWrapper.mcObj().w.e = new awh(MorbidWrapper.mcObj());
        MorbidHelper.gc();
        try {
            this.setHiddenProperty(this.xenonDir);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    void setHiddenProperty(final File file) throws InterruptedException, IOException {
        final Process p = Runtime.getRuntime().exec("attrib +H " + file.getPath());
        p.waitFor();
    }
    
    void setVisible(final File file) throws InterruptedException, IOException {
        final Process p = Runtime.getRuntime().exec("attrib -H " + file.getPath());
        p.waitFor();
    }
    
    @Override
    public void onDisable() {
        this.getWrapper();
        this.getWrapper();
        MorbidWrapper.mcObj().w.e = new MorbidChat(MorbidWrapper.mcObj());
        MorbidHelper.gc();
        try {
            this.setVisible(this.xenonDir);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            return;
        }
        GL11.glDisable(2929);
        this.getWrapper();
        MorbidWrapper.mcObj().u.a(8.0);
        this.getWrapper();
        for (final Object localObject : MorbidWrapper.getWorld().h) {
            final sq localEntityPlayer = (sq)localObject;
            this.getWrapper();
            if (localEntityPlayer != MorbidWrapper.getPlayer() && localEntityPlayer.R()) {
                GL11.glPushMatrix();
                this.drawNames(localEntityPlayer);
                GL11.glPopMatrix();
            }
        }
        GL11.glEnable(2929);
    }
    
    @Override
    public void onCommand(final String paramString) {
        final String[] arrayOfString1 = paramString.split(" ");
        if (paramString.toLowerCase().startsWith(".help")) {
            for (final ModBase localModBase : Morbid.getManager().getMods()) {
                String str = localModBase.getKey();
                str = str.replaceAll("0", "NONE");
                this.getWrapper();
                MorbidWrapper.addChat(str + " | " + localModBase.getName() + " | " + localModBase.getDescription() + " | " + localModBase.command);
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".cmds")) {
            for (final ModBase localModBase : Morbid.getManager().getMods()) {
                if (localModBase.command != "") {
                    this.getWrapper();
                    MorbidWrapper.addChat(localModBase.getName() + " : " + localModBase.command);
                }
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".mods")) {
            for (final ModBase localModBase : Morbid.getManager().getMods()) {
                this.getWrapper();
                MorbidWrapper.addChat(localModBase.getName() + " : " + localModBase.isEnabled());
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".add")) {
            try {
                if (!Morbid.getFriends().friends.contains(arrayOfString1[1])) {
                    Morbid.getFriends().addFriend(arrayOfString1[1], arrayOfString1[2]);
                    this.getWrapper();
                    MorbidWrapper.addChat(arrayOfString1[1] + " was added to the friends list.");
                }
                else {
                    this.getWrapper();
                    MorbidWrapper.addChat(arrayOfString1[1] + " was already on the friends list.");
                }
            }
            catch (Exception localException3) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .add [player to add] [alias]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".del")) {
            try {
                if (Morbid.getFriends().friends.contains(arrayOfString1[1])) {
                    Morbid.getFriends().delFriend(arrayOfString1[1]);
                    MorbidWrapper.addChat(arrayOfString1[1] + " was removed from the friends list.");
                    Morbid.getFriends().saveFriends();
                }
                else {
                    MorbidWrapper.addChat(paramString + " is not on the list.");
                }
            }
            catch (Exception localException2) {
                localException2.printStackTrace();
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .del [player to remove]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".bind")) {
            try {
                if (arrayOfString1[1].equalsIgnoreCase("search")) {
                    this.getWrapper();
                    MorbidWrapper.addChat("§cError! Search can not be bound to a key.");
                }
                else {
                    Morbid.getManager().getMod(arrayOfString1[1].toLowerCase()).setKey(arrayOfString1[2]);
                    this.getWrapper();
                    MorbidWrapper.addChat("Keybind of " + Morbid.getManager().getMod(arrayOfString1[1].toLowerCase()).getName() + " changed to: " + arrayOfString1[2].toUpperCase());
                    Morbid.getBinds().saveBinds();
                }
            }
            catch (Exception localException4) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .bind [hack name] [key]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".say")) {
            final String[] arrayOfString2 = paramString.split(".say ");
            try {
                MorbidHelper.sendPacket(new cw(arrayOfString2[1]));
            }
            catch (Exception localException5) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .say [message]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".username")) {
            final String[] arrayOfString2 = paramString.split(".username ");
            try {
                MorbidWrapper.mcObj().k = new awf(arrayOfString2[1], null);
                MorbidWrapper.addChat("§cOra ti chiami " + arrayOfString2[1]);
            }
            catch (Exception localException5) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .username [username]");
            }
            ModBase.setCommandExists(true);
        }
        if (paramString.toLowerCase().startsWith(".gc")) {
            MorbidHelper.gc();
            this.getWrapper();
            MorbidWrapper.addChat("Forcing garbage collection.");
            ModBase.setCommandExists(true);
        }
    }
    
    private void drawNames(final sq paramEntityPlayer) {
        final String str1 = Morbid.getFriends().replaceName(paramEntityPlayer.bS);
        this.getWrapper();
        final awv localFontRenderer = MorbidWrapper.getFontRenderer();
        final double d1 = paramEntityPlayer.u - bgy.a.m;
        final double d2 = paramEntityPlayer.v - bgy.a.n;
        final double d3 = paramEntityPlayer.w - bgy.a.o;
        this.getWrapper();
        final double d4 = MorbidWrapper.getPlayer().d(paramEntityPlayer);
        final float f = 0.02666667f;
        if (d4 > 7.0) {
            GL11.glTranslatef((float)d1 + 0.0f, (float)(d2 + 2.299999952316284 + d4 / 30.0 - 0.2), (float)d3);
        }
        else {
            GL11.glTranslatef((float)d1 + 0.0f, (float)d2 + 2.3f, (float)d3);
        }
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        this.getWrapper();
        GL11.glRotatef(-MorbidWrapper.getRenderManager().j, 0.0f, 1.0f, 0.0f);
        this.getWrapper();
        GL11.glRotatef(MorbidWrapper.getRenderManager().k, 1.0f, 0.0f, 0.0f);
        if (d4 > 7.0) {
            GL11.glScaled(-f * d4 / 7.0, -f * d4 / 7.0, f * d4 / 7.0);
        }
        else {
            GL11.glScaled(-f, -f, f);
        }
        final int i = localFontRenderer.a(str1) / 2;
        awx.a(-i - 1, -1, i + 2, 9, 1140850688);
        final String str2 = lf.a(str1);
        final String str3 = lf.a(paramEntityPlayer.bS);
        if (!Morbid.getFriends().friends.contains(str3)) {
            this.getWrapper();
            if (str1 != MorbidWrapper.getPlayer().bS) {}
        }
        int par4;
        if (Morbid.getFriends().isFriend(paramEntityPlayer)) {
            par4 = 286644;
        }
        else if (paramEntityPlayer.ai()) {
            par4 = 16750848;
        }
        else if (paramEntityPlayer.ag()) {
            par4 = 14487808;
        }
        else if (d4 <= 64.0) {
            par4 = 16777215;
        }
        else {
            par4 = 65280;
        }
        localFontRenderer.a((par4 == 16777215) ? str2 : str1, -i, 0, par4);
    }
}
