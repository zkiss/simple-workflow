package zkiss.eventing.workflow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;
import zkiss.eventing.message.SendEmailCommand;
import zkiss.eventing.message.UserDetailsSubmitted;
import zkiss.eventing.message.UserInitialPasswordSet;
import zkiss.eventing.message.UserRegistrationEvaluated;
import zkiss.eventing.service.User;
import zkiss.eventing.service.UserRepository;

import java.util.UUID;

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

    public void start(UserDetailsSubmitted event) {
        repository.save(new ApprovedUserRegistrationWorkflowState(event));
    }

    @Subscribe
    public void sendUserEmailToConfirmReceipt(UserDetailsSubmitted event) {
        ApprovedUserRegistrationWorkflowState state = repository.findByCorrelation(event.getCorrelationId());
        if (state == null) {
            return;
        }
        User user = userRepository.get(state.getUserId());
        eventBus.post(new SendEmailCommand(event, user.getEmail(), "info@site.com",
                "We have received your application"));
        eventBus.post(new SendEmailCommand(event, "admin@site.com", "info@site.com",
                "Please evaluate user " + state.getUserId()));
    }

    @Subscribe
    public void processUserEvaluationByAdmin(UserRegistrationEvaluated event) {
        ApprovedUserRegistrationWorkflowState state = repository.findByCorrelation(event.getCorrelationId());
        if (state == null) {
            return;
        }
        User user = userRepository.get(state.getUserId());
        if (event.isAccepted()) {
            user.accept();
            eventBus.post(new SendEmailCommand(event, user.getEmail(), "info@site.com",
                    "Please set up your password with this link: " + UUID.randomUUID().toString()));
        } else {
            user.reject();
            eventBus.post(new SendEmailCommand(event, user.getEmail(), "info@site.com",
                    "Sorry, your application is rejected"));
        }
        userRepository.save(user);
    }

    @Subscribe
    public void activateUserAfterPasswordSetup(UserInitialPasswordSet event) {
        ApprovedUserRegistrationWorkflowState state = repository.findByCorrelation(event.getCorrelationId());
        if (state == null) {
            return;
        }
        User user = userRepository.get(state.getUserId());
        user.activate();
        userRepository.save(user);
    }

}
