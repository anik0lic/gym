package raf.gymuserservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.gymuserservice.domain.Client;
import raf.gymuserservice.domain.Manager;
import raf.gymuserservice.dto.ManagerCreateDto;
import raf.gymuserservice.dto.ManagerDto;
import raf.gymuserservice.dto.UserDto;
import raf.gymuserservice.mapper.UserMapper;
import raf.gymuserservice.repository.ManagerRepository;
import raf.gymuserservice.repository.RoleRepository;
import raf.gymuserservice.service.ManagerService;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    private ManagerRepository managerRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    public ManagerServiceImpl(ManagerRepository managerRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserDto> findAllManagers(Pageable pageable) {
        return managerRepository.findUserByRole(roleRepository.findRoleByName("ROLE_MANAGER").get(), pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public ManagerDto addManager(ManagerCreateDto managerCreateDto) {
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto);
        managerRepository.save(manager);
        return userMapper.managerToManagerDto(manager);
    }
}
