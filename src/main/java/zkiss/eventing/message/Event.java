package zkiss.eventing.message;

import lombok.ToString;

@ToString(callSuper = true)
public abstract class Event extends Message {
    public Event(Message causedBy) {
        super(causedBy);
    }

    public Event() {
    }
}
