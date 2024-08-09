package fun.ellant.events;


import lombok.*;

@Data
@AllArgsConstructor
public class EventKey {
    int key;
    public boolean isKeyDown(int key) {
        return this.key == key;
    }
}
