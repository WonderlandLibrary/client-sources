package arsenic.module.property;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.Module;
import arsenic.utils.interfaces.IContainable;

import java.util.function.Supplier;

public abstract class Property<T> implements IContainable {

    protected T value;
    protected Module parent;
    protected Supplier<Boolean> visible = () -> true;

    public final void setParent(Module parent) {
        this.parent = parent;
    }
    protected Property(T value) {
        this.value = value;
    }

    public T getValue() { return value; }

    public void setValueSilently(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
        onValueUpdate();
    }

    public void onValueUpdate() {

    }

    public void setVisible(Supplier<Boolean> visible) { this.visible = visible; }

    public boolean isVisible() { return visible.get(); }

    public abstract PropertyComponent<?> createComponent();

    public String getName() {
        return value.toString();
    }

}