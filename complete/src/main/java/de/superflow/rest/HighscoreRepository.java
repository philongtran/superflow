package de.superflow.rest;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Phi Long Tran
 *
 * This class creates the sql commands for the db server
 */
public interface HighscoreRepository extends CrudRepository<Highscore, Long> {
    public List<Highscore> findByLevelOrderByPointDesc(int level);
    public List<Highscore> findTop100ByLevelAndGameNameOrderByPointDesc(int level, String gamename);
    public List<Highscore> findTop10ByLevelAndGameNameOrderByPointDesc(int level, String gamename);
}
