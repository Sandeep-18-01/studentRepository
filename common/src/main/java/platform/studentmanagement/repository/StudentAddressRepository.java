package platform.studentmanagement.repository;

import platform.studentmanagement.entity.StudentAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAddressRepository extends JpaRepository<StudentAddress, Long> {

    List<StudentAddress> findByStudent_Id(Long studentId);

    List<StudentAddress> findByAddressType(String addressType);
}
