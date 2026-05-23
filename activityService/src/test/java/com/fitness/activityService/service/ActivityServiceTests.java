package com.fitness.activityService.service;

import com.fitness.activityService.dto.ActivityRequestDTO;
import com.fitness.activityService.dto.ActivityResponseDTO;
import com.fitness.activityService.model.Activity;
import com.fitness.activityService.model.ActivityType;
import com.fitness.activityService.repository.ActivityRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ActivityServiceTests {

    private final String USER_ID = "test_userId";
    private final ActivityType ACTIVITY_TYPE = ActivityType.CARDIO;
    private final Integer CALORIESBURNED = 500;
    private final Integer DURATION = 50;
    private final LocalDateTime STARTTIME = LocalDateTime.parse("2026-05-23T13:32:25.744");
    private static Map<String, Object> ADDITIONALMETRICS = new HashMap<>();

    @Autowired
    private ActivityService activityService;

    @MockitoBean
    private ActivityRepository activityRepository;

    @BeforeAll
    public  static void setup(){
        ADDITIONALMETRICS.put("String", "test");
        ADDITIONALMETRICS.put("integer", 1);
        ADDITIONALMETRICS.put("Boolean", true);
    }

    @Test
    public void testTrackActivity(){
        // given
        ActivityRequestDTO activityRequestDTO = new ActivityRequestDTO();
        activityRequestDTO.setUserId(USER_ID);
        activityRequestDTO.setActivityType(ACTIVITY_TYPE);
        activityRequestDTO.setCaloriesBurned(CALORIESBURNED);
        activityRequestDTO.setDuration(DURATION);
        activityRequestDTO.setAdditionalMetrics(ADDITIONALMETRICS);
        activityRequestDTO.setStartTime(STARTTIME);

        Activity activity = Activity.builder()
                .id("test_id")
                .userId(USER_ID)
                .activityType(ACTIVITY_TYPE)
                .caloriesBurned(CALORIESBURNED)
                .duration(DURATION)
                .additionalMetrics(ADDITIONALMETRICS)
                .startTime(STARTTIME)
                .createdAt(STARTTIME)
                .updatedAt(STARTTIME).build();

        when(activityRepository.save(any())).thenReturn(activity);

        ActivityResponseDTO responseDTO = activityService.trackActivity(activityRequestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isNotNull();
        assertThat(responseDTO.getCreatedAt()).isNotNull();
        assertThat(responseDTO.getUpdatedAt()).isNotNull();
        assertThat(responseDTO.getAdditionalMetrics()).isEqualTo(ADDITIONALMETRICS);
        assertThat(responseDTO.getUserId()).isEqualTo(USER_ID);
        assertThat(responseDTO.getDuration()).isEqualTo(DURATION);
        assertThat(responseDTO.getStartTime()).isEqualTo(STARTTIME);
        assertThat(responseDTO.getCaloriesBurned()).isEqualTo(CALORIESBURNED);
        assertThat(responseDTO.getActivityType()).isEqualTo(ACTIVITY_TYPE);

        verify(activityRepository).save(any());
    }

    @Test
    public void testFindAllByUserId(){
        List<Activity> expectedActivities = Arrays.asList(Activity.builder()
                .id("test_id1")
                .userId(USER_ID)
                .activityType(ACTIVITY_TYPE)
                .caloriesBurned(CALORIESBURNED)
                .duration(DURATION)
                .additionalMetrics(ADDITIONALMETRICS)
                .startTime(STARTTIME)
                .createdAt(STARTTIME)
                .updatedAt(STARTTIME).build(),
                Activity.builder()
                        .id("test_id2")
                        .userId(USER_ID)
                        .activityType(ACTIVITY_TYPE)
                        .caloriesBurned(CALORIESBURNED)
                        .duration(DURATION)
                        .additionalMetrics(ADDITIONALMETRICS)
                        .startTime(STARTTIME)
                        .createdAt(STARTTIME)
                        .updatedAt(STARTTIME).build());

        doReturn(expectedActivities).when(activityRepository).findAllByUserId(USER_ID);

        List<ActivityResponseDTO> activityResponseDTOS = activityService.getUserActivities(USER_ID);

        assertThat(activityResponseDTOS).isNotNull();
        assertThat(activityResponseDTOS.size()).isEqualTo(expectedActivities.size());
        verify(activityRepository).findAllByUserId(USER_ID);
    }

    @Test
    public void testFindById(){
        Optional<Activity> activity = Optional.ofNullable(Activity.builder()
                .id("test_id")
                .userId(USER_ID)
                .activityType(ACTIVITY_TYPE)
                .caloriesBurned(CALORIESBURNED)
                .duration(DURATION)
                .additionalMetrics(ADDITIONALMETRICS)
                .startTime(STARTTIME)
                .createdAt(STARTTIME)
                .updatedAt(STARTTIME).build());

        doReturn(activity).when(activityRepository).findById("test_id");

        ActivityResponseDTO activityResponseDTO = activityService.getActivityById("test_id");

        assertThat(activityResponseDTO).isNotNull();
        assertThat(activityResponseDTO.getId()).isEqualTo("test_id");
        assertThat(activityResponseDTO.getCreatedAt()).isNotNull();
        assertThat(activityResponseDTO.getUpdatedAt()).isNotNull();
        assertThat(activityResponseDTO.getAdditionalMetrics()).isEqualTo(ADDITIONALMETRICS);
        assertThat(activityResponseDTO.getUserId()).isEqualTo(USER_ID);
        assertThat(activityResponseDTO.getDuration()).isEqualTo(DURATION);
        assertThat(activityResponseDTO.getStartTime()).isEqualTo(STARTTIME);
        assertThat(activityResponseDTO.getCaloriesBurned()).isEqualTo(CALORIESBURNED);
        assertThat(activityResponseDTO.getActivityType()).isEqualTo(ACTIVITY_TYPE);
        verify(activityRepository).findById("test_id");
    }
}
