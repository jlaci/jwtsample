package hu.bme.jwtsampleapp.gateway.sample;

import hu.bme.jwtsampleapp.entities.sample.dto.SampleDTO;
import hu.bme.jwtsampleapp.entities.sample.service.SampleServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private SampleServiceIF sampleService;

    @RequestMapping(method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public List<SampleDTO> getAllSamples() {
        return sampleService.getAllSamples();
    }
    
    @RequestMapping(path = "/{sampleId}", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity getSampleById(@PathVariable Long sampleId) {
        SampleDTO sample = sampleService.getSampleById(sampleId);

        if (sample == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sample);
        }
    }

}
