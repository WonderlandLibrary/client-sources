package fun.ellant.functions.impl.player;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;

@FunctionRegister(name = "NoEventDelay", type = Category.PLAYER, desc = "Пон?")
public class NoEventDelay extends Function {
    @Override
    public boolean onEnable() {
        print("Эта функция не работает.");
        return false;
    }
}
