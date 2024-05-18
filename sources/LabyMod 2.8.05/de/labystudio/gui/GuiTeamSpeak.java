package de.labystudio.gui;

import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.language.L;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.Utils;
import de.zockermaus.ts3.Chat;
import de.zockermaus.ts3.EnumTargetMode;
import de.zockermaus.ts3.Message;
import de.zockermaus.ts3.PopUpCallback;
import de.zockermaus.ts3.TeamSpeak;
import de.zockermaus.ts3.TeamSpeakBridge;
import de.zockermaus.ts3.TeamSpeakChannel;
import de.zockermaus.ts3.TeamSpeakChannelGroup;
import de.zockermaus.ts3.TeamSpeakController;
import de.zockermaus.ts3.TeamSpeakServerGroup;
import de.zockermaus.ts3.TeamSpeakUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiTeamSpeak extends GuiMenuScreen
{
    private DrawUtils draw;
    boolean boxEnabled = false;
    boolean boxIsUser = false;
    int boxId = 0;
    int boxPosX = 0;
    int boxPosY = 0;
    int boxLengthX = 0;
    int boxLengthY = 0;
    boolean drag = false;
    boolean drop = false;
    int dragVisible = 0;
    boolean dropFocus = false;
    int dropX = 0;
    int dropY = 0;
    boolean dragIsUser = false;
    int dragId = 0;
    boolean changeNickName = false;
    ModGuiTextField nickNameField;
    ModGuiTextField chatInputField;
    long lastClick = 0L;
    boolean allowChatScroll;
    boolean allowChannelScroll;
    int yMouse = 0;
    int doubleClickDelay = 500;
    boolean doubleClickIsUser;
    int doubleClickTarget = 0;
    GuiButton connectButton;
    int connect = 0;
    boolean init = false;
    int clickX;
    int clickY;
    boolean moveX;
    boolean moveY;

    public GuiTeamSpeak()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.draw = LabyMod.getInstance().draw;
        this.id = "TeamSpeak";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        TeamSpeak.setDefaultScreen();
        this.changeNickName = false;
        this.drag = false;
        this.closeBox();
        this.connect = 0;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.chatInputField = new ModGuiTextField(0, LabyMod.getInstance().mc.fontRendererObj, 5, this.draw.getHeight() - 17, this.draw.getWidth() - 10, 12);
        this.chatInputField.setText(TeamSpeak.inputString);
        this.chatInputField.setMaxStringLength(200);
        int i = TeamSpeak.xSplit - 26;
        this.nickNameField = new ModGuiTextField(0, LabyMod.getInstance().mc.fontRendererObj, 0, 0, i, 9);
        this.nickNameField.setMaxStringLength(20);
        this.connectButton = new GuiButton(1, this.width / 2 - 100, this.height / 2, L.f("gui_ts_connect", new Object[0]));

        if (TeamSpeakController.getInstance() != null)
        {
            this.connectButton.visible = !TeamSpeakController.getInstance().isConnectionEstablished();
        }

        this.buttonList.add(this.connectButton);

        while (TeamSpeak.xSplit > LabyMod.getInstance().draw.getWidth() / 4 * 3)
        {
            --TeamSpeak.xSplit;
        }

        while (TeamSpeak.xSplit < 200)
        {
            ++TeamSpeak.xSplit;
        }

        while (TeamSpeak.ySplit > LabyMod.getInstance().draw.getHeight() - 50)
        {
            --TeamSpeak.ySplit;
        }

        while (TeamSpeak.ySplit < 50)
        {
            ++TeamSpeak.ySplit;
        }

        this.boxEnabled = false;
        super.initGui();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 1:
                this.connect = 1;

            case 0:
            default:
                super.actionPermformed(button);
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            LabyMod.getInstance().back();
        }

        if (TeamSpeak.overlayWindows != null)
        {
            TeamSpeak.overlayWindows.KeyTyped(typedChar, keyCode);

            if (!TeamSpeak.overlayWindows.allow())
            {
                return;
            }
        }

        if (keyCode == 28)
        {
            if (this.changeNickName && this.nickNameField != null && this.nickNameField.isFocused())
            {
                this.changeNickname();
            }
            else if (!TeamSpeak.inputString.isEmpty() && this.chatInputField != null && this.chatInputField.isFocused())
            {
                TeamSpeakBridge.sendTextMessage(TeamSpeak.selectedChat, TeamSpeak.inputString);
                TeamSpeak.inputString = "";
                this.chatInputField.setText("");
            }
        }

        if (this.chatInputField.textboxKeyTyped(typedChar, keyCode))
        {
            TeamSpeak.inputString = this.chatInputField.getText();
        }

        if (this.changeNickName)
        {
            this.nickNameField.textboxKeyTyped(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (this.boxAction(mouseX, mouseY, mouseButton))
        {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        else
        {
            this.closeBox();

            if (TeamSpeak.overlayWindows != null)
            {
                TeamSpeak.overlayWindows.mouseClicked(mouseX, mouseY, mouseButton);

                if (TeamSpeak.overlayWindows.isInScreen(mouseX, mouseY))
                {
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                    return;
                }
            }

            if (this.changeNickName)
            {
                this.changeNickname();
            }

            this.chatInputField.mouseClicked(mouseX, mouseY, mouseButton);

            if (this.changeNickName)
            {
                this.nickNameField.mouseClicked(mouseX, mouseY, mouseButton);
            }

            this.clickX = mouseX - TeamSpeak.xSplit;
            this.clickY = mouseY - TeamSpeak.ySplit;

            if (mouseX > TeamSpeak.xSplit - 5 && mouseX < TeamSpeak.xSplit + 5)
            {
                this.moveX = true;
            }

            if (mouseY > TeamSpeak.ySplit - 5 && mouseY < TeamSpeak.ySplit + 5)
            {
                this.moveY = true;
            }

            if (!this.moveX && !this.moveY)
            {
                this.switchChat(mouseX, mouseY, mouseButton);
                this.channelAction(mouseX, mouseY, mouseButton);
                this.menuAction(mouseX, mouseY, mouseButton);
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (this.boxEnabled)
            {
                return;
            }

            if (this.changeNickName)
            {
                return;
            }

            if (this.yMouse > TeamSpeak.ySplit)
            {
                if (i > 0)
                {
                    if (this.allowChatScroll)
                    {
                        TeamSpeak.scrollChat -= 10;
                    }
                }
                else if (TeamSpeak.scrollChat < 0)
                {
                    TeamSpeak.scrollChat += 10;
                }
            }
            else if (i > 0)
            {
                if (TeamSpeak.scrollChannel < 0)
                {
                    TeamSpeak.scrollChannel += 20;
                }
            }
            else if (this.allowChannelScroll)
            {
                TeamSpeak.scrollChannel -= 20;
            }
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.callBackListener(mouseX, mouseY);
        this.connectButton.visible = !TeamSpeakController.getInstance().isConnectionEstablished();

        if (this.connectButton != null)
        {
            if (this.connect == 2)
            {
                this.connect = 0;
                TeamSpeakController.getInstance().connect();
            }

            if (this.connect == 1)
            {
                ++this.connect;
            }
        }

        if (!TeamSpeakController.getInstance().isConnectionEstablished())
        {
            String s = TeamSpeakController.getInstance().serverIP + ":" + TeamSpeakController.getInstance().serverPort;

            if (!TeamSpeakController.getInstance().serverIP.isEmpty() && TeamSpeakController.getInstance().serverPort != 0)
            {
                this.draw.drawCenteredString(Color.cl("a") + L.f("gui_ts_connectto", new Object[] {s}), this.width / 2, this.height / 2 - 15);
            }
            else
            {
                this.draw.drawCenteredString(Color.cl("c") + L.f("gui_ts_error_connect", new Object[0]), this.width / 2, this.height / 2 - 15);

                if (this.connectButton != null)
                {
                    this.connectButton.visible = true;

                    if (!this.connectButton.enabled)
                    {
                        this.connectButton.displayString = L.f("gui_ts_connecting", new Object[0]);
                    }
                    else
                    {
                        this.connectButton.displayString = L.f("gui_ts_connect", new Object[0]);
                    }
                }
            }

            if (this.init)
            {
                this.init = false;
                this.initGui();
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else if (!TeamSpeakController.getInstance().serverIP.isEmpty() && TeamSpeakController.getInstance().serverPort != 0 && TeamSpeakUser.amount() != 0 && TeamSpeakChannel.amount() != 0)
        {
            if (!this.init)
            {
                this.init = true;
                this.initGui();
            }

            this.chatInputField.drawTextBox();
            this.drawChat();
            this.drawChannel(mouseX, mouseY);
            this.drawClientInfo();
            this.drawMenu(mouseX, mouseY);

            if (this.changeNickName)
            {
                this.nickNameField.drawTextBox();
            }

            this.yMouse = mouseY;

            if (TeamSpeak.selectedUser != -1 && TeamSpeakController.getInstance().getUser(TeamSpeak.selectedUser) == null)
            {
                TeamSpeak.selectedUser = -1;
            }

            if (TeamSpeak.selectedChannel != -1 && TeamSpeakController.getInstance().getChannel(TeamSpeak.selectedChannel) == null)
            {
                TeamSpeak.selectedChannel = -1;
            }

            if (TeamSpeak.selectedChannel == -1 && TeamSpeak.selectedUser == -1 && TeamSpeakController.getInstance().me() != null)
            {
                TeamSpeak.selectedUser = TeamSpeakController.getInstance().me().getClientId();
            }

            if (TeamSpeak.overlayWindows != null)
            {
                TeamSpeak.overlayWindows.drawWindow(mouseX, mouseY);

                if (TeamSpeak.overlayWindows.isInScreen(mouseX, mouseY))
                {
                    super.drawScreen(mouseX, mouseY, partialTicks);
                    this.drawBox(mouseX, mouseY);
                    return;
                }
            }

            this.drawBox(mouseX, mouseY);

            if (mouseX > TeamSpeak.xSplit - 5 && mouseX < TeamSpeak.xSplit + 5 && mouseY > TeamSpeak.ySplit - 5 && mouseY < TeamSpeak.ySplit + 5)
            {
                LabyMod.getInstance().draw.drawCenteredString(Color.cl("7") + "+", mouseX + 1, mouseY - 2);
            }
            else
            {
                if (mouseX > TeamSpeak.xSplit - 5 && mouseX < TeamSpeak.xSplit + 5 && mouseY < TeamSpeak.ySplit)
                {
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("7") + "...", mouseX + 1, mouseY - 6);
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("7") + "...", mouseX + 1, mouseY - 3);
                }

                if (mouseY > TeamSpeak.ySplit - 5 && mouseY < TeamSpeak.ySplit + 5)
                {
                    LabyMod.getInstance().draw.drawCenteredString(Color.cl("7") + "||", mouseX + 1, mouseY - 3);
                }
            }

            if (this.drag)
            {
                this.drawDrag(mouseX, mouseY);
            }

            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else
        {
            if (this.connectButton != null)
            {
                this.connectButton.visible = false;
            }

            this.draw.drawCenteredString(Color.cl("c") + L.f("gui_ts_noserverfound", new Object[0]), this.width / 2, this.height / 2 - 15);
            this.draw.drawCenteredString(Color.cl("7") + L.f("gui_ts_tryrestart", new Object[0]), this.width / 2, this.height / 2 - 5);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        if (TeamSpeak.overlayWindows != null)
        {
            TeamSpeak.overlayWindows.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

            if (TeamSpeak.overlayWindows.isInScreen(mouseX, mouseY))
            {
                super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
                return;
            }
        }

        if (this.moveX && mouseX - this.clickX < LabyMod.getInstance().draw.getWidth() / 4 * 3 && mouseX - this.clickX > 200)
        {
            TeamSpeak.xSplit = mouseX - this.clickX;
        }

        if (this.moveY && mouseY - this.clickY < LabyMod.getInstance().draw.getHeight() - 50 && mouseY - this.clickY > 50)
        {
            TeamSpeak.ySplit = mouseY - this.clickY;
        }

        if (this.drag)
        {
            ++this.dragVisible;
        }

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (TeamSpeak.overlayWindows != null)
        {
            TeamSpeak.overlayWindows.mouseReleased(mouseX, mouseY, state);

            if (TeamSpeak.overlayWindows.isInScreen(mouseX, mouseY))
            {
                super.mouseReleased(mouseX, mouseY, state);
                return;
            }
        }

        if (this.moveX || this.moveY)
        {
            this.initGui();
        }

        this.moveX = false;
        this.moveY = false;

        if (this.drag)
        {
            this.setDrop(mouseX, mouseY);
        }

        super.mouseReleased(mouseX, mouseY, state);
    }

    private void drawChat()
    {
        DrawUtils drawutils = this.draw;
        DrawUtils.drawRect(5, TeamSpeak.ySplit, this.draw.getWidth() - 5, this.draw.getHeight() - 20, Integer.MIN_VALUE);
        Chat chat = null;
        int i = 0;

        for (Chat chat1 : TeamSpeak.chats)
        {
            drawutils = this.draw;
            DrawUtils.drawRect(5 + i, this.draw.getHeight() - 33, 50 + i, this.draw.getHeight() - 20, 835640000);
            String s = "";

            if (TeamSpeak.selectedChat == chat1.getSlotId())
            {
                drawutils = this.draw;
                DrawUtils.drawRect(5 + i + 1, this.draw.getHeight() - 33 + 1, 50 + i - 1, this.draw.getHeight() - 20 - 1, Integer.MAX_VALUE);
                chat = chat1;
            }

            String s1 = Utils.normalizeString(chat1.getTargetMode().name());

            if (chat1.getTargetMode() == EnumTargetMode.USER)
            {
                s1 = chat1.getChatOwner().getNickName();

                if (s1.length() > 7)
                {
                    s1 = s1.substring(0, 7);
                }
            }

            this.draw.drawCenteredString(s + s1, 27 + i, this.draw.getHeight() - 30);
            i += 46;
        }

        if (chat != null)
        {
            int j = 0;
            List<Message> list = new ArrayList();
            list.addAll(chat.getLog());
            Collections.reverse(list);

            for (Message message : list)
            {
                if (LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat >= TeamSpeak.ySplit && LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat <= LabyMod.getInstance().draw.getHeight() - 45)
                {
                    if (message.getUser() == null)
                    {
                        LabyMod.getInstance().draw.drawString(message.getMessage(), 8.0D, (double)(LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat));
                    }
                    else
                    {
                        LabyMod.getInstance().draw.drawString(Color.cl("9") + message.getUser().getNickName() + Color.cl("7") + ": " + message.getMessage(), 8.0D, (double)(LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat));
                    }
                }

                j += 10;
            }

            this.allowChatScroll = LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat < TeamSpeak.ySplit - 10;
        }
    }

    public void switchChat(int mouseX, int mouseY, int mouseButton)
    {
        int i = 0;
        Chat chat = null;

        for (Chat chat1 : TeamSpeak.chats)
        {
            if (mouseX > 5 + i && mouseX < 50 + i && mouseY > this.draw.getHeight() - 33 && mouseY < this.draw.getHeight() - 20)
            {
                TeamSpeak.scrollChat = 0;

                if (mouseButton == 0)
                {
                    TeamSpeak.selectedChat = chat1.getSlotId();
                }

                if (mouseButton == 1 && chat1.getSlotId() >= 0)
                {
                    TeamSpeak.selectedChat = -1;
                    chat = chat1;
                }
            }

            i += 46;
        }

        if (chat != null)
        {
            TeamSpeak.chats.remove(chat);
        }

        Chat chat3 = null;

        for (Chat chat2 : TeamSpeak.chats)
        {
            if (TeamSpeak.selectedChat == chat2.getSlotId())
            {
                chat3 = chat2;
            }
        }

        if (chat3 != null)
        {
            int j = 0;
            List<Message> list = new ArrayList();
            list.addAll(chat3.getLog());
            Collections.reverse(list);

            for (Message message : list)
            {
                if (LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat >= TeamSpeak.ySplit && LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat <= LabyMod.getInstance().draw.getHeight() - 45 && message.getUser() != null)
                {
                    if (mouseX > 8 && mouseX < LabyMod.getInstance().draw.getStringWidth(message.getUser().getNickName()) + 8 && mouseY > LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat && mouseY < LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat + 10)
                    {
                        this.openBox(true, message.getUser().getClientId(), mouseX, mouseY);
                    }

                    if (mouseX > 8 && mouseX > LabyMod.getInstance().draw.getStringWidth(message.getUser().getNickName()) + 8 && mouseY > LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat && mouseY < LabyMod.getInstance().draw.getHeight() - 45 - j - TeamSpeak.scrollChat + 10)
                    {
                        ArrayList<String> arraylist = Utils.extractDomains(message.getMessage());

                        if (!arraylist.isEmpty())
                        {
                            LabyMod.getInstance().openWebpage((String)arraylist.get(0), true);
                        }
                    }
                }

                j += 10;
            }
        }
    }

    private void drawChannel(int mouseX, int mouseY)
    {
        DrawUtils drawutils = this.draw;
        DrawUtils.drawRect(5, TeamSpeak.ySplit - 1, TeamSpeak.xSplit, 25, Integer.MIN_VALUE);
        ArrayList<TeamSpeakChannel> arraylist = new ArrayList();
        int i = 0;

        for (TeamSpeakChannel teamspeakchannel : TeamSpeakBridge.getChannels())
        {
            if (teamspeakchannel.getChannelOrder() == 0)
            {
                arraylist.add(teamspeakchannel);
            }
            else
            {
                TeamSpeakChannel teamspeakchannel1 = TeamSpeak.getFromOrder(i);

                if (teamspeakchannel1 == null)
                {
                    break;
                }

                i = teamspeakchannel1.getChannelId();
                arraylist.add(teamspeakchannel1);
            }
        }

        boolean flag = false;
        int j = 0;
        int k = 0;
        TeamSpeak.outOfView.clear();

        for (TeamSpeakChannel teamspeakchannel2 : arraylist)
        {
            if (mouseX > 5 && mouseX < TeamSpeak.xSplit && mouseY > 30 + k + TeamSpeak.scrollChannel && mouseY < 30 + k + TeamSpeak.scrollChannel + 11 && 30 + k + TeamSpeak.scrollChannel + 10 < TeamSpeak.ySplit && 30 + k + TeamSpeak.scrollChannel > 20)
            {
                flag = true;
                j = teamspeakchannel2.getChannelId();

                if (this.drag)
                {
                    drawRect(5, 30 + k + TeamSpeak.scrollChannel - 1, TeamSpeak.xSplit, 30 + k + TeamSpeak.scrollChannel + 10, 1230000000);
                }
            }

            if (30 + k + TeamSpeak.scrollChannel < 20)
            {
                TeamSpeak.outOfView.add(Integer.valueOf(teamspeakchannel2.getChannelId()));
            }

            if (30 + k + TeamSpeak.scrollChannel + 10 < TeamSpeak.ySplit && 30 + k + TeamSpeak.scrollChannel > 20)
            {
                String s = Color.cl("7");

                if (TeamSpeak.selectedChannel == teamspeakchannel2.getChannelId())
                {
                    drawRect(5, 30 + k + TeamSpeak.scrollChannel - 1, TeamSpeak.xSplit, 30 + k + TeamSpeak.scrollChannel + 10, 1230000000);
                }

                if (TeamSpeak.isSpacer(teamspeakchannel2.getChannelName()))
                {
                    LabyMod.getInstance().draw.drawCenteredString(s + TeamSpeak.toStarSpacer(teamspeakchannel2.getChannelName(), TeamSpeak.xSplit), TeamSpeak.xSplit / 2, 30 + k + TeamSpeak.scrollChannel);
                }
                else if (TeamSpeak.isStarSpacer(teamspeakchannel2.getChannelName()))
                {
                    LabyMod.getInstance().draw.drawCenteredString(s + TeamSpeak.toStarSpacer(teamspeakchannel2.getChannelName(), TeamSpeak.xSplit), TeamSpeak.xSplit / 2, 30 + k + TeamSpeak.scrollChannel);
                }
                else if (TeamSpeak.isCenterSpacer(teamspeakchannel2.getChannelName()))
                {
                    LabyMod.getInstance().draw.drawCenteredString(s + TeamSpeak.toCenterSpacer(teamspeakchannel2.getChannelName()), TeamSpeak.xSplit / 2, 30 + k + TeamSpeak.scrollChannel);
                }
                else
                {
                    String s1 = Color.cl("2");

                    if (teamspeakchannel2.getIsPassword())
                    {
                        s1 = Color.cl("e");
                    }

                    if (!teamspeakchannel2.getSubscription())
                    {
                        s1 = Color.cl("b");
                    }

                    if (teamspeakchannel2.getTotalClients() >= teamspeakchannel2.getMaxClients() && teamspeakchannel2.getMaxClients() != -1)
                    {
                        s1 = Color.cl("4");
                    }

                    LabyMod.getInstance().draw.drawString(s1 + "\u2b1b" + s + teamspeakchannel2.getChannelName(), 5.0D, (double)(30 + k + TeamSpeak.scrollChannel));
                }
            }

            ArrayList<TeamSpeakUser> arraylist2 = new ArrayList();
            arraylist2.addAll(TeamSpeakBridge.getUsers());
            Collections.sort(arraylist2, new Comparator<TeamSpeakUser>()
            {
                public int compare(TeamSpeakUser o1, TeamSpeakUser o2)
                {
                    return o1 != null && o2 != null ? (o1.getTalkPower() < o2.getTalkPower() ? 1 : (o1.getTalkPower() > o2.getTalkPower() ? -1 : 0)) : 0;
                }
            });

            for (TeamSpeakUser teamspeakuser : arraylist2)
            {
                if (teamspeakuser != null && teamspeakuser.getChannelId() == teamspeakchannel2.getChannelId())
                {
                    k += 10;

                    if (30 + k + TeamSpeak.scrollChannel + 10 < TeamSpeak.ySplit && 30 + k + TeamSpeak.scrollChannel > 20)
                    {
                        String s2 = "";

                        if (TeamSpeak.selectedUser == teamspeakuser.getClientId())
                        {
                            drawRect(5, 30 + k + TeamSpeak.scrollChannel - 1, TeamSpeak.xSplit, 30 + k + TeamSpeak.scrollChannel + 10, 1230000000);
                        }

                        if (TeamSpeakController.getInstance().me() != null && teamspeakuser.getClientId() == TeamSpeakController.getInstance().me().getClientId())
                        {
                            s2 = s2 + Color.cl("l");
                            this.nickNameField.xPosition = 25;
                            this.nickNameField.yPosition = 30 + k + TeamSpeak.scrollChannel;
                        }

                        String s3 = "";

                        if (TeamSpeak.teamSpeakGroupPrefix)
                        {
                            ArrayList<TeamSpeakServerGroup> arraylist1 = new ArrayList();
                            arraylist1.addAll(TeamSpeakServerGroup.getGroups());

                            for (TeamSpeakServerGroup teamspeakservergroup : arraylist1)
                            {
                                if (teamspeakservergroup != null && teamspeakuser.getServerGroups().contains(Integer.valueOf(teamspeakservergroup.getSgid())) && teamspeakservergroup.getType() == 1 && teamspeakservergroup.getIconId() != 0)
                                {
                                    s3 = s3 + Color.cl("b") + "[" + TeamSpeak.fix(teamspeakservergroup.getGroupName()) + "] ";
                                }
                            }
                        }

                        LabyMod.getInstance().draw.drawString(TeamSpeak.getTalkColor(teamspeakuser) + "  \u2b24 " + Color.cl("f") + s3 + Color.cl("f") + s2 + teamspeakuser.getNickName() + TeamSpeak.getAway(teamspeakuser), 5.0D, (double)(30 + k + TeamSpeak.scrollChannel));
                    }
                }
            }

            k += 10;
        }

        this.allowChannelScroll = 30 + k + TeamSpeak.scrollChannel > TeamSpeak.ySplit - 10;

        if (30 + k + TeamSpeak.scrollChannel < TeamSpeak.ySplit - 30)
        {
            TeamSpeak.scrollChannel += 10;
        }

        if (TeamSpeak.scrollChannel > 20)
        {
            TeamSpeak.scrollChannel -= 10;
        }

        this.drop(j, flag);
    }

    private void channelAction(int mouseX, int mouseY, int mouseButton)
    {
        ArrayList<TeamSpeakChannel> arraylist = new ArrayList();
        int i = 0;

        for (TeamSpeakChannel teamspeakchannel : TeamSpeakBridge.getChannels())
        {
            TeamSpeakChannel teamspeakchannel1 = TeamSpeak.getFromOrder(i);

            if (teamspeakchannel.getChannelOrder() == 0)
            {
                arraylist.add(teamspeakchannel);
            }
            else
            {
                if (teamspeakchannel1 == null)
                {
                    break;
                }

                i = teamspeakchannel1.getChannelId();
                arraylist.add(teamspeakchannel1);
            }
        }

        int j = 0;

        for (TeamSpeakChannel teamspeakchannel2 : arraylist)
        {
            if (30 + j + TeamSpeak.scrollChannel + 5 < TeamSpeak.ySplit && 30 + j + TeamSpeak.scrollChannel > 20 && mouseX > 5 && mouseX < TeamSpeak.xSplit && mouseY > 30 + j + TeamSpeak.scrollChannel && mouseY < 30 + j + TeamSpeak.scrollChannel + 10)
            {
                TeamSpeak.selectedChannel = teamspeakchannel2.getChannelId();
                TeamSpeak.selectedUser = -1;

                if (mouseButton == 1)
                {
                    this.openBox(false, teamspeakchannel2.getChannelId(), mouseX, mouseY);
                    return;
                }

                if (this.lastClick + (long)this.doubleClickDelay > System.currentTimeMillis() && this.doubleClickTarget == teamspeakchannel2.getChannelId() && !this.doubleClickIsUser && TeamSpeakController.getInstance().me() != null && TeamSpeakController.getInstance().me().getChannelId() != teamspeakchannel2.getChannelId())
                {
                    if (teamspeakchannel2.getIsPassword())
                    {
                        TeamSpeak.overlayWindows.openInput(teamspeakchannel2.getChannelId(), L.f("gui_ts_channelpassword_title", new Object[0]), L.f("gui_ts_channelpassword_content", new Object[] {teamspeakchannel2.getChannelName()}), new PopUpCallback()
                        {
                            public void ok(int cid, String message)
                            {
                                TeamSpeakBridge.moveClient(TeamSpeakController.getInstance().me().getClientId(), cid);
                            }
                            public void ok()
                            {
                            }
                            public void cancel()
                            {
                            }
                            public boolean tick(int cid)
                            {
                                return TeamSpeakController.getInstance().me() != null && TeamSpeakController.getInstance().me().getChannelId() == cid;
                            }
                        });
                    }

                    TeamSpeakBridge.moveClient(TeamSpeakController.getInstance().me().getClientId(), teamspeakchannel2.getChannelId());
                }

                this.doubleClickTarget = teamspeakchannel2.getChannelId();
                this.doubleClickIsUser = false;
                this.lastClick = System.currentTimeMillis();
            }

            ArrayList<TeamSpeakUser> arraylist1 = new ArrayList();
            arraylist1.addAll(TeamSpeakBridge.getUsers());
            Collections.sort(arraylist1, new Comparator<TeamSpeakUser>()
            {
                public int compare(TeamSpeakUser o1, TeamSpeakUser o2)
                {
                    return o1 != null && o2 != null ? (o1.getTalkPower() < o2.getTalkPower() ? 1 : (o1.getTalkPower() > o2.getTalkPower() ? -1 : 0)) : 0;
                }
            });

            for (TeamSpeakUser teamspeakuser : arraylist1)
            {
                if (teamspeakuser.getChannelId() == teamspeakchannel2.getChannelId())
                {
                    j += 10;

                    if (30 + j + TeamSpeak.scrollChannel + 5 < TeamSpeak.ySplit && 30 + j + TeamSpeak.scrollChannel > 20 && mouseX > 5 && mouseX < TeamSpeak.xSplit && mouseY > 30 + j + TeamSpeak.scrollChannel && mouseY < 30 + j + TeamSpeak.scrollChannel + 10)
                    {
                        TeamSpeak.selectedUser = teamspeakuser.getClientId();
                        TeamSpeak.selectedChannel = -1;

                        if (mouseButton == 1)
                        {
                            this.openBox(true, teamspeakuser.getClientId(), mouseX, mouseY);
                            return;
                        }

                        this.drag(true, teamspeakuser.getClientId());

                        if (this.lastClick + (long)this.doubleClickDelay > System.currentTimeMillis() && this.doubleClickTarget == teamspeakuser.getClientId() && this.doubleClickIsUser)
                        {
                            if (TeamSpeakController.getInstance().me() != null && teamspeakuser.getClientId() != TeamSpeakController.getInstance().me().getClientId())
                            {
                                TeamSpeak.addChat(teamspeakuser, TeamSpeakController.getInstance().me(), (String)null, EnumTargetMode.USER);
                                TeamSpeak.selectedChat = teamspeakuser.getClientId();
                            }
                            else
                            {
                                this.openNickNameBox();
                            }
                        }

                        this.doubleClickTarget = teamspeakuser.getClientId();
                        this.doubleClickIsUser = true;
                        this.lastClick = System.currentTimeMillis();
                    }
                }
            }

            j += 10;
        }
    }

    private void drawClientInfo()
    {
        if (TeamSpeak.selectedUser != -1)
        {
            TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(TeamSpeak.selectedUser);

            if (teamspeakuser == null)
            {
                return;
            }

            int i = 30;
            int j = TeamSpeak.xSplit + 10;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_user_nickname", new Object[0]) + Color.cl("f") + ":", j, i);
            i = i + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_user_country", new Object[0]) + Color.cl("f") + ":", j, i);
            i = i + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_user_talkpower", new Object[0]) + Color.cl("f") + ":", j, i);
            i = i + 12;
            i = 30;
            this.drawInfo(teamspeakuser.getNickName(), j + 70, i);
            i = i + 12;
            this.drawInfo("" + TeamSpeak.country(teamspeakuser.getCountry()), j + 70, i);
            i = i + 12;
            this.drawInfo("" + teamspeakuser.getTalkPower(), j + 70, i);
            i = i + 12;
            i = i + 10;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_user_servergroups", new Object[0]) + Color.cl("f") + ":", j, i);
            i = i + 12;

            if (teamspeakuser.getServerGroups() == null)
            {
                return;
            }

            Iterator group = teamspeakuser.getServerGroups().iterator();

            while (group.hasNext())
            {
                int k = ((Integer)group.next()).intValue();
                TeamSpeakServerGroup teamspeakservergroup = TeamSpeakController.getInstance().getServerGroup(k);

                if (teamspeakservergroup != null)
                {
                    this.drawInfo(TeamSpeak.fix(teamspeakservergroup.getGroupName()), j, i);
                    i += 12;
                }
                else
                {
                    this.drawInfo("" + k, j, i);
                    i += 12;
                }
            }

            i = i + 10;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_user_channelgroups", new Object[0]) + Color.cl("f") + ":", j, i);
            i = i + 12;
            TeamSpeakChannelGroup teamspeakchannelgroup = TeamSpeakController.getInstance().getChannelGroup(teamspeakuser.getChannelGroupId());

            if (teamspeakchannelgroup != null)
            {
                this.drawInfo(TeamSpeak.fix(teamspeakchannelgroup.getGroupName()), j, i);
                i = i + 12;
            }
            else
            {
                this.drawInfo("" + teamspeakuser.getChannelGroupId(), j, i);
                i = i + 12;
            }

            i = i + 10;

            if (!teamspeakuser.hasClientInputHardware())
            {
                this.drawInfo(Color.cl("c") + L.f("gui_ts_user_micoff", new Object[0]), j, i);
                i += 12;
            }

            if (teamspeakuser.hasClientInputMuted())
            {
                this.drawInfo(Color.cl("c") + L.f("gui_ts_user_micmute", new Object[0]), j, i);
                i += 12;
            }

            if (!teamspeakuser.hasClientOutputHardware())
            {
                this.drawInfo(Color.cl("4") + L.f("gui_ts_user_soundoff", new Object[0]), j, i);
                i += 12;
            }

            if (teamspeakuser.hasClientOutputMuted())
            {
                this.drawInfo(Color.cl("4") + L.f("gui_ts_user_soundmute", new Object[0]), j, i);
                i = i + 12;
            }
        }
        else if (TeamSpeak.selectedChannel != -1)
        {
            TeamSpeakChannel teamspeakchannel = TeamSpeakController.getInstance().getChannel(TeamSpeak.selectedChannel);

            if (teamspeakchannel == null)
            {
                return;
            }

            int l = 30;
            int i1 = TeamSpeak.xSplit + 10;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_name", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;

            if (teamspeakchannel != null && teamspeakchannel.getTopic() != null && !teamspeakchannel.getTopic().isEmpty())
            {
                this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_topic", new Object[0]) + Color.cl("f") + ":", i1, l);
                l += 12;
            }

            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_codec", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_codecquality", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_type", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_currentclients", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;
            this.drawInfo(Color.cl("7") + L.f("gui_ts_channel_subscriptionsatus", new Object[0]) + Color.cl("f") + ":", i1, l);
            l = l + 12;
            l = 30;
            i1 = i1 + 110;
            this.drawInfo(teamspeakchannel.getChannelName(), i1, l);
            l = l + 12;

            if (!teamspeakchannel.getTopic().isEmpty())
            {
                this.drawInfo("" + teamspeakchannel.getTopic(), i1, l);
                l += 12;
            }

            this.drawInfo("" + teamspeakchannel.getChannelCodecName(), i1, l);
            l = l + 12;
            this.drawInfo("" + teamspeakchannel.getChannelCodecQuality(), i1, l);
            l = l + 12;
            this.drawInfo("" + TeamSpeak.status(teamspeakchannel.getIsPermanent(), L.f("gui_ts_channel_permanent", new Object[0]) + " ", "") + TeamSpeak.status(teamspeakchannel.getIsSemiPermanent(), L.f("gui_ts_channel_semipermanent", new Object[0]) + " ", "") + TeamSpeak.status(teamspeakchannel.getIsPassword(), L.f("gui_ts_channel_password", new Object[0]), ""), i1, l);
            l = l + 12;
            this.drawInfo("" + teamspeakchannel.getTotalClients() + "/" + (teamspeakchannel.getMaxClients() + "").replace("-1", "Unlimited"), i1, l);
            l = l + 12;
            this.drawInfo("" + TeamSpeak.status(teamspeakchannel.getSubscription(), L.f("gui_ts_channel_subscribed", new Object[0]), L.f("gui_ts_channel_notsubscribed", new Object[0])), i1, l);
            l = l + 12;
        }
    }

    public void drawInfo(String text, int x, int y)
    {
        if (y < TeamSpeak.ySplit - 10)
        {
            LabyMod.getInstance().draw.drawString(text, (double)x, (double)y);
        }
    }

    public void drawBox(int mouseX, int mouseY)
    {
        if (this.boxEnabled)
        {
            int i = this.boxPosX;
            int j = this.boxPosY;
            int k = this.boxPosX + this.boxLengthX;
            int l = this.boxPosY + this.boxLengthY;
            boolean flag = this.boxLengthY == 0;

            if (this.boxIsUser)
            {
                if (this.boxId == TeamSpeakController.getInstance().me().getClientId())
                {
                    int i1 = 0;
                    LabyMod.getInstance().draw.drawBox(i, j, k, l);
                    this.boxSlot(L.f("gui_ts_action_changenickname", new Object[0]), i, j, k, l, i1, mouseX, mouseY);
                    i1 = i1 + 15;
                    this.boxSlot(L.f("gui_ts_action_channelcommander", new Object[0]), i, j, k, l, i1, mouseX, mouseY);
                    i1 = i1 + 15;
                    this.boxLengthX = 110;
                    this.boxLengthY = i1;
                }
                else
                {
                    int j1 = 0;
                    LabyMod.getInstance().draw.drawBox(i, j, k, l);
                    this.boxSlot(L.f("gui_ts_action_opentextchat", new Object[0]), i, j, k, l, j1, mouseX, mouseY);
                    j1 = j1 + 15;
                    this.boxSlot(L.f("gui_ts_action_poke", new Object[0]), i, j, k, l, j1, mouseX, mouseY);
                    j1 = j1 + 15;
                    this.boxSlot(L.f("gui_ts_action_movetoown", new Object[0]), i, j, k, l, j1, mouseX, mouseY);
                    j1 = j1 + 15;
                    this.boxLengthX = 145;
                    this.boxLengthY = j1;
                }
            }
            else
            {
                int k1 = 0;
                LabyMod.getInstance().draw.drawBox(i, j, k, l);
                this.boxSlot(L.f("gui_ts_action_switchchannel", new Object[0]), i, j, k, l, k1, mouseX, mouseY);
                k1 = k1 + 15;
                this.boxLengthX = 100;
                this.boxLengthY = k1;
            }

            if (flag)
            {
                int l1 = i + this.boxLengthX;
                int i2 = j + this.boxLengthY;
                LabyMod.getInstance().draw.drawBox(i, j, l1, i2);
            }
        }
    }

    public void boxSlot(String text, int x, int y, int lengthX, int lengthY, int slot, int mouseX, int mouseY)
    {
        String s = Color.cl("7");

        if (mouseX > x && mouseX < x + lengthX && mouseY > y + slot && mouseY < y + slot + 15)
        {
            s = Color.cl("f");
        }

        LabyMod.getInstance().draw.drawString(s + text, (double)(x + 5), (double)(y + 4 + slot));
    }

    public void boxSplit(int x, int y, int lengthX, int lengthY, int slot, int mouseX, int mouseY)
    {
        DrawUtils drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(x + 5, y + slot + 3, lengthX - 5, y + slot + 4, 1423232232);
    }

    private boolean boxClick(int x, int y, int lengthX, int lengthY, int slot, int mouseX, int mouseY)
    {
        return mouseX > x && mouseX < x + lengthX && mouseY > y + slot && mouseY < y + slot + 15;
    }

    private boolean boxAction(int mouseX, int mouseY, int mouseButton)
    {
        if (!this.boxEnabled)
        {
            return false;
        }
        else if (mouseX > this.boxPosX && mouseX < this.boxPosX + this.boxLengthX && mouseY > this.boxPosY && mouseY < this.boxPosY + this.boxLengthY)
        {
            if (mouseButton != 0)
            {
                return true;
            }
            else
            {
                int i = this.boxPosX;
                int j = this.boxPosY;
                int k = this.boxPosX + this.boxLengthX;
                int l = this.boxPosY + this.boxLengthY;

                if (this.boxIsUser)
                {
                    if (this.boxId == TeamSpeakController.getInstance().me().getClientId())
                    {
                        int i1 = 0;

                        if (this.boxClick(i, j, k, l, i1, mouseX, mouseY))
                        {
                            this.openNickNameBox();
                        }

                        i1 = i1 + 15;

                        if (this.boxClick(i, j, k, l, i1, mouseX, mouseY))
                        {
                            TeamSpeakBridge.setChannelCommander(!TeamSpeakController.getInstance().me().isChannelCommander());
                        }

                        i1 = i1 + 15;
                    }
                    else
                    {
                        int j1 = 0;

                        if (this.boxClick(i, j, k, l, j1, mouseX, mouseY))
                        {
                            TeamSpeak.addChat(TeamSpeakController.getInstance().getUser(this.boxId), TeamSpeakController.getInstance().me(), (String)null, EnumTargetMode.USER);
                            TeamSpeak.selectedChat = this.boxId;
                        }

                        j1 = j1 + 15;

                        if (this.boxClick(i, j, k, l, j1, mouseX, mouseY))
                        {
                            TeamSpeak.overlayWindows.openInput(TeamSpeak.selectedUser, L.f("gui_ts_window_poke_title", new Object[0]), L.f("gui_ts_window_poke_content", new Object[0]), new PopUpCallback()
                            {
                                public void ok(int id, String message)
                                {
                                    TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(id);

                                    if (teamspeakuser == null)
                                    {
                                        TeamSpeak.error(L.f("gui_ts_window_poke_error_offline", new Object[0]));
                                    }
                                    else
                                    {
                                        TeamSpeakBridge.pokeClient(teamspeakuser, message);
                                    }
                                }
                                public void ok()
                                {
                                }
                                public void cancel()
                                {
                                }
                                public boolean tick(int cid)
                                {
                                    return false;
                                }
                            });
                        }

                        j1 = j1 + 15;

                        if (this.boxClick(i, j, k, l, j1, mouseX, mouseY))
                        {
                            TeamSpeakBridge.moveClient(this.boxId, TeamSpeakController.getInstance().me().getChannelId());
                        }

                        j1 = j1 + 15;
                    }
                }
                else
                {
                    int k1 = 0;

                    if (this.boxClick(i, j, k, l, k1, mouseX, mouseY))
                    {
                        if (TeamSpeakController.getInstance().me() != null)
                        {
                            TeamSpeakBridge.moveClient(TeamSpeakController.getInstance().me().getClientId(), this.boxId);
                        }

                        this.closeBox();
                    }

                    k1 = k1 + 15;
                }

                this.closeBox();
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    private void openBox(boolean isUser, int id, int x, int y)
    {
        this.boxEnabled = true;
        this.boxIsUser = isUser;
        this.boxId = id;
        this.boxPosX = x;
        this.boxPosY = y;

        if (isUser)
        {
            TeamSpeak.selectedChannel = -1;
            TeamSpeak.selectedUser = id;
        }
        else
        {
            TeamSpeak.selectedUser = -1;
            TeamSpeak.selectedChannel = id;
        }
    }

    private void closeBox()
    {
        this.boxEnabled = false;
        this.boxIsUser = true;
        this.boxId = 0;
        this.boxPosX = 0;
        this.boxPosY = 0;
        this.boxLengthX = 0;
        this.boxLengthY = 0;
    }

    private void changeNickname()
    {
        this.changeNickName = false;

        if (!this.nickNameField.getText().equals(TeamSpeakController.getInstance().me().getNickName()))
        {
            TeamSpeakBridge.setNickname(this.nickNameField.getText());
        }
    }

    private void openNickNameBox()
    {
        this.changeNickName = true;
        this.nickNameField.setFocused(true);
        this.nickNameField.setText(TeamSpeakController.getInstance().me().getNickName());
    }

    private void drag(boolean isUser, int Id)
    {
        this.resetDrag();
        this.drag = true;
        this.dragIsUser = isUser;
        this.dragId = Id;
    }

    private void drop(int channelId, boolean isInRegion)
    {
        if (this.drag)
        {
            this.dropFocus = isInRegion;

            if (this.drop)
            {
                if (this.dragIsUser && this.dropFocus)
                {
                    TeamSpeakBridge.moveClient(this.dragId, channelId);
                }

                this.resetDrag();
            }
        }
    }

    private void resetDrag()
    {
        this.drag = false;
        this.drop = false;
        this.dragIsUser = false;
        this.dragId = 0;
        this.dropX = 0;
        this.dropY = 0;
        this.dragVisible = 0;
        this.dropFocus = false;
    }

    public void setDrop(int x, int y)
    {
        this.dropX = x;
        this.dropY = y;
        this.drop = true;
    }

    public void drawDrag(int mouseX, int mouseY)
    {
        if (this.drag)
        {
            if (this.dragVisible >= 5)
            {
                String s = "";
                String s1 = "";

                if (this.dragIsUser)
                {
                    TeamSpeakUser teamspeakuser = TeamSpeakController.getInstance().getUser(this.dragId);

                    if (teamspeakuser == null)
                    {
                        return;
                    }

                    s1 = teamspeakuser.getNickName();
                }
                else
                {
                    TeamSpeakChannel teamspeakchannel = TeamSpeakController.getInstance().getChannel(this.dragId);

                    if (teamspeakchannel == null)
                    {
                        return;
                    }

                    s1 = teamspeakchannel.getChannelName();
                }

                if (!this.dropFocus)
                {
                    s = Color.cl("c");
                }

                LabyMod.getInstance().draw.drawString(s + s1, (double)mouseX, (double)mouseY);
            }
        }
    }

    public void drawMenu(int mouseX, int mouseY)
    {
        if (TeamSpeakController.getInstance().me() != null)
        {
            int i = 0;
            String s = TeamSpeakController.getInstance().serverIP + ":" + TeamSpeakController.getInstance().serverPort;
            i = i + (LabyMod.getInstance().draw.getWidth() - 30 - LabyMod.getInstance().draw.getStringWidth(s));
            DrawUtils drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(i, 5, LabyMod.getInstance().draw.getWidth() - 5, 20, Integer.MIN_VALUE);
            LabyMod.getInstance().draw.drawRightString(Color.cl("a") + s, (double)(LabyMod.getInstance().draw.getWidth() - 20), 9.0D);
            i = i - 4;
            int j = Integer.MIN_VALUE;

            if (TeamSpeakController.getInstance().me().hasClientOutputMuted())
            {
                j = 2122022291;
            }

            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(i, 5, i - 16, 20, j);
            LabyMod.getInstance().draw.drawString(Color.cl("f") + "", 0.0D, 0.0D);
            this.mc.getTextureManager().bindTexture(LabyMod.getInstance().texture_teamSpeak);
            LabyMod.getInstance().draw.drawTexturedModalRect(i - 16 + 3, 7, 12, 0, 12, 12);
            i = i - 20;
            j = Integer.MIN_VALUE;

            if (TeamSpeakController.getInstance().me().hasClientInputMuted())
            {
                j = 2122022291;
            }

            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(i, 5, i - 16, 20, j);
            LabyMod.getInstance().draw.drawString(Color.cl("f") + "", 0.0D, 0.0D);
            this.mc.getTextureManager().bindTexture(LabyMod.getInstance().texture_teamSpeak);
            LabyMod.getInstance().draw.drawTexturedModalRect(i - 16 + 3, 7, 0, 0, 12, 12);
            i = i - 20;
            j = Integer.MIN_VALUE;

            if (TeamSpeak.teamSpeakGroupPrefix)
            {
                j = 2122022291;
            }

            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(i, 5, i - 16, 20, j);
            LabyMod.getInstance().draw.drawCenteredString(Color.cl("b") + "[]", i - 8, 9);
        }
    }

    public void menuAction(int mouseX, int mouseY, int mouseButton)
    {
        int i = 0;
        String s = TeamSpeakController.getInstance().serverIP + ":" + TeamSpeakController.getInstance().serverPort;
        i = i + (LabyMod.getInstance().draw.getWidth() - 30 - LabyMod.getInstance().draw.getStringWidth(s));
        i = i - 4;

        if (mouseX > i - 16 && mouseX < i && mouseY > 5 && mouseY < 20)
        {
            TeamSpeakBridge.setOutputMuted(!TeamSpeakController.getInstance().me().hasClientOutputMuted());
        }

        i = i - 20;

        if (mouseX > i - 16 && mouseX < i && mouseY > 5 && mouseY < 20)
        {
            TeamSpeakBridge.setInputMuted(!TeamSpeakController.getInstance().me().hasClientInputMuted());
        }

        i = i - 20;

        if (mouseX > i - 16 && mouseX < i && mouseY > 5 && mouseY < 20)
        {
            TeamSpeak.teamSpeakGroupPrefix = !TeamSpeak.teamSpeakGroupPrefix;
        }
    }

    public void callBackListener(int mouseX, int mouseY)
    {
        if (TeamSpeak.callBack)
        {
            TeamSpeak.callBack = false;
            this.openBox(true, TeamSpeak.callBackClient, mouseX, mouseY);
        }
    }
}
