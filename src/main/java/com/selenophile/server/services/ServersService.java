package com.selenophile.server.services;

import com.selenophile.server.model.Server;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface ServersService {
    Server create(Server server);
    Server ping(String ipAddress) throws IOException;
    Collection<Server> listServers(int limit);
    Server get(Long id);
    Server update(Server server);
    Boolean delete(Server server);
}
