package com.practice.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.practice.data.CustomerEntity;

@Mapper
public interface CustomerMapper {
	CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );
    
    Customer entityToDto(CustomerEntity customerEntity);
    CustomerEntity dtoToEntity(Customer customer);
}
