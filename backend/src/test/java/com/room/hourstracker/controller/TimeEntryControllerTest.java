package com.room.hourstracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.exception.NotFoundException;
import com.room.hourstracker.dto.CreateEntryRequest;
import com.room.hourstracker.service.TimeEntryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(TimeEntryController.class)
class TimeEntryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeEntryService timeEntryService;

    @Test
    @DisplayName("POST /api/time-entries returns 400 when validation fails")
    void create_shouldReturn400_whenValidationFails() throws Exception {

        CreateEntryRequest request = new CreateEntryRequest(
                null,
                LocalDate.now(),
                4.0,
                "worked on feature",
                true
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(timeEntryService);
    }

    @Test
    @DisplayName("POST /api/time-entries returns 404 when project does not exist")
    void create_shouldReturn404_whenNotFound() throws Exception {

        CreateEntryRequest request = validRequest();

        when(timeEntryService.create(any(CreateEntryRequest.class)))
                .thenThrow(new NotFoundException("Project not found"));

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isNotFound());

        verify(timeEntryService).create(any(CreateEntryRequest.class));

    }

    @Test
    @DisplayName("POST /api/time-entries returns 200 request created")
    void create_shouldReturn201_whenCreated() throws Exception {

        CreateEntryRequest request = validRequest();

        TimeEntry savedEntry = new TimeEntry();
        savedEntry.setId(1L); // if your entity has id
        savedEntry.setHours(4.0);
        savedEntry.setNotes("worked on feature");
        savedEntry.setBillable(true);

        when(timeEntryService.create(any(CreateEntryRequest.class)))
                .thenReturn(savedEntry);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.hours").value(4.0))
                .andExpect(jsonPath("$.notes").value("worked on feature"));

        verify(timeEntryService).create(any(CreateEntryRequest.class));

    }
    @Test
    @DisplayName("DELETE /time-entries/{id} returns success when entry exists")
    void delete_shouldReturnSuccess_whenEntryExists() throws Exception {

        doNothing().when(timeEntryService).delete(1L);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());

        verify(timeEntryService).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/time-entries/{id} returns 404 when entry does not exist")
    void delete_shouldReturn404_whenEntryDoesNotExist() throws Exception {

        doThrow(new NotFoundException("Entry not found"))
                .when(timeEntryService)
                .delete(1L);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNotFound());

        verify(timeEntryService).delete(1L);
    }

//    helpers
    private CreateEntryRequest validRequest() {
        return new CreateEntryRequest(
                1L,
                LocalDate.now(),
                4.0,
                "worked on feature",
                true
        );
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    private static final String BASE_URL = "/api/time-entries";



}
