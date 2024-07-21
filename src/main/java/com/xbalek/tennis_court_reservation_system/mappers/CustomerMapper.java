package com.xbalek.tennis_court_reservation_system.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.xbalek.tennis_court_reservation_system.dto.CustomerDTO;
import com.xbalek.tennis_court_reservation_system.model.Customer;
import com.xbalek.tennis_court_reservation_system.model.SurfaceType;

/**
 * Mapper for {@link SurfaceType}.
 *
 * @author Filip Balek
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Customer mapToCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> toDTOList(List<Customer> customers);
}
