package dev.darkmoon.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    private String name;
    private int uid;
    private String role;
}
