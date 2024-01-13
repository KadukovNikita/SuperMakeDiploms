package com.example.demo2.controller;

import com.example.demo2.entity.Participant;
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
    public String start_page(Model model){
        if(httpSession.getAttribute("participants")==null) {
            httpSession.setAttribute("participants", new ArrayList<Participant>());
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
            taskService.saveParticipant(participant);
            return "redirect:/superDiplom/";
        }
    }

    @RequestMapping("/updateParticipant/{id}")
    public String updateInfo(@PathVariable int id, Model model){
        Participant participant = taskService.getParticipant(id);
        model.addAttribute("participant", participant);
        return "showParticipant";
    }

    @RequestMapping("/deleteParticipant/{id}")
    public String deleteInfo(@PathVariable int id){
        taskService.deleteParticipant(id);
        return "redirect:/superDiplom/";
    }

    @RequestMapping("/createDiploms")
    public String createDiploms(){
        taskService.createDiploms();
        return "redirect:/superDiplom/";
    }

}
