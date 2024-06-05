package gq.vapu.czfclient.Module.Modules.Misc.domcer;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.API.eventapi.EventTarget;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DomcerFucker extends Module {

    private static final Executor executor = Executors.newCachedThreadPool();

    private static IntBuffer pixelBuffer;
    private static int[] pixelValues;

    byte[] uuid = UUID.randomUUID().toString().getBytes();

    public DomcerFucker() {
        super("DomcerFuck", new String[]{}, ModuleType.Blatant);
    }

    public void onEnable() {
//        Helper.sendMessage("本功能无法绕过红字反作弊");
        this.setSuffix("DoMCer");
        uuid = UUID.randomUUID().toString().getBytes();
    }

    @EventTarget
    public void onPacket(final EventPacketReceive event) {
        this.setSuffix("DoMCer");
        if (event.getPacket() instanceof S3FPacketCustomPayload) {
            S3FPacketCustomPayload packet = (S3FPacketCustomPayload) event.getPacket();
            byte[] data = new byte[packet.getBufferData().readableBytes()];
            packet.getBufferData().readBytes(data);
            if ("REGISTER".equals(packet.getChannelName())) {
                String salutation = Joiner.on('\0')
                        .join(Arrays.asList("FML|HS", "FML", "FML|MP", "CustomSkinLoader", "UView"));
                C17PacketCustomPayload proxy = new C17PacketCustomPayload("REGISTER",
                        new PacketBuffer(Unpooled.wrappedBuffer(salutation.getBytes(Charsets.UTF_8))));
                //mc.getNetHandler().addToSendQueueNoEvent(proxy);\
                mc.thePlayer.sendQueue.addToSendQueue1(proxy);
                if (new String(data).contains("CustomSkinLoader")) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("CustomSkinLoader",
                            new PacketBuffer(Unpooled.wrappedBuffer(UUID.randomUUID().toString().getBytes()))));
                    System.out.println("Bypass DoMCer1");
                }
                if (new String(data).contains("UView")) {
                    FakePacket.send();
                    System.out.println("Bypass DoMCer2");
                }
            }
            if ("CustomSkinLoader".equals(packet.getChannelName())) {

                mc.addScheduledTask(() -> {
                    String id = new String(packet.getBufferData().array());

                    int width = 1;
                    int height = 1;
                    Framebuffer buffer = mc.getFramebuffer();

                    int l = width * height;

                    if (pixelBuffer == null || pixelBuffer.capacity() < l) {
                        pixelBuffer = BufferUtils.createIntBuffer(l);
                        pixelValues = new int[l];
                    }
                    GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
                    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
                    pixelBuffer.clear();

                    if (OpenGlHelper.isFramebufferEnabled()) {
                        GlStateManager.bindTexture(buffer.framebufferTexture);
                        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
                                pixelBuffer);
                    } else {
                        GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
                                pixelBuffer);
                    }
                    pixelBuffer.get(pixelValues);
                    TextureUtil.processPixelValues(pixelValues, width, height);
                    BufferedImage bufferedimage = null;

                    if (OpenGlHelper.isFramebufferEnabled()) {
                        bufferedimage = new BufferedImage(buffer.framebufferWidth, buffer.framebufferHeight, 1);
                        int i1 = buffer.framebufferTextureHeight - buffer.framebufferHeight;

                        for (int j1 = i1; j1 < buffer.framebufferTextureHeight; ++j1) {
                            for (int k1 = 0; k1 < buffer.framebufferWidth; ++k1) {
                                bufferedimage.setRGB(k1, j1 - i1,
                                        pixelValues[j1 * buffer.framebufferTextureWidth + k1]);
                            }
                        }
                    } else {
                        bufferedimage = new BufferedImage(width, height, 1);
                        bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
                    }
                    BufferedImage finalBufferedimage = bufferedimage;

                    //截图一个像素

                    executor.execute(() -> {
                        try {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            boolean foundWriter = ImageIO.write(finalBufferedimage, "jpg", baos);
                            assert (foundWriter);
                            byte[] bytes = baos.toByteArray();
                            String url = "https://upload.server.domcer.com:25566/uploadJpg?key=0949a0d0-bc98-4535-9f5e-086835123f75&type="
                                    + id;
                            HashMap<String, byte[]> map = new HashMap<String, byte[]>();
                            map.put("file", bytes);
                            map.put("check", FakePacket.getMD5List().getBytes());//假MD5列表
                            HttpUtil.HttpResponse response = HttpUtil.postFormData(url, map, null, null);
                            String result = response.getContent();
                            ByteBuf buf = Unpooled.wrappedBuffer(
                                    (id + ":" + new Gson().fromJson(result, JsonObject.class).get("data")
                                            .getAsString()).getBytes());
                            C17PacketCustomPayload proxyPacket = new C17PacketCustomPayload("CustomSkinLoader",
                                    new PacketBuffer(buf));
                            mc.thePlayer.sendQueue.addToSendQueue(proxyPacket);
                            System.out.println("Bypassed DoMCer Final");
                        } catch (IOException e) {

                        }
                    });
                });

            }
        }
    }
}
