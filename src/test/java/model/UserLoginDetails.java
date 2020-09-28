package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserLoginDetails {
    public static final UserLoginDetails
            DEFAULT_USER_LOGIN_DETAILS = new UserLoginDetails("bruno@email.com", "bruno");
    private String email;
    private String password;
}