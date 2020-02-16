package com.man.fota.repository;

import com.man.fota.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Feature findByIdentification(String identification);

}
