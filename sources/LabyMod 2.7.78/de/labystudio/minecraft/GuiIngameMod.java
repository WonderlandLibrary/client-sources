package de.labystudio.minecraft;

import de.labystudio.chat.ChatHandler;
import de.labystudio.chat.Client;
import de.labystudio.chat.LabyModPlayer;
import de.labystudio.gui.GuiStopWatch;
import de.labystudio.labymod.ClickCounter;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Timings;
import de.labystudio.listener.Games;
import de.labystudio.listener.GommeHD;
import de.labystudio.listener.HiveMC;
import de.labystudio.listener.JumpLeague;
import de.labystudio.listener.Revayd;
import de.labystudio.listener.Timolia;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.events.RenderOverlayEvent;
import de.labystudio.utils.Allowed;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FriendsLoader;
import de.labystudio.utils.ModGui;
import de.zockermaus.ts3.TeamSpeak;
import de.zockermaus.ts3.TeamSpeakBridge;
import de.zockermaus.ts3.TeamSpeakChannel;
import de.zockermaus.ts3.TeamSpeakController;
import de.zockermaus.ts3.TeamSpeakUser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiIngameMod extends GuiIngame
{
    ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dt2 = new SimpleDateFormat("hh:mm a");
    ItemStack itemArrow = new ItemStack(Item.getItemById(262));
    private final Minecraft mc;
    private final DrawUtils draw;
    int mouseLocation = 0;

    public GuiIngameMod(Minecraft mcIn)
    {
        super(mcIn);
        this.mc = mcIn;
        this.draw = LabyMod.getInstance().draw;
    }

    public void drawPotions()
    {
        if (!LabyMod.getInstance().isChatGUI())
        {
            if (ConfigManager.settings.potionEffects)
            {
                if (Allowed.potions())
                {
                    double d0 = 1.7D;
                    double d1 = 0.7D;

                    if (ConfigManager.settings.potionsize == 0)
                    {
                        d0 = 2.4D;
                        d1 = 0.5D;
                    }
                    else if (ConfigManager.settings.potionsize != 1)
                    {
                        d0 = 1.3D;
                        d1 = 0.9D;
                    }

                    int i = ModGui.mainList;

                    if (ModGui.isSwitch())
                    {
                        i = ModGui.offList;
                    }

                    int j = (int)((double)i * d0);
                    int k = -5;
                    Collection collection = this.mc.thePlayer.getActivePotionEffects();

                    if (!collection.isEmpty())
                    {
                        GlStateManager.scale(d1, d1, d1);
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.disableLighting();
                        int l = 23;

                        if (collection.size() > 5)
                        {
                            l = 132 / (collection.size() - 1);
                        }

                        for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects())
                        {
                            Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                            this.mc.getTextureManager().bindTexture(this.inventoryBackground);

                            if (potion.hasStatusIcon())
                            {
                                int i1 = potion.getStatusIconIndex();
                                this.drawTexturedModalRect(k + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                            }

                            String s1 = I18n.format(potion.getName(), new Object[0]);

                            if (potioneffect.getAmplifier() == 1)
                            {
                                s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
                            }
                            else if (potioneffect.getAmplifier() == 2)
                            {
                                s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
                            }
                            else if (potioneffect.getAmplifier() == 3)
                            {
                                s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
                            }

                            this.draw.fontRenderer.drawStringWithShadow(Color.c(1) + s1, (float)(k + 10 + 18), (float)(j + 6), 16777215);
                            String s = Potion.getDurationString(potioneffect);
                            this.draw.fontRenderer.drawStringWithShadow(Color.c(3) + s, (float)(k + 10 + 18), (float)(j + 6 + 10), 8355711);
                            j += l;
                        }
                    }
                }
            }
        }
    }

    public static String toUpperCaseFirstLetter(String userIdea)
    {
        char[] achar = userIdea.toCharArray();
        achar[0] = Character.toUpperCase(achar[0]);
        return new String(achar);
    }

    public void drawMainGui(int set)
    {
        ModGui.mainList = set;

        if (ConfigManager.settings.showFPS.booleanValue())
        {
            ModGui.addMainLabel("FPS", ModGui.getFPS() + "", ModGui.mainList);
        }

        if (ConfigManager.settings.showCoords)
        {
            ModGui.addMainLabel("X", ModGui.getX() + "", ModGui.mainList);
            ModGui.addMainLabel("Y", ModGui.getY() + "", ModGui.mainList);
            ModGui.addMainLabel("Z", ModGui.getZ() + "", ModGui.mainList);
        }

        String s = "";

        if (ConfigManager.settings.fLayoutNumber)
        {
            s = s + ModGui.getF();
        }

        if (ConfigManager.settings.fLayoutDirection)
        {
            s = s + ModGui.getD();
        }

        if (ConfigManager.settings.fLayoutXAndZ)
        {
            s = s + ModGui.getXZD();
        }

        if (!s.isEmpty())
        {
            ModGui.addMainLabel("F", s, ModGui.mainList);
        }

        if (ConfigManager.settings.showBiome)
        {
            ModGui.addMainLabel("Biome", ModGui.getBiom() + "", ModGui.mainList);
        }

        if (!this.mc.isSingleplayer() && LabyMod.getInstance().playerPing != 0 && ConfigManager.settings.showPing)
        {
            ModGui.addMainLabel("Ping", LabyMod.getInstance().playerPing + "", ModGui.mainList);
        }

        if (ConfigManager.settings.showOnlinePlayers && LabyMod.getInstance().onlinePlayers.size() >= 2)
        {
            ModGui.addMainLabel("Online", LabyMod.getInstance().onlinePlayers.size() + "", ModGui.mainList);
        }

        if (ConfigManager.settings.showServerIP.booleanValue() && !LabyMod.getInstance().ip.isEmpty())
        {
            ModGui.addMainLabel("IP", LabyMod.getInstance().ip + "", ModGui.mainList);
        }

        if (ConfigManager.settings.lavaTime && LabyMod.getInstance().lavaTime != 0)
        {
            if (LabyMod.getInstance().removeChallenge == 0)
            {
                ModGui.addMainLabel("Timer", ModGui.translateTimer(LabyMod.getInstance().lavaTime) + "", ModGui.mainList);
            }
            else
            {
                ModGui.addMainLabel("Timer", Color.cl("c") + ModGui.translateTimer(LabyMod.getInstance().lavaTime) + "", ModGui.mainList);
            }
        }

        if (ConfigManager.settings.clickTest && ClickCounter.getClickResult() != 0.0D)
        {
            ModGui.addMainLabel("Clicks", (int)ClickCounter.getClickResult() + "", ModGui.mainList);
        }

        if (ConfigManager.settings.afkTimer.booleanValue())
        {
            if (LabyMod.getInstance().isAFK)
            {
                ModGui.addMainLabel("AFK", ModGui.translateTimer((int)((LabyMod.getInstance().lastMove - System.currentTimeMillis()) * -1L / 1000L)) + "", ModGui.mainList);
            }
            else if (LabyMod.getInstance().keepTime + 5000L > System.currentTimeMillis())
            {
                ModGui.addMainLabel("AFK", Color.cl("c") + ModGui.translateTimer((int)((LabyMod.getInstance().lastTimeAFK - LabyMod.getInstance().keepTime) * -1L / 1000L)) + "", ModGui.mainList);
            }
        }

        if (ConfigManager.settings.showKills && Games.kills != 0)
        {
            ModGui.addMainLabel("Kills", Games.kills + "", ModGui.mainList);
        }

        if (ConfigManager.settings.showNickname.booleanValue() && !LabyMod.getInstance().nickname.isEmpty())
        {
            ModGui.addMainLabel("Nick", LabyMod.getInstance().nickname, ModGui.mainList);
        }

        if (ConfigManager.settings.showClock)
        {
            if (ConfigManager.settings.twelveHourClock)
            {
                ModGui.addMainLabel("Clock", "" + this.dt2.format(Long.valueOf(System.currentTimeMillis())), ModGui.mainList);
            }
            else
            {
                ModGui.addMainLabel("Clock", "" + this.dt1.format(Long.valueOf(System.currentTimeMillis())), ModGui.mainList);
            }
        }
    }

    public Boolean isNotEmpty(int slot)
    {
        return slot == -1 ? Boolean.valueOf(this.mc.thePlayer.inventory.getCurrentItem() != null) : Boolean.valueOf(this.mc.thePlayer.inventory.armorItemInSlot(slot) != null);
    }

    public void drawArmorTop()
    {
        if (ConfigManager.settings.hud != 0 && Allowed.armorHud() && ConfigManager.settings.armorHudPositionOnTop)
        {
            if (this.isNotEmpty(-1).booleanValue())
            {
                this.addArmor(this.mc.thePlayer.inventory.getCurrentItem());
            }

            if (this.isNotEmpty(3).booleanValue())
            {
                this.addArmor(this.mc.thePlayer.inventory.armorItemInSlot(3));
            }

            if (this.isNotEmpty(2).booleanValue())
            {
                this.addArmor(this.mc.thePlayer.inventory.armorItemInSlot(2));
            }

            if (this.isNotEmpty(1).booleanValue())
            {
                this.addArmor(this.mc.thePlayer.inventory.armorItemInSlot(1));
            }

            if (this.isNotEmpty(0).booleanValue())
            {
                this.addArmor(this.mc.thePlayer.inventory.armorItemInSlot(0));
            }
        }
    }

    public void drawArmorHotbar()
    {
        boolean flag = false;

        if (ConfigManager.settings.hud != 0 && Allowed.armorHud() && !ConfigManager.settings.armorHudPositionOnTop)
        {
            int i = 0;

            if (this.isNotEmpty(2).booleanValue())
            {
                ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(2);
                this.draw.drawItem(itemstack, this.draw.getWidth() / 2 - 110, this.draw.getHeight() - 18 - i);
                this.drawArmorStatus(itemstack, this.draw.getWidth() / 2 - 110, this.draw.getHeight() - 18 - i + 4, false);
                i += 15;
                flag = true;
            }

            if (this.isNotEmpty(3).booleanValue())
            {
                ItemStack itemstack2 = this.mc.thePlayer.inventory.armorItemInSlot(3);
                this.draw.drawItem(itemstack2, this.draw.getWidth() / 2 - 110, this.draw.getHeight() - 18 - i);
                this.drawArmorStatus(itemstack2, this.draw.getWidth() / 2 - 110, this.draw.getHeight() - 18 - i + 5, false);
                flag = true;
            }

            i = 0;

            if (this.isNotEmpty(0).booleanValue())
            {
                ItemStack itemstack3 = this.mc.thePlayer.inventory.armorItemInSlot(0);
                this.draw.drawItem(itemstack3, this.draw.getWidth() / 2 + 96, this.draw.getHeight() - 18 - i);
                this.drawArmorStatus(itemstack3, this.draw.getWidth() / 2 + 113, this.draw.getHeight() - 18 - i + 4, true);
                i += 15;
            }

            if (this.isNotEmpty(1).booleanValue())
            {
                ItemStack itemstack4 = this.mc.thePlayer.inventory.armorItemInSlot(1);
                this.draw.drawItem(itemstack4, this.draw.getWidth() / 2 + 96, this.draw.getHeight() - 18 - i);
                this.drawArmorStatus(itemstack4, this.draw.getWidth() / 2 + 113, this.draw.getHeight() - 18 - i + 5, true);
            }
        }

        if (ConfigManager.settings.showArrow && this.mc.thePlayer != null && this.mc.thePlayer.inventory.hasItem(Item.getItemById(261)))
        {
            int j = 0;

            if (!ConfigManager.settings.armorHudPositionOnTop && flag)
            {
                switch (ConfigManager.settings.hud)
                {
                    case 1:
                        j = 43;
                        break;

                    case 2:
                        j = 65;
                        break;

                    case 3:
                        j = 13;
                        break;

                    case 4:
                        j = 42;
                }
            }

            int k = 0;

            if (this.mc.thePlayer.inventory.mainInventory != null)
            {
                for (ItemStack itemstack1 : this.mc.thePlayer.inventory.mainInventory)
                {
                    if (itemstack1 != null && itemstack1.getItem() != null && Item.getIdFromItem(itemstack1.getItem()) == 262)
                    {
                        k += itemstack1.stackSize;
                    }
                }
            }

            this.draw.drawItem(this.itemArrow, (double)(this.draw.getWidth() / 2 - 116 - j), (double)(this.draw.getHeight() - 20), k + "x");
        }
    }

    public void addArmor(ItemStack item)
    {
        if (ModGui.isSwitch())
        {
            this.draw.drawItem(item, 2, ModGui.offList);
            this.drawArmorStatus(item, 20, ModGui.offList + 5, true);
        }
        else
        {
            double d0 = LabyMod.getInstance().draw.getScale(ConfigManager.settings.size);
            this.draw.drawRightItem(item, (int)((double)(this.draw.getWidth() - 28) / d0), ModGui.offList);
            this.drawArmorStatus(item, (int)((double)(this.draw.getWidth() - 20) / d0), ModGui.offList + 5, false);
        }

        ModGui.offListNext(14);
    }

    public void drawArmorStatus(ItemStack item, int x, int y, boolean left)
    {
        if (left)
        {
            if (ConfigManager.settings.hud == 1 && item.getMaxDamage() - item.getItemDamage() > 0)
            {
                this.draw.drawString(item.getMaxDamage() - item.getItemDamage() + "", (double)x, (double)y);
            }

            if (ConfigManager.settings.hud == 2 && item.getMaxDamage() - item.getItemDamage() > 0)
            {
                this.draw.drawString(item.getMaxDamage() - item.getItemDamage() + "/" + item.getMaxDamage(), (double)x, (double)y);
            }

            if (ConfigManager.settings.hud == 4 && item.getMaxDamage() - item.getItemDamage() > 0)
            {
                double d0 = (double)(item.getMaxDamage() - item.getItemDamage());
                double d1 = (double)item.getMaxDamage();
                String s = Math.round(100.0D / d1 * d0) + "%";
                this.draw.drawString(s, (double)x, (double)y);
            }
        }
        else
        {
            double d4 = LabyMod.getInstance().draw.getScale(ConfigManager.settings.size);
            String s1 = "";

            if (item.getMaxDamage() - item.getItemDamage() > 0)
            {
                if (ConfigManager.settings.hud == 2)
                {
                    s1 = item.getMaxDamage() - item.getItemDamage() + "/" + item.getMaxDamage();
                }

                if (ConfigManager.settings.hud == 1)
                {
                    s1 = item.getMaxDamage() - item.getItemDamage() + "";
                }

                if (ConfigManager.settings.hud == 4)
                {
                    double d2 = (double)(item.getMaxDamage() - item.getItemDamage());
                    double d3 = (double)item.getMaxDamage();
                    s1 = Math.round(100.0D / d3 * d2) + "%";
                }

                this.draw.drawRightString(s1, (double)x, (double)y);
            }
        }
    }

    public void drawRadar()
    {
        if ((ConfigManager.settings.radarCoordinate || ConfigManager.settings.radarDirection) && Allowed.gui())
        {
            int i = this.draw.getWidth() / 2;
            int j = (int)MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) * -1 - 360;
            int k = 45;
            int l = 0;

            if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)
            {
                if (ConfigManager.settings.showBossBar)
                {
                    l += 15;
                }
                else
                {
                    l += 9;
                }
            }

            for (int i1 = 0; i1 <= 2; ++i1)
            {
                for (double d0 = 0.0D; d0 <= 3.5D; d0 += 0.5D)
                {
                    if (this.draw.getWidth() / 2 + j > i - 50 && this.draw.getWidth() / 2 + j < i + 50)
                    {
                        int j1 = 0;

                        if (ConfigManager.settings.radarCoordinate)
                        {
                            this.draw.drawCenteredString(Color.c(3) + d0, this.draw.getWidth() / 2 + j, 4 + l + j1);
                            j1 += 10;
                        }

                        if (ConfigManager.settings.radarDirection)
                        {
                            String s = "South";

                            if (d0 == 0.5D)
                            {
                                s = "South/West";
                            }

                            if (d0 == 1.0D)
                            {
                                s = "West";
                            }

                            if (d0 == 1.5D)
                            {
                                s = "West/North";
                            }

                            if (d0 == 2.0D)
                            {
                                s = "North";
                            }

                            if (d0 == 2.5D)
                            {
                                s = "North/East";
                            }

                            if (d0 == 3.0D)
                            {
                                s = "East";
                            }

                            if (d0 == 3.5D)
                            {
                                s = "East/South";
                            }

                            if ((d0 + "").endsWith(".5"))
                            {
                                s = Color.c(2) + s;
                            }
                            else
                            {
                                s = Color.c(1) + s;
                            }

                            this.draw.drawCenteredString(Color.c(3) + s, this.draw.getWidth() / 2 + j, 4 + l + j1);
                        }
                    }

                    j += k;
                }
            }
        }
    }

    public void drawGameModes()
    {
        GommeHD.drawGommeHDGui();
        JumpLeague.drawPlayMinityGui();
        Timolia.drawTimoliaGui();
        Revayd.drawRevaydGui();
        HiveMC.drawHiveGui();
    }

    private void drawOnlineFriendsOnServer()
    {
        int i = 4;

        try
        {
            if (ConfigManager.settings.showOnlineFriends && !LabyMod.getInstance().isChatGUI() && ChatHandler.getMyFriends() != null && FriendsLoader.friends != null)
            {
                ArrayList<String> arraylist = new ArrayList();
                Iterator<LabyModPlayer> iterator = ChatHandler.getMyFriends().iterator();

                while (iterator.hasNext())
                {
                    arraylist.add(((LabyModPlayer)iterator.next()).getName().toLowerCase());
                }

                for (NetworkPlayerInfo networkplayerinfo : LabyMod.getInstance().onlinePlayers)
                {
                    if (LabyMod.getInstance().draw.getWidth() / 2 - 120 - i * 16 > 0 && networkplayerinfo != null && FriendsLoader.friends != null && networkplayerinfo.getGameProfile() != null && networkplayerinfo.getGameProfile().getName() != null && (FriendsLoader.friends.containsKey(networkplayerinfo.getGameProfile().getName()) || arraylist.contains(networkplayerinfo.getGameProfile().getName().toLowerCase())))
                    {
                        if (ConfigManager.settings.onlineFriendsPositionOnTop)
                        {
                            int j = LabyMod.getInstance().draw.getWidth() / 6 * 5;
                            drawRect(j - i * 10 - 2, 0, j - i * 10 + 8, 9, Integer.MIN_VALUE);
                            drawRect(j - i * 10 - 1, 1, j - i * 10 + 7, 8, Integer.MAX_VALUE);
                            GlStateManager.color(1.0F, 1.0F, 1.0F);
                            LabyMod.getInstance().textureManager.drawPlayerHead(networkplayerinfo.getGameProfile().getName(), (double)(j - i * 10), 4.5D, 0.19D);
                            ++i;
                        }
                        else
                        {
                            int k = LabyMod.getInstance().draw.getWidth() / 2 - 120;
                            drawRect(k - i * 16 - 2, this.draw.getHeight() - 1, k - i * 16 + 14, this.draw.getHeight() - 18, Integer.MIN_VALUE);
                            drawRect(k - i * 16 - 1, this.draw.getHeight() - 2, k - i * 16 + 13, this.draw.getHeight() - 17, Integer.MAX_VALUE);
                            GlStateManager.color(1.0F, 1.0F, 1.0F);
                            LabyMod.getInstance().textureManager.drawPlayerHead(networkplayerinfo.getGameProfile().getName(), (double)(k - i * 16) + 0.4D, (double)this.draw.getHeight() - 12.5D, 0.37D);
                            ++i;
                        }
                    }
                }
            }
        }
        catch (Exception var7)
        {
            ;
        }
    }

    public void drawTeamSpeak()
    {
        if (ConfigManager.settings.teamSpeak.booleanValue())
        {
            if (ConfigManager.settings.teamSpakIngame || ConfigManager.settings.teamSpakIngameClients.booleanValue())
            {
                TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().me();

                if (teamspeakuser != null)
                {
                    List<TeamSpeakUser> list = TeamSpeakBridge.getChannelUsers(teamspeakuser.getChannelId());
                    TeamSpeakChannel teamspeakchannel = TeamSpeakController.getInstance().getChannel(teamspeakuser.getChannelId());

                    if (teamspeakchannel != null)
                    {
                        String s = teamspeakchannel.getChannelName();

                        if (ConfigManager.settings.teamSpakIngame)
                        {
                            ModGui.addMainLabel(s, "", ModGui.mainList);
                        }

                        if (ConfigManager.settings.teamSpakIngameClients.booleanValue())
                        {
                            for (TeamSpeakUser teamspeakuser1 : list)
                            {
                                String s1 = TeamSpeak.getTalkColor(teamspeakuser1) + "  \u2b24 " + Color.cl("f") + teamspeakuser1.getNickName() + TeamSpeak.getAway(teamspeakuser1);

                                if (ModGui.isSwitch())
                                {
                                    s1 = Color.cl("f") + teamspeakuser1.getNickName() + TeamSpeak.getAway(teamspeakuser1) + TeamSpeak.getTalkColor(teamspeakuser1) + "  \u2b24 ";
                                }

                                this.draw.addString(s1, ModGui.mainList);
                                ModGui.mainListNext();
                            }
                        }
                    }
                }
            }
        }
    }

    public void drawSpotify()
    {
        if (ConfigManager.settings.spotfiyTrack)
        {
            if (LabyMod.getInstance().getSpotifyManager() != null && SystemUtils.IS_OS_WINDOWS)
            {
                if (LabyMod.getInstance().getSpotifyManager().getArtistName() == null)
                {
                    if (LabyMod.getInstance().getSpotifyManager().getSpotifyTitle() != null && !LabyMod.getInstance().getSpotifyManager().getSpotifyTitle().equals("?") && LabyMod.getInstance().getSpotifyManager().getDisplayTime() + 10000L > System.currentTimeMillis())
                    {
                        if (ModGui.mainList != 2)
                        {
                            ModGui.mainListNext();
                        }

                        ModGui.addMainLabel("Spotify", LabyMod.getInstance().getSpotifyManager().getSpotifyTitle(), ModGui.mainList);
                    }
                }
                else
                {
                    if (ModGui.mainList != 2)
                    {
                        ModGui.mainListNext();
                    }

                    ModGui.addMainLabel("Track", LabyMod.getInstance().getSpotifyManager().getTrackName(), ModGui.mainList);
                    ModGui.addMainLabel("Artist", LabyMod.getInstance().getSpotifyManager().getArtistName(), ModGui.mainList);
                }
            }
            else
            {
                ModGui.mainListNext();
                ModGui.addMainLabel("Spotify", "Windows support only", ModGui.mainList);
            }
        }
    }

    private void drawStopWatch()
    {
        if (GuiStopWatch.running)
        {
            int i = 0;

            if ((ConfigManager.settings.radarCoordinate || ConfigManager.settings.radarDirection) && Allowed.gui())
            {
                if (ConfigManager.settings.radarCoordinate && ConfigManager.settings.radarDirection)
                {
                    i = 22;
                }
                else
                {
                    i = 11;
                }
            }

            if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)
            {
                if (ConfigManager.settings.showBossBar)
                {
                    i += 15;
                }
                else
                {
                    i += 9;
                }
            }

            this.draw.drawCenteredString(GuiStopWatch.parseTime(GuiStopWatch.getTimer()), (double)(this.draw.getWidth() / 2), (double)(i + 5), 2.0D);
        }
    }

    public void drawGui()
    {
        if (ConfigManager.settings.gui)
        {
            if (LabyMod.getInstance().isInGame())
            {
                if (!this.mc.gameSettings.showDebugInfo)
                {
                    if (Allowed.gui())
                    {
                        ModGui.mainList = 2;
                        ModGui.offList = 2;
                        GL11.glPushMatrix();
                        GL11.glScaled(this.draw.getScale(ConfigManager.settings.size), this.draw.getScale(ConfigManager.settings.size), this.draw.getScale(ConfigManager.settings.size));

                        if (ConfigManager.settings.alertsChat)
                        {
                            LabyMod.getInstance().getClient();

                            if (!Client.isBusy())
                            {
                                int i = 0;
                                ArrayList<LabyModPlayer> arraylist = new ArrayList();
                                arraylist.addAll(LabyMod.getInstance().getClient().getFriends());

                                for (LabyModPlayer labymodplayer : arraylist)
                                {
                                    if (labymodplayer.isNotify())
                                    {
                                        i += labymodplayer.messages;
                                    }
                                }

                                if (i != 0)
                                {
                                    String s = "";

                                    if (i != 1)
                                    {
                                        s = "s";
                                    }

                                    String s1 = Color.cl("c") + i + " new message" + s;
                                    this.draw.addRightString(s1, ModGui.offList);
                                    ModGui.offListNext();
                                }
                            }
                        }

                        this.drawArmorTop();
                        this.drawMainGui(2);
                        this.drawGameModes();
                        this.drawTeamSpeak();
                        this.drawSpotify();
                        this.drawPotions();
                        GL11.glPopMatrix();
                        this.drawArmorHotbar();
                    }
                }
            }
        }
    }

    public void renderGameOverlay(float partialTicks)
    {
        super.renderGameOverlay(partialTicks);
        Timings.start("Render IngameGui");
        ModGui.mainList = 0;
        ModGui.offList = 0;

        if (!ConfigManager.settings.hideMod)
        {
            this.drawGui();
            this.drawOnlineFriendsOnServer();
            this.drawRadar();
            this.drawStopWatch();
        }

        LabyMod.getInstance().onRender();

        if (ModGui.frameTimer + 1000L > System.currentTimeMillis())
        {
            ++ModGui.frames;
        }
        else
        {
            ModGui.fps = ModGui.frames;
            ModGui.frames = 0;
            ModGui.frameTimer = System.currentTimeMillis();
        }

        if (this.mouseLocation != Mouse.getX() || this.mc.thePlayer.moveForward != 0.0F || this.mc.thePlayer.fallDistance != 0.0F)
        {
            if (LabyMod.getInstance().isAFK)
            {
                ChatHandler.setAFK(false);
                LabyMod.getInstance().lastTimeAFK = LabyMod.getInstance().lastMove;
                LabyMod.getInstance().keepTime = System.currentTimeMillis();
            }

            LabyMod.getInstance().lastMove = System.currentTimeMillis();
        }

        this.mouseLocation = Mouse.getX();

        if (LabyMod.getInstance().lastMove + 20000L < System.currentTimeMillis())
        {
            if (!LabyMod.getInstance().isAFK)
            {
                ChatHandler.setAFK(true);
            }

            LabyMod.getInstance().isAFK = true;
        }
        else
        {
            LabyMod.getInstance().isAFK = false;
        }

        if (ModAPI.enabled())
        {
            ModAPI.callEvent(new RenderOverlayEvent(partialTicks));
        }

        LabyMod.getInstance().overlay(0, 0);
        Timings.stop("Render IngameGui");
    }
}
