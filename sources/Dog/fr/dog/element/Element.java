package fr.dog.element;

import fr.dog.util.InstanceAccess;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import lombok.Getter;

public abstract class Element implements InstanceAccess {
    //Timing
    @Getter
    private final long startTime;
    private final long time;
    public final float width, height;

    //Misc
    @Getter
    private final Placement placement;
    public final Animation animation;

    public Element(Placement placement, long time, final float width, final float height){
        this.startTime = System.currentTimeMillis();
        this.placement = placement;
        this.time = time;

        this.width = width;
        this.height = height;

        this.animation = new Animation(Easing.EASE_OUT_BACK, time / 4);
    }

    public abstract void draw();

    public final boolean isFinished(){
        return System.currentTimeMillis() - startTime >= time;
    }

}
