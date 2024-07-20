/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import ru.govno.client.clickgui.CheckBox;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.TargetHUD;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.MusicHelper;

public class ClientTune
extends Module {
    public static ClientTune get;
    public Settings Modules = new Settings("Modules", true, (Module)this);
    public Settings Module;
    public Settings ModuleVolume;
    public Settings ClickGui;

    public ClientTune() {
        super("ClientTune", 0, Module.Category.MISC, true);
        this.settings.add(this.Modules);
        this.Module = new Settings("Module", "VL", this, new String[]{"VL", "Dev", "Discord", "Sigma", "Akrien", "Hanabi", "Tone", "Alarm", "Heavy", "Speech", "Frontiers"}, () -> this.Modules.bValue);
        this.settings.add(this.Module);
        this.ModuleVolume = new Settings("ModuleVolume", 90.0f, 200.0f, 1.0f, this, () -> this.Modules.bValue);
        this.settings.add(this.ModuleVolume);
        this.ClickGui = new Settings("ClickGui", true, (Module)this);
        this.settings.add(this.ClickGui);
        get = this;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Module.currentMode);
    }

    public void playSong(String song) {
        MusicHelper.playSound(song);
    }

    public void playSong(String song, float volume) {
        MusicHelper.playSound(song, volume);
    }

    private boolean canPlaySong(Class at) {
        boolean play;
        boolean bl = play = at == Module.class && this.Modules.bValue;
        if ((at == ClickGuiScreen.class || at == CheckBox.class || at == ClientColors.class || at == TargetHUD.class) && this.ClickGui.bValue) {
            play = true;
        }
        return this.actived && play;
    }

    private String moduleSong(String mode, boolean enable) {
        return (enable ? "enable" : "disable") + mode.toLowerCase() + ".wav";
    }

    private String guiScreenSong(boolean open) {
        return open ? "guienabledev2.wav" : "guidisabledev2.wav";
    }

    private String guiScreenFoneticOpenSong() {
        return "guifoneticonopen.wav";
    }

    private String guicolorsScreenSong(boolean open) {
        return open ? "guicolorsopen.wav" : "guicolorsclose.wav";
    }

    private String guiScreenMusicSaveToggleSong(boolean enable) {
        return enable ? "guisavemusonenable.wav" : "guisavemusondisable.wav";
    }

    private String targetSelectSong() {
        return "targetselect.wav";
    }

    private String guiScreenScrollSong() {
        return "guiscrolldev.wav";
    }

    private String guiScreenModeChangeSong() {
        return "guichangemode.wav";
    }

    private String guiScreenCheckOpenOrCloseSong(boolean open) {
        return "guicheck" + (open ? "open" : "close") + ".wav";
    }

    private String guiScreenCheckBoxSong(boolean enable) {
        return "gui" + (enable ? "enable" : "disable") + "checkbox.wav";
    }

    private String getSliderMoveSong() {
        return "guislidermovedev.wav";
    }

    private String guiScreenModuleOpenOrCloseSong(boolean open) {
        return "guimodulepanel2" + (open ? "open" : "close") + ".wav";
    }

    private String guiScreenModuleBindSong(boolean nonNullBind) {
        return "guibindset" + (nonNullBind ? "released" : "nulled") + ".wav";
    }

    private String guiScreenModuleBindToggleSong(boolean enable) {
        return "guibinding" + (enable ? "enable" : "disable") + ".wav";
    }

    private String guiScreenModuleBindHoldStatusSong(boolean reset) {
        return "guibindhold" + (reset ? "reset" : "start") + ".wav";
    }

    private String guiScreenPanelOpenOrCloseSong(boolean open) {
        return "guipanel" + (open ? "open" : "close") + ".wav";
    }

    private String guiClientcolorModeChangeSong() {
        return "guiclientcolorchangemode.wav";
    }

    private String guiClientcolorPresetChangeSong() {
        return "guiclientcolorchangepreset.wav";
    }

    public void playModule(boolean enable) {
        if (this.canPlaySong(Module.class)) {
            this.playSong(this.moduleSong(this.Module.currentMode, enable), this.ModuleVolume.fValue / 200.0f);
        }
    }

    public void playGuiScreenOpenOrCloseSong(boolean open) {
        if (this.canPlaySong(ClickGuiScreen.class)) {
            this.playSong(this.guiScreenSong(open));
        }
    }

    public void playGuiScreenFoneticSong() {
        if (this.canPlaySong(ClickGuiScreen.class)) {
            this.playSong(this.guiScreenFoneticOpenSong());
        }
    }

    public void playGuiScreenScrollSong() {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenScrollSong());
    }

    public void playGuiScreenCheckBox(boolean enable) {
        if (this.canPlaySong(ClickGuiScreen.class)) {
            this.playSong(this.guiScreenCheckBoxSong(enable));
        }
    }

    public void playGuiScreenChangeModeSong() {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenModeChangeSong());
    }

    public void playGuiCheckOpenOrCloseSong(boolean open) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenCheckOpenOrCloseSong(open));
    }

    public void playGuiSliderMoveSong() {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.getSliderMoveSong());
    }

    public void playGuiModuleOpenOrCloseSong(boolean open) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenModuleOpenOrCloseSong(open), 0.1f);
    }

    public void playGuiPenelOpenOrCloseSong(boolean open) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenPanelOpenOrCloseSong(open));
    }

    public void playGuiModuleBindSong(boolean nonNullBind) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenModuleBindSong(nonNullBind));
    }

    public void playGuiModuleBindingToggleSong(boolean enable) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenModuleBindToggleSong(enable));
    }

    public void playGuiModuleBindingHoldStatusSong(boolean reset) {
        if (!this.canPlaySong(ClickGuiScreen.class)) {
            return;
        }
        this.playSong(this.guiScreenModuleBindHoldStatusSong(reset), reset ? 0.1f : 0.25f);
    }

    public void playGuiClientcolorsChangeModeSong() {
        if (!this.canPlaySong(ClientColors.class)) {
            return;
        }
        this.playSong(this.guiClientcolorModeChangeSong());
    }

    public void playGuiClientcolorsChangePresetSong() {
        if (!this.canPlaySong(ClientColors.class)) {
            return;
        }
        this.playSong(this.guiClientcolorPresetChangeSong());
    }

    public void playGuiColorsScreenOpenOrCloseSong(boolean open) {
        if (this.canPlaySong(ClientColors.class)) {
            this.playSong(this.guicolorsScreenSong(open));
        }
    }

    public void playGuiScreenMusicSaveToggleSong(boolean enable) {
        if (this.canPlaySong(ClientColors.class)) {
            this.playSong(this.guiScreenMusicSaveToggleSong(enable), 0.2f);
        }
    }

    public void playTargetSelect() {
        if (this.canPlaySong(TargetHUD.class)) {
            this.playSong(this.targetSelectSong());
        }
    }
}

