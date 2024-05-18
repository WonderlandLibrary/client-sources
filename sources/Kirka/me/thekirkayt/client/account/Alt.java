/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.account;

public class Alt {
    public String email;
    public String username;
    public String password;

    public Alt(String email, String name, String pass) {
        this.email = email;
        this.username = name;
        this.password = pass;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
}

