package com.graceman.propertymangement.converter;

import com.graceman.propertymangement.dto.PropertyDTO;
import com.graceman.propertymangement.model.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyConverter {

    public Property convertDTOtoEntity(PropertyDTO propertyDTO){

        Property pe = new Property();
        pe.setTitle(propertyDTO.getTitle());
        pe.setAddress(propertyDTO.getAddress());
        pe.setPrice(propertyDTO.getPrice());
        pe.setDescription(propertyDTO.getDescription());

        return pe;
    }

    public PropertyDTO convertEntityToDTO(Property propertyEntity){

        PropertyDTO propertyDTO =  new PropertyDTO();
        propertyDTO.setId(propertyEntity.getId());
        propertyDTO.setTitle(propertyEntity.getTitle());
        propertyDTO.setAddress(propertyEntity.getAddress());
        propertyDTO.setPrice(propertyEntity.getPrice());
        propertyDTO.setDescription(propertyEntity.getDescription());
        propertyDTO.setUserId(propertyEntity.getUser().getId());

        return propertyDTO;
    }
}