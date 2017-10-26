package hu.bme.jwtsampleapp.sampleservice.sample;

import hu.bme.jwtsampleapp.entities.sample.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}
