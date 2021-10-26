package com.selenophile.server.repository;

import com.selenophile.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Server,Long> {
    Server findByIpAddress(String ipAddress);
}
