package best.actinium.property;

import best.actinium.module.Module;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@Setter
public class Property {

    private final String name;
    private final Module parent;

    private Supplier<Boolean> hidden = () -> false;

    public Property(String name, Module parent) {
        this.name = name;
        this.parent = parent;

        this.parent.getProperties().add(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends Property> T setHidden(Supplier<Boolean> hidden) {
        this.hidden = hidden;
        return (T) this;
    }

    public boolean isHidden() {
        return this.hidden.get();
    }

}