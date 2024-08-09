/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TabCompleteStorage
implements StorableObject {
    private final Map<UUID, String> usernames = new HashMap<UUID, String>();
    private final Set<String> commands = new HashSet<String>();
    private int lastId;
    private String lastRequest;
    private boolean lastAssumeCommand;

    public Map<UUID, String> usernames() {
        return this.usernames;
    }

    public Set<String> commands() {
        return this.commands;
    }

    public int lastId() {
        return this.lastId;
    }

    public void setLastId(int n) {
        this.lastId = n;
    }

    public String lastRequest() {
        return this.lastRequest;
    }

    public void setLastRequest(String string) {
        this.lastRequest = string;
    }

    public boolean isLastAssumeCommand() {
        return this.lastAssumeCommand;
    }

    public void setLastAssumeCommand(boolean bl) {
        this.lastAssumeCommand = bl;
    }
}

