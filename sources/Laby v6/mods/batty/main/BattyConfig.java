package mods.batty.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BattyConfig
{
    private static BattyUI ui = BattyMod.getInstance().getBatheartgui();

    public static void retrieveOptions()
    {
        if (ui.optionsFile.exists())
        {
            try
            {
                FileInputStream fileinputstream = new FileInputStream(ui.optionsFile);

                try
                {
                    ui.propts.load((InputStream)fileinputstream);
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }
            catch (FileNotFoundException filenotfoundexception)
            {
                filenotfoundexception.printStackTrace();
            }
        }

        String s12 = ui.propts.getProperty("Coords.shade");
        String s = ui.propts.getProperty("Coords.chars.Increase");
        String s1 = ui.propts.getProperty("Coords.chars.Decrease");
        String s2 = ui.propts.getProperty("Coords.colours.TitleText");
        String s3 = ui.propts.getProperty("Coords.colours.CoordText");
        String s4 = ui.propts.getProperty("Coords.colours.PosCoordText");
        String s5 = ui.propts.getProperty("Coords.colours.NegCoordText");
        String s6 = ui.propts.getProperty("Coords.colours.CompassText");
        String s7 = ui.propts.getProperty("Coords.colours.ChevronText");
        String s8 = ui.propts.getProperty("Coords.colours.BiomeText");
        String s9 = ui.propts.getProperty("Coords.colours.PosChunkText");
        String s10 = ui.propts.getProperty("Coords.colours.NegChunkText");
        String s11 = ui.propts.getProperty("Coords.copy.tpFormat");

        if (s12 != null)
        {
            ui.shadedCoords = s12.equals("true");
        }

        if (s != null)
        {
            if (s.length() > 1)
            {
                ui.myChevronUp = s.substring(0, 1);
            }
            else
            {
                ui.myChevronUp = s;
            }
        }

        if (s1 != null)
        {
            if (s1.length() > 1)
            {
                ui.myChevronDown = s1.substring(0, 1);
            }
            else
            {
                ui.myChevronDown = s1;
            }
        }

        if (s2 != null)
        {
            BattyUI battyui11 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s2);

            if (ui.myFind != -1)
            {
                battyui11 = ui;
                ui.myTitleText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s4 != null)
        {
            BattyUI battyui = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s4);

            if (ui.myFind != -1)
            {
                battyui = ui;
                ui.myPosCoordText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s5 != null)
        {
            BattyUI battyui1 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s5);

            if (ui.myFind != -1)
            {
                battyui1 = ui;
                ui.myNegCoordText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s6 != null)
        {
            BattyUI battyui2 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s6);

            if (ui.myFind != -1)
            {
                battyui2 = ui;
                ui.myCompassText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s7 != null)
        {
            BattyUI battyui3 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s7);

            if (ui.myFind != -1)
            {
                battyui3 = ui;
                ui.myChevronText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s8 != null)
        {
            BattyUI battyui4 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s8);

            if (ui.myFind != -1)
            {
                battyui4 = ui;
                ui.myBiomeText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s3 != null)
        {
            BattyUI battyui5 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s3);

            if (ui.myFind != -1)
            {
                battyui5 = ui;
                ui.myPosCoordText = BattyUI.myColourCodes[ui.myFind];
                battyui5 = ui;
                ui.myNegCoordText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s9 != null)
        {
            BattyUI battyui6 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s9);

            if (ui.myFind != -1)
            {
                battyui6 = ui;
                ui.myPosChunkText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s10 != null)
        {
            BattyUI battyui7 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s10);

            if (ui.myFind != -1)
            {
                battyui7 = ui;
                ui.myNegChunkText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s11 != null)
        {
            ui.coordsCopyTPFormat = s11.equals("true");
        }

        s12 = ui.propts.getProperty("Timer.shade");
        s2 = ui.propts.getProperty("Timer.colours.Stopped");
        s4 = ui.propts.getProperty("Timer.colours.Running");

        if (s12 != null)
        {
            ui.shadedTimer = s12.equals("true");
        }

        if (s2 != null)
        {
            BattyUI battyui8 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s2);

            if (ui.myFind != -1)
            {
                battyui8 = ui;
                ui.myTimerStopText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        if (s4 != null)
        {
            BattyUI battyui9 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s4);

            if (ui.myFind != -1)
            {
                battyui9 = ui;
                ui.myTimerRunText = BattyUI.myColourCodes[ui.myFind];
            }
        }

        s12 = ui.propts.getProperty("FPS.shade");
        s2 = ui.propts.getProperty("FPS.colours.Text");

        if (s12 != null)
        {
            ui.shadedFPS = s12.equals("true");
        }

        if (s2 != null)
        {
            BattyUI battyui10 = ui;
            ui.myFind = BattyUtils.nameSearch(BattyUI.myColourList, s2);

            if (ui.myFind != -1)
            {
                battyui10 = ui;
                ui.myFPSText = BattyUI.myColourCodes[ui.myFind];
            }
        }
    }

    public static void retrieveRuntimeOptions()
    {
        if (ui.runtimeFile.exists())
        {
            try
            {
                FileInputStream fileinputstream = new FileInputStream(ui.runtimeFile);

                try
                {
                    ui.proprt.load((InputStream)fileinputstream);
                }
                catch (IOException ioexception)
                {
                    ioexception.printStackTrace();
                }
            }
            catch (FileNotFoundException filenotfoundexception)
            {
                filenotfoundexception.printStackTrace();
            }
        }

        String s7 = ui.proprt.getProperty("Timer.saved");

        if (s7 != null)
        {
            BattyUtils.parseTimeString(s7);
        }

        String s = ui.proprt.getProperty("Coords.visible");

        if (s != null)
        {
            ui.showCoords = Integer.parseInt(s);
        }

        String s1 = ui.proprt.getProperty("Timer.visible");

        if (s1 != null)
        {
            ui.hideTimer = !s1.equals("true");
        }

        String s2 = ui.proprt.getProperty("Coords.location");

        if (s2 != null)
        {
            ui.coordLocation = Integer.parseInt(s2);
        }

        String s3 = ui.proprt.getProperty("Timer.location");

        if (s3 != null)
        {
            ui.timerLocation = Integer.parseInt(s3);
        }

        String s4 = ui.proprt.getProperty("Timer.running");

        if (s4 != null)
        {
            ui.timerRunning = s4.equals("true");
        }

        String s5 = ui.proprt.getProperty("FPS.visible");

        if (s5 != null)
        {
            ui.hideFPS = !s5.equals("true");
        }

        String s6 = ui.proprt.getProperty("FPS.location");

        if (s6 != null)
        {
            ui.fpsLocation = Integer.parseInt(s6);
        }
    }

    public static void storeRuntimeOptions()
    {
        ui.proprt.setProperty("Timer.saved", BattyUtils.getSaveString());
        ui.proprt.setProperty("Coords.visible", BattyUtils.constructCoordVisString());
        ui.proprt.setProperty("Timer.visible", BattyUtils.constructTimerVisString());
        ui.proprt.setProperty("Coords.location", BattyUtils.constructCoordLocString());
        ui.proprt.setProperty("Timer.location", BattyUtils.constructTimerLocString());
        ui.proprt.setProperty("Timer.running", BattyUtils.constructTimerRunString());
        ui.proprt.setProperty("FPS.visible", BattyUtils.constructFPSVisString());
        ui.proprt.setProperty("FPS.location", BattyUtils.constructFPSLocString());

        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(ui.runtimeFile);
            ui.proprt.store((OutputStream)fileoutputstream, (String)null);
            fileoutputstream.flush();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }
}
