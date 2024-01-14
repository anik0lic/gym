package raf.gymuserservice.runner;

import raf.gymuserservice.domain.*;
import raf.gymuserservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {
    private RoleRepository roleRepository;
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private UserStatusRepository userStatusRepository;

    public TestDataRunner(RoleRepository roleRepository, AdminRepository adminRepository,
                          ClientRepository clientRepository, ManagerRepository managerRepository,
                          UserStatusRepository userStatusRepository) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.userStatusRepository = userStatusRepository;
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

        //Insert user status
        userStatusRepository.save(new UserStatus(0, 5, 0));
        userStatusRepository.save(new UserStatus(6, 10, 10));
        userStatusRepository.save(new UserStatus(11, 20, 20));

        //Insert admin
        User admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        adminRepository.save(admin);

        //Insert clients
        Client client = new Client();
        client.setEmail("client@client.com");
        client.setUsername("client");
        client.setPassword("client");
        client.setFirstName("Client");
        client.setLastName("Client");
        client.setDateOfBirth(LocalDate.parse("01/02/1980", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        client.setRole(roleClient);
        client.setNumberOfReservations(7);
        clientRepository.save(client);

        //Insert Manager
        Manager manager = new Manager();
        manager.setEmail("manager@manager.com");
        manager.setUsername("manager");
        manager.setPassword("manager");
        manager.setFirstName("Manager");
        manager.setLastName("Manager");
        manager.setDateOfBirth(LocalDate.parse("02/03/1981", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        manager.setRole(roleManager);
        manager.setStartDate(LocalDate.parse("10/07/1999", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        manager.setGym("Gym01");
        managerRepository.save(manager);
    }
}
