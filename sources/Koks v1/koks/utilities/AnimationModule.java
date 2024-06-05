package koks.utilities;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 09:50
 */
public class AnimationModule {

    private double zoomAnimation;

    private double slideAnimation;
    private double yAnimation;

    public void setUp() {
        setZoomAnimation(0);
        setYAnimation(0);
        setSlideAnimation(1);
    }

    public void setSlideAnimation(double slideAnimation) {
        this.slideAnimation = slideAnimation;
    }

    public double getSlideAnimation() {
        return slideAnimation;
    }

    public double getZoomAnimation() {
        return zoomAnimation;
    }

    public double getYAnimation() {
        return yAnimation;
    }

    public void setYAnimation(double yAnimation) {
        this.yAnimation = yAnimation;
    }

    public void setZoomAnimation(double zoomAnimation) {
        this.zoomAnimation = zoomAnimation;
    }
}
