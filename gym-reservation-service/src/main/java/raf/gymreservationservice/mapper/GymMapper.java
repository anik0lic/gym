package raf.gymreservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.gymreservationservice.domain.Gym;
import raf.gymreservationservice.dto.GymCreateDto;
import raf.gymreservationservice.dto.GymDto;

@Component
public class GymMapper {
    public GymDto gymToGymDto(Gym gym){
        GymDto gymDto = new GymDto();
        gymDto.setId(gym.getId());
        gymDto.setName(gym.getName());
        gymDto.setDescription(gym.getDescription());
        gymDto.setNumberOfCoaches(gym.getNumberOfCoaches());
        gymDto.setCapacity(gym.getCapacity());
        return gymDto;
    }

    public Gym gymCreateDtoToGym(GymCreateDto gymCreateDto){
        Gym gym = new Gym();
        gym.setName(gymCreateDto.getName());
        gym.setDescription(gymCreateDto.getDescription());
        gym.setNumberOfCoaches(gymCreateDto.getNumberOfCoaches());
        gym.setCapacity(gymCreateDto.getCapacity());
        return gym;
    }
}
