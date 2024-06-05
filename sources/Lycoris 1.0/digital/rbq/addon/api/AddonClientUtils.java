package digital.rbq.addon.api;

import com.soterdev.SoterObfuscator;
import digital.rbq.Lycoris;
import digital.rbq.addon.api.utils.Image;
import digital.rbq.irc.IRCClient;

/**
 * 客户端信息获取
 */
public class AddonClientUtils {
    static AddonClientUtils INSTANCE = new AddonClientUtils();

    /**
     * 获取客户端Utils实例
     *
     * @return Utils实例
     */
    public static AddonClientUtils getInstance() {
        return INSTANCE;
    }

    /**
     * 获取用户UID
     *
     * @return 用户UID
     */
    public int getUID() {
        return IRCClient.loggedPacket.getUid();
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return IRCClient.loggedPacket.getRealUsername();
    }

    /**
     * 获取用户头像Base64
     *
     * @return 用户头像Base64
     */
    public Image getUserAvatar() {
        return new Image(IRCClient.loggedPacket.getAvatarBase64());
    }

    /**
     * 获取客户端版本号
     *
     * @return 客户端版本号
     */
    public float getClientVersion() {
        return Lycoris.VERSION;
    }
}
