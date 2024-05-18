package de.labystudio.gui;

import de.labystudio.gui.extras.GuiCustomButton;
import de.labystudio.gui.extras.SliderColor;
import de.labystudio.gui.extras.SliderCoords;
import de.labystudio.gui.extras.SliderSize;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.ControllerInput;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.ModGui;
import de.zockermaus.ts3.TeamSpeak;
import de.zockermaus.ts3.TeamSpeakController;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;

public class GuiModSettings extends GuiScreen implements GuiYesNoCallback
{
    private final GameSettings game_settings_1;
    private final GuiScreen field_146441_g;
    private DrawUtils draw;
    private int tabY = 0;
    int x;
    int y;
    int sy = 40;
    int ey = 21;
    public boolean slider = false;
    GuiButton theSlider;
    String currentTab = "";

    public GuiModSettings(GuiScreen p_i1046_1_, GameSettings p_i1046_2_)
    {
        this.draw = LabyMod.getInstance().draw;
        this.field_146441_g = p_i1046_1_;
        this.game_settings_1 = p_i1046_2_;
        this.currentTab = "Ingame";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.tabY = 28;
        this.sy = this.height / 7;

        if (this.sy > 60)
        {
            this.sy = 60;
        }

        this.ey = this.height / 14;

        if (this.ey > 21)
        {
            this.ey = 21;
        }

        this.buttonList.add(new GuiButton(1, 2, this.height - 21, 99, 20, "Done"));
        this.addTab("Ingame");
        this.addTab("Formatting");
        this.addTab("Animations");
        this.addTab("Gui Settings");
        this.addTab("Extras");
        this.addTab("Server Support");
        this.addTab("GommeHD.net");
        this.addTab("Menu");
        this.addTab("TeamSpeak");
        this.addTab("Minecraft Chat");
        this.initSettings(false);
    }

