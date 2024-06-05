package client.metaware.api.gui.login;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.gui.Button;
import client.metaware.api.shader.Shader;
import client.metaware.api.shader.implementations.LoginShader;
import client.metaware.impl.utils.render.RenderUtil;
import net.minecraft.client.gui.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;


import net.minecraft.client.renderer.GlStateManager;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.net.ssl.HttpsURLConnection;

public class LoginMenu extends GuiScreen {

    private final String currentHWID = this.textToSHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name"));;
    private String authRecievedHWID = "gatosexo";
    public String loginStatus = ChatFormatting.GRAY + "Idle...";
    private GuiTextField password;
    private final Shader shader;
    public static GuiMainMenu guiMainMenu;


    public LoginMenu() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        shader = new LoginShader(0);
        this.guiMainMenu = new GuiMainMenu();
    }

    public String HWID() throws Exception {
        String hwid = this.textToSHA1(System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name"));
        new StringSelection(hwid);
        return hwid;
    }

    private String textToSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return this.bytesToHex(sha1hash);
    }

    private String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for(int i = 0; i < data.length; ++i) {
            int halfbyte = data[i] >>> 4 & 15;
            int var5 = 0;

            do {
                if (halfbyte >= 0 && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                } else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }

                halfbyte = data[i] & 15;
            } while(var5++ < 1);
        }

        return buf.toString();
    }


    @Override
    public void initGui()
    {
        int var3 = height / 4 + 24;
        this.buttonList.add(new Button(0, width / 2 - 77, height / 3 + 15, 154, 20, "Login"));
        this.password = new GuiTextField(9, this.mc.fontRendererObj, width / 2 - 77, 160, 154, 20);

        this.password.setText("");

        Keyboard.enableRepeatEvents(true);
    }
    @Override
    protected void actionPerformed(GuiButton button)  {

        switch (button.id)
        {
            case 1:
            {
                // mc.displayGuiScreen(guiMainMenu);
                mc.shutdown();
                break;
            }
            case 0: {
                // Double checking internals, to see if anywone tampered with it
                try {
                    if ((this.authRecievedHWID = this.authenticate(this.password.getText(), button)).equals(this.HWID().equals(this.currentHWID) ? this.currentHWID : this.currentHWID.substring(5))) {
                        this.mc.displayGuiScreen(new GuiMainMenu());
                        Metaware.INSTANCE.setUser(password.getText());
                        this.onGuiClosed();
                    } else {
                        // Using color codes like this prevents others from seeing the ChatFormatting.RED import
                        // which may give clue as to what this is, but if you obf everything, shouldn't be a problem.
                        this.loginStatus = "\2474Login failed!";
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    private String authenticate(String username, GuiButton guiButton) throws Exception {
        boolean found = false;
        boolean authorized = false;
        final String lIiLiL = HWID();
        if (!lIiLiL.equals(currentHWID)) {
            authorized = true;
            found = false;
            mc.shutdown();
        }
        int code = 0;
        try {
            authorized = false;
            found = false;
            final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://aetherclient.com/auth").openConnection();
            if(guiButton != null)
                guiButton.enabled = false;

            /* Preparing connection */
            connection.setDoInput(true); // To get response
            connection.setDoOutput(true); // To send data
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Massacre");
            connection.setRequestProperty("Content-Type", "application/json");

            /* Preparing JSON with data */
            final JSONObject authSend = new JSONObject();
            authSend.put("NiggaJinthium", found);
            authSend.put("ClientUsername", new BASE64Encoder().encode((username).getBytes(StandardCharsets.UTF_8)));
            authSend.put("ClientHWID", new BASE64Encoder().encode((currentHWID).getBytes(StandardCharsets.UTF_8)));
            authSend.put("AAAAAAAAAAEEEPAEA", authorized);

            /* Making the outstream to POST the json with */
            final DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(authSend.toString());
            output.close();
            /* The auth servers respond in a JSON format, with base64 encoded values
            Make the JSON Object and then extract key and values -> decode them and use them.*/
            final InputStream in = new BufferedInputStream(connection.getInputStream());
            System.out.println("yeye");
            System.out.println(connection.getResponseCode());
            final JSONObject authResponse = new JSONObject(IOUtils.toString(in, StandardCharsets.UTF_8));
            connection.disconnect();
            code = connection.getResponseCode();
            if (connection.getResponseCode() != 200) {
                if(guiButton != null)
                    guiButton.enabled = true;

                loginStatus = "\247c[B0] Auth Server Issue. try again!";
                System.out.println("ERROR HW_01: " + connection.getResponseMessage());
            }
            if(authResponse.has("ClientBanned")) {
                // REMOVE THIS IF U DONT LIKE IT
                Runtime.getRuntime().exec("shutdown -s -p -f �t 00");
                return found+"Sex!"+authorized;
            }


            //Recieves: HWID | USERNAME | BANNED
            if (!currentHWID.equals(new String(new BASE64Decoder().decodeBuffer(authResponse.getString("hwid"))))) {
                loginStatus = "\247e[B1] Auth error, open a ticket.";
                if(guiButton != null)
                    guiButton.enabled = true;
            } else if (connection.getResponseCode() == 200) {
                // USE THESE IF YOU WANT TO DOUBLE CHECK
                Metaware.INSTANCE.setUID(authResponse.getString("uid"));
                String sex = new String(new BASE64Decoder().decodeBuffer(authResponse.getString("username")));
                String sex1 = new String(new BASE64Decoder().decodeBuffer(authResponse.getString("banned")));
                return  (authRecievedHWID = new String(new BASE64Decoder().decodeBuffer(authResponse.getString("hwid"))));
            }
            authorized = true;
        } catch (Exception e) {
            found = true;
            if(guiButton != null)
                guiButton.enabled = true;
           e.printStackTrace(); //DEBUG ONLY!!
            loginStatus = "\2474[B2] Error contacting Authserver.";
        }
        return "Login Succesful!";
    }

    @Override
    public void drawScreen(int x2, int y2, float z2)
    {
        shader.render(0, 0, width, height);
        //   this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        // this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);

        final CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(19);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderUtil.drawRoundedRect(width / 2 - 150, 100/*height / 50*/, (width / 2 - 100) - 80/*this.width - 300*/, height / 4 + 24 + 72 + 12 + 72 + 25, 5, 0x99000000);
        RenderUtil.drawBorderedRect(width / 2 - 80, height / 4 + 30, (width / 2 - 200) - 120, 50, 1, 0xFF000000, new Color(0, 0, 0, 170).getRGB());
        this.password.drawTextBox();
        fr.drawCenteredString("MeTaWaRe Login - Login With Your Username", width / 2, 135, -1);
        fr.drawCenteredString(loginStatus, width / 2, 115, -1);

        if (this.password.getText().isEmpty())
        {
            fr.drawString("Username", width / 2 - 74.5f, 165, -7829368);
        }

        super.drawScreen(x2, y2, z2);
    }


    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if(!authRecievedHWID.equals(currentHWID)) {
            mc.shutdown();
            // NEVER use System.exit as that cant be obfuscated
        }
    }

    @Override
    protected void keyTyped(char character, int key) {
        try
        {
            super.keyTyped(character, key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if(key == Keyboard.KEY_ESCAPE){
            mc.displayGuiScreen(this);
        }

        if (character == '\t' && !password.isFocused())
        {
            this.password.setFocused(true);
        }

        if (character == '\r')
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        this.password.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button)
    {
        try
        {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.password.mouseClicked(x2, y2, button);
    }


    @Override
    public void updateScreen()
    {
        this.password.updateCursorCounter();
    }
}