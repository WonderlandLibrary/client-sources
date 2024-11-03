package dev.star.module.impl.display.targethud;

import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.render.RoundedUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class StraightTargetHUD extends TargetHUD {
    private float health = 0;
    private float barAnim = 0;
    private final DecimalFormat DF_1 = new DecimalFormat("0.0");

    // Assuming these need to be defined based on the errors
    private final Animation globalAnimation = new DecelerateAnimation(250, 1, Direction.FORWARDS);

    // Assuming these are your font variables


    public StraightTargetHUD() {
        super("Straight");
    }

    @Override
    public void render(float x, float y, float alpha, EntityLivingBase target) {
        float width = 130;
        float height = 50;

        RenderUtil.scaleStart(x + width / 2, y + height / 2, globalAnimation.getOutput().floatValue());
        RoundedUtil.drawRoundOutline(x, y, width, height, 2, 1f, new Color(0xFF080809), new Color(0xFF3C3C3D));
        RoundedUtil.drawRoundOutline(x + 3, y + 3, 32, 44, 2, 0.5f, new Color(0xFF151515), new Color(0xFF3C3C3D));

        GL11.glPushMatrix();
        RendererLivingEntity.NAME_TAG_RANGE = 0;
        RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 0;
        GuiInventory.drawEntityOnScreen((int) (x + 18), (int) (y + 44), 20, target.rotationYaw, -target.rotationPitch, target);
        RendererLivingEntity.NAME_TAG_RANGE = 64f;
        RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 32f;
        GL11.glPopMatrix();

        health = (float) RenderUtil.animate((width - 41) * (target.getHealth() / target.getMaxHealth()), health, 0.025f);
        barAnim = (float) RenderUtil.animate(width - 41, barAnim, 0.025f);
        Font20.drawStringWithShadow(target.getName(), x + 37, y + 6, -1);

        RoundedUtil.drawRound(x + 38, y + 8 + Font20.getHeight(), barAnim, 5, 0, new Color(getHealthColor(target)).darker().darker());
        RoundedUtil.drawRound(x + 38, y + 8 + Font20.getHeight(), health, 5, 0, new Color(getHealthColor(target)));

        RenderUtil.scissorStart(x + 38, y + 7 + Font20.getHeight(), barAnim, 9);

        float amount = barAnim / 10;
        float length = barAnim / amount;
        for (int i = 1; i < amount; i++) {
            RoundedUtil.drawRound(x + 38 + i * length - 0.5f, y + 6.9f + Font20.getHeight(), 0.5f, 6.9f, 0, Color.black);
        }
        RenderUtil.scissorEnd();

        Font20.drawStringWithShadow(String.format("HP: %s | DIST: %s", DF_1.format(target.getHealth()), DF_1.format(mc.thePlayer.getDistanceToEntity(target))),
                x + 38, y + 27, -1);

        boolean winning = target.getHealth() < mc.thePlayer.getHealth();
        Font20.drawStringWithShadow(mc.thePlayer.getHealth() == target.getHealth() ? "Indecisive" : winning ? "Winning" : "Losing", x + 38, y + 29 + Font20.getHeight(), -1);
        RenderUtil.scaleEnd();
    }

    @Override
    public void renderEffects(float x, float y, float alpha) {
        // Add any additional rendering effects if needed
    }

    // Method to get health color based on health percentage
    private int getHealthColor(EntityLivingBase entity) {
        float healthPercentage = entity.getHealth() / entity.getMaxHealth();
        if (healthPercentage > 0.75f) {
            return 0xFF00FF00; // Green
        } else if (healthPercentage > 0.5f) {
            return 0xFFFFFF00; // Yellow
        } else if (healthPercentage > 0.25f) {
            return 0xFFFFA500; // Orange
        } else {
            return 0xFFFF0000; // Red
        }
    }
}
