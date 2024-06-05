package net.shoreline.client.api.command.arg.arguments;

import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.ConfigContainer;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author linus
 * @since 1.0
 */
public class ConfigArgument extends Argument<Config<?>> {
    //
    private ConfigContainer container;
    private ArrayList<String> configNames;

    /**
     * Initializes the config with a default value. This constructor should
     * not be used to initialize a configuration, instead use the explicit
     * definitions of the configs in {@link com.caspian.client.api.config.setting}.
     *
     * @param name The unique config identifier
     * @param desc The config description
     * @throws NullPointerException if value is <tt>null</tt>
     */
    public ConfigArgument(String name, String desc) {
        super(name, desc);
    }

    /**
     * @param container The parent container
     */
    @Override
    public void setContainer(ConfigContainer container) {
        this.container = container;
    }

    /**
     * @return
     */
    @Override
    public Config<?> getValue() {
        String literal = getLiteral();
        if (literal == null) {
            return null;
        }
        for (Config<?> config : container.getConfigs()) {
            if (config.getName().equalsIgnoreCase(literal)) {
                return config;
            }
        }
        ChatUtil.error("Failed to parse config!");
        return null;
    }

    /**
     * @return
     * @see #getSuggestion()
     */
    @Override
    public Collection<String> getSuggestions() {
        if (configNames != null) {
            return configNames;
        }
        configNames = new ArrayList<>();
        for (Config<?> config : container.getConfigs()) {
            configNames.add(config.getName().toLowerCase());
        }
        return configNames;
    }
}
