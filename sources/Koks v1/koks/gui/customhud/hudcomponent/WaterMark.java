package koks.gui.customhud.hudcomponent;

import koks.Koks;
import koks.gui.customhud.Component;
import koks.gui.customhud.ComponentSettings;
import koks.gui.customhud.valuehudsystem.ValueHUD;
import koks.utilities.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 03:17
 */
public class WaterMark extends Component {

    public ValueHUD white = new ValueHUD("White", false, this);

    public ValueHUD first_letter_size = new ValueHUD("First L Size", 1, 1, 4, false, this);
    public ValueHUD other_letter_size = new ValueHUD("Other L Size", 1, 1, 4, false, this);

    public WaterMark() {
        super(0, 0, 0, 0);
        setWidth(getMcFontRenderer().getStringWidth(Koks.getKoks().CLIENT_NAME));
        setHeight(getMcFontRenderer().FONT_HEIGHT);

        Koks.getKoks().valueHUDManager.getValueHUDS().add(white);

        Koks.getKoks().valueHUDManager.getValueHUDS().add(first_letter_size);
        Koks.getKoks().valueHUDManager.getValueHUDS().add(other_letter_size);

        this.componentSettings = new ComponentSettings(this, "WaterMark Settings");
    }

    @Override
    public void drawTemplate() {


        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        String name = Koks.getKoks().CLIENT_NAME;

        GL11.glPushMatrix();
        GL11.glTranslatef(getX(), getY(), 0);

        GL11.glScaled(first_letter_size.getCurrent(), first_letter_size.getCurrent(), first_letter_size.getCurrent());
        getMcFontRenderer().drawString(name.substring(0, 1), 0, 0, white.isToggled() ? -1 : Koks.getKoks().client_color.getRGB());
        GL11.glPopMatrix();


        GL11.glPushMatrix();
        GL11.glTranslatef(getX() + (getMcFontRenderer().getStringWidth("K") * first_letter_size.getCurrent()), getY() + (int) (getMcFontRenderer().FONT_HEIGHT * Math.max(first_letter_size.getCurrent(), other_letter_size.getCurrent())) - getMc().fontRendererObj.FONT_HEIGHT * other_letter_size.getCurrent(), 0);
        GL11.glScaled(other_letter_size.getCurrent(), other_letter_size.getCurrent(), other_letter_size.getCurrent());
        getMcFontRenderer().drawString(name.substring(1), 0, 0, white.isToggled() ? -1 : Koks.getKoks().client_color.getRGB());
        GL11.glPopMatrix();

        setHeight((getMcFontRenderer().FONT_HEIGHT / 1.25F * Math.max(first_letter_size.getCurrent(), other_letter_size.getCurrent())));
        setWidth((getMcFontRenderer().getStringWidth(name.substring(0, 1)) * first_letter_size.getCurrent() + getMcFontRenderer().getStringWidth(name.substring(1)) * other_letter_size.getCurrent()));
    }


}
