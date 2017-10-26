package hu.bme.jwtsampleapp.entities.sample.entity;

import hu.bme.jwtsampleapp.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class SampleEntity extends BaseEntity {

    private String sampleData;

    public SampleEntity() {
    }

    public SampleEntity(String sampleData) {
        this.sampleData = sampleData;
    }
}
