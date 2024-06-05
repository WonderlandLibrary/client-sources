package net.shoreline.client.api.config;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.Identifiable;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.impl.event.config.ConfigUpdateEvent;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.shoreline.client.api.config.setting.*;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Supplier;

/**
 * Client Configuration which is saved to a local <tt>.json</tt> file. All
 * configs must be associated with a {@link ConfigContainer} which is
 * responsible for handling caching and saving.
 *
 * <p>All configs hold a modifiable value which can be changed in the
 * ClickGui or through Commands in the chat. The config value cannot be
 * <tt>null</tt>.</p>
 *
 * @author linus
 * @since 1.0
 * @param <T> The config value type
 *
 * @see BooleanConfig
 * @see ColorConfig
 * @see EnumConfig
 * @see MacroConfig
 * @see NumberConfig
 * @see StringConfig
 */
public abstract class Config<T> implements Identifiable, Serializable<T>
{
    // Config name is its UNIQUE identifier
    private final String name;
    // Concise config description, displayed in the ClickGui to help users
    // understand the properties that the config modifies.
    private final String desc;
    // Config value which modifies some property. This value is configured by
    // the user and saved to a local JSON file.
    protected T value;
    //
    private T defaultValue;
    // Parent container. All configs should be added to a config container,
    // otherwise they will not be saved locally.
    private ConfigContainer container;
    //
    private Supplier<Boolean> visible;

    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link net.shoreline.client.api.config.setting}.
     *
     * @param name  The unique config identifier
     * @param desc  The config description
     * @param value The default config value
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public Config(String name, String desc, T value)
    {
        if (value == null)
        {
            throw new NullPointerException("Null values not supported");
        }
        this.name = name;
        this.desc = desc;
        this.value = value;
        this.defaultValue = value;
    }

    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link com.caspian.client.api.config.setting}.
     *
     * @param name  The unique config identifier
     * @param desc  The config description
     * @param value The default config value
     * @param visible The visibility of the config
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public Config(String name, String desc, T value, Supplier<Boolean> visible)
    {
        this(name, desc, value);
        this.visible = visible;
    }

    /**
     * Initializes the config without the default value. DO NOT INITIALIZE
     * CONFIGS USING THIS CONSTRUCTOR.
     *
     * @param name
     * @param desc
     */
    @Internal
    public Config(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public JsonObject toJson()
    {
        final JsonObject obj = new JsonObject();
        obj.addProperty("name", getName());
        obj.addProperty("id", getId());
        return obj;
    }

    /**
     *
     * @param obj The data as a json object
     * @return
     */
    @Override
    public T fromJson(JsonObject obj)
    {
        if (obj.has("value"))
        {
            JsonElement element = obj.get("value");
            return (T) (Byte) element.getAsByte();
        }
        return null;
    }

    /**
     *
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     *
     * @return
     *
     * @see ConfigContainer#getName()
     */
    @Override
    public String getId()
    {
        return String.format("%s-%s-config", container.getName().toLowerCase(),
                name.toLowerCase());
    }

    /**
     * Returns a detailed description of the property that the {@link Config}
     * value represents.
     *
     * @return The config value description
     */
    public String getDescription()
    {
        return desc;
    }

    /**
     * Returns the configuration value.
     *
     * @return The config value
     */
    public T getValue()
    {
        return value;
    }

    /**
     *
     *
     * @return
     */
    public ConfigContainer getContainer()
    {
        return container;
    }

    /**
     *
     * @return
     */
    public boolean isVisible()
    {
        return visible.get();
    }

    /**
     * Sets the current config value to the param value. The passed value
     * cannot be <tt>null</tt>.
     *
     * @param val The param value
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public void setValue(final T val)
    {
        if (val == null)
        {
            throw new NullPointerException("Null values not supported!");
        }
        final ConfigUpdateEvent event = new ConfigUpdateEvent(this);
        // PRE
        event.setStage(EventStage.PRE);
        Shoreline.EVENT_HANDLER.dispatch(event);
        value = val;
        // POST
        event.setStage(EventStage.POST);
        Shoreline.EVENT_HANDLER.dispatch(event);
    }

    /**
     *
     */
    public void resetValue()
    {
        setValue(defaultValue);
    }

    /**
     * Initializes the {@link #container} field with the parent
     * {@link ConfigContainer}. This process will be handled everytime the
     * config is added to a container during the constructor of
     * {@link ConfigContainer#ConfigContainer(String)}.
     *
     * @param cont The parent container
     */
    public void setContainer(final ConfigContainer cont)
    {
        container = cont;
    }
}


