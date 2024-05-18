package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.util.*;
import java.io.*;
import org.lwjgl.opengl.*;
import java.util.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import net.minecraft.src.*;

public final class Vanilla extends ModBase
{
    public File rebornDir;
    public File rebornDir2;
    private double vclip;
    
    public Vanilla() {
        super("Vanilla", "0", false, ".t vanilla");
        this.setDescription("Makes everything look like you are using vanilla.");
    }
    
    @Override
    public void onEnable() {
        this.rebornDir = new File(Minecraft.getMinecraftDir(), "/Reborn/");
        this.getWrapper();
        this.getWrapper();
        MorbidHelper.gc();
        try {
            this.setHiddenProperty(this.rebornDir);
        }
        catch (InterruptedException var2) {
            var2.printStackTrace();
        }
        catch (IOException var3) {
            var3.printStackTrace();
        }
    }
    
    void setHiddenProperty(final File var1) throws InterruptedException, IOException {
        final Process var2 = Runtime.getRuntime().exec("attrib +H " + var1.getPath());
        var2.waitFor();
    }
    
    void setVisible(final File var1) throws InterruptedException, IOException {
        final Process var2 = Runtime.getRuntime().exec("attrib -H " + var1.getPath());
        var2.waitFor();
    }
    
    @Override
    public void onDisable() {
        this.getWrapper();
        this.getWrapper();
        MorbidHelper.gc();
        try {
            this.setVisible(this.rebornDir);
        }
        catch (InterruptedException var2) {
            var2.printStackTrace();
        }
        catch (IOException var3) {
            var3.printStackTrace();
        }
    }
    
