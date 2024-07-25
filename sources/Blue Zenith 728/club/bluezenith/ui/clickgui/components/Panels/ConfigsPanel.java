package club.bluezenith.ui.clickgui.components.Panels;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.ui.clickgui.components.Panel;
import club.bluezenith.util.client.FileUtil;
import club.bluezenith.util.MinecraftInstance;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.ColorUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static club.bluezenith.util.render.RenderUtil.rect;
import static org.lwjgl.input.Keyboard.*;
import static org.lwjgl.input.Mouse.isButtonDown;

public class ConfigsPanel extends Panel {
    private final StringValue val = new StringValue("Name", "", true, (a1, a2) -> {
       if(a2.length() > 25) return a1;
       else return a2;
    }, null);

    private StringValue selectedTextField = null;
    private float textFieldCounter = 0f;
    private String selectedConfig;
    private boolean sex = false;
    private final ArrayList<File> files = new ArrayList<>();
    public ConfigsPanel(float x, float y) {
        super(x , y, "Configs");
        f = FontUtil.inter28;
        mHeight = f.FONT_HEIGHT + 9; //- 3.5f;
        originalMheight = mHeight;
        width = 120 - 10;

    }
    public void update(){
        textFieldCounter = 0f;
        files.clear();
        File directory = new File(FileUtil.configFolder);
        if(directory.exists() && directory.isDirectory()){
            for (File f : Objects.requireNonNull(directory.listFiles())) {
                if(f != null && f.exists() && f.isFile() && FilenameUtils.getExtension(f.getName()).equalsIgnoreCase("json")){
                    this.files.add(f);
                }
            }
        }
    }

