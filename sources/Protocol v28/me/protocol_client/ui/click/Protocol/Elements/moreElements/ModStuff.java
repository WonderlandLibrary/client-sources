package me.protocol_client.ui.click.Protocol.Elements.moreElements;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class ModStuff extends Element {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected final Module mod;
    public List<Element> elements;
    public boolean open;

    public ModStuff(Module mod, float x, float y, float width, float height) {
        super(x, y, width, height);

        this.mod = mod;
        this.elements = new ArrayList<>();

        for (Value val : Protocol.getValueManager().getContents()) {
            if (val.getName().toLowerCase().startsWith(getMod().getName().toLowerCase().replaceAll(" ", "") + "_")) {
                if (!(val.getValue() instanceof Enum)) {
                    String actualName = val.getName().toLowerCase().replaceAll(mod.getName().toLowerCase(), mod.getName().toLowerCase().replaceAll(" ", ""));
                    String prettyName = "";
                    String[] actualNameSplit = actualName.split("_");
                    if (actualNameSplit.length > 0) {
                        for (String arg : actualNameSplit) {
                            if (!arg.equalsIgnoreCase(getMod().getName().replaceAll(" ", ""))) {
                                arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
                                prettyName += arg + " ";
                            }
                        }
                    } else {
                        prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length());
                    }


                    prettyName = prettyName.replaceAll(mod.getName().replaceAll(" ", "").toLowerCase(), "");

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
    	 Protocol.getGuiClick().getTheme().renderButton(mod.getName(), mod.isToggled(), getX(), getY(), getWidth(), getHeight(), isOverElement(mouseX, mouseY), this);
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX > getX() && mouseY > getY() && mouseX < getX() + getWidth() && mouseY < getY() + Protocol.getGuiClick().getTheme().getElementHeight()) {
            if (mouseButton == 0) {
                mod.toggle();
            }

            if (mouseButton == 1 && Protocol.getGuiClick().getTheme().hasSubMenus()) {
                this.open = !open;
                for(Value val : Protocol.getValueManager().getContents()){
                	val.setValue(val.getValue());
                }
            }
        }
    }

    public Module getMod() {
        return mod;
    }

    @Override
    public void onGuiClosed() {
    }
}
