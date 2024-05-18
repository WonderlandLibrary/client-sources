package me.enrythebest.reborn.cracked.gui;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.*;
import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.util.*;
import java.util.*;

public class MorbidIngame extends GuiIngame
{
    public MorbidIngame(final Minecraft var1) {
        super(var1);
    }
    
    @Override
    public void renderGameOverlay(final float var1, final boolean var2, final int var3, final int var4) {
        super.renderGameOverlay(var1, var2, var3, var4);
        Morbid.getManager();
        if (!ModManager.getMod("vanilla").isEnabled() && !MorbidWrapper.mcObj().gameSettings.showDebugInfo) {
            MorbidWrapper.getFontRenderer().drawStringWithShadow("§4Reborn §fv0.6", 2, 2, 16729088);
            Morbid.getManager();
            if (ModManager.getMod("autosoup").isEnabled()) {
                this.drawCenteredString(MorbidWrapper.getFontRenderer(), "§bSoups §0[§e" + AutoSoup.soups + "§0]", MorbidWrapper.getScreenWidth() / 2, 2, 16729088);
            }
            int var5 = 12;
            Morbid.getManager();
            for (final ModBase var7 : ModManager.getMods()) {
                final String var8 = var7.getName();
                final int var9 = MorbidWrapper.getScreenWidth() - (MorbidWrapper.getFontRenderer().getStringWidth(new StringBuilder().append(var8).toString()) + 2);
                if (var7.isEnabled() && var7.shouldShow()) {
                    MorbidWrapper.getFontRenderer().drawStringWithShadow(new StringBuilder().append(var8).toString(), 2, var5, ColorUtil.random(200000000L, 1.0f).getRGB());
                    var5 += 12;
                }
            }
            final ScaledResolution var10 = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            final int var11 = ScaledResolution.getScaledWidth();
            final int var12 = ScaledResolution.getScaledHeight();
            try {
                Minecraft.getMinecraft();
                for (final Object var14 : Minecraft.theWorld.loadedEntityList) {
                    if (var14 instanceof EntityPlayer) {
                        final EntityPlayer var15 = (EntityPlayer)var14;
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentArmor(0) != null) {
                            Minecraft.getMinecraft();
                            Utils.drawItem(var11 - 18, var12 - 16, Minecraft.thePlayer.getCurrentArmor(0));
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[0].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[0].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[0].getMaxDamage(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[0].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[0].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[0].getMaxDamage()) + 20), var12 - 12, -1);
                        }
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentArmor(1) != null) {
                            Minecraft.getMinecraft();
                            Utils.drawItem(var11 - 18, var12 - 30, Minecraft.thePlayer.getCurrentArmor(1));
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            MorbidWrapper.getFontRenderer().drawString(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[1].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[1].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[1].getMaxDamage(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[1].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[1].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[1].getMaxDamage()) + 20), var12 - 26, -1);
                        }
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentArmor(2) != null) {
                            Minecraft.getMinecraft();
                            Utils.drawItem(var11 - 18, var12 - 44, Minecraft.thePlayer.getCurrentArmor(2));
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            MorbidWrapper.getFontRenderer().drawString(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[2].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[2].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[2].getMaxDamage(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[2].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[2].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[2].getMaxDamage()) + 20), var12 - 40, -1);
                        }
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentArmor(3) != null) {
                            Minecraft.getMinecraft();
                            Utils.drawItem(var11 - 18, var12 - 58, Minecraft.thePlayer.getCurrentArmor(3));
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            MorbidWrapper.getFontRenderer().drawString(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[3].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[3].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[3].getMaxDamage(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(String.valueOf(Minecraft.thePlayer.inventory.armorInventory[3].getMaxDamage() - Minecraft.thePlayer.inventory.armorInventory[3].getItemDamage()) + "/" + Minecraft.thePlayer.inventory.armorInventory[3].getMaxDamage()) + 20), var12 - 54, -1);
                        }
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentEquippedItem() == null) {
                            continue;
                        }
                        Minecraft.getMinecraft();
                        Utils.drawItem(var11 - 18, var12 - 72, Minecraft.thePlayer.getCurrentEquippedItem());
                        Minecraft.getMinecraft();
                        if (Minecraft.thePlayer.getCurrentEquippedItem().isItemStackDamageable()) {
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            MorbidWrapper.getFontRenderer().drawString(String.valueOf(Minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage() - Minecraft.thePlayer.inventory.getCurrentItem().getItemDamage()) + "/" + Minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(String.valueOf(Minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage() - Minecraft.thePlayer.inventory.getCurrentItem().getItemDamage()) + "/" + Minecraft.thePlayer.inventory.getCurrentItem().getMaxDamage()) + 20), var12 - 68, -1);
                        }
                        else {
                            Minecraft.getMinecraft();
                            Minecraft.getMinecraft();
                            MorbidWrapper.getFontRenderer().drawString(Minecraft.thePlayer.inventory.getCurrentItem().getDisplayName(), var11 - (MorbidWrapper.getFontRenderer().getStringWidth(Minecraft.thePlayer.inventory.getCurrentItem().getDisplayName()) + 20), var12 - 68, -1);
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
    }
}
