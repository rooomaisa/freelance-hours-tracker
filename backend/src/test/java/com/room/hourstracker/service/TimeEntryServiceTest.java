package com.room.hourstracker.service;

import com.room.hourstracker.domain.Project;
import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.dto.CreateEntryRequest;
import com.room.hourstracker.repository.ProjectRepository;
import com.room.hourstracker.repository.TimeEntryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class TimeEntryServiceTest {

    @Mock
    private TimeEntryRepository entries;

    @Mock
    private ProjectRepository projects;

    @InjectMocks
    private TimeEntryService service;

    public TimeEntryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveEntry_whenProjectExists() {

        // Arrange
        Project project = new Project();
        project.setId(1L);

        CreateEntryRequest request = new CreateEntryRequest(
                1L,
                LocalDate.now(),
                5.0,
                "Worked on feature",
                true
        );

        when(projects.findById(1L)).thenReturn(Optional.of(project));

        // Act
        service.create(request);

        // Assert
        ArgumentCaptor<TimeEntry> captor = ArgumentCaptor.forClass(TimeEntry.class);
        verify(entries).save(captor.capture());

        TimeEntry saved = captor.getValue();
        assertEquals(5.0, saved.getHours());
        assertEquals("Worked on feature", saved.getNotes());
        assertEquals(true, saved.isBillable());
        assertEquals(project, saved.getProject());
        assertEquals(request.date(), saved.getDate());
    }

    @Test
    void create_shouldThrowNotFound_whenProjectDoesNotExist() {

        // Arrange
        CreateEntryRequest request = new CreateEntryRequest(
                999L,
                LocalDate.now(),
                2.0,
                "test",
                true
        );

        when(projects.findById(999L)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.create(request)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(entries, never()).save(any());
    }


    @Test
    void create_shouldSaveEntry_whenBillableIsFalse() {

        // Arrange
        Project project = new Project();
        project.setId(1L);

        CreateEntryRequest request = new CreateEntryRequest(
                1L,
                LocalDate.now(),
                5.0,
                "Worked on feature",
                false
        );

        when(projects.findById(1L)).thenReturn(Optional.of(project));

        // Act
        service.create(request);

        // Assert
        ArgumentCaptor<TimeEntry> captor = ArgumentCaptor.forClass(TimeEntry.class);
        verify(entries).save(captor.capture());

        TimeEntry saved = captor.getValue();
        assertEquals(5.0, saved.getHours());
        assertEquals("Worked on feature", saved.getNotes());
        assertFalse(saved.isBillable());
        assertEquals(project, saved.getProject());
        assertEquals(request.date(), saved.getDate());


    }

    @Test
    void delete_shouldRemoveEntry_whenEntryExists() {
        // Arrange
        when(entries.existsById(1L)).thenReturn(true);
        // Act
        service.delete(1L);
        // Assert
        verify(entries).deleteById(1L);
    }

    @Test
    void delete_shouldThrowNotFound_whenEntryDoesNotExist() {

        // Arrange
        when(entries.existsById(1L)).thenReturn(false);

        // Act + Assert
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> service.delete(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());

        verify(entries, never()).deleteById(any());
    }


}