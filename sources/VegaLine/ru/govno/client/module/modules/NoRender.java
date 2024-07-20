/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;

public class NoRender
extends Module {
    public static NoRender get;
    public Settings BreakParticles;
    public Settings HurtCam;
    public Settings ScoreBoard;
    public Settings TotemOverlay;
    public Settings EatParticles;
    public Settings Holograms;
    public Settings ArmorLayers;
    public Settings ArrowLayers;
    public Settings EntityHurt;
    public Settings LiquidOverlay;
    public Settings CameraCollide;
    public Settings BadEffects;
    public Settings TitleScreen;
    public Settings BossStatusBar;
    public Settings EnchGlintEffect;
    public Settings ExpBar;
    public Settings Fire;
    public Settings FireOnEntity;
    public Settings HandShake;
    public Settings LightShotBolt;
    public Settings VanishEffect;
    public Settings FogEffect;
    public Settings ClientCape;
    public Settings HeldTooltips;

    public NoRender() {
        super("NoRender", 0, Module.Category.RENDER);
        get = this;
        this.BreakParticles = new Settings("BreakParticles", true, (Module)this);
        this.settings.add(this.BreakParticles);
        this.HurtCam = new Settings("HurtCam", true, (Module)this);
        this.settings.add(this.HurtCam);
        this.ScoreBoard = new Settings("ScoreBoard", true, (Module)this);
        this.settings.add(this.ScoreBoard);
        this.TotemOverlay = new Settings("TotemOverlay", true, (Module)this);
        this.settings.add(this.TotemOverlay);
        this.EatParticles = new Settings("EatParticles", true, (Module)this);
        this.settings.add(this.EatParticles);
        this.Holograms = new Settings("Holograms", false, (Module)this);
        this.settings.add(this.Holograms);
        this.ArmorLayers = new Settings("ArmorLayers", false, (Module)this);
        this.settings.add(this.ArmorLayers);
        this.ArrowLayers = new Settings("ArrowLayers", true, (Module)this);
        this.settings.add(this.ArrowLayers);
        this.EntityHurt = new Settings("EntityHurt", false, (Module)this);
        this.settings.add(this.EntityHurt);
        this.LiquidOverlay = new Settings("LiquidOverlay", true, (Module)this);
        this.settings.add(this.LiquidOverlay);
        this.CameraCollide = new Settings("CameraCollide", true, (Module)this);
        this.settings.add(this.CameraCollide);
        this.BadEffects = new Settings("BadEffects", true, (Module)this);
        this.settings.add(this.BadEffects);
        this.TitleScreen = new Settings("TitleScreen", true, (Module)this);
        this.settings.add(this.TitleScreen);
        this.BossStatusBar = new Settings("BossStatusBar", false, (Module)this);
        this.settings.add(this.BossStatusBar);
        this.EnchGlintEffect = new Settings("EnchGlintEffect", false, (Module)this);
        this.settings.add(this.EnchGlintEffect);
        this.ExpBar = new Settings("ExpBar", false, (Module)this);
        this.settings.add(this.ExpBar);
        this.Fire = new Settings("Fire", true, (Module)this);
        this.settings.add(this.Fire);
        this.FireOnEntity = new Settings("FireOnEntity", true, (Module)this);
        this.settings.add(this.FireOnEntity);
        this.HandShake = new Settings("HandShake", false, (Module)this);
        this.settings.add(this.HandShake);
        this.LightShotBolt = new Settings("LightShotBolt", true, (Module)this);
        this.settings.add(this.LightShotBolt);
        this.VanishEffect = new Settings("VanishEffect", true, (Module)this);
        this.settings.add(this.VanishEffect);
        this.FogEffect = new Settings("FogEffect", false, (Module)this);
        this.settings.add(this.FogEffect);
        this.ClientCape = new Settings("ClientCape", false, (Module)this);
        this.settings.add(this.ClientCape);
        this.HeldTooltips = new Settings("HeldTooltips", false, (Module)this);
        this.settings.add(this.HeldTooltips);
    }

    @Override
    public void onUpdate() {
        if (this.CameraCollide.bValue) {
            Minecraft.player.noClip = true;
        }
    }

    @Override
    public void onRender2D(ScaledResolution sr) {
        if (this.BadEffects.bValue) {
            Arrays.asList(2, 4, 9, 15, 17, 18, 20, 27).stream().map(INT -> Potion.getPotionById(INT)).filter(POT -> Minecraft.player.isPotionActive((Potion)POT)).forEach(POT -> Minecraft.player.removeActivePotionEffect((Potion)POT));
        }
    }
}

