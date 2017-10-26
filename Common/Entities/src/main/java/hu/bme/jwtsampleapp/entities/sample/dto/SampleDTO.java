package hu.bme.jwtsampleapp.entities.sample.dto;

import hu.bme.jwtsampleapp.entities.BaseDTO;
import hu.bme.jwtsampleapp.entities.sample.entity.SampleEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class SampleDTO implements BaseDTO{

    private String sampleData;


    public SampleDTO() {
    }

    public SampleDTO(SampleEntity sampleEntity) {
        BeanUtils.copyProperties(sampleEntity, this); // This copies properties by their name, e.g sampleData
    }
}
