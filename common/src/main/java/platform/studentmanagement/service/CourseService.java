package platform.studentmanagement.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.studentmanagement.dto.CourseDTO;
import platform.studentmanagement.entity.Course;
import platform.studentmanagement.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    private Logger logger;

    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(CourseDTO courseDto) {
        logger.info("Entering addCourse method with CourseDTO: {}", courseDto);

        try {
            Course course = new Course();
            course.setCourseName(courseDto.getCourseName());
            course.setDescription(courseDto.getDescription());
            course.setCourseType(courseDto.getCourseType());
            course.setTopics(courseDto.getTopics());
            course.setDuration(courseDto.getDuration());
            courseRepository.save(course);
            logger.info("Course added successfully: {}", course.getCourseName());
            return course;
        } catch (Exception e) {
            logger.error("Error while adding course: {}", e.getMessage());
            throw new RuntimeException("Failed to add course: " + courseDto.getCourseName(), e);
        }
    }
}
