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
    ParticipantService participantService;

    @Autowired
    HttpSession httpSession;

    @RequestMapping("/")
    public String start_page_db(Model model) throws Exception{
        if(httpSession.getAttribute("db_part")==null){
            List<Participant> loadedParticipants = ParticipantIO.loadParticipantsFromFile("participants.txt");
            httpSession.setAttribute("db_part", loadedParticipants);
        }
        List<Participant> participantList = participantService.getParticipantsfromDB();
        model.addAttribute("participants", participantList);
        return "database";
    }

    @RequestMapping("/addNewParticipant")
    public String addNewParticipant_db(Model model){
        Participant participant = new Participant();
        participant.setId(participantService.getParticipantsfromDB().size());
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
            participantService.saveParticipant(list, participant);
            return "redirect:/superDiplom/database/";
        }
    }

    @RequestMapping("/updateParticipant/{id}")
    public String updateInfo_db(@PathVariable int id, Model model){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
        Participant participant = participantService.getParticipant(list, id);
        model.addAttribute("participant", participant);
        return "showParticipant_db";
    }

    @RequestMapping("/deleteParticipant/{id}")
    public String deletePart_db(@PathVariable int id){
        List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
        participantService.deleteParticipant(list, id);
        return "redirect:/superDiplom/database/";
    }

    @RequestMapping("/refresh")
    public String refresh_db(Model model) throws Exception{
        List<Participant> list = (List<Participant>)httpSession.getAttribute("db_part");
        List<Participant> list2 = new ArrayList<>();
        int counter = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).isEnable()){
                Participant participant = list.get(i);
                participant.setId(counter);
                counter++;
                list2.add(participant);
            }
        }
        ParticipantIO.saveParticipantsToFile(list2, "participants.txt");
        httpSession.setAttribute("db_part", list2);
        model.addAttribute("message", "Все участники добавлены в базу данных!");
        return "changeOK";
    }

    @RequestMapping("/import")
    public String import_db(Model model) throws Exception{
        List<Participant> loadedParticipants = ParticipantIO.loadParticipantsFromFile("participants.txt");
        httpSession.setAttribute("db_part", loadedParticipants);
        model.addAttribute("message", "ОКНО БАЗЫ ДАННЫХ СИНХРОНИЗИРОВАННО С БД!");
        return "changeOK";
    }

    @RequestMapping("/moveParticipant/{id}")
    public String movePart_db(@PathVariable int id) throws Exception{
        List<Participant> list = (List<Participant>)httpSession.getAttribute("participants");
        List<Participant> list2 = (List<Participant>)httpSession.getAttribute("db_part");
        Participant participant = (Participant) list2.get(id).clone(); participant.setId(list.size());
        participantService.saveParticipant(list, participant);
        return "redirect:/superDiplom/database/";
    }

}
