package club.bluezenith.ui.notifications;

import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import club.bluezenith.util.render.RenderUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import static club.bluezenith.util.font.FontUtil.newIconsMedium;
import static club.bluezenith.util.font.FontUtil.newIconsSmall;
import static club.bluezenith.util.render.RenderUtil.animate;
import static club.bluezenith.util.render.RenderUtil.rect;
import static java.lang.Math.*;
import static java.util.Arrays.stream;
import static net.minecraft.client.Minecraft.getSystemTime;
import static net.minecraft.client.renderer.GlStateManager.*;

public class Notification {
    private static final TFontRenderer titleFontRenderer = FontUtil.rubikMedium32,
                          descriptionFontRenderer = FontUtil.rubikR32;

    private final String title;
    private final String[] description;
                                       //targetY used for moving the notif when another one in the queue disappears
    private float x, y, width, height, targetY, animationSpeed, animationProgress, barProgress, iconOffset;

    private boolean doBlur, isCancelled;
    private final boolean isMultiline;

    private long timeCreated, timeEnded;
    private final long livingTime;

    private final NotificationType notificationType;

    private int stacks, prevStacks;

    public Notification(String title, String description, NotificationType notificationType, long livingTime) {
        this.title = title; //needed for multiline support       trim just in case you forget to not put spaces before and after \n
        this.description = stream(description.split("\n")).map(String::trim).toArray(String[]::new);
        this.notificationType = notificationType;
        this.livingTime = livingTime;

        final float titleWidth = titleFontRenderer.getStringWidthF(this.title);
        float descriptionWidth = 0;

        this.height = 25;
        int additionalEntries = -1;
        for (String descriptionEntry : this.description) { //find the longest string in descriptions
            descriptionWidth = max(descriptionFontRenderer.getStringWidthF(descriptionEntry.trim()), descriptionWidth);
            additionalEntries++;
        }

        this.isMultiline = additionalEntries > 0;
        this.height += (isMultiline ? 6F : -2) * max(1, additionalEntries);

        if(!this.isMultiline) {
            iconOffset = 9;
        }
        final float bonusWidth = this.isMultiline ? 15 + iconOffset : 7 + iconOffset;

        this.width = max(100, max(titleWidth + bonusWidth, descriptionWidth + bonusWidth));
    }

    public boolean hasTimeExpired() {
        return getSystemTime() >= timeEnded;
    }

    public boolean shouldBeRemoved() {
        return (hasTimeExpired() || this.isCancelled) //remove the notification if it's time is over or it was cancelled
                && isAnimationDone(0, animationProgress, 0.03) //and if all animations are (almost) done
                && isAnimationDone(0, barProgress, 0.03)
                || (timeEnded != 0 && (getSystemTime() - timeEnded) > 5000); //or if the notification died 5+ seconds ago and for some reason hasn't been removed yet
    }

    public Notification moveTo(float x, float y) {
        this.x = x - this.width;
        this.targetY = y - this.height; //setting it to targetY because there might be a need for y smoothing (when notification below disappears and u need to take it's place)
        return this;
    }

    public Notification setSpeed(float speed) {
        this.animationSpeed = speed;
        return this;
    }

    public Notification doBlur(boolean doBlur) {
        this.doBlur = doBlur;
        return this;
    }

    private boolean animationDone;

