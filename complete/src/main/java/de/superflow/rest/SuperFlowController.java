package de.superflow.rest;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by philo on 25.05.2017.
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

    @GetMapping("/getALL")
    public @ResponseBody Iterable<Highscore> getAllDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreRepository.findAll();
        }
        return null;
    }

    @GetMapping("/get")
    public @ResponseBody List<Highscore> getDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Highscore> returnList = new ArrayList<>();
            Iterable<Highscore> it = highscoreRepository.findAll();
            for (Highscore highscoreSF : it) {
                if (highscoreSF.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(highscoreSF);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        }
        return null;
    }

    @GetMapping("/get/player/{nickname}")
    public @ResponseBody List<Highscore> getDB(@PathVariable String nickname, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Highscore> returnList = new ArrayList<>();
            Iterable<Highscore> it = highscoreRepository.findAll();
            for (Highscore highscoreSF : it) {
                if (highscoreSF.getNickname().toLowerCase().equals(nickname.toLowerCase()) && highscoreSF.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(highscoreSF);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        } return null;
    }

    @GetMapping("/get/level/{level}")
    public @ResponseBody List<Highscore> getDB(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreRepository.findByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    @GetMapping("/get/level/top10/{level}")
    public @ResponseBody List<Highscore> getDBTop10(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreRepository.findTop10ByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    @GetMapping("/get/player/{nickname}/{level}")
    public @ResponseBody List<Highscore> getDB(@PathVariable String nickname, @PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Highscore> returnList = new ArrayList<>();
            Iterable<Highscore> it = highscoreRepository.findAll();
            for (Highscore highscoreSF : it) {
                if (highscoreSF.getNickname().toLowerCase().equals(nickname.toLowerCase()) && highscoreSF.getLevel() == level && highscoreSF.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(highscoreSF);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        }
        return null;
    }

    @PostMapping("/login")
    public @ResponseBody boolean login(@RequestBody() PlayerSF object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();
            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase()) && encoder.matches(object.getPassword(), player.getPassword())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

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

    @PostMapping("/register")
    public @ResponseBody boolean register(@RequestBody() Player object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();
            boolean used = false;

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase())) {
                    used = true;
                }
            }
            if (!used) {
                Player player = new Player(object.getNickname(), encoder.encode(object.getPassword()));
                playerRepository.save(player);
                return true;
            }
            return false;
        }
        return false;
    }

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

    @PutMapping("/changepw")
    public @ResponseBody boolean changepw(@RequestBody() PlayerSF object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase())) {
                    player.setPassword(encoder.encode(object.getPassword()));
                    playerRepository.save(player);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @PutMapping("/changepw/{nickname}/{password}")
    public @ResponseBody boolean changepw(@PathVariable String nickname, @PathVariable String password, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase())) {
                    player.setPassword(encoder.encode(password));
                    playerRepository.save(player);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @PostMapping("/set")
    public @ResponseBody List<Highscore> setDB(@RequestBody() Highscore highscore, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Highscore highscoreSF = new Highscore(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Timestamp(new java.util.Date().getTime()));
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

            return highscoreRepository.findByLevelAndGameNameOrderByPointDesc(highscore.getLevel(), "superflow");
        }
        return null;
    }
}
