package com.laft17s.mscompaccountmovement.mappers;

import com.laft17s.mscompaccountmovement.dto.AccountDTO;
import com.laft17s.mscompaccountmovement.entities.Account;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AccountMapper {

    Account toAcount(AccountDTO data);

    AccountDTO toAcountDTO(Account data);

    List<AccountDTO> toListAccountDTO(List<Account> list);

}
