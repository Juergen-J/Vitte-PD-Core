package ru.vitte.online.helpdesk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.entity.PersonEntity;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDto mapPerson(PersonEntity personEntity);
    PersonEntity mapPerson(PersonDto personDto);
}
