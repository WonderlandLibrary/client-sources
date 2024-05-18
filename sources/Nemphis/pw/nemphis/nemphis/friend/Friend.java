/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.friend;

public class Friend {
    private String username;
    private String alias;

    public Friend(String username) {
        this.username = username;
    }

    public Friend(String username, String alias) {
        this.username = username;
        this.alias = alias;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAlias() {
        return this.alias;
    }
}

