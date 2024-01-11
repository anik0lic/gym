package raf.gymuserservice.runner;

import raf.gymuserservice.domain.Admin;
import raf.gymuserservice.domain.Role;
import raf.gymuserservice.domain.User;
import raf.gymuserservice.repository.AdminRepository;
import raf.gymuserservice.repository.RoleRepository;
import raf.gymuserservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;

    public TestDataRunner(RoleRepository roleRepository, AdminRepository adminRepository) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleClient = new Role("ROLE_CLIENT");
        Role roleManager = new Role("ROLE_MANAGER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleClient);
        roleRepository.save(roleManager);

        //Insert admin
        User admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        adminRepository.save(admin);
    }
}
