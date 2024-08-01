package wtf.diablo.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Constants {
    private Constants() {}

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
}
