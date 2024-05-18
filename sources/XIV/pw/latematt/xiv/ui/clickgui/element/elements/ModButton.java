package pw.latematt.xiv.ui.clickgui.element.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

import java.util.ArrayList;
import java.util.List;

public class ModButton extends Element {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected final Mod mod;
    public List<Element> elements;
    public boolean open;

    public ModButton(Mod mod, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.mod = mod;
        this.elements = new ArrayList<>();

        for (Value val : XIV.getInstance().getValueManager().getContents()) {
            if (val.getName().toLowerCase().startsWith(getMod().getName().toLowerCase())) {
                if (!(val.getValue() instanceof Enum)) {
                    String actualName = val.getName().replaceAll(mod.getName().replaceAll(" ", "_"), "");
                    String prettyName = "";
                    String[] actualNameSplit = actualName.split("_");
                    if (actualNameSplit.length > 0) {
                        for (String arg : actualNameSplit) {
                            if (!arg.equalsIgnoreCase(getMod().getName())) {
                                arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
                                prettyName += arg + " ";
                            }
                        }
                    } else {
                        prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length());
                    }

                    if (val.getValue() instanceof Boolean) {
                        elements.add(new ValueButton(val, prettyName, x, y + 12, width, height));
                    }
                    if (val instanceof ClampedValue) {
                        if (val.getValue() instanceof Float) {
                            ClampedValue<Float> cv = (ClampedValue<Float>) val;
                            elements.add(new ValueSlider(cv, prettyName, x, y + 12, width, height));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void drawElement(int mouseX, int mouseY) {
        XIV.getInstance().getGuiClick().getTheme().renderButton(mod.getName(), mod.isEnabled(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverElement(mouseX, mouseY)) {
            if (mouseButton == 0) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
                mod.toggle();
            }

            if (mouseButton == 1 && XIV.getInstance().getGuiClick().getTheme().hasSubMenus()) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 0.7F));
                this.open = !open;
            }
        }
    }

    public Mod getMod() {
        return mod;
    }

    @Override
    public void onGuiClosed() {
    }
}
