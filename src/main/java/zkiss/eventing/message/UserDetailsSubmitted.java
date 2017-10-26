package zkiss.eventing.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class UserDetailsSubmitted extends Event {
    private final String userId;
}
