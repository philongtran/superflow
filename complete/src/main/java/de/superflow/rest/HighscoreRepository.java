package de.superflow.rest;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Long on 16.06.2017.
 */
public interface HighscoreRepository extends CrudRepository<Highscore, Long> {
    public List<Highscore> findByLevelOrderByPointDesc(int level);
    public List<Highscore> findByLevelAndGameNameOrderByPointDesc(int level, String gamename);
    public List<Highscore> findTop10ByLevelAndGameNameOrderByPointDesc(int level, String gamename);
}
