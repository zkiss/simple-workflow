package zkiss.eventing.service;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    private final String id;
    private final String userName;

    private String email;
    private String phone;


    @Builder
    public User(String userName, String email, String phone) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
        this.email = email;
        this.phone = phone;
    }

}
