package de.superflow.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/superflow")
public class SuperFlowController {

    @Autowired
    private GamerunRepository gamerunRepository;

    @GetMapping("/getALL")
    public @ResponseBody Iterable<Gamerun> getAllDB() {
        return gamerunRepository.findAll();
    }

    @GetMapping("/get")
    public @ResponseBody List<Gamerun> getDB() {
        List<Gamerun> returnList = new ArrayList<>();
        Iterable<Gamerun> it = gamerunRepository.findAll();
        for (Gamerun gamerun : it) {
            if(gamerun.getGameName().toLowerCase().equals("superflow")) {
                returnList.add(gamerun);
            }
        }
        return returnList;
    }

    @GetMapping("/get/player/{nickname}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable String nickname) {
        List<Gamerun> returnList = new ArrayList<>();
        Iterable<Gamerun> it = gamerunRepository.findAll();
        for (Gamerun gamerun : it) {
            if(gamerun.getNickname().toLowerCase().equals(nickname.toLowerCase()) && gamerun.getGameName().toLowerCase().equals("superflow")) {
                returnList.add(gamerun);
            }
        }
        return returnList;
    }

    @GetMapping("/get/level/{level}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable int level) {
        List<Gamerun> returnList = new ArrayList<>();
        Iterable<Gamerun> it = gamerunRepository.findAll();
        for (Gamerun gamerun : it) {
            if(gamerun.getLevel() == level && gamerun.getGameName().toLowerCase().equals("superflow")) {
                returnList.add(gamerun);
            }
        }
        return returnList;
    }

    @GetMapping("/get/player/{nickname}/{level}")
    public @ResponseBody List<Gamerun> getDB(@PathVariable String nickname, @PathVariable int level) {
        List<Gamerun> returnList = new ArrayList<>();
        Iterable<Gamerun> it = gamerunRepository.findAll();
        for (Gamerun gamerun : it) {
            if(gamerun.getNickname().toLowerCase().equals(nickname.toLowerCase()) && gamerun.getLevel() == level && gamerun.getGameName().toLowerCase().equals("superflow")) {
                returnList.add(gamerun);
            }
        }
        return returnList;
    }

    @PostMapping("/set")
    public @ResponseBody Iterable<Gamerun> setDB(@RequestBody() Gamerun highscore) {
        Gamerun gamerun = new Gamerun(highscore.getNickname(), highscore.getPoint(), highscore.getLevel(), new Date());
        gamerunRepository.save(gamerun);
        return gamerunRepository.findAll();
    }
}
