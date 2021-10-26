package com.selenophile.server.services;

import com.selenophile.server.enumeration.Status;
import com.selenophile.server.model.Server;
import com.selenophile.server.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import static com.selenophile.server.enumeration.Status.SERVER_DOWN;
import static com.selenophile.server.enumeration.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServersServiceImpl implements ServersService{
    private Repository repository;
    @Override
    public Server create(Server server) {
        log.debug("CREATING NEW SERVER : {}", server.getName());
        log.info("CREATING NEW SERVER : {}", server.getName());
        server.setImageURL(setImageURL());
        return repository.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("PINGING NEW SERVER : {}", ipAddress);
        Server server = repository.findByIpAddress(ipAddress);
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        server.setStatus(inetAddress.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        repository.save(server);
        return server;
    }

    @Override
    public Collection<Server> listServers(int limit) {
        log.info("RETURNING A LIST OF SERVERS : {}");
        return repository.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("RETURNING A SERVER : {}", id);
        return repository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("UPDATING A SERVER : {}", server);
        return repository.save(server);
    }

    @Override
    public Boolean delete(Server server) {
        log.info("DELETING A SERVER : {}", server);
        repository.deleteById(server.getId());
        return true;
    }
    private String setImageURL() {
        log.info("SETTING THE IMAGE URL : {}");
        String[] imagesName = {"server1.png", "server2.png", "server3.png", "server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/img/" + imagesName[new Random().nextInt(4)]).toUriString();
    }
}