    public Notification render(float screenWidth, float screenHeight) {
        final float width = screenWidth - this.x,
                    height = screenHeight - this.y; //xy screen values to translate to (for animation that appears from the right side of screen)

        final float translateX = screenWidth - (width * this.animationProgress),
                    translateY = screenHeight - height; //xy screen values to translate to

        translate(translateX, translateY, 0);

        if(this.doBlur)
            RenderUtil.blur(translateX - iconOffset, translateY, translateX + this.width, translateY + this.height);

        rect(-iconOffset, 0, this.width, this.height, new Color(1, 1, 1, 150).getRGB()); //background


        if(!this.isMultiline) { //draw a bigger notif icon on the side if the notification isnt multiline
            final String chr = this.notificationType.getIconCode();
            newIconsMedium.drawString(chr, -iconOffset + 4.5F, this.height / 2 - newIconsMedium.getHeight(chr)/2, this.notificationType.getColor().getRGB());
        }
        float textY = 2,
                titleX = iconOffset + 2.5F;

        if(this.isMultiline) { //if the notification IS multiline, draw a small icon before the title
            final String chr = this.notificationType.getIconCode();
            newIconsSmall.drawString(chr, titleX, textY + 1.5F, this.notificationType.getColor().getRGB());
            titleX += 2.5F + newIconsSmall.getStringWidthF(chr);
        }

        titleFontRenderer.drawString(this.title + (stacks > 0 ? " §7(" + stacks + "x)" : ""), titleX, textY, -1);
        textY += descriptionFontRenderer.FONT_HEIGHT + 3F;

        for (String descriptionEntry : description) {
            descriptionFontRenderer.drawString(descriptionEntry, iconOffset + 2, textY, -1);
            textY += descriptionFontRenderer.FONT_HEIGHT + 1F;
        }

        //background for the progress bar (same color but darker)
       // rect(0, this.height - 1.5F, this.width, this.height, this.notificationType.getColor().darker().darker());
        //progress bar
        rect(min(this.width, (this.width + iconOffset) - ((this.width + iconOffset) * barProgress)), this.height - 1.5F, this.width, this.height, this.notificationType.getColor());
        translate(-translateX, -translateY, 0); //translate back instead of pushing matrix
        return this;
    }

    public Notification performAnimations() {
        if(this.y == 0) this.y = this.targetY; //no need to smooth y transition if the notif was just created

        testWidth(); //increase width if needed for stackable notifications

        if(!isAnimationDone(targetY, y, 0.03))
            this.y = animate(targetY, this.y, animationSpeed);


        this.animationProgress = animate((this.timeEnded != 0 && getSystemTime() >= this.timeEnded) ? (iconOffset > 0 ? -0.1F : 0) : 1, animationProgress, animationSpeed);

        if(!this.animationDone)
            this.animationDone = isAnimationDone(1, animationProgress, 0.1);

        if(this.animationDone)
            setTime();
        //if animation isnt done keep the progress bar where it is
        this.barProgress = this.animationDone ? animate(isCancelled ? 0 : max(0F, (timeEnded - getSystemTime())) / (timeEnded - timeCreated), barProgress, isCancelled ? 0.2F : 0.15F) : 1;

        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

    public float getAnimationProgress() {
        return this.animationProgress;
    }

    private static boolean isAnimationDone(float target, float progress, double threshold) {
        return abs(target - progress) < threshold;
    }

    private void testWidth() {
        if(prevStacks == stacks) return;
        float newTitleWidth = titleFontRenderer.getStringWidthF(this.title + " (" + stacks + "x)");

        final float bonusWidth = this.isMultiline ? 15 + iconOffset : 7 + iconOffset;
        newTitleWidth = max(100, max(newTitleWidth + bonusWidth, getDescriptionWidth() + bonusWidth));

        if(newTitleWidth > this.width) {
            this.animationProgress -= 1 - this.width / newTitleWidth; //smoothly translate to the new width (normally the progress is at 1 (full) so the new width wont be translated to smoothly
            this.width = newTitleWidth;
        }
        prevStacks = stacks;
    }

    private float getDescriptionWidth() {
        float width = 0;
        for (String descriptionEntry : this.description) { //find the longest string in descriptions
            width = max(descriptionFontRenderer.getStringWidthF(descriptionEntry.trim()), width);
        }
        return width;
    }

    private void setTime() { //set notif time when animation is reached
        if(this.timeEnded != 0) return; //time was alr set
        this.timeCreated = getSystemTime();
        this.timeEnded = timeCreated + livingTime;
    }

    public void cancel() {
        this.isCancelled = true;
        this.timeEnded = getSystemTime() + 150;
    }

    public void stack() {
        stacks++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return title.equals(that.title) && Arrays.equals(description, that.description) && notificationType == that.notificationType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, notificationType);
        result = 31 * result + Arrays.hashCode(description);
        return result;
    }
}
