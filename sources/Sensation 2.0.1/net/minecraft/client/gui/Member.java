package net.minecraft.client.gui;

/**
 * @author antja03
 */
public class Member {

    private int id;
    private String name;
    private MemberGroup group;

    public Member(int id, String name, MemberGroup group) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MemberGroup getGroup() {
        return group;
    }

}
