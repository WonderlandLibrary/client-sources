/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

public final class CommandException
extends RuntimeException {
    private final String message;

    public CommandException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof CommandException)) {
            return true;
        }
        CommandException commandException = (CommandException)object;
        if (!commandException.canEqual(this)) {
            return true;
        }
        String string = this.getMessage();
        String string2 = commandException.getMessage();
        return string == null ? string2 != null : !string.equals(string2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof CommandException;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        String string = this.getMessage();
        n2 = n2 * 59 + (string == null ? 43 : string.hashCode());
        return n2;
    }

    @Override
    public String toString() {
        return "CommandException(message=" + this.getMessage() + ")";
    }
}

