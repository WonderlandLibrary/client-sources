/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.server.management.UserListEntryBan;

public class UserListIPBansEntry
extends UserListEntryBan<String> {
    public UserListIPBansEntry(String valueIn) {
        this(valueIn, (Date)null, (String)null, (Date)null, (String)null);
    }

    public UserListIPBansEntry(String valueIn, Date startDate, String banner, Date endDate, String banReason) {
        super(valueIn, startDate, banner, endDate, banReason);
    }

    public UserListIPBansEntry(JsonObject json) {
        super(UserListIPBansEntry.getIPFromJson(json), json);
    }

    private static String getIPFromJson(JsonObject json) {
        return json.has("ip") ? json.get("ip").getAsString() : null;
    }

    @Override
    protected void onSerialization(JsonObject data) {
        if (this.getValue() != null) {
            data.addProperty("ip", (String)this.getValue());
            super.onSerialization(data);
        }
    }
}

