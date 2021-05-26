package com.mamda.tp.web;

import java.util.List;
import java.util.Optional;

import com.mamda.tp.model.Client;
import com.mamda.tp.repositories.ClientRepos;
import com.mamda.tp.requestmodels.ClientRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/private/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepos clientRepos;

    @PostMapping("/")
    public Client addClient(@RequestBody ClientRequest clientRequest) {
        Client client = new Client();
        client.setAdresse(clientRequest.getAdresse());
        client.setCin(clientRequest.getCin());
        client.setName(clientRequest.getName());
        return clientRepos.save(client);
    }

    @GetMapping("/")
    public List<Client> getAllClients() {
        return clientRepos.findAll();
    }

    @PutMapping(value = "/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody ClientRequest clientRequest) {
        Optional<Client> clientOpt = clientRepos.findById(new Integer(id));
        if (!clientOpt.isPresent()) {
            throw new RuntimeException("No Client with the given id");
        }
        Client client = clientOpt.get();
        client.setAdresse(clientRequest.getAdresse());
        client.setCin(clientRequest.getCin());
        client.setName(clientRequest.getName());
        return clientRepos.save(client);
    }

    @DeleteMapping(value = "/{id}")
    public Client deleteClient(@PathVariable String id) {
        Optional<Client> clientOpt = clientRepos.findById(new Integer(id));
        if (!clientOpt.isPresent()) {
            throw new RuntimeException("No Client with the given id");
        }
        clientRepos.delete(clientOpt.get());
        return clientOpt.get();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable String id) {
        Optional<Client> clientOpt = clientRepos.findById(new Integer(id));
        if (!clientOpt.isPresent()) {
            throw new RuntimeException("No Client with the given id");
        }
        return clientOpt.get();
    }
}