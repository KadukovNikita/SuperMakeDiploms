package com.example.demo2.service;


import com.example.demo2.entity.Participant;

import java.util.List;

public interface ParticipantService {
    public List<Participant> getParticipants();

    public void saveParticipant(Participant participant);

    public Participant getParticipant(int id);

    public void deleteParticipant(int id);

    public void createDiploms();
}
