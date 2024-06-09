// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.altloader;

import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonObject;

final class Api$1 implements Runnable
{
    final String val$token;
    final Callback val$callback;
    
    Api$1(final String string, final Callback callback) {
        this.val$token = string;
        this.val$callback = callback;
    }
    
    @Override
    public void run() {
        final HttpsURLConnection connection = Api.preparePostRequest("https://api.easymc.io/v1/token/redeem", "{\"token\":\"" + this.val$token + "\",\"client\":\"mod-1.8.9\"}");
        if (connection == null) {
            this.val$callback.done("Could not create Connection. Please try again later.");
            return;
        }
        final Object o = Api.getResult(connection);
        if (o instanceof String) {
            this.val$callback.done(o);
            return;
        }
        final JsonObject jsonObject = (JsonObject)o;
        final RedeemResponse response = new RedeemResponse();
        response.session = jsonObject.get("session").getAsString();
        response.mcName = jsonObject.get("mcName").getAsString();
        response.uuid = jsonObject.get("uuid").getAsString();
        this.val$callback.done(response);
    }
}
