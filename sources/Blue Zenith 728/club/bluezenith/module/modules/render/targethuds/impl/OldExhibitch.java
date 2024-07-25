package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;
import static net.minecraft.client.renderer.GlStateManager.*;

public class OldExhibitch implements ITargetHUD {

    @Override
    public ITargetHUD createInstance() {
        return new OldExhibitch();
    }

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        final FontRenderer font = mc.fontRendererObj;

        float rectWidth = targetHUD.width = 135,
              rectHeight = targetHUD.height = 47;

        final String name = mc.getNetHandler().getPlayerInfo(target.getUniqueID()).getGameProfile().getName();

        pushMatrix(); //start drawing the player model
        translate(19, 43, 0); //move it
        enableDepth(); //enables depth test, otherwise the model looks broken
        pushAttrib(); //pushes attrib, prevents overriding texture that is already in use by something else => prevents black screen.
        getBlueZenith().targetHudEntity = target; //this entity is currently being drawn, so the nametag/esp/other won't be rendered for it
        resetColor();
        drawEntityOnScreen(0, 0, 20, -target.rotationYaw, -target.rotationPitch, target);
        getBlueZenith().targetHudEntity = null; //no longer drawn
        popAttrib();
        popMatrix();

        final float percent = target.getHealth() / target.getMaxHealth();

        RenderUtil.rect(0, 0, rectWidth, rectHeight, 150 << 24);
        translate(4, 0, 0);
        font.drawString(name, 32, 4, -1, true);
        RenderUtil.rect(32, 6F + font.FONT_HEIGHT, 32 + 75, 5 + font.FONT_HEIGHT + 5, 100 << 24);
        RenderUtil.rect(32.5F, 6.5F + font.FONT_HEIGHT, 32 + (74.5 * percent), 5 + font.FONT_HEIGHT + 4.5F, targetHUD.getColorForHealth(target.getMaxHealth(), target.getHealth()));

        float lineX = 32;

        for (int i = 0; i < 9; i++) {
            lineX += (75F / 10F);
            RenderUtil.rect(lineX, 6F + font.FONT_HEIGHT, lineX + .5F, 10 + font.FONT_HEIGHT, 255 << 24);
        }
        RenderUtil.drawScaledFont(
                font,
                String.format("HP: %s | Dist: %s", (int) target.getHealth(), (int) target.getDistanceToEntity(mc.thePlayer)),
                32.5F,
                12 + font.FONT_HEIGHT,
                -1,
                true, 0.5F
                );
        final ItemStack[] items = new ItemStack[5];
        int index = 4;

        if(target.getHeldItem() != null)
            items[index--] = target.getHeldItem();

        for (ItemStack stack : target.inventory.armorInventory) {
            if(stack == null) continue;
            items[index--] = stack;
        }


        int width = 29;

        float y = 10 + font.FONT_HEIGHT * 2;
        for (ItemStack armor : items) {
            if(armor == null) continue;
            mc.getRenderItem().renderItemIntoGUI(armor, width, (int) y);
            if(armor.isItemEnchanted()) {
                float yCopy = y;
                for (int i = 0; i < armor.getEnchantmentTagList().tagCount(); i++) {
                    final NBTTagCompound enchTag = armor.getEnchantmentTagList().getCompoundTagAt(i);
                    final Enchantment enchantment = Enchantment.getEnchantmentById(enchTag.getShort("id"));
                    if(enchantment == null) continue;
                    final String toDraw = I18n.format(enchantment.getName()).substring(0, 3) + " " + enchTag.getShort("lvl");
                    translate(0, 0, 102 + mc.getRenderItem().zLevel);
                    RenderUtil.drawScaledFont(
                            font,
                            toDraw,
                            width,
                            yCopy,
                            -1,
                            true,
                            0.5F
                    );
                    yCopy += font.FONT_HEIGHT * 0.42F;
                }
            }
            width += 16;
        }
    }

    private static final OldExhibitch oldExhibitch = new OldExhibitch();

    public static OldExhibitch getInstance() {
        return oldExhibitch;
    }
}
