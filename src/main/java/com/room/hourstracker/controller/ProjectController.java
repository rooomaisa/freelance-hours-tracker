package com.room.hourstracker.controller;

import com.room.hourstracker.domain.Client;
import com.room.hourstracker.domain.Project;
import com.room.hourstracker.repository.ClientRepository;
import com.room.hourstracker.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    private final ProjectRepository projects;
    private final ClientRepository clients;

    public ProjectController(ProjectRepository projects, ClientRepository clients) {
        this.projects = projects;
        this.clients = clients;
    }

    // GET all projects
    @GetMapping("/projects")
    public List<Project> getAll() {
        return projects.findAll();
    }

    // GET projects by client
    @GetMapping("/clients/{clientId}/projects")
    public List<Project> getByClient(@PathVariable Long clientId) {
        return projects.findByClientId(clientId);
    }

    // Create a project (expects clientId)
    @PostMapping("/projects")
    public Project create(@RequestBody CreateProjectRequest body) {
        Client client = clients.findById(body.clientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        Project p = new Project();
        p.setClient(client);
        p.setName(body.name());
        p.setActive(body.active() != null ? body.active() : true);
        return projects.save(p);
    }

    // Delete project
    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        projects.deleteById(id);
    }

    // simple request payload
    public record CreateProjectRequest(Long clientId, String name, Boolean active) {}
}
