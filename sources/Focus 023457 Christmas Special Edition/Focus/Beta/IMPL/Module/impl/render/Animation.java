package Focus.Beta.IMPL.Module.impl.render;


import java.awt.*;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;

public class Animation extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) AnimationMode.values(), (Enum) AnimationMode.Swank);
    public static Option<Boolean> newSwing = new Option<>("New Swing", "New Swing", false);
    public static Numbers<Double> speed = new Numbers<Double>("Swing Speed", "Swing Speed", 1.0D, 0.1D, 2.0D, 0.1D);
    public static Numbers<Double> x = new Numbers<Double>("Item X", "Item X", 0.0D, -1.0D, 1.0D, 0.01D);
    public static Numbers<Double> y = new Numbers<Double>("Item Y", "Item Y", 0.0D, -1.0D, 1.0D, 0.01D);
    public static Numbers<Double> z = new Numbers<Double>("Item Z", "Item Z", 0.0D, -1.0D, 1.0D, 0.01D);




    public Animation() {
        super("Animation", new String[]{"anim"}, Type.RENDER, "Change sword animations while blocking");
        this.setColor(new Color(159, 190, 192).getRGB());
        this.addValues(this.mode, speed, x, y, z, newSwing);


    }
    @EventHandler
    public void onUpdate(EventPreUpdate e){
        setSuffix(mode.getModeAsString());
    }

    public enum AnimationMode {
        Swank, Swing, Swang, Swong, Swaing, Punch, Stella, Styles, Slide, Interia, Ethereal, Sigma,Exhibition,Smooth,Spinning
    }
}
