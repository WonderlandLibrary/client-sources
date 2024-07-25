package club.bluezenith.module;

import club.bluezenith.command.Command;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.*;
import net.minecraft.client.gui.FontRenderer;

import java.util.List;
import java.util.regex.Pattern;

public class ModuleCommand extends Command {
    private final Module parent;
    public ModuleCommand(Module parent, String... pref) {
        super(parent.getName(), "Auto-generated command.", ".module valuename valueparameter", pref);
        this.parent = parent;
    }
    @Override
    public void execute(String[] args){
        if(args.length > 1){
            Value<?> value = parent.getValue(args[1]);
            if(value == null){
                chat("Invalid value!");
                return;
            }
            if(args.length <= 2) return;
            if(args[2].equalsIgnoreCase("get")) {
                chat(String.format("\"%s\" is set to %s", value.name, value.get()));
                return;
            }
            if (value instanceof BooleanValue) {
                BooleanValue v = (BooleanValue) value;
                v.next();
                chat("Set "+v.name+" to "+v.get());
                changedSound();
            }else {
                if(value instanceof ModeValue) {
                    ModeValue v = (ModeValue) value;
                    String result = v.find(args[2]);
                    if(result != null) {
                        v.set(result);
                        changedSound();
                    } else {
                        chat("Illegal argument: " + args[2] + " is not in the " + value.name + " possible values range.");
                    }
                }else if(value instanceof StringValue){
                    StringValue v = (StringValue) value;
                    v.set(args[2]);
                    chat("Set "+v.name+" to "+v.get());
                    changedSound();
                }else if(!Pattern.matches("[a-zA-Z]+", args[2])){
                    if(value instanceof FloatValue){
                        FloatValue v = (FloatValue) value;
                        final float val = Float.parseFloat(args[2]);
                        if(val > v.max || val < v.min) {
                            chat(String.format("\"%s\" value is out of bounds! Min %s, Max %s", v.name, v.min, v.max));
                        }
                        v.set(val);
                        chat("Set "+v.name+" to "+v.get());
                        changedSound();
                    }else if(value instanceof IntegerValue){
                        try {
                            IntegerValue v = (IntegerValue) value;
                            final int val = Integer.parseInt(args[2]);
                            if (val > v.max || val < v.min) {
                                chat(String.format("\"%s\" value is out of bounds! Min %s, Max %s", v.name, v.min, v.max));
                            }
                            v.set(val);
                            chat("Set " + v.name + " to " + v.get());
                            changedSound();
                        } catch (Exception exception) {
                            chat("Invalid number provided!");
                        }
                    }
                }else{
                    printSyntax();
                }
            }
        }else{
            printSyntax();
        }
    }
    private final FontRenderer download = mc.fontRendererObj;
    private void printSyntax(){
        List<Value<?>> f = this.parent.getValues();
        StringBuilder die = new StringBuilder();
        die.append("<");
        chat(String.format("Syntax: .%s", name));
        for (Value<?> v : f) {
            float d = download.getStringWidthF(v.name);
            if(download.getStringWidthF(die.toString()) + d > 240){
                chat(die.toString());
                die = new StringBuilder();
            }
            die.append(v.name);
            if(v != f.get(f.size() - 1)){
                die.append(", ");
            }
        }
        chat(die.append("> <value>").toString());
    }
}
