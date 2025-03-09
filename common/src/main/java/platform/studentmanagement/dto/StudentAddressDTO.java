
package platform.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import platform.studentmanagement.entity.StudentAddress;
import platform.studentmanagement.enumDetails.AddressType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentAddressDTO {

    private String area;
    private String state;
    private String district;
    private String pincode;
    private AddressType addressType;

    public StudentAddressDTO(StudentAddress address) {
        this.area = address.getArea();
        this.state = address.getState();
        this.district = address.getDistrict();
        this.pincode = address.getPincode();
        this.addressType = address.getAddressType();
    }

}
