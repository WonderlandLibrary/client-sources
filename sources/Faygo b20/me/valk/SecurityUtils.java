package me.valk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import me.valk.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IlIlIlIlIlIlIlIlIlIlIlIlIlIl;

public class SecurityUtils {
	
	private static Scanner in;
	
    public static String getURLSource(String link) {
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder str = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                str.append(inputLine);
            }
            reader.close();
            return str.toString();
        }
        catch (Exception e) {
        	throw new RuntimeException("Couldn't properly connect to internet.");
        }
    }
    
    public static void checkUUID(String link) {
        try {
			if (!getURLSource(link).contains(IlIlIlIlIlIlIlIlIlIlIlIlIlIl.getHwid())) {
				Wrapper.getMinecraft().shutdownMinecraftApplet();
			}
			if (!getURLSource(link).contains(IlIlIlIlIlIlIlIlIlIlIlIlIlIl.getHwid())) {
				Wrapper.getMinecraft().shutdown();
                Minecraft.getMinecraft().shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