    public float getHeightFirstTime() {
        update();
        float a = this.y;
        for(int i = 0; i < this.files.size() + 4; i++) {
            a += this.mHeight;
        }
        return a;
    }
    public void drawPanel(int mouseX, int mouseY, float partialTicks, boolean handleClicks) {
        if(!isButtonDown(0)) sex = false;
        textFieldCounter += 0.1f;
        Color mainColor = click.primaryColor.get();
        Color backgroundColor = click.oldDropdownBackground;
        float borderWidth = 1.5f;
        rect(x - borderWidth, y, x + width + borderWidth, y + mHeight, new Color(21, 21, 21));
        f.drawString("Configs", x + 4, y + mHeight / 2f - f.FONT_HEIGHT / 2f, Color.WHITE.getRGB());
        if(!showContent) return;
        float y = this.y + mHeight;
        for (File file : files) {
            String no = FilenameUtils.removeExtension(file.getName());
            rect(x - borderWidth, y, x, y + mHeight, backgroundColor.darker());
            rect(x + width, y, x + width + borderWidth, y + mHeight, backgroundColor.darker());
            rect(x, y, x + width, y + mHeight, backgroundColor);
            f.drawString(no, x + width/2-f.getStringWidth(no)/2f, y + (mHeight / 2f - f.FONT_HEIGHT / 2f), selectedConfig != null && selectedConfig.equals(no) ? mainColor.getRGB() : ColorUtil.grayColor.getRGB());
            if(i(mouseX, mouseY, x, y, x + width, y + mHeight) && !sex && isButtonDown(0) && handleClicks){
                sex = true;
                selectedConfig = no;
                //ConfigManager.load(no, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT), (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_X)) || Keyboard.isKeyDown(Keyboard.KEY_X));
                toggleSound();
            }
            y += mHeight;
        }
        rect(x - borderWidth, y, x, y + 20, backgroundColor.darker());
        rect(x + width, y, x + width + borderWidth, y + 30, backgroundColor.darker());
        rect(x, y, x + width, y + 20, backgroundColor);
        boolean blank = StringUtils.isBlank(val.get());
        boolean selected = this.selectedTextField == val;
        f.drawString(blank ? val.name + (selected ? "_" : "..") : val.get() + (selectedTextField != null && selectedTextField == val && textFieldCounter > partialTicks % 0.5 ? "_" : ""), blank ? x + width/2-f.getStringWidth(val.name)/2f : x + width/2 - f.getStringWidth(val.get())/2f, y + (20 / 2f - f.FONT_HEIGHT / 2f), blank ? Color.GRAY.getRGB() : Color.WHITE.getRGB());
        if (isButtonDown(0) && i(mouseX, mouseY, x, y, x + width, y + 19) && handleClicks && !sex) {
            sex = true;
            this.selectedTextField = val;
            toggleSound();
        } else if(isButtonDown(0) && !i(mouseX, mouseY, x, y, x + width, y + 10) && handleClicks && !sex) {
            this.selectedTextField = null;
        }
        y += 10;

        rect(x,  y + 10, x + width, y + 30, backgroundColor);
        rect(x - borderWidth, y + 10, x, y + 30, backgroundColor.darker());
        rect(x + width, y + 10, x + width + borderWidth, y + 30, backgroundColor.darker());
        f.drawString("Save", x + width/2 - f.getStringWidth("Save")/2f, y + 10 + (mHeight / 2f - f.FONT_HEIGHT / 2f), mainColor.brighter().getRGB());
        if(isButtonDown(0) && i(mouseX, mouseY, x, y  + 5, x + width, y + 30) && handleClicks && !sex) {
            update();
            sex = true;
            if(StringUtils.isBlank(val.get())) {
                if(selectedConfig != null) {
                    BlueZenith.getBlueZenith().getConfigManager().saveConfig(selectedConfig, true);
                    update();
                    val.set("");
                    selectedConfig = null;
                } else {
                    BlueZenith.getBlueZenith().getNotificationPublisher().postError("Config Manager", "Specify a config name to proceed.", 2000);
                }
            } else {
                BlueZenith.getBlueZenith().getConfigManager().saveConfig(val.get(), true);
                update();
                val.set("");
            }
        }
        y += 10;
        rect(x - borderWidth, y + 20, x, y + 40, backgroundColor.darker());
        rect(x + width, y + 20, x + width + borderWidth, y + 40, backgroundColor.darker());
        rect(x,  y + 20, x + width, y + 40, backgroundColor);
        f.drawString("Delete", x + width/2 - f.getStringWidth("Delete")/2f, y + 20 + (mHeight / 2f - f.FONT_HEIGHT / 2f), mainColor.brighter().getRGB());
        if(isButtonDown(0) && i(mouseX, mouseY, x, y  + 20, x + width, y + 40) && handleClicks && !sex) {
            sex = true;
            if(selectedConfig != null) {
                boolean a = new File(FileUtil.configFolder + File.separator + selectedConfig + ".json").delete();
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Config Manager", "Deleted config " + selectedConfig, 2000);
                selectedConfig = null;
                update();
            } else BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Config Manager", "Select a config to delete!", 2000);
            val.set("");
        }
        y += 10;
        rect(x - borderWidth, y + 30, x, y + 50, backgroundColor.darker());
        rect(x + width, y + 30, x + width + borderWidth, y + 50, backgroundColor.darker());
        rect(x,  y + 30, x + width, y + 50, backgroundColor);
        rect(x - borderWidth, y + 50, x + width + borderWidth, y + 50 + borderWidth, backgroundColor.darker());
        f.drawString("Load", x + width/2 - f.getStringWidth("Load")/2f, y + 30 + (mHeight / 2f - f.FONT_HEIGHT / 2f), mainColor.brighter().getRGB());
        if(isButtonDown(0) && i(mouseX, mouseY, x, y  + 30, x + width, y + 50) && handleClicks && !sex) {
            sex = true;
            if(selectedConfig != null) {
                BlueZenith.getBlueZenith().getConfigManager().loadConfigFromName(selectedConfig, isKeyDown(KEY_LSHIFT), isKeyDown(KEY_X), true);
                selectedConfig = null;
            } else BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Config Manager", "Select a config to load!", 2000);
            val.set("");
        }
    }

    public void keyTyped(char charTyped, int keyCode){
        Keyboard.enableRepeatEvents(true);
        if(selectedTextField == null || selectedTextField.get() == null){
            if (keyCode == 1 && selectedTextField == null) {
                MinecraftInstance.mc.displayGuiScreen(null);

                if (MinecraftInstance.mc.currentScreen == null) {
                    MinecraftInstance.mc.setIngameFocus();
                }
            }
            return;
        }
        String fieldText = selectedTextField.get();
        if(keyCode == 14) {
            selectedTextField.set(fieldText.substring(0, fieldText.length() > 0 ? fieldText.length() - 1 : 0));
        }else if(keyCode == 28 || keyCode == Keyboard.KEY_ESCAPE){
            selectedTextField = null;
        }else if(!Character.isISOControl(charTyped)){
            selectedTextField.set(fieldText + charTyped);
        }
    }
}
