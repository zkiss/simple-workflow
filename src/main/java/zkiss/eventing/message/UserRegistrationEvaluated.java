package zkiss.eventing.message;

import lombok.Getter;

@Getter
public class UserRegistrationEvaluated extends Event {
    private boolean accepted;
}
