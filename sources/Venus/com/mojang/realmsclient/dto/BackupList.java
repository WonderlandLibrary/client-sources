/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.ValueObject;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupList
extends ValueObject {
    private static final Logger field_230561_b_ = LogManager.getLogger();
    public List<Backup> field_230560_a_;

    public static BackupList func_230753_a_(String string) {
        JsonParser jsonParser = new JsonParser();
        BackupList backupList = new BackupList();
        backupList.field_230560_a_ = Lists.newArrayList();
        try {
            JsonElement jsonElement = jsonParser.parse(string).getAsJsonObject().get("backups");
            if (jsonElement.isJsonArray()) {
                Iterator<JsonElement> iterator2 = jsonElement.getAsJsonArray().iterator();
                while (iterator2.hasNext()) {
                    backupList.field_230560_a_.add(Backup.func_230750_a_(iterator2.next()));
                }
            }
        } catch (Exception exception) {
            field_230561_b_.error("Could not parse BackupList: " + exception.getMessage());
        }
        return backupList;
    }
}

