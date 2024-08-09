package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ScoreBoard extends Feature
{
    public OptionMode position;
    public OptionSlider y;
    public OptionSlider scale;
    public OptionBoolean remove;
    
    public ScoreBoard() {
        super("ScoreBoard", "\u041d\u0430\u0441\u0442\u0440\u0430\u0438\u0432\u0430\u0435\u0442 \u0441\u043a\u043e\u0440\u0431\u043e\u0440\u0434.", Category.VISUAL);
        this.remove = new OptionBoolean(this, "Remove", false);
        this.position = new OptionMode(this, "Position", "Left", new String[] { "Left", "Right" }, 0);
        this.y = new OptionSlider(this, "Y Pos", 0.0f, -1000.0f, 1000.0f, OptionSlider.SliderType.NULLINT);
        this.scale = new OptionSlider(this, "Scale", 1.0f, 0.0f, 2.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.remove, this.position, this.y, this.scale);
    }
}
