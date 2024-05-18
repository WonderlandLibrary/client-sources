package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.MathHelper;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.commands.*;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.mod.mods.misc.Commands;
import pw.latematt.xiv.mod.mods.misc.Keybinds;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.utils.EntityUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Matthew
 */
public class CommandManager extends ListManager<Command> {
    private String prefix = ".";
    private final Minecraft mc = Minecraft.getMinecraft();

    public CommandManager() {
        super(new ArrayList<>());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setup() {
        // setup is not required in command management, commands will add themselves as they are built
        // however, we do add certain commands here

        Command.newCommand().cmd("help").description("Provides help with commands.").aliases("cmds", "?").arguments("[command]")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String commandName = arguments[1];
                        Command command = XIV.getInstance().getCommandManager().find(commandName);
                        if (command != null) {
                            ChatLogger.print(String.format("Help for %s:", command.getCmd()));
                            if (command.getDescription() != null) {
                                ChatLogger.print(String.format("Description for %s: %s", command.getCmd(), command.getDescription()));
                            }
                            if (command.getAliases() != null) {
                                ChatLogger.print(String.format("Aliases for %s: %s", command.getCmd(), Arrays.asList(command.getAliases())));
                            }
                            if (command.getArguments() != null) {
                                ChatLogger.print(String.format("Arguments for %s: %s", command.getCmd(), Arrays.asList(command.getArguments())));
                            }
                        } else {
                            ChatLogger.print(String.format("Invalid command \"%s\"", commandName));
                        }
                    } else {
                        List<Command> commandList = XIV.getInstance().getCommandManager().getContents();
                        StringBuilder commands = new StringBuilder("Commands (" + commandList.size() + "): ");
                        for (Command command : commandList) {
                            commands.append(prefix).append(command.getCmd()).append(", ");
                        }
                        ChatLogger.print(commands.toString().substring(0, commands.length() - 2));
                    }
                }).build();
        Command.newCommand().cmd("vclip").description("Allows you to teleport up and down.").arguments("<blocks>").aliases("vc", "up", "down")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String blockChangeString = arguments[1];
                        try {
                            double newBlockChange = Double.parseDouble(blockChangeString);
                            mc.thePlayer.func_174826_a(mc.thePlayer.getEntityBoundingBox().offset(0, newBlockChange, 0));
                            ChatLogger.print(String.format("Teleported %s %s block%s", newBlockChange < 0 ? "down" : "up", newBlockChange, (newBlockChange > 1 || newBlockChange < -1) ? "s" : ""));
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", blockChangeString));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: vclip <blocks>");
                    }
                }).build();
        Command.newCommand().cmd("damage").description("Fall hard enough to lose a half of a heart.").aliases("dmg").arguments("<blocks>")
                .handler(message -> {
                    String[] arguments = message.split(" ");

                    if (arguments.length >= 2) {
                        String damageString = arguments[1];
                        try {
                            int damage = Integer.parseInt(damageString);
                            EntityUtils.damagePlayer(damage);
                        } catch (NumberFormatException e) {
                            ChatLogger.print(String.format("\"%s\" is not a number.", damageString));
                        }
                    } else if (arguments.length > 1) {
                        EntityUtils.damagePlayer(1);
                    }
                }).build();
        Command.newCommand().cmd("suicide").description("Fall to your untimely death.").aliases("kms", "bleach", "clorox").handler(message -> EntityUtils.damagePlayer(999)).build();
        Command.newCommand().cmd("say").description("Makes you send a chat message.").arguments("<message>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length > 1) {
                        mc.getNetHandler().getNetworkManager().sendPacket(new C01PacketChatMessage(message.substring(arguments[0].length() + 1, message.length())));
                    } else {
                        ChatLogger.print("Invalid arguments, valid: say <message>");
                    }
                }).build();
        Command.newCommand().cmd("echo").description("Makes a client message appear.").arguments("<message>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length > 1) {
                        ChatLogger.print(message.substring(arguments[0].length() + 1, message.length()));
                    } else {
                        ChatLogger.print("Invalid arguments, valid: echo <message>");
                    }
                }).build();
        Command.newCommand().cmd("clearchat").description("Clear your chat.").arguments("<action>").aliases("clear").handler(message -> mc.ingameGUI.getChatGUI().clearChatMessages()).build();
        Command.newCommand().cmd("alloff").description("Turn off all mods.").arguments("<action>").aliases("legit", "hide")
                .handler(message -> XIV.getInstance().getModManager().getContents().stream()
                        .filter(mod -> !(mod instanceof Commands || mod instanceof Keybinds))
                        .forEach(mod -> mod.setEnabled(false))).build();
        Command.newCommand().cmd("clearxiv").description("Clear your chat of client messages and client commands.").arguments("<action>").aliases("clearclient")
                .handler(message -> {
                    List<ChatLine> toRemoveLines = new ArrayList<>();
                    for (Object o : mc.ingameGUI.getChatGUI().getField_146253_i()) {
                        ChatLine line = (ChatLine) o;

                        if (line.getChatComponent().getUnformattedText().startsWith("\247g[XIV]")) {
                            toRemoveLines.add(line);
                        }
                    }

                    for (ChatLine line : toRemoveLines) {
                        mc.ingameGUI.getChatGUI().getField_146253_i().remove(line);
                    }

                    List<String> toRemoveSent = new ArrayList<>();
                    for (Object o : mc.ingameGUI.getChatGUI().getSentMessages()) {
                        String command = (String) o;

                        if (command.startsWith(getPrefix())) {
                            toRemoveSent.add(command);
                        }
                    }

                    for (String msg : toRemoveSent) {
                        mc.ingameGUI.getChatGUI().getSentMessages().remove(msg);
                    }
                }).build();

        Command.newCommand().cmd("rename").description("Rename your items.").arguments("<name>").aliases("ren")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (mc.thePlayer.getHeldItem() == null) {
                        ChatLogger.print("You must be holding an item to rename.");
                    } else {
                        if (!mc.thePlayer.capabilities.isCreativeMode) {
                            ChatLogger.print("You must be in creative mode to rename.");
                        } else {
                            if (arguments.length > 1) {
                                String name = message.substring(arguments[0].length() + 1, message.length()).replaceAll("&", "§");

                                mc.thePlayer.getHeldItem().setStackDisplayName(name);

                                if (name.equalsIgnoreCase("clear")) {
                                    mc.thePlayer.getHeldItem().clearCustomName();
                                }

                                ChatLogger.print("Renamed current item to: " + mc.thePlayer.getHeldItem().getDisplayName() + "§r.");
                            } else {
                                ChatLogger.print("Invalid arguments, valid: rename <name>");
                            }
                        }
                    }
                }).build();
        Command.newCommand().cmd("copyip").description("Copy the servers IP.").aliases("ci")
                .handler(message -> {
                    if (mc.isSingleplayer()) {
                        ChatLogger.print("Singleplayer does not have an IP.");
                    } else {
                        StringSelection contents = new StringSelection(mc.getCurrentServerData().serverIP);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(contents, null);

                        ChatLogger.print("Copied the servers IP to clipboard.");
                    }
                }).build();
        Command.newCommand().cmd("copycoords").description("Copy your current coordinates.").aliases("cc")
                .handler(message -> {
                    int x = MathHelper.floor_double(mc.thePlayer.posX);
                    int y = MathHelper.floor_double(mc.thePlayer.posY);
                    int z = MathHelper.floor_double(mc.thePlayer.posZ);
                    StringSelection contents = new StringSelection(String.format("%s %s %s", x, y, z));
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(contents, null);

                    ChatLogger.print("Copied your current coordinates to clipboard.");
                }).build();
        Command.newCommand().cmd("breed").description("Breed any animals around you.").handler(new Breed()).build();
        Command.newCommand().cmd("render").description("Manages options for render mods.").arguments("<action>").aliases("rnd").handler(new Render()).build();
        Command.newCommand().cmd("potion").description("Enhance your potions.").arguments("<effect> <level>").aliases("pot").handler(new Potion()).build();
        Command.newCommand().cmd("enchant").description("Enchante your items.").arguments("<enchantment> <level>").aliases("enc").handler(new Enchant()).build();
        Command.newCommand().cmd("history").description("Get the name history of people.").aliases("namehistory", "nh").handler(new History()).build();
        Command.newCommand().cmd("massmessage").description("Send a message to every player in the tab list.").arguments("<delay> <message>").aliases("masscommand", "mc", "masschat", "mm").handler(new MassMessage()).build();
        Command.newCommand().cmd("screenshot").description("Take a screenshot of your minecraft.").aliases("scr", "imgur", "image", "img").handler(new Screenshot()).build();
        Command.newCommand().cmd("fillworldedit").description("Use world edit in 1.8 servers that don't have world edit.").aliases("fwe", "we", "//", "worldedit").handler(new FillWorldEdit()).build();
        Command.newCommand().cmd("pluginfinder").description("Find plugins the server has.").arguments("<action>").aliases("pl", "pf").handler(new PluginFinder()).build();
        Command.newCommand().cmd("drown").description("Drowns you faster than usual.").handler(new Drown()).build();
        Command.newCommand().cmd("forward").description("Teleport forward a few blocks.").arguments("<blocks>").aliases("fwd").handler(new Forward()).build();

        Command.newCommand().cmd("prefix").description("Changes your command prefix").arguments("<new prefix>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String newPrefix = message.substring((String.format("%s ", arguments[0])).length(), (String.format("%s ", arguments[0])).length() + 1);
                        if (!newPrefix.equals("") && !newPrefix.equals(" ")) {
                            prefix = newPrefix;
                        }
                        ChatLogger.print(String.format("Chat Prefix set to: %s", prefix));
                    }
                }).build();

        new XIVFile("commandPrefix", "cfg") {
            @Override
            public void load() throws IOException {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                prefix = reader.readLine();
            }

            @Override
            public void save() throws IOException {
                Files.write(getPrefix().getBytes("UTF-8"), file);
            }
        };
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public boolean parseCommand(String message) {
        String[] spaceSplit = message.split(" ");
        if (spaceSplit[0].startsWith(prefix)) {
            for (Command command : contents) {
                if (spaceSplit[0].equalsIgnoreCase(prefix + command.getCmd())) {
                    command.getHandler().onCommandRan(message);
                    return true;
                }

                if (!Objects.isNull(command.getAliases())) {
                    for (String alias : command.getAliases()) {
                        if (spaceSplit[0].equalsIgnoreCase(prefix + alias)) {
                            command.getHandler().onCommandRan(message);
                            return true;
                        }
                    }
                }
            }
            ChatLogger.print(String.format("Invalid command \"%s\"", spaceSplit[0]));
            return true;
        }
        return false;
    }

    public Command find(String name) {
        for (Command cmd : getContents()) {
            if (cmd.getCmd().equals(name)) {
                return cmd;
            }
        }

        return null;
    }
}
