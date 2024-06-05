package rip.helium.cheat.impl.visual;

import javafx.beans.property.StringProperty;
import me.hippo.systems.lwjeb.annotation.Collect;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.EntityRenderEvent;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.RenderNametagEvent;
import rip.helium.event.minecraft.RenderOverlayEvent;
import rip.helium.utils.property.impl.BooleanProperty;
import rip.helium.utils.property.impl.ColorProperty;
import rip.helium.utils.property.impl.DoubleProperty;
import rip.helium.utils.property.impl.StringsProperty;

import java.awt.*;

public class Hud extends Cheat {

    public BooleanProperty   prop_logo    = new BooleanProperty("Watermark", "Show Watermark",null, true);
    public StringsProperty   prop_logomode    = new StringsProperty("Watermark Mode", "Changes watermark theme.",() -> prop_logo.getValue(), false, true, new String[]{"Helium"}, new Boolean[]{false, true, false});
    public static BooleanProperty   prop_arraylist    = new BooleanProperty("Arraylist", "Show Arraylist",null, true);
    public static BooleanProperty scoreboard_enabled = new BooleanProperty("Scoreboard", "Scoreboard Status.", null, true);
    public static BooleanProperty shadowfont_enabled = new BooleanProperty("Shadow", "Shadow Font", null, false);
    public static StringsProperty prop_theme = new StringsProperty("Arraylist Mode", "dank", null,
            false, true, new String[] {"Helium", "Virtue"}, new Boolean[] {true, false});
    public static BooleanProperty hud_tnt = new BooleanProperty("Tnt Alert", "Alerts you if tnt is close to you", null, true);
    public static BooleanProperty lowercase = new BooleanProperty("Lowercase", "Makes the arraylist lowercase.", null, true);
    public static DoubleProperty down = new DoubleProperty("Scoreboard Down", "Scoreboard Down.", null, 10, 0, 100.0, 0.1, null);
    public static BooleanProperty scoreboard_background = new BooleanProperty("Scoreboard Background", "Background status.", null, true);
    public static StringsProperty prop_colormode = new StringsProperty("ArrayList Color Mode", "Color mode of the arraylist", () -> prop_arraylist.getValue(), false, true, new String[]{"Custom", "Rainbow", "Categories", "Pulsing"}, new Boolean[]{false,false, false, true});
    //public BooleanProperty   prop_rainbow    = new BooleanProperty("Rainbow Arraylist", "",() -> prop_arraylist.getValue(), true);
    public static BooleanProperty targethud = new BooleanProperty("Target Hud", "Displays the target's ping and health.", null, true);
    public static DoubleProperty backgroundcolor = new DoubleProperty("Background Opacity", "Changes the opacity.", null, 0.3,0, 1, 0.1, null);
    public BooleanProperty   prop_customfont    = new BooleanProperty("Custom Font", "Use verdana as the client font.",() -> prop_arraylist.getValue(), true);
    public StringsProperty prop_arraylistmode = new StringsProperty("Arraylist Mode", "", null,
            false, true, new String[] {"Helium", "Plain"}, new Boolean[] {true, false});
    public static ColorProperty prop_color = new ColorProperty("Arraylist Color", "", () -> prop_arraylist.getValue(), 1f, 0f, 1f, 255);

    public static Color colorr;
    private static int brightness = 130;
    private float hue;
    private static boolean ascending;
    private static float[] hsb = new float[3];
    private Hud hud;
    
    public Hud() {
        super("Hud", "", CheatCategory.VISUAL);
        registerProperties(targethud, lowercase, prop_theme, hud_tnt, backgroundcolor,scoreboard_background,down,shadowfont_enabled,prop_arraylist,prop_colormode,  prop_customfont, prop_color);
    }

    @Collect
    public void onUpdate(PlayerUpdateEvent e) { 
    }


}
