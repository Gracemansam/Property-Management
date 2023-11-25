package com.graceman.propertymangement.service.impl;

import com.graceman.propertymangement.converter.PropertyConverter;
import com.graceman.propertymangement.dto.PropertyDTO;
import com.graceman.propertymangement.exception.BusinessException;
import com.graceman.propertymangement.exception.ErrorModel;
import com.graceman.propertymangement.model.Property;
import com.graceman.propertymangement.model.User;
import com.graceman.propertymangement.repository.PropertyRepository;
import com.graceman.propertymangement.repository.UserRepository;
import com.graceman.propertymangement.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {


    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyConverter propertyConverter;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {

        Optional<User> optUe = userRepository.findById(propertyDTO.getUserId());
        if(optUe.isPresent()) {
            Property pe = propertyConverter.convertDTOtoEntity(propertyDTO);
            pe.setUser(optUe.get());
            pe = propertyRepository.save(pe);

            propertyDTO = propertyConverter.convertEntityToDTO(pe);
        }else{
//            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("USER_ID_NOT_EXIST");
            errorModel.setMessage("User does not exist");
//            errorModelList.add(errorModel);

            throw new BusinessException(errorModel);
        }
        return propertyDTO;
    }

    @Override
    public List<PropertyDTO> getAllProperties() {


        List<Property> listOfProps = (List<Property>)propertyRepository.findAll();
        List<PropertyDTO> propList = new ArrayList<>();

        for(Property pe : listOfProps){
            PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    @Override
    public List<PropertyDTO> getAllPropertiesForUser(Long userId) {
        List<Property> listOfProps = (List<Property>)propertyRepository.findAllByUserId(userId);
        List<PropertyDTO> propList = new ArrayList<>();

        for(Property pe : listOfProps){
            PropertyDTO dto = propertyConverter.convertEntityToDTO(pe);
            propList.add(dto);
        }
        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyId) {

        Optional<Property> optEn =  propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if(optEn.isPresent()){

            Property pe = optEn.get();//data from database
            pe.setTitle(propertyDTO.getTitle());
            pe.setAddress(propertyDTO.getAddress());
            pe.setPrice(propertyDTO.getPrice());
            pe.setDescription(propertyDTO.getDescription());
            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyId) {
        Optional<Property> optEn =  propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if(optEn.isPresent()){
            Property pe = optEn.get();//data from database
            pe.setDescription(propertyDTO.getDescription());
            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
    }

    @Override
    public PropertyDTO updatePropertyPrice(PropertyDTO propertyDTO, Long propertyId) {
        Optional<Property> optEn =  propertyRepository.findById(propertyId);
        PropertyDTO dto = null;
        if(optEn.isPresent()){
            Property pe = optEn.get();//data from database
            pe.setPrice(propertyDTO.getPrice());
            dto = propertyConverter.convertEntityToDTO(pe);
            propertyRepository.save(pe);
        }
        return dto;
    }

    @Override
    public void deleteProperty(Long propertyId) {
        propertyRepository.deleteById(propertyId);
    }
}
