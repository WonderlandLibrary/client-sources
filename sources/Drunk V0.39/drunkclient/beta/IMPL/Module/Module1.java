/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module;

import drunkclient.beta.API.commands.Command;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.IMPL.set.Value;
import drunkclient.beta.UTILS.Math.MathUtil;
import drunkclient.beta.UTILS.helper.Helper;

class Module1
extends Command {
    private final Module m;
    final Module this$0;

    Module1(Module var1, String $anonymous0, String[] $anonymous1, String $anonymous2, String $anonymous3) {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
        this.this$0 = var1;
        this.m = var1;
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 2) {
            Value option = null;
            Numbers pies = null;
            Mode pies2 = null;
            for (Value v : this.m.values) {
                if (!(v instanceof Option) || !v.getName().equalsIgnoreCase(args[0])) continue;
                option = (Option)v;
            }
            if (option != null) {
                option.setValue((Boolean)option.getValue() == false);
                Helper.sendMessage(String.format("%s has been set to %s", option.getName(), option.getValue()));
            } else {
                for (Value v : this.m.values) {
                    if (!(v instanceof Numbers) || !v.getName().equalsIgnoreCase(args[0])) continue;
                    pies = (Numbers)v;
                }
                if (pies != null) {
                    if (MathUtil.parsable(args[1], (byte)4)) {
                        double v1 = MathUtil.round(Double.parseDouble(args[1]), 1);
                        pies.setValue(v1 > ((Number)pies.getMaximum()).doubleValue() ? ((Number)pies.getMaximum()).doubleValue() : v1);
                        Helper.sendMessage(String.format("> %s has been set to %s", pies.getName(), pies.getValue()));
                    } else {
                        Helper.sendMessage("> " + args[1] + " is not a number");
                    }
                }
                for (Value v : this.m.values) {
                    if (!args[0].equalsIgnoreCase(v.getDisplayName()) || !(v instanceof Mode)) continue;
                    pies2 = (Mode)v;
                }
                if (pies2 != null) {
                    if (pies2.isValid(args[1])) {
                        pies2.setMode(args[1]);
                        Helper.sendMessage(String.format("> %s set to %s", pies2.getName(), pies2.getModeAsString()));
                    } else {
                        Helper.sendMessage("> " + args[1] + " is an invalid mode");
                    }
                }
            }
            if (pies != null) return null;
            if (option != null) return null;
            if (pies2 != null) return null;
            this.syntaxError("Valid .<module> <setting> <mode if needed>");
            return null;
        }
        if (args.length < 1) {
            Helper.sendMessage(String.format("%s Values: \n %s", this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getSyntax(), "false"));
            return null;
        }
        Value option = null;
        for (Value fuck1 : this.m.values) {
            if (!(fuck1 instanceof Option) || !fuck1.getName().equalsIgnoreCase(args[0])) continue;
            option = (Option)fuck1;
        }
        if (option == null) {
            this.syntaxError("Valid .<module> <setting> <mode if needed>");
            return null;
        }
        option.setValue((Boolean)option.getValue() == false);
        String pies22 = option.getName().substring(1);
        String pies23 = option.getName().substring(0, 1).toUpperCase();
        if (((Boolean)option.getValue()).booleanValue()) {
            Helper.sendMessage(String.format("> %s has been set to \u00a7a%s", pies23 + pies22, option.getValue()));
            return null;
        }
        Helper.sendMessage(String.format("> %s has been set to \u00a7c%s", pies23 + pies22, option.getValue()));
        return null;
    }
}

