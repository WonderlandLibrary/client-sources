/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import ru.govno.client.Client;
import ru.govno.client.cfg.GuiConfig;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.clickgui.Panel;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientTune;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Render.AnimationUtils;

public class ClickGui
extends Module {
    public static ClickGui instance;
    public static AnimationUtils categoryColorFactor;
    public static Settings Images;
    public static Settings Image;
    public static Settings Gradient;
    public static Settings GradientAlpha;
    public static Settings BlurBackground;
    public static Settings BlurStrengh;
    public static Settings Descriptions;
    public static Settings CustomCursor;
    public static Settings Darkness;
    public static Settings DarkOpacity;
    public static Settings Particles;
    public static Settings Epilepsy;
    public static Settings CategoryColor;
    public static Settings MusicInGui;
    public static Settings Song;
    public static Settings MusicVolume;
    public static Settings ScanLinesOverlay;
    public static Settings ScreenBounds;
    public static Settings SaveMusic;
    boolean doForceMusicChange = true;
    int savedScale = -1;

    public ClickGui() {
        super("ClickGui", 29, Module.Category.RENDER);
        Settings set;
        instance = this;
        int x = 80;
        for (int i = 0; i < 5; ++i) {
            this.settings.add(new Settings("P" + i + "X", x, 10000.0f, -10000.0f, this, () -> false));
            this.settings.add(new Settings("P" + i + "Y", 20.0f, 10000.0f, -10000.0f, this, () -> false));
            x += 135;
        }
        Images = new Settings("Images", false, (Module)this);
        this.settings.add(Images);
        Image = new Settings("Image", "Sage", this, new String[]{"Nolik", "Succubbus", "SuccubbusHot", "AstolfoHot", "Furry", "Lake", "Kiskis", "IceGirl", "LoliGirl", "LoliGirl2", "PandaPo", "Sage", "SonicGenerations", "SonicMovie"}, () -> Images.getBool());
        this.settings.add(Image);
        Gradient = new Settings("Gradient", true, (Module)this);
        this.settings.add(Gradient);
        GradientAlpha = new Settings("GradientAlpha", 100.0f, 255.0f, 0.0f, this, () -> Gradient.getBool());
        this.settings.add(GradientAlpha);
        BlurBackground = new Settings("BlurBackground", true, (Module)this);
        this.settings.add(BlurBackground);
        BlurStrengh = new Settings("BlurStrengh", 1.0f, 2.0f, 0.25f, this, () -> BlurBackground.getBool());
        this.settings.add(BlurStrengh);
        Descriptions = new Settings("Descriptions", true, (Module)this);
        this.settings.add(Descriptions);
        CustomCursor = new Settings("CustomCursor", false, (Module)this);
        this.settings.add(CustomCursor);
        Darkness = new Settings("Darkness", false, (Module)this);
        this.settings.add(Darkness);
        DarkOpacity = new Settings("DarkOpacity", 170.0f, 255.0f, 0.0f, this, () -> Darkness.getBool());
        this.settings.add(DarkOpacity);
        Particles = new Settings("Particles", false, (Module)this);
        this.settings.add(Particles);
        Epilepsy = new Settings("Epilepsy", false, (Module)this);
        this.settings.add(Epilepsy);
        CategoryColor = set = new Settings("CategoryColor", false, (Module)this);
        this.settings.add(set);
        ClickGui.categoryColorFactor.to = set.bValue ? 1.0f : 0.0f;
        categoryColorFactor.setAnim(set.bValue ? 1.0f : 0.0f);
        MusicInGui = new Settings("MusicInGui", true, (Module)this);
        this.settings.add(MusicInGui);
        Song = new Settings("Song", "Ost-SF-9", this, new String[]{"Ost-SF-1", "Ost-SF-2", "Ost-SF-3", "Ost-SF-4", "Ost-SF-5", "Ost-SF-6", "Ost-SF-7", "Ost-SF-8", "Ost-SF-9", "Ost-SF-10", "Ost-SF-11", "Ost-SF-12", "Ost-SF-13", "Ost-SF-14", "Ost-SF-15", "Ost-SF-16"}, () -> MusicInGui.getBool());
        this.settings.add(Song);
        MusicVolume = new Settings("MusicVolume", 50.0f, 200.0f, 1.0f, this, () -> MusicInGui.getBool());
        this.settings.add(MusicVolume);
        ScanLinesOverlay = new Settings("ScanLinesOverlay", true, (Module)this);
        this.settings.add(ScanLinesOverlay);
        ScreenBounds = new Settings("ScreenBounds", true, (Module)this);
        this.settings.add(ScreenBounds);
        SaveMusic = new Settings("SaveMusic", false, (Module)this, () -> false);
        this.settings.add(SaveMusic);
    }

    public static float[] getPositionPanel(Panel curPanel) {
        float X = 0.0f;
        float Y = 0.0f;
        int i = 0;
        for (Panel panel : Client.clickGuiScreen.panels) {
            if (panel == curPanel) {
                X = instance.currentFloatValue("P" + i + "X");
                Y = instance.currentFloatValue("P" + i + "Y");
            }
            ++i;
        }
        return new float[]{X, Y};
    }

    public static void setPositionPanel(Panel curPanel, float x, float y) {
        int i = 0;
        for (Panel panel : Client.clickGuiScreen.panels) {
            if (panel == curPanel) {
                ((Settings)ClickGui.instance.settings.get((int)Integer.valueOf((int)i).intValue())).fValue = x;
                ((Settings)ClickGui.instance.settings.get((int)Integer.valueOf((int)(i + 1)).intValue())).fValue = y;
            }
            i += 2;
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (this.savedScale == -1 && ClickGui.mc.gameSettings.guiScale != -1) {
            this.savedScale = ClickGui.mc.gameSettings.guiScale;
        }
        boolean playMusic = (actived || ClickGui.SaveMusic.bValue) && MusicInGui.getBool();
        Client.clickGuiMusic.setPlaying(playMusic);
        if (actived) {
            this.savedScale = ClickGui.mc.gameSettings.guiScale;
            ClickGui.mc.gameSettings.guiScale = 2;
            if (Client.clickGuiScreen != null) {
                mc.displayGuiScreen(Client.clickGuiScreen);
            }
            int i = 0;
            for (Panel panel : Client.clickGuiScreen.panels) {
                panel.X = instance.currentFloatValue("P" + i + "X");
                panel.Y = instance.currentFloatValue("P" + i + "Y");
                panel.posX.to = panel.X;
                panel.posY.to = panel.Y;
                panel.posX.setAnim(panel.posX.to);
                panel.posY.setAnim(panel.posY.to);
                ++i;
            }
        } else {
            if (ClickGui.mc.currentScreen == Client.clickGuiScreen) {
                ClickGuiScreen.colose = true;
                ClickGuiScreen.scale.to = 0.0f;
                ClickGuiScreen.globalAlpha.to = 0.0f;
                ClientTune.get.playGuiScreenOpenOrCloseSong(false);
            }
            ClickGui.mc.gameSettings.guiScale = this.savedScale;
        }
        super.onToggled(actived);
    }

    @Override
    public void onUpdate() {
        boolean categoryFactored;
        boolean playMusic = MusicInGui.getBool();
        if (playMusic) {
            String track = ClickGui.Song.currentMode.replace("Ost-SF-", "clickguimusic");
            if (this.doForceMusicChange) {
                Client.clickGuiMusic.setTrackNameForce(track);
                this.doForceMusicChange = false;
            } else {
                Client.clickGuiMusic.setTrackName(track);
            }
            Client.clickGuiMusic.setTrackName(ClickGui.Song.currentMode.replace("Ost-SF-", "clickguimusic"));
            Client.clickGuiMusic.setMaxVolume(MusicVolume.getFloat() / 200.0f);
        }
        Client.clickGuiMusic.setPlaying((ClickGui.mc.currentScreen == Client.clickGuiScreen && !ClickGuiScreen.colose || ClickGui.mc.currentScreen instanceof GuiConfig || ClickGui.SaveMusic.bValue) && playMusic);
        if (ClickGui.mc.currentScreen != Client.clickGuiScreen && !(ClickGui.mc.currentScreen instanceof GuiConfig)) {
            this.toggleSilent(false);
        } else if (ClickGui.categoryColorFactor.to == 1.0f != (categoryFactored = CategoryColor.getBool())) {
            ClickGui.categoryColorFactor.to = categoryFactored ? 1.0f : 0.0f;
        }
    }

    static {
        categoryColorFactor = new AnimationUtils(0.0f, 0.0f, 0.015f);
    }
}

