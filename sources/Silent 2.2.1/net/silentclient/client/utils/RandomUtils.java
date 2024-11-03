package net.silentclient.client.utils;

import java.security.SecureRandom;

import io.netty.util.internal.ThreadLocalRandom;

public class RandomUtils {
	public static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	public static String randomString(int len)
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
}
