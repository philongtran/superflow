package de.superflow.rest;

import java.util.Comparator;

/**
 * Created by philo on 25.05.2017.
 */
public class MyComparator implements Comparator<HighscoreSF>{
    @Override
    public int compare(HighscoreSF o1, HighscoreSF o2) {
        if (o1.getPoint() > o2.getPoint()) {
            return -1;
        } else if (o1.getPoint() < o2.getPoint()) {
            return 1;
        }
        return 0;
    }
}
