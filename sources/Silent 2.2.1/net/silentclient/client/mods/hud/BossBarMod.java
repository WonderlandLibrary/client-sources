package net.silentclient.client.mods.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.boss.BossStatus;
import net.silentclient.client.Client;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class BossBarMod extends Mod {
    public BossBarMod() {
        super("Boss Bar", ModCategory.MODS, "silentclient/icons/mods/bossbar.png", true);
    }

    @Override
    public void setup() {
        this.addBooleanSetting("Boss Bar", this, true);
        this.addBooleanSetting("Boss Text", this, true);
    }

    public static void renderBossHealth(GuiIngame instance)
    {
        if(!Client.getInstance().getModInstances().getModByClass(BossBarMod.class).isEnabled()) {
            return;
        }
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)
        {
            --BossStatus.statusBarTime;
            FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            short short1 = 182;
            int j = i / 2 - short1 / 2;
            int k = (int)(BossStatus.healthScale * (float)(short1 + 1));
            byte b0 = 12;
            if(Client.getInstance().getSettingsManager().getSettingByClass(BossBarMod.class, "Boss Bar").getValBoolean()) {
                instance.drawTexturedModalRect(j, b0, 0, 74, short1, 5);
                instance.drawTexturedModalRect(j, b0, 0, 74, short1, 5);

                if (k > 0)
                {
                    instance.drawTexturedModalRect(j, b0, 0, 79, k, 5);
                }
            }

            String s = BossStatus.bossName;
            int l = 16777215;

//            if (Config.isCustomColors())
//            {
//                l = CustomColors.getBossTextColor(l);
//            }
            if(Client.getInstance().getSettingsManager().getSettingByClass(BossBarMod.class, "Boss Text").getValBoolean()) {
                fontrenderer.drawStringWithShadow(s, (float)(i / 2 - fontrenderer.getStringWidth(s) / 2), (float)(b0 - 10), -1);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.icons);
        }
    }
}
