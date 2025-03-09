package platform.studentmanagement.controller;

import platform.studentmanagement.dto.AdminDTO;
import platform.studentmanagement.entity.Admin;
import platform.studentmanagement.exception.AdminNotFoundException;
import platform.studentmanagement.exception.WrongPasswordException;
import platform.studentmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Endpoint for admin registration
    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminDTO adminDTO) {
        String createdAdmin = adminService.createAdmin(adminDTO);
        return ResponseEntity.ok(createdAdmin);
    }

    // Endpoint for admin login
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestParam String username, @RequestParam String password) {

        Admin admin = adminService.findByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with username: " + username));

        if (!adminService.validatePassword(password, admin.getPassword())) {
            throw new WrongPasswordException("Invalid password");
        }

        return ResponseEntity.ok("Admin logged in successfully");
    }
}
