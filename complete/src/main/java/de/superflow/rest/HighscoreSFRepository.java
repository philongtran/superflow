package de.superflow.rest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by philo on 25.05.2017.
 */
public interface HighscoreSFRepository extends CrudRepository<HighscoreSF, Long> {

    public List<HighscoreSF> findByLevelOrderByPointDesc(int level);
    public List<HighscoreSF> findByLevelAndGameNameOrderByPointDesc(int level, String gamename);
    public List<HighscoreSF> findTop10ByLevelAndGameNameOrderByPointDesc(int level, String gamename);
}
