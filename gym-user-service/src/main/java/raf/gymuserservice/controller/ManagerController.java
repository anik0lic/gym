package raf.gymuserservice.controller;

import io.swagger.annotations.ApiOperation;
import raf.gymuserservice.dto.*;
import raf.gymuserservice.security.CheckSecurity;
import raf.gymuserservice.service.ManagerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<UserDto>> getAllManagers(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(managerService.findAllManagers(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Register manager")
    @PostMapping
    public ResponseEntity<ManagerDto> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

}
