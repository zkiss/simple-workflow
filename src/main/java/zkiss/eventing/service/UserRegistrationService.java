package zkiss.eventing.service;

import com.google.common.eventbus.EventBus;
import lombok.Builder;
import zkiss.eventing.message.UserDetailsSubmitted;
import zkiss.eventing.workflow.ApprovedUserRegistrationWorkflow;

@Builder
public class UserRegistrationService {
    private final UserRepository repository;
    private final EventBus eventBus;
    private final ApprovedUserRegistrationWorkflow workflow;

    public void startRegistrationFlow(UserDetails details) {
        User user = User.builder()
                .userName(details.getUserName())
                .email(details.getEmail())
                .phone(details.getPhone())
                .build();
        repository.save(user);

        UserDetailsSubmitted event = new UserDetailsSubmitted(user.getId());
        workflow.start(event);

        eventBus.post(event);
    }
}
