package de.superflow.rest;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Long on 16.06.2017.
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {
    public List<Player> findById(String uuid);
    public List<Player> findByNickname(String nickname);
}
