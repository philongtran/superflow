package de.superflow.rest;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by philo on 25.05.2017.
 */
@RestController
@RequestMapping("/superflow")
public class SuperFlowController {

    @Autowired
    private GamerunRepository gamerunRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private String auth = "secret";

    @GetMapping("/getALL")
    public @ResponseBody Iterable<Gamerun> getAllDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return gamerunRepository.findAll();
        }
        return null;
    }

    @GetMapping("/get")
    public @ResponseBody List<Gamerun> getDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Gamerun> returnList = new ArrayList<>();
            Iterable<Gamerun> it = gamerunRepository.findAll();
            for (Gamerun gamerun : it) {
                if (gamerun.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerun);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        }
        return null;
    }

    @GetMapping("/get/player/{nickname}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable String nickname, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Gamerun> returnList = new ArrayList<>();
            Iterable<Gamerun> it = gamerunRepository.findAll();
            for (Gamerun gamerun : it) {
                if (gamerun.getNickname().toLowerCase().equals(nickname.toLowerCase()) && gamerun.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerun);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        } return null;
    }

    @GetMapping("/get/level/{level}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Gamerun> returnList = new ArrayList<>();
            Iterable<Gamerun> it = gamerunRepository.findAll();
            for (Gamerun gamerun : it) {
                if (gamerun.getLevel() == level && gamerun.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerun);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        }
        return null;
    }

    @GetMapping("/get/player/{nickname}/{level}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable String nickname, @PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<Gamerun> returnList = new ArrayList<>();
            Iterable<Gamerun> it = gamerunRepository.findAll();
            for (Gamerun gamerun : it) {
                if (gamerun.getNickname().toLowerCase().equals(nickname.toLowerCase()) && gamerun.getLevel() == level && gamerun.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerun);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        }
        return null;
    }

    @PostMapping("/login")
    public @ResponseBody boolean login(@RequestBody() Player object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();
            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase()) && player.getPassword().equals(object.getPassword())) {
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
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase()) && player.getPassword().equals(password)) {
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
                Player player = new Player(object.getNickname(), object.getPassword());
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
                Player player = new Player(nickname, password);
                playerRepository.save(player);
                return true;
            }
            return false;
        }
        return false;
    }

    @PutMapping("/changepw")
    public @ResponseBody boolean changepw(@RequestBody() Player object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<Player> it = playerRepository.findAll();

            for (Player player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase())) {
                    player.setPassword(object.getPassword());
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
                    player.setPassword(password);
                    playerRepository.save(player);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @PostMapping("/set")
    public @ResponseBody List<Gamerun> setDB(@RequestBody() Gamerun highscore, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Gamerun gamerun = new Gamerun(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Date());
            gamerunRepository.save(gamerun);

            List<Gamerun> returnList = new ArrayList<>();
            Iterable<Gamerun> it = gamerunRepository.findAll();
            for (Gamerun gamerunvar : it) {
                if (gamerunvar.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(gamerunvar);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;

            //return gamerunRepository.findAll();
        }
        return null;
    }
}
