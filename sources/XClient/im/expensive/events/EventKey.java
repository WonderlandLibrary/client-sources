package im.expensive.events;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
public class EventKey {
    int key;
    public boolean isKeyDown(int key) {
        return this.key == key;
    }
}
