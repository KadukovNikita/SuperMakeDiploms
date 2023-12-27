package com.example.demo2.service;


import com.example.demo2.dao.ParticipantDAO;
import com.example.demo2.entity.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    ParticipantDAO participantDAO;

    @Transactional
    @Override
    public List<Participant> getParticipants() {
        return participantDAO.getParticipants();
    }

    @Transactional
    @Override
    public void saveParticipant(Participant participant) {
        participantDAO.saveParticipant(participant);
    }


    @Transactional
    @Override
    public Participant getParticipant(int id) {
        return participantDAO.getParticipant(id);
    }

    @Transactional
    @Override
    public void deleteParticipant(int id) {
        participantDAO.deleteParticipant(id);
    }

    @Override
    public void createDiploms() {
        participantDAO.createDiploms();
    }
}
