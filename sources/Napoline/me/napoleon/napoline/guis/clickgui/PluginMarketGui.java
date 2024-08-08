package me.napoleon.napoline.guis.clickgui;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.HttpUtil;
import org.lwjgl.input.Mouse;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.junk.openapi.java.NapolinePlugin;
import me.napoleon.napoline.junk.openapi.script.NapolineScript;
import me.napoleon.napoline.utils.client.WebPlugin;
import me.napoleon.napoline.utils.client.WebPluginUtil;
import me.napoleon.napoline.utils.render.RenderUtils;

import static me.napoleon.napoline.guis.clickgui.Clickgui.rgb;
import static me.napoleon.napoline.modules.Mod.mc;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: QianXia
 * @description: 插件市场GUI
 * @create: 2021/01/08-18:49
 */
public class PluginMarketGui {
    private int startValue = 0;
    public int windowX, windowY, windowWeight;
    public List<NapolineScript> scripts = Napoline.scriptManager.scripts;
    public List<NapolinePlugin> plugins = Napoline.pluginManager.plugins;
    public List<WebPlugin> webPlugins = new ArrayList<>();
    public boolean web = false;

    public void drawPluginMarket(int windowX, int windowY, int windowWeight, int windowHeight){
        final ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i1 = scaledresolution.getScaledWidth();
        int j1 = scaledresolution.getScaledHeight();
        final int mouseX = Mouse.getX() * i1 / mc.displayWidth;
        final int mouseY = j1 - Mouse.getY() * j1 / mc.displayHeight - 1;

        startValue = 0;
        int startY = 30;
        int realY = startY + startValue;
        int rgb = Clickgui.rgb;
        this.windowX = windowX;
        this.windowY = windowY;
        this.windowWeight = windowWeight;

        FontLoaders.F14.drawString("Web", windowX + 210, windowY, rgb);

        if(web){
            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY, windowX + windowWeight - 15, windowY + 10, mouseX, mouseY)) {
                RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY - 0.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + 10.5), Clickgui.color_select);
            }
            RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY, windowX + windowWeight - 15, windowY + 10, Clickgui.color_select);
            RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + 5, 3, new Color(255, 255, 255).getRGB());

