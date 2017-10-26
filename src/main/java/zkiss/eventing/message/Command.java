package zkiss.eventing.message;

public abstract class Command extends Message {
    public Command(Message causedBy) {
        super(causedBy);
    }

    public Command() {
    }
}
