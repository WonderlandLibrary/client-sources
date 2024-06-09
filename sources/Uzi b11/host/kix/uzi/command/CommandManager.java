package host.kix.uzi.command;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.commands.*;
import host.kix.uzi.management.ListManager;
import host.kix.uzi.utilities.minecraft.Logger;

/**
 * Created by myche on 2/3/2017.
 */
public class CommandManager extends ListManager<Command> {

    public void load() {
        System.out.println("Loading Command System...");
        addContent(new Toggle());
        addContent(new Banwave());
        addContent(new Add());
        addContent(new Remove());
        addContent(new Help());
        addContent(new Bind());
        addContent(new Ghost());
        addContent(new Crash());
        addContent(new TabSettings());
        addContent(new VClip());
        addContent(new Modules());
        addContent(new XrayCommand());
        addContent(new Admin());
        addContent(new WaypointsCommand());
        addContent(new ThemeCommand());
        addModuleCommands();
        System.out.println("Command System Finished Loading!");
    }

    private void addModuleCommands() {
        Uzi.getInstance().getModuleManager().getContents().forEach(module -> {
            if (!module.getValues().isEmpty()) {
                addContent(new Command(module.getName(), "Changes values for " + module.getName(), module.getName()) {
                    @Override
                    public void dispatch(String message) {
                        String[] args = message.split(" ");
                        module.getValues().forEach(value -> {
                            if (args[1].equalsIgnoreCase(value.getName())) {
                                if (value.getValue() instanceof Boolean) {
                                    value.setValue(!(Boolean) value.getValue());
                                }
                                if (value.getValue() instanceof Integer) {
                                    value.setValue(Integer.parseInt(args[2]));
                                }
                                if (value.getValue() instanceof Double) {
                                    value.setValue(Double.parseDouble(args[2]));
                                }
                                if (value.getValue() instanceof Float) {
                                    value.setValue(Float.parseFloat(args[2]));
                                }
                                if (value.getValue() instanceof String) {
                                    value.setValue(args[2]);
                                }
                            }
                            if (args[1].equalsIgnoreCase("Help")) {
                                Logger.logToChat(value.getName());
                            }
                        });
                    }
                });
            }
        });
    }

}
