package fr.dog.ui.mainmenu;

import fr.dog.ui.altmanager.NewGuiAltManager;
import fr.dog.util.input.MouseUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.animation.Animation;
import fr.dog.util.render.animation.Easing;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.img.ImageManager;
import fr.dog.util.render.img.ImageObject;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainMenuScreen extends GuiScreen {
    private final List<MainMenuButton> mainButtons = Arrays.asList(
            new MainMenuButton(ButtonType.SINGLEPLAYER, () -> mc.displayGuiScreen(new GuiSelectWorld(this))),
            new MainMenuButton(ButtonType.MULTIPLAYER, () -> mc.displayGuiScreen(new GuiMultiplayer(this))),
            new MainMenuButton(ButtonType.SETTINGS, () -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings)))
    );

    private final List<MainMenuSecondaryButton> secondaryButtons = Arrays.asList(
            new MainMenuSecondaryButton(ButtonType.ALTMANAGER, () -> mc.displayGuiScreen(new NewGuiAltManager(this))),
            new MainMenuSecondaryButton(ButtonType.CLOSE, () -> mc.shutdown())
    );

    private final Animation hoverAnimation = new Animation(Easing.EASE_OUT_QUAD, 500);
    private ImageObject backgroundImage;

    public void initGui() {
        backgroundImage = ImageManager.menuImages.get("wallpaper");
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean hoveringWallpaper = MouseUtil.isHovering(mouseX,mouseY,0, height-30,width,30);
        float mouseDeltaX = (((float) mouseX / width)*2)-2;
        float mouseDeltaY = (((float) mouseY / height)*2)-2;

        RenderUtil.drawRect(0,0,width,height, new Color(0,0,0));
        backgroundImage.drawImg(mouseDeltaX*2,mouseDeltaY*2,width+4,height+4);
        RenderUtil.drawRect(0,0,width,height, new Color(0,0,0,100));

        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.ENGLISH);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = currentDate.format(dateFormatter);
        String textTime = currentTime.format(timeFormatter);

        Fonts.getOpenSansBold(25).drawCenteredString(formattedDate, width / 2f,height/6f, new Color(255,255,255,150).getRGB());
        Fonts.getOpenSansBold(125).drawCenteredString(textTime, width / 2f,height/6f, new Color(255,255,255,150).getRGB());

        RenderUtil.drawBlur(() -> drawBlur(mouseX,mouseY));

        hoverAnimation.run(hoveringWallpaper ? 1.0F : 0.0F);
        RenderUtil.drawRect(0, (float) (height - 30 * hoverAnimation.getValue()), width, 30, new Color(0,0,0,150));
        Fonts.getOpenSansBold(15).drawCenteredString("Change Wallpaper", width / 2f, (float) (height - 20 * hoverAnimation.getValue()), new Color(255,255,255,(int) (255*(hoverAnimation.getValue()))).getRGB());

        //buttons
        float offset = this.width/2f - 100;
        for(MainMenuButton button : mainButtons) {
            button.render(10 + offset - 35, (float) (height - 100 - hoverAnimation.getValue()*10), mouseX, mouseY);
            offset+=100;
        }

        offset = this.width - 35*secondaryButtons.size();
        for(MainMenuSecondaryButton button : secondaryButtons) {
            button.render(offset, 10, mouseX, mouseY);
            offset+=35;
        }
    }

    private void drawBlur(final int mouseX, final int mouseY){
        for(MainMenuButton button : mainButtons) {
            button.renderBlur(mouseX,mouseY);
        }

        for(MainMenuSecondaryButton button : secondaryButtons) {
            button.renderBlur(mouseX,mouseY);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean hoveringWallpaper = MouseUtil.isHovering(mouseX,mouseY,0, height-30,width,30);
        mainButtons.forEach(button -> button.click(mouseX, mouseY));
        secondaryButtons.forEach(button -> button.click(mouseX, mouseY));

        if(hoveringWallpaper){
            EventQueue.invokeLater(this::openFileChooser);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    @SuppressWarnings("all")
    private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String name = f.getName().toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            mc.addScheduledTask(() -> {
                ImageManager.menuImages.get("wallpaper").unload();
                ImageManager.menuImages.replace("wallpaper", new ImageObject(new File(selectedFile.getAbsolutePath())));
                ImageManager.menuImages.get("wallpaper").loadAsync();
                this.initGui();
            });
        }
    }

}
