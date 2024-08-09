package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Optimizer extends Feature
{
    public static OptionBoolean fastMath;
    public static OptionSlider renderDistance;
    public static OptionBoolean removeBehindWalls;
    public static OptionBoolean entities;
    
    public Optimizer() {
        super("Optimizer", "\u041e\u043f\u0442\u0438\u043c\u0438\u0437\u0438\u0440\u0443\u0435\u0442 \u0438\u0433\u0440\u0443.", Category.ANOTHER);
        Optimizer.fastMath = new OptionBoolean(this, "Fast Math", true);
        Optimizer.entities = new OptionBoolean(this, "Entities", true);
        Optimizer.removeBehindWalls = new OptionBoolean(this, "Behind walls", true);
        Optimizer.renderDistance = new OptionSlider(this, "Max Distance", 0.6f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.addOptions(Optimizer.fastMath, Optimizer.entities, Optimizer.removeBehindWalls, Optimizer.renderDistance);
    }
    
    @Override
    public void updateElements() {
        Optimizer.renderDistance.display = Optimizer.entities.enabled;
        Optimizer.removeBehindWalls.display = Optimizer.entities.enabled;
    }
}
