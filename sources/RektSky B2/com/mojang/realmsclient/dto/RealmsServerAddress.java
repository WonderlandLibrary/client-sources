package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class RealmsServerAddress extends ValueObject
{
    private static final Logger LOGGER;
    public String address;
    
    public static RealmsServerAddress parse(final String json) {
        final JsonParser parser = new JsonParser();
        final RealmsServerAddress serverAddress = new RealmsServerAddress();
        try {
            final JsonObject object = parser.parse(json).getAsJsonObject();
            serverAddress.address = JsonUtils.getStringOr("address", object, null);
        }
        catch (Exception e) {
            RealmsServerAddress.LOGGER.error("Could not parse McoServerAddress: " + e.getMessage());
        }
        return serverAddress;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
