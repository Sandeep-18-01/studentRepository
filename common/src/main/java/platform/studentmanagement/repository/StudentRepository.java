package platform.studentmanagement.repository;

import platform.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContaining(String name);

    Optional<Student> findByStudentCodeAndDateOfBirth(String studentCode, String dateOfBirth);

    Optional<Student> findByStudentCode(String studentCode);

    List<Student> findByCourses_Id(Long courseId);

    List<Student> findByName(String name);

}
