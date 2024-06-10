package me.kaimson.melonclient.cosmetics;

import me.kaimson.melonclient.*;
import me.kaimson.melonclient.database.*;
import java.sql.*;
import java.util.*;
import com.google.gson.*;
import com.google.common.collect.*;

public class CosmeticManager
{
    public static final HashMap<String, CosmeticData> cosmetics;
    
    public static void init() {
        Client.info("Initializing connection...", new Object[0]);
        final Database.Connection connection = Database.INSTANCE.initConnection();
        try {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `cosmetics`");
            try {
                final ResultSet result = statement.executeQuery();
                try {
                    if (result != null) {
                        while (result.next()) {
                            final List<CosmeticList> cosmeticList = (List<CosmeticList>)Lists.newArrayList();
                            for (final String cosmetic : result.getObject("id").toString().split(",")) {
                                cosmeticList.add(CosmeticList.valueOf(cosmetic));
                            }
                            final String data = result.getString("data");
                            final HashMap<String, CosmeticData> cosmetics = CosmeticManager.cosmetics;
                            final String string = result.getString("uuid");
                            final List<CosmeticList> cosmetics2 = cosmeticList;
                            final String data2 = data;
                            jy capeTexture;
                            if (cosmeticList.contains(CosmeticList.CAPES)) {
                                final StringBuilder sb;
                                capeTexture = new jy(sb.append("melonclient/capes/").append(getCapeName(data)).append(".png").toString());
                                sb = new StringBuilder();
                            }
                            else {
                                capeTexture = null;
                            }
                            cosmetics.put(string, new CosmeticData(cosmetics2, data2, capeTexture));
                        }
                    }
                }
                finally {
                    if (Collections.singletonList(result).get(0) != null) {
                        result.close();
                    }
                }
            }
            finally {
                if (Collections.singletonList(statement).get(0) != null) {
                    statement.close();
                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Database.INSTANCE.close();
        Client.info("Cosmetics fetched!", new Object[0]);
    }
    
    public static boolean hasCape(final String uuid) {
        return CosmeticManager.cosmetics.containsKey(uuid) && CosmeticManager.cosmetics.get(uuid).getCosmetics().stream().anyMatch(cosmetic -> cosmetic == CosmeticList.CAPES);
    }
    
    public static String getCapeName(final String json) {
        return new JsonParser().parse(json).getAsJsonObject().get("cape").getAsString();
    }
    
    static {
        cosmetics = Maps.newHashMap();
    }
    
    enum CosmeticList
    {
        CAPES, 
        WINGS, 
        BACK_TOOL;
    }
}
