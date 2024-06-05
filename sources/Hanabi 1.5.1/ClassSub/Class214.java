package ClassSub;

import cn.Hanabi.modules.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.value.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class Class214
{
    private Class325 panel;
    private Mod module;
    private boolean isExtended;
    @NotNull
    private List<Class48<?>> settings;
    
    
    public Class214(final Class325 panel, final Mod module) {
        this.isExtended = false;
        this.settings = new ArrayList<Class48<?>>();
        this.panel = panel;
        this.module = module;
        final ArrayList<Value> list = new ArrayList<Value>();
        for (final Value value : Value.list) {
            if (value.getValueName().split("_")[0].contains(module.getName())) {
                list.add(value);
            }
        }
        if (list == null) {
            return;
        }
        for (final Value value2 : list) {
            if (value2.isValueBoolean) {
                this.settings.add(new Class115(value2));
            }
            if (value2.isValueMode) {
                this.settings.add(new Class91(value2));
            }
            if (value2.isValueDouble || value2.isValueFloat || value2.isValueInteger || value2.isValueLong) {
                this.settings.add(new Class40(value2));
            }
        }
    }
    
    public void click() {
        this.module.setState(!this.module.getState());
    }
    
    public Mod getModule() {
        return this.module;
    }
    
    public boolean isHover(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n5 >= n && n5 <= n + n3 && n6 >= n2 && n6 <= n2 + n4;
    }
    
    public int renderExtended(final int x, final int n) {
        int y = n + 1;
        int max = 0;
        if (this.isExtended) {
            for (final Class48<?> class48 : this.settings) {
                class48.update();
                class48.setX(x);
                class48.setY(y);
                y += class48.getHeight() + 2;
                max = Math.max(class48.getWidth(), max);
            }
            ++y;
            Gui.drawRect(x, n, max + x, y, Class8.PANEL_MAIN_COLOR.getRGB());
            final Iterator<Class48<?>> iterator2 = this.settings.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().draw();
            }
        }
        return y;
    }
    
    public boolean isExtended() {
        return this.isExtended;
    }
    
    public void setExtended(final boolean isExtended) {
        this.isExtended = isExtended;
    }
    
    public boolean onMouseClick(final int n, final int n2, final int n3) {
        boolean b = false;
        for (final Class48<?> class48 : this.settings) {
            if (this.isExtended && class48.onMouseClick(n, n2, n3)) {
                b = true;
            }
        }
        return b;
    }
}
