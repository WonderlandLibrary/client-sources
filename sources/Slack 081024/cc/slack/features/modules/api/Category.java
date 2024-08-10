// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.api;

import lombok.Getter;

import java.awt.*;

@Getter
public enum Category {
    COMBAT(new Color(55, 55, 55)),
    MOVEMENT(new Color(55, 55, 55)),
    PLAYER(new Color(55, 55, 55)),
    WORLD(new Color(55, 55, 55)),
    EXPLOIT(new Color(55, 55, 55)),
    RENDER(new Color(55, 55, 55)),
    GHOST(new Color(55, 55, 55)),
    UTILITIES(new Color(55, 55, 55)),
    OTHER(new Color(55, 55, 55));

    final String name;
    final String icon;
    final Color color;

    Category(Color color) {
        this.color = color;
        this.name = this.name().charAt(0) + this.name().substring(1).toLowerCase();
        this.icon = this.name().substring(0, 1);
    }

    public int getColorRGB() {
        return getColor().getRGB();
    }
}
