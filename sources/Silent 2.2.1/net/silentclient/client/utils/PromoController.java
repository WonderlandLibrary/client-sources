package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PromoController {
    public static PromoResponse promo;
    public static boolean loading = false;

    public static PromoResponse getResponse() {
        return promo;
    }

    public static void setResponse(PromoResponse promo) {
        PromoController.promo = promo;
    }

    public class PromoResponse {
        public ArrayList<Panel> panels;
        private int panelIndex = 0;
        private TimerUtils timer;

        public int getPanelIndex() {
            return panelIndex;
        }

        public void update() {
            if(timer == null) {
                timer = new TimerUtils();
            }
            if(timer.delay(6000)) {
                nextPanel();
            }
        }

        public void setPanelIndex(int panelIndex) {
            if(timer == null) {
                timer = new TimerUtils();
            }
            timer.reset();
            this.panelIndex = panelIndex;
        }

        public void prevPanel() {
            if(timer == null) {
                timer = new TimerUtils();
            }
            timer.reset();
            if(panelIndex == 0) {
                this.panelIndex = PromoController.getResponse().getPanels().size() - 1;
            } else {
                this.panelIndex -= 1;
            }
        }

        public void nextPanel() {
            if(timer == null) {
                timer = new TimerUtils();
            }
            timer.reset();
            if(panelIndex == PromoController.getResponse().getPanels().size() - 1) {
                this.panelIndex = 0;
            } else {
                this.panelIndex += 1;
            }
        }

        public Panel getCurrentPanel() {
            return panels.get(panelIndex) != null ? panels.get(panelIndex) : null;
        }

        public ArrayList<Panel> getPanels() {
            return panels;
        }
    }

    public class Panel {
        public String imageUrl;
        public String redirectUrl;

        public ResourceLocation imageLocation;
        public BufferedImage image = null;
        public boolean initImage = false;

        public void loadImage() {
            if(image == null && !PromoController.loading && !initImage) {
                PromoController.loading = true;
                (new Thread("PromoImageThread") {
                    public void run() {
                        Client.logger.info(String.format("Downloading promo image %s...", imageUrl));
                        BufferedImage avatar = SCTextureManager.getImage(imageUrl);
                        if(avatar != null) {
                            Client.logger.info(String.format("Promo image %s successfully downloaded!", imageUrl));
                            image = avatar;
                        } else {
                            Client.logger.info(String.format("Promo image %s downloading failed!", imageUrl));
                            initImage = true;
                        }
                        PromoController.loading = false;
                    }
                }).start();
            }
            if(image != null && !initImage) {
                Client.logger.info(String.format("Crating ResourceLocation of promo image %s...", imageUrl));
                imageLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("promo_image_" + new Random().nextLong(), new DynamicTexture(image));
                initImage = true;
            }
        }

        public ResourceLocation getImageLocation() {
            return imageLocation;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }
    }
}
