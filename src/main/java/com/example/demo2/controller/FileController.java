package com.example.demo2.controller;

import com.example.demo2.entity.Participant;
import com.example.demo2.service.ParticipantService;
import com.opencsv.CSVReader;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class FileController {

    @Autowired
    ParticipantService taskService;

    @Autowired
    HttpSession httpSession;

    private static void setFieldValue(Object object, String fieldName, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @RequestMapping("/csv")
    public String handleCsvUpload(Model model, @RequestParam("file") MultipartFile file, @RequestParam("booleanParam") boolean booleanParam) throws Exception{
        if (!file.isEmpty()) {
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

            CSVReader csvReader = new CSVReader(reader);
            List<String[]> records = csvReader.readAll();

            int cnt = 0;
            String[] fields = null;

            List<Participant> list;

            if(booleanParam) {
                list = (List<Participant>) httpSession.getAttribute("participants");
            }
            else{
                list = (List<Participant>) httpSession.getAttribute("db_part");
            }
            for (String[] strings : records) {
                if(cnt == 0){
                    fields = strings;
                }
                else {
                    Participant participant = new Participant();
                    for (int i = 0; i < fields.length; i++) {

                        if (isNumeric(strings[i])) {
                            int integerValue = Integer.parseInt(strings[i]);
                            setFieldValue(participant, fields[i], integerValue);
                        }
                        else {
                            setFieldValue(participant, fields[i], strings[i]);
                        }
                    }
                    participant.setEnable(true);
                    participant.setId(list.size());
                    taskService.saveParticipant(list, participant);
                    System.out.println(participant);
                }
                cnt++;
            }
            if(booleanParam) {
                httpSession.setAttribute("participants", list);
                return "redirect:/superDiplom/";
            }
            else{
                httpSession.setAttribute("db_part", list);
                return "redirect:/superDiplom/database/";
            }
        }
        else {
            model.addAttribute("message", "ОШИБКА С ЗАГРУЗКОЙ ФАЙЛА!");
            return "changeOK";
        }
    }

}
