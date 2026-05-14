package com.fitness.activityService.controller;

import com.fitness.activityService.dto.ActivityRequestDTO;
import com.fitness.activityService.dto.ActivityResponseDTO;
import com.fitness.activityService.service.ActivityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@Tag(name = "Activity service", description = "APIs for managing activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> trackActivity(@RequestBody ActivityRequestDTO activityRequestDTO ){
        return ResponseEntity.ok(activityService.trackActivity(activityRequestDTO));
    }
}
