package optifine;

import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;

public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl
{
    private GameSettings.Options option;
    
    public GuiOptionButtonOF(final int id, final int x, final int y, final GameSettings.Options option, final String text) {
        super(id, x, y, option, text);
        this.option = null;
        this.option = option;
    }
    
    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}
