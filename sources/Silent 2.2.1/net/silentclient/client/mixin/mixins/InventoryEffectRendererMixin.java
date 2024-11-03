package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import net.silentclient.client.Client;
import net.silentclient.client.mods.hud.PotionHudMod;
import net.silentclient.client.mods.settings.RenderMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InventoryEffectRenderer.class)
public abstract class InventoryEffectRendererMixin extends GuiContainer {
    public InventoryEffectRendererMixin(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Shadow private boolean hasActivePotionEffects;

    /**
     * @author kirillsaint
     * @reason custom effects update
     */
    @Overwrite
    public void updateActivePotionEffects()
    {
        if (!Minecraft.getMinecraft().thePlayer.getActivePotionEffects().isEmpty() && (!Client.getInstance().getModInstances().getPotionHudMod().isEnabled() || Client.getInstance().getModInstances().getPotionHudMod().isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(PotionHudMod.class, "Potions In Inventory").getValBoolean()))
        {
            if(Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Centered Potion Inventory").getValBoolean()) {
                this.guiLeft = (this.width - this.xSize) / 2;
            } else {
                this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
            }
            this.hasActivePotionEffects = true;
        }
        else
        {
            this.guiLeft = (this.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }
}
