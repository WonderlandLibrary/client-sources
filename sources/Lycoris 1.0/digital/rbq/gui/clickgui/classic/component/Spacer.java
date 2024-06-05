package digital.rbq.gui.clickgui.classic.component;

import digital.rbq.gui.clickgui.classic.ClickGUI;
import digital.rbq.gui.clickgui.classic.window.Window;

/**
 * Created by John on 2017/04/17.
 */
public class Spacer extends Component {
    public Spacer(Window window, int offX, int offY, int height) {
        super(window, offX, offY, "");
        this.width = ClickGUI.settingsWidth;
        this.height = height;
        this.type = "Spacer";
    }

    @Override
    public void render(int var1, int var2) {

    }

    @Override
    public void mouseUpdates(int var1, int var2, boolean var3) {

    }
}
