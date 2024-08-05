package fr.dog.ui.altmanager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dog.Dog;
import fr.dog.component.impl.misc.AltsComponent;
import fr.dog.util.packet.RequestUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Session;
import org.lwjglx.input.Keyboard;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class NewGuiAltManager extends GuiScreen {

    private final GuiScreen parent;
    private ArrayList<AltManagerButton> altManagerButtons = new ArrayList<>();

    @SneakyThrows
    public NewGuiAltManager(GuiScreen parent){

        AltsComponent.read();


        this.parent = parent;

        AltsComponent.addAlt(Minecraft.getMinecraft().getSession());

        altManagerButtons.add(new AltManagerButton("Browser Login", ()->{
            MicrosoftLogin.getRefreshToken(refreshToken -> {
                if (refreshToken != null) {
                    new Thread(() -> {
                        MicrosoftLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                        AltsComponent.addAlt(mc.getSession());
                        AltsComponent.write();
                    }).start();
                }
            });
        }));

        altManagerButtons.add(new AltManagerButton("Hass Alts", ()->{
            try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                String result = null;
                try {
                    result = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String dc = getDecrypt(result.replace(" ", "").split("\\|")[0]);
                String name = getName(dc.split(":")[0]);
                mc.setSession(new Session(name, dc.split(":")[0], dc.split(":")[1], "legacy"));
                AltsComponent.addAlt(mc.getSession());
                RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + name + "&uuid=" + dc.split(":")[0]);
                AltsComponent.write();
            }catch (Exception e){

            }
        }));

        altManagerButtons.add(new AltManagerButton("UUID:TOKEN", ()->{
            try {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                String result = null;
                try {
                    result = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String dc = result;
                String name = getName(dc.split(":")[0]);
                mc.setSession(new Session(name, dc.split(":")[0], dc.split(":")[1], "legacy"));
                AltsComponent.addAlt(mc.getSession());
                RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + name + "&uuid=" + dc.split(":")[0]);
                AltsComponent.write();
            }catch (Exception e){

            }
        }));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        RenderUtil.verticalGradient(0, 0, width, height, new Color(25, 25, 25), new Color(20, 20, 20));
        RenderUtil.drawRoundedRect(10, 10, width - 20, height - 20, 5, new Color(30,30,30));

        // first 1/3 of the screen is menu
        // other is alts

        RenderUtil.drawRoundedRect(15,15,width / 3f - 30, height - 30, 5, new Color(60,60,60));

        RenderUtil.drawRoundedRect(20,20,width / 3f - 40, 35, 5, new Color(80,80,80));




        Fonts.getOpenSansMedium(30).drawString(mc.getSession().getUsername(), 25,25, Color.WHITE.getRGB());
        Fonts.getOpenSansMedium(15).drawString(mc.getSession().getPlayerID(), 25,43, Color.WHITE.getRGB());
        RenderUtil.drawRect(25, 41, Math.max(Fonts.getOpenSansMedium(30).getWidth(mc.getSession().getUsername()), Fonts.getOpenSansMedium(15).getWidth(mc.getSession().getPlayerID())), 1, Color.WHITE);


        int yf = 60;
        for(AltManagerButton button : altManagerButtons){
            button.onDraw(20, yf, (int) (width / 3f - 40), 20);
            yf += 25;
        }
        int x = 0;
        int y = 0;

        ArrayList<Alt> alts = (ArrayList<Alt>) AltsComponent.getAltList().clone();

        for(Alt alt : alts){
            int bx = x * 105 + width / 3;
            int by = y * 25 + 20;

            if(bx > width - 100){
                x = 0;
                y += 1;
                bx = x * 105 + width / 3;
                by = y * 25 + 20;
            }
            alt.onDraw(bx, by);
            x++;
        }
    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(AltManagerButton b : altManagerButtons){
           b.onClick(mouseX, mouseY);
        }
        ArrayList<Alt> alts = (ArrayList<Alt>) AltsComponent.getAltList().clone();
        for(Alt alt : alts){
            alt.onMouseClick(mouseX, mouseY, mouseButton);
        }
    }
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == Keyboard.KEY_ESCAPE){
            mc.displayGuiScreen(parent);
        }
    }
    private MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        mc.setSession(new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft"));

        if (mc.getSession().getUsername() != null) {
            RequestUtil.requestResult.apply("/addMinecraftAccount?token=" + Dog.getInstance().getToken() + "&username=" + loginData.username + "&uuid=" + loginData.uuid);
        }

        return loginData;
    }
    @SneakyThrows
    public String getName(String uuid) {
        String name = "";
        try {
            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid).openStream()));
            name = (((JsonObject)new JsonParser().parse(in)).get("name")).toString().replaceAll("\"", "");
            in.close();
        } catch (Exception e) {
            System.out.println("Unable to get Name of: " + uuid + "!");
            name = "None - Issue";
        }
        return name;
    }

    @SneakyThrows
    public String getDecrypt(String bas){
        SecretKeySpec key = new SecretKeySpec("LiticaneFurryFemboy69420".getBytes(StandardCharsets.UTF_8), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(bas));
        return new String(decrypted).replaceAll("[^a-zA-Z0-9-._:]", "");
    }
}
