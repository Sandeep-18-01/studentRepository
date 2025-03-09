package platform.studentmanagement.service;

import platform.studentmanagement.dto.CourseDTO;
import platform.studentmanagement.dto.ProfileUpdateDTO;
import platform.studentmanagement.dto.StudentAddressDTO;
import platform.studentmanagement.dto.StudentDTO;
import platform.studentmanagement.entity.Course;
import platform.studentmanagement.entity.Student;
import platform.studentmanagement.entity.StudentAddress;
import platform.studentmanagement.exception.CourseNotFoundException;
import platform.studentmanagement.exception.StudentNotFoundException;
import platform.studentmanagement.exception.WarningException;
import platform.studentmanagement.repository.CourseRepository;
import platform.studentmanagement.repository.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Student admitStudent(StudentDTO studentDTO) {
        logger.info("Admitting student: ", studentDTO);

        Student student = new Student();
        Set<StudentAddress> addressSet = studentDTO.getAddresses().stream()
                .map(this::studentAddressDTOToEntity)
                .peek(address -> address.setStudent(student))
                .collect(Collectors.toSet());

        student.setAddresses(addressSet);
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setGender(studentDTO.getGender());
        student.setName(studentDTO.getName());
        student.setStudentCode(studentDTO.getStudentCode());

        logger.debug("Student entity created:", student);
        return studentRepository.save(student);
    }

    private StudentAddress studentAddressDTOToEntity(StudentAddressDTO studentAddressDTO) {
        StudentAddress studentAddress = new StudentAddress();
        studentAddress.setArea(studentAddressDTO.getArea());
        studentAddress.setState(studentAddressDTO.getState());
        studentAddress.setDistrict(studentAddressDTO.getDistrict());
        studentAddress.setPincode(studentAddressDTO.getPincode());
        studentAddress.setAddressType(studentAddressDTO.getAddressType());
        return studentAddress;
    }

    public void addCourse(CourseDTO courseDto) {
        logger.info("Adding course:", courseDto);

        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());
        course.setCourseType(courseDto.getCourseType());
        course.setDuration(courseDto.getDuration());

        logger.debug("Course entity created:", course);
        courseRepository.save(course);
    }

    public List<StudentDTO> getStudentsByName(String name) {
        logger.info("Searching for students with name: ", name);

        List<Student> students = studentRepository.findByName(name);
        logger.debug("Found students: ", students);

        return students.stream().map(StudentDTO::new).collect(Collectors.toList());
    }

    public StudentDTO updateStudentProfile(ProfileUpdateDTO profileUpdateDTO, Long studentId) {
        logger.info("Updating profile for student ID: ", studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found with id:", studentId);
                    return new StudentNotFoundException("Student not found with id: " + studentId);
                });

        updateProfile(profileUpdateDTO, student);
        studentRepository.save(student);
        logger.info("Profile updated for student ID: ", studentId);

        return new StudentDTO(student);
    }

    private void updateProfile(ProfileUpdateDTO profileUpdateDTO, Student student) {
        if (profileUpdateDTO.getStudentCode() != null) {
            student.setStudentCode(profileUpdateDTO.getStudentCode());
            logger.debug("Updated student code for ID : ", student.getId(), profileUpdateDTO.getStudentCode());
        }
        if (profileUpdateDTO.getName() != null) {
            student.setName(profileUpdateDTO.getName());
            logger.debug("Updated name for ID:", student.getId(), profileUpdateDTO.getName());
        }
        if (profileUpdateDTO.getDateOfBirth() != null) {
            student.setDateOfBirth(profileUpdateDTO.getDateOfBirth());
            logger.debug("Updated date of birth for ID:", student.getId(), profileUpdateDTO.getDateOfBirth());
        }
        if (profileUpdateDTO.getGender() != null) {
            student.setGender(profileUpdateDTO.getGender());
            logger.debug("Updated gender for ID:", student.getId(), profileUpdateDTO.getGender());
        }

        if (profileUpdateDTO.getStudentAddressDTO() != null) {
            List<StudentAddressDTO> studentAddressDTOs = profileUpdateDTO.getStudentAddressDTO();
            Set<StudentAddress> compareList = student.getAddresses();
            for (StudentAddress co : compareList) {
                for (StudentAddressDTO addressDTO : studentAddressDTOs) {
                    if (addressDTO.getAddressType().equals(co.getAddressType())) {
                        updateAddress(student, addressDTO);
                    }
                }
            }
        }
    }

    private void updateAddress(Student student, StudentAddressDTO addressDTO) {
        student.getAddresses().stream()
                .filter(address -> address.getAddressType() == addressDTO.getAddressType())
                .findFirst()
                .ifPresentOrElse(existingAddress -> {
                    existingAddress.setArea(addressDTO.getArea());
                    existingAddress.setState(addressDTO.getState());
                    existingAddress.setDistrict(addressDTO.getDistrict());
                    existingAddress.setPincode(addressDTO.getPincode());
                    logger.debug("Updated existing address:", existingAddress);
                }, () -> {
                    StudentAddress newAddress = new StudentAddress();
                    newAddress.setArea(addressDTO.getArea());
                    newAddress.setState(addressDTO.getState());
                    newAddress.setDistrict(addressDTO.getDistrict());
                    newAddress.setPincode(addressDTO.getPincode());
                    newAddress.setAddressType(addressDTO.getAddressType());
                    newAddress.setStudent(student);
                    student.getAddresses().add(newAddress);
                    logger.debug("Added new address:", newAddress);
                });
    }

    public Student assignCourseToStudent(Long studentId, Long courseId) {
        logger.info("Assigning course ID: to student ID:", courseId, studentId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student not found with id:", studentId);
                    return new StudentNotFoundException("Student not found with id: " + studentId);
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with id:", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });

        if (student.getCourses().contains(course)) {
            logger.error("Course already present for student ID:", studentId);
            throw new WarningException("Course already present for student ID: " + studentId);
        }

        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);

        logger.info("Course ID: assigned to student ID:", courseId, studentId);
        return student;
    }

    public List<String> getAssignedCourses(String studentCode) {
        logger.info("Getting assigned courses for student code:", studentCode);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> {
                    logger.error("Student not found with code:", studentCode);
                    return new StudentNotFoundException("Student not found with code: " + studentCode);
                });

        List<String> assignedCourses = student.getCourses().stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());

        logger.info("Assigned courses for student:", student.getName(), assignedCourses);
        return assignedCourses;
    }

    public String leaveCourse(String studentCode, Long courseId) {
        logger.info("Student with code: is leaving course ID:", studentCode, courseId);

        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> {
                    logger.error("Student not found with code:", studentCode);
                    return new StudentNotFoundException("Student not found with code: " + studentCode);
                });

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with id:", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });

        if (!student.getCourses().contains(course)) {
            logger.error("No course found associated with student code:", studentCode);
            throw new CourseNotFoundException("No course found associated with student code: " + studentCode);
        }

        student.getCourses().remove(course);
        studentRepository.save(student);

        logger.info("Student has left course ID:", student.getName(), courseId);
        return "Course removed successfully";
    }

    public List<StudentDTO> getStudentsInCourse(Long courseId) {
        logger.info("Getting students for course ID:", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course not found with id:", courseId);
                    return new CourseNotFoundException("Course not found with id: " + courseId);
                });

        List<StudentDTO> studentsInCourse = course.getStudents().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());

        logger.info("Found students in course ID:", courseId, studentsInCourse);
        return studentsInCourse;
    }

    public String studentValidate(String studentCode, String dateOfBirth) {
        logger.info("Validating student with code: and date of birth:", studentCode, dateOfBirth);

        Optional<Student> student = studentRepository.findByStudentCodeAndDateOfBirth(studentCode, dateOfBirth);
        String result = student.map(s -> "Student validated successfully")
                .orElse("Invalid student credentials");

        logger.info("Validation result for student code:", studentCode, result);
        return result;
    }
}
