package de.zockermaus.ts3;

import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.DrawUtils;
import java.awt.Color;
import java.util.ArrayList;

public class TeamSpeakOverlayWindow
{
    private ArrayList<TeamSpeakOverlayWindow.InfoMessage> info = new ArrayList();
    String infoTitle;
    boolean infoDrag;
    int infoPosX;
    int infoPosY;
    int infoLengthX;
    int infoLengthY;
    int infoClickX;
    int infoClickY;
    String inputTitle;
    boolean inputDrag;
    int inputPosX;
    int inputPosY;
    int inputLengthX;
    int inputLengthY;
    int inputClickX;
    int inputClickY;
    int inputTarget;
    String input;
    PopUpCallback inputCallBack;
    ModGuiTextField inputField;
    boolean closedInfo = false;
    boolean closedInput = false;

    public void openInfo(int clientId, String title, String message)
    {
        if (message.length() > 70)
        {
            message = message.substring(0, 70) + "..";
        }

        this.info.add(new TeamSpeakOverlayWindow.InfoMessage(clientId, message));
        this.infoTitle = title;

        if (this.info.size() > 15)
        {
            this.info.remove(0);
        }

        this.calcInfo();
    }

    public void openInput(int targetId, String title, String message, PopUpCallback callBack)
    {
        this.inputTitle = title;
        this.input = message;
        this.inputTarget = targetId;
        this.inputCallBack = callBack;
        this.inputField = new ModGuiTextField(0, LabyMod.getInstance().mc.fontRendererObj, 0, 0, 159, 10);
        this.inputField.setText("");
        this.inputField.setMaxStringLength(50);
        this.inputField.setFocused(true);
        this.calcInput();
    }

    private void resetInfo()
    {
        this.info.clear();
        this.infoTitle = null;
        this.infoDrag = false;
        this.infoPosX = 0;
        this.infoPosY = 0;
        this.infoLengthX = 0;
        this.infoLengthY = 0;
        this.infoClickX = 0;
        this.infoClickY = 0;
        this.closedInfo = true;
    }

    private void resetinput()
    {
        this.input = null;
        this.inputTitle = null;
        this.inputDrag = false;
        this.inputPosX = 0;
        this.inputPosY = 0;
        this.inputLengthX = 0;
        this.inputLengthY = 0;
        this.inputClickX = 0;
        this.inputClickY = 0;
        this.inputTarget = 0;
        this.closedInput = true;
        this.inputField = null;
    }

    public void drawWindow(int mouseX, int mouseY)
    {
        if (!this.info.isEmpty())
        {
            this.drawInfo(mouseX, mouseY);
        }

        if (this.input != null)
        {
            this.drawInput(mouseX, mouseY);
        }
    }

    public boolean isInInfoScreen(int mouseX, int mouseY)
    {
        return this.closedInfo ? true : mouseX > this.infoPosX - 1 && mouseX < this.infoPosX + this.infoLengthX + 1 && mouseY > this.infoPosY - 1 && mouseY < this.infoPosY + this.infoLengthY + 1;
    }

    public boolean isInInfoFrame(int mouseX, int mouseY)
    {
        return mouseX > this.infoPosX - 1 && mouseX < this.infoPosX + this.infoLengthX + 1 && mouseY > this.infoPosY - 1 && mouseY < this.infoPosY + 15;
    }

    public boolean isInInputScreen(int mouseX, int mouseY)
    {
        return this.closedInput ? true : mouseX > this.inputPosX - 1 && mouseX < this.inputPosX + this.inputLengthX + 1 && mouseY > this.inputPosY - 1 && mouseY < this.inputPosY + this.inputLengthY + 1;
    }

    public boolean isInInputFrame(int mouseX, int mouseY)
    {
        return mouseX > this.inputPosX - 1 && mouseX < this.inputPosX + this.inputLengthX + 1 && mouseY > this.inputPosY - 1 && mouseY < this.inputPosY + 15;
    }

    public boolean isInScreen(int mouseX, int mouseY)
    {
        return this.isInInfoScreen(mouseX, mouseY) || this.isInInputScreen(mouseX, mouseY);
    }

    public boolean isInFrame(int mouseX, int mouseY)
    {
        return this.isInInputScreen(mouseX, mouseY) || this.isInInputScreen(mouseX, mouseY);
    }

    private void calcInfo()
    {
        int i = LabyMod.getInstance().draw.getStringWidth(this.infoTitle);

        for (TeamSpeakOverlayWindow.InfoMessage teamspeakoverlaywindow$infomessage : this.info)
        {
            int j = LabyMod.getInstance().draw.getStringWidth(teamspeakoverlaywindow$infomessage.message);

            if (j > i)
            {
                i = j;
            }
        }

        this.infoLengthX = 20 + i;
        this.infoLengthY = 40 + this.info.size() * 10;

        if (this.info.size() == 1)
        {
            this.posInfo();
        }
    }

