package raf.gymreservationservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import raf.gymreservationservice.domain.Appointment;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.dto.GymCreateDto;
import raf.gymreservationservice.dto.GymDto;
import raf.gymreservationservice.mapper.GymMapper;
import raf.gymreservationservice.repository.GymRepository;
import raf.gymreservationservice.service.GymService;

@Service
public class GymServiceImpl implements GymService {
    private GymRepository gymRepository;
    private GymMapper gymMapper;

    public GymServiceImpl(GymRepository gymRepository, GymMapper gymMapper) {
        this.gymRepository = gymRepository;
        this.gymMapper = gymMapper;
    }

    @Override
    public Page<GymDto> findAll(Pageable pageable) {
        return gymRepository.findAll(pageable)
                .map(gymMapper::gymToGymDto);
    }

    @Override
    public GymDto findById(Long id) {
        Gym gym = gymRepository.findById(id).get();
        return gymMapper.gymToGymDto(gym);
    }

    @Override
    public GymDto add(GymCreateDto gymCreateDto) {
        Gym gym = gymMapper.gymCreateDtoToGym(gymCreateDto);
        gymRepository.save(gym);
        return gymMapper.gymToGymDto(gym);
    }

    @Override
    public void deleteById(Long id) {
        gymRepository.deleteById(id);
    }
}
