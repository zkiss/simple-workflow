package zkiss.eventing.workflow;

import java.time.Instant;

public interface WorkflowState {
    String getCorrelationId();

    Instant getStartedAt();
}
