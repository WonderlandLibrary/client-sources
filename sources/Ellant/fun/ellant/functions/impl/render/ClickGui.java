package fun.ellant.functions.impl.render;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;

@FunctionRegister(name="ClickGui", type= Category.RENDER, desc = "Изменяет кликгуи")
public class ClickGui
        extends Function {
    public final ModeSetting mode = new ModeSetting("\u041c\u043e\u0434", "Cs", "Cs", "Dropdown");
    public final ModeSetting outline = new ModeSetting("\u0426\u0432\u0435\u0442 \u043e\u0431\u0432\u043e\u0434\u043a\u0438", "\u041d\u0435\u0442\u0443", "\u041d\u0435\u0442\u0443", "\u0420\u0430\u0434\u0443\u0436\u043d\u044b\u0439", "\u041e\u0431\u044b\u0447\u043d\u044b\u0439", "\u0426\u0432\u0435\u0442 \u0442\u0435\u043c\u044b").setVisible(this::lambda$new$0);

    public ClickGui() {
        this.addSettings(mode, outline);
    }

    private Boolean lambda$new$0() {
        return !this.mode.is("Dropdown");
    }
}