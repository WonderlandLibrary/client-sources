package client.metaware.api.module.api;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.gui.notis.NotificationType;
import client.metaware.api.module.bind.Bindable;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.PropertyRepository;
import client.metaware.api.utils.MinecraftUtil;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.module.movmeent.Flight;
import client.metaware.impl.module.movmeent.Speed;
import client.metaware.impl.utils.render.StringUtils;
import client.metaware.impl.utils.render.Translate;
import net.minecraft.util.EnumChatFormatting;

import java.util.Objects;

public abstract class Module implements MinecraftUtil, Bindable {

    protected static final PropertyRepository propertyRepository = new PropertyRepository();

    private final ModuleInfo annotation = getClass().getAnnotation(ModuleInfo.class);
    private final String name = annotation.name();
    private final Category category = annotation.category();
    private final String renderName = annotation.renderName();
    private final String description = annotation.description();
    private final String[] aliases = annotation.aliases();
    private int keybind = annotation.keybind();
    public float optionAnim = 0;// present
    public float optionAnimNow = 0;// present

    private boolean toggled, hidden, hasSuffix;
    private String suffix;

    public void init() {
        propertyRepository.register(this);
    }

    public Translate translate = new Translate(0f, 0f);

    public void addValueChangeListener(Property<?> mode, boolean pascal) {
        setSuffix(pascal ? StringUtils.upperSnakeCaseToPascal(mode.getValue().toString()) : mode.getValue().toString());
        mode.addValueChange((oldValue, value) -> setSuffix(pascal ? StringUtils.upperSnakeCaseToPascal(mode.getValue().toString()) : mode.getValue().toString()));
    }

    public void addValueChangeListener(Property<?> mode) {
        addValueChangeListener(mode, true);
    }

    public void setSuffix(String suffix) {
        hasSuffix = true;
        this.suffix = "\2478\2477" + suffix.replaceAll("_", " ") + "\2478";
    }

    public Listener<UpdatePlayerEvent> eventListener = event -> {

    };

    @Override
    public void pressed() {toggle();}

    @Override
    public int getKey() {
        return keybind;
    }

    @Override
    public void setKey(int key) {
        this.keybind = key;
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled)
            onEnable();
        else
            onDisable();
    }

    public boolean moduleCheckMovement(){
        return Metaware.INSTANCE.getModuleManager().getModuleByClass(Flight.class).isToggled();
    }

    public void toggled(boolean toggled) {
        if (this.toggled != toggled) {
            this.toggled = toggled;

            if (toggled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public void onEnable() {
        if(!Objects.equals(getName(), "ClickGui") || Metaware.INSTANCE.getConfigManager().currentConfig() == null){
            Metaware.INSTANCE.getNotificationManager().pop("Module Enabled!", "Enabled " + EnumChatFormatting.GREEN + getName() + EnumChatFormatting.RESET + "!", 5000, NotificationType.SUCCESS);
        }
        Metaware.INSTANCE.getEventBus().subscribe(this);
    }

    public void onDisable() {
        if(!Objects.equals(getName(), "ClickGui") || Metaware.INSTANCE.getConfigManager().currentConfig() == null){
           Metaware.INSTANCE.getNotificationManager().pop("Module Disabled!", "Disabled " + EnumChatFormatting.RED + getName() + EnumChatFormatting.RESET + "!", 5000, NotificationType.ERROR);
        }
        Metaware.INSTANCE.getEventBus().unsubscribe(this);
        mc.timer.timerSpeed = 1F;
    }

    public void hidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getRenderName() {
        return hasSuffix ? renderName + "\2477 " + suffix : renderName;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public boolean isToggled() {
        return toggled;
    }

    public static PropertyRepository getPropertyRepository() {
        return propertyRepository;
    }
}
