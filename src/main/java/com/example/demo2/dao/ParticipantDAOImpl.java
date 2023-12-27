package com.example.demo2.dao;

import com.example.demo2.entity.Participant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Repository
public class ParticipantDAOImpl implements ParticipantDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Participant> getParticipants() {
        Query query = entityManager.createQuery("from Participant");
        List<Participant> participantList = query.getResultList();
        return participantList;
    }

    @Override
    public void saveParticipant(Participant participant) {
        participant.setId(entityManager.merge(participant).getId());
    }


    @Override
    public Participant getParticipant(int id) {
        return entityManager.find(Participant.class, id);
    }

    @Override
    public void deleteParticipant(int id) {
        Query query = entityManager.createQuery("delete from Participant where id = :TaskId");
        query.setParameter("TaskId", id);
        query.executeUpdate();
    }

    @Override
    public void createDiploms() {
        List<Participant> participants = getParticipants();

        String filePath = "template.docx";
        String filePath2 = "part.docx";

        for(Integer i = 1; i <= participants.size(); i++){
            String filePathPart = i.toString() + filePath2;
            System.out.println(filePathPart);

            try {
                FileInputStream fis = new FileInputStream(filePath);
                XWPFDocument document = new XWPFDocument(fis);

                fis.close();

                List<XWPFParagraph> paragraphs = document.getParagraphs();

                if (paragraphs.size() >= 29) {

                    XWPFParagraph champParagraph = paragraphs.get(8);

                    XWPFRun champRun = champParagraph.createRun();

                    champRun.setFontFamily("Arial");
                    champRun.setColor("333399");
                    champRun.setFontSize(16);
                    champRun.setBold(true);
                    champRun.setText("Чемпионат Чемпионат Чемпионат Чемпионат Чемпионат Чемпионат Чемпионат Чемпионат Чемпионат");

                    XWPFParagraph placeParagraph = paragraphs.get(11);

                    XWPFRun placeRun = placeParagraph.createRun();

                    placeRun.setFontFamily("Arial");
                    placeRun.setColor("ff0000");
                    placeRun.setFontSize(28);
                    placeRun.setBold(true);
                    placeRun.setText(participants.get(i-1).getPlace().toString() + " степени");


                    XWPFParagraph nameParagraph = paragraphs.get(13);

                    XWPFRun nameRun = nameParagraph.createRun();

                    nameRun.setFontFamily("Arial");
                    nameRun.setColor("000099");
                    nameRun.setFontSize(18);
                    nameRun.setBold(true);
                    nameRun.setText(participants.get(i-1).getFio());


                    XWPFParagraph trenerParagraph = paragraphs.get(15);

                    XWPFRun trenerRun = trenerParagraph.createRun();

                    trenerRun.setFontFamily("Arial");
                    trenerRun.setColor("000099");
                    trenerRun.setFontSize(18);
                    trenerRun.setBold(true);
                    trenerRun.setText(participants.get(i-1).getCoach());

                    XWPFParagraph dateParagraph = paragraphs.get(28);

                    XWPFRun dateRun = dateParagraph.createRun();

                    dateRun.setFontFamily("Calibri");
                    dateRun.setColor("ffffff");
                    dateRun.setFontSize(14);
                    dateRun.setBold(true);
                    dateRun.setText("99 никогдабря 555 года");

                } else {
                    System.out.println("Документ содержит меньше трех параграфов.");
                }


                FileOutputStream fos = new FileOutputStream(filePathPart);
                document.write(fos);
                fos.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
