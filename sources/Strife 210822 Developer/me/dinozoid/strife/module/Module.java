package me.dinozoid.strife.module;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.Listenable;
import me.dinozoid.strife.bind.Bindable;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.PropertyRepository;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.property.implementations.EnumProperty;
import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.player.AltService;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.StringUtil;

public abstract class Module extends MinecraftUtil implements Listenable, Bindable {

    protected static final PropertyRepository propertyRepository = new PropertyRepository();

    private final ModuleInfo annotation = getClass().getAnnotation(ModuleInfo.class);
    private final String name = annotation.name();
    private final Category category = annotation.category();
    private final String renderName = annotation.renderName();
    private final String description = annotation.description();
    private final String[] aliases = annotation.aliases();
    private int keybind = annotation.keybind();

    private boolean toggled, hidden, hasSuffix;
    private String suffix;

    public void init() {
        propertyRepository.register(this);
    }

    public void addValueChangeListener(Property<?> mode, boolean pascal) {
        setSuffix(pascal ? StringUtil.upperSnakeCaseToPascal(mode.value().toString()) : mode.value().toString());
        mode.addValueChange((oldValue, value) -> setSuffix(pascal ? StringUtil.upperSnakeCaseToPascal(mode.value().toString()) : mode.value().toString()));
    }

    public void addValueChangeListener(Property<?> mode) {
        addValueChangeListener(mode, true);
    }

    public void setSuffix(String suffix) {
        hasSuffix = true;
        this.suffix = "\2478\2477" + suffix.replaceAll("_", " ") + "\2478";
    }

    @Override
    public int key() {
        return keybind;
    }

    @Override
    public void key(int key) {
        this.keybind = key;
    }

    @Override
    public void pressed() {
        toggle();
    }

    public void toggle() {
        toggled(!toggled);
    }

    public void toggled(boolean toggled) {
        if (toggled) {
            if (!this.toggled) onEnable();
        } else {
            if (this.toggled) onDisable();
        }
        this.toggled = toggled;
    }

    public void onEnable() {
        StrifeClient.INSTANCE.eventBus().subscribe(this);
    }

    public void onDisable() {
        StrifeClient.INSTANCE.eventBus().unsubscribe(this);
    }

    public void hidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String name() {
        return name;
    }


    public Category category() {
        return category;
    }

    public String renderName() {
        return hasSuffix ? renderName + "\2477 " + suffix : renderName;
    }

    public String description() {
        return description;
    }

    public String[] aliases() {
        return aliases;
    }

    public int keybind() {
        return keybind;
    }

    public boolean toggled() {
        return toggled;
    }

    public boolean hasSuffix() {
        return hasSuffix;
    }

    public boolean hidden() {
        return hidden;
    }

    public static PropertyRepository propertyRepository() {
        return propertyRepository;
    }
}
