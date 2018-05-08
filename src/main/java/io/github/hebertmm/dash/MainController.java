package io.github.***REMOVED***mm.dash;

import io.github.***REMOVED***mm.dash.domain.*;
import io.github.***REMOVED***mm.dash.message.CcsOutMessage;
import io.github.***REMOVED***mm.dash.message.MessageMapper;
import io.github.***REMOVED***mm.dash.util.geoutils.Geoutils;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.gcm.packet.GcmPacketExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
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
    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    @Qualifier("xmppOutbond")
    private MessageChannel channel;


    private RestTemplate restTemplate;

    @ModelAttribute("allPersons")
    public Iterable<Person> populateMembers(){
        return this.personRepository.findAll();
    }
    @ModelAttribute("allRemotes")
    public Iterable<RemoteDevice> populateRemotes() {return this.remoteDeviceRepository.findAll();}

    @GetMapping(path="/addPerson")
    public ModelAndView addPerson(){
        ModelAndView mv = new ModelAndView("/addPerson.html");
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
            return String.valueOf(team.getId());
        }

    }
    @GetMapping(path="/addTarget")
    public ModelAndView addTarget(){
        ModelAndView mv = new ModelAndView("/addTarget.html");
        mv.addObject("target", new Target());
        return mv;
    }
    @PostMapping(path="/addTarget", params = {"geocode"})
    public String geocodeAddress(Target target, BindingResult result, HttpServletRequest req){
        Geoutils geoutils = new Geoutils();
        Map<String,String> coord = geoutils.geocodeForLocation(req.getParameter("address"));
        target.setLat(coord.get("lat"));
        target.setLng(coord.get("lng"));
        return "addTarget";
    }
    @PostMapping(path="/addTarget", params = {"save"})
    public String saveTarget(Target target, BindingResult result){
        if(result.hasErrors())
            return "erro";
        else {
            targetRepository.save(target);
            return "addTarget";
        }
    }
    @PostMapping(path="/person")
    @ResponseBody
    public String savePerson(@Valid Person person, BindingResult result){
        if(result.hasErrors())
            return "Erro";
        else{
            personRepository.save(person);
            return person.name;
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
            return String.valueOf(device.getId());
        }

    }
    @GetMapping(path="/map")
    public ModelAndView showMap(){
        List<GeoLocation> loc = new ArrayList<>();
        loc.add(new GeoLocation("POINT", "-16.6921","-49.2672"));
        loc.add(new GeoLocation("POINT", "-16.5921","-49.2697"));
        loc.add(new GeoLocation("POINT", "-16.6921","-48.2677"));
        loc.add(new GeoLocation("POINT", "-16.621","-49.1674"));
        loc.add(new GeoLocation("POINT", "-16.612","-49.05677"));
        ModelAndView mv = new ModelAndView("map.html");
        mv.addObject("point",loc);
        return mv;
    }
    @GetMapping(path="/map2")
    public ModelAndView showMap2(){
        return new ModelAndView("map2.html");
    }
    @GetMapping(path="/markersList")
    @ResponseBody
    public Iterable<Team> markersList(){
        return teamRepository.findAll();
    }

    @GetMapping(path="/targetsList")
    @ResponseBody
    public Iterable<Target> targetsList(){return targetRepository.findAll();}


    @GetMapping(path="/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam String message){
        Map<String,String> map = new HashMap<>();
        map.put("mensagem", "Esta e uma mensagem de texto");
        CcsOutMessage outMessage = new CcsOutMessage("dFm4U_YygyE:APA91bH_q2MqVDBGfDhf-BZtjCpqd8C1YjVPLqBs4d32_DfwHvTXDh7irA-9p5B3-92FtmME47lQ27npaVIg7ZMbJYsI31X6nrETeA7r9bUcNjjdobW2VPcwKtoeqgs4lSVktR7oW-hE",
                "0000100",map);
        Map<String, String> notificationPayload = new HashMap<>();
        notificationPayload.put("title", "Notificação teste");
        notificationPayload.put("body", message);
        outMessage.setNotificationPayload(notificationPayload);
        org.jivesoftware.smack.packet.Message message1 = new org.jivesoftware.smack.packet.Message();
        message1.addExtension(new GcmPacketExtension(MessageMapper.toJsonString(outMessage)));
        org.springframework.messaging.Message<Message> msgFinal = new GenericMessage<Message>(message1);
        if(channel.send(msgFinal))
            return "yes";
        else
            return "no";
    }
    @PutMapping(path="/updateRemoteDevice/{id}")
    @ResponseBody
    public RemoteDevice updateRemoteDevice(@PathVariable(value = "id") Integer id,
                                           @Valid @RequestBody RemoteDevice device){
        RemoteDevice remoteDevice = remoteDeviceRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("note"));
        remoteDevice.setMessagingId(device.getMessagingId());
        remoteDevice.setLat(device.getLat());
        remoteDevice.setLng(device.getLng());
        remoteDevice.setStatus(device.getStatus());
        return remoteDeviceRepository.save(remoteDevice);
    }
    @GetMapping(path="/remoteDevice/{id}")
    @ResponseBody
    public RemoteDevice queryRemoteDevice(@PathVariable Integer id){
        return remoteDeviceRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("note"));
    }
}
