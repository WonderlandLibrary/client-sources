/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.server.management.BanEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class IPBanEntry
extends BanEntry<String> {
    public IPBanEntry(String string) {
        this(string, (Date)null, (String)null, (Date)null, (String)null);
    }

    public IPBanEntry(String string, @Nullable Date date, @Nullable String string2, @Nullable Date date2, @Nullable String string3) {
        super(string, date, string2, date2, string3);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent((String)this.getValue());
    }

    public IPBanEntry(JsonObject jsonObject) {
        super(IPBanEntry.getIPFromJson(jsonObject), jsonObject);
    }

    private static String getIPFromJson(JsonObject jsonObject) {
        return jsonObject.has("ip") ? jsonObject.get("ip").getAsString() : null;
    }

    @Override
    protected void onSerialization(JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("ip", (String)this.getValue());
            super.onSerialization(jsonObject);
        }
    }
}

