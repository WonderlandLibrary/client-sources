/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.commands;

import drunkclient.beta.UTILS.helper.Helper;

public abstract class Command {
    private String name;
    private String[] alias;
    private String syntax;
    private String help;

    public Command(String name, String[] alias, String syntax, String help) {
        this.name = name.toLowerCase();
        this.syntax = syntax.toLowerCase();
        this.help = help;
        this.alias = alias;
    }

    public abstract String execute(String[] var1);

    public String getName() {
        return this.name;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public String getSyntax() {
        return this.syntax;
    }

    public String getHelp() {
        return this.help;
    }

    public void syntaxError(String msg) {
        Helper.sendMessage(String.format("\u00a77Invalid command usage", msg));
    }

    public void syntaxError(byte errorType) {
        switch (errorType) {
            case 0: {
                this.syntaxError("bad argument");
                return;
            }
            case 1: {
                this.syntaxError("argument gay");
                return;
            }
        }
    }
}

