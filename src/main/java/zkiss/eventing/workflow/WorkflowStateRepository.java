package zkiss.eventing.workflow;

public interface WorkflowStateRepository {
    void save(WorkflowState state);

    <T extends WorkflowState> T findByCorrelation(String correlationId);
}
