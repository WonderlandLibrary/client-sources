package de.labystudio.gui;

import de.labystudio.chat.EnumAlertType;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Timings;
import de.labystudio.utils.ModGui;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiAchievementMod extends Gui
{
    private static final ResourceLocation achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    private Minecraft mc;
    private int width;
    private int height;
    private boolean large = false;
    private static final String __OBFID = "CL_00000721";
    ArrayList<String> title = new ArrayList();
    ArrayList<String> message = new ArrayList();
    public ArrayList<Long> time = new ArrayList();
    public ArrayList<Boolean> out = new ArrayList();
    public ArrayList<Integer> type = new ArrayList();
    boolean single = true;

    public GuiAchievementMod(Minecraft mc)
    {
        this.mc = mc;
    }

    public void displayBroadcast(String title, String message, EnumAlertType type)
    {
        if (type != EnumAlertType.CHAT || ConfigManager.settings.alertsChat)
        {
            if (type != EnumAlertType.TEAMSPEAK || ConfigManager.settings.alertsTeamSpeak)
            {
                this.title.add(title);
                this.message.add(message);
                this.time.add(Long.valueOf(Minecraft.getSystemTime()));
                this.out.add(Boolean.valueOf(false));
                this.type.add(Integer.valueOf(0));
                this.single();
            }
        }
    }

    private void single()
    {
        this.single = true;
    }

    public void displayMessage(String player, String message, EnumAlertType type)
    {
        if (type != EnumAlertType.CHAT || ConfigManager.settings.alertsChat)
        {
            if (type != EnumAlertType.TEAMSPEAK || ConfigManager.settings.alertsTeamSpeak)
            {
                if (this.title.contains(player))
                {
                    int i = this.title.indexOf(player);
                    this.message.set(i, message);
                    this.out.set(i, Boolean.valueOf(false));
                    this.time.set(i, Long.valueOf(Minecraft.getSystemTime() - 150L));
                }
                else
                {
                    this.title.add(player);
                    this.message.add(message);
                    this.time.add(Long.valueOf(Minecraft.getSystemTime()));
                    this.out.add(Boolean.valueOf(false));
                    this.type.add(Integer.valueOf(1));
                    this.single();
                }
            }
        }
    }

    private void updateAchievementWindowScale()
    {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        this.width = this.mc.displayWidth;
        this.height = this.mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        this.width = scaledresolution.getScaledWidth();
        this.height = scaledresolution.getScaledHeight();
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double)this.width, (double)this.height, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);

        if (this.time.size() >= 3)
        {
            this.single = false;
        }
    }

    public void updateAchievementWindow()
    {
        int i = 0;
        Timings.start("UpdateAchievementWindow");

        for (int j = this.time.size() - 1; j > 0; --j)
        {
            String s = (String)this.title.get(j);
            String s1 = (String)this.message.get(j);
            long k = ((Long)this.time.get(j)).longValue();
            s1 = ModGui.shortString(s1, LabyMod.getInstance().draw.getWidth() / 8);
            double d0 = (double)(Minecraft.getSystemTime() - k) / 3000.0D;
            double d1 = d0;

            if (d0 < 0.0D || d0 > 1.0D)
            {
                this.out.set(j, Boolean.valueOf(true));
            }

            if (d0 > 0.5D)
            {
                d0 = 0.5D;
            }

            this.updateAchievementWindowScale();
            double d2 = d0 * 2.0D;

            if (d2 > 1.0D)
            {
                d2 = 2.0D - d2;
            }

            d2 = d2 * 4.0D;
            d2 = 1.0D - d2;

            if (d2 < 0.0D)
            {
                d2 = 0.0D;
            }

            d2 = d2 * d2;
            d2 = d2 * d2;
            int l = this.width - 160;
            int i1 = 0 - (int)(d2 * 36.0D);
            int j1 = 0;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if (((Boolean)this.out.get(j)).booleanValue())
            {
                int k1 = (int)((double)i1 - d1 * 300.0D + 300.0D);

                if ((double)(k1 + 72) < 0.0D)
                {
                    this.title.remove(j);
                    this.message.remove(j);
                    this.time.remove(j);
                    this.out.remove(j);
                    this.type.remove(j);
                    continue;
                }

                if (!this.single)
                {
                    j1 = k1 * 4 * -1;
                }
                else
                {
                    i1 = k1;
                }
            }

            i1 = i1 + i;
            i = i1 + 32;
            int l1 = ((Integer)this.type.get(j)).intValue();
            GlStateManager.enableTexture2D();
            this.mc.getTextureManager().bindTexture(achievementBg);
            GlStateManager.disableLighting();
            this.draw(s, s1, i1, l1, j1);
        }

        Timings.stop("UpdateAchievementWindow");
    }

    private void draw(String title, String message, int y, int type, int xx)
    {
        int i = LabyMod.getInstance().draw.getStringWidth(message);

        if (i < 160)
        {
            i = 160;
        }

        if (i > 160)
        {
            i += 10;
        }

        if (type == 1)
        {
            i += 26;
        }

        i = i + 10;
        int j = LabyMod.getInstance().draw.getWidth() - i + xx;

        if (i > 160)
        {
            for (int k = i; k >= 0; --k)
            {
                if (k <= i - 10)
                {
                    this.drawTexturedModalRect(j + k, y, 96, 202, 7, 32);
                }
            }

            this.drawTexturedModalRect(j + i - 10, y, 246, 202, 10, 32);
        }
        else
        {
            this.drawTexturedModalRect(j, y, 96, 202, 160, 32);
        }

        if (type == 0)
        {
            this.mc.fontRendererObj.drawString(title, j + 6, y + 7, -256);
            this.mc.fontRendererObj.drawString(message, j + 6, y + 18, -1);
        }

        if (type == 1)
        {
            this.mc.fontRendererObj.drawString(title, j + 6 + 24, y + 7, -256);
            this.mc.fontRendererObj.drawString(message, j + 6 + 24, y + 18, -1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            LabyMod.getInstance().textureManager.drawPlayerHead(title, (double)(j + 5), (double)(y + 8), 0.7D);
        }
    }
}
