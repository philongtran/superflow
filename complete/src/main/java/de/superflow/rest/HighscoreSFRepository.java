package de.superflow.rest;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by philo on 25.05.2017.
 */
public interface GamerunRepository extends CrudRepository<HighscoreSF, Long> {
}
