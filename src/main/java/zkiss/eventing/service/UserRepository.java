package zkiss.eventing.service;

public interface UserRepository {
    void save(User user);

    User get(String id);
}
