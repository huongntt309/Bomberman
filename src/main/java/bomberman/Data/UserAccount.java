package bomberman.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private Integer userID;
    private String username;
    private String password;
    private String createdDate;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
}
