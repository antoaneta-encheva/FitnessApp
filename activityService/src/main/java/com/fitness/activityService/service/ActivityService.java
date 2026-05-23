package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequestDTO;
import com.fitness.activityService.dto.ActivityResponseDTO;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    public ActivityResponseDTO trackActivity(ActivityRequestDTO activityRequestDTO) {
        Activity activity = Activity.builder()
                .userId(activityRequestDTO.getUserId())
                .activityType(activityRequestDTO.getActivityType())
                .caloriesBurned(activityRequestDTO.getCaloriesBurned())
                .startTime(activityRequestDTO.getStartTime())
                .duration(activityRequestDTO.getDuration())
                .additionalMetrics(activityRequestDTO.getAdditionalMetrics()).build();

        Activity savedActivity = activityRepository.save(activity);

       return this.mapToResponse(savedActivity);
    }

    private ActivityResponseDTO mapToResponse(Activity activity){
        ActivityResponseDTO responseDTO = new ActivityResponseDTO();
        responseDTO.setId(activity.getId());
        responseDTO.setUserId(activity.getUserId());
        responseDTO.setActivityType(activity.getActivityType());
        responseDTO.setDuration(activity.getDuration());
        responseDTO.setCaloriesBurned(activity.getCaloriesBurned());
        responseDTO.setStartTime(activity.getStartTime());
        responseDTO.setAdditionalMetrics(activity.getAdditionalMetrics());
        responseDTO.setCreatedAt(activity.getCreatedAt());
        responseDTO.setUpdatedAt(activity.getUpdatedAt());
        return responseDTO;
    }

    public List<ActivityResponseDTO> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findAllByUserId(userId);

        if(activities.isEmpty()){
           return new ArrayList<>();
        }

        return activities.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ActivityResponseDTO getActivityById(String id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.map(this::mapToResponse).orElseThrow(() -> new RuntimeException("The Activity not Found!"));
    }
}
