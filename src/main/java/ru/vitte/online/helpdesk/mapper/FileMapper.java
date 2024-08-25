package ru.vitte.online.helpdesk.mapper;

import org.mapstruct.Mapper;
import ru.vitte.online.helpdesk.dto.FileDto;
import ru.vitte.online.helpdesk.entity.FileEntity;

@Mapper
public interface FileMapper {


    FileEntity mapFile(FileDto  fileDto);

    FileDto mapFile(FileEntity fileEntity);
}
