package de.superflow.rest;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Phi Long Tran
 *
 * This class creates the sql commands for the player table
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {
    public List<Player> findById(String uuid);
    public List<Player> findByNickname(String nickname);
}
