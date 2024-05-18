package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.ArrayList;

public class StylePanel extends Panel
{
    public StylePanel(final int x, final int y, final int width, final int height, final boolean open, final boolean visible) {
        super("Style", null, x, y, width, height, open, visible);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Layout Style"));
        final List<String> themes = new ArrayList<String>();
        themes.add("metro");
        themes.add("pixel");
        this.áˆºÑ¢Õ().add(new UIStyleComboBox(themes));
        this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, ""));
        this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Color"));
        final List<String> colors = new ArrayList<String>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("orange");
        colors.add("magenta");
        colors.add("rainbow");
        this.áˆºÑ¢Õ().add(new UIColorComboBox(colors));
        this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, ""));
        this.áˆºÑ¢Õ().add(new UILabel(UIFonts.Ý, "Font"));
        final List<String> fonts = new ArrayList<String>();
        fonts.add("Arial");
        fonts.add("Helvetica");
        fonts.add("Comfortaa");
        this.áˆºÑ¢Õ().add(new UIFontComboBox(fonts));
    }
}
