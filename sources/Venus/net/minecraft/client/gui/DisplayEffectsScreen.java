/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.text.ITextComponent;

public abstract class DisplayEffectsScreen<T extends Container>
extends ContainerScreen<T> {
    protected boolean hasActivePotionEffects;

    public DisplayEffectsScreen(T t, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(t, playerInventory, iTextComponent);
    }

    @Override
    protected void init() {
        super.init();
        this.updateActivePotionEffects();
    }

    protected void updateActivePotionEffects() {
        if (this.minecraft.player.getActivePotionEffects().isEmpty()) {
            this.guiLeft = (this.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        } else {
            this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        if (this.hasActivePotionEffects) {
            this.func_238811_b_(matrixStack);
        }
    }

    private void func_238811_b_(MatrixStack matrixStack) {
        int n = this.guiLeft - 124;
        Collection<EffectInstance> collection = this.minecraft.player.getActivePotionEffects();
        if (!collection.isEmpty()) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n2 = 33;
            if (collection.size() > 5) {
                n2 = 132 / (collection.size() - 1);
            }
            List<EffectInstance> list = Ordering.natural().sortedCopy(collection);
            this.func_238810_a_(matrixStack, n, n2, list);
            this.func_238812_b_(matrixStack, n, n2, list);
            this.func_238813_c_(matrixStack, n, n2, list);
        }
    }

    private void func_238810_a_(MatrixStack matrixStack, int n, int n2, Iterable<EffectInstance> iterable) {
        this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int n3 = this.guiTop;
        for (EffectInstance effectInstance : iterable) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.blit(matrixStack, n, n3, 0, 166, 140, 32);
            n3 += n2;
        }
    }

    private void func_238812_b_(MatrixStack matrixStack, int n, int n2, Iterable<EffectInstance> iterable) {
        PotionSpriteUploader potionSpriteUploader = this.minecraft.getPotionSpriteUploader();
        int n3 = this.guiTop;
        for (EffectInstance effectInstance : iterable) {
            Effect effect = effectInstance.getPotion();
            TextureAtlasSprite textureAtlasSprite = potionSpriteUploader.getSprite(effect);
            this.minecraft.getTextureManager().bindTexture(textureAtlasSprite.getAtlasTexture().getTextureLocation());
            DisplayEffectsScreen.blit(matrixStack, n + 6, n3 + 7, this.getBlitOffset(), 18, 18, textureAtlasSprite);
            n3 += n2;
        }
    }

    private void func_238813_c_(MatrixStack matrixStack, int n, int n2, Iterable<EffectInstance> iterable) {
        int n3 = this.guiTop;
        for (EffectInstance effectInstance : iterable) {
            Object object = I18n.format(effectInstance.getPotion().getName(), new Object[0]);
            if (effectInstance.getAmplifier() >= 1 && effectInstance.getAmplifier() <= 9) {
                object = (String)object + " " + I18n.format("enchantment.level." + (effectInstance.getAmplifier() + 1), new Object[0]);
            }
            this.font.drawStringWithShadow(matrixStack, (String)object, n + 10 + 18, n3 + 6, 0xFFFFFF);
            String string = EffectUtils.getPotionDurationString(effectInstance, 1.0f);
            this.font.drawStringWithShadow(matrixStack, string, n + 10 + 18, n3 + 6 + 10, 0x7F7F7F);
            n3 += n2;
        }
    }
}

