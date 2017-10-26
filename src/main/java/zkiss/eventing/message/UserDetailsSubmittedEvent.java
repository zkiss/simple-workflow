package zkiss.eventing.message;

public class UserDetailsSubmittedEvent extends Event {
    private final String userId;

    public UserDetailsSubmittedEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
