package de.superflow.rest;

import java.util.Comparator;

/**
 * @author Phi Long Tran
 *
 * This is the comparator to sort the highscores descending
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
