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
    private HighscoreSFRepository highscoreSFRepository;

    @Autowired
    private PlayerSFRepository playerSFRepository;

    private String auth = "secret";

    @GetMapping("/getALL")
    public @ResponseBody Iterable<HighscoreSF> getAllDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreSFRepository.findAll();
        }
        return null;
    }

    @GetMapping("/get")
    public @ResponseBody List<HighscoreSF> getDB(@RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<HighscoreSF> returnList = new ArrayList<>();
            Iterable<HighscoreSF> it = highscoreSFRepository.findAll();
            for (HighscoreSF highscoreSF : it) {
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
    public @ResponseBody List<HighscoreSF> getDB(@PathVariable String nickname, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<HighscoreSF> returnList = new ArrayList<>();
            Iterable<HighscoreSF> it = highscoreSFRepository.findAll();
            for (HighscoreSF highscoreSF : it) {
                if (highscoreSF.getNickname().toLowerCase().equals(nickname.toLowerCase()) && highscoreSF.getGameName().toLowerCase().equals("superflow")) {
                    returnList.add(highscoreSF);
                }
            }
            returnList.sort(new MyComparator());
            return returnList;
        } return null;
    }

    @GetMapping("/get/level/{level}")
    public @ResponseBody List<HighscoreSF> getDB(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreSFRepository.findByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    @GetMapping("/get/level/top10/{level}")
    public @ResponseBody List<HighscoreSF> getDBTop10(@PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            return highscoreSFRepository.findTop10ByLevelAndGameNameOrderByPointDesc(level, "superflow");
        }
        return null;
    }

    @GetMapping("/get/player/{nickname}/{level}")
    public @ResponseBody List<HighscoreSF> getDB(@PathVariable String nickname, @PathVariable int level, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            List<HighscoreSF> returnList = new ArrayList<>();
            Iterable<HighscoreSF> it = highscoreSFRepository.findAll();
            for (HighscoreSF highscoreSF : it) {
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
            Iterable<PlayerSF> it = playerSFRepository.findAll();
            for (PlayerSF player : it) {
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
            Iterable<PlayerSF> it = playerSFRepository.findAll();
            for (PlayerSF player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase()) && player.getPassword().equals(password)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @PostMapping("/register")
    public @ResponseBody boolean register(@RequestBody() PlayerSF object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<PlayerSF> it = playerSFRepository.findAll();
            boolean used = false;

            for (PlayerSF player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase())) {
                    used = true;
                }
            }
            if (!used) {
                PlayerSF player = new PlayerSF(object.getNickname(), object.getPassword());
                playerSFRepository.save(player);
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/register/{nickname}/{password}")
    public @ResponseBody boolean register(@PathVariable String nickname, @PathVariable String password, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<PlayerSF> it = playerSFRepository.findAll();
            boolean used = false;

            for (PlayerSF player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase())) {
                    used = true;
                }
            }
            if (!used) {
                PlayerSF player = new PlayerSF(nickname, password);
                playerSFRepository.save(player);
                return true;
            }
            return false;
        }
        return false;
    }

    @PutMapping("/changepw")
    public @ResponseBody boolean changepw(@RequestBody() PlayerSF object, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            Iterable<PlayerSF> it = playerSFRepository.findAll();

            for (PlayerSF player : it) {
                if (player.getNickname().toLowerCase().equals(object.getNickname().toLowerCase())) {
                    player.setPassword(object.getPassword());
                    playerSFRepository.save(player);
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
            Iterable<PlayerSF> it = playerSFRepository.findAll();

            for (PlayerSF player : it) {
                if (player.getNickname().toLowerCase().equals(nickname.toLowerCase())) {
                    player.setPassword(password);
                    playerSFRepository.save(player);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @PostMapping("/set")
    public @ResponseBody List<HighscoreSF> setDB(@RequestBody() HighscoreSF highscore, @RequestHeader String auth) {
        if(this.auth.equals(auth)) {
            HighscoreSF highscoreSF = new HighscoreSF(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Date());
            highscoreSFRepository.save(highscoreSF);

            List<HighscoreSF> returnList = new ArrayList<>();
            Iterable<HighscoreSF> it = highscoreSFRepository.findAll();
            for (HighscoreSF gamerunvar : it) {
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
