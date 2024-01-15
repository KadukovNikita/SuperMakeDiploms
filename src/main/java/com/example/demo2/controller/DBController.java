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
@RequestMapping("/superDiplom/database")
public class DBController {

    @Autowired
    ParticipantService taskService;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/")
    public String start_page_db(Model model) throws Exception{
        if(httpSession.getAttribute("db_part")==null){
            List<Participant> loadedParticipants = ParticipantIO.loadParticipantsFromFile("participants.txt");
            httpSession.setAttribute("db_part", loadedParticipants);
        }
        List<Participant> participantList = taskService.getParticipantsfromDB();
        model.addAttribute("participants", participantList);
        return "database";
    }

    @RequestMapping("/addNewParticipant")
    public String addNewParticipant_db(Model model){
        Participant participant = new Participant();
        participant.setId(taskService.getParticipantsfromDB().size());
        participant.setEnable(true);
        model.addAttribute("participant", participant);
        return "showParticipant_db";
    }

    @RequestMapping("/saveParticipant")
    public String saveParticipant_db(@Valid @ModelAttribute("participant") Participant participant, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "showParticipant_db";
        }
        else {
            List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
            taskService.saveParticipant(list, participant);
            return "redirect:/superDiplom/database/";
        }
    }

    @RequestMapping("/updateParticipant/{id}")
    public String updateInfo_db(@PathVariable int id, Model model){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
        Participant participant = taskService.getParticipant(list, id);
        model.addAttribute("participant", participant);
        return "showParticipant_db";
    }

    @RequestMapping("/deleteParticipant/{id}")
    public String deletePart_db(@PathVariable int id){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
        taskService.deleteParticipant(list, id);
        return "redirect:/superDiplom/database/";
    }

}
