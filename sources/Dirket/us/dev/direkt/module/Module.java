package us.dev.direkt.module;

import us.dev.api.interfaces.Aliased;
import us.dev.api.interfaces.Labeled;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.command.handler.exceptions.ArgumentParseException;
import us.dev.direkt.command.handler.handling.ArgumentHandler;
import us.dev.direkt.command.handler.handling.parameter.CommandParameter;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.property.ModProperty;
import us.dev.direkt.module.property.Propertied;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public abstract class Module implements Labeled, Aliased {
    private final String label;
    private final String[] aliases;
    private final int color;
    private final ModCategory category;

    public Module() {
        if (!this.getClass().isAnnotationPresent(ModData.class)) {
            throw new RuntimeException("Module " + this.getClass().getSimpleName() + " is malformed, no ModuleData annotation found");
        }
        final ModData data = this.getClass().getDeclaredAnnotation(ModData.class);

        this.label = data.label();
        this.aliases = data.aliases();
        this.category = data.category();
        this.color = data.color() != Integer.MIN_VALUE ? data.color() : this.category.getDefaultColor();
    }

    protected void addCommand(final Command command) {
        Direkt.getInstance().getCommandManager().register(command);
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String[] getAliases() {
        return this.aliases;
    }

    public int getColor() {
        return this.color;
    }

    public ModCategory getCategory() {
        return this.category;
    }

    public abstract class ModuleCommand extends Command {
        public ModuleCommand(CommandManager manager, String label, String[] aliases) {
            super(manager, label, aliases);
        }

        @Executes({"values|vals", "set|s"})
        public String setProperty(String propertyName, String newValue) {
            final Set<ModProperty> properties = ((Propertied) Module.this).getProperties();
            for (ModProperty property : properties) {
                if (property.getLabel().equalsIgnoreCase(propertyName)) {
                    Object parsedValue = parse(property.getType(), newValue);
                    if (parsedValue != null) {
                        property.setValue(parsedValue);
                        return "Set value of " + Module.this.getLabel() + " property \"" + property.getLabel()
                                + "\" to " + property.getValue();
                    } else {
                        return "Failed to parse value of type " + property.getType().getSimpleName()
                                + " from input \"" + newValue + "\"";
                    }
                }
            }
            return "Invalid property name \"" + propertyName
                    + "\", valid properties for " + Module.this.getLabel() + " are: "
                    + properties.stream().map(ModProperty::getLabel).collect(Collectors.joining(", "));
        }

        @Executes({"values|vals", "get|g"})
        public String getProperty(String propertyName) {
            final Set<ModProperty> properties = ((Propertied) Module.this).getProperties();
            for (ModProperty property : properties) {
                if (property.getLabel().equalsIgnoreCase(propertyName)) {
                    return "Value of " + Module.this.getLabel() + " property \"" + property.getLabel()
                            + "\" is: " + property.getValue();
                }
            }
            return "Invalid property name \"" + propertyName
                    + "\", valid properties for " + Module.this.getLabel() + " are: "
                    + properties.stream().map(ModProperty::getLabel).collect(Collectors.joining(", "));
        }

        @Executes({"values|vals", "list|l"})
        public String listProperties() {
            return "Properties of " + Module.this.getLabel() + ":" + System.lineSeparator()
                    + ((Propertied) Module.this).getProperties().stream()
                    .map(property -> {
                        if (property.getProperty() instanceof MultiProperty) {
                            return "[" + property.getLabel() + " - "
                                    + ((Map<String, Property>) property.getValue()).values().stream()
                                    .map(this::formatPropertyString)
                                    .collect(Collectors.joining(",\n    ", "(", ")")) + "]";
                        } else {
                            return formatPropertyString(property.getProperty());
                        }
                    })
                    .collect(Collectors.joining("," + System.lineSeparator()));
        }

        private <T> T parse(Class<T> type, String input) {
            Optional<ArgumentHandler<T>> handlerLookup = commandManager.findArgumentHandler(type);
            if (handlerLookup.isPresent()) {
                try {
                    return handlerLookup.get().parse(commandManager, new CommandParameter.Transient(type), input);
                } catch (ArgumentParseException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        private String formatPropertyString(Property<?> property) {
            if (Enum.class.isAssignableFrom(property.getType())) {
                return property.getLabel() + "(" + "One of " + Arrays.toString(property.getType().getEnumConstants()) + ")" + " - " + property.getValue() + "]";
            } else {
                return property.getLabel() + "(" + property.getType().getSimpleName() + ")" + " - " + property.getValue() + "]";
            }
        }
    }

}
