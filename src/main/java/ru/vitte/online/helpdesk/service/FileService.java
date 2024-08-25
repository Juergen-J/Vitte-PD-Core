package ru.vitte.online.helpdesk.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vitte.online.helpdesk.dto.FileDto;
import ru.vitte.online.helpdesk.entity.FileEntity;
import ru.vitte.online.helpdesk.mapper.FileMapper;
import ru.vitte.online.helpdesk.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final FileMapper fileMapper = Mappers.getMapper(FileMapper.class);

    public FileDto saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String uploadDir = "uploads/";
        Path filePath = Paths.get(uploadDir + fileName);

        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setPath(filePath.toString());

        fileEntity = fileRepository.save(fileEntity);

        return fileMapper.mapFile(fileEntity);
    }


    public FileDto getFile(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        return fileEntity.map(fileMapper::mapFile).orElse(null);
    }
}
