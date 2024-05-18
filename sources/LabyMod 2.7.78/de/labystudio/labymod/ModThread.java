package de.labystudio.labymod;

public class ModThread extends Thread
{
    long time = 0L;

    public void run()
    {
        int i = 0;

        while (true)
        {
            try
            {
                if (!ConfigManager.settings.thread)
                {
                    LabyMod.getInstance().secondLoop();

                    synchronized (this)
                    {
                        try
                        {
                            this.wait(1000L);
                        }
                        catch (Exception var5)
                        {
                            System.out.println("Failed to wait (LabyMod)");
                        }
                    }
                }
                else if (this.time + 1000L < System.currentTimeMillis())
                {
                    this.time = System.currentTimeMillis();
                    LabyMod.getInstance().secondLoop();
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                System.out.println("Failed to loop (LabyMod)");
            }
        }
    }
}
