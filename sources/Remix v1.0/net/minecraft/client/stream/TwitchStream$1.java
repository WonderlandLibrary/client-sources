package net.minecraft.client.stream;

import com.mojang.authlib.properties.*;
import java.net.*;
import net.minecraft.util.*;
import tv.twitch.*;
import java.io.*;
import com.google.gson.*;

class TwitchStream$1 extends Thread {
    final /* synthetic */ Property val$p_i46057_2_;
    
    @Override
    public void run() {
        try {
            final URL var1 = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(this.val$p_i46057_2_.getValue(), "UTF-8"));
            final String var2 = HttpUtil.get(var1);
            final JsonObject var3 = JsonUtils.getElementAsJsonObject(new JsonParser().parse(var2), "Response");
            final JsonObject var4 = JsonUtils.getJsonObject(var3, "token");
            if (JsonUtils.getJsonObjectBooleanFieldValue(var4, "valid")) {
                final String var5 = JsonUtils.getJsonObjectStringFieldValue(var4, "user_name");
                TwitchStream.access$000().debug(TwitchStream.field_152949_a, "Authenticated with twitch; username is {}", new Object[] { var5 });
                final AuthToken var6 = new AuthToken();
                var6.data = this.val$p_i46057_2_.getValue();
                TwitchStream.access$100(TwitchStream.this).func_152818_a(var5, var6);
                TwitchStream.access$200(TwitchStream.this).func_152998_c(var5);
                TwitchStream.access$200(TwitchStream.this).func_152994_a(var6);
                Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook") {
                    @Override
                    public void run() {
                        TwitchStream.this.shutdownStream();
                    }
                });
                TwitchStream.access$100(TwitchStream.this).func_152817_A();
                TwitchStream.access$200(TwitchStream.this).func_175984_n();
            }
            else {
                TwitchStream.access$302(TwitchStream.this, AuthFailureReason.INVALID_TOKEN);
                TwitchStream.access$000().error(TwitchStream.field_152949_a, "Given twitch access token is invalid");
            }
        }
        catch (IOException var7) {
            TwitchStream.access$302(TwitchStream.this, AuthFailureReason.ERROR);
            TwitchStream.access$000().error(TwitchStream.field_152949_a, "Could not authenticate with twitch", (Throwable)var7);
        }
    }
}