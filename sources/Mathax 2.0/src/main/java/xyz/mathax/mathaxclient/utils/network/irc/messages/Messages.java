package xyz.mathax.mathaxclient.utils.network.irc.messages;

import xyz.mathax.mathaxclient.utils.misc.CryptUtils;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Messages {
    public static Message auth(String pub_key, SecretKey secret, int iv) {
        try {
            String key = Base64.getEncoder().encodeToString(secret.getEncoded());
            Message message = new Message(MessageType.AUTH);
            message.data.put("token", CryptUtils.encryptRSA(iv + "|" + key, pub_key));
            return message;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static Message broadcast(String from, String message) {
        Message ircMessage = new Message(MessageType.BROADCAST);
        ircMessage.data.put("from", from);
        ircMessage.data.put("message", message);
        return ircMessage;
    }

    public static Message directMessage(String from, String to, String message) {
        Message ircMessage = new Message(MessageType.DIRECT_MESSAGE);
        ircMessage.data.put("from", from);
        ircMessage.data.put("to", to);
        ircMessage.data.put("message", message);
        return ircMessage;
    }

    public static Message ping() {
        return new Message(MessageType.PING);
    }
}
