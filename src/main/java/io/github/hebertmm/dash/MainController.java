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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping(path = "/")
public class MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Random random = new Random();

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RemoteDeviceRepository remoteDeviceRepository;
    @Autowired
    private TargetRepository targetRepository;
    @Autowired
    private MyMessageRepository myMessageRepository;

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
    @ModelAttribute("allTargets")
    public Iterable<Target> populateTargets() {return this.targetRepository.findAll();}

    @GetMapping(path = "/")
    public ModelAndView homePage(){
        return new ModelAndView("index.html");
    }

    @GetMapping(path="/addPerson")
    public ModelAndView addPerson(@ModelAttribute("error") String error, @ModelAttribute("sucess") String sucess){
        ModelAndView mv = new ModelAndView("addPerson.html");
        mv.addObject("person", new Person());
        mv.addObject("errorMessage", error);
        mv.addObject("sucessMessage", sucess);
        return mv;
    }
    @PostMapping(path="/person")
    public String savePerson(@Valid Person person, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()) {
            attributes.addFlashAttribute("error", "Erro ao inserir");
        }
        else{
            personRepository.save(person);
            attributes.addFlashAttribute("sucess", "Dado inserido com sucesso");
        }
        return "redirect:/addPerson";
    }
    @GetMapping(path="/addTeam")
    public ModelAndView addTeam(){
        ModelAndView mv = new ModelAndView("addTeam.html");
        mv.addObject("persons", personRepository.findAll());
        mv.addObject("team", new Team());
        return mv;
    }
    @PostMapping(path="/addTeam", params = {"addMember"})
    public String addMember(Team team, BindingResult result){
        team.addMember(new Person());
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
    public String saveTeam(@Valid Team team, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            redirectAttributes.addFlashAttribute("error", result.getGlobalError().toString());
        else{
            teamRepository.save(team);
            redirectAttributes.addFlashAttribute("sucess", "Equipe cadastrada com sucesso");
        }
        return "redirect:/addTeam";
    }
    @GetMapping(path="/listTeams")
    public ModelAndView listTeams(){
        ModelAndView mv = new ModelAndView("listTeam.html");
        mv.addObject("teams",teamRepository.findAll());
        return mv;
    }
    @GetMapping(path = "/teamsSituation")
    public ModelAndView teamsSituation(){
        ModelAndView mv = new ModelAndView("teamSituation.html");
        mv.addObject("nao_iniciado", remoteDeviceRepository.countByStatus("NAO_INICIADO"));
        mv.addObject("deslocamento", remoteDeviceRepository.countByStatus("DESLOCAMENTO"));
        mv.addObject("acao", remoteDeviceRepository.countByStatus("AÇÃO"));
        mv.addObject("encerrada", remoteDeviceRepository.countByStatus("ENCERRADA"));
        mv.addObject("em_atencao", remoteDeviceRepository.countByStatus("EM_ATENÇÃO"));
        mv.addObject("total", remoteDeviceRepository.count());
        return mv;
    }
    @GetMapping(path="/delTeam")
    public ModelAndView delTeam(@RequestParam Integer teamId){
        Team t = teamRepository.findById(teamId).orElseThrow(() -> new ResourceAccessException("team"));
        teamRepository.delete(t);
        return new ModelAndView("listTeam.html");
    }
    @GetMapping(path="/addTarget")
    public ModelAndView addTarget(){
        ModelAndView mv = new ModelAndView("addTarget.html");
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
    public String saveTarget(Target target, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            redirectAttributes.addFlashAttribute("error", result.getGlobalError().toString());
        else {
            targetRepository.save(target);
            redirectAttributes.addFlashAttribute("sucess", "Entidade inserida com sucesso");
        }
        return "redirect:/addTarget";
    }

    @GetMapping(path="/addRemoteDevice")
    public ModelAndView addRemoteDevice(){
        ModelAndView mv = new ModelAndView("addRemoteDevice.html");
        mv.addObject("remoteDevice", new RemoteDevice());
        return mv;
    }
    @PostMapping(path="/addRemoteDevice")
    public String saveRemoteDevice(@Valid RemoteDevice device, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            redirectAttributes.addFlashAttribute("error", result.getGlobalError().toString());
        else{
            remoteDeviceRepository.save(device);
            redirectAttributes.addFlashAttribute("sucess", "Dispositivo cadastrado com sucesso ");
        }
        return "redirect:/addRemoteDevice";
    }

    @GetMapping(path="/map2")
    public ModelAndView showMap2(){
        return new ModelAndView("map2.html");
    }

    @GetMapping(path = "/chat")
    public ModelAndView showChatBox(){
        ModelAndView mv = new ModelAndView("chatBox.html");
        return mv;
    }
    @GetMapping(path="/markersList")
    @ResponseBody
    public Iterable<Team> markersList(){
        return teamRepository.findAll();
    }

    @GetMapping(path="/targetsList")
    @ResponseBody
    public Iterable<Target> targetsList(){return targetRepository.findAll();}

    @GetMapping(path="/messageList")
    @ResponseBody
    public Iterable<MyMessage> messageList(){return myMessageRepository.findAll();}
    @GetMapping(path="/teamsList")
    @ResponseBody
    public Iterable<Team> teamsList() {return teamRepository.findAll();}


    @GetMapping(path="/sendMessage")
    public String sendMessage(@RequestParam String message, @RequestParam Integer teamId){
        try{
            Team t = teamRepository.findById(teamId).orElseThrow(()-> new ResourceAccessException("Team"));
            String destId = t.getRemoteDevice().getMessagingId();
            if(destId == null || destId.equals("")){
                return "no";
            }
            Integer id = random.nextInt(9999);
            Map<String,String> map = new HashMap<>();
            map.put("message", message);
            CcsOutMessage outMessage = new CcsOutMessage(destId,id.toString(),map);
            org.jivesoftware.smack.packet.Message message1 = new org.jivesoftware.smack.packet.Message();
            message1.addExtension(new GcmPacketExtension(MessageMapper.toJsonString(outMessage)));
            org.springframework.messaging.Message<Message> msgFinal = new GenericMessage<Message>(message1);
            if(true){//channel.send(msgFinal)) {
                MyMessage msg = new MyMessage();
                msg.setTeam(t);
                msg.setType("sent");
                msg.setText(message);
                msg.setFirebaseId(id);
                msg.setTimestamp(msgFinal.getHeaders().getTimestamp());
                myMessageRepository.save(msg);
                return "redirect:/messageConsole";
            }
            else
                return "redirect:/messageConsole";
        }catch(ResourceAccessException e){
            //e.printStackTrace();
            return "Erro ao enviar a mensagem";
        }

    }
    @PutMapping(path="/updateRemoteDevice/{id}")
    @ResponseBody
    public RemoteDevice updateRemoteDevice(@PathVariable(value = "id") Integer id,
                                           @Valid @RequestBody RemoteDevice device){
        RemoteDevice remoteDevice = remoteDeviceRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("note"));
        if(device.getMessagingId() != null)
            remoteDevice.setMessagingId(device.getMessagingId());
        if(device.getLat() != null)
            remoteDevice.setLat(device.getLat());
        if(device.getLng() != null)
            remoteDevice.setLng(device.getLng());
        if(device.getStatus() != null)
            remoteDevice.setStatus(device.getStatus());
        return remoteDeviceRepository.save(remoteDevice);
    }
    @GetMapping(path="/remoteDevice/{id}")
    @ResponseBody
    public RemoteDevice queryRemoteDevice(@PathVariable Integer id){
        return remoteDeviceRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("remote"));
    }

    @GetMapping(path="/console")
    public ModelAndView showConsole(){
        return (new ModelAndView("console.html"));
    }

    @GetMapping(path="/messageConsole")
    public ModelAndView showMessageConsole(){
        ModelAndView mv = new ModelAndView("messageConsole.html");
        mv.addObject("teams", teamRepository.findAll());
        return mv;
    }
    @GetMapping(path = "/idByNumber")
    @ResponseBody
    public Integer findRemoteId(@RequestParam String number){
        RemoteDevice remoteDevice = remoteDeviceRepository.findByNumber(number);
        if(remoteDevice != null)
            return remoteDevice.getId();
        else
            return 0;
    }


}
