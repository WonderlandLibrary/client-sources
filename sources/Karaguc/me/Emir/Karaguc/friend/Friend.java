package me.Emir.Karaguc.friend;

public class Friend {
	
    private String name;
    private String nickname;

    public Friend(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }
}