    private void calcInput()
    {
        int i = LabyMod.getInstance().draw.getStringWidth(this.inputTitle);
        int j = LabyMod.getInstance().draw.getStringWidth(this.input);

        if (j > i)
        {
            i = j;
        }

        int k = 165;

        if (i > 165)
        {
            k = i + 10;
        }

        this.inputLengthX = k;
        this.inputLengthY = 62;
        this.inputPosX = LabyMod.getInstance().draw.getWidth() / 2 - this.inputLengthX / 2;
        this.inputPosY = LabyMod.getInstance().draw.getHeight() / 2 - this.inputLengthY / 2;
    }

    private void posInfo()
    {
        this.infoPosX = LabyMod.getInstance().draw.getWidth() / 2 - this.infoLengthX / 2;
        this.infoPosY = LabyMod.getInstance().draw.getHeight() / 2 - this.infoLengthY / 2;
    }

    private void drawInfo(int mouseX, int mouseY)
    {
        DrawUtils drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(this.infoPosX - 1, this.infoPosY - 1, this.infoPosX + this.infoLengthX + 1, this.infoPosY + this.infoLengthY + 1, Color.BLACK.getRGB());
        drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(this.infoPosX, this.infoPosY, this.infoPosX + this.infoLengthX, this.infoPosY + this.infoLengthY, Color.GRAY.getRGB());
        drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(this.infoPosX, this.infoPosY, this.infoPosX + this.infoLengthX, this.infoPosY + 15, Color.WHITE.getRGB());
        LabyMod.getInstance().draw.drawString(de.labystudio.utils.Color.cl("6") + this.infoTitle, (double)(this.infoPosX + 3), (double)(this.infoPosY + 3));
        String s = de.labystudio.utils.Color.cl("c");

        if (mouseX > this.infoPosX + this.infoLengthX - 20 && mouseX < this.infoPosX + this.infoLengthX && mouseY > this.infoPosY + 3 && mouseY < this.infoPosY + 15)
        {
            s = de.labystudio.utils.Color.cl("4");
        }

        LabyMod.getInstance().draw.drawString(s + "x", (double)(this.infoPosX + this.infoLengthX - 13), (double)(this.infoPosY + 3));
        int i = 0;

        for (TeamSpeakOverlayWindow.InfoMessage teamspeakoverlaywindow$infomessage : this.info)
        {
            double d0 = (double)(this.infoPosX + 3);
            LabyMod.getInstance().draw.drawString(teamspeakoverlaywindow$infomessage.message, d0, (double)(this.infoPosY + 20 + i));
            i += 10;
        }

        drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(this.infoPosX + this.infoLengthX / 2 - 25, this.infoPosY + this.infoLengthY - 15, this.infoPosX + this.infoLengthX / 2 + 25, this.infoPosY + this.infoLengthY - 2, Color.BLACK.getRGB());
        drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(this.infoPosX + this.infoLengthX / 2 - 24, this.infoPosY + this.infoLengthY - 14, this.infoPosX + this.infoLengthX / 2 + 24, this.infoPosY + this.infoLengthY - 3, Color.DARK_GRAY.getRGB());

        if (mouseX > this.infoPosX + this.infoLengthX / 2 - 24 && mouseX < this.infoPosX + this.infoLengthX / 2 + 24 && mouseY > this.infoPosY + this.infoLengthY - 14 && mouseY < this.infoPosY + this.infoLengthY - 3)
        {
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.infoPosX + this.infoLengthX / 2 - 24, this.infoPosY + this.infoLengthY - 14, this.infoPosX + this.infoLengthX / 2 + 24, this.infoPosY + this.infoLengthY - 3, Color.GRAY.getRGB() + 40);
        }

        int j = this.infoPosX + this.infoLengthX / 2;
        int k = this.infoPosY + this.infoLengthY;
        LabyMod.getInstance().draw.drawCenteredString("OK", j, k - 12);

