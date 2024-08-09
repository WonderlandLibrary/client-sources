package xyz.mathax.mathaxclient.utils.network.irc.messages;

import org.json.JSONObject;
import xyz.mathax.mathaxclient.utils.misc.CryptUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;

import javax.crypto.SecretKey;

public class Message implements ISerializable<Message> {
    public MessageType type;

    public JSONObject data;

    public Message(MessageType type) {
        this.type = type;
        this.data = new JSONObject();
    }

    public Message(JSONObject json) {
        fromJson(json);
    }

    public Message encrypt(SecretKey secret, int iv) {
        try {
            if (data.has("message")) {
                data.put("message", CryptUtils.encryptAES(data.getString("message"), secret, iv));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this;
    }

    public Message decrypt(SecretKey secret, int iv) {
        try {
            if (data.has("message")) {
                data.put("message", CryptUtils.decryptAES(data.getString("message"), secret, iv));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type.toString());
        json.put("data", data);
        return json;
    }

    @Override
    public Message fromJson(JSONObject json) {
        if (json.has("type") && json.has("data")) {
            try {
                type = MessageType.valueOf(json.getString("type"));
            } catch (IllegalArgumentException ignored) {}

            data = json.getJSONObject("data");
        }

        return this;
    }
}