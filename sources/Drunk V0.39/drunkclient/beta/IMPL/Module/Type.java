/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module;

public enum Type {
    COMBAT("Combat"),
    RENDER("Render"),
    MOVE("Movement"),
    MISC("Misc"),
    EXPLOIT("Exploit"),
    DrunkClient("Drunk Client");

    String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

