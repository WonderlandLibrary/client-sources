/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Crosshair;
import ru.govno.client.module.modules.OffHand;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class AutoApple
extends Module {
    public static AutoApple get;
    Settings MinHealth;
    Settings OnlyTargetStrafe;
    Settings PlusAbsorb;
    public boolean pverAllowUseInput;
    public boolean wantToEat;
    public AnimationUtils scaleAnimation = new AnimationUtils(0.0f, 0.0f, 0.05f);
    public AnimationUtils triggerAnimation = new AnimationUtils(0.0f, 0.0f, 0.2f);
    private ItemStack lastUsedStack = new ItemStack(Items.GOLDEN_APPLE, 1);

    public AutoApple() {
        super("AutoApple", 0, Module.Category.COMBAT);
        get = this;
        this.MinHealth = new Settings("MinHealth", 14.5f, 20.0f, 5.0f, this);
        this.settings.add(this.MinHealth);
        this.OnlyTargetStrafe = new Settings("OnlyTargetStrafe", true, (Module)this);
        this.settings.add(this.OnlyTargetStrafe);
        this.PlusAbsorb = new Settings("PlusAbsorb", true, (Module)this);
        this.settings.add(this.PlusAbsorb);
    }

    @Override
    public String getDisplayName() {
        return this.name + this.getSuff() + (int)this.MinHealth.fValue + "H" + (String)(Minecraft.player == null ? "" : (Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemAppleGold ? " " + Minecraft.player.getHeldItemOffhand().stackSize + "G" : ""));
    }

    private boolean healthWarning() {
        return Minecraft.player.getHealth() + (AutoApple.get.PlusAbsorb.bValue ? Minecraft.player.getAbsorptionAmount() : 0.0f) < AutoApple.get.MinHealth.fValue;
    }

    public boolean canEat() {
        return (!AutoApple.get.OnlyTargetStrafe.bValue || TargetStrafe.goStrafe()) && Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemAppleGold && !Minecraft.player.isBlocking() && !Minecraft.player.isDrinking() && this.healthWarning() && !Minecraft.player.isCreative() && !this.itemStackCanBeUsed(Minecraft.player.getHeldItemMainhand());
    }

    private void stopProcessEat() {
        if (this.wantToEat) {
            if (!Mouse.isButtonDown(1)) {
                AutoApple.mc.playerController.onStoppedUsingItem(Minecraft.player);
                Minecraft.player.resetActiveHand();
                AutoApple.mc.gameSettings.keyBindUseItem.pressed = false;
            }
            if (AutoApple.mc.currentScreen != null) {
                AutoApple.mc.currentScreen.allowUserInput = this.pverAllowUseInput;
            }
            this.wantToEat = false;
        }
    }

    private boolean itemStackCanBeUsed(ItemStack stack) {
        if (stack != null) {
            Item item = stack.getItem();
            return item != null && item != Items.air && (!Minecraft.player.isCreative() && !Minecraft.player.isSpectator() && item instanceof ItemFood || item instanceof ItemShield || item instanceof ItemPotion || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemEnderPearl || item instanceof ItemEnderEye || item instanceof ItemSnowball);
        }
        return false;
    }

    private void processEat() {
        if (this.canEat() && Minecraft.player != null && !Minecraft.player.isDead && AutoApple.get.actived) {
            if (!Minecraft.player.isHandActive() && !AutoApple.mc.gameSettings.keyBindUseItem.pressed) {
                AutoApple.mc.playerController.processRightClick(Minecraft.player, AutoApple.mc.world, EnumHand.OFF_HAND);
                AutoApple.mc.gameSettings.keyBindUseItem.pressed = true;
                Minecraft.player.setActiveHand(EnumHand.OFF_HAND);
                this.wantToEat = true;
            }
            if (AutoApple.mc.currentScreen != null && !AutoApple.mc.currentScreen.allowUserInput) {
                this.pverAllowUseInput = AutoApple.mc.currentScreen.allowUserInput;
                AutoApple.mc.currentScreen.allowUserInput = true;
            }
        } else {
            this.stopProcessEat();
        }
    }

    @Override
    public void onUpdate() {
        this.processEat();
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived || Mouse.isButtonDown(1)) {
            return;
        }
        this.stopProcessEat();
        super.onToggled(actived);
    }

    public void onEatenSucessfully(ItemStack stack) {
        if (stack != null && stack == Minecraft.player.getActiveItemStack()) {
            this.triggerAnimation.to = 1.0f;
        }
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        boolean isEatingApple;
        float f;
        this.scaleAnimation.to = this.isActived() && this.canEat() || this.triggerAnimation.anim > 0.001f ? 1.0f : 0.0f;
        float alphaPC = 0.0f;
        float scale = this.scaleAnimation.getAnim();
        alphaPC = MathUtils.clamp(scale, 0.0f, 1.0f);
        if (alphaPC == 0.0f) {
            return;
        }
        float triggerAnim = this.triggerAnimation.getAnim();
        if (f > 0.975f) {
            this.triggerAnimation.speed = 0.025f;
            this.triggerAnimation.setAnim(0.0f);
            this.triggerAnimation.to = 0.0f;
        }
        float prevTriggerAnim = triggerAnim;
        triggerAnim = triggerAnim > 0.5f ? 1.0f - triggerAnim : triggerAnim;
        boolean bl = isEatingApple = Minecraft.player.isHandActive() && Minecraft.player.getActiveItemStack().getItem() instanceof ItemAppleGold;
        if (isEatingApple) {
            this.lastUsedStack = Minecraft.player.getActiveItemStack();
        }
        float pTicks = mc.getRenderPartialTicks();
        float eatProgress = MathUtils.clamp(Minecraft.player.getItemInUseMaxCount() == 0 ? 0.0f : (float)Minecraft.player.getItemInUseMaxCount() + pTicks, 0.0f, 32.0f) / 32.0f;
        float x = (float)sr.getScaledWidth() / 2.0f + (AutoApple.mc.gameSettings.thirdPersonView != 0 ? -8.0f - 20.0f * OffHand.scaleAnim.anim : 12.0f - 40.0f * OffHand.scaleAnim.anim) + Crosshair.get.crossPosMotions[0];
        float yExtPC = (float)(OffHand.scaleAnim.to > 0.0f ? 1 : -1) * (OffHand.scaleAnim.anim > 0.5f ? 1.0f - OffHand.scaleAnim.anim : OffHand.scaleAnim.anim) * 2.0f;
        float y = (float)sr.getScaledHeight() / 2.0f - 8.0f + 4.0f * (yExtPC + yExtPC + yExtPC * (0.5f + yExtPC / 2.0f)) + Crosshair.get.crossPosMotions[1];
        int magnitudeColor = ColorUtils.getColor(255, (int)(90.0f * alphaPC * (1.0f - triggerAnim) * (1.0f - triggerAnim)));
        int textColor = this.lastUsedStack.stackSize == 0 ? ColorUtils.fadeColor(ColorUtils.getColor(255, 80, 50, 80.0f * alphaPC), ColorUtils.getColor(255, 80, 50, 255.0f * alphaPC), 1.5f) : ColorUtils.getColor(255, (int)(255.0f * alphaPC));
        int triggerTextColor = ColorUtils.getColor(240, 30, 0, 255.0f * (1.0f - prevTriggerAnim) * alphaPC);
        CFontRenderer font = this.lastUsedStack.stackSize == 0 ? Fonts.noise_20 : Fonts.mntsb_12;
        CFontRenderer font2 = Fonts.mntsb_20;
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(2929);
        GL11.glTranslatef(x, y, 0.0f);
        RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, scale);
        RenderUtils.customRotatedObject2D(0.0f, 0.0f, 8.0f, 16.0f, triggerAnim * 15.0f);
        mc.getRenderItem().renderItemIntoGUI(this.lastUsedStack, 0, 0);
        if (eatProgress != 0.0f) {
            float yOffset = 0.5f;
            StencilUtil.initStencilToWrite();
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, 0.925f);
            RenderUtils.resetBlender();
            mc.getRenderItem().renderItemIntoGUI(this.lastUsedStack, 0, 0);
            GL11.glEnable(3042);
            GL11.glPopMatrix();
            StencilUtil.readStencilBuffer(1);
            RenderUtils.drawAlphedSideways(0.0, yOffset + (16.0f - yOffset) * (1.0f - eatProgress * 1.1f), 16.0, 16.0f - yOffset, magnitudeColor, magnitudeColor, true);
            StencilUtil.uninitStencilBuffer();
        }
        if (ColorUtils.getAlphaFromColor(textColor) >= 26) {
            String countString = this.lastUsedStack.stackSize + "x";
            if (OffHand.get.actived) {
                int itemCount = 0;
                for (int i = 0; i < 44; ++i) {
                    ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
                    if (!(stack.getItem() instanceof ItemAppleGold)) continue;
                    itemCount += stack.stackSize;
                }
                countString = itemCount + "x";
            }
            float textX = MathUtils.lerp(14.5f, (float)(-font.getStringWidth(countString)) + 1.5f, OffHand.scaleAnim.anim);
            font.drawStringWithShadow(countString, textX, 12.0, textColor);
        }
        if (prevTriggerAnim > 0.0f && ColorUtils.getAlphaFromColor(triggerTextColor) >= 33) {
            float triggerAPC = 1.0f - prevTriggerAnim;
            GL11.glBlendFunc(770, 32772);
            GL11.glPushMatrix();
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, (0.925f + triggerAnim * 7.0f) * triggerAPC * triggerAPC);
            mc.getRenderItem().renderItemIntoGUI(this.lastUsedStack, 0, 0);
            GL11.glPopMatrix();
            GL11.glBlendFunc(770, 771);
            float textW = font2.getStringWidth("-1");
            float textX = MathUtils.lerp(9.0f + 9.0f * prevTriggerAnim, -1.5f - 9.0f * prevTriggerAnim, OffHand.scaleAnim.anim);
            GL11.glTranslated(textX, 8.0f * triggerAPC, 0.0);
            RenderUtils.customRotatedObject2D(0.0f, -2.0f, textW, font.getHeight(), (20.0f - 40.0f * triggerAnim - 20.0f * prevTriggerAnim) * (float)(OffHand.scaleAnim.to == 0.0f ? -1 : 1));
            font2.drawStringWithShadow("-1", 0.0, 0.0, triggerTextColor);
        }
        GL11.glDisable(2896);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }
}

