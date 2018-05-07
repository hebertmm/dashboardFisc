package io.github.***REMOVED***mm.dash;

import io.github.***REMOVED***mm.dash.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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
    @Autowired
    private RemoteDeviceRepository remoteDeviceRepository;
    private RestTemplate restTemplate;

    @ModelAttribute("allPersons")
    public List<Person> populateMembers(){
        return this.personRepository.findAll();
    }
    @ModelAttribute("allRemotes")
    public List<RemoteDevice> populateRemotes() {return this.remoteDeviceRepository.findAll();}

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
    @GetMapping(path="/addTarget")
    public ModelAndView addTarget(){
        ModelAndView mv = new ModelAndView("/addTarget.html");
        mv.addObject("target", new Target());
        return mv;
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
    @GetMapping(path="/addRemoteDevice")
    public ModelAndView addRemoteDevice(){
        ModelAndView mv = new ModelAndView("addRemoteDevice.html");
        mv.addObject("remoteDevice", new RemoteDevice());
        return mv;
    }
    @PostMapping(path="/addRemoteDevice")
    @ResponseBody
    public String saveRemoteDevice(@Valid RemoteDevice device, BindingResult result){
        if(result.hasErrors())
            return "erro";
        else{
            remoteDeviceRepository.save(device);
            return device.getId();
        }

    }
    @GetMapping(path="/map")
    public ModelAndView showMap(){
        List<Location> loc = new ArrayList<>();
        loc.add(new Location("POINT", "-16.6921","-49.2672"));
        loc.add(new Location("POINT", "-16.5921","-49.2697"));
        loc.add(new Location("POINT", "-16.6921","-48.2677"));
        loc.add(new Location("POINT", "-16.621","-49.1674"));
        loc.add(new Location("POINT", "-16.612","-49.05677"));
        ModelAndView mv = new ModelAndView("map.html");
        mv.addObject("point",loc);
        return mv;
    }
    @GetMapping(path="/markersList")
    @ResponseBody
    public String markersList(){
        return "";
    }
}
