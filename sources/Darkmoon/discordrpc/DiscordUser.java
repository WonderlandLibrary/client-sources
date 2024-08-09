package discordrpc;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * @author HypherionSA
 * Class representing the Discord User
 */
public class DiscordUser extends Structure {

    // The User ID of the User
    public String userId;

    // The Username of the User
    public String username;

    // The unique identifier of the user. Discontinued by Discord
    @Deprecated
    public String discriminator;

    // The avatar has of the user
    public String avatar;

    /**
     * DO NOT TOUCH THIS... EVER!
     */
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
              "userId",
              "username",
              "discriminator",
              "avatar"
        );
    }
}
