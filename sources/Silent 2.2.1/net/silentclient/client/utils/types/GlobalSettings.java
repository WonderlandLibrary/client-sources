package net.silentclient.client.utils.types;

import net.silentclient.client.Client;
import net.silentclient.client.utils.FeaturedServers;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GlobalSettings {
    public String config;
    public boolean lite;
    public boolean displayedTutorial;
    public boolean configsMigrated;
    public boolean packsPanoramaEnabled;
    public ArrayList<FeaturedServers.FeaturedServerInfo> savedFeaturedServers;
    public ArrayList<GlobalSettings.CustomColor> latestColors;
    public boolean resourcePacksFetched;
    public ArrayList<String> usedResourcePacks;


    public GlobalSettings() {
        this.config = "Default.txt";
        this.lite = false;
        this.displayedTutorial = false;
        this.packsPanoramaEnabled = true;
        this.savedFeaturedServers = new ArrayList<>();
        this.latestColors = new ArrayList<>();
        this.resourcePacksFetched = false;
        this.usedResourcePacks = new ArrayList<>();
    }

    public String getConfig() {
        if(config == null) {
            config = "Default.txt";
        }
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public boolean isLite() {
        return lite;
    }

    public void setLite(boolean lite) {
        this.lite = lite;
        Client.getInstance().updateWindowTitle();
    }

    public void setDisplayedTutorial(boolean displayedTutorial) {
        this.displayedTutorial = displayedTutorial;
    }

    public boolean isDisplayedTutorial() {
        return displayedTutorial;
    }

    public void setConfigsMigrated(boolean configsMigrated) {
        this.configsMigrated = configsMigrated;
    }

    public boolean isPacksPanoramaEnabled() {
        return packsPanoramaEnabled;
    }

    public void setPacksPanoramaEnabled(boolean packsPanoramaEnabled) {
        this.packsPanoramaEnabled = packsPanoramaEnabled;
    }

    public ArrayList<FeaturedServers.FeaturedServerInfo> getSavedFeaturedServers() {
        if(savedFeaturedServers == null) {
            this.savedFeaturedServers = new ArrayList<>();
        }
        return savedFeaturedServers;
    }

    public void setSavedFeaturedServers(ArrayList<FeaturedServers.FeaturedServerInfo> savedFeaturedServers) {
        this.savedFeaturedServers = savedFeaturedServers;
    }

    public ArrayList<CustomColor> getLatestColors() {
        if(latestColors == null) {
            this.latestColors = new ArrayList<>();
            latestColors.add(new CustomColor(new Color(255, 255, 255)));
            latestColors.add(new CustomColor(new Color(156, 157, 151)));
            latestColors.add(new CustomColor(new Color(71,79,82)));
            latestColors.add(new CustomColor(new Color(0, 0, 0)));
            latestColors.add(new CustomColor(new Color(255,216,61)));
            latestColors.add(new CustomColor(new Color(249,128,29)));
            latestColors.add(new CustomColor(new Color(176,46,38)));
            latestColors.add(new CustomColor(new Color(130,84,50)));
            latestColors.add(new CustomColor(new Color(128,199,31)));
            latestColors.add(new CustomColor(new Color(58,179,218)));
            latestColors.add(new CustomColor(new Color(22,156,157)));
            latestColors.add(new CustomColor(new Color(60,68,169)));
            latestColors.add(new CustomColor(new Color(243,140,170)));
            latestColors.add(new CustomColor(new Color(198,79,189)));
            latestColors.add(new CustomColor(new Color(137,50,183)));
        }
        return latestColors;
    }

    public void addToLatestColors(Color color) {
        latestColors.removeIf(customColor -> customColor.color == new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB() && customColor.opacity == color.getAlpha());
        if(latestColors.size() == 21) {
            latestColors.remove(20);
        }
        latestColors.add(0, new CustomColor(color));
    }

    public boolean isResourcePacksFetched() {
        return resourcePacksFetched;
    }

    public void setResourcePacksFetched(boolean resourcePacksFetched) {
        this.resourcePacksFetched = resourcePacksFetched;
    }

    public ArrayList<String> getUsedResourcePacks() {
        if(usedResourcePacks == null) {
            this.usedResourcePacks = new ArrayList<>();
        }
        return usedResourcePacks;
    }

    public void addToUsedResourcePacks(String pack) {
        if(!this.usedResourcePacks.contains(pack)) {
            this.usedResourcePacks.add(pack);
        }
    }

    public void save() {
        try {
            FileOutputStream outputStream = new FileOutputStream(Client.getInstance().getGlobalSettingsFile());
            byte[] strToBytes = Client.getInstance().getGson().toJson(this).toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public class CustomColor {
        public int color;
        public int opacity;

        public CustomColor(Color color) {
            this(new Color(color.getRed(), color.getGreen(), color.getBlue()), color.getAlpha());
        }

        public CustomColor(Color color, int opacity) {
            this.color = new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB();
            this.opacity = opacity;
        }

        public Color getColor() {
            return getColor(false);
        }

        public Color getColor(boolean ignoreOpacity) {
            if(!ignoreOpacity) {
                Color color = new Color(this.color);
                return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
            }
            return new Color(this.color);
        }

        public int getRGB() {
            return getRGB(false);
        }

        public int getRGB(boolean ignoreOpacity) {
            if(!ignoreOpacity) {
                Color color = new Color(this.color);
                return new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity).getRGB();
            }
            return this.color;
        }
    }
}
