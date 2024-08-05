package fr.dog.notification.impl;

import fr.dog.util.math.TimeUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Notification {

    private final String message, subString;
    private final NotificationType type;
    private final NotificationMode mode;
    public float width;
    public float height;
    public final Animation popIn = new Animation(Easing.EASE_IN_OUT_SINE, 250);
    private final TimeUtil time = new TimeUtil();

    public Notification(String message, String subString, NotificationType type, NotificationMode mode) {
        this.mode = mode;
        this.message = message;
        this.subString = subString;
        this.type = type;
        setSize(mode);
        time.reset();
    }

    private void setSize(NotificationMode mode){
        switch (mode){
            case DOG:
                this.width = 125;
                this.height = 45;
                break;
            case FLUX:
                this.width = 125;
                this.height = 25;
                break;
        }
    }

    public final void draw(final float x, final float y){
        popIn.run(time.getTime()<250 || time.getTime()<1725 ? 1 : 0);

        switch (mode) {
            case DOG:
                float newXDog = (float) (x - (width - 5) * popIn.getValue());
                float newYDog = (float) (y -25 * popIn.getValue());

                RenderUtil.drawRect(newXDog, newYDog, width, height, new Color(0, 0, 0, 100));
                RenderUtil.themeRectangle(newXDog, newYDog, width, 10, 2.5F, 0.5F, 1.0F);
                Fonts.getOpenSansBold(16).drawCenteredStringWithShadow("Notification :", newXDog + width / 2, newYDog, new Color(255, 255, 255).getRGB());
                Fonts.getOpenSansMedium(20).drawString(message, newXDog + 10, newYDog + 15, new Color(255, 255, 255).getRGB());
                Fonts.getOpenSansRegular(16).drawString(subString, newXDog + 10, newYDog + 30, new Color(255, 255, 255).getRGB());
                break;

            case FLUX:
                float newXFlux = (float) (x - width * popIn.getValue());
                float newYFlux = (float) (y - 20 * popIn.getValue());

                RenderUtil.drawRect(newXFlux, newYFlux, width, height, new Color(40, 40, 40));
                RenderUtil.drawRect(newXFlux, newYFlux, 25, height, new Color(108, 72, 136));
                RenderUtil.triangle(newXFlux + 25, newYFlux + 7.5f, (float) (x + 25 - width * popIn.getValue()), newYFlux + 17.5f, newXFlux + 30, newYFlux + 12.5f, new Color(108, 72, 136));
                RenderUtil.drawImage(new ResourceLocation("dogclient/icons/flux.png"), newXFlux + 5, newYFlux + 5, 15, 15);
                Fonts.getOpenSansBold(19).drawString(message, newXFlux + 35, newYFlux + 2, new Color(108, 72, 136).getRGB());
                Fonts.getOpenSansRegular(16).drawString(subString, newXFlux + 35, newYFlux + 13, new Color(-1).getRGB());
                break;
        }
    }

    public boolean shouldDelete(){
        return time.finished(2000);
    }


}
