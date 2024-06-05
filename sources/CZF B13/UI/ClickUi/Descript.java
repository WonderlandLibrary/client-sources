package gq.vapu.czfclient.UI.ClickUi;

import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.ClientUtil;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Descript extends ValueButton {
    public Module cheat;
    public double opacity = 0.0;
    public boolean bind;

    public Descript(Module cheat, int x, int y) {
        super(null, x, y);
        this.custom = true;
        this.bind = false;
        this.cheat = cheat;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        CFontRenderer font = FontLoaders.GoogleSans18;
        CFontRenderer mfont = FontLoaders.GoogleSans16;
        CFontRenderer bigfont = FontLoaders.GoogleSans28;
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        Gui.drawRect(this.x - 10, this.y - 4, this.x + 80, this.y + 11, ClientUtil.reAlpha(1, 0.3f));
        mfont.drawString("Descript", this.x - 5, this.y + 2, new Color(184, 184, 184).getRGB());
        mfont.drawString("" + Keyboard.getKeyName(this.cheat.getKey()),
                this.x + 76 - mfont.getStringWidth(Keyboard.getKeyName(this.cheat.getKey())), this.y + 2,
                new Color(184, 184, 184).getRGB());
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {

    }
}
