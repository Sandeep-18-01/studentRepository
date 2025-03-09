package platform.studentmanagement.dto;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import platform.studentmanagement.entity.Student;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private String studentCode;
    private String name;
    private String dateOfBirth;
    private String gender;
    private Set<StudentAddressDTO> addresses;

    public StudentDTO(Student student) {
        this.studentCode = student.getStudentCode();
        this.name = student.getName();
        this.dateOfBirth = student.getDateOfBirth();
        this.gender = student.getGender();
        this.addresses = student.getAddresses().stream()
                .map(StudentAddressDTO::new)
                .collect(Collectors.toSet());
    }

}
