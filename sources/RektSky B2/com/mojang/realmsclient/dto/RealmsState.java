package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class RealmsState
{
    private static final Logger LOGGER;
    private String statusMessage;
    private String buyLink;
    
    public static RealmsState parse(final String json) {
        final RealmsState realmsState = new RealmsState();
        try {
            final JsonParser parser = new JsonParser();
            final JsonObject jsonObject = parser.parse(json).getAsJsonObject();
            realmsState.statusMessage = JsonUtils.getStringOr("statusMessage", jsonObject, null);
            realmsState.buyLink = JsonUtils.getStringOr("buyLink", jsonObject, null);
        }
        catch (Exception e) {
            RealmsState.LOGGER.error("Could not parse RealmsState: " + e.getMessage());
        }
        return realmsState;
    }
    
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    public String getBuyLink() {
        return this.buyLink;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
