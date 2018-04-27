package io.github.***REMOVED***mm.dash;

import io.github.***REMOVED***mm.dash.domain.Person;
import io.github.***REMOVED***mm.dash.domain.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

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
