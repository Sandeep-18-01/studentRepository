package platform.studentmanagement.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileUpdateDTO {

    private String studentCode;
    private String name;
    private String dateOfBirth;
    private String gender;
    private List<StudentAddressDTO> studentAddressDTO;
}
