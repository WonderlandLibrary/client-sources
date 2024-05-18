package de.zockermaus.ts3;

import de.labystudio.utils.Debug;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamSpeakController implements Runnable
{
    private static TeamSpeakController instance;
    private Socket socket;
    private BufferedWriter writer;
    private List<ControlListener> listeners;
    public String serverIP;
    public int serverPort;
    TeamSpeakUser me;
    private boolean connectionEstablished;
    private boolean tested;

    public TeamSpeakController()
    {
        this.listeners = new ArrayList();
        this.serverIP = "";
        this.serverPort = 0;
        this.tested = false;
        instance = this;
        (new Thread(this, "TeamSpeak")).start();
    }

    public TeamSpeakController(ControlListener listener)
    {
        this();
        this.listeners.add(listener);
    }

    public static TeamSpeakController getInstance()
    {
        return instance;
    }

    public void addControlListener(ControlListener listener)
    {
        this.listeners.add(listener);
    }

    public void run()
    {
        try
        {
            TeamSpeak.print("Connect to TeamSpeak..");
            this.socket = new Socket("localhost", 25639);
            OutputStream outputstream = this.socket.getOutputStream();
            InputStream inputstream = this.socket.getInputStream();
            this.writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputstream), Charset.forName("utf-8")));
            new TeamSpeakController.InputStreamReaderThread(inputstream);
            new TeamSpeakController.OutputStreamWriterThread(outputstream);
            this.onEnable();
        }
        catch (UnknownHostException unknownhostexception)
        {
            TeamSpeak.print("TeamSpeak Connection Error: " + unknownhostexception.getMessage());

            if (Debug.teamspeak())
            {
                unknownhostexception.printStackTrace();
            }
        }
        catch (IOException ioexception)
        {
            TeamSpeak.print("Can\'t connect to TeamSpeak");

            if (Debug.teamspeak())
            {
                ioexception.printStackTrace();
            }
        }
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    protected void sendMessage(String message)
    {
        if (this.getSocket() != null && !this.getSocket().isClosed())
        {
            try
            {
                this.writer.write(message + "\n");
                this.writer.flush();
            }
            catch (IOException var3)
            {
                TeamSpeak.print("Can\'t send message");
                this.reset();
            }
        }
        else
        {
            TeamSpeak.print("Trying to send message via closed socket");
        }
    }

    public static void main(String[] args)
    {
        new TeamSpeakController();
    }

    protected void onMessageRecieved(String message)
    {
        if (!message.isEmpty() && Debug.teamspeak())
        {
            if (TeamSpeak.inputString.contains("-nonotify"))
            {
                if (!message.startsWith("notify"))
                {
                    TeamSpeak.print("TeamSpeak: " + message);
                }
            }
            else
            {
                TeamSpeak.print("TeamSpeak: " + message);
            }
        }

        if (message.startsWith("notify"))
        {
            this.handleNotifyMessage(message);
        }
        else if (message.startsWith("clid") && message.contains("|"))
        {
            ArrayList<Integer> arraylist1 = new ArrayList();
            String[] astring4 = message.split("[|]");

            for (String s1 : astring4)
            {
                String[] astring5 = s1.split(" ");
                TeamSpeakController.Argument[] ateamspeakcontroller$argument3 = new TeamSpeakController.Argument[astring5.length];

                for (int l = 0; l < ateamspeakcontroller$argument3.length; ++l)
                {
                    ateamspeakcontroller$argument3[l] = new TeamSpeakController.Argument(astring5[l]);
                }

                TeamSpeakUser teamspeakuser3 = null;

                if (TeamSpeakUser.contains(ateamspeakcontroller$argument3[0].getAsInt()))
                {
                    teamspeakuser3 = getInstance().getUser(ateamspeakcontroller$argument3[0].getAsInt());
                }
                else
                {
                    teamspeakuser3 = new TeamSpeakUser(ateamspeakcontroller$argument3[0].getAsInt());
                }

                if (teamspeakuser3 != null)
                {
                    teamspeakuser3.updateChannelId(ateamspeakcontroller$argument3[1].getAsInt());
                    teamspeakuser3.updateDatabaseId(ateamspeakcontroller$argument3[2].getAsInt());
                    teamspeakuser3.updateNickname(TeamSpeak.fix(ateamspeakcontroller$argument3[3].getValue()));
                    teamspeakuser3.updateTyping(ateamspeakcontroller$argument3[4].getAsBoolean());
                    teamspeakuser3.updateAway(ateamspeakcontroller$argument3[5].getAsBoolean(), TeamSpeak.fix(ateamspeakcontroller$argument3[6].getValue()));
                    teamspeakuser3.updateTalkStatus(ateamspeakcontroller$argument3[7].getAsBoolean());
                    teamspeakuser3.updateClientInput(ateamspeakcontroller$argument3[8].getAsBoolean());
                    teamspeakuser3.updateClientOutput(ateamspeakcontroller$argument3[9].getAsBoolean());
                    teamspeakuser3.updateClientInputHardware(ateamspeakcontroller$argument3[10].getAsBoolean());
                    teamspeakuser3.updateClientOutputHardware(ateamspeakcontroller$argument3[11].getAsBoolean());
                    teamspeakuser3.updateTalkPower(ateamspeakcontroller$argument3[12].getAsInt());
                    teamspeakuser3.updateTalker(ateamspeakcontroller$argument3[13].getAsBoolean());
                    teamspeakuser3.updatePrioritySpeaker(ateamspeakcontroller$argument3[14].getAsBoolean());
                    teamspeakuser3.updateRecording(ateamspeakcontroller$argument3[15].getAsBoolean());
                    teamspeakuser3.updateChannelCommander(ateamspeakcontroller$argument3[16].getAsBoolean());
                    teamspeakuser3.updateMuted(ateamspeakcontroller$argument3[17].getAsBoolean());
                    teamspeakuser3.updateUid(ateamspeakcontroller$argument3[18].getValue());
                    teamspeakuser3.updateServerGroups(ateamspeakcontroller$argument3[19].getAsIntArray());
                    teamspeakuser3.updateChannelGroupId(ateamspeakcontroller$argument3[20].getAsInt());
                    teamspeakuser3.updateIconId(ateamspeakcontroller$argument3[21].getAsInt());
                    teamspeakuser3.updateCountry(ateamspeakcontroller$argument3[22].getValue());
                    arraylist1.add(Integer.valueOf(teamspeakuser3.getClientId()));
                }
            }

            List<TeamSpeakUser> list1 = TeamSpeakUser.getUsers();
            Collections.synchronizedList(list1);
            ArrayList<TeamSpeakUser> arraylist3 = new ArrayList();

            for (TeamSpeakUser teamspeakuser1 : list1)
            {
                if (!arraylist1.contains(Integer.valueOf(teamspeakuser1.getClientId())))
                {
                    arraylist3.add(teamspeakuser1);
                }
            }

            for (TeamSpeakUser teamspeakuser2 : arraylist3)
            {
                TeamSpeakUser.unregisterUser(teamspeakuser2);
            }
        }
        else if (message.startsWith("cid") && message.contains("|"))
        {
            ArrayList<Integer> arraylist = new ArrayList();
            String[] astring3 = message.split("[|]");

            for (String s : astring3)
            {
                String[] astring1 = s.split(" ");
                TeamSpeakController.Argument[] ateamspeakcontroller$argument1 = new TeamSpeakController.Argument[astring1.length];

                for (int j = 0; j < ateamspeakcontroller$argument1.length; ++j)
                {
                    ateamspeakcontroller$argument1[j] = new TeamSpeakController.Argument(astring1[j]);
                }

                TeamSpeakChannel teamspeakchannel2 = null;

                if (TeamSpeakChannel.contains(ateamspeakcontroller$argument1[0].getAsInt()))
                {
                    teamspeakchannel2 = getInstance().getChannel(ateamspeakcontroller$argument1[0].getAsInt());
                }
                else
                {
                    teamspeakchannel2 = new TeamSpeakChannel(ateamspeakcontroller$argument1[0].getAsInt());
                }

                teamspeakchannel2.updatePID(ateamspeakcontroller$argument1[1].getAsInt());
                teamspeakchannel2.updateChannelOrder(ateamspeakcontroller$argument1[2].getAsInt());
                teamspeakchannel2.updateChannelName(TeamSpeak.fix(ateamspeakcontroller$argument1[3].getValue()));
                teamspeakchannel2.updateTopic(ateamspeakcontroller$argument1[4].getValue());
                teamspeakchannel2.updateFlagDefault(ateamspeakcontroller$argument1[5].getAsBoolean());
                teamspeakchannel2.updateIsPassword(ateamspeakcontroller$argument1[6].getAsBoolean());
                teamspeakchannel2.updatePermanent(ateamspeakcontroller$argument1[7].getAsBoolean());
                teamspeakchannel2.updateSemiPermanent(ateamspeakcontroller$argument1[8].getAsBoolean());
                teamspeakchannel2.updateChannelCodec(ateamspeakcontroller$argument1[9].getAsInt());
                teamspeakchannel2.updateChannelCodecQuality(ateamspeakcontroller$argument1[10].getAsInt());
                teamspeakchannel2.updateTalkPower(ateamspeakcontroller$argument1[11].getAsInt());
                teamspeakchannel2.updateIconID(ateamspeakcontroller$argument1[12].getAsInt());
                teamspeakchannel2.updateMaxClients(ateamspeakcontroller$argument1[13].getAsInt());
                teamspeakchannel2.updateMaxFamilyClients(ateamspeakcontroller$argument1[14].getAsInt());
                teamspeakchannel2.updateFlagAreSubscribed(ateamspeakcontroller$argument1[15].getAsBoolean());

                if (ateamspeakcontroller$argument1.length == 17)
                {
                    teamspeakchannel2.updateTotalClients(ateamspeakcontroller$argument1[16].getAsInt());
                }

                arraylist.add(Integer.valueOf(teamspeakchannel2.getChannelId()));
            }

            List<TeamSpeakChannel> list = TeamSpeakChannel.getChannels();
            Collections.synchronizedList(list);
            ArrayList<TeamSpeakChannel> arraylist2 = new ArrayList();

            for (TeamSpeakChannel teamspeakchannel : list)
            {
                if (!arraylist.contains(Integer.valueOf(teamspeakchannel.getChannelId())))
                {
                    arraylist2.add(teamspeakchannel);
                }
            }

            for (TeamSpeakChannel teamspeakchannel1 : arraylist2)
            {
                TeamSpeakChannel.deleteChannel(teamspeakchannel1);
            }
        }
        else if (message.startsWith("clid") && !message.contains("|") && message.contains("cid"))
        {
            String[] astring2 = message.split(" ");
            TeamSpeakController.Argument[] ateamspeakcontroller$argument2 = new TeamSpeakController.Argument[astring2.length];

            for (int k = 0; k < ateamspeakcontroller$argument2.length; ++k)
            {
                ateamspeakcontroller$argument2[k] = new TeamSpeakController.Argument(astring2[k]);
            }

            if (ateamspeakcontroller$argument2.length == 2)
            {
                TeamSpeakUser teamspeakuser = this.getUser(ateamspeakcontroller$argument2[0].getAsInt());

                if (teamspeakuser != null)
                {
                    teamspeakuser.updateChannelId(ateamspeakcontroller$argument2[1].getAsInt());
                    this.me = teamspeakuser;
                }
            }
        }
        else if (message.startsWith("ip=") && message.contains("port="))
        {
            String[] astring = message.split(" ");
            TeamSpeakController.Argument[] ateamspeakcontroller$argument = new TeamSpeakController.Argument[astring.length];

            for (int i = 0; i < ateamspeakcontroller$argument.length; ++i)
            {
                ateamspeakcontroller$argument[i] = new TeamSpeakController.Argument(astring[i]);
            }

            if (ateamspeakcontroller$argument.length >= 2)
            {
                this.serverIP = ateamspeakcontroller$argument[0].getValue();
                this.serverPort = ateamspeakcontroller$argument[1].getAsInt();
                TeamSpeak.print("Connected to " + this.serverIP + ":" + this.serverPort);
                TeamSpeak.setupChat();

                for (ControlListener controllistener : this.listeners)
                {
                    controllistener.onConnect();
                }
            }
        }
        else
        {
            this.handleOther(message);
        }
    }

    private void onEnable()
    {
        TeamSpeak.print("Successfully connected to TeamSpeak!");
        this.updateInformation(EnumUpdateType.ALL);
        this.sendMessage("clientnotifyregister schandlerid=1 event=any");
        String s = "Hallo lieber Decompiler, wie geht\'s dir heute? Macht das Code-Lesen denn auch Spa\u00df? Gr\u00fc\u00dfe, die Internet-Polizei";
        s.length();
    }

    private void handleOther(String message)
    {
        String[] astring = message.split(" ");
        TeamSpeakController.Argument[] ateamspeakcontroller$argument = new TeamSpeakController.Argument[astring.length - 1];

        for (int i = 0; i < ateamspeakcontroller$argument.length; ++i)
        {
            ateamspeakcontroller$argument[i] = new TeamSpeakController.Argument(astring[i + 1]);
        }

        String s1 = astring[0];

        if (s1.equalsIgnoreCase("error"))
        {
            int j = ateamspeakcontroller$argument[0].getAsInt();

            if (j != 0 && j != 1794)
            {
                String s = ateamspeakcontroller$argument[1].getValue();

                for (ControlListener controllistener : this.listeners)
                {
                    controllistener.onError(j, s);
                }
            }
        }
    }

    private void handleNotifyMessage(String message)
    {
        message = message.substring(6, message.length());
        String[] astring = message.split(" ");
        TeamSpeakController.Argument[] ateamspeakcontroller$argument = new TeamSpeakController.Argument[astring.length - 1];

        for (int i = 0; i < ateamspeakcontroller$argument.length; ++i)
        {
            ateamspeakcontroller$argument[i] = new TeamSpeakController.Argument(astring[i + 1]);
        }

        String s = astring[0];

        if (s.equalsIgnoreCase("talkstatuschange"))
        {
            TeamSpeakUser teamspeakuser = this.getUser(ateamspeakcontroller$argument[3].getAsInt());

            if (teamspeakuser != null)
            {
                teamspeakuser.updateTalkStatus(ateamspeakcontroller$argument[1].getAsBoolean());
            }
            else
            {
                this.updateInformation(EnumUpdateType.CLIENTS);
            }
        }
        else if (s.equalsIgnoreCase("cliententerview"))
        {
            if (this.getUser(ateamspeakcontroller$argument[4].getAsInt()) != null)
            {
                return;
            }

            TeamSpeakUser teamspeakuser1 = new TeamSpeakUser(ateamspeakcontroller$argument[4].getAsInt());
            teamspeakuser1.updateChannelId(ateamspeakcontroller$argument[14].getAsInt());
            teamspeakuser1.updateNickname(ateamspeakcontroller$argument[6].getValue().replace("\\s", " "));
            teamspeakuser1.updateClientInput(ateamspeakcontroller$argument[7].getAsBoolean());
            teamspeakuser1.updateClientOutput(ateamspeakcontroller$argument[8].getAsBoolean());

            for (ControlListener controllistener : this.listeners)
            {
                controllistener.onClientConnect(teamspeakuser1);
            }

            this.updateInformation(EnumUpdateType.CLIENTS);
            TeamSpeak.updateScroll(teamspeakuser1.getChannelId(), true);
        }
        else if (s.equalsIgnoreCase("clientleftview"))
        {
            if (ateamspeakcontroller$argument.length > 5 && ateamspeakcontroller$argument[5].isInt())
            {
                TeamSpeakUser teamspeakuser3 = this.getUser(ateamspeakcontroller$argument[5].getAsInt());

                if (ateamspeakcontroller$argument[3].getAsInt() == 3)
                {
                    for (ControlListener controllistener7 : this.listeners)
                    {
                        controllistener7.onClientTimout(teamspeakuser3);
                        TeamSpeakUser.unregisterUser(teamspeakuser3);
                    }
                }
                else
                {
                    for (ControlListener controllistener6 : this.listeners)
                    {
                        controllistener6.onClientDisconnected(teamspeakuser3, ateamspeakcontroller$argument[4].getValue());
                        TeamSpeakUser.unregisterUser(teamspeakuser3);
                    }
                }
            }
            else
            {
                TeamSpeakUser teamspeakuser2 = this.getUser(ateamspeakcontroller$argument[3].getAsInt());

                if (ateamspeakcontroller$argument[2].getAsInt() == 3)
                {
                    for (ControlListener controllistener5 : this.listeners)
                    {
                        controllistener5.onClientTimout(teamspeakuser2);
                        TeamSpeakUser.unregisterUser(teamspeakuser2);
                    }
                }
                else
                {
                    for (ControlListener controllistener4 : this.listeners)
                    {
                        controllistener4.onClientDisconnected(teamspeakuser2, "Disconnected");
                        TeamSpeakUser.unregisterUser(teamspeakuser2);
                    }
                }
            }

            this.updateInformation(EnumUpdateType.CLIENTS);
            TeamSpeakUser teamspeakuser4 = this.getUser(ateamspeakcontroller$argument[3].getAsInt());

            if (teamspeakuser4 != null)
            {
                TeamSpeak.updateScroll(teamspeakuser4.getChannelId(), false);
            }
        }
        else if (s.equalsIgnoreCase("clientupdated"))
        {
            TeamSpeakUser teamspeakuser5 = this.getUser(ateamspeakcontroller$argument[1].getAsInt());

            if (teamspeakuser5 == null)
            {
                return;
            }

            if (ateamspeakcontroller$argument[2].getKey().equalsIgnoreCase("client_input_muted"))
            {
                teamspeakuser5.updateClientInput(ateamspeakcontroller$argument[2].getAsBoolean());
            }
            else if (ateamspeakcontroller$argument[2].getKey().equalsIgnoreCase("client_output_muted"))
            {
                teamspeakuser5.updateClientOutput(ateamspeakcontroller$argument[2].getAsBoolean());
            }
            else if (ateamspeakcontroller$argument[2].getKey().equalsIgnoreCase("client_input_hardware"))
            {
                teamspeakuser5.updateClientInputHardware(ateamspeakcontroller$argument[2].getAsBoolean());
            }
            else if (ateamspeakcontroller$argument[2].getKey().equalsIgnoreCase("client_output_hardware"))
            {
                teamspeakuser5.updateClientOutputHardware(ateamspeakcontroller$argument[2].getAsBoolean());
            }
            else if (ateamspeakcontroller$argument[2].getKey().equalsIgnoreCase("client_away"))
            {
                if (ateamspeakcontroller$argument.length == 4)
                {
                    teamspeakuser5.updateAway(ateamspeakcontroller$argument[2].getAsBoolean(), ateamspeakcontroller$argument[3].getValue());
                }
                else
                {
                    teamspeakuser5.updateAway(ateamspeakcontroller$argument[2].getAsBoolean());
                }
            }

            this.updateInformation(EnumUpdateType.CLIENTS);
        }
        else if (!s.equalsIgnoreCase("channeldeleted") && !s.equalsIgnoreCase("channelcreated") && !s.equalsIgnoreCase("channeledited"))
        {
            if (s.equalsIgnoreCase("connectstatuschange"))
            {
                if (ateamspeakcontroller$argument[1].getValue().equalsIgnoreCase("disconnected"))
                {
                    for (ControlListener controllistener3 : this.listeners)
                    {
                        this.reset();
                        controllistener3.onDisconnect();
                    }
                }
                else if (ateamspeakcontroller$argument[1].getValue().equalsIgnoreCase("connection_established"))
                {
                    this.updateInformation(EnumUpdateType.ALL);
                }
            }
            else if (s.equalsIgnoreCase("clientpoke"))
            {
                TeamSpeakUser teamspeakuser6 = this.getUser(ateamspeakcontroller$argument[1].getAsInt());
                String s1 = TeamSpeak.fix(ateamspeakcontroller$argument[4].getValue());

                for (ControlListener controllistener1 : this.listeners)
                {
                    controllistener1.onPokeRecieved(teamspeakuser6, s1);
                }
            }
            else if (s.equalsIgnoreCase("textmessage"))
            {
                int k = ateamspeakcontroller$argument[1].getAsInt();

                if (k == 1)
                {
                    TeamSpeakUser teamspeakuser8 = this.getUser(ateamspeakcontroller$argument[3].getAsInt());
                    TeamSpeakUser teamspeakuser12 = this.getUser(ateamspeakcontroller$argument[4].getAsInt());

                    if (teamspeakuser12 == null)
                    {
                        return;
                    }

                    for (ControlListener controllistener2 : this.listeners)
                    {
                        if (teamspeakuser12.isTyping())
                        {
                            teamspeakuser12.updateTyping(false);
                        }

                        controllistener2.onMessageRecieved(teamspeakuser8, teamspeakuser12, TeamSpeak.fix(ateamspeakcontroller$argument[2].getValue()));
                    }
                }

                if (k == 2)
                {
                    TeamSpeakUser teamspeakuser9 = this.getUser(ateamspeakcontroller$argument[3].getAsInt());

                    if (teamspeakuser9 == null)
                    {
                        return;
                    }

                    for (ControlListener controllistener9 : this.listeners)
                    {
                        if (teamspeakuser9.isTyping())
                        {
                            teamspeakuser9.updateTyping(false);
                        }

                        controllistener9.onChannelMessageRecieved(teamspeakuser9, TeamSpeak.fix(ateamspeakcontroller$argument[2].getValue()));
                    }
                }

                if (k == 3)
                {
                    TeamSpeakUser teamspeakuser10 = this.getUser(ateamspeakcontroller$argument[3].getAsInt());

                    if (teamspeakuser10 == null)
                    {
                        return;
                    }

                    for (ControlListener controllistener10 : this.listeners)
                    {
                        if (teamspeakuser10.isTyping())
                        {
                            teamspeakuser10.updateTyping(false);
                        }

                        controllistener10.onServerMessageRecieved(teamspeakuser10, TeamSpeak.fix(ateamspeakcontroller$argument[2].getValue()));
                    }
                }
            }
            else if (s.equalsIgnoreCase("clientchatcomposing"))
            {
                TeamSpeakUser teamspeakuser7 = this.getUser(ateamspeakcontroller$argument[1].getAsInt());

                if (teamspeakuser7 == null)
                {
                    return;
                }

                for (ControlListener controllistener8 : this.listeners)
                {
                    controllistener8.onClientStartTyping(teamspeakuser7);
                    teamspeakuser7.updateTyping(true);
                }
            }
            else if (s.equalsIgnoreCase("clientmoved"))
            {
                int l = ateamspeakcontroller$argument[1].getAsInt();
                TeamSpeakUser teamspeakuser11 = this.getUser(ateamspeakcontroller$argument[2].getAsInt());

                if (teamspeakuser11 != null)
                {
                    TeamSpeak.updateScroll(teamspeakuser11.getChannelId(), false);
                }

                this.updateInformation(EnumUpdateType.CLIENTS);
                TeamSpeak.updateScroll(l, true);
            }
            else if (s.equalsIgnoreCase("currentserverconnectionchanged"))
            {
                this.updateInformation(EnumUpdateType.ALL);
            }
            else if (s.equalsIgnoreCase("servergrouplist"))
            {
                TeamSpeakServerGroup.getGroups().clear();
                String[] astring2 = message.replace("servergrouplist", "").replace(" schandlerid=1 ", "").replace(" schandlerid=0 ", "").split("[|]");

                for (String s2 : astring2)
                {
                    String[] astring1 = s2.split(" ");
                    ateamspeakcontroller$argument = new TeamSpeakController.Argument[astring1.length];

                    for (int j = 0; j < ateamspeakcontroller$argument.length; ++j)
                    {
                        ateamspeakcontroller$argument[j] = new TeamSpeakController.Argument(astring1[j]);
                    }

                    TeamSpeakServerGroup teamspeakservergroup = new TeamSpeakServerGroup(ateamspeakcontroller$argument[0].getAsInt());
                    teamspeakservergroup.setGroupName(ateamspeakcontroller$argument[1].getValue());
                    teamspeakservergroup.setType(ateamspeakcontroller$argument[2].getAsInt());
                    teamspeakservergroup.setIconId(ateamspeakcontroller$argument[3].getAsInt());
                    teamspeakservergroup.setSavebd(ateamspeakcontroller$argument[4].getAsInt());
                    TeamSpeakServerGroup.addGroup(teamspeakservergroup);
                }
            }
            else if (s.equalsIgnoreCase("channelgrouplist"))
            {
                TeamSpeakChannelGroup.getGroups().clear();
                String[] astring3 = message.replace("channelgrouplist", "").replace(" schandlerid=1 ", "").replace(" schandlerid=0 ", "").split("[|]");

                for (String s3 : astring3)
                {
                    String[] astring4 = s3.split(" ");
                    ateamspeakcontroller$argument = new TeamSpeakController.Argument[astring4.length];

                    for (int i1 = 0; i1 < ateamspeakcontroller$argument.length; ++i1)
                    {
                        ateamspeakcontroller$argument[i1] = new TeamSpeakController.Argument(astring4[i1]);
                    }

                    TeamSpeakChannelGroup teamspeakchannelgroup = new TeamSpeakChannelGroup(ateamspeakcontroller$argument[0].getAsInt());
                    teamspeakchannelgroup.setGroupName(ateamspeakcontroller$argument[1].getValue());
                    teamspeakchannelgroup.setType(ateamspeakcontroller$argument[2].getAsInt());
                    teamspeakchannelgroup.setIconId(ateamspeakcontroller$argument[3].getAsInt());
                    teamspeakchannelgroup.setSavebd(ateamspeakcontroller$argument[4].getAsInt());
                    teamspeakchannelgroup.setNamemode(ateamspeakcontroller$argument[5].getAsInt());
                    teamspeakchannelgroup.setNameModifyPower(ateamspeakcontroller$argument[6].getAsInt());
                    teamspeakchannelgroup.setNameMemberAddPower(ateamspeakcontroller$argument[7].getAsInt());
                    teamspeakchannelgroup.setNameMemberRemovePower(ateamspeakcontroller$argument[8].getAsInt());
                    TeamSpeakChannelGroup.addGroup(teamspeakchannelgroup);
                }
            }
            else
            {
                this.updateInformation(EnumUpdateType.ALL);
            }
        }
        else
        {
            this.updateInformation(EnumUpdateType.CHANNELS);
        }

        if (TeamSpeakChannel.getChannels().size() == 0 || TeamSpeakUser.getUsers().size() == 0)
        {
            this.updateInformation(EnumUpdateType.ALL);
        }

        if ((TeamSpeakServerGroup.getGroups().isEmpty() || TeamSpeakChannelGroup.getGroups().isEmpty()) && !s.contains("grouplist"))
        {
            this.updateInformation(EnumUpdateType.GROUPS);
        }
    }

    private void updateInformation(EnumUpdateType type)
    {
        if (type == EnumUpdateType.ALL || type == EnumUpdateType.CHANNELS)
        {
            this.sendMessage("channellist -topic -flags -voice -icon -limits");
        }

        if (type == EnumUpdateType.ALL || type == EnumUpdateType.CLIENTS)
        {
            this.sendMessage("clientlist -uid -away -voice -groups -icon -country");
        }

        if (type == EnumUpdateType.ALL || type == EnumUpdateType.ME)
        {
            this.sendMessage("whoami");
        }

        if (type == EnumUpdateType.ALL || type == EnumUpdateType.GROUPS)
        {
            this.sendMessage("servergrouplist");
            this.sendMessage("channelgrouplist");
        }

        if (this.serverIP.isEmpty() || this.serverPort == 0 || !this.isConnectionEstablished())
        {
            this.sendMessage("serverconnectinfo");
        }

        int i = 0;

        for (Chat chat : TeamSpeak.chats)
        {
            if (chat.getSlotId() < 0)
            {
                ++i;
            }
        }

        if (i != 2)
        {
            TeamSpeak.setupChat();
        }
    }

    public void tick()
    {
        this.testForConnectionEstablished();

        if (this.isConnectionEstablished() && this.serverIP.isEmpty() && this.serverPort == 0)
        {
            this.updateInformation(EnumUpdateType.ALL);
        }
    }

    public TeamSpeakUser getUser(int clientId)
    {
        List<TeamSpeakUser> list = TeamSpeakUser.getUsers();
        Collections.synchronizedList(list);

        for (TeamSpeakUser teamspeakuser : list)
        {
            if (teamspeakuser.getClientId() == clientId)
            {
                return teamspeakuser;
            }
        }

        return null;
    }

    public TeamSpeakUser getUser(String clientName)
    {
        for (TeamSpeakUser teamspeakuser : TeamSpeakUser.getUsers())
        {
            if (teamspeakuser.getNickName() == clientName)
            {
                return teamspeakuser;
            }
        }

        return null;
    }

    public TeamSpeakChannel getChannel(int channelId)
    {
        for (TeamSpeakChannel teamspeakchannel : TeamSpeakChannel.getChannels())
        {
            if (teamspeakchannel.getChannelId() == channelId)
            {
                return teamspeakchannel;
            }
        }

        return null;
    }

    public TeamSpeakServerGroup getServerGroup(int id)
    {
        List<TeamSpeakServerGroup> list = new ArrayList();
        list.addAll(TeamSpeakServerGroup.getGroups());

        for (TeamSpeakServerGroup teamspeakservergroup : list)
        {
            if (teamspeakservergroup.getSgid() == id)
            {
                return teamspeakservergroup;
            }
        }

        return null;
    }

    public TeamSpeakChannelGroup getChannelGroup(int id)
    {
        List<TeamSpeakChannelGroup> list = new ArrayList();
        list.addAll(TeamSpeakChannelGroup.getGroups());

        for (TeamSpeakChannelGroup teamspeakchannelgroup : list)
        {
            if (teamspeakchannelgroup != null && teamspeakchannelgroup.getSgid() == id)
            {
                return teamspeakchannelgroup;
            }
        }

        return null;
    }

    public void diconnectUser(TeamSpeakUser user)
    {
        TeamSpeakUser.unregisterUser(user);
    }

    public TeamSpeakUser me()
    {
        return this.me;
    }

    private void reset()
    {
        TeamSpeak.chats.clear();
        TeamSpeakUser.reset();
        TeamSpeakChannel.reset();
        this.serverIP = "";
        this.serverPort = 0;
    }

    public void connect()
    {
        this.listeners.clear();
        this.reset();
        TeamSpeak.enable();
    }

    public boolean isConnectionEstablished()
    {
        if (!this.tested)
        {
            this.tested = true;
            this.testForConnectionEstablished();
        }

        return this.connectionEstablished;
    }

    public void testForConnectionEstablished()
    {
        try
        {
            this.writer.write("whoami\n");
            this.writer.flush();
            this.connectionEstablished = true;
        }
        catch (Exception var2)
        {
            this.connectionEstablished = false;
        }
    }

    public void quit()
    {
        this.sendMessage("quit");
    }

    public static class Argument
    {
        private String key;
        private String value;

        public Argument(String input)
        {
            if (input.contains("=") && input.split("=").length >= 1)
            {
                String[] astring = input.split("=", 2);
                this.key = astring[0];
                this.value = astring[1];
            }
            else
            {
                this.key = input;
                this.value = "";
            }
        }

        public String getKey()
        {
            return this.key;
        }

        public String getValue()
        {
            return this.value;
        }

        public boolean isInt()
        {
            try
            {
                Integer.parseInt(this.value);
                return true;
            }
            catch (Exception var2)
            {
                return false;
            }
        }

        public int getAsInt()
        {
            return Integer.parseInt(this.value);
        }

        public boolean getAsBoolean()
        {
            int i = Integer.parseInt(this.value);
            return i == 1;
        }

        public ArrayList<Integer> getAsIntArray()
        {
            ArrayList<Integer> arraylist = new ArrayList();

            if (this.value.contains(","))
            {
                String[] astring = this.value.split(",");

                for (String s : astring)
                {
                    arraylist.add(Integer.valueOf(Integer.parseInt(s)));
                }
            }
            else
            {
                arraylist.add(Integer.valueOf(Integer.parseInt(this.value)));
            }

            return arraylist;
        }
    }

    private class InputStreamReaderThread extends Thread
    {
        private InputStream input;

        public InputStreamReaderThread(InputStream input)
        {
            super("InputStreamReadThread");
            this.input = input;
            this.start();
        }

        public void run()
        {
            TeamSpeakController.this.testForConnectionEstablished();
            InputStreamReader inputstreamreader = new InputStreamReader(new BufferedInputStream(this.getInput()), Charset.forName("utf-8"));
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            while (TeamSpeakController.this.isConnectionEstablished())
            {
                try
                {
                    String s = bufferedreader.readLine();

                    if (s != null)
                    {
                        TeamSpeakController.this.onMessageRecieved(s);
                    }
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                    return;
                }
            }
        }

        public InputStream getInput()
        {
            return this.input;
        }
    }

    protected class OutputStreamWriterThread extends Thread
    {
        private OutputStream output;

        public OutputStreamWriterThread(OutputStream output)
        {
            super("OutputStreamWriteThread");
            this.output = output;
            this.start();
        }

        public void run()
        {
            TeamSpeakController.this.testForConnectionEstablished();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("utf-8")));

            while (TeamSpeakController.this.isConnectionEstablished())
            {
                try
                {
                    String s = bufferedreader.readLine();

                    if (s != null)
                    {
                        if (s.equalsIgnoreCase("usage"))
                        {
                            TeamSpeak.print("Free: " + Runtime.getRuntime().freeMemory() / 1024L / 1024L);
                            TeamSpeak.print("Max: " + Runtime.getRuntime().maxMemory() / 1024L / 1024L);
                            TeamSpeak.print("Total: " + Runtime.getRuntime().totalMemory() / 1024L / 1024L);
                        }
                        else
                        {
                            TeamSpeakController.this.sendMessage(s);
                        }
                    }
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                    return;
                }
            }
        }

        public OutputStream getOutput()
        {
            return this.output;
        }
    }
}
