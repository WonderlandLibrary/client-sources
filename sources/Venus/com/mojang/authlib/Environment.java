/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib;

import java.util.StringJoiner;

public interface Environment {
    public String getAuthHost();

    public String getAccountsHost();

    public String getSessionHost();

    public String getServicesHost();

    public String getName();

    public String asString();

    public static Environment create(String string, String string2, String string3, String string4, String string5) {
        return new Environment(string, string2, string3, string4, string5){
            final String val$auth;
            final String val$account;
            final String val$session;
            final String val$services;
            final String val$name;
            {
                this.val$auth = string;
                this.val$account = string2;
                this.val$session = string3;
                this.val$services = string4;
                this.val$name = string5;
            }

            @Override
            public String getAuthHost() {
                return this.val$auth;
            }

            @Override
            public String getAccountsHost() {
                return this.val$account;
            }

            @Override
            public String getSessionHost() {
                return this.val$session;
            }

            @Override
            public String getServicesHost() {
                return this.val$services;
            }

            @Override
            public String getName() {
                return this.val$name;
            }

            @Override
            public String asString() {
                return new StringJoiner(", ", "", "").add("authHost='" + this.getAuthHost() + "'").add("accountsHost='" + this.getAccountsHost() + "'").add("sessionHost='" + this.getSessionHost() + "'").add("servicesHost='" + this.getServicesHost() + "'").add("name='" + this.getName() + "'").toString();
            }
        };
    }
}

