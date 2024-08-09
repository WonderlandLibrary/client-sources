package wtf.shiyeno.command.impl;

import wtf.shiyeno.command.Command;
import wtf.shiyeno.command.CommandInfo;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.TimerUtil;

@CommandInfo(
        name = "calc",
        description = "Делит на 8 ваши координаты"
)
public class Calculator extends Command {
    public int test;
    private final TimerUtil timerUtil = new TimerUtil();

    public Calculator() {
    }

    public void run(String[] args) throws Exception {
        if (!args[1].isEmpty() && !args[2].isEmpty()) {
            int xPos = Integer.parseInt(args[1]);
            int zPos = Integer.parseInt(args[2]);
            int var10000 = Integer.parseInt(args[1]) / 8;
            ClientUtil.sendMesage(String.valueOf("Ваши координаты: " + var10000 + ", " + Integer.parseInt(args[2]) / 8));
            if (args[3].contains("gps")) {
                mc.player.sendChatMessage(".gps " + xPos / 8 + " " + zPos / 8);
            }
        }
    }

    public void error() {
    }
}