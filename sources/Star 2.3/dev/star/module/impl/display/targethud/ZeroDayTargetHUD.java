package dev.star.module.impl.display.targethud;
import dev.star.utils.animations.ContinualAnimation;
import dev.star.utils.render.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.text.DecimalFormat;

public class ZeroDayTargetHUD extends TargetHUD {

    private final ContinualAnimation animation = new ContinualAnimation();

    public ZeroDayTargetHUD() {
        super("ZeroDay");
    }

    @Override
    public void render(float x, float y, float alpha, EntityLivingBase target) {
        double healthPercentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0, 1);

        setWidth(145);
        setHeight(60);

        // Draw background
        Gui.drawRect2(x, y, getWidth(), getHeight(), new Color(0, 0, 0, (0.6F * alpha)).getRGB());

        // Draw player
        RenderUtil.resetColor();
        GuiInventory.drawEntityOnScreen((int) x + 18, (int) y + 48, 20, -target.rotationYaw, target.rotationPitch, target);
        RenderUtil.resetColor();

        // Colors
        int nameColor = new Color(255, 255, 255).getRGB();
        int infoColor = new Color(225, 225, 225).getRGB();

        // Draw name & info
        Font24.drawString(target.getName(), x + 36, y + 8, nameColor);

//        Font16.drawString(
//                "Health: " + Math.round(target.getHealth()),
//                x + 36, y + 22,
//                infoColor
//        );
//
//        Font16.drawString(
//                "Range: " + Math.round(mc.thePlayer.getDistanceToEntity(target) - 0.5),
//                x + 36, y + 30,
//                infoColor
//        );

        Font14.drawStringWithShadow("" + Math.round((double)target.getHealth()), (float)x + 41.5f, y + 27, new Color(255, 255, 255).getRGB());
        Font14.drawStringWithShadow("" + Math.round((double)TargetHUD.mc.thePlayer.getDistanceToEntity(target) - 0.5), (float)x + 63.5f, y + 27, new Color(255, 255, 255).getRGB());

        double deltaX = target.posX - mc.thePlayer.posX;
        double deltaZ = target.posZ - mc.thePlayer.posZ;

        // Compute the angle in radians
        double angleRad = Math.atan2(deltaZ, deltaX);

        // Convert the angle to degrees
        double angleDeg = Math.toDegrees(angleRad);

        // Normalize the angle to be within [0, 360]
        angleDeg = (angleDeg + 360) % 180;

        // Adjust the angle based on the player's yaw
        float relativeAngle = (float) angleDeg - mc.thePlayer.rotationYaw;

        // Normalize the relative angle to be within [0, 360]
        if (relativeAngle < 0) {
            relativeAngle += 360;
        }

        // Round the relative angle to the nearest whole number
        int roundedAngle = Math.round(relativeAngle);

        // Draw the rounded angle on the screen
        Font14.drawStringWithShadow(String.valueOf(roundedAngle), (float) (x + 80), (float) (y + 27), new Color(255, 255, 255).getRGB());

      //  RenderUtil.drawLoadingCircleNormal(x + 36, y + 29, new Color(246, 174, 90, 200));
     //   RenderUtil.drawLoadingCircleFast(x + 36, y + 29, new Color(183, 211, 82, 200));
       // RenderUtil.drawLoadingCircleSlow(x + 66, y + 29, new Color(227, 103, 103, 200));
   //     RenderUtil.drawLoadingCircleFast(x + 66, y + 29, new Color(182, 182, 84, 200));
       // RenderUtil.drawLoadingCircleNormal(x + 90, y + 29, new Color(199, 19, 19, 200));


        RenderUtil.drawFirstCircle(x + 45, y + 29, 8, (int)target.getHealth());
        RenderUtil.drawSecondCircle(x + 65 , y + 29, 8,(int)(TargetHUD.mc.thePlayer.getDistanceToEntity(target) - 0.5));
        RenderUtil.drawThirdCircle(x + 85, y + 29, 9);

        Font22.drawString(target.getHealth() < mc.thePlayer.getHealth() ? "Winning" : "Losing", x + 36, y + 40, nameColor);

        // damage anim
        float endWidth = (float) Math.max(0, getWidth() * healthPercentage);
        animation.animate(endWidth, 50);

        float healthWidth = animation.getOutput();

        RenderUtil.scissorStart(
                x, y + getHeight() - 2,
                healthWidth, 2
        );

        Gui.drawGradientRectSideways2(
                x, y + getHeight() - 2,
                getWidth() / 2, 2,
                new Color(255, 0, 0).getRGB(),
                new Color(255, 255, 0).getRGB()
        );

        Gui.drawGradientRectSideways2(
                x + getWidth() / 2, y + getHeight() - 2,
                getWidth() / 2, 2,
                new Color(255, 255, 0).getRGB(),
                new Color(0, 255, 0).getRGB()
        );

        RenderUtil.scissorEnd();
    }


    @Override
    public void renderEffects(float x, float y, float alpha) {
        Gui.drawRect2(x, y, getWidth(), getHeight(), ColorUtil.applyOpacity(Color.BLACK.getRGB(), alpha));
    }
}