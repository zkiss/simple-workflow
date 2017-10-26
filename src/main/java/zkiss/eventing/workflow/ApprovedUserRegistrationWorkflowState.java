package zkiss.eventing.workflow;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import zkiss.eventing.message.UserDetailsSubmittedEvent;

import java.time.Instant;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApprovedUserRegistrationWorkflowState implements WorkflowState {
    private final String correlationId;
    private final String userId;
    private final Instant startedAt;

    private boolean userConfirmationSent;
    private boolean adminRequestSent;

    public ApprovedUserRegistrationWorkflowState(UserDetailsSubmittedEvent firstEvent) {
        this(
                firstEvent.getCorrelationId(),
                firstEvent.getUserId(),
                firstEvent.getCreatedAt()
        );
    }

    public void userConfirmationSent() {
        this.userConfirmationSent = true;
    }

    public void adminRequestSent() {
        this.adminRequestSent = true;
    }
}
