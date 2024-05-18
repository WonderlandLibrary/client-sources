package de.labystudio.labymod;

import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Timings
{
    public static HashMap<String, Timings.Debug> result = new HashMap();
    public static ArrayList<Timings.Debug> cache = new ArrayList();
    static HashMap<String, Long> open = new HashMap();
    static HashMap<String, Long> lost = new HashMap();
    static long lastUpdate = 0L;

    public static void start(String title)
    {
        if (isTiming())
        {
            open.put(title, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stop(String title)
    {
        if (isTiming())
        {
            if (open.containsKey(title))
            {
                Long olong = (Long)open.get(title);
                open.remove(title);

                if (System.currentTimeMillis() - olong.longValue() != 0L)
                {
                    result.put(title, new Timings.Debug(title, (int)(System.currentTimeMillis() - olong.longValue()), System.currentTimeMillis()));
                }
            }
        }
    }

    public static void draw()
    {
        start("Draw Timings");

        if (isTiming())
        {
            if (lastUpdate + 1000L < System.currentTimeMillis())
            {
                try
                {
                    lastUpdate = System.currentTimeMillis();
                    ArrayList<String> arraylist = new ArrayList();

                    for (String s : result.keySet())
                    {
                        Timings.Debug timings$debug = (Timings.Debug)result.get(s);

                        if (timings$debug.created + 1000L < System.currentTimeMillis())
                        {
                            arraylist.add(s);
                        }
                    }

                    for (String s2 : arraylist)
                    {
                        result.remove(s2);
                    }

                    cache = new ArrayList(result.values());
                    Collections.sort(cache, new Comparator<Timings.Debug>()
                    {
                        public int compare(Timings.Debug o1, Timings.Debug o2)
                        {
                            return o2.time - o1.time;
                        }
                    });
                    lost.clear();
                    lost.putAll(open);
                }
                catch (Exception var6)
                {
                    ;
                }
            }

            DrawUtils drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(LabyMod.getInstance().draw.getWidth() - 190, 5, LabyMod.getInstance().draw.getWidth() - 151, 10 + cache.size() * 5 + 2 + lost.size() * 5 + 10, Color.toRGB(20, 20, 20, 100));
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(LabyMod.getInstance().draw.getWidth() - 150, 5, LabyMod.getInstance().draw.getWidth() - 5, 10 + cache.size() * 5, Color.toRGB(20, 20, 20, 100));
            int j = 0;

            for (Timings.Debug timings$debug1 : cache)
            {
                String s3 = "a";
                int i = timings$debug1.time;

                if (i > 2)
                {
                    s3 = "e";
                }

                if (i > 5)
                {
                    s3 = "6";
                }

                if (i > 10)
                {
                    s3 = "c";
                }

                if (i > 15)
                {
                    s3 = "4";
                }

                String s1 = Color.cl(s3) + i + "ms " + Color.cl("7") + " [" + Color.cl("c") + timings$debug1.path + Color.cl("7") + "]";
                LabyMod.getInstance().draw.drawString(s1, (double)(LabyMod.getInstance().draw.getWidth() - 148), (double)(7 + j), 0.5D);
                j += 5;
            }

            int k = 0;
            drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(LabyMod.getInstance().draw.getWidth() - 150, 10 + cache.size() * 5 + 2, LabyMod.getInstance().draw.getWidth() - 5, 10 + cache.size() * 5 + 2 + lost.size() * 5 + 10, Color.toRGB(20, 20, 20, 100));

            for (String s4 : lost.keySet())
            {
                Long olong = Long.valueOf(System.currentTimeMillis() - ((Long)lost.get(s4)).longValue());

                if (olong.longValue() > 1000L)
                {
                    LabyMod.getInstance().draw.drawString(s4 + " (" + olong + "ms)", (double)(LabyMod.getInstance().draw.getWidth() - 148), (double)(15 + j), 0.5D);
                    j += 5;
                    ++k;
                }
            }

            LabyMod.getInstance().draw.drawRightString(result.size() + " timings", (double)(LabyMod.getInstance().draw.getWidth() - 153), 7.0D, 0.5D);
            LabyMod.getInstance().draw.drawRightString(cache.size() + " cached", (double)(LabyMod.getInstance().draw.getWidth() - 153), 12.0D, 0.5D);
            LabyMod.getInstance().draw.drawRightString(k + " missing", (double)(LabyMod.getInstance().draw.getWidth() - 153), 17.0D, 0.5D);
            stop("Draw Timings");
        }
    }

    public static boolean isTiming()
    {
        return ConfigManager.settings == null ? false : de.labystudio.utils.Debug.timings();
    }

    public static class Debug
    {
        String path = "";
        int time;
        long created;

        public Debug(String path, int time, long created)
        {
            this.path = path;
            this.time = time;
            this.created = created;
        }
    }
}
