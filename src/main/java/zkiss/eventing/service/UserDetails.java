package zkiss.eventing.service;

import lombok.Value;

@Value
public class UserDetails {
    private String userName;
    private String email;
    private String phone;
}
