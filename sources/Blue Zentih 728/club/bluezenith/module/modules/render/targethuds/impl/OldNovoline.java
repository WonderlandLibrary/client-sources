package club.bluezenith.module.modules.render.targethuds.impl;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import fr.lavache.anime.AnimateTarget;
import fr.lavache.anime.Easing;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.awt.Color;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.util.font.FontUtil.inter35;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;
import static net.minecraft.client.renderer.GlStateManager.*;

public class OldNovoline implements ITargetHUD {

    private float targetPrevHealth = 0;
    private final AnimateTarget prevAnim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);
    private final AnimateTarget anim = new AnimateTarget().setEase(Easing.QUAD_IN).setSpeed(50);

    @Override
    public void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD) {
        if(target.getHealth() <= 0) return;
        pushMatrix();
        NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
        if (info == null) {
            popMatrix();
            popMatrix();
            return;
        }
        int baseWidth = 29;
        int preCalculatedWidth = baseWidth; //used to determine the size of the background rectangle. if basewidth is used, all items will draw on the same spot.
        int index = 4;
        final ItemStack[] reversed = new ItemStack[6]; //should be of size 5 but sometimes sword overrides boots, so i set it to 6

        for(ItemStack stack : target.inventory.armorInventory) { //inserting elements into array
            if(stack == null) continue;
            reversed[index] = stack;
            index--; //put items in reversed order, like novoline does
            preCalculatedWidth += 15;
        }

        translate(3, 5, 0); //move items a little

        if(target.getHeldItem() != null) {
            reversed[5] = target.getHeldItem(); //draw the currently held item too
            preCalculatedWidth += 15;
        }

        float rectWidth = max(80, preCalculatedWidth + 15); //actual rectangle width is being precalculated here
        rectWidth = max(rectWidth, 35 + inter35.getStringWidthF(info.getGameProfile().getName())); //expand the rectangle if the name will go outside the bounds

        pushMatrix(); //start drawing the player model
        translate(14, 31f, 0); //move it
        enableDepth(); //enables depth test, otherwise the model looks broken
        pushAttrib(); //pushes attrib, prevents overriding texture that is already in use by something else => prevents black screen.
        getBlueZenith().targetHudEntity = target; //this entity is currently being drawn, so the nametag/esp/other won't be rendered for it
        drawEntityOnScreen(0, 0, 17, -target.rotationYaw, -target.rotationPitch, target);
        getBlueZenith().targetHudEntity = null; //no longer drawn
        popAttrib();
        popMatrix();

        rect(-3, -5, rectWidth, 37, new Color(1, 1, 1, 150)); //background rectangle
        disableDepth(); //disable depth test, otherwise everything looks darker
       // enableDepth(); //enable it again, helps to prevent item glint drawing over the entire tile (not always)
        boolean first = false;
        GlStateManager.disableAlpha();
        for (ItemStack armor : reversed) {
            if(armor == null) continue;
            mc.getRenderItem().renderItemIntoGUI(armor, baseWidth, 13);
            baseWidth += 16;
        }
        GlStateManager.enableAlpha();
        disableDepth(); //disable

        targetHUD.width = (rectWidth + 3); //set width and height for the outline (used when mouse hovered to drag)
        targetHUD.height = 42;
        //healthbar fuckery
        final float progress = 1 - (target.getMaxHealth() - target.getHealth()) / target.getMaxHealth();
        final float healthbarWidth = rectWidth;
        final float translateWidth = healthbarWidth * progress;
        final float normaldiff = max(abs(targetPrevHealth - translateWidth - 2), 2);
        prevAnim.setSpeed(40 / normaldiff);
        anim.setSpeed(90 / normaldiff);
        final float animPrevWidth = prevAnim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
        final float animWidth = anim.setMax(healthbarWidth).setEase(Easing.QUAD_OUT).setTarget(translateWidth).update().getValue();
        final Color col = new Color(targetHUD.getColorForHealth(target.getMaxHealth(), target.getHealth()));
        rect(-3, 35, animPrevWidth, 37, col); //healthbar
        rect(-3, 35, animWidth, 37,  col.brighter().getRGB()); //erasing healthbar thing
        inter35.drawString(info.getGameProfile().getName(), 31, 0.5f, 0xffffffff);
        targetPrevHealth = translateWidth;
        popMatrix();
    }


    @Override
    public ITargetHUD createInstance() {
        return new OldNovoline();
    }

    private OldNovoline() {}
    private final static OldNovoline oldNovolineMode = new OldNovoline();
    public static ITargetHUD getInstance() {
        return oldNovolineMode;
    }
}
