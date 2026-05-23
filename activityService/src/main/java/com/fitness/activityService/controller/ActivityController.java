package com.fitness.activityService.controller;

import com.fitness.activityService.dto.ActivityRequestDTO;
import com.fitness.activityService.dto.ActivityResponseDTO;
import com.fitness.activityService.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@Tag(name = "Activity service", description = "APIs for managing activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Create a new activity", description = "Add a new activity to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity created successfully",
                    content = @Content(schema = @Schema(implementation = ActivityResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<ActivityResponseDTO> trackActivity(@RequestBody ActivityRequestDTO activityRequestDTO ){
        return ResponseEntity.ok(activityService.trackActivity(activityRequestDTO));
    }

    @Operation(summary = "Get activities by UserId", description = "Get All of the activities tracked by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activities get successfully",
                    content = @Content(schema = @Schema(implementation = ActivityResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<ActivityResponseDTO>>  getUserActivities(@PathVariable String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @Operation(summary = "Get activity by id", description = "Get activity by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity get successfully",
                    content = @Content(schema = @Schema(implementation = ActivityResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}/activity")
    public ResponseEntity<ActivityResponseDTO>  getActivityById(@PathVariable String id){
        return ResponseEntity.ok(activityService.getActivityById(id));
    }
}
