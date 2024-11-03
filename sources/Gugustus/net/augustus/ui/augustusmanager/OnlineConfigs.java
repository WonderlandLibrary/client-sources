package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import net.augustus.Augustus;
import net.augustus.ui.widgets.ConfigButton;
import net.augustus.ui.widgets.CustomButton;
import net.augustus.utils.PlayerUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.lwjgl.input.Mouse;

public class OnlineConfigs extends GuiScreen {
    private GuiTextField createTextField;
    private GuiScreen parent;
    private ConfigButton selected;
    private HashMap<Integer, String> loadedConfigs = new HashMap<>();
    private HashMap<Integer, String> urls = new HashMap<>();

    private ArrayList<ConfigButton> configButtons = new ArrayList<>();

    public GuiScreen start(GuiScreen parent) {
        this.parent = parent;
        return this;
    }


    public OnlineConfigs(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.createTextField = new GuiTextField(1, this.fontRendererObj, sr.getScaledWidth() / 2 - 75, sr.getScaledHeight() - 55, 150, 20);
        this.createTextField.setMaxStringLength(1377);
        /*
        this.buttonList.add(new CustomButton(2, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 105, 100, 20, "Create", Augustus.getInstance().getClientColor()));
        this.buttonList.add(new CustomButton(3, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 80, 100, 20, "Load", Augustus.getInstance().getClientColor()));
        this.buttonList.add(new CustomButton(4, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 55, 100, 20, "Save", Augustus.getInstance().getClientColor()));
        this.buttonList.add(new CustomButton(5, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 30, 100, 20, "Delete", Augustus.getInstance().getClientColor()));
         */
        this.buttonList.add(new CustomButton(69420, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 30, 100, 20, "Refresh", Augustus.getInstance().getClientColor()));
        //this.initConfigs();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 69420) {
            //this.createConfig();
            reDownload();
        }


