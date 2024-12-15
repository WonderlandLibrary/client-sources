package rip.vantage.commons.util.packet;

import rip.vantage.commons.packet.api.interfaces.IPacket;
import rip.vantage.commons.util.encryption.AESUtil;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class PacketUtil {

    public static String exportEncrypted(IPacket<?> packet, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
        try {
            String exported = packet.export();
            return AESUtil.encrypt(exported, secretKey, ivParameterSpec);
        } catch (Exception e) {
            System.out.println("Failed to encrypt packet, usually this is because Rise doesn't have access to the internet.");
            return "";
        }
    }

}
