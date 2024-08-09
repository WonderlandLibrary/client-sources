package wtf.shiyeno.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private final String name;
    private final int uid;
    private final String expiration;
    private final RoleType role;
}