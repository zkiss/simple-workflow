package zkiss.eventing.workflow;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zkiss.eventing.message.UserDetailsSubmitted;

import java.time.Instant;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApprovedUserRegistrationWorkflowState implements WorkflowState {
    private final String correlationId;
    private final String userId;
    private final Instant startedAt;

    public ApprovedUserRegistrationWorkflowState(UserDetailsSubmitted firstEvent) {
        this(
                firstEvent.getCorrelationId(),
                firstEvent.getUserId(),
                firstEvent.getCreatedAt()
        );
    }

}
