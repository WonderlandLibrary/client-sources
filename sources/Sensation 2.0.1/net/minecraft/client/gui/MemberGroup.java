package net.minecraft.client.gui;

/**
 * @author antja03
 */
public enum MemberGroup {
    INVITED(3), PREMIUM(7), MEDIA(10), BETA(15), DEVELOPER(13), MODERATOR(6), ADMINISTRATOR(4), BANNED(8);

    private int id;

    MemberGroup(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
