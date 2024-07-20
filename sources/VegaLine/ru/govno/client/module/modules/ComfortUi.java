/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Panic;

public class ComfortUi
extends Module {
    public static ComfortUi get;
    public Settings BetterTabOverlay;
    public Settings ScreensDarking;
    public Settings ChatAnimations;
    public Settings ContainerAnim;
    public Settings AnimPauseScreen;
    public Settings AddClientButtons;
    public Settings BetterButtons;
    public Settings BetterChatline;
    public Settings BetterDebugF3;
    public Settings ClipHelperInChat;
    public Settings PaintInChat;
    public static int alphaTransition;

    public ComfortUi() {
        super("ComfortUi", 0, Module.Category.RENDER);
        get = this;
        this.BetterTabOverlay = new Settings("BetterTabOverlay", true, (Module)this);
        this.settings.add(this.BetterTabOverlay);
        this.ScreensDarking = new Settings("ScreensDarking", true, (Module)this);
        this.settings.add(this.ScreensDarking);
        this.ChatAnimations = new Settings("ChatAnimations", true, (Module)this);
        this.settings.add(this.ChatAnimations);
        this.ContainerAnim = new Settings("ContainerAnim", true, (Module)this);
        this.settings.add(this.ContainerAnim);
        this.AnimPauseScreen = new Settings("AnimPauseScreen", true, (Module)this);
        this.settings.add(this.AnimPauseScreen);
        this.AddClientButtons = new Settings("AddClientButtons", true, (Module)this);
        this.settings.add(this.AddClientButtons);
        this.BetterButtons = new Settings("BetterButtons", true, (Module)this);
        this.settings.add(this.BetterButtons);
        this.BetterChatline = new Settings("BetterChatline", true, (Module)this);
        this.settings.add(this.BetterChatline);
        this.BetterDebugF3 = new Settings("BetterDebugF3", true, (Module)this);
        this.settings.add(this.BetterDebugF3);
        this.ClipHelperInChat = new Settings("ClipHelperInChat", true, (Module)this);
        this.settings.add(this.ClipHelperInChat);
        this.PaintInChat = new Settings("PaintInChat", true, (Module)this);
        this.settings.add(this.PaintInChat);
    }

    public boolean isUsement() {
        return get != null && this.actived && !Panic.stop;
    }

    public boolean isBetterTabOverlay() {
        return this.isUsement() && this.BetterTabOverlay.bValue;
    }

    public boolean isScreensDarking() {
        return this.isUsement() && this.ScreensDarking.bValue;
    }

    public boolean isChatAnimations() {
        return this.isUsement() && this.ChatAnimations.bValue;
    }

    public boolean isContainerAnim() {
        return this.isUsement() && this.ContainerAnim.bValue;
    }

    public boolean isAnimPauseScreen() {
        return this.isUsement() && this.AnimPauseScreen.bValue;
    }

    public boolean isAddClientButtons() {
        return this.isUsement() && this.AddClientButtons.bValue;
    }

    public boolean isBetterButtons() {
        return this.isUsement() && this.BetterButtons.bValue;
    }

    public boolean isBetterChatline() {
        return this.isUsement() && this.BetterChatline.bValue;
    }

    public boolean isBetterDebugF3() {
        return this.isUsement() && this.BetterDebugF3.bValue;
    }

    public boolean isClipHelperInChat() {
        return this.isUsement() && this.ClipHelperInChat.bValue;
    }

    public boolean isPaintInChat() {
        return this.isUsement() && this.PaintInChat.bValue;
    }

    static {
        alphaTransition = 0;
    }
}

