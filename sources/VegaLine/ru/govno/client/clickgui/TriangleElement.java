/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.clickgui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.util.math.Vec2f;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;

public class TriangleElement {
    private final List<Vec2f> offsetPos = new ArrayList<Vec2f>();
    private final boolean reverseY;
    private int color;
    private int color2;
    private int color3;
    private int color4;
    private final Random RANDOM;
    private final TimerHelper timerHelper = new TimerHelper();
    private long delay;
    private final long minRandomDelay;
    private final long maxRandomDelay;
    private final AnimationUtils colorOffsetX;
    private final AnimationUtils colorOffsetY;
    private final AnimationUtils colorOffsetDark;
    private final float chanceX;
    private final float chanceY;
    private final float chanceToNullColor;
    private final float size;

    public TriangleElement(Vec2f vec2f, float size, boolean reverseY, int color, int color2, int color3, int color4, float colorSpeed, Random RANDOM, long minRandomDelay, long maxRandomDelay, float chanceX, float chanceY, float chanceToNullColor) {
        this.color = color;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.reverseY = reverseY;
        this.size = size;
        if (reverseY) {
            this.offsetPos.add(new Vec2f(vec2f.x, vec2f.y));
            this.offsetPos.add(new Vec2f(vec2f.x + size / 2.0f, vec2f.y + size / 1.25f));
            this.offsetPos.add(new Vec2f(vec2f.x + size, vec2f.y));
        } else {
            this.offsetPos.add(new Vec2f(vec2f.x + size, vec2f.y + size / 1.25f));
            this.offsetPos.add(new Vec2f(vec2f.x + size / 2.0f, vec2f.y));
            this.offsetPos.add(new Vec2f(vec2f.x, vec2f.y + size / 1.25f));
        }
        this.RANDOM = RANDOM;
        this.minRandomDelay = minRandomDelay;
        this.maxRandomDelay = maxRandomDelay;
        this.chanceX = chanceX;
        this.chanceY = chanceY;
        this.chanceToNullColor = chanceToNullColor;
        this.delay = (long)((float)this.minRandomDelay + (float)((this.maxRandomDelay - this.minRandomDelay) * (long)RANDOM.nextInt(1000)) / 1000.0f);
        float randInt01X = (float)RANDOM.nextInt(1000) <= 1000.0f * this.chanceX ? 1.0f : 0.0f;
        float randInt01Y = (float)RANDOM.nextInt(1000) <= 1000.0f * this.chanceY ? 1.0f : 0.0f;
        float randInt01Dark = (float)RANDOM.nextInt(1000) <= 1000.0f * this.chanceToNullColor ? 1.0f : 0.0f;
        this.colorOffsetX = new AnimationUtils(randInt01X * randInt01Dark, randInt01X * randInt01Dark, colorSpeed);
        this.colorOffsetY = new AnimationUtils(randInt01X * randInt01Dark, randInt01Y * randInt01Dark, colorSpeed);
        this.colorOffsetDark = new AnimationUtils(randInt01Dark, randInt01Dark, colorSpeed);
    }

    public List<Vec2f> getOffsetVectors() {
        return this.offsetPos;
    }

    public boolean isReversedY() {
        return this.reverseY;
    }

    public float getSize() {
        return this.size;
    }

    public int getColor(float alphaPC) {
        int colorY1 = ColorUtils.getOverallColorFrom(this.color, this.color2, this.colorOffsetX.getAnim());
        int colorY2 = ColorUtils.getOverallColorFrom(this.color4, this.color3, this.colorOffsetX.anim);
        int color = ColorUtils.getOverallColorFrom(colorY1, colorY2, this.colorOffsetY.getAnim());
        return ColorUtils.swapAlpha(ColorUtils.swapDark(color, this.colorOffsetDark.getAnim()), (float)ColorUtils.getAlphaFromColor(color) * alphaPC * this.colorOffsetDark.anim);
    }

    public void setColorSpeed(float colorSpeed) {
        this.colorOffsetX.speed = colorSpeed;
        this.colorOffsetY.speed = colorSpeed;
        this.colorOffsetDark.speed = colorSpeed;
    }

    public void updateElement() {
        if (this.timerHelper.hasReached(this.delay)) {
            this.delay = (long)((float)this.minRandomDelay + (float)((this.maxRandomDelay - this.minRandomDelay) * (long)this.RANDOM.nextInt(1000)) / 1000.0f);
            float randInt01X = (float)this.RANDOM.nextInt(1000) <= 1000.0f * this.chanceX ? 1.0f : 0.0f;
            float randInt01Y = (float)this.RANDOM.nextInt(1000) <= 1000.0f * this.chanceY ? 1.0f : 0.0f;
            float randInt01Dark = (float)this.RANDOM.nextInt(1000) <= 1000.0f * this.chanceToNullColor ? 1.0f : 0.0f;
            this.colorOffsetX.to = randInt01X;
            this.colorOffsetY.to = randInt01Y;
            this.colorOffsetDark.to = randInt01Dark;
            this.timerHelper.reset();
        }
    }

    public void setColors(int color, int color2, int color3, int color4) {
        this.color = color;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
    }
}

