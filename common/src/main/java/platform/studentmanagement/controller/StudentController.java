package platform.studentmanagement.controller;

import platform.studentmanagement.dto.CourseDTO;
import platform.studentmanagement.dto.ProfileUpdateDTO;
import platform.studentmanagement.dto.StudentDTO;
import platform.studentmanagement.entity.Course;
import platform.studentmanagement.entity.Student;
import platform.studentmanagement.service.CourseService;
import platform.studentmanagement.service.StudentService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private Logger logger;

    @Tag(name = "Admin Operations")
    @Operation(summary = "Admit a student", operationId = "adminAdmitStudent")
    @PostMapping("/admin/admitStudent")
    public void admitStudent(@RequestBody StudentDTO studentDTO) {
        studentService.admitStudent(studentDTO);
    }

    @Tag(name = "Admin Operations")
    @Operation(summary = "Upload course details", operationId = "adminUploadCourse")
    @PostMapping("/admin/uploadCourse")
    public ResponseEntity<Course> addCourse(@RequestBody CourseDTO course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    @Tag(name = "Admin Operations")
    @Operation(summary = "Assign course to student", operationId = "adminAssignCourseToStudent")
    @PostMapping("/admin/assignCourseToStudent")
    public ResponseEntity<Student> assignCourseToStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        Student assigned = studentService.assignCourseToStudent(studentId, courseId);
        return ResponseEntity.ok(assigned);
    }

    @Tag(name = "Admin Operations")
    @Operation(summary = "Search student by name", operationId = "adminSearchStudent")
    @GetMapping("/admin/searchStudent")
    public List<StudentDTO> getStudentsByName(@RequestParam String name) {
        return studentService.getStudentsByName(name);
    }

    @Tag(name = "Admin Operations")
    @Operation(summary = "Get students in a course", operationId = "adminGetStudentsInCourse")
    @GetMapping("/admin/getStudentsInCourse")
    public List<StudentDTO> getStudentsInCourse(@RequestParam Long courseId) {
        return studentService.getStudentsInCourse(courseId);
    }

    @Tag(name = "Student Operations")
    @Operation(summary = "Validate student", operationId = "studentValidate")
    @PostMapping("/student/validate")
    public ResponseEntity<String> studentValidate(@RequestParam String studentCode, @RequestParam String dateOfBirth) {
        String validationResult = studentService.studentValidate(studentCode, dateOfBirth);
        return ResponseEntity.ok(validationResult);
    }

    @Tag(name = "Student Operations")
    @Operation(summary = "Update student profile", operationId = "studentUpdateProfile")
    @PutMapping("/student/{studentId}/updateProfile")
    public ResponseEntity<StudentDTO> updateStudentProfile(
            @PathVariable Long studentId,
            @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        logger.info("Received JSON for Profile Update: {}", profileUpdateDTO);
        StudentDTO updatedStudent = studentService.updateStudentProfile(profileUpdateDTO, studentId);
        return ResponseEntity.ok(updatedStudent);
    }

    @Tag(name = "Student Operations")
    @Operation(summary = "Get assigned courses", operationId = "studentGetAssignedCourses")
    @GetMapping("/student/{studentCode}/assignedCourses")
    public List<String> getAssignedCourses(@PathVariable String studentCode) {
        return studentService.getAssignedCourses(studentCode);
    }

    @Tag(name = "Student Operations")
    @Operation(summary = "Leave a course", operationId = "studentLeaveCourse")
    @DeleteMapping("/student/{studentCode}/leaveCourse")
    public String leaveCourse(@PathVariable String studentCode, @RequestParam Long courseId) {
        return studentService.leaveCourse(studentCode, courseId);
    }
}
