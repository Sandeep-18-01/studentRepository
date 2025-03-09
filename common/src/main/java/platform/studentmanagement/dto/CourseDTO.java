
package platform.studentmanagement.dto;

import platform.studentmanagement.entity.Course;

public class CourseDTO {

    private String courseName;
    private String description;
    private String courseType;
    private String duration;
    private String topics;

    public CourseDTO() {
    }

    public CourseDTO(Course course) {
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.courseType = course.getCourseType();
        this.duration = course.getDuration();
        this.topics = course.getTopics();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
