package zkiss.eventing.workflow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;
import zkiss.eventing.message.SendEmailCommand;
import zkiss.eventing.message.UserDetailsSubmittedEvent;
import zkiss.eventing.service.User;
import zkiss.eventing.service.UserRepository;

@RequiredArgsConstructor
public class ApprovedUserRegistrationWorkflow {
    /*
     * 1. User details are persisted
     * 2. Send email to confirm receipt
     * 2. Send email to admin for approval
     * 3. Manual user approval by admin
     * 4. Send email: password setup link / rejected
     * 5. Activate user status so that user can log in
     */

    private final WorkflowStateRepository repository;
    private final EventBus eventBus;
    private final UserRepository userRepository;

    public void start(UserDetailsSubmittedEvent event) {
        repository.save(new ApprovedUserRegistrationWorkflowState(event));
    }

    @Subscribe
    public void sendUserEmailToConfirmReceipt(UserDetailsSubmittedEvent event) {
        ApprovedUserRegistrationWorkflowState state = repository.findByCorrelation(event.getCorrelationId());
        if (state == null) {
            return;
        }
        if (state.isUserConfirmationSent()) {
            return;
        }
        state.userConfirmationSent();
        User user = userRepository.get(state.getUserId());
        eventBus.post(new SendEmailCommand(event, user.getEmail(), "info@site.com",
                "We have received your application"));
    }

    @Subscribe
    public void sendEmailToAdminForApproval(UserDetailsSubmittedEvent event) {
        ApprovedUserRegistrationWorkflowState state = repository.findByCorrelation(event.getCorrelationId());
        if (state == null) {
            return;
        }
        if (state.isAdminRequestSent()) {
            return;
        }
        state.adminRequestSent();
        eventBus.post(new SendEmailCommand(event, "admin@site.com", "info@site.com",
                "Please evaluate user " + state.getUserId()));
    }

}
