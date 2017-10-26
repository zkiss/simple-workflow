package zkiss.eventing.message;


import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
public abstract class Message {
    private final String id;
    private final String causationId;
    private final String correlationId;
    private final Instant createdAt;

    public Message(Message causedBy) {
        this(causedBy.getId(), causedBy.getCorrelationId());
    }

    public Message() {
        this(null, UUID.randomUUID().toString());
    }

    private Message(String causationId, String correlationId) {
        this.id = UUID.randomUUID().toString();
        this.causationId = causationId;
        this.correlationId = requireNonNull(correlationId);
        this.createdAt = Instant.now();
    }

}