    public void initSettings(boolean r)
    {
        this.y = 46;
        this.x = 120;

        if (this.currentTab.equals("Ingame"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.potionEffects), 6, "Potion Effects", "Shows your current potion effects");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showOnlinePlayers), 5, "Online Players", "Shows how many players are online");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showCoords), 22, "Show coords", "Shows the coordinates");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showServerIP), 34, "Show Server IP", "Displays the IP adress of the server you\'re currently playing on.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showFPS), 37, "Show FPS", "Shows the FPS");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showKills), 51, "Kills", "Shows the amount of killed players (In Hardcore Games and Survival Games)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showPing), 33, "Show ping", "Shows your current ping");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showBossBar), 63, "Bossbar", "This option allows you to remove the boss health bar, but the text above it will still be displayed.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showClock), 83, "Clock", "Displays your current real life time.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showArrow), 93, "Arrow amount", "Displays the current amount of arrows in your inventory");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showBiome), 97, "Show biome", "Shows the world biome of your position");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showDate), 106, "Show Date", "Shows the date ingame");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.memory), 110, "Show Memory", "Shows used memory in percent");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showLiveTicker), 107, "EM LIVETICKER", "Shows the EM LIVE-STATS");
        }

        if (this.currentTab.equals("Formatting"))
        {
            this.addSwitchNoOff(r, ConfigManager.settings.layout, 8, 9, "Colon", 10, "Brackets", 11, "Angle brackets", "Gui layout");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.bold), 29, "Bold", "");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.underline), 30, "Underline", "");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.italic), 31, "Italic", "");
            this.addSliderCoords(r, "Number precision");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.twelveHourClock), 104, "12-hour clock", "It displays the 12-hour clock (0-12 hours)");
            this.addSliderColor(r, "Prefix", 1);
            this.addSliderColor(r, "Brackets", 2);
            this.addSliderColor(r, "Text", 3);
            this.addSliderColor(r, "Title", 4);
            this.addSliderColor(r, "Info", 5);
            this.draw.drawString(Color.c(4) + "Preview:", 108.0D, 5.0D);
            this.draw.drawString(ModGui.createLabel("X", ModGui.getX()), 108.0D, 15.0D);
        }

        if (this.currentTab.equals("Animations"))
        {
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldDMG), 32, "Damage", "In the 1.8. a damaged player will flash up red for a short time. In the 1.7 the armor will flash up aswell.");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldHearts), 40, "Heart", "In the 1.8 your hearts that you lost will light up in white shortly. This does not happen in 1.7");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldBow), 41, "Bow", "In the 1.8 the bow is scaled different, in the 1.7 we scaled it as u are used to it");
            this.addToggleCustom(r, Boolean.valueOf(ConfigManager.settings.oldTablist), 58, "Tablist", "1.8 Design", "1.7 Design", "The 1.8 Tablist is showing you more information such as playerheads and is sorted alphabetically");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldBlockBuild), 81, "BlockBuild", "In 1.7 it was possible to build a block and destory it the same time. We integrated this feature into 1.8 - This feature is only available on GommeHD.net");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldSword), 42, "Sword", "In the 1.7 setting you will see the typical Swordblockanimation in third Personview");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldBlockhit), 43, "Blockhit", "the 1.8 does not support the blockhit animation. By choosing 1.7 you will see the wellknown animation again");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldFishing), 46, "Fishing", "In the 1.8 the rod is scaled different and shown in another angle, in the 1.7 we scaled it as u are used to it");
            this.addToggleA(r, Boolean.valueOf(ConfigManager.settings.oldSneak), 79, "Sneaking", "In the 1.7 sneaking is made smoother similar to the sneaking animation in 1.7 - This feature is only available on GommeHD.net");
            this.addToggleCustom(r, Boolean.valueOf(ConfigManager.settings.oldInventory), 102, "Inventory", Color.cl("c") + "1.8 Shift", "1.7 Shift", "In 1.7, the inventory will stay in the middle of the screen, regardless of whether you\'ve got an active effect or not. In 1.8, the inventory is shifted to the right, if you\'ve got an active effect.");
        }

        if (this.currentTab.equals("Gui Settings"))
        {
            this.addSwitch(r, ConfigManager.settings.hud, 12, 13, "Durability only", 14, "Durability/Max", 15, "Item only", 44, "Percent", "Armor HUD");
            this.addMToggle(r, "Direction F Layout", new GuiModSettings.SettingsButton[] {new GuiModSettings.SettingsButton(19, "Number", ConfigManager.settings.fLayoutNumber), new GuiModSettings.SettingsButton(20, "Cardinal Direction", ConfigManager.settings.fLayoutDirection), new GuiModSettings.SettingsButton(21, "X and Z", ConfigManager.settings.fLayoutXAndZ)}, "Shows the F Direction");
            this.addMToggle(r, "Direction HUD", new GuiModSettings.SettingsButton[] {new GuiModSettings.SettingsButton(2, "F Coordinate", ConfigManager.settings.radarCoordinate), new GuiModSettings.SettingsButton(3, "Cardinal Direction", ConfigManager.settings.radarDirection)}, "Shows the current direction you are facing at (F Coordinate) in the top of the screen");
            this.addSliderSize(r, "Mod Scale");
            this.addToggleS(r, ConfigManager.settings.potionsize, 50, "Potion size");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.guiPositionRight), 59, "Gui position", "Left", "Right", "Change the position of all important informations");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.onlineFriendsPositionOnTop), 60, "Online Friends position", "Bottom", "Top", "Change the position of the ingame friendlist");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.armorHudPositionOnTop), 86, "Armor HUD position", "Hotbar", "Top", "Change the position of the armor hud");
            this.draw.drawString(Color.c(4) + "Preview:", 108.0D, 5.0D);
            this.draw.drawString(ModGui.createLabel("X", ModGui.getX()), 108.0D, 15.0D);
        }

        if (this.currentTab.equals("Extras"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.box), 7, "HG Box", "All game information from Brawl Hardcore Games in an information box");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.clickTest), 90, "Clicktest", "Test your clickspeed");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.lavaTime), 35, "Lavachallenge Timer", "It starts a Timer as you touch lava");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showMyName), 55, "Show my Name", "It will displays your own ingame name above your Head");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.capes), 62, "Capes", "Enable/Disable all Capes");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.foodSaturation), 52, "Food saturation", "It displays how long you are saturated");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.smoothFPS), 48, "Smooth FPS", "The FPS display will be updated more often");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.afkTimer), 56, "AFK Timer", "Its starts a timer once you are AFK");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.tabPing), 45, "Ping on Tab", "You can see the ping of every player on the tablist");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showOnlineFriends), 57, "Show Online Friends", "Shows the player head of all online friends in the top or bottom of the screen");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.speedFOV), 71, "SpeedFOV", "If this option is set to OFF, the speed potion effect will no longer increase your FOV.");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.leftHand), 87, "Main hand", "Right", "Left", "Swap the position of your main hand");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.leftBow), 92, "Swap bow", "Swap the position of your hand if you holding a bow");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.spotfiyTrack), 96, "Spotify Track", "Shows you your current playing spotify track");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.cosmetics), 98, "Cosmetics", "Shows cosmetics like wings, hats..");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.hiveAutoScramble), 101, "Hive autoscramble", "Automaticly run the command \'/scramble\' if you join a SG Server");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.api), 105, "LabyMod API", "This option enable/disable all addons (ToggleSprint, DamageIndicator, ItemPhysics..)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.controller), 108, "Controller BETA", "Play minecraft with your controller (Restart Minecraft and install a driver for your controller - Tested on XBOX 360 CONTROLLER + Windows 10)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.mineconParticle), 114, "Minecon Particle", "All original Minecon capes have some sparkling particles (Restart required)");
        }

        if (this.currentTab.equals("Server Support"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gameBrawl), 39, "Brawl", "Shows the game information about Brawl");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gameTimolia), 16, "Timolia", "Shows the game information about Timolia");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gamePlayMinity), 17, "PlayMinity", "Shows the game information about PlayMinity");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gameGommeHD), 18, "GommeHD", "Shows the game information about GommeHD");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gameHiveMC), 74, "HiveMC", "Shows the game information about HiveMC");
        }

        if (this.currentTab.equals("GommeHD.net"))
        {
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.gommePosLeft), 73, "BedWars Timer", "Right", "Left", "Change the position of the GommeHD Gold cooldown");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.autoLeave), 80, "Auto Leave", "If your party doesn\'t find a team in bedwars you will automatically connect to the hub.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showBWTimer), 75, "BedWars Timer", "Indicates the time until the next gold/iron will spawn at BedWars");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.showNickname), 23, "Show Nickname", "Displays your nickname of the youtuber-nick plugin (on GommeHD.net).");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.skyblock), 109, "Show Skyblock Owner", "Displays the island owner in Skyblock (on GommeHD for example).");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gommeBedTimer), 112, "Bed Percentage", "Displays the block break percentage of the bed in bedwars (GommeHD)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.gommeBeaconTimer), 72, "Core Percentage", "Displays the block break percentage of the beacon in cores (GommeHD)");
        }

        if (this.currentTab.equals("Menu"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.smoothScroll), 64, "Smooth scrolling", "Reduces the scroll-speed for the Serverlist");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.background), 82, "Gui background", "This option allows you to enable and disable the background of the GUI");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.betterRefresh), 49, "Better Refresh", "Adds an auto refresh button in the multiplayer gui");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.confirmDisconnect), 61, "Confirm Disconnect", "You have to confirm with pressing the button again if you want to disconnect the server");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.infoInMenu), 47, "Serverstatus in Menu", "Shows you all server information in the ESCAPE menu (Server icon, motd, slots, online players, ping)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.directConnectInfo), 54, "Directconnect Serverstatus", "Shows you all important server information in the direct connent gui (online players, slots, ping)");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.tags), 68, "Tags", "This option allows you to change the names of your friends and costumize them using colorcodes. The nicknames can be seen in the tablist, over their heads and in chat.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.miniGames), 36, "Minigames", "Minigames in Minecraft");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.quickPlay), 84, "QuickPlay", "Shows in the main menu the latest server and your directconnect server");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.stopWatch), 85, "Stopwatch", "A simple stopwatch in Minecraft. It lets you measure the time, which will also be displayed in the GUI.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.mojangStatus), 94, "Mojang Status", "Displays the current offline servers of Mojang in your server list");
        }

        if (this.currentTab.equals("TeamSpeak"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.teamSpeak), 65, "TeamSpeak", "If you run TeamSpeak you\'re able to accses it using Minecraft, you\'ll be able to see channels, join them, etc.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.alertsTeamSpeak), 4, "TS Alerts", "Shows a message from TeamSpeak as an achievement or chat message");
            this.addToggleSub(r, Boolean.valueOf(ConfigManager.settings.teamSpakIngameClients), Boolean.valueOf(ConfigManager.settings.teamSpeak), 70, "TS Clients", "Showing you who is in the channel and who is currently talking.");
            this.addToggleSub(r, Boolean.valueOf(ConfigManager.settings.teamSpakIngame), Boolean.valueOf(ConfigManager.settings.teamSpeak), 69, "TS Channel", "If this option is on, the channel you\'re currently in will be displayed below your coordinates.");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.teamSpeakAlertTypeChat), 88, "Alert type", "Achievement", "Chat", "With this function you can choose the type of the Teamspeak message. Either as Achievement or in the normal chat.");
            this.addSliderColor(r, "TS Silent", 6);
            this.addSliderColor(r, "TS Talking", 7);
            this.addSliderColor(r, "TS Away", 10);
            this.addSliderColor(r, "TS Micro muted", 8);
            this.addSliderColor(r, "TS Sound muted", 9);
        }

        if (this.currentTab.equals("Minecraft Chat"))
        {
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.chatFilter), 78, "Chat Filter", "You can filter your chat by certain keywords, or commands definded at \"filters\"");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.autoText), 77, "AutoText", "Your previously defined chat messages will be sended by pressing a single key");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.extraChat), 38, "Extra Chat", "You can see all private messages in an extra chat on the right side of the screen");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.chatAlertType), 89, "LabyMod Chat Notify Type", "Achievement", "Chat", "With this function you can choose the type of the LabyMod Chat message. Either as Achievement or in the normal chat.");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.chatPositionRight), 91, "Chat Position", "Right", "Left", "The Position of the Minecraft Chat");
            this.addToggleCustomNC(r, Boolean.valueOf(ConfigManager.settings.mojangStatusChat), 95, "Mojang Status type", "Achievement", "Chat", "With this function you can choose the type of the Mojang Status message. Either as Achievement or in the normal chat.");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.nameHistory), 111, "Minecraft Name History", "Get the full name history of a player in an ingame GUI!");
            this.addToggle(r, Boolean.valueOf(ConfigManager.settings.fastChat), 113, "Fast Chat", "Transparent chat GUI!");
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == -1)
        {
            this.currentTab = button.displayString;

            if (button.id != -3)
            {
                this.initGui();
            }
        }
        else
        {
            switch (button.id)
            {
                case 0:
                    ConfigManager.settings.alertsChat = !ConfigManager.settings.alertsChat;
                    break;

                case 1:
                    this.mc.displayGuiScreen(this.field_146441_g);
                    break;

                case 2:
                    ConfigManager.settings.radarCoordinate = !ConfigManager.settings.radarCoordinate;
                    break;

                case 3:
                    ConfigManager.settings.radarDirection = !ConfigManager.settings.radarDirection;
                    break;

                case 4:
                    ConfigManager.settings.alertsTeamSpeak = !ConfigManager.settings.alertsTeamSpeak;
                    break;

                case 5:
                    ConfigManager.settings.showOnlinePlayers = !ConfigManager.settings.showOnlinePlayers;
                    break;

                case 6:
                    ConfigManager.settings.potionEffects = !ConfigManager.settings.potionEffects;
                    break;

                case 7:
                    ConfigManager.settings.box = !ConfigManager.settings.box;
                    break;

                case 8:
                    ConfigManager.settings.layout = 0;
                    break;

                case 9:
                    ConfigManager.settings.layout = 1;
                    break;

                case 10:
                    ConfigManager.settings.layout = 2;
                    break;

                case 11:
                    ConfigManager.settings.layout = 3;
                    break;

                case 12:
                    ConfigManager.settings.hud = 0;
                    break;

                case 13:
                    ConfigManager.settings.hud = 1;
                    break;

                case 14:
                    ConfigManager.settings.hud = 2;
                    break;

                case 15:
                    ConfigManager.settings.hud = 3;
                    break;

                case 16:
                    ConfigManager.settings.gameTimolia = !ConfigManager.settings.gameTimolia;
                    break;

                case 17:
                    ConfigManager.settings.gamePlayMinity = !ConfigManager.settings.gamePlayMinity;
                    break;

                case 18:
                    ConfigManager.settings.gameGommeHD = !ConfigManager.settings.gameGommeHD;
                    break;

                case 19:
                    ConfigManager.settings.fLayoutNumber = !ConfigManager.settings.fLayoutNumber;
                    break;

                case 20:
                    ConfigManager.settings.fLayoutDirection = !ConfigManager.settings.fLayoutDirection;
                    break;

                case 21:
                    ConfigManager.settings.fLayoutXAndZ = !ConfigManager.settings.fLayoutXAndZ;
                    break;

                case 22:
                    ConfigManager.settings.showCoords = !ConfigManager.settings.showCoords;
                    break;

                case 23:
                    ConfigManager.settings.showNickname = !ConfigManager.settings.showNickname;
                    break;

                case 24:
                    int j1 = Color.colorToID(ConfigManager.settings.color1);
                    j1 = this.manageInt(j1);
                    ConfigManager.settings.color1 = Color.IDToColor(j1);
                    this.initGui();
                    break;

                case 25:
                    int i1 = Color.colorToID(ConfigManager.settings.color2);
                    i1 = this.manageInt(i1);
                    ConfigManager.settings.color2 = Color.IDToColor(i1);
                    this.initGui();
                    break;

                case 26:
                    int l = Color.colorToID(ConfigManager.settings.color3);
                    l = this.manageInt(l);
                    ConfigManager.settings.color3 = Color.IDToColor(l);
                    this.initGui();
                    break;

                case 27:
                    int k = Color.colorToID(ConfigManager.settings.color4);
                    k = this.manageInt(k);
                    ConfigManager.settings.color4 = Color.IDToColor(k);
                    this.initGui();
                    break;

                case 28:
                    int j = Color.colorToID(ConfigManager.settings.color5);
                    j = this.manageInt(j);
                    ConfigManager.settings.color5 = Color.IDToColor(j);
                    this.initGui();
                    break;

                case 29:
                    ConfigManager.settings.bold = !ConfigManager.settings.bold;
                    break;

                case 30:
                    ConfigManager.settings.underline = !ConfigManager.settings.underline;
                    break;

                case 31:
                    ConfigManager.settings.italic = !ConfigManager.settings.italic;
                    break;

                case 32:
                    ConfigManager.settings.oldDMG = !ConfigManager.settings.oldDMG;
                    break;

                case 33:
                    ConfigManager.settings.showPing = !ConfigManager.settings.showPing;
                    break;

                case 34:
                    ConfigManager.settings.showServerIP = !ConfigManager.settings.showServerIP;
                    break;

                case 35:
                    ConfigManager.settings.lavaTime = !ConfigManager.settings.lavaTime;
                    break;

                case 36:
                    ConfigManager.settings.miniGames = !ConfigManager.settings.miniGames;
                    break;

                case 37:
                    ConfigManager.settings.showFPS = !ConfigManager.settings.showFPS;
                    break;

                case 38:
                    ConfigManager.settings.extraChat = !ConfigManager.settings.extraChat;
                    break;

                case 39:
                    ConfigManager.settings.gameBrawl = !ConfigManager.settings.gameBrawl;
                    break;

                case 40:
                    ConfigManager.settings.oldHearts = !ConfigManager.settings.oldHearts;
                    break;

                case 41:
                    ConfigManager.settings.oldBow = !ConfigManager.settings.oldBow;
                    break;

                case 42:
                    ConfigManager.settings.oldSword = !ConfigManager.settings.oldSword;
                    break;

                case 43:
                    ConfigManager.settings.oldBlockhit = !ConfigManager.settings.oldBlockhit;
                    break;

                case 44:
                    ConfigManager.settings.hud = 4;
                    break;

                case 45:
                    ConfigManager.settings.tabPing = !ConfigManager.settings.tabPing;
                    break;

                case 46:
                    ConfigManager.settings.oldFishing = !ConfigManager.settings.oldFishing;
                    break;

                case 47:
                    ConfigManager.settings.infoInMenu = !ConfigManager.settings.infoInMenu;
                    break;

                case 48:
                    ConfigManager.settings.smoothFPS = !ConfigManager.settings.smoothFPS;
                    break;

                case 49:
                    ConfigManager.settings.betterRefresh = !ConfigManager.settings.betterRefresh;
                    break;

                case 50:
                    int i = ConfigManager.settings.potionsize;
                    i = this.manageInt2(i);
                    ConfigManager.settings.potionsize = i;
                    break;

                case 51:
                    ConfigManager.settings.showKills = !ConfigManager.settings.showKills;
                    break;

                case 52:
                    ConfigManager.settings.foodSaturation = !ConfigManager.settings.foodSaturation;
                    break;

                case 53:
                    ConfigManager.settings.armorRating = !ConfigManager.settings.armorRating;
                    break;

                case 54:
                    ConfigManager.settings.directConnectInfo = !ConfigManager.settings.directConnectInfo;
                    break;

                case 55:
                    ConfigManager.settings.showMyName = !ConfigManager.settings.showMyName;
                    break;

                case 56:
                    ConfigManager.settings.afkTimer = !ConfigManager.settings.afkTimer;
                    break;

                case 57:
                    ConfigManager.settings.showOnlineFriends = !ConfigManager.settings.showOnlineFriends;
                    break;

                case 58:
                    ConfigManager.settings.oldTablist = !ConfigManager.settings.oldTablist;
                    break;

                case 59:
                    ConfigManager.settings.guiPositionRight = !ConfigManager.settings.guiPositionRight;
                    break;

                case 60:
                    ConfigManager.settings.onlineFriendsPositionOnTop = !ConfigManager.settings.onlineFriendsPositionOnTop;
                    break;

                case 61:
                    ConfigManager.settings.confirmDisconnect = !ConfigManager.settings.confirmDisconnect;
                    break;

                case 62:
                    ConfigManager.settings.capes = !ConfigManager.settings.capes;
                    LabyMod.getInstance().getCapeManager().refresh();
                    break;

                case 63:
                    ConfigManager.settings.showBossBar = !ConfigManager.settings.showBossBar;
                    break;

                case 64:
                    ConfigManager.settings.smoothScroll = !ConfigManager.settings.smoothScroll;
                    break;

                case 65:
                    ConfigManager.settings.teamSpeak = !ConfigManager.settings.teamSpeak;

                    if (!ConfigManager.settings.teamSpeak)
                    {
                        ConfigManager.settings.teamSpakIngame = false;
                        ConfigManager.settings.teamSpakIngameClients = false;
                        TeamSpeakController.getInstance().quit();
                    }
                    else
                    {
                        TeamSpeak.enable();
                    }

                case 66:
                case 99:
                case 100:
                case 103:
                default:
                    break;

                case 67:
                    ConfigManager.settings.brawl = !ConfigManager.settings.brawl;
                    break;

                case 68:
                    ConfigManager.settings.tags = !ConfigManager.settings.tags;
                    break;

                case 69:
                    ConfigManager.settings.teamSpakIngame = !ConfigManager.settings.teamSpakIngame;
                    break;

                case 70:
                    ConfigManager.settings.teamSpakIngameClients = !ConfigManager.settings.teamSpakIngameClients;
                    break;

                case 71:
                    ConfigManager.settings.speedFOV = !ConfigManager.settings.speedFOV;
                    break;

                case 72:
                    ConfigManager.settings.gommeBeaconTimer = !ConfigManager.settings.gommeBeaconTimer;
                    break;

                case 73:
                    ConfigManager.settings.gommePosLeft = !ConfigManager.settings.gommePosLeft;
                    break;

                case 74:
                    ConfigManager.settings.gameHiveMC = !ConfigManager.settings.gameHiveMC;
                    break;

                case 75:
                    ConfigManager.settings.showBWTimer = !ConfigManager.settings.showBWTimer;
                    break;

                case 76:
                    ConfigManager.settings.showBWTeams = !ConfigManager.settings.showBWTeams;
                    break;

                case 77:
                    ConfigManager.settings.autoText = !ConfigManager.settings.autoText;
                    break;

                case 78:
                    ConfigManager.settings.chatFilter = !ConfigManager.settings.chatFilter;
                    break;

                case 79:
                    ConfigManager.settings.oldSneak = !ConfigManager.settings.oldSneak;
                    break;

                case 80:
                    ConfigManager.settings.autoLeave = !ConfigManager.settings.autoLeave;
                    break;

                case 81:
                    ConfigManager.settings.oldBlockBuild = !ConfigManager.settings.oldBlockBuild;
                    break;

                case 82:
                    ConfigManager.settings.background = !ConfigManager.settings.background;
                    break;

                case 83:
                    ConfigManager.settings.showClock = !ConfigManager.settings.showClock;
                    break;

                case 84:
                    ConfigManager.settings.quickPlay = !ConfigManager.settings.quickPlay;
                    break;

                case 85:
                    ConfigManager.settings.stopWatch = !ConfigManager.settings.stopWatch;
                    break;

                case 86:
                    ConfigManager.settings.armorHudPositionOnTop = !ConfigManager.settings.armorHudPositionOnTop;
                    break;

                case 87:
                    ConfigManager.settings.leftHand = !ConfigManager.settings.leftHand;
                    break;

                case 88:
                    ConfigManager.settings.teamSpeakAlertTypeChat = !ConfigManager.settings.teamSpeakAlertTypeChat;
                    break;

                case 89:
                    ConfigManager.settings.chatAlertType = !ConfigManager.settings.chatAlertType;
                    break;

                case 90:
                    ConfigManager.settings.clickTest = !ConfigManager.settings.clickTest;
                    break;

                case 91:
                    ConfigManager.settings.chatPositionRight = !ConfigManager.settings.chatPositionRight;
                    break;

                case 92:
                    ConfigManager.settings.leftBow = !ConfigManager.settings.leftBow;
                    break;

                case 93:
                    ConfigManager.settings.showArrow = !ConfigManager.settings.showArrow;
                    break;

                case 94:
                    ConfigManager.settings.mojangStatus = !ConfigManager.settings.mojangStatus;
                    break;

                case 95:
                    ConfigManager.settings.mojangStatusChat = !ConfigManager.settings.mojangStatusChat;
                    break;

                case 96:
                    ConfigManager.settings.spotfiyTrack = !ConfigManager.settings.spotfiyTrack;
                    break;

                case 97:
                    ConfigManager.settings.showBiome = !ConfigManager.settings.showBiome;
                    break;

                case 98:
                    ConfigManager.settings.cosmetics = !ConfigManager.settings.cosmetics;
                    break;

                case 101:
                    ConfigManager.settings.hiveAutoScramble = !ConfigManager.settings.hiveAutoScramble;
                    break;

                case 102:
                    ConfigManager.settings.oldInventory = !ConfigManager.settings.oldInventory;
                    break;

                case 104:
                    ConfigManager.settings.twelveHourClock = !ConfigManager.settings.twelveHourClock;
                    break;

                case 105:
                    ConfigManager.settings.api = !ConfigManager.settings.api;
                    break;

                case 106:
                    ConfigManager.settings.showDate = !ConfigManager.settings.showDate;
                    break;

                case 107:
                    ConfigManager.settings.showLiveTicker = !ConfigManager.settings.showLiveTicker;
                    break;

                case 108:
                    ConfigManager.settings.controller = !ConfigManager.settings.controller;

                    if (ConfigManager.settings.controller)
                    {
                        ControllerInput.init();
                    }
                    else
                    {
                        ControllerInput.exit();
                    }

                    break;

                case 109:
                    ConfigManager.settings.skyblock = !ConfigManager.settings.skyblock;
                    break;

                case 110:
                    ConfigManager.settings.memory = !ConfigManager.settings.memory;
                    break;

                case 111:
                    ConfigManager.settings.nameHistory = !ConfigManager.settings.nameHistory;
                    break;

                case 112:
                    ConfigManager.settings.gommeBedTimer = !ConfigManager.settings.gommeBedTimer;
                    break;

                case 113:
                    ConfigManager.settings.fastChat = !ConfigManager.settings.fastChat;
                    break;

                case 114:
                    ConfigManager.settings.mineconParticle = !ConfigManager.settings.mineconParticle;
            }

            if (button.id != -3)
            {
                this.initGui();
            }

            ConfigManager.save();
        }
    }

    public void drawPreview()
    {
        int i = this.height - 10;

        if (ConfigManager.settings.layout != 0)
        {
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
                this.draw.addNoScaleLabel("F", s, i);
                i -= 11;
            }

            if (ConfigManager.settings.showCoords)
            {
                this.draw.addNoScaleLabel("Z", ModGui.getZ(), i);
                i = i - 11;
                this.draw.addNoScaleLabel("Y", ModGui.getY(), i);
                i = i - 11;
                this.draw.addNoScaleLabel("Z", ModGui.getZ(), i);
                i = i - 11;
            }

            if (ConfigManager.settings.showFPS)
            {
                this.draw.addNoScaleLabel("FPS", ModGui.getFPS() + "", i);
                i -= 11;
            }
        }

        if (i == this.height - 10)
        {
            this.draw.addNoScaleString(Color.cl("c") + Color.cl("o") + "Gui disabled!", i);
            i -= 11;
        }

        this.draw.addNoScaleString(Color.c(4) + "Preview:", i);
    }

    public boolean toggle(boolean c)
    {
        return !c;
    }

    public int manageInt(int i)
    {
        if (i >= 15)
        {
            i = -1;
        }

        ++i;
        return i;
    }

    public int manageInt2(int i)
    {
        if (i >= 2)
        {
            i = -1;
        }

        ++i;
        return i;
    }

    private void addSliderSize(boolean refresh, String title)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            SliderSize slidersize = new SliderSize(-3, this.x, this.y, 77);
            this.buttonList.add(slidersize);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    private void addSliderCoords(boolean refresh, String title)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            SliderCoords slidercoords = new SliderCoords(this.x, this.y, 75);
            this.buttonList.add(slidercoords);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    private void addSliderColor(boolean refresh, String title, int color)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            SliderColor slidercolor = new SliderColor(-3, this.x, this.y, 80, color);
            this.buttonList.add(slidercolor);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    public void addToggle(boolean refresh, Boolean t, int id, String title, String info)
    {
        this.addToggleCustom(refresh, t, id, title, "OFF", "ON", info);
    }

    public void addToggleSub(boolean refresh, Boolean t, Boolean sub, int id, String title, String info)
    {
        this.addToggleCustomSub(refresh, t, sub, id, title, "OFF", "ON", info);
    }

    private void addToggleA(boolean r, Boolean old, int i, String title, String info)
    {
        this.addToggleCustom(r, old, i, title, "1.8 Animation", "1.7 Animation", info);
    }

    public void addToggleCustom(boolean refresh, Boolean t, int id, String title, String disabled, String enabled, String info)
    {
        if (refresh)
        {
            if (title.length() > 25)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.55D);
            }
            else if (title.length() > 22)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.65D);
            }
            else if (title.length() > 20)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 9), 0.6D);
            }
            else if (title.length() > 16)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.7D);
            }
            else if (title.length() > 15)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.8D);
            }
            else
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            }

            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            String s = Color.cl("c") + disabled;

            if (t.booleanValue())
            {
                s = Color.cl("a") + enabled;
            }

            GuiCustomButton guicustombutton = new GuiCustomButton(id, this.x, this.y, 80, 20, s);
            guicustombutton.info = info;
            this.buttonList.add(guicustombutton);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    public void addToggleCustomNC(boolean refresh, Boolean t, int id, String title, String disabled, String enabled, String info)
    {
        if (refresh)
        {
            if (title.length() > 25)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.55D);
            }
            else if (title.length() > 22)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.65D);
            }
            else if (title.length() > 20)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 9), 0.6D);
            }
            else if (title.length() > 16)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.7D);
            }
            else if (title.length() > 15)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.8D);
            }
            else
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            }

            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            String s = disabled;

            if (t.booleanValue())
            {
                s = enabled;
            }

            GuiCustomButton guicustombutton = new GuiCustomButton(id, this.x, this.y, 80, 20, s);
            guicustombutton.info = info;
            this.buttonList.add(guicustombutton);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    public void addToggleCustomSub(boolean refresh, Boolean t, Boolean sub, int id, String title, String disabled, String enabled, String info)
    {
        if (refresh)
        {
            if (title.length() > 25)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.55D);
            }
            else if (title.length() > 22)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 8), 0.65D);
            }
            else if (title.length() > 20)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 9), 0.6D);
            }
            else if (title.length() > 16)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.7D);
            }
            else if (title.length() > 15)
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 10), 0.8D);
            }
            else
            {
                this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            }

            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            String s = Color.cl("c") + disabled;

            if (t.booleanValue())
            {
                s = Color.cl("a") + enabled;
            }

            GuiCustomButton guicustombutton = new GuiCustomButton(id, this.x, this.y, 80, 20, s);
            guicustombutton.info = info;
            guicustombutton.enabled = sub.booleanValue();
            this.buttonList.add(guicustombutton);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    public void addToggleS(boolean refresh, int t, int id, String title)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
        else
        {
            String s = "Small";

            if (t == 1)
            {
                s = "Normal";
            }
            else if (t == 2)
            {
                s = "Large";
            }

            GuiButton guibutton = new GuiButton(id, this.x, this.y, 80, 20, s);
            this.buttonList.add(guibutton);
            this.x += 100;

            if (this.x > this.width - 110)
            {
                this.x = 120;
                this.y += this.sy;
            }
        }
    }

    public void addSwitch(boolean refresh, int s, int id, int id2, String Title1, int id3, String Title2, int id4, String Title3, int id5, String Title4, String title)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x = 120;
            this.y += this.sy;
        }
        else
        {
            int i = 0;

            if (id2 != -1)
            {
                ++i;
            }

            if (id3 != -1)
            {
                ++i;
            }

            if (id4 != -1)
            {
                ++i;
            }

            if (id5 != -1)
            {
                ++i;
            }

            int j = 0;

            for (int k = 0; k <= i; ++k)
            {
                int l = id;
                String s1 = "Layout " + k;

                if (k == 1)
                {
                    s1 = Title1;
                    l = id2;
                }

                if (k == 2)
                {
                    s1 = Title2;
                    l = id3;
                }

                if (k == 3)
                {
                    s1 = Title3;
                    l = id4;
                }

                if (k == 4)
                {
                    s1 = Title4;
                    l = id5;
                }

                int i1 = 50;
                int j1 = -20;

                if (k == 0)
                {
                    s1 = Color.cl("c") + "OFF";
                    i1 = 30;
                    j1 = 0;
                }

                GuiButton guibutton = new GuiButton(l, this.x + j, this.y, this.draw.getStringWidth(s1) + 13, 20, s1);

                if (s == k)
                {
                    guibutton.enabled = false;
                }

                this.buttonList.add(guibutton);
                j += this.draw.getStringWidth(s1) + 14;
            }

            this.x = 120;
            this.y += this.sy;
        }
    }

    public void addSwitchNoOff(boolean refresh, int s, int id, int id2, String Title1, int id3, String Title2, int id4, String Title3, String title)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x = 120;
            this.y += this.sy;
        }
        else
        {
            int i = 0;

            if (id2 != -1)
            {
                ++i;
            }

            if (id3 != -1)
            {
                ++i;
            }

            if (id4 != -1)
            {
                ++i;
            }

            int j = 0;

            for (int k = 1; k <= i; ++k)
            {
                String s1 = "Layout " + k;

                if (k == 1)
                {
                    s1 = Title1;
                }

                if (k == 2)
                {
                    s1 = Title2;
                }

                if (k == 3)
                {
                    s1 = Title3;
                }

                int l = 50;
                int i1 = -20;
                GuiButton guibutton = new GuiButton(id + k, this.x + j, this.y, this.draw.getStringWidth(s1) + 13, 20, s1);

                if (s == k)
                {
                    guibutton.enabled = false;
                }

                this.buttonList.add(guibutton);
                j += this.draw.getStringWidth(s1) + 14;
            }

            this.x = 120;
            this.y += this.sy;
        }
    }

    public void addMToggle(boolean refresh, String title, GuiModSettings.SettingsButton[] button, String info)
    {
        if (refresh)
        {
            this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
            this.x = 120;
            this.y += this.sy;
        }
        else
        {
            int i = 0;

            for (int j = 0; j < button.length; ++j)
            {
                GuiModSettings.SettingsButton guimodsettings$settingsbutton = button[j];
                String s = Color.cl("c");

                if (guimodsettings$settingsbutton.enabled)
                {
                    s = Color.cl("a");
                }

                GuiCustomButton guicustombutton = new GuiCustomButton(guimodsettings$settingsbutton.id, this.x + i, this.y, this.draw.getStringWidth(guimodsettings$settingsbutton.text) + 8, 20, s + guimodsettings$settingsbutton.text);
                guicustombutton.info = info;
                this.buttonList.add(guicustombutton);
                i += this.draw.getStringWidth(guimodsettings$settingsbutton.text) + 10;
            }

            this.x = 120;
            this.y += this.sy;
        }
    }

    public void addTitle(String title)
    {
        this.y += 42;
        this.draw.drawString(title + ":", (double)(this.x + 1), (double)(this.y - 12));
    }

    public void addColorSwitcher(String color, int id, String title, int plus)
    {
        GuiButton guibutton = new GuiButton(id, this.x + plus, this.y, this.draw.getStringWidth(title) + 10, 20, color + title);
        this.buttonList.add(guibutton);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(this.field_146441_g);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (LabyMod.getInstance().isInGame())
        {
            GlStateManager.enableBlend();
            this.draw.drawTransparentBackground(105, 28, this.width - 10, this.height - 10);
        }
        else
        {
            this.drawDefaultBackground();
            this.draw.drawChatBackground(105, 28, this.width - 10, this.height - 10);
        }

        this.draw.overlayBackground(0, 0, 105, this.height);
        this.draw.overlayBackground(0, 0, this.width, 28);
        this.draw.overlayBackground(this.width - 10, 0, this.width, this.height);
        DrawUtils drawutils = this.draw;
        DrawUtils.drawRect(103, 0, 104, this.height, Integer.MAX_VALUE);
        this.drawModInfo();
        this.initSettings(true);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.draw.overlayBackground(105, this.height - 10, this.width, this.height);

        for (GuiButton guibutton : this.buttonList)
        {
            if (guibutton instanceof GuiCustomButton)
            {
                ((GuiCustomButton)guibutton).drawInfoBox();
            }
        }
    }

    public void drawModInfo()
    {
        this.draw.drawCenteredString(this.currentTab, (this.width + 105) / 2, 10);
        this.draw.drawCenteredString("LabyMod Settings", 51, 10);
        this.draw.drawRightString(Color.cc(4) + "LabyMod" + " v" + "2.8.05", (double)(this.width - 10), 10.0D);
    }

    public void addTab(String title)
    {
        GuiButton guibutton = new GuiButton(-1, 2, this.tabY, 99, 20, title);
        guibutton.enabled = !title.equals(this.currentTab);
        this.buttonList.add(guibutton);
        this.tabY += this.ey;
    }

    class SettingsButton
    {
        boolean enabled;
        String text;
        int id;

        public SettingsButton(int id, String text, boolean enabled)
        {
            this.id = id;
            this.text = text;
            this.enabled = enabled;
        }
    }
}
