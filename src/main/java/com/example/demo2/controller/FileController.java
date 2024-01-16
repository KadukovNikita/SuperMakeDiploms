package com.example.demo2.controller;

import com.example.demo2.entity.Participant;
import com.example.demo2.service.ParticipantService;
import com.opencsv.CSVReader;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequestMapping("/upload")
public class FileController {

    @Autowired
    ParticipantService participantService;

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
            Double.parseDouble(str);
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
                            int integerValue = (int)Double.parseDouble(strings[i]);
                            setFieldValue(participant, fields[i], integerValue);
                        }
                        else {
                            setFieldValue(participant, fields[i], strings[i]);
                        }
                    }
                    participant.setEnable(true);
                    participant.setId(list.size());
                    participantService.saveParticipant(list, participant);
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

    @RequestMapping("/xlsx")
    public String handleXlsxUpload(Model model, @RequestParam("file2") MultipartFile file, @RequestParam("booleanParam2") boolean booleanParam) throws Exception{
        InputStream inputStream = file.getInputStream();
        if (!file.isEmpty()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int cnt = 0, fields_num = 0;

            Row fields = null;

            List<Participant> list;

            if(booleanParam) {
                list = (List<Participant>) httpSession.getAttribute("participants");
            }
            else{
                list = (List<Participant>) httpSession.getAttribute("db_part");
            }

            for (Row row : sheet) {
                if(cnt == 0){
                    fields = row;
                    for (Cell cell : fields) {
                        fields_num++;
                    }
                }
                else {
                    Participant participant = new Participant();
                    for (int i = 0; i < fields_num; i++) {
                        String value = row.getCell(i).toString();
                        System.out.println(value + " " + fields.getCell(i).toString());

                        if (isNumeric(value)) {
                            int integerValue = (int)Double.parseDouble(value);
                            System.out.println("*" + fields.getCell(i).toString() + "*" + integerValue);
                            setFieldValue(participant, fields.getCell(i).toString(), integerValue);
                        }
                        else {
                            System.out.println(fields.getCell(i).toString());
                            setFieldValue(participant, fields.getCell(i).toString(), value);
                        }
                    }
                    participant.setEnable(true);
                    participant.setId(list.size());
                    participantService.saveParticipant(list, participant);
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


    @RequestMapping("/superxlsx")
    public String handleSuperXlsxUpload(Model model, @RequestParam("file3") MultipartFile file,
                                        @RequestParam("booleanParam3") boolean booleanParam, @RequestParam("col_name") int col_name,
                                        @RequestParam("col_surname") int col_surname, @RequestParam("col_patronymic") int col_patronymic,
                                        @RequestParam("col_coach_name") int col_coach_name, @RequestParam("col_coach_surname") int col_coach_surname,
                                        @RequestParam("col_coach_patronymic") int col_coach_patronymic, @RequestParam("col_place") int col_place) throws Exception{
        InputStream inputStream = file.getInputStream();
        System.out.println(col_name + " " + col_surname + " " + col_patronymic + " " + col_coach_surname + " " + col_coach_name + " " + col_coach_patronymic + " " + col_place);
        if (!file.isEmpty()) {
            System.out.println("*************" + booleanParam);
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int cnt = -1;

            List<Participant> list;

            if(booleanParam) {
                list = (List<Participant>) httpSession.getAttribute("participants");
            }
            else{
                list = (List<Participant>) httpSession.getAttribute("db_part");
            }

            for (Row row : sheet) {
                cnt++;
                if(cnt == 0) continue;
                else {
                    Participant participant = new Participant();
                    if(col_name>0) participant.setName(row.getCell(col_name-1).toString()); else participant.setName("");
                    if(col_surname>0) participant.setSurname(row.getCell(col_surname-1).toString()); else participant.setSurname("");
                    if(col_patronymic>0) participant.setPatronymic(row.getCell(col_patronymic-1).toString()); else participant.setPatronymic("");
                    if(col_coach_name>0) participant.setCoach_name(row.getCell(col_coach_name-1).toString()); else participant.setCoach_name("");
                    if(col_coach_surname>0) participant.setCoach_surname(row.getCell(col_coach_surname-1).toString()); else participant.setCoach_surname("");
                    if(col_coach_patronymic>0) participant.setCoach_patronymic(row.getCell(col_coach_patronymic-1).toString()); else participant.setCoach_patronymic("");
                    if(col_place>0) participant.setPlace((int)Double.parseDouble(row.getCell(col_place-1).toString())); else participant.setPlace(1);

                    participant.setEnable(true);
                    participant.setId(list.size());
                    participantService.saveParticipant(list, participant);
                    System.out.println(participant);
                }
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
