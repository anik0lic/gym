package raf.gymnotificationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.gymnotificationservice.dto.NotificationDto;
import raf.gymnotificationservice.security.CheckSecurity;
import raf.gymnotificationservice.service.NotificationService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/admin")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(notificationService.getAllNotificationsForAdmin(pageable), HttpStatus.OK);
    }
}
