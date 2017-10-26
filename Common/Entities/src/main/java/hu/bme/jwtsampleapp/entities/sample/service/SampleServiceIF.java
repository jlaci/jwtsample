package hu.bme.jwtsampleapp.entities.sample.service;

import hu.bme.jwtsampleapp.entities.sample.dto.SampleDTO;

import java.util.List;

public interface SampleServiceIF {

    String REMOTE_ENDPOINT = "/remoteSampleService";

    SampleDTO getSampleById(Long id);

    List<SampleDTO> getAllSamples();

}
