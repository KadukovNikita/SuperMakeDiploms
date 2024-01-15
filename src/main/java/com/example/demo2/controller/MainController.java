package com.example.demo2.controller;

import com.example.demo2.entity.Participant;
import com.example.demo2.entity.ParticipantIO;
import com.example.demo2.service.ParticipantService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/superDiplom")
public class MainController {

    @Autowired
    ParticipantService taskService;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/")
    public String start_page(Model model) throws Exception{
        if(httpSession.getAttribute("participants")==null) {
            httpSession.setAttribute("participants", new ArrayList<Participant>());
        }
        if(httpSession.getAttribute("db_part")==null){
            List<Participant> loadedParticipants = ParticipantIO.loadParticipantsFromFile("participants.txt");
            httpSession.setAttribute("db_part", loadedParticipants);
        }
        List<Participant> participantList = taskService.getParticipants();
        model.addAttribute("participants", participantList);
        return "start";
    }

    @RequestMapping("/addNewParticipant")
    public String addNewParticipant(Model model){
        Participant participant = new Participant();
        participant.setId(taskService.getParticipants().size());
        participant.setEnable(true);
        model.addAttribute("participant", participant);
        return "showParticipant";
    }

    @RequestMapping("/saveParticipant")
    public String saveParticipant(@Valid @ModelAttribute("participant") Participant participant, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "showParticipant";
        }
        else {
            List<Participant> list = (List<Participant>)httpSession.getAttribute("participants");
            taskService.saveParticipant(list, participant);
            return "redirect:/superDiplom/";
        }
    }

    @RequestMapping("/updateParticipant/{id}")
    public String updateInfo(@PathVariable int id, Model model){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("participants");
        Participant participant = taskService.getParticipant(list, id);
        model.addAttribute("participant", participant);
        return "showParticipant";
    }

    @RequestMapping("/deleteParticipant/{id}")
    public String deleteInfo(@PathVariable int id){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("participants");
        taskService.deleteParticipant(list, id);
        return "redirect:/superDiplom/";
    }

    @RequestMapping("/createDiploms")
    public String createDiploms(){
        taskService.createDiploms();
        return "redirect:/superDiplom/";
    }

    @RequestMapping("/import")
    public String import_db(Model model) throws Exception{
        List<Participant> loadedParticipants = ParticipantIO.loadParticipantsFromFile("participants.txt");
        List<Participant> list = (List<Participant>)httpSession.getAttribute("participants");
        for(int i = 0; i < loadedParticipants.size(); i++){
            Participant participant = (Participant) loadedParticipants.get(i).clone();
            participant.setId(list.size());
            taskService.saveParticipant(list, participant);
        }
        return "redirect:/superDiplom/";
    }
}
