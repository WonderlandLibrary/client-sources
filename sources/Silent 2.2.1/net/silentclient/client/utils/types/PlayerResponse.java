package net.silentclient.client.utils.types;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.utils.NotificationUtils;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.Requests;
import net.silentclient.client.utils.reply.AbstractReply;
import org.json.JSONObject;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PlayerResponse extends AbstractReply {
    public Account account;
    public BanInfo banInfo;

    public Account getAccount() {
        return account;
    }

    public class Account {
        public int selected_cape;
        public int selected_wings;
        public int selected_icon;
        public int selected_bandana;
        public int selected_hat;
        public int selected_neck;
        public int selected_mask;
        public int selected_shield;
        public int is_plus;
        public int plus_icon;
        public int custom_cape;
        public String username;
        public String original_username;
        public int plus_icon_color;
        public int is_online;
        public int is_admin;
        public int is_staff;
        public int is_tester;
        public int is_partner;
        public int is_dev;
        public int is_banned;
        public int custom_skin;
        public int is_retired;
        public String skin_type;
        public String cape_type;
        public int cape_shoulders;
        public int bandana_color;
        public int is_premium_plus;
        public int show_nametag_message;
        public int is_manager;
        public String nametag_message;
        public String plus_expiration;
        public int claimed_premium_cosmetics;
        public FavoriteCosmetics favorite_cosmetics;
        public int is_senior_tester;
        public int is_senior_tech_support;
        public int is_tech_support;
        public int is_jr_admin;
        public int is_tester_manager;
        public BigInteger discord_id;
        public String custom_skin_path;

        public int getPlusExpiration() {
            if(plus_expiration != null) {
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(plus_expiration.split("\\.")[0].toString());
                    Date date2 = new Date();

                    long elapsedms = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() - date2.getTime();
                    long diff = TimeUnit.MINUTES.convert(elapsedms, TimeUnit.MILLISECONDS);
                    return (int) (diff / 60 / 24);
                } catch (Exception err) {
                    return -1;
                }
            }

            return -1;
        }

        public FavoriteCosmetics getFavoriteCosmetics() {
            return favorite_cosmetics;
        }

        public Cosmetics cosmetics;

        public void setPlusIconColor(int plus_icon_color) {

        }

        public int getSelectedMask() {
            return selected_mask;
        }

        public int getSelectedNeck() {
            return selected_neck;
        }

        public void updateFavorite(int id, String type) {
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("updateFavorite") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/update_favorite_cosmetics", new JSONObject().put("id", id).put("type", type).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();
        }

        public void setBandanaColor(int color) {
            this.bandana_color = color;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
        }

        public void saveBandanaColor() {
            (new Thread(() -> {
                Requests.post("https://api.silentclient.net/plus/set_bandana_color", new JSONObject().put("color", bandana_color).toString());
                Client.getInstance().updateUserInformation();
            })).start();
        }

        public int getSelectedHat() {
            return selected_hat;
        }

        public int getSelectedShield() {
            return selected_shield;
        }

        public String getCapeType() {
            return cape_type;
        }

        public boolean getCapeShoulders() {
            return cape_shoulders == 1;
        }

        public boolean showNametagMessage() {
            return isPremiumPlus() && show_nametag_message == 1;
        }

        public boolean getClaimedPremiumCosmetics() {
            if(!isPlus()) {
                return true;
            }

            return claimed_premium_cosmetics == 1;
        }

        public void claimPremiumCosmetics() {
            Requests.post("https://api.silentclient.net/plus/claim_premium_cosmetics");
        }

        public void setShowNametagMessage(boolean show) {
            this.show_nametag_message = show ? 1 : 0;
            Players.reload();
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setShowNametagMessage") {
                public void run() {
                    Requests.post("https://api.silentclient.net/plus/set_nametag_message", new JSONObject().put("enabled", show).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();
        }

        public void setNametagMessage(String message) {
            this.nametag_message = message;
            Players.reload();
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            String response = Requests.post("https://api.silentclient.net/plus/set_nametag_message", new JSONObject().put("message", message).toString());
            if(response != null) {
                NotificationUtils.showNotification("success", "Nametag Message updated successfully!");
            } else {
                NotificationUtils.showNotification("error", "");
            }
            Client.getInstance().updateUserInformation();
        }

        public String getNametagMessage() {
            return nametag_message;
        }

        public void setCapeShoulders(boolean shoulders) {
            this.cape_shoulders = shoulders ? 1 : 0;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setShoulders") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/set_cape_shoulders", new JSONObject().put("enabled", shoulders).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();
        }



        public void setCapeType(String type) {
            this.cape_type = type;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setCapeType") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/set_cape_type", new JSONObject().put("type", type).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();
        }

        public boolean isTester() {
            return is_tester == 1;
        }

        public boolean isStaff() {
            return is_staff == 1 || isAdmin() || isDev() || isTester() || isManager() || isSeniorTester() || isTesterManager() || is_senior_tech_support == 1 || is_tech_support == 1;
        }

        public boolean isSeniorTester() {
            return is_senior_tester == 1;
        }

        public boolean isTesterManager() {
            return is_tester_manager == 1;
        }

        public boolean isBanned() {
            return is_banned == 1;
        }

        public boolean isAdmin() {
            return is_admin == 1 || is_jr_admin == 1;
        }

        public boolean isDev() {
            return is_dev == 1;
        }

        public boolean isManager() {
            return is_manager == 1;
        }

        public boolean isPartner() {
            return is_partner == 1;
        }

        public boolean getCustomSkin() {
            return custom_skin == 1;
        }

        public String getSkinType() {
            return skin_type;
        }

        public int getPlusIconColor() {
            return plus_icon_color;
        }

        public boolean isOnline() {
            return is_online == 1;
        }

        public boolean isPlus() {
            return is_plus == 1 || isStaff() || isPartner() || is_retired == 1;
        }

        public boolean isPremiumPlus() {
            return (is_plus == 1 && is_premium_plus == 1)  || isStaff() || isPartner() || is_retired == 1;
        }

        public int getBandanaColor() {
            return bandana_color;
        }

        public String getUsername() {
            return username;
        }

        public boolean plusIcon() {
            return (isPlus() && plus_icon == 1) ? true : false;
        }

        public boolean customCape() {
            return (isPlus() && custom_cape == 1) ? true : false;
        }

        public int getSelectedBandana() {
            return selected_bandana;
        }

        public void setSelectedBandana(int id) {
            final int bandanaId = id == this.selected_bandana ? 0 : id;
            this.selected_bandana = bandanaId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setBandana") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_bandana", new JSONObject().put("id", bandanaId).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public void setSelectedHat(int id, String type) {
            int hatId = 0;
            switch(type) {
                case "mask":
                    hatId = id == this.selected_mask ? 0 : id;
                    this.selected_mask = hatId;
                    break;
                case "neck":
                    hatId = id == this.selected_neck ? 0 : id;
                    this.selected_neck = hatId;
                    break;
                case "hat":
                    hatId = id == this.selected_hat ? 0 : id;
                    this.selected_hat = hatId;
                    break;
            }
            final int cid = hatId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setHat") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_hat", new JSONObject().put("id", cid).put("type", type).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public void setSelectedShield(int id) {
            final int hatId = id == this.selected_shield ? 0 : id;
            this.selected_shield = hatId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setShield") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_shield", new JSONObject().put("id", hatId).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public int getSelectedCape() {
            return selected_cape;
        }

        public void setSelectedCape(int id) {
            final int capeId = id == this.selected_cape ? 0 : id;
            this.selected_cape = capeId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setCapeRequest") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_cape", new JSONObject().put("id", capeId).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public int getSelectedWings() {
            return selected_wings;
        }

        public void setSelectedWings(int id) {
            final int wingsId = id == this.selected_wings ? 0 : id;
            this.selected_wings = wingsId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setWingsRequest") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_wings", new JSONObject().put("id", wingsId).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public int getSelectedIcon() {
            return selected_icon;
        }

        public void setSelectedIcon(int id) {
            final int iconId = id == this.selected_icon ? 0 : id;
            this.selected_icon = iconId;
            Players.reload();
            if(Minecraft.getMinecraft().thePlayer != null) {
                Players.getPlayerStatus(false, ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$getNameClear(), EntityPlayer.getUUID(Minecraft.getMinecraft().thePlayer.getGameProfile()), Minecraft.getMinecraft().thePlayer);
            }
            (new Thread("setIconRequest") {
                public void run() {
                    Requests.post("https://api.silentclient.net/account/select_icon", new JSONObject().put("id", iconId).toString());
                    Client.getInstance().updateUserInformation();
                }
            }).start();

        }

        public String getCustomSkinPath() {
            return custom_skin_path;
        }

        public Cosmetics getCosmetics() {
            return cosmetics;
        }

        public class Cosmetics {
            public ArrayList<CosmeticItem> icons = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> wings = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> capes = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> bandanas = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> hats = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> shields = new ArrayList<CosmeticItem>();
            public ArrayList<CosmeticItem> emotes = new ArrayList<CosmeticItem>();

            public ArrayList<CosmeticItem> getCapes() {
                return capes;
            }

            public ArrayList<CosmeticItem> getIcons() {
                return icons;
            }

            public ArrayList<CosmeticItem> getWings() {
                return wings;
            }

            public ArrayList<CosmeticItem> getBandanas() {
                return bandanas;
            }

            public ArrayList<CosmeticItem> getHats() {
                return hats;
            }

            public ArrayList<CosmeticItem> getShields() {
                return shields;
            }

            public ArrayList<CosmeticItem> getEmotes() {
                return emotes;
            }

            public class CosmeticItem {
                public int id;
                public String name;
                public String preview;
                public String texture;

                public int is_animated;
                public int frames;
                public int frame_delay;
                public String model;


                public int getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getPreview() {
                    return preview;
                }

                public boolean getIsAnimated() {
                    return is_animated == 1;
                }

                public int getFrames() {
                    return frames;
                }

                public int getFrameDelay() {
                    return frame_delay;
                }

                public String getModel() {
                    return model;
                }
            }
        }
    }

    public class FavoriteCosmetics {
        public ArrayList<Number> icons = new ArrayList<Number>();
        public ArrayList<Number> wings = new ArrayList<Number>();
        public ArrayList<Number> capes = new ArrayList<Number>();
        public ArrayList<Number> bandanas = new ArrayList<Number>();
        public ArrayList<Number> hats = new ArrayList<Number>();
        public ArrayList<Number> shields = new ArrayList<Number>();
        public ArrayList<Number> emotes = new ArrayList<Number>();
    }

    public class BanInfo {
        public boolean banned;
        public String reason;
    }
}