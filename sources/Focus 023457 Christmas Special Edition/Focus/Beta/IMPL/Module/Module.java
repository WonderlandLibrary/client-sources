package Focus.Beta.IMPL.Module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import Focus.Beta.IMPL.Module.impl.render.HUD;
import Focus.Beta.NOT.NotificationManager;
import Focus.Beta.NOT.NotificationType;
import org.lwjgl.input.Keyboard;

import Focus.Beta.Client;
import Focus.Beta.API.EventBus;
import Focus.Beta.API.commands.Command;
import Focus.Beta.IMPL.managers.FileManager;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.IMPL.set.Value;
import Focus.Beta.UTILS.Math.MathUtil;
import Focus.Beta.UTILS.helper.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class Module {
	 public String name;
     public String description;
	 private String suffix;
	 private int color;
	 private String[] alias;
	 private boolean enabled;
	 public boolean enabledOnStartup = false;
	 private int key;
	 public List<Value> values;
	 public Type type;
	 private boolean removed;
	 public static Minecraft mc = Minecraft.getMinecraft();
	 public static Random random = new Random();
	 
	 public Module(String name, String[] alias, Type type, String description) {
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.type = type;
        this.suffix = "";
        this.key = 0;
        this.removed = false;
        this.enabled = false;
        this.values = new ArrayList();
    }

    public Module() { }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean wasRemoved() {
        return this.removed;
    }
    
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getSuffix() {
        return this.suffix;
    }
    
    public void setSuffix(Object obj) {
        String suffix = obj.toString();
        if (suffix.isEmpty()) {
            this.suffix = suffix;
        } else {
            this.suffix = String.format("%s\u00a77", EnumChatFormatting.GRAY + suffix );
        }

    }
    boolean hasClicked = false;
    public void setEnabled(boolean enabled) {
        HUD hud = (HUD) ModuleManager.getModuleByName("Hud");
        this.enabled = enabled;
        if (enabled) {
            EventBus.getInstance().register( this );
            this.onEnable();
        } else {
            EventBus.getInstance().unregister( this );
            this.onDisable();
        }

    }
    
    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    protected void addValues(Value... values) {
        Value[] var5 = values;
        int var4 = values.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            Value value = var5[var3];
            this.values.add(value);
        }

    }

    public List<Value> getValues() {
        return this.values;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
        String content = "";

        Module m;
        for (Iterator var4 = Client.instance.getModuleManager().getModules().iterator(); var4.hasNext(); content = content + String.format(
                "%s:%s%s", m.getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator() )) {
            m = (Module) var4.next();
        }

        FileManager.save("Binds.txt", content, false);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
    
    public void makeCommand() {
        if (this.values.size() > 0) {
            String options = "";
            String other = "";
            Iterator var4 = this.values.iterator();

            Value v;
            while (var4.hasNext()) {
                v = (Value) var4.next();
                if (!(v instanceof Mode)) {
                    if (options.isEmpty()) {
                        options = options + v.getName();
                    } else {
                        options = options + String.format(", %s", v.getName() );
                    }
                }
            }

            var4 = this.values.iterator();

            while (true) {
                do {
                    if (!var4.hasNext()) {
                        Client.instance.getCommandManager().add(new Module1(this, this.name, this.alias, String.format(
                                "%s%s",
                                options.isEmpty() ? "" : String.format("%s,", options ),
                                other.isEmpty() ? "" : String.format("%s", other ) ),
                                "Setup this module"));
                        return;
                    }

                    v = (Value) var4.next();
                } while (!(v instanceof Mode));

                Mode mode = (Mode) v;
                Enum[] modes;
                int length = (modes = mode.getModes()).length;

                for (int i = 0; i < length; ++i) {
                    Enum e = modes[i];
                    if (other.isEmpty()) {
                        other = other + e.name().toLowerCase();
                    } else {
                        other = other + String.format(", %s", e.name().toLowerCase() );
                    }
                }
            }
        }
    }
}

class Module1 extends Command {
    private final Module m;
    final Module this$0;

    Module1(Module var1, String $anonymous0, String[] $anonymous1, String $anonymous2, String $anonymous3) {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
        this.this$0 = var1;
        this.m = var1;
    }
    
    public String execute(String[] args) {
        Option option;
        if (args.length >= 2) {
            option = null;
            Numbers pies = null;
            Mode pies2 = null;
            Iterator var6 = this.m.values.iterator();

            Value v;
            while (var6.hasNext()) {
                v = (Value) var6.next();
                if (v instanceof Option && v.getName().equalsIgnoreCase(args[0])) {
                    option = (Option) v;
                }
            }

            if (option != null) {
                option.setValue(Boolean.valueOf(!((Boolean) option.getValue()).booleanValue()));
                Helper.sendMessage(
                        String.format("%s has been set to %s", option.getName(), option.getValue() ));
            } else {
                var6 = this.m.values.iterator();

                while (var6.hasNext()) {
                    v = (Value) var6.next();
                    if (v instanceof Numbers && v.getName().equalsIgnoreCase(args[0])) {
                        pies = (Numbers) v;
                    }
                }

                if (pies != null) {
                    if (MathUtil.parsable(args[1], (byte) 4)) {
                        double v1 = MathUtil.round(Double.parseDouble(args[1]), 1);
                        pies.setValue( v1 > pies.getMaximum ().doubleValue ()
                                ? pies.getMaximum ().doubleValue ()
                                : v1 );
                        Helper.sendMessage(String.format("> %s has been set to %s",
                                pies.getName(), pies.getValue() ));
                    } else {
                        Helper.sendMessage("> " + args[1] + " is not a number");
                    }
                }

                var6 = this.m.values.iterator();

                while (var6.hasNext()) {
                    v = (Value) var6.next();
                    if (args[0].equalsIgnoreCase(v.getDisplayName()) && v instanceof Mode) {
                        pies2 = (Mode) v;
                    }
                }

                if (pies2 != null) {
                    if (pies2.isValid(args[1])) {
                    	pies2.setMode(args[1]);
                        Helper.sendMessage(
                                String.format("> %s set to %s", pies2.getName(), pies2.getModeAsString() ));
                    } else {
                        Helper.sendMessage("> " + args[1] + " is an invalid mode");
                    }
                }
            }

            if (pies == null && option == null && pies2 == null) {
                this.syntaxError("Valid .<module> <setting> <mode if needed>");
            }
        } else if (args.length >= 1) {
            option = null;
            Iterator xd1 = this.m.values.iterator();

            while (xd1.hasNext()) {
                Value fuck1 = (Value) xd1.next();
                if (fuck1 instanceof Option && fuck1.getName().equalsIgnoreCase(args[0])) {
                    option = (Option) fuck1;
                }
            }

            if (option != null) {
                option.setValue(Boolean.valueOf(!((Boolean) option.getValue()).booleanValue()));
                String pies22 = option.getName().substring(1);
                String pies23 = option.getName().substring(0, 1).toUpperCase();
                if (((Boolean) option.getValue()).booleanValue()) {
                    Helper.sendMessage(String.format("> %s has been set to \u00a7a%s",
                    		pies23 + pies22, option.getValue() ));
                } else {
                    Helper.sendMessage(String.format("> %s has been set to \u00a7c%s",
                    		pies23 + pies22, option.getValue() ));
                }
            } else {
                this.syntaxError("Valid .<module> <setting> <mode if needed>");
            }
        } else {
            Helper.sendMessage(String.format("%s Values: \n %s",
                    this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1).toLowerCase(),
                    this.getSyntax(), "false" ));
        }

        return null;
    }
}
