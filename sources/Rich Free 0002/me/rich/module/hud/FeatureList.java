package me.rich.module.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.event.events.EventUpdate;
import me.rich.font.CFontRenderer;
import me.rich.font.Fonts;
import me.rich.helpers.render.AnimationHelper;
import me.rich.helpers.render.ColorHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.helpers.render.Translate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.FeatureDirector;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public class FeatureList extends Feature {
    private double lastPosX = 0.0D / 0.0;
    private double lastPosZ = 0.0D / 0.0;
    private final ArrayList<Double> distances = new ArrayList();
    private final String BACKGROUND = "Background";
    private final String OPACITY = "BackgroundAlpha";
    private final String OUTLINE = "Border";
    private final String INFO = "Info";
    private final String OFFSET = "Height";
    
    private static CFontRenderer font;

    public static boolean hotbar;
    float xd = 0.0F;
    float ld = 0.0F;


    public FeatureList() {
        super("FeatureList", 0, Category.HUD);
        Main.settingsManager.rSetting(new Setting("Background", this, true));
        Main.settingsManager.rSetting(new Setting("Info", this, true));
        Main.settingsManager.rSetting(new Setting("Rect", this, true));
        Main.settingsManager.rSetting(new Setting("BackgroundAlpha", this, 120.0D, 1.0D, 255.0D, true));
        Main.settingsManager.rSetting(new Setting("LineWidth", this, 1.5,0, 2, false));
        Main.settingsManager.rSetting(new Setting("Height", this, 10.0D, 6.0D, 20.0D, true));
        Main.settingsManager.rSetting(new Setting("Rainbow Spread", this, 0.1, 0, 1, false));
        Main.settingsManager.rSetting(new Setting("Saturation", this, 0.8, 0, 1, false));
        Main.settingsManager.rSetting(new Setting("ColorTime", this, 10, 1, 20, false));
        Main.settingsManager.rSetting(new Setting("FontY", this, 2.5, 1, 5, false));
        ArrayList<String> color = new ArrayList();
        color.add("Candy");
        color.add("Rainbow");
        color.add("Astolfo");
        color.add("FDP");
        color.add("Blood");
        color.add("Pinky");
        color.add("Toxic");
        color.add("White");
        ArrayList<String> font = new ArrayList();
        font.add("SFUI");
        font.add("Roboto");
        font.add("Myseo");
        ArrayList<String> mode = new ArrayList();
        mode.add("ArrayBorder");
        mode.add("ArrayLine");
        mode.add("ArrayNone");
        ArrayList<String> sort = new ArrayList();
        sort.add("Width");
        sort.add("Random");
        sort.add("Minus");
        Main.settingsManager.rSetting(new Setting("FeatureList", this, "Pulsive", color));
        Main.settingsManager.rSetting(new Setting("Mode", this, "Border", mode));
        Main.settingsManager.rSetting(new Setting("Sort Mode", this, "Width", sort));
        Main.settingsManager.rSetting(new Setting("Font", this, "Comfortaa", font));
        Main.settingsManager.rSetting(new Setting("Minecraft Font", this, true));
    }

    @EventTarget
    public void onRender2D(EventRender2D render) {
        ScaledResolution sr = new ScaledResolution(mc);
        
        this.GayHud(render.getResolution());
    }

    private void GayHud(ScaledResolution sr) {
    	String modes = Main.settingsManager.getSettingByName("Mode").getValString();
        String fonts = Main.settingsManager.getSettingByName("Font").getValString();
        if(!Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Minecraft Font").getValBoolean()) {
        if (fonts.equalsIgnoreCase("SFUI")) {
        	 font = Fonts.sfui18;
        }

        if (fonts.equalsIgnoreCase("Roboto")) {
        	font = Fonts.roboto_18;
        }
        
        if (fonts.equalsIgnoreCase("Myseo")) {
        	font = Fonts.neverlose500_18;
        }
        
        }
        
		// Astolfo fix.
		int yTotal = 0;
		for (int i = 0; i < Main.moduleManager.getModules().size(); ++i) {
			yTotal += Fonts.sfui16.getHeight() + 5;
		} // End.
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        if(!Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Minecraft Font").getValBoolean()) {
        ArrayList<Feature> sortedList = this.getSortedModules(font);
        int listOffset = (int)Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class) ,"Height").getValFloat();
        int y = 1;
        int[] counter = new int[]{1};
        GL11.glEnable(3042);
        int i = 0;
        for(int sortedListSize = sortedList.size(); i < sortedListSize; ++i) {
            Feature module = (Feature)sortedList.get(i);
            Translate translate = module.getTranslate();
            String moduleLabel = module.getModuleName();
            float length = (float)font.getStringWidth(moduleLabel);
            float featureX = (float)width - length - 3.0F;
            boolean enable = module.isToggled();
            if (enable) {
                translate.interpolate(featureX, (float)y, 7.0D);
            } else {
                translate.interpolate((float)(width + 3), (float)y, 7.0D);
            }
            double translateX = (double)translate.getX();
            double translateY = (double)translate.getY() - 1;
            boolean visible = translateX > (double)(-listOffset);
            int nextIndex = sortedList.indexOf(module) + 1;
            Feature nextModule = null;
            int colorArray = -1;
            String mode = Main.settingsManager.getSettingByName("FeatureList").getValString();
            this.setModuleName("FeatureList §7[" + mode + "]");
            double time = Main.settingsManager.getSettingByName("ColorTime").getValDouble();
            int candy_color = TwoColoreffect(new Color(65, 179, 255), new Color(248, 54, 255), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D).getRGB();
            int fdp_color = TwoColoreffect((new Color(42, 191, 255)), new Color(38, 116, 149), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D).getRGB();
            int astolfo_color = ColorHelper.astolfoColors4((float) translateY, (float) yTotal, (float) Main.settingsManager.getSettingByName("Saturation").getValDouble());
            int rainbow_color =  ColorHelper.rainbow((int) (translateY * 200 * Main.settingsManager.getSettingByName("Rainbow Spread").getValDouble()),(float) Main.settingsManager.getSettingByName("Saturation").getValDouble(), 1.0f);
            int blood_color =  TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 60).getRGB();
            int pink_color = TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 90).getRGB();
            int toxic_color = TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 90).getRGB();
            if (mode.equalsIgnoreCase("Candy")) {
                colorArray = candy_color;
            }
            
            if (mode.equalsIgnoreCase("Rainbow")) {
                colorArray = rainbow_color;
            }
            
            if (mode.equalsIgnoreCase("Astolfo")) {
                colorArray = astolfo_color;
            }
            
            if (mode.equalsIgnoreCase("FDP")) {
                colorArray = fdp_color;
            }
            
            if (mode.equalsIgnoreCase("Blood")) {
                colorArray = blood_color;
            }
            
            if (mode.equalsIgnoreCase("Pinky")) {
                colorArray = pink_color;
            }
            
            if (mode.equalsIgnoreCase("Toxic")) {
                colorArray = toxic_color;
            }
            
            if (mode.equalsIgnoreCase("White")) {
                colorArray = -1;
            }


            
            if (sortedList.size() > nextIndex) {
                nextModule = this.getNextEnabledModule(sortedList, nextIndex);
            }

            if (Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayNone")) {
            	RenderHelper.drawRect(translateX + 0.5, translateY, (double)width, translateY + (double)listOffset, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
            } else if(Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayLine")) {
            	RenderHelper.drawRect(translateX - 1.2D, translateY, (double)width, translateY + (double)listOffset, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
            } else if(Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayBorder")) {
            	RenderHelper.drawRect(translateX - 2.0D, translateY - 0.0D, (double)width, translateY + (double)listOffset - 0.0D, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
            }
            
            if (modes.equalsIgnoreCase("ArrayBorder")) {
            	RenderHelper.drawRect(translateX - 2.6D, translateY + 0D, translateX - 1.0D, translateY + (double)listOffset + 1.5D, colorArray);
                double offsetY = (double)listOffset;
                if (nextModule != null) {
                    double dif = (double)(length - (float)font.getStringWidth(nextModule.getModuleName()));
                    RenderHelper.drawRect(translateX - 2.6D, translateY + offsetY, translateX - 2.6D + dif, translateY + offsetY + 1.5D, colorArray);
                } else {
                	RenderHelper.drawRect(translateX - 2.6D, translateY + offsetY, (double)width, translateY + offsetY + 1.6D, colorArray);
                }

            font.drawStringWithShadow(moduleLabel, translateX + 1, translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat(), colorArray);
            }
            
            if (modes.equalsIgnoreCase("ArrayLine")) {
            	int count = 0;
            	if(!Main.moduleManager.getModuleByName("ESP").isToggled()) {
            	Gui.drawRect(sr.getScaledWidth() - 0, translateY, sr.getScaledWidth() - 1.6, translateY + (double)listOffset, colorArray);
            font.drawStringWithShadow(moduleLabel, translateX, (translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat()), colorArray);
        	count++;
            }else {
            	Gui.drawRect(sr.getScaledWidth() - 0, translateY, sr.getScaledWidth() - 1.6, translateY + (double)listOffset, colorArray);
            font.drawStringWithShadow(moduleLabel, translateX, (translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat()), colorArray);
        	count++;
            }
            	if(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Rect").getValBoolean()) {
            	RenderHelper.drawRect(translateX - 1 - Main.settingsManager.getSettingByName("LineWidth").getValFloat(), translateY + 0D, translateX - 1.0D, translateY + (double)listOffset, colorArray);
            	}
            }
            
        
            
            if (modes.equalsIgnoreCase("ArrayNone")) {
            font.drawStringWithShadow(moduleLabel, (int)translateX + 1.5, (int)translateY + 1 +  Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat(), colorArray);
        	if(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Rect").getValBoolean()) {
        		RenderHelper.drawRect(translateX - Main.settingsManager.getSettingByName("LineWidth").getValFloat(), translateY + 0D, translateX + 0.5f, translateY + (double)listOffset, colorArray);
        		}
            }
            
            
            if (module.isToggled()) {
                y += listOffset;
                counter[0] = (int)((float)counter[0] - 1.0F);
            }
        }
        } else {
            ArrayList<Feature> sortedList = this.getSortedModules2(mc.fontRendererObj);
            int listOffset = (int)Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class) ,"Height").getValFloat();
            int y = 1;
            int[] counter = new int[]{1};
            GL11.glEnable(3042);
            int i = 0;
            for(int sortedListSize = sortedList.size(); i < sortedListSize; ++i) {
                Feature module = (Feature)sortedList.get(i);
                Translate translate = module.getTranslate();
                String moduleLabel = module.getModuleName();
                float length = (float)mc.fontRendererObj.getStringWidth(moduleLabel);
                float featureX = (float)width - length - 3.0F;
                boolean enable = module.isToggled();
                if (enable) {
                    translate.interpolate(featureX, (float)y, 7.0D);
                } else {
                    translate.interpolate((float)(width + 3), (float)y, 7.0D);
                }
                double translateX = (double)translate.getX();
                double translateY = (double)translate.getY() - 1;
                boolean visible = translateX > (double)(-listOffset);
                int nextIndex = sortedList.indexOf(module) + 1;
                Feature nextModule = null;
                int colorArray = -1;
                String mode = Main.settingsManager.getSettingByName("FeatureList").getValString();
                this.setModuleName("FeatureList §7[" + mode + "]");
                double time = Main.settingsManager.getSettingByName("ColorTime").getValDouble();
                int candy_color = TwoColoreffect(new Color(65, 179, 255), new Color(248, 54, 255), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D).getRGB();
                int fdp_color = TwoColoreffect((new Color(42, 191, 255)), new Color(38, 116, 149), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D).getRGB();
                int astolfo_color = ColorHelper.astolfoColors4((float) translateY, (float) yTotal, (float) Main.settingsManager.getSettingByName("Saturation").getValDouble());
                int rainbow_color =  ColorHelper.rainbow((int) (translateY * 200 * Main.settingsManager.getSettingByName("Rainbow Spread").getValDouble()),(float) Main.settingsManager.getSettingByName("Saturation").getValDouble(), 1.0f);
                int blood_color =  TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 60).getRGB();
                int pink_color = TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 90).getRGB();
                int toxic_color = TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 6.0F * (counter[0] * 2.55) / 90).getRGB();
                if (mode.equalsIgnoreCase("Candy")) {
                    colorArray = candy_color;
                }
                
                if (mode.equalsIgnoreCase("Rainbow")) {
                    colorArray = rainbow_color;
                }
                
                if (mode.equalsIgnoreCase("Astolfo")) {
                    colorArray = astolfo_color;
                }
                
                if (mode.equalsIgnoreCase("FDP")) {
                    colorArray = fdp_color;
                }
                
                if (mode.equalsIgnoreCase("Blood")) {
                    colorArray = blood_color;
                }
                
                if (mode.equalsIgnoreCase("Pinky")) {
                    colorArray = pink_color;
                }
                
                if (mode.equalsIgnoreCase("Toxic")) {
                    colorArray = toxic_color;
                }
                
                if (mode.equalsIgnoreCase("White")) {
                    colorArray = -1;
                }

                
                if (sortedList.size() > nextIndex) {
                    nextModule = this.getNextEnabledModule(sortedList, nextIndex);
                }

                if (Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayNone")) {
                	RenderHelper.drawRect(translateX, translateY, (double)width, translateY + (double)listOffset, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
                } else if(Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayLine")) {
                	RenderHelper.drawRect(translateX - 1.4D, translateY, (double)width, translateY + (double)listOffset, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
                } else if(Main.settingsManager.getSettingByName("Background").getValBoolean() && modes.equalsIgnoreCase("ArrayBorder")) {
                	RenderHelper.drawRect(translateX - 2.0D, translateY - 0.0D, (double)width, translateY + (double)listOffset - 0.0D, ColorHelper.getColor(0, (int)Main.settingsManager.getSettingByName("BackgroundAlpha").getValFloat()));
                }
                
                if (modes.equalsIgnoreCase("ArrayBorder")) {
                	RenderHelper.drawRect(translateX - 2.6D, translateY + 0D, translateX - 1.0D, translateY + (double)listOffset + 1.5D, colorArray);
                    double offsetY = (double)listOffset;
                    if (nextModule != null) {
                        double dif = (double)(length - (float)mc.fontRendererObj.getStringWidth(nextModule.getModuleName()));
                        RenderHelper.drawRect(translateX - 2.6D, translateY + offsetY, translateX - 2.6D + dif, translateY + offsetY + 1.5D, colorArray);
                    } else {
                    	RenderHelper.drawRect(translateX - 2.6D, translateY + offsetY, (double)width, translateY + offsetY + 1.6D, colorArray);
                    }

                mc.fontRendererObj.drawStringWithShadow(moduleLabel, (float) translateX + 1, (float) translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat(), colorArray);
                }
                
                if (modes.equalsIgnoreCase("ArrayLine")) {
                	int count = 0;
                	if(!Main.moduleManager.getModuleByName("ESP").isToggled()) {
                	Gui.drawRect(sr.getScaledWidth() - 0.5, translateY, sr.getScaledWidth() - 1.6, translateY + (double)listOffset, colorArray);
                	mc.fontRendererObj.drawStringWithShadow(moduleLabel, (float) translateX, (float) (translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat()), colorArray);
            	count++;
                }else {
                	Gui.drawRect(sr.getScaledWidth() - 0.5, translateY, sr.getScaledWidth() - 1.6, translateY + (double)listOffset, colorArray);
                	mc.fontRendererObj.drawStringWithShadow(moduleLabel, (float) translateX, (float) (translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat()), colorArray);
            	count++;
                }
                	if(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Rect").getValBoolean()) {
                	RenderHelper.drawRect(translateX - 1.4 - Main.settingsManager.getSettingByName("LineWidth").getValFloat(), translateY + 0D, translateX - 1.4D, translateY + (double)listOffset, colorArray);
                	}
                }
                
            
                
                if (modes.equalsIgnoreCase("ArrayNone")) {
                	mc.fontRendererObj.drawStringWithShadow(moduleLabel, (float) translateX + 1.5f, (float) translateY + Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "FontY").getValFloat(), colorArray);
            	if(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class), "Rect").getValBoolean()) {
            		RenderHelper.drawRect(translateX - Main.settingsManager.getSettingByName("LineWidth").getValFloat(), translateY + 0D, translateX + 0.5f, translateY + (double)listOffset, colorArray);
            		}
                }
                
                
                if (module.isToggled()) {
                    y += listOffset;
                    counter[0] = (int)((float)counter[0] - 1.0F);
                }
            }
        }
        }
        


    @EventTarget
    public void onUrMom(EventUpdate event) {
        if (!Double.isNaN(this.lastPosX) && !Double.isNaN(this.lastPosZ)) {
            double differenceX = Math.abs(this.lastPosX - mc.player.posX);
            double differenceZ = Math.abs(this.lastPosZ - mc.player.posZ);
            double distance = Math.sqrt(differenceX * differenceX + differenceZ * differenceZ) * 2.0D;
            this.distances.add(distance);
            if (this.distances.size() > 20) {
                this.distances.remove(0);
            }
        }

        this.lastPosX = mc.player.posX;
        this.lastPosZ = mc.player.posZ;
    }

    private Feature getNextEnabledModule(ArrayList<Feature> modules, int startingIndex) {
        int i = startingIndex;

        for(int modulesSize = modules.size(); i < modulesSize; ++i) {
            Feature module = (Feature)modules.get(i);
            if (module.isToggled()) {
                return module;
            }
        }

        return null;
    }

    private ArrayList<Feature> getSortedModules(CFontRenderer font2) {
        FeatureDirector var10002 = Main.moduleManager;
        ArrayList<Feature> sortedList = new ArrayList(FeatureDirector.getModules());
        String sort = Main.settingsManager.getSettingByName("Sort Mode").getValString();
        if(sort.equalsIgnoreCase("Width")) {
        sortedList.sort(Comparator.comparingDouble((e) -> {
            return (double)(-font2.getStringWidth(e.getModuleName()));
        }));
        }
        
        if(sort.equalsIgnoreCase("Random")) {

        }
        
        if(sort.equalsIgnoreCase("Minus")) {
        sortedList.sort(Comparator.comparingDouble((e) -> {
            return (double)(font2.getStringWidth(e.getModuleName()));
        }));
        }
        
        return sortedList;
    }
    
    private ArrayList<Feature> getSortedModules2(FontRenderer font2) {
        FeatureDirector var10002 = Main.moduleManager;
        ArrayList<Feature> sortedList = new ArrayList(FeatureDirector.getModules());
        String sort = Main.settingsManager.getSettingByName("Sort Mode").getValString();
        if(sort.equalsIgnoreCase("Width")) {
        sortedList.sort(Comparator.comparingDouble((e) -> {
            return (double)(-font2.getStringWidth(e.getModuleName()));
        }));
        }
        
        if(sort.equalsIgnoreCase("Random")) {

        }
        
        if(sort.equalsIgnoreCase("Minus")) {
        sortedList.sort(Comparator.comparingDouble((e) -> {
            return (double)(font2.getStringWidth(e.getModuleName()));
        }));
        }
        
        return sortedList;
    }

    public static Color TwoColoreffect(Color color, Color color2, double delay) {
        double n3;
        if (delay > 1.0D) {
            n3 = delay % 1.0D;
            delay = (int)delay % 2 == 0 ? n3 : 1.0D - n3;
        }

        n3 = 1.0D - delay;
        return new Color((int)((double)color.getRed() * n3 + (double)color2.getRed() * delay), (int)((double)color.getGreen() * n3 + (double)color2.getGreen() * delay), (int)((double)color.getBlue() * n3 + (double)color2.getBlue() * delay), (int)((double)color.getAlpha() * n3 + (double)color2.getAlpha() * delay));
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)(index / count) * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public double getDistTraveled() {
        double total = 0.0D;

        double d;
        for(Iterator var5 = this.distances.iterator(); var5.hasNext(); total += d) {
            d = (Double)var5.next();
        }

        return total;
    }

    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