        super.actionPerformed(button);
    }
    public static void downloadJSON() throws IOException {
        URL jsonUrl = new URL("https://raw.githubusercontent.com/JonnyOnlineYT/xenzaconfigs/main/onlineconfigs.json");
        URLConnection connection = jsonUrl.openConnection();
        BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(Paths.get("gugustus/onlineconfigs.json").toFile());

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        fileOutputStream.close();
        in.close();
    }

    private void reDownload() throws IOException {
        downloadJSON();
        refresh();
    }

    private void refresh() {
        ScaledResolution sr = new ScaledResolution(mc);
        int yAdd = 0;
        int highest = 0;
        try {
            // Read the JSON file
            FileReader fileReader = new FileReader("gugustus/onlineconfigs.json");
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONObject jsonConfig = new JSONObject(tokener);

            // Access the "Names" array
            JSONArray namesArray = jsonConfig.getJSONArray("configs");

            // Iterate over each object in the array
            for (int i = 0; i < namesArray.length(); i++) {
                JSONObject nameObj = namesArray.getJSONObject(i);

                // Access the properties of each name
                String name = nameObj.getString("name");
                JSONObject linkObj = nameObj.getJSONObject("link");
                String link = linkObj.getString("value");
                JSONObject idObj = nameObj.getJSONObject("id");
                int id = idObj.getInt("value");

                // Print the information
                //System.out.println("Name: " + name);
                //System.out.println("Link: " + link);
                //System.out.println("ID: " + id);
                //System.out.println();
                loadedConfigs.put(id, name);
                urls.put(id, link);
                highest = id;
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 1; i<highest+1; i++) {
            this.configButtons.add(new ConfigButton(i, sr.getScaledWidth() / 2 - 140, 55 + yAdd, 280, 35, loadedConfigs.get(i), "", "", Color.gray));
            yAdd += 36;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sr = new ScaledResolution(this.mc);
        drawRect(sr.getScaledWidth() / 2 - 150, 0, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight(), new Color(0, 0, 0, 190).getRGB());
        boolean b = true;
        float mdw = (float)Mouse.getDWheel() / 10.0F;
        float y = 0.0F;
        if (this.configButtons.size() > 0) {
            if ((float)(this.configButtons.get(this.configButtons.size() - 1).yPosition + this.configButtons.get(this.configButtons.size() - 1).getHeight()) + mdw
                    < (float)(sr.getScaledHeight() - 140)
                    && mdw < 0.0F) {
                b = false;
                y = (float)(55 + (sr.getScaledHeight() - 140 - 55 - this.configButtons.size() * 36) + 2);
            } else if ((float)this.configButtons.get(0).yPosition + mdw > 55.0F && mdw > 0.0F) {
                b = false;
                y = 55.0F;
            }

            float yAdd = 0.0F;

            for(ConfigButton configButton : this.configButtons) {
                if (this.configButtons.size() > 1 && this.configButtons.size() * 36 > sr.getScaledHeight() - 140 - 55) {
                    if (b) {
                        configButton.yPosition = (int)((float)configButton.yPosition + mdw);
                    } else {
                        configButton.yPosition = (int)(y + yAdd);
                    }
                }

                configButton.draw(this.mc, mouseX, mouseY);
                yAdd += 36.0F;
            }
        }

        drawRect(sr.getScaledWidth() / 2 - 150, 0, sr.getScaledWidth() / 2 + 150, 55, new Color(15, 15, 15, 255).getRGB());
        drawRect(
                sr.getScaledWidth() / 2 - 150, sr.getScaledHeight() - 65, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight(), new Color(15, 15, 15, 255).getRGB()
        );
        drawRect(sr.getScaledWidth() / 2 - 150, 55, sr.getScaledWidth() / 2 - 140, sr.getScaledHeight() - 140, new Color(15, 15, 15, 255).getRGB());
        drawRect(sr.getScaledWidth() / 2 + 140, 55, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight() - 140, new Color(15, 15, 15, 255).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.createTextField.drawTextBox();
        GlStateManager.pushMatrix();
        GlStateManager.scale(3.0F, 3.0F, 1.0F);
        this.fontRendererObj
                .drawStringWithShadow(
                        "Online Configs", (float)sr.getScaledWidth() / 2.0F / 3.0F - (float)this.fontRendererObj.getStringWidth("Online Configs") / 2.0F, 5.0F, Color.gray.getRGB()
                );
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.createTextField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.createTextField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 || mouseButton == 0) {
            for(ConfigButton configButton : this.configButtons) {
                if (this.mouseOver(
                        (double)mouseX,
                        (double)mouseY,
                        (double)configButton.xPosition,
                        (double)configButton.yPosition,
                        (double)configButton.getButtonWidth(),
                        (double)configButton.getHeight()
                )) {
                    this.selected = configButton;
                    this.handleClick(selected.id);
                    configButton.setSelected(false);
                } else {
                    configButton.setSelected(false);
                }
            }
        }
    }

    private void handleClick(int id) {
        selected.displayString = "Downloading...";
        new Thread(() -> {
            String fileUrl = urls.get(id);
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new FileWriter(new File("gugustus/configs/", loadedConfigs.get(id) + ".json")));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        out.write(inputLine);
                        out.newLine();
                    }
                    in.close();
                    out.close();
                    System.out.println("File downloaded successfully.");
                    selected.displayString = "Done!";
                } else {
                    System.out.println("File download failed. Response code: " + responseCode);
                    selected.displayString = "Error downloading";
                }
            } catch (IOException e) {
                e.printStackTrace();
                selected.displayString = "Error downloading";
            }
        }).start();
    }

    @Override
    public void updateScreen() {
        this.createTextField.updateCursorCounter();
        super.updateScreen();
    }

    private void initConfigs() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.configButtons = new ArrayList<>();
        if (!Files.exists(Paths.get("gugustus/configs"))) {
            try {
                Files.createDirectories(Paths.get("gugustus/configs"));
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }

        File f = new File("gugustus/configs");
        File[] fileArray = f.listFiles();
        int yAdd = 0;

        assert fileArray != null;

        for(File file : fileArray) {
            if (file.getName().contains(".json")) {
                String name = "";

                for(int i = 0; i < file.getName().length() - 5; ++i) {
                    name = name + file.getName().charAt(i);
                }

                String[] s = Augustus.getInstance().getConverter().configReader(name);
                this.configButtons.add(new ConfigButton(6, sr.getScaledWidth() / 2 - 140, 55 + yAdd, 280, 35, name, s[1], s[2], Color.gray));
                yAdd += 36;
            }
        }
    }

    public boolean mouseOver(double mouseX, double mouseY, double posX, double posY, double width, double height) {
        return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
    }
}
