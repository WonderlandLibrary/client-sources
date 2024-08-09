/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.network.PacketBuffer;

public class CriterionProgress {
    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private Date obtained;

    public boolean isObtained() {
        return this.obtained != null;
    }

    public void obtain() {
        this.obtained = new Date();
    }

    public void reset() {
        this.obtained = null;
    }

    public Date getObtained() {
        return this.obtained;
    }

    public String toString() {
        return "CriterionProgress{obtained=" + (Serializable)(this.obtained == null ? "false" : this.obtained) + "}";
    }

    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.obtained != null);
        if (this.obtained != null) {
            packetBuffer.writeTime(this.obtained);
        }
    }

    public JsonElement serialize() {
        return this.obtained != null ? new JsonPrimitive(DATE_TIME_FORMATTER.format(this.obtained)) : JsonNull.INSTANCE;
    }

    public static CriterionProgress read(PacketBuffer packetBuffer) {
        CriterionProgress criterionProgress = new CriterionProgress();
        if (packetBuffer.readBoolean()) {
            criterionProgress.obtained = packetBuffer.readTime();
        }
        return criterionProgress;
    }

    public static CriterionProgress fromJson(String string) {
        CriterionProgress criterionProgress = new CriterionProgress();
        try {
            criterionProgress.obtained = DATE_TIME_FORMATTER.parse(string);
            return criterionProgress;
        } catch (ParseException parseException) {
            throw new JsonSyntaxException("Invalid datetime: " + string, parseException);
        }
    }
}

