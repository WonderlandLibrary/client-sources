/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class WallHack
extends Module {
    public static WallHack get;
    Settings Players;
    Settings Friends;
    Settings Crystals;
    Settings Mobs;
    Settings Self;
    Settings Tiles;
    Settings ColorMode;
    Settings Opacity;
    Settings PickColor;

    public WallHack() {
        super("WallHack", 0, Module.Category.RENDER);
        get = this;
        this.Players = new Settings("Players", true, (Module)this);
        this.settings.add(this.Players);
        this.Friends = new Settings("Friends", true, (Module)this);
        this.settings.add(this.Friends);
        this.Mobs = new Settings("Mobs", true, (Module)this);
        this.settings.add(this.Mobs);
        this.Self = new Settings("Self", true, (Module)this);
        this.settings.add(this.Self);
        this.Crystals = new Settings("Crystals", true, (Module)this);
        this.settings.add(this.Crystals);
        this.ColorMode = new Settings("CrysColorMode", "Client", this, new String[]{"Client", "Picker"}, () -> this.Crystals.bValue);
        this.settings.add(this.ColorMode);
        this.Opacity = new Settings("CrysOpacity", 0.7f, 1.0f, 0.05f, this, () -> this.Crystals.bValue && this.ColorMode.currentMode.equalsIgnoreCase("Client"));
        this.settings.add(this.Opacity);
        this.PickColor = new Settings("CrysPickColor", ColorUtils.getColor(110, 160, 255, 225), (Module)this, () -> this.Crystals.bValue && this.ColorMode.currentMode.equalsIgnoreCase("Picker"));
        this.settings.add(this.PickColor);
        this.Tiles = new Settings("Tiles", true, (Module)this);
        this.settings.add(this.Tiles);
    }

    @Override
    public void onUpdate() {
        if (!(this.Players.getBool() || this.Friends.getBool() || this.Crystals.getBool() || this.Mobs.getBool())) {
            this.toggle(false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77]: \u00a77\u0432\u043a\u043b\u044e\u0447\u0438\u0442\u0435 \u0447\u0442\u043e-\u043d\u0438\u0431\u0443\u0434\u044c \u0432 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430\u0445.", false);
        }
    }

    private boolean[] getEtypes() {
        return new boolean[]{this.Players.bValue, this.Friends.bValue, this.Crystals.bValue, this.Mobs.bValue, this.Self.bValue, this.Tiles.bValue};
    }

    private boolean isCurrent(boolean[] entTypes, Entity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof EntityLivingBase) {
            EntityOtherPlayerMP mp;
            EntityLivingBase base = (EntityLivingBase)entity;
            if (entTypes[4] && base instanceof EntityPlayerSP || base instanceof EntityOtherPlayerMP && entTypes[Client.friendManager.isFriend((mp = (EntityOtherPlayerMP)base).getName()) ? 1 : 0] || (base instanceof EntityAnimal || base instanceof EntityMob) && entTypes[3]) {
                return base.isEntityAlive();
            }
        }
        if (entity instanceof EntityEnderCrystal) {
            EntityEnderCrystal crystal = (EntityEnderCrystal)entity;
            if (!crystal.isDead) {
                return entTypes[2];
            }
        }
        if (entity instanceof EntityMinecartContainer || entity instanceof IProjectile || entity instanceof EntityArmorStand) {
            return entTypes[5];
        }
        return false;
    }

    private boolean isCurrent(boolean[] entTypes, TileEntity tileEntity) {
        return entTypes[5];
    }

    private int getChamsColor(Entity entityIn) {
        int color = 0;
        if (entityIn instanceof EntityEnderCrystal) {
            switch (this.ColorMode.currentMode) {
                case "Client": {
                    color = ClientColors.getColor1(Math.abs(entityIn.getEntityId()), this.Opacity.fValue);
                    break;
                }
                case "Picker": {
                    color = this.PickColor.color;
                }
            }
        }
        return color;
    }

    private void renderChams(boolean chamsMode, boolean pre, int col, Runnable renderModel, boolean isRenderItems) {
        if (pre) {
            if (!isRenderItems && chamsMode) {
                GlStateManager.enableBlend();
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(2884);
                GL11.glDisable(2896);
                RenderUtils.glColor(ColorUtils.swapAlpha(col, (float)ColorUtils.getAlphaFromColor(col) / 2.0f));
                GL11.glDepthMask(false);
                WallHack.mc.entityRenderer.disableLightmap();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            }
            if (isRenderItems) {
                GL11.glEnable(3553);
            } else {
                GL11.glDepthRange(0.0, 0.01);
            }
            renderModel.run();
            GL11.glDepthRange(0.0, 1.0);
            if (!isRenderItems && chamsMode) {
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                WallHack.mc.entityRenderer.enableLightmap();
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GlStateManager.resetColor();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public void preRenderLivingBase(Entity baseIn, Runnable renderModel, boolean isRenderItems) {
        if (!this.isCurrent(this.getEtypes(), baseIn)) {
            return;
        }
        WallHack.mc.renderManager.renderShadow = false;
        this.renderChams(baseIn instanceof EntityEnderCrystal, true, this.getChamsColor(baseIn), renderModel, isRenderItems);
    }

    @Override
    public void postRenderLivingBase(Entity baseIn, Runnable renderModel, boolean isRenderItems) {
        if (!this.isCurrent(this.getEtypes(), baseIn)) {
            return;
        }
        WallHack.mc.renderManager.renderShadow = WallHack.mc.gameSettings.entityShadows;
        this.renderChams(baseIn instanceof EntityEnderCrystal, false, 0, renderModel, isRenderItems);
    }

    public void preRenderTileEntity(TileEntity tileIn, Runnable renderModel) {
        if (!this.isCurrent(this.getEtypes(), tileIn)) {
            return;
        }
        WallHack.mc.renderManager.renderShadow = false;
        this.renderChams(false, true, 0, renderModel, false);
    }

    public void postRenderTileEntity(TileEntity tileIn, Runnable renderModel) {
        if (!this.isCurrent(this.getEtypes(), tileIn)) {
            return;
        }
        WallHack.mc.renderManager.renderShadow = WallHack.mc.gameSettings.entityShadows;
        this.renderChams(false, true, 0, renderModel, false);
    }
}

