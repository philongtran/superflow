package de.superflow.rest;

import java.util.Comparator;

/**
 * Created by philo on 25.05.2017.
 */
public class MyComparator implements Comparator<Highscore>{
    @Override
    public int compare(Highscore o1, Highscore o2) {
        if (o1.getPoint() > o2.getPoint()) {
            return -1;
        } else if (o1.getPoint() < o2.getPoint()) {
            return 1;
        }
        return 0;
    }
}
