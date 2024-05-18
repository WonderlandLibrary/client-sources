package de.labystudio.chat;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessageChatComponent
{
    public static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    protected String message;
    private String sender;
    private long sentTime;
    private int range = 0;
    int max;
    private SingleChat chat;
    protected boolean downloaded = true;
    long id = 0L;
    int gx;
    int gy;

    public MessageChatComponent(String sender, long sentTime, String message)
    {
        this.message = message;
        this.sender = sender;
        this.sentTime = sentTime;
        this.id = LabyMod.random.nextLong();

        if (this.id == 0L)
        {
            ++this.id;
        }
    }

    public void setChat(SingleChat chat)
    {
        this.chat = chat;
    }

    public SingleChat getChat()
    {
        return this.chat;
    }

    public boolean isDownloaded()
    {
        return this.downloaded;
    }

    public long getId()
    {
        return this.id;
    }

    public void draw(int x, int y)
    {
        this.gx = x;
        this.gy = y;
        this.max = LabyMod.getInstance().draw.getWidth() - 150;
        String s = de.labystudio.utils.Color.cl("7");

        if (this.getSender().equals(LabyMod.getInstance().getPlayerName()))
        {
            s = de.labystudio.utils.Color.cl("f");
        }

        String s1 = de.labystudio.utils.Color.cl("6") + "[" + format.format(new Date(this.getSentTime())) + "] " + s + this.getSender() + ": " + de.labystudio.utils.Color.cl("f") + this.getMessage().replace("\u00b4", "\'");
        this.range = this.getRange(s1);
        y = y - this.range * 12;
        String s2 = this.getFirstStrings(this.max, s1);
        String s3 = "";

        for (int i = 0; i <= this.range; ++i)
        {
            LabyMod.getInstance().draw.drawString(s2, (double)x, (double)(y + i * 12));
            s3 = s3 + s2;
            s2 = this.getFirstStrings(this.max, s1.replace(s3, ""));
        }
    }

    private String getFirstStrings(int pixels, String string)
    {
        int i = 0;
        String s = "";

        for (int j = 0; j < string.length(); ++j)
        {
            char c0 = string.charAt(j);
            i += LabyMod.getInstance().draw.getStringWidth(new String(new char[] {c0}));

            if (pixels <= i)
            {
                if (pixels == i)
                {
                    s = s + c0;
                }

                break;
            }

            s = s + new String(new char[] {c0});
        }

        return s;
    }

    private int getRange(String msg)
    {
        int i = 0;
        int j = 0;
        String s = msg;

        for (int k = 0; k <= s.length() - 1; ++k)
        {
            char c0 = s.charAt(k);

            if (i > this.max)
            {
                ++j;
                i = 0;
            }

            i += LabyMod.getInstance().draw.getStringWidth("" + c0);
        }

        return j;
    }

    public int getYSize()
    {
        return (this.range + 1) * 12;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getSender()
    {
        return this.sender;
    }

    public long getSentTime()
    {
        return this.sentTime;
    }

    public void click(int mouseX, int mouseY, int mouseButton)
    {
        this.range = this.getRange(this.message);

        if (mouseX > this.gx && mouseX < this.gx + LabyMod.getInstance().draw.getStringWidth(this.message) + 100 && mouseY < this.gy + 12 && mouseY > this.gy - this.getYSize() + 12)
        {
            ArrayList<String> arraylist = Utils.extractDomains(this.message);

            if (!arraylist.isEmpty())
            {
                LabyMod.getInstance().openWebpage((String)arraylist.get(0), true);
            }
        }
    }

    public void drawOpen()
    {
    }

    public void handleMouseInput()
    {
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }

    public void mouseRelease(int mouseX, int mouseY, int state)
    {
    }
}
