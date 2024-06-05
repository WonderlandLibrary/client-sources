// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module;

import java.util.Iterator;
import java.lang.reflect.Field;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.gui.animation.Animation;
import gg.childtrafficking.smokex.gui.animation.animations.SmoothMoveAnimation;
import gg.childtrafficking.smokex.gui.animation.Easing;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.gui.element.HAlignment;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import java.util.ArrayList;
import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.property.Property;
import java.util.List;
import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.bind.Bindable;

public class Module implements Bindable
{
    protected final Minecraft mc;
    private final String name;
    private final ModuleCategory category;
    private final String renderName;
    private final String[] aliases;
    private final String description;
    private final List<Property<?>> properties;
    private final List<Element> elements;
    private boolean toggled;
    private boolean hidden;
    private boolean hasSuffix;
    private String suffix;
    public boolean renderElements;
    private int key;
    
    public Module() {
        this.mc = Minecraft.getMinecraft();
        this.name = this.getClass().getAnnotation(ModuleInfo.class).name();
        this.category = this.getClass().getAnnotation(ModuleInfo.class).category();
        this.renderName = this.getClass().getAnnotation(ModuleInfo.class).renderName();
        this.aliases = this.getClass().getAnnotation(ModuleInfo.class).aliases();
        this.description = this.getClass().getAnnotation(ModuleInfo.class).description();
        this.properties = new ArrayList<Property<?>>();
        this.elements = new ArrayList<Element>();
        this.toggled = false;
        this.hidden = false;
        this.suffix = "";
        this.renderElements = true;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        if (toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void onEnable() {
        if (ModuleManager.getInstance(HUDModule.class).getElement("modules").getElement(this.getName()) == null && !this.hidden && ModuleManager.getInstance(HUDModule.class).isToggled()) {
            ModuleManager.getInstance(HUDModule.class).getElement("modules").addElement(new TextElement(this.getName(), -150.0f, -150.0f, this.getRenderName(), -16777216).setHAlignment(HAlignment.RIGHT));
            ModuleManager.getInstance(HUDModule.class).sortModules();
        }
        SmokeXClient.getInstance().getEventDispatcher().register(this);
    }
    
    public void onDisable() {
        if (ModuleManager.getInstance(HUDModule.class).isToggled()) {
            final Element element = ModuleManager.getInstance(HUDModule.class).getElement("modules").getElement(this.getName());
            if (element != null) {
                element.setParent(ModuleManager.getInstance(HUDModule.class).getElement("leavingModules")).setAnimation(new SmoothMoveAnimation(-250.0f, element.y, 600L, Easing.EASE_OUT));
                ModuleManager.getInstance(HUDModule.class).sortModules();
            }
        }
        SmokeXClient.getInstance().getEventDispatcher().unregister(this);
    }
    
    public void toggle() {
        this.setToggled(!this.toggled);
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void init() {
    }
    
    public void setSuffix(final String suffix) {
        if (!this.suffix.equals(" §7" + suffix)) {
            this.hasSuffix = true;
            this.suffix = " §7" + suffix;
            final Element element = ModuleManager.getInstance(HUDModule.class).getElement("modules").getElement(this.getName());
            if (element != null) {
                ((TextElement)element).setText(this.getRenderName());
            }
        }
    }
    
    public void setHidden(final boolean hidden) {
        if (this.hidden != hidden) {
            this.hidden = hidden;
        }
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getRenderName() {
        return this.hasSuffix ? (this.renderName + this.getSuffix()) : this.renderName;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
    
    public void reflectProperties() {
        for (final Field field : this.getClass().getDeclaredFields()) {
            final Class<?> type = field.getType();
            if (type.isAssignableFrom(Property.class) || type.isAssignableFrom(NumberProperty.class) || type.isAssignableFrom(EnumProperty.class) || type.isAssignableFrom(BooleanProperty.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    this.properties.add((Property)field.get(this));
                }
                catch (final IllegalAccessException ex) {}
            }
        }
    }
    
    public List<? extends Property<?>> getProperties() {
        return this.properties;
    }
    
    public Property<?> getProperty(final String name) {
        for (final Property<?> property : this.properties) {
            if (property.getIdentifier().equalsIgnoreCase(name)) {
                return property;
            }
        }
        return null;
    }
    
    public List<Element> getElements() {
        return this.elements;
    }
    
    public Element addElement(final Element element) {
        this.elements.add(element);
        element.updatePosition();
        return element;
    }
    
    public void addElements(final Element... e) {
        for (final Element element : this.elements) {
            this.addElement(element);
        }
    }
    
    public Element getElement(final String identifier) {
        for (final Element element : this.elements) {
            if (element.getIdentifier().equalsIgnoreCase(identifier)) {
                return element;
            }
        }
        return null;
    }
    
    public void removeElement(final String identifier) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getIdentifier().equalsIgnoreCase(identifier)) {
                this.elements.remove(this.elements.get(i));
                --i;
            }
        }
    }
    
    public void clearElements() {
        this.elements.clear();
    }
    
    @Override
    public int getKey() {
        return this.key;
    }
    
    @Override
    public void setKey(final int key) {
        this.key = key;
    }
    
    @Override
    public void press() {
        this.toggle();
    }
}
