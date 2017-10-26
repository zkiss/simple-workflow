package zkiss.eventing.message;

public class UserInitialPasswordSet extends Event {
    public UserInitialPasswordSet(Message causedBy) {
        super(causedBy);
    }
}
