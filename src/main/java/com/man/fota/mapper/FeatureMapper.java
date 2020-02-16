package com.man.fota.mapper;

import com.man.fota.dto.FeatureDTO;
import com.man.fota.model.Feature;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeatureMapper {

    FeatureMapper INSTANCE = Mappers.getMapper(FeatureMapper.class);

    FeatureDTO featureToFeatureDTO(Feature feature);
}
