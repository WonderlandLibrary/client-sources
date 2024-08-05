package fr.dog.module;

import fr.dog.Dog;
import fr.dog.module.impl.render.HUD;
import fr.dog.property.Property;
import fr.dog.notification.NotificationManager;
import fr.dog.notification.impl.NotificationType;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.util.InstanceAccess;
import fr.dog.util.input.MouseUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Module implements InstanceAccess {
    public final Animation animation = new Animation(Easing.EASE_IN_OUT_QUAD, 250);

    private BooleanProperty onlyOnKeyHold = BooleanProperty.newInstance("Only when key hold", false);

    private final String name;
    private final ModuleCategory category;
    public List<Property<?>> propertyList = new ArrayList<>();
    private int keyBind;
    private boolean enabled;
    private boolean draggable = false;
    private int x;
    private int y;
    private float width;
    private float height;
    private boolean isDragged;
    private int oldMouseX;
    private int oldMouseY;
    private String suffix;
    private String customName;

    public Module(String name, ModuleCategory category) {
        this(name, category, Integer.MIN_VALUE);
    }

    public Module(String name, ModuleCategory category, int keyBind) {
        this.name = name;
        this.category = category;
        this.keyBind = keyBind;
        this.customName = name;

        if(ModuleCategory.RENDER == category){
            propertyList.add(onlyOnKeyHold);
        }
    }

    public Property<?> setting(String name) {
        return propertyList.stream().filter(setting -> setting.getLabel().equals(name)).findFirst().orElse(null);
    }

    public void registerProperty(Property<?> property) {
        this.propertyList.add(property);
    }

    public void registerProperties(Property<?>... properties) {
        this.propertyList.addAll(Arrays.asList(properties));
    }

    @SuppressWarnings("unchecked")
    public <T extends Property<?>> T getValueByName(String name) {
        return (T) propertyList.stream()
                .filter(value -> value.getLabel().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void setEnabled(boolean enabled) {
        if(this.enabled != enabled){
            this.enabled = enabled;
            NotificationManager.addNotification("Module", this.name + (enabled ? EnumChatFormatting.GREEN + " Enabled" : EnumChatFormatting.RED + " Disabled"), enabled ? NotificationType.INFO : NotificationType.WARNING);
            if (enabled) {
                Dog.getInstance().getEventBus().register(this);
                try {
                    this.onEnable();
                } catch (Throwable ignored) {
                }
            } else {
                Dog.getInstance().getEventBus().unregister(this);
                try {
                    this.onDisable();
                } catch (Throwable ignored) {
                }
            }
        }

        setToArray();
    }

    private void setToArray(){
        if(mc.thePlayer == null) return;

        Dog.getInstance().getModuleManager().getModule(HUD.class).setArraylist();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public String getSuffix() {
        if (suffix == null)
            return "";

        return suffix;
    }

    public void setDragged(boolean isDragged, int i, int j) {
        this.isDragged = isDragged;
        this.oldMouseX = x - i;
        this.oldMouseY = y - j;
    }

    public void drag() {
        this.x = MouseUtil.getMouseX() + this.oldMouseX;
        this.y = MouseUtil.getMouseY() + this.oldMouseY;

        clamp();
    }

    public boolean isHovered() {
        return MouseUtil.isHovering(MouseUtil.getMouseX(), MouseUtil.getMouseY(), x, y, width, height);
    }
    private void clamp() {
        ScaledResolution sr = new ScaledResolution(mc);

        this.x = (int) Math.max(0, Math.min(this.x, sr.getScaledWidth() - this.width));
        this.y = (int) Math.max(0, Math.min(this.y, sr.getScaledHeight() - this.height));
    }
}
