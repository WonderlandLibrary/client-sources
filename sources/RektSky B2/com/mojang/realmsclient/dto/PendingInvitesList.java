package com.mojang.realmsclient.dto;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class PendingInvitesList extends ValueObject
{
    private static final Logger LOGGER;
    public List<PendingInvite> pendingInvites;
    
    public PendingInvitesList() {
        this.pendingInvites = (List<PendingInvite>)Lists.newArrayList();
    }
    
    public static PendingInvitesList parse(final String json) {
        final PendingInvitesList list = new PendingInvitesList();
        try {
            final JsonParser jsonParser = new JsonParser();
            final JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
            if (jsonObject.get("invites").isJsonArray()) {
                final Iterator<JsonElement> it = jsonObject.get("invites").getAsJsonArray().iterator();
                while (it.hasNext()) {
                    list.pendingInvites.add(PendingInvite.parse(it.next().getAsJsonObject()));
                }
            }
        }
        catch (Exception e) {
            PendingInvitesList.LOGGER.error("Could not parse PendingInvitesList: " + e.getMessage());
        }
        return list;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
