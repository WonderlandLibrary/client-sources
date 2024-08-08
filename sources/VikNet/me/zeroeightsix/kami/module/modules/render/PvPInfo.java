/*
 * Decompiled with CFR <Could not determine version>.
 *
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 */
package me.zeroeightsix.kami.module.modules.render;

import java.awt.Color;
import java.awt.Font;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Module.Info(name="PvPInfo", category=Module.Category.RENDER)
public class PvPInfo
        extends Module {
    private Setting<Float> x = this.register(Settings.f("InfoX", 16.0f));
    private Setting<Float> y = this.register(Settings.f("InfoY", 320.0f));
    private Setting<Boolean> rainbow = this.register(Settings.b("Rainbow", false));
    private Setting<Integer> red = this.register(Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
    private Setting<Integer> green = this.register(Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
    private Setting<Integer> blue = this.register(Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
    CFontRenderer cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);

    @Override
    public void onRender() {
        int drgb;
        float yCount = this.y.getValue().floatValue();
        int ared = this.red.getValue();
        int bgreen = this.green.getValue();
        int cblue = this.blue.getValue();
        int color = drgb = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        int totems = PvPInfo.mc.player.inventory.mainInventory.stream().filter(itemStack -> {
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING) return false;
            return true;
        }).mapToInt(ItemStack::getCount).sum();
        if (PvPInfo.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++totems;
        }
        if (this.rainbow.getValue().booleanValue()) {
            int argb;
            float[] hue = new float[]{(float) (System.currentTimeMillis() % 11520L) / 11520.0f};
            int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            int red = rgb >> 16 & 255;
            int green = rgb >> 8 & 255;
            int blue = rgb & 255;
            color = argb = ColourUtils.toRGBA(red, green, blue, 255);
        }
        {
            this.cFontRenderer.drawStringWithShadow("Trap: " + this.getAutoTrap(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float) this.cFontRenderer.getHeight() - 1.0f, color);
            this.cFontRenderer.drawStringWithShadow("Feet: " + this.getAutoFeetPlace(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float) this.cFontRenderer.getHeight() - 1.0f, color);
            this.cFontRenderer.drawStringWithShadow("Web: " + this.getWebAuraCombined(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float) this.cFontRenderer.getHeight() - 1.0f, color);
            this.cFontRenderer.drawStringWithShadow("Self: " + this.getSelfTrap(), this.x.getValue().floatValue(), (yCount += 10.0f) - (float) this.cFontRenderer.getHeight() - 1.0f, color);

            return;
        }
    }

    private String getAutoTrap() {
        try {
            return ModuleManager.getModuleByName("AutoTrap").isEnabled() ? ChatFormatting.GREEN.toString() + "ON" : ChatFormatting.DARK_RED.toString() + "OFF";
        } catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }

    private String getAutoFeetPlace() {
        try {
            return ModuleManager.getModuleByName("AutoFeetPlace").isEnabled() ? ChatFormatting.GREEN.toString() + "ON" : ChatFormatting.DARK_RED.toString() + "OFF";
        } catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }

    private String getWebAuraCombined() {
        try {
            return ModuleManager.getModuleByName("WebAuraCombined").isEnabled() ? ChatFormatting.GREEN.toString() + "ON" : ChatFormatting.DARK_RED.toString() + "OFF";
        } catch (Exception var2) {
            return "lack of games: " + var2;
        }

    }
    private String getSelfTrap() {
        try {
            return ModuleManager.getModuleByName("SelfTrap").isEnabled() ? ChatFormatting.GREEN.toString() + "ON" : ChatFormatting.DARK_RED.toString() + "OFF";
        } catch (Exception var2) {
            return "lack of games: " + var2;
        }
    }
}

