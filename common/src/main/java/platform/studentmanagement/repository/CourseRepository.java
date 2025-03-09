package platform.studentmanagement.repository;

import platform.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCourseNameContaining(String courseName);

    List<Course> findByStudents_Id(Long studentId);
}