        while (this.infoPosX + this.infoLengthX > LabyMod.getInstance().draw.getWidth())
        {
            --this.infoPosX;
        }
    }

    private void drawInput(int mouseX, int mouseY)
    {
        if (this.inputCallBack.tick(this.inputTarget))
        {
            this.resetinput();
        }
        else
        {
            DrawUtils drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX - 1, this.inputPosY - 1, this.inputPosX + this.inputLengthX + 1, this.inputPosY + this.inputLengthY + 1, Color.BLACK.getRGB());
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX, this.inputPosY, this.inputPosX + this.inputLengthX, this.inputPosY + this.inputLengthY, Color.GRAY.getRGB());
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX, this.inputPosY, this.inputPosX + this.inputLengthX, this.inputPosY + 15, Color.WHITE.getRGB());
            LabyMod.getInstance().draw.drawString(de.labystudio.utils.Color.cl("6") + this.inputTitle, (double)(this.inputPosX + 3), (double)(this.inputPosY + 3));
            String s = de.labystudio.utils.Color.cl("c");

            if (mouseX > this.inputPosX + this.inputLengthX - 20 && mouseX < this.inputPosX + this.inputLengthX && mouseY > this.inputPosY + 3 && mouseY < this.inputPosY + 15)
            {
                s = de.labystudio.utils.Color.cl("4");
            }

            LabyMod.getInstance().draw.drawString(s + "x", (double)(this.inputPosX + this.inputLengthX - 13), (double)(this.inputPosY + 3));
            double d0 = (double)(this.inputPosX + 3);
            LabyMod.getInstance().draw.drawString(this.input, d0, (double)(this.inputPosY + 20));
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 - 60, this.inputPosY + this.inputLengthY - 15, this.inputPosX + this.inputLengthX / 2 - 10, this.inputPosY + this.inputLengthY - 2, Color.BLACK.getRGB());
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 - 60 + 1, this.inputPosY + this.inputLengthY - 14, this.inputPosX + this.inputLengthX / 2 - 11, this.inputPosY + this.inputLengthY - 3, Color.DARK_GRAY.getRGB());

            if (mouseX > this.inputPosX + this.inputLengthX / 2 - 60 + 1 && mouseX < this.inputPosX + this.inputLengthX / 2 + 11 && mouseY > this.inputPosY + this.inputLengthY - 14 && mouseY < this.inputPosY + this.inputLengthY - 3)
            {
                drawutils = LabyMod.getInstance().draw;
                DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 - 60 + 1, this.inputPosY + this.inputLengthY - 14, this.inputPosX + this.inputLengthX / 2 - 11, this.inputPosY + this.inputLengthY - 3, Color.GRAY.getRGB() + 40);
            }

            int i = this.inputPosX + this.inputLengthX / 2 - 35;
            int j = this.inputPosY + this.inputLengthY;
            LabyMod.getInstance().draw.drawCenteredString("OK", i, j - 12);
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 + 10, this.inputPosY + this.inputLengthY - 15, this.inputPosX + this.inputLengthX / 2 + 60, this.inputPosY + this.inputLengthY - 2, Color.BLACK.getRGB());
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 + 11, this.inputPosY + this.inputLengthY - 14, this.inputPosX + this.inputLengthX / 2 + 60 - 1, this.inputPosY + this.inputLengthY - 3, Color.DARK_GRAY.getRGB());

            if (mouseX > this.inputPosX + this.inputLengthX / 2 + 10 && mouseX < this.inputPosX + this.inputLengthX / 2 + 60 && mouseY > this.inputPosY + this.inputLengthY - 14 && mouseY < this.inputPosY + this.inputLengthY - 3)
            {
                drawutils = LabyMod.getInstance().draw;
                DrawUtils.drawRect(this.inputPosX + this.inputLengthX / 2 + 11, this.inputPosY + this.inputLengthY - 14, this.inputPosX + this.inputLengthX / 2 + 60 - 1, this.inputPosY + this.inputLengthY - 3, Color.GRAY.getRGB() + 40);
            }

            i = this.inputPosX + this.inputLengthX / 2 + 35;
            j = this.inputPosY + this.inputLengthY;
            LabyMod.getInstance().draw.drawCenteredString("Cancel", i, j - 12);
            this.inputField.xPosition = this.inputPosX + 3;
            this.inputField.yPosition = this.inputPosY + 32;
            this.inputField.xPosition = this.inputPosX + this.inputLengthX / 2 - 80;
            this.inputField.drawTextBox();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.isOpen())
        {
            if (this.isInInfoFrame(mouseX, mouseY))
            {
                this.infoClickX = mouseX - this.infoPosX;
                this.infoClickY = mouseY - this.infoPosY;
                this.infoDrag = true;
            }

            if (this.isInInputFrame(mouseX, mouseY))
            {
                this.inputClickX = mouseX - this.inputPosX;
                this.inputClickY = mouseY - this.inputPosY;
                this.inputDrag = true;
            }

            if (!this.info.isEmpty())
            {
                if (mouseX > this.infoPosX + this.infoLengthX / 2 - 24 && mouseX < this.infoPosX + this.infoLengthX / 2 + 24 && mouseY > this.infoPosY + this.infoLengthY - 14 && mouseY < this.infoPosY + this.infoLengthY - 3)
                {
                    this.resetInfo();
                }

                if (mouseX > this.infoPosX + this.infoLengthX - 20 && mouseX < this.infoPosX + this.infoLengthX && mouseY > this.infoPosY + 3 && mouseY < this.infoPosY + 15)
                {
                    this.resetInfo();
                }

                int i = 0;

                for (TeamSpeakOverlayWindow.InfoMessage teamspeakoverlaywindow$infomessage : this.info)
                {
                    double d0 = (double)(this.infoPosX + 3);
                    LabyMod.getInstance().draw.drawString(teamspeakoverlaywindow$infomessage.message, d0, (double)(this.infoPosY + 20 + i));
                    int j = this.infoLengthX;
                    TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(teamspeakoverlaywindow$infomessage.clientId);

                    if (teamspeakuser != null)
                    {
                        j = LabyMod.getInstance().draw.getStringWidth(teamspeakuser.getNickName());
                    }

                    if (mouseX > this.infoPosX && mouseX < this.infoPosX + j && mouseY > this.infoPosY + 20 + i && mouseY < this.infoPosY + 20 + i + 10)
                    {
                        TeamSpeak.callBack = true;
                        TeamSpeak.callBackClient = teamspeakoverlaywindow$infomessage.clientId;
                        break;
                    }

                    i += 10;
                }
            }

            if (this.input != null)
            {
                if (mouseX > this.inputPosX + this.inputLengthX / 2 - 60 + 1 && mouseX < this.inputPosX + this.inputLengthX / 2 + 11 && mouseY > this.inputPosY + this.inputLengthY - 14 && mouseY < this.inputPosY + this.inputLengthY - 3)
                {
                    this.inputCallBack.ok(this.inputTarget, this.inputField.getText());
                    this.resetinput();
                }

                if (mouseX > this.inputPosX + this.inputLengthX / 2 + 10 && mouseX < this.inputPosX + this.inputLengthX / 2 + 60 && mouseY > this.inputPosY + this.inputLengthY - 14 && mouseY < this.inputPosY + this.inputLengthY - 3)
                {
                    this.inputCallBack.cancel();
                    this.resetinput();
                }

                if (mouseX > this.inputPosX + this.inputLengthX - 20 && mouseX < this.inputPosX + this.inputLengthX && mouseY > this.inputPosY + 3 && mouseY < this.inputPosY + 15)
                {
                    this.inputCallBack.cancel();
                    this.resetinput();
                }
            }

            if (this.inputField != null)
            {
                this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void KeyTyped(char typedChar, int keyCode)
    {
        if (this.isOpen())
        {
            if (keyCode == 28 && this.inputField != null && this.inputField.isFocused() && this.inputCallBack != null && this.inputField != null)
            {
                this.inputCallBack.ok(this.inputTarget, this.inputField.getText());
                this.resetinput();
            }

            if (this.inputField != null)
            {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if (this.isOpen())
        {
            if (this.infoDrag && !this.inputDrag)
            {
                if (mouseX - this.infoClickX > 0 && mouseX - this.infoClickX < LabyMod.getInstance().draw.getWidth() - this.infoLengthX)
                {
                    this.infoPosX = mouseX - this.infoClickX;
                }

                if (mouseY - this.infoClickY > 0 && mouseY - this.infoClickY < LabyMod.getInstance().draw.getHeight() - this.infoLengthY)
                {
                    this.infoPosY = mouseY - this.infoClickY;
                }
            }

            if (this.inputDrag)
            {
                if (mouseX - this.inputClickX > 0 && mouseX - this.inputClickX < LabyMod.getInstance().draw.getWidth() - this.inputLengthX)
                {
                    this.inputPosX = mouseX - this.inputClickX;
                }

                if (mouseY - this.inputClickY > 0 && mouseY - this.inputClickY < LabyMod.getInstance().draw.getHeight() - this.inputLengthY)
                {
                    this.inputPosY = mouseY - this.inputClickY;
                }
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.infoDrag = false;
        this.inputDrag = false;
        this.closedInfo = false;
        this.closedInput = false;
    }

    public boolean allow()
    {
        return !this.closedInfo && !this.closedInput;
    }

    public boolean isOpen()
    {
        return !this.info.isEmpty() || this.input != null;
    }

    class InfoMessage
    {
        String message;
        int clientId;

        public InfoMessage(int clientId, String message)
        {
            this.clientId = clientId;
            this.message = message;
        }
    }
}
