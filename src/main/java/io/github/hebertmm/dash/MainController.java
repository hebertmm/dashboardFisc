package io.github.***REMOVED***mm.dash;

import io.github.***REMOVED***mm.dash.domain.Person;
import io.github.***REMOVED***mm.dash.domain.PersonRepository;
import io.github.***REMOVED***mm.dash.domain.Team;
import io.github.***REMOVED***mm.dash.domain.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
public class MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;

    @ModelAttribute("allPersons")
    public List<Person> populateMembers(){
        return this.personRepository.findAll();
    }

    @GetMapping(path="/add")
    public ModelAndView addPerson(){
        ModelAndView mv = new ModelAndView("/index.html");
        mv.addObject("person", new Person());
        return mv;
    }
    @GetMapping(path="/addTeam")
    public ModelAndView addTeam(){
        ModelAndView mv = new ModelAndView("/addTeam.html");
        mv.addObject("persons", personRepository.findAll());
        mv.addObject("team", new Team());
        return mv;
    }
    @PostMapping(path="/addTeam", params = {"addMember"})
    public String addMember(Team team, BindingResult result){
        team.addMember(new Person());
        log.info("Add Member---");
        return "addTeam";
    }
    @PostMapping(path="/addTeam", params = {"removeMember"})
    public String removeMember(Team team, BindingResult bindingResult, HttpServletRequest req){
        Integer index = Integer.valueOf(req.getParameter("removeMember"));
        log.info(String.valueOf(index));
        team.removeMember(index.intValue());
        return "addTeam";
    }


    @PostMapping(path="/addTeam", params = {"saveTeam"})
    @ResponseBody
    public String saveTeam(@Valid Team team, BindingResult result){
        if(result.hasErrors())
            return "erro";
        else{
            teamRepository.save(team);
            return team.getId();
        }

    }
    @PostMapping(path="/person")
    @ResponseBody
    public String savePerson(@Valid Person person, BindingResult result){
        if(result.hasErrors())
            return "Erro";
        else{
            personRepository.save(person);
            return person.firstName;
        }
    }
    @GetMapping(path="/find")
    @ResponseBody
    public String findPerson(@RequestParam("lastName") String lastName){
        return personRepository.findByFirstName(lastName).firstName;
    }
    @GetMapping(path="/map")
    public ModelAndView showMap(){
        Map<String, Double> loc = new HashMap<>();
        loc.put("lat", (-16.2));
        loc.put("lon",(-49.1));
        ModelAndView mv = new ModelAndView("map.html");
        mv.addObject("point",loc);
        return mv;
    }
}
