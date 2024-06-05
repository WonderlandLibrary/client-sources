package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;

import java.awt.*;

public class Animation extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) AnimationMode.values(), (Enum) AnimationMode.Exhi);
    public static Numbers<Double> scale = new Numbers<Double>("Scale", "Scale", 1D, 0.0D, 2.0D, 0.05D);
    //public static Numbers<Double> far = new Numbers<Double>("Far", "Far", 0.0, 0.0, 3.0, 0.1);
    public static Numbers<Double> speed = new Numbers<Double>("Swing Speed", "Swing Speed", 1.0D, 0.1D, 2.0D, 0.1D);
    public static Numbers<Double> x = new Numbers<Double>("Item X", "Item X", 0.0D, -1.0D, 1.0D, 0.01D);
    public static Numbers<Double> y = new Numbers<Double>("Item Y", "Item Y", 0.0D, -1.0D, 1.0D, 0.01D);
    public static Numbers<Double> z = new Numbers<Double>("Item Z", "Item Z", 0.0D, -1.0D, 1.0D, 0.01D);



    public Animation() {
        super("Animation", new String[]{"anim"}, ModuleType.Render);
        this.setColor(new Color(159, 190, 192).getRGB());
        this.addValues(this.mode, scale, speed, x, y, z);


    }
    @EventHandler
    public void onUpdate(EventPreUpdate e){
       setSuffix ( mode.getModeAsString () + " | " + scale.getValue ().intValue () );
    }

    public enum AnimationMode {
        Exhi, Ascii, Remix, Sigma, ETB, AsciiOLD, Astro, ExhibitionNew, Cloverhook, Astlofo
    }
}
