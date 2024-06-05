package gq.vapu.czfclient.Command.Commands;


import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Send extends Command {
    public int width = 240;
    public int height = 16;
    public byte[][] data = new byte[height][width];
    public int width_index = 0;
    public byte[][] encode = new byte[][]{
            new byte[]{1, 4},
            new byte[]{2, 5},
            new byte[]{3, 6},
            new byte[]{7, 8},
    };
    public Send() {
        super("send", new String[]{"send"}, "", "send");
    }

    @Override
    public String execute(String[] args) {
        String msg = args[0];
//ExampleMod.this.displayChatMessage(msg);
        //String msg = s.substring(2);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        //boolean unicodeFlag = fontRenderer.getUnicodeFlag();
        byte[] glyphWidth = new byte[0];

        this.reset();
        for (int i = 0; i < msg.length(); ++i) {
            char character = msg.charAt(i);
            int page = character / 256;
            BufferedImage bufferImage;
            try {
                bufferImage = TextureUtil.readBufferedImage(
                        Helper.mc.getResourceManager().getResource(
                                new ResourceLocation(
                                        String.format("textures/font/unicode_page_%02x.png", page)
                                )).getInputStream()
                );
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            //fontRenderer.setUnicodeFlag(true);
            int x = character % 16 * bufferImage.getWidth() / 16;
            int width = (fontRenderer.getCharWidth(character) - 1) * 2;

            x = width <= 1 ? x + 4 : (width <= bufferImage.getHeight() / 16 / 8 ? x + 2 : (width <= bufferImage.getHeight() / 16 / 4 ? x + 1 : (x)));
            width = (int) (width <= 1 ? width + 2 : (width <= bufferImage.getHeight() / 16 / 8 ? width * 3 : (width <= bufferImage.getHeight() / 16 / 4 ? width * 2 : (width <= bufferImage.getHeight() / 16 / 2 ? width * 1.5 : width))));
            if (character == ' ')
                width /= 2;
            int height = bufferImage.getHeight() / 16;
//					MiniMap.this.log(String.format("width: %s", width));
            if (glyphWidth[character] == 0 || width == 0) {
                continue;
            }


            //int f2 = (character % 16 * 16) + f;
            int y = (character & 255) / 16 * 16;
            int[] tmpdata = new int[width * height];
            if (character != ' ')
                bufferImage.getRGB(x, y, width, height, tmpdata, 0, width);
            this.put(tmpdata, width);

        }

        for (String ss : this.toStringList()) {
//                Wrapper.INSTANCE.player().sendChatMessage(ss);
            Helper.mc.thePlayer.sendChatMessage(ss);
        }
        //fontRenderer.setUnicodeFlag(unicodeFlag);
        return null;
    }

    public void reset() {
        for (byte[] datum : data) {
            Arrays.fill(datum, (byte) 1);
        }
        width_index = 0;
    }

    public List<String> toStringList() {
        List<String> list = new ArrayList<>();
        for (int y = 0; y < this.height; y += 4) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < Math.min(this.width, this.width_index); x += 2) {
                sb.append(this.genChar(x, y));
            }
            list.add(sb.toString());
        }
        return list;
    }

    public void put(int[] datas, int w) {
        for (int i = 0; i <= datas.length; i++) {
            int x, y;
            x = (i % w) + width_index;
            y = i / w;
            //byte data = (byte) datas[i];
            if (x >= this.width || y >= this.height)
                continue;
            this.data[y][x] = (byte) (datas[i] >> 24 == 0 ? 1 : 0);
        }

        this.width_index += w;
    }

    public char genChar(int x, int y) {
        int c = 0x2800;
        for (int dx = 0; dx < 2; dx++) {
            for (int dy = 0; dy < 4; dy++) {
                int format = 1 << (encode[dy][dx] - 1);
                int tx, ty;
                tx = x + dx;
                ty = y + dy;
                if (x >= width || y >= height)
                    continue;
                if (this.data[ty][tx] == 0)
                    c += format;
            }
        }

        return (char) c;
    }

    @Override
    public String getSyntax() {
        return "fa NM$L";
    }
}