//            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
//                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(127, 147, 255).getRGB());
//            } else {
//                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(107, 127, 255).getRGB());
//            }
//
//            RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + realY + 10, 3, new Color(255, 255, 255).getRGB());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //FontLoaders.F16.drawString("Loading...", windowX + 210, windowY + startY + startValue + 8, rgb);
                    String webPluginList = get("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Plugins/PluginList");

                    webPlugins = WebPluginUtil.getWebPluginListByString(webPluginList);
                }
            }).run();

            for (WebPlugin plugin : webPlugins) {
                FontLoaders.F16.drawString(plugin.pluginName + " - " + plugin.author, windowX + 210, windowY + realY + 8, rgb);

//                if (Napoline.pluginManager.isPluginEnabled(plugin)) {
//                    if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
//                        RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(127, 147, 255).getRGB());
//                    } else {
//                        RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(107, 127, 255).getRGB());
//                    }
//
//                    RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + realY + 10, 3, new Color(255, 255, 255).getRGB());
//                } else {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY + realY + 4.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + realY + 15.5), Clickgui.color_select);
                }
                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, Clickgui.color_unselect);
                RenderUtils.drawCircle(windowX + windowWeight - 30, windowY + realY + 10, 3, new Color(112, 112, 112).getRGB());
         //       }
                realY += 20;
            }
            return;
        }else{
            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY, windowX + windowWeight - 15, windowY + 10, mouseX, mouseY)) {
                RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY - 0.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + 10.5), Clickgui.color_select);
            }
            RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY, windowX + windowWeight - 15, windowY + 10, Clickgui.color_unselect);
            RenderUtils.drawCircle(windowX + windowWeight - 30, windowY + 5, 3, new Color(112, 112, 112).getRGB());
        }

        // Start drawing a list of plugins
        for (NapolinePlugin plugin : plugins) {
            FontLoaders.F16.drawString(plugin.pluginName + " - " + plugin.author, windowX + 210, windowY + realY + 8, rgb);

            if (Napoline.pluginManager.isPluginEnabled(plugin)) {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(127, 147, 255).getRGB());
                } else {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(107, 127, 255).getRGB());
                }

                RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + realY + 10, 3, new Color(255, 255, 255).getRGB());
            } else {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY + realY + 4.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + realY + 15.5), Clickgui.color_select);
                }
                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, Clickgui.color_unselect);
                RenderUtils.drawCircle(windowX + windowWeight - 30, windowY + realY + 10, 3, new Color(112, 112, 112).getRGB());
            }
            realY += 20;
        }

        // Start drawing a list of scripts
        for (NapolineScript script : scripts) {
            FontLoaders.F16.drawString(script.name + (script.scriptCommand != null && !script.name.endsWith("Command") ? "Command" : "") + " - " + script.author, windowX + 210, windowY + realY + 8, rgb);

            if (Napoline.scriptManager.isScriptEnabled(script)) {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, (float) (windowY + realY + 5), windowX + windowWeight - 15, windowY + realY + 15, new Color(127, 147, 255).getRGB());
                } else {
                    RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, new Color(107, 127, 255).getRGB());
                }

                RenderUtils.drawCircle(windowX + windowWeight - 20, windowY + realY + 10, 3, new Color(255, 255, 255).getRGB());
            } else {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, mouseX, mouseY)) {
                    RenderUtils.drawRoundRect((float) (windowX + windowWeight - 36.5), (float) (windowY + realY + 4.5), (float) (windowX + windowWeight - 14.5), (float) (windowY + realY + 15.5), Clickgui.color_select);
                }
                RenderUtils.drawRoundRect(windowX + windowWeight - 36, windowY + realY + 5, windowX + windowWeight - 15, windowY + realY + 15, Clickgui.color_unselect);
                RenderUtils.drawCircle(windowX + windowWeight - 30, windowY + realY + 10, 3, new Color(112, 112, 112).getRGB());
            }
            realY += 20;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int valueStartY = 30;
        int valueRealY = valueStartY + startValue;

        if (Clickgui.isHovered(windowX + windowWeight - 36, windowY, windowX + windowWeight - 15, windowY + 5, mouseX, mouseY)) {
            web = !web;
        }

        if(web){
            for (WebPlugin webPlugin : webPlugins) {
                if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + valueRealY + 5, windowX + windowWeight - 15, windowY + valueRealY + 15, mouseX, mouseY)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("Downloading...");
                                downloadCodingFile(webPlugin.webFileCode, Napoline.NapolineDataFolder.getPath() + File.separator + "Plugins" + File.separator + webPlugin.pluginName + ".jar");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                valueRealY += 20;
            }
            return;

        }
        for (NapolinePlugin plugin : plugins) {
            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + valueRealY + 5, windowX + windowWeight - 15, windowY + valueRealY + 15, mouseX, mouseY)) {
                Napoline.pluginManager.setPluginState(plugin, !Napoline.pluginManager.isPluginEnabled(plugin));
                return;
            }
            valueRealY += 20;
        }

        for (NapolineScript script : scripts) {
            if (Clickgui.isHovered(windowX + windowWeight - 36, windowY + valueRealY + 5, windowX + windowWeight - 15, windowY + valueRealY + 15, mouseX, mouseY)) {
                Napoline.scriptManager.setScriptState(script, !Napoline.scriptManager.isScriptEnabled(script));
                return;
            }
            valueRealY += 20;
        }
    }

    public File downloadCodingFile(String fileCode, String fileSavePath) throws Exception {
        File file = null;
        BufferedInputStream bin = null;
        OutputStream out = null;
        String baseUrl = "https://qian-xia233.coding.net/api/share/download/";
        String fileUrl = baseUrl + fileCode;

        try {
            URL url = new URL(fileUrl);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 绕过Coding检测
            httpURLConnection.setRequestProperty("Referer", fileUrl);
            httpURLConnection.connect();

            int fileLength = httpURLConnection.getContentLength();

            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);
            fileFullName = fileFullName.substring(fileFullName.lastIndexOf("/") + 1);

            url.openConnection();

            bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = fileSavePath;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);

            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.toString());
        } finally {
            if (bin != null) {
                bin.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public static String downloadCodingFile(String fileCode) {
        StringBuilder stringbuilder = new StringBuilder();
        String baseUrl = "https://qian-xia233.coding.net/api/share/download/";
        String fileUrl = baseUrl + fileCode;

        try {
            URL url = new URL(fileUrl);
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setRequestProperty("Charset", "UTF-8");
            // 绕过Coding检测
            httpurlconnection.setRequestProperty("Referer", fileUrl);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                stringbuilder.append(s);
                stringbuilder.append('\r');
            }

            bufferedreader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringbuilder.toString();
    }

    public static String get(String urlPath) {
        StringBuilder stringbuilder = new StringBuilder();

        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setRequestProperty("Charset", "UTF-8");
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                stringbuilder.append(s);
                stringbuilder.append('\r');
            }

            bufferedreader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringbuilder.toString();
    }
}
