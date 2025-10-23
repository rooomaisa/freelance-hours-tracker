package com.room.hourstracker.controller;

import com.room.hourstracker.domain.Client;
import com.room.hourstracker.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {

    private final ClientRepository repo;

    public ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Client> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Client add(@RequestBody Client client) {
        return repo.save(client);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
