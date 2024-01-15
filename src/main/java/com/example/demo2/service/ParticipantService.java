package com.example.demo2.service;


import com.example.demo2.entity.Participant;

import java.util.List;

public interface ParticipantService {
    public List<Participant> getParticipants();

    public List<Participant> getParticipantsfromDB();

    public void saveParticipant(List<Participant> list, Participant participant);

    public Participant getParticipant(List<Participant> list, int id);

    public void deleteParticipant(List<Participant> list, int id);

    public void createDiploms();
}
