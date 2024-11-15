// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.modules.impl.ClickGui;
import net.minecraft.util.math.MathHelper;

public final class AnimationUtil {
    private final double field475;
    private double field474;

    public AnimationUtil(final double value) {
        this.field474 = value;
        this.field475 = value;
    }

    public double animate(final double delta, final double end) {
        if (ClickGui.animations.is(AnimationType.OFF)) {
            this.field474 = RandomUtil.method403(delta, this.field474, end);
        } else if (ClickGui.animations.is(AnimationType.POSITIVE)) {
            this.field474 = MathHelper.lerpPositive((float) delta / 2.0f, (int) this.field474, (int) end);
        } else if (ClickGui.animations.is(AnimationType.NORMAL)) {
            this.field474 = end;
        }
        return this.field474;
    }

    public double method346() {
        return this.field474 / this.field475;
    }

    public void method347(final double delta) {
        this.field474 = RandomUtil.method403(delta, this.field474, this.field475);
    }
}
