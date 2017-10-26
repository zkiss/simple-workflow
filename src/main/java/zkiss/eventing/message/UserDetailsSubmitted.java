package zkiss.eventing.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsSubmitted extends Event {
    private final String userId;
}
