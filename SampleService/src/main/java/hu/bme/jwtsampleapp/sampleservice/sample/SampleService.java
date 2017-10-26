package hu.bme.jwtsampleapp.sampleservice.sample;

import hu.bme.jwtsampleapp.entities.sample.dto.SampleDTO;
import hu.bme.jwtsampleapp.entities.sample.entity.SampleEntity;
import hu.bme.jwtsampleapp.entities.sample.service.SampleServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleService implements SampleServiceIF {

    @Autowired
    private SampleRepository sampleRepository;

    @PostConstruct
    public void init() {
        if (sampleRepository.count() == 0) {
            sampleRepository.save(new SampleEntity("test 1"));
            sampleRepository.save(new SampleEntity("test 2"));
            sampleRepository.save(new SampleEntity("test 3"));
        }
    }

    @Override
    public SampleDTO getSampleById(Long id) {
        SampleEntity sampleEntity = sampleRepository.findOne(id);

        if (sampleEntity != null) {
            return new SampleDTO(sampleEntity);
        }

        return null;
    }

    @Override
    public List<SampleDTO> getAllSamples() {
        return sampleRepository.findAll().stream().map(SampleDTO::new).collect(Collectors.toList());
    }
}
