package zkiss.eventing.message;

public class SendEmailCommand extends Command {
    private final String to;
    private final String from;
    private final String text;

    public SendEmailCommand(Message causedBy, String to, String from, String text) {
        super(causedBy);
        this.to = to;
        this.from = from;
        this.text = text;
    }
}
