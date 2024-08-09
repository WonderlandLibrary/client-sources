package ru.FecuritySQ.clickgui.elements;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.clickgui.UIPanel;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.Option;
import ru.FecuritySQ.option.imp.*;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ElementModule extends Element {

    public UIPanel panel;
    public Module module;
    public boolean extended;
    public boolean binding;
    public List<Element> elements = new ArrayList<>();

    public ElementModule(Module module, UIPanel panel){
        this.panel = panel;
        this.module = module;
        setHeight(15);
        for(Option o : module.getOptionList()){
            if(o instanceof OptionBoolean){
                elements.add(new ElementBoolean(this, (OptionBoolean) o));
            }
            if(o instanceof OptionMode){
                elements.add(new ElementMode(this, (OptionMode) o));
            }
            if(o instanceof OptionNumric){
                elements.add(new ElementNumric(this, (OptionNumric) o));
            }
            if(o instanceof OptionBoolList){
                elements.add(new ElementBoolList(this, (OptionBoolList) o));
            }
            if(o instanceof OptionColor){
                elements.add(new ElementColor(this, (OptionColor) o));
            }
            if(o instanceof OptionTheme){
                elements.add(new ElementTheme(((OptionTheme) o)));
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {

        Fonts.GREYCLIFF.drawCenteredString(stack, binding ? "Нажми клавишу " : module.getName(), x + this.width / 2, y + height / 2F - 2, module.isEnabled() ? FecuritySQ.get().theme.getColor() : Color.LIGHT_GRAY.getRGB());
        if (elements.size() > 0) {
            RenderUtil.drawImage(stack, new ResourceLocation((this.extended ? "FecuritySQ/panel/minus" : "FecuritySQ/panel/dots") + ".png"), (float) (x + width - 9), (float) (y + height / 2F - 5), 8, 8);
        }
        int offset = 0;
        if (extended) {
            for (Element e : elements) {
                if (e.isShown()) {
                    e.x = this.x;
                    e.y = this.y + this.height + offset;
                    e.width = this.width;
                    e.height = 16;
                    e.draw(mouseX, mouseY);
                    offset += e.getHeight();
                }
            }
         }
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        if (collided(x, y) && button == 1) {
            extended = !extended;
        }else if (collided(x, y) && ((button == 2))) {
            binding = true;
        } else if (collided(x, y) && button == 0) {
            module.toggle();
        }

        if (extended) {
            for (Element e : elements) {
                if (!e.isShown()) continue;
                e.mouseClicked(x, y, button);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        super.mouseReleased(x, y, button);
        if (extended) {
            for (Element e : elements) {
                if (!e.isShown()) continue;
                e.mouseReleased(x, y, button);
            }
        }
    }

    @Override
    public void keypressed(int key) {
        super.keypressed(key);
        elements.forEach(e -> e.keypressed(key));
        if (binding) {
            if (key == GLFW.GLFW_KEY_DELETE) {
                module.setKey(GLFW.GLFW_KEY_0);
                binding = false;
                return;
            }
            module.setKey(key);
            binding = false;
        }
    }
}
