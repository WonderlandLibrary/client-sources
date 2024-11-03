package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.Client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AsyncScreenshots implements Runnable {

    public static final String prefix = "SLC Screenshot ";
    private static BufferedImage image;
    private static File screenshot;
    private final int width, height;
    private final int[] pixelValues;
    private final Minecraft mc = Minecraft.getMinecraft();
    private final File screenshotDirectory;

    public AsyncScreenshots(int width, int height, int[] pixelValues, File screenshotDirectory) {
        this.width = width;
        this.height = height;
        this.pixelValues = pixelValues;
        this.screenshotDirectory = screenshotDirectory;
    }

    private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
        String dateFormatting = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        int screenshotCount = 1;
        File screenshot;

        while (true) {
            screenshot = new File(
                gameDirectory,
                dateFormatting + ((screenshotCount == 1) ? "" : ("_" + screenshotCount)) + ".png"
            );
            if (!screenshot.exists()) {
                break;
            }

            ++screenshotCount;
        }

        return screenshot;
    }

    @Override
    public void run() {
        processPixelValues(pixelValues, width, height);
        screenshot = getTimestampedPNGFileForDirectory(screenshotDirectory);
        Client.getInstance().getScreenshotManager().setScreenshot(screenshot);

        try {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image.setRGB(0, 0, width, height, pixelValues, 0, width);
            ImageIO.write(image, "png", screenshot);
            Client.getInstance().getScreenshotManager().setImage(image);

            sendChatMessages(screenshot);
        } catch (Exception e) {
        	if(Minecraft.getMinecraft().thePlayer != null) {
        		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix + "Failed to capture screenshot. " + e.getMessage()));
        	}
			Client.logger.error("Failed to capture screenshot.", e);
        }
    }

    private void sendChatMessages(File screenshot) throws IOException {
        IChatComponent chatComponent;
        chatComponent = new ChatComponentText("SLC Screenshot");

        final IChatComponent deleteComponent = new ChatComponentText(EnumChatFormatting.RED.toString() + EnumChatFormatting.BOLD +
"[DELETE]");
        deleteComponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/$delete"));

        final IChatComponent copyComponent = new ChatComponentText(EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD +
        		"[COPY]");
        copyComponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/$copyss"));

        final IChatComponent folderComponent = new ChatComponentText(EnumChatFormatting.BLUE.toString() + EnumChatFormatting.BOLD +
            "[OPEN]");
        folderComponent.getChatStyle()
            .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, screenshot.getCanonicalPath()));
        
        chatComponent.appendText(" ").appendSibling(folderComponent);
        chatComponent.appendText(" ").appendSibling(copyComponent);
        chatComponent.appendText(" ").appendSibling(deleteComponent);

        final GuiNewChat chat = mc.ingameGUI.getChatGUI();
        chat.printChatMessage(chatComponent);
    }

    private void processPixelValues(int[] pixels, int displayWidth, int displayHeight) {
        final int[] xValues = new int[displayWidth];
        for (int yValues = displayHeight >> 1, val = 0; val < yValues; ++val) {
            System.arraycopy(pixels, val * displayWidth, xValues, 0, displayWidth);
            System.arraycopy(
                pixels,
                (displayHeight - 1 - val) * displayWidth,
                pixels,
                val * displayWidth,
                displayWidth
            );
            System.arraycopy(xValues, 0, pixels, (displayHeight - 1 - val) * displayWidth, displayWidth);
        }
    }

    static class ImageSelection implements Transferable {
        private final Image image;

        ImageSelection(Image image) {
            this.image = image;
        }

        // Returns supported flavors
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        // Returns true if flavor is supported
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor == flavor;
        }

        // Returns image
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (DataFlavor.imageFlavor != flavor) {
                throw new UnsupportedFlavorException(flavor);
            }

            return image;
        }
    }
}
