package zkiss.eventing.workflow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import zkiss.eventing.message.Message;
import zkiss.eventing.service.User;
import zkiss.eventing.service.UserDetails;
import zkiss.eventing.service.UserRegistrationService;
import zkiss.eventing.service.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApprovedUserRegistrationWorkflowTest {

    private EventBus bus;
    private WorkflowStateRepository wfRepo;
    private UserRepository userRepo;
    private ApprovedUserRegistrationWorkflow wf;
    private UserRegistrationService service;
    private EventListener listener;

    @Before
    public void setUp() throws Exception {
        bus = new EventBus();
        wfRepo = new WorkflowStateRepository() {
            private Map<String, WorkflowState> states = new HashMap<>();

            @Override
            public void save(WorkflowState state) {
                states.put(state.getCorrelationId(), state);
            }

            @Override
            public <T extends WorkflowState> T findByCorrelation(String correlationId) {
                return (T) states.get(correlationId);
            }
        };
        userRepo = new UserRepository() {
            private Map<String, User> users = new HashMap<>();

            @Override
            public void save(User user) {
                users.put(user.getId(), user);
            }

            @Override
            public User get(String id) {
                return users.get(id);
            }
        };
        wf = new ApprovedUserRegistrationWorkflow(
                wfRepo, bus, userRepo
        );
        service = UserRegistrationService.builder()
                .eventBus(bus)
                .repository(userRepo)
                .workflow(wf)
                .build();

        listener = Mockito.mock(EventListener.class);
        bus.register(wf);
        bus.register(listener);
    }

    @Captor
    private ArgumentCaptor<Message> captor;

    @Test
    public void name() throws Exception {
        service.startRegistrationFlow(new UserDetails("testing", "mail", "phone"));

        verify(listener, times(3)).listen(captor.capture());

        System.out.println(captor.getAllValues());
        assertThat(captor.getAllValues()).isNotEmpty();
    }

    public interface EventListener {
        @Subscribe
        void listen(Message msg);
    }
}