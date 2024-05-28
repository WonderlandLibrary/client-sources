package arsenic.gui.click.impl;

import arsenic.gui.click.Component;
import arsenic.module.property.Property;
import arsenic.utils.interfaces.IContainable;
import arsenic.utils.render.RenderInfo;

public abstract class PropertyComponent<T extends Property> extends Component implements IContainable {

    // placeholder class
    private final String name;
    protected final T self;

    protected PropertyComponent(T p) {
        self = p;
        name = p.getName();
    }

    @Override
    protected final float drawComponent(RenderInfo ri) {
        if (self.isVisible()) {
            //name
            ri.getFr().drawString(name, x1, midPointY, getWhite(), ri.getFr().CENTREY);
            return draw(ri);
        }
        return 0f;
    }

    protected abstract float draw(RenderInfo ri);

    @Override
    protected final void  clickComponent(int mouseX, int mouseY, int mouseButton) {
        if (self.isVisible()) {
            click(mouseX, mouseY, mouseButton);
        }
    }

    protected void click(int mouseX, int mouseY, int mouseButton) {

    }

    public String getName() { return name; }

    @Override
    public int getWidth(int i) {
        return self.isVisible() ? 23 * (i / 100) : 0;
    }

    @Override
    public int getHeight(int i) {
        return self.isVisible() ? 5 * (i / 100) : 0;
    }
}
