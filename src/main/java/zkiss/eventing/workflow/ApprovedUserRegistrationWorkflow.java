package zkiss.eventing.workflow;

public class ApprovedUserRegistrationWorkflow {
    /*
     * 1. User details are persisted
     * 2. Send email to confirm receipt
     * 2. Send email for admin to approve
     * 3. Manual user approval by admin
     * 4. Send email: password setup link / rejected
     * 5. Activate user status so that user can log in
     */

    private final ApprovedUserRegistrationWorkflowState state;

    public ApprovedUserRegistrationWorkflow(ApprovedUserRegistrationWorkflowState state) {
        this.state = state;
    }
}
