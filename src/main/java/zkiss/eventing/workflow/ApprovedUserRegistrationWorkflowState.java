package zkiss.eventing.workflow;


public class ApprovedUserRegistrationWorkflowState {
    private final String correlationId;
    private final String userId;

    public ApprovedUserRegistrationWorkflowState(String correlationId, String userId) {
        this.correlationId = correlationId;
        this.userId = userId;
    }
}
