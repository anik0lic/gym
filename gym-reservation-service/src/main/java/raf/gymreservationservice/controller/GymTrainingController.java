package raf.gymreservationservice.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.gymreservationservice.dto.GymCreateDto;
import raf.gymreservationservice.dto.GymTrainingCreateDto;
import raf.gymreservationservice.dto.GymTrainingDto;
import raf.gymreservationservice.secutiry.CheckSecurity;
import raf.gymreservationservice.service.GymService;
import raf.gymreservationservice.service.GymTrainingService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/gym-training")
public class GymTrainingController {
    private GymTrainingService gymTrainingService;

    public GymTrainingController(GymTrainingService gymTrainingService) {
        this.gymTrainingService = gymTrainingService;
    }

    @ApiOperation(value = "Get all gym trainings")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<GymTrainingDto>> findAll(@RequestHeader("Authorization") String authorization, @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(gymTrainingService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<GymTrainingDto> add(@RequestHeader("Authorization") String authorization, @RequestBody @Valid GymTrainingCreateDto gymTrainingCreateDto){
        return new ResponseEntity<>(gymTrainingService.add(gymTrainingCreateDto), HttpStatus.CREATED);
    }
}
