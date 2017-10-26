package zkiss.eventing.message;

public abstract class Event extends Message {
    public Event(Message causedBy) {
        super(causedBy);
    }

    public Event() {
    }
}
