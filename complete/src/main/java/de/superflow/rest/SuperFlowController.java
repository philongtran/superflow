package de.superflow.rest;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Phi Long Tran
 *
 * this class represents the rest controller
 */
@RestController
@RequestMapping("/superflow")
public class SuperFlowController {

    @Autowired
    private HighscoreRepository highscoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private String auth = "secret";
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * This method returns the highscore of a specific level
     * 
     * @param level
     * @param auth
     * @return
     */
    @GetMapping("/get/level/{level}")
    public @ResponseBody List<HighscoreSend> getDB(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {

            List<HighscoreSend> highscoreSends = new ArrayList<>();
            List<Highscore> highscores = highscoreRepository.findTop100ByLevelAndGameNameOrderByPointDesc(level, "superflow");
            for (Highscore highscore : highscores) {
                HighscoreSend highscoreSend = new HighscoreSend(getNickname(highscore.getNickname()), highscore.getPoint(), highscore.getLevel(), highscore.getDate());
                //highscoreSends.add(new HighscoreSend(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Timestamp(new java.util.Date().getTime())));
                highscoreSends.add(highscoreSend);
            }
            //return highscoreRepository.findTop10ByLevelAndGameNameOrderByPointDesc(level, "superflow");
            return highscoreSends;

            //return highscoreRepository.findByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    /**
     * This method returns the top 10 of a specific level
     *
     * @param level
     * @param auth
     * @return
     */
    @GetMapping("/get/level/top10/{level}")
    public @ResponseBody List<HighscoreSend> getDBTop10(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<HighscoreSend> highscoreSends = new ArrayList<>();
            List<Highscore> highscores = highscoreRepository.findTop10ByLevelAndGameNameOrderByPointDesc(level, "superflow");
            for (Highscore highscore : highscores) {
                HighscoreSend highscoreSend = new HighscoreSend(getNickname(highscore.getNickname()), highscore.getPoint(), highscore.getLevel(), highscore.getDate());
                //highscoreSends.add(new HighscoreSend(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Timestamp(new java.util.Date().getTime())));
                highscoreSends.add(highscoreSend);
            }
            //return highscoreRepository.findTop10ByLevelAndGameNameOrderByPointDesc(level, "superflow");
            return highscoreSends;
        }
        return null;
    }


    /**
     * This method is used for player login
     *
     * @param nickname
     * @param password
     * @param auth
     * @return
     */
    @PostMapping("/login/{nickname}/{password}")
    public @ResponseBody boolean login(@PathVariable String nickname, @PathVariable String password, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();
            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase()) && encoder.matches(password, player.getPassword())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    /**
     * use this
     *
     * @param nickname
     * @param password
     * @param auth
     * @return
     */
    @PostMapping("/register/{nickname}/{password}")
    public @ResponseBody boolean register(@PathVariable String nickname, @PathVariable String password, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();
            boolean used = false;

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase())) {
                    used = true;
                }
            }
            if (!used) {
                Player player = new Player(nickname, encoder.encode(password));
                playerRepository.save(player);
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     * This method is used to change the users password
     *
     * @param nickname
     * @param password
     * @param auth
     * @return
     */
    @PutMapping("/changepw/{nickname}/{password}")
    public @ResponseBody boolean changepw(@PathVariable String nickname, @PathVariable String password, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Player> players = playerRepository.findByNickname(nickname);
            players.get(0).setPassword(encoder.encode(password));
            playerRepository.save(players.get(0));
            return true;
            /*Iterable<Player> it = playerRepository.findAll();

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase())) {
                    player.setPassword(encoder.encode(password));
                    playerRepository.save(player);
                    return true;
                }
            }
            return false;
        }
        return false;*/
        }
        return false;
    }

    /**
     * This method is used to register a new score via body
     *
     * @param nickname
     * @param highscore
     * @param auth
     * @return
     */
    @PostMapping("/set/{nickname}")
    public @ResponseBody List<Highscore> setDB(@PathVariable String nickname, @RequestBody() Highscore highscore, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Highscore highscoreSF = new Highscore(getId(nickname), highscore.getPoint(), highscore.getLevel(), new Timestamp(new java.util.Date().getTime()));
            //highscoreSF.setPlayerid(getId(highscore.getNickname()));
            highscoreRepository.save(highscoreSF);

            /*
            List<Highscore> returnList = new ArrayList<>();
            Iterable<Highscore> it = highscoreRepository.findAll();
            for (Highscore gamerunvar : it) {
                if (gamerunvar.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerunvar);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;*/

            return highscoreRepository.findTop100ByLevelAndGameNameOrderByPointDesc(highscore.getLevel(), "superflow");
        }
        return null;
    }

    /**
     * This method is used to register a new score
     *
     * @param nickname
     * @param level
     * @param point
     * @param auth
     * @return
     */
    @PostMapping("/set/{nickname}/{level}/{point}")
    public @ResponseBody List<Highscore> setDB2(@PathVariable String nickname, @PathVariable int level, @PathVariable int point, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Highscore highscoreSF = new Highscore(getId(nickname), point, level, new Timestamp(new java.util.Date().getTime()));
            //highscoreSF.setPlayerid(getId(highscore.getNickname()));
            highscoreRepository.save(highscoreSF);

            /*
            List<Highscore> returnList = new ArrayList<>();
            Iterable<Highscore> it = highscoreRepository.findAll();
            for (Highscore gamerunvar : it) {
                if (gamerunvar.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerunvar);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;*/

            return highscoreRepository.findTop100ByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    /**
     * This method returns a nickname if only id is known
     *
     * @param uuid
     * @return
     */
    private String getNickname(String uuid) {
        List<Player> player = playerRepository.findById(uuid);
        return player.get(0).getNickname();
    }

    /**
     * This method gets the id if nickname is known
     *
     * @param nickname
     * @return
     */
    private String getId(String nickname) {
        List<Player> player = playerRepository.findByNickname(nickname);
        return player.get(0).getID();
    }
}
