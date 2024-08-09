package src.Wiksi.functions.impl.movement;

import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;

@FunctionRegister(name = "AutoSprint", type = Category.Movement)
public class AutoSprint extends Function {
    public BooleanSetting saveSprint = new BooleanSetting("Вечный спринт", true);
    public AutoSprint() {
        addSettings(saveSprint);
    }
    }
