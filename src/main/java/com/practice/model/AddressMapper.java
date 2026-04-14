package com.practice.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.practice.data.AddressEntity;

@Mapper
public interface AddressMapper {
	AddressMapper INSTANCE = Mappers.getMapper( AddressMapper.class );
    
    Address entityToDto(AddressEntity addressEntity);
    AddressEntity dtoToEntity(Address address);
}
