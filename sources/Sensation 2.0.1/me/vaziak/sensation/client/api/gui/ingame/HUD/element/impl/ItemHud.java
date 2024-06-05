package me.vaziak.sensation.client.api.gui.ingame.HUD.element.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.EventSystem;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class ItemHud extends Element {
    private BooleanProperty border = new BooleanProperty("Border", "Do you want the white border around it", true);

	private DoubleProperty opactiy = new DoubleProperty("Opacity", "The darkness of the background", 160, 50, 250, 1);
    public ItemHud() {
        super("ItemHud", Quadrant.TOP_LEFT, 415, 320);
        registerValue(border);
        registerValue(opactiy);
		EventSystem.hook(this);
    }

    @Override
    public void drawElement(boolean editor) {
        editX = positionX;
        editY = positionY;
        width = 30;
        height = 30;

        ItemStack itemstack = Minecraft.getMinecraft().thePlayer.getHeldItem();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        int xPos = (int) positionX;
        int yPos = (int) positionY;
        int multiplier = 0;
        
        if (itemstack != null) {

            if (itemstack.getEnchantmentTagList() != null) {
                for (int i = 0; i < itemstack.getEnchantmentTagList().tagCount(); i++) {
                    height += 5;
                }
            }

            String displayLine = itemstack.getDisplayName() + " x" + itemstack.stackSize;

            width += Fonts.f16.getStringWidth(displayLine);
            if (border.getValue()) {
                Draw.drawBorderedRectangle(positionX, positionY, positionX + width, positionY + height, .5, new Color(0, 0, 0, opactiy.getValue().intValue()).getRGB(), new Color(255, 255, 255).getRGB(), true);
            } else {
                Draw.drawRectangle(positionX, positionY, positionX + width, positionY + height, new Color(0, 0, 0, opactiy.getValue().intValue()).getRGB());
            }
            renderItemAndEffectIntoGUI(itemstack, xPos + 5, yPos + 2);

            Fonts.f16.drawStringWithShadow(displayLine, xPos + 25, yPos + 5, -1);

            if (itemstack.isItemDamaged() && itemstack.getMaxDamage() > 0) {
                Fonts.f16.drawStringWithShadow("Durability: " + (itemstack.getMaxDamage() - itemstack.getItemDamage()), xPos + 25, yPos + 15, -1);
            }

            if (itemstack.isItemEnchanted()) {
                if (EnchantmentHelper.getEnchantments(itemstack).size() > 0) {
                    for (Map.Entry<Integer, Integer> nigger : EnchantmentHelper.getEnchantments(itemstack).entrySet() ) {
                        String enchantName = requireNonNull(Enchantment.getEnchantmentById(nigger.getKey())).getName();
                        Fonts.f16.drawStringWithShadow(I18n.format(enchantName) + " " + nigger.getValue(),xPos + 5, yPos + (25 + (multiplier * 7)), -1);
                        multiplier++;

                    }
                }
            }
        }
    }

    public void renderItem(ItemStack stack, IBakedModel model) {
        if (stack != null) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(.5F, .5F, .5F);

            if (model.isBuiltInRenderer()) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                TileEntityItemStackRenderer.instance.renderByItem(stack);
            } else {
                GlStateManager.translate(-0.5F, -0.5F, -0.5F);
                Minecraft.getMinecraft().getRenderItem().renderModel(model, stack);

                if (stack.hasEffect()) {
                    Minecraft.getMinecraft().getRenderItem().renderEffect(model);
                }
            }

            GlStateManager.popMatrix();
        }
    }
    public void renderItemAndEffectIntoGUI(final ItemStack stack, int xPosition, int yPosition) {
        if (stack != null && stack.getItem() != null) {
            renderItemIntoGUI(stack, xPosition, yPosition);
        }
    }

    public void renderItemIntoGUI(ItemStack stack, int x, int y) {
        IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().itemModelMesher.getItemModel(stack);
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getRenderItem().setupGuiTransform(x, y, ibakedmodel.isGui3d());
        ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
        this.renderItem(stack, ibakedmodel);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }

}