    @Override
    public void onRenderHand() {
        if (!this.isEnabled()) {
            GL11.glDisable(2929);
            this.getWrapper();
            MorbidWrapper.mcObj().entityRenderer.disableLightmap(8.0);
            this.getWrapper();
            for (final Object var2 : MorbidWrapper.getWorld().playerEntities) {
                final EntityPlayer var3 = (EntityPlayer)var2;
                this.getWrapper();
                if (var3 != MorbidWrapper.getPlayer() && var3.isEntityAlive()) {
                    GL11.glPushMatrix();
                    this.drawNames(var3);
                    GL11.glPopMatrix();
                }
            }
            GL11.glEnable(2929);
        }
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".help")) {
            Morbid.getManager();
            final Iterator var3 = ModManager.getMods().iterator();
            MorbidWrapper.addChat("Format: Keybind - Mod Name - Description - Command");
            while (var3.hasNext()) {
                final ModBase var4 = var3.next();
                String var5 = var4.getKey();
                var5 = var5.replaceAll("0", "NONE");
                this.getWrapper();
                MorbidWrapper.addChat(String.valueOf(var5) + " - " + var4.getName() + " - " + var4.getDescription() + " - " + var4.command);
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".cmds")) {
            Morbid.getManager();
            for (final ModBase var4 : ModManager.getMods()) {
                if (var4.command != "") {
                    this.getWrapper();
                    MorbidWrapper.addChat(String.valueOf(var4.getName()) + " : " + var4.command);
                }
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".mods")) {
            Morbid.getManager();
            for (final ModBase var4 : ModManager.getMods()) {
                this.getWrapper();
                MorbidWrapper.addChat(String.valueOf(var4.getName()) + " : " + var4.isEnabled());
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".add")) {
            try {
                if (!Morbid.getFriends().friends.contains(var2[1])) {
                    Morbid.getFriends().addFriend(var2[1], var2[2]);
                    this.getWrapper();
                    MorbidWrapper.addChat(String.valueOf(var2[1]) + " was added to the friends list.");
                }
                else {
                    this.getWrapper();
                    MorbidWrapper.addChat(String.valueOf(var2[1]) + " was already on the friends list.");
                }
            }
            catch (Exception var8) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .add [player to add] [alias]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".del")) {
            try {
                if (Morbid.getFriends().friends.contains(var2[1])) {
                    Morbid.getFriends().delFriend(var2[1]);
                    MorbidWrapper.addChat(String.valueOf(var2[1]) + " was removed from the friends list.");
                    Morbid.getFriends().saveFriends();
                }
                else {
                    MorbidWrapper.addChat(String.valueOf(var1) + " is not on the list.");
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .del [player to remove]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".bind")) {
            try {
                if (var2[1].equalsIgnoreCase("search")) {
                    this.getWrapper();
                    MorbidWrapper.addChat("§cError! Search can not be bound to a key.");
                }
                else {
                    Morbid.getManager();
                    ModManager.getMod(var2[1].toLowerCase()).setKey(var2[2]);
                    this.getWrapper();
                    final StringBuilder sb = new StringBuilder("Keybind of ");
                    Morbid.getManager();
                    MorbidWrapper.addChat(sb.append(ModManager.getMod(var2[1].toLowerCase()).getName()).append(" changed to: ").append(var2[2].toUpperCase()).toString());
                    Morbid.getBinds().saveBinds();
                }
            }
            catch (Exception var9) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .bind [hack name] [key]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".say")) {
            final String[] var7 = var1.split(".say ");
            try {
                MorbidHelper.sendPacket(new Packet3Chat(var7[1]));
            }
            catch (Exception var10) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .say [message]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".username")) {
            final String[] var7 = var1.split(".username ");
            try {
                MorbidWrapper.mcObj().session = new Session(var7[1], null);
                MorbidWrapper.addChat("§cOra ti chiami " + var7[1]);
            }
            catch (Exception var11) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .username [username]");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".vclip+")) {
            try {
                final double vclip1 = Double.parseDouble(var2[1]);
                this.vclip = vclip1;
                this.getWrapper();
                if (var1.contains("-")) {
                    MorbidWrapper.addChat("§fCorrect usage: .vclip- <blocks>");
                }
                else {
                    MorbidWrapper.getPlayer().setPosition(MorbidWrapper.getPlayer().posX, MorbidWrapper.getPlayer().posY + vclip1, MorbidWrapper.getPlayer().posZ);
                    MorbidWrapper.addChat("§fTeleported up: " + vclip1 + " blocks");
                }
            }
            catch (Exception var11) {
                this.getWrapper();
                MorbidWrapper.addChat("§fCorrect usage: .vclip+ <blocks>");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".vclip-")) {
            try {
                final double vclip1 = Double.parseDouble(var2[1]);
                this.vclip = vclip1;
                this.getWrapper();
                if (var1.contains("+")) {
                    MorbidWrapper.addChat("§fCorrect usage: .vclip+ <blocks>");
                }
                else {
                    MorbidWrapper.getPlayer().setPosition(MorbidWrapper.getPlayer().posX, MorbidWrapper.getPlayer().posY - vclip1, MorbidWrapper.getPlayer().posZ);
                    MorbidWrapper.addChat("§fTeleported down: " + vclip1 + " blocks");
                }
            }
            catch (Exception var11) {
                this.getWrapper();
                MorbidWrapper.addChat("§fCorrect usage: .vclip- <blocks>");
            }
            ModBase.setCommandExists(true);
        }
        if (var1.toLowerCase().startsWith(".gc")) {
            MorbidHelper.gc();
            this.getWrapper();
            MorbidWrapper.addChat("Forcing garbage collection.");
            ModBase.setCommandExists(true);
        }
    }
    
    private void drawNames(final EntityPlayer var1) {
        final String var2 = Morbid.getFriends().replaceName(String.valueOf(var1.username) + " §0[§e" + (int)MorbidWrapper.getPlayer().getDistanceToEntity(var1) + "§0]");
        this.getWrapper();
        final FontRenderer var3 = MorbidWrapper.getFontRenderer();
        final double var4 = var1.posX - RenderManager.instance.viewerPosX;
        final double var5 = var1.posY - RenderManager.instance.viewerPosY;
        final double var6 = var1.posZ - RenderManager.instance.viewerPosZ;
        this.getWrapper();
        final double var7 = MorbidWrapper.getPlayer().getDistanceToEntity(var1);
        final float var8 = 0.02666667f;
        if (var7 > 7.0) {
            GL11.glTranslatef((float)var4 + 0.0f, (float)(var5 + 2.299999952316284 + var7 / 30.0 - 0.2), (float)var6);
        }
        else {
            GL11.glTranslatef((float)var4 + 0.0f, (float)var5 + 2.3f, (float)var6);
        }
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        this.getWrapper();
        GL11.glRotatef(-MorbidWrapper.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        this.getWrapper();
        GL11.glRotatef(MorbidWrapper.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        if (var7 > 7.0) {
            GL11.glScaled(-var8 * var7 / 7.0, -var8 * var7 / 7.0, var8 * var7 / 7.0);
        }
        else {
            GL11.glScaled(-var8, -var8, var8);
        }
        final int var9 = var3.getStringWidth(var2) / 2;
        Gui.drawRect(-var9 - 1, -1, var9 + 2, 9, 1140850688);
        final String var10 = StringUtils.stripControlCodes(var2);
        final String var11 = StringUtils.stripControlCodes(var1.username);
        if (!Morbid.getFriends().friends.contains(var11)) {
            this.getWrapper();
        }
        int var12;
        if (var1.isInvisible()) {
            var12 = 16750848;
        }
        else if (var1.isSneaking()) {
            var12 = 14487808;
        }
        else if (var7 <= 64.0) {
            var12 = 16777215;
        }
        else {
            var12 = 65280;
        }
        var3.drawStringWithShadow((var12 != 16777215) ? var10 : var2, -var9, 0, var12);
    }
}
