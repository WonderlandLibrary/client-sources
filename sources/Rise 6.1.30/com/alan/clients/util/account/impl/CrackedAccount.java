package com.alan.clients.util.account.impl;

import com.alan.clients.util.account.Account;
import com.alan.clients.util.account.AccountType;

import java.util.UUID;

public class CrackedAccount extends Account {
    public CrackedAccount(String name) {
        super(AccountType.CRACKED, name, getUUID(name), "accessToken");
    }

    /**
     * Converts cracked name to UUID.
     *
     * @param name Cracked name.
     * @return UUID.
     */
    private static String getUUID(String name) {
        String s = "OfflinePlayer:" + name;
        return UUID.nameUUIDFromBytes(s.getBytes()).toString().replace("-", "");
    }
}
