package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.settings.ColorSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.TripleTextHudElement;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WelcomeHudElement extends TripleTextHudElement {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Message> messageSetting = generalSettings.add(new EnumSetting.Builder<Message>()
            .name("Message")
            .description("Determines what message style to use.")
            .defaultValue(Message.Welcome)
            .build()
    );

    //TODO: Rainbow doesnt update :)))
    private final Setting<SettingColor> usernameColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Username Color")
            .description("Color of the username.")
            .defaultValue(new SettingColor(true))
            .build()
    );

    public WelcomeHudElement(Hud hud) {
        super(hud, "Welcome", "Displays a welcome message.", true);
        centerColor = usernameColorSetting.get();
    }

    @Override
    protected String getLeft() {
        return switch (messageSetting.get()) {
            case Using -> "You are using MatHax, ";
            case Time -> getTime() + ", ";
            case Retarded_Time -> getRetardedTime() + ", ";
            case Sussy -> "You are a sussy baka, ";
            default -> "Welcome to MatHax, ";
        };
    }

    @Override
    protected String getCenter() {
        return mc.getSession().getProfile().getName();
    }

    @Override
    public String getRight() {
        return "!";
    }

    private String getTime() {
        int hour = Integer.parseInt(new SimpleDateFormat("k").format(new Date()));
        if (hour < 6) {
            return "Good Night";
        }

        if (hour < 12) {
            return "Good Morning";
        }

        if (hour < 17) {
            return "Good Afternoon";
        }

        if (hour < 20) {
            return "Good Evening";
        }

        return "Good Night";
    }

    private String getRetardedTime() {
        int hour = Integer.parseInt(new SimpleDateFormat("k").format(new Date()));
        if (hour < 3) {
            return "Why are you killing newfags at this hour retard";
        }

        if (hour < 6) {
            return "You really need get some sleep retard";
        }

        if (hour < 9) {
            return "Ur awake already? such a retard";
        }

        if (hour < 12) {
            return "Retard moment";
        }

        if (hour < 14) {
            return "Go eat lunch retard";
        }

        if (hour < 17) {
            return "Retard playing minecraft";
        }

        return "Time to sleep retard";
    }

    public enum Message {
        Welcome("Welcome"),
        Using("Using"),
        Time("Time"),
        Retarded_Time("Retarded time"),
        Sussy("Sussy");

        private final String name;

        Message(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}