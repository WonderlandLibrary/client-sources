package de.labystudio.chat;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.DrawUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TitleChatComponent extends MessageChatComponent
{
    String title;
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

    public TitleChatComponent(String sender, long sentTime, String message)
    {
        super(sender, sentTime, "<title>" + message + "</title>");
        this.title = message;
    }

    public String buildDate()
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String s = this.date.format(Long.valueOf(this.getSentTime()));
        String s1 = this.date.format(new Date());

        if (s.equals(s1))
        {
            return de.labystudio.utils.Color.c(1) + "Today " + de.labystudio.utils.Color.c(2) + "| " + de.labystudio.utils.Color.c(3) + simpledateformat.format(Long.valueOf(this.getSentTime()));
        }
        else
        {
            new GregorianCalendar();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.add(5, -1);
            String s2 = format.format(calendar.getTime());
            return s.equals(s2) ? de.labystudio.utils.Color.c(1) + "Yesterday " + de.labystudio.utils.Color.c(2) + "| " + de.labystudio.utils.Color.c(3) + simpledateformat.format(Long.valueOf(this.getSentTime())) : de.labystudio.utils.Color.c(3) + simpledateformat.format(Long.valueOf(this.getSentTime()));
        }
    }

    public void draw(int x, int y)
    {
        DrawUtils drawutils = LabyMod.getInstance().draw;
        DrawUtils.drawRect(140, y - 10, LabyMod.getInstance().draw.getWidth() - 5, y + 10, Integer.MIN_VALUE);
        LabyMod.getInstance().draw.drawCenteredString(de.labystudio.utils.Color.c(3) + this.buildDate(), (LabyMod.getInstance().draw.getWidth() - 140) / 2 + 5 + 132, y - 5);
    }

    public int getYSize()
    {
        return 20;
    }
}
