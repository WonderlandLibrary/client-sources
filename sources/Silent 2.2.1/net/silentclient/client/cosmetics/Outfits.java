package net.silentclient.client.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.Requests;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Outfits {
    public static File outfitsDir;
    public static ArrayList<Outfit> outfits = new ArrayList<>();

    public static void loadOutfits() {
        outfits.clear();
        if(outfitsDir == null) {
            outfitsDir = new File(Minecraft.getMinecraft().mcDataDir, "SilentClient-Cosmetic-Outfits");
        }
        if(!outfitsDir.exists()) {
            outfitsDir.mkdirs();
        }
        Set<String> outfitsNames = Stream.of(outfitsDir.listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
        outfitsNames.forEach((name) -> {
            Client.logger.info("Loading Cosmetic Outfit: " + name);
            try {
                InputStream in = new FileInputStream(new File(outfitsDir, name));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer content = new StringBuffer();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                outfits.add(Client.getInstance().getGson().fromJson(content.toString(), Outfit.class));
                in.close();
            } catch (Exception err) {
                Client.logger.catching(err);
            }
        });
    }

    public static void createOutfit(Outfit outfit) {
        Client.logger.info("Creating Outfit: " + outfit.name);
        try {
            File outfitFile = new File(outfitsDir, outfit.name + ".json");

            if(!outfitFile.exists()) {
                outfitFile.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(outfitFile);
            byte[] strToBytes = Client.getInstance().getGson().toJson(outfit).getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
            outfits.add(outfit);
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public static void loadOutfit(Outfit outfit) {
        Client.logger.info("Loading Outfit: " + outfit.name);
        Requests.post("https://api.silentclient.net/account/load_outfit", Client.getInstance().getGson().toJson(outfit));
        Client.getInstance().updateUserInformation();
        Players.reload();
        if(Minecraft.getMinecraft().thePlayer != null) {
            Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
        }
    }

    public static ArrayList<Outfit> getOutfits() {
        return outfits;
    }

    public static void deleteOutfit(Outfit outfit) {
        outfits.remove(outfit);
        try {
            new File(outfitsDir, outfit.name + ".json").delete();
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public static class Outfit {
        public final String name;
        public final int selected_cape;
        public final int selected_wings;
        public final int selected_icon;
        public final int selected_bandana;
        public final int selected_hat;
        public final int selected_neck;
        public final int selected_mask;
        public final int selected_shield;

        public Outfit(String name, int selected_cape, int selected_wings, int selected_icon, int selected_bandana, int selected_hat, int selected_neck, int selected_mask, int selected_shield) {
            this.name = name;
            this.selected_cape = selected_cape;
            this.selected_wings = selected_wings;
            this.selected_icon = selected_icon;
            this.selected_bandana = selected_bandana;
            this.selected_hat = selected_hat;
            this.selected_neck = selected_neck;
            this.selected_mask = selected_mask;
            this.selected_shield = selected_shield;
        }
    }
}
