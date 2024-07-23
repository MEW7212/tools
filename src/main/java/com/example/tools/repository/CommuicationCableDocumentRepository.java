package com.example.tools.repository;

import com.example.tools.model.Mtrread;

import java.util.List;

public interface CommuicationCableDocumentRepository {

    List<Mtrread> getData(String interfaceId);
}
