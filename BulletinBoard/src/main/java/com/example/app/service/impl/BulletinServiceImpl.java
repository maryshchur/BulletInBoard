package com.example.app.service.impl;

import com.example.app.dto.BulletinDto;
import com.example.app.entities.Bulletin;
import com.example.app.exceptions.NotFoundException;
import com.example.app.repository.BulletinRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.BulletinService;
import com.example.app.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.app.util.Pagination.validatePage;
import static com.example.app.util.Pagination.validatePageSize;

@Service
public class BulletinServiceImpl implements BulletinService {
    private BulletinRepository bulletinRepository;
    private UserRepository userRepository;
    private FileStorageService fileStorageService;
    private ModelMapper modelMapper;
    @Value("${ENDPOINT_URL}")
    private String endpointUrl;

    @Autowired
    public BulletinServiceImpl(BulletinRepository bulletinRepository,
                               UserRepository userRepository,
                               FileStorageService fileStorageService) {
        this.bulletinRepository = bulletinRepository;
        this.userRepository = userRepository;
        this.modelMapper =new ModelMapper();
        this.modelMapper.typeMap(Bulletin.class, BulletinDto.class).setPostConverter(b ->
        {
            BulletinDto bb = b.getDestination();
            bb.setImage(endpointUrl + b.getSource().getImage());
            return bb;
        });

        this.fileStorageService = fileStorageService;
    }

    @Override
    public Long save(String userEmail, BulletinDto bulletinDto) {
        Bulletin bulletin = modelMapper.map(bulletinDto, Bulletin.class);
        bulletin.setAddedDate(java.time.LocalDateTime.now());
        bulletin.setUser(userRepository.findUserByEmail(userEmail).get());
        return bulletinRepository.save(bulletin).getId();
    }

    @Override
    @Transactional
    public void uploadPhoto(MultipartFile photo, Long id) {
        Bulletin bulletin = bulletinRepository
                .findById(id).orElseThrow(() -> new NotFoundException("Bulletin was not found"));
        bulletin.setImage(fileStorageService.uploadFile(photo));
        bulletinRepository.save(bulletin);
    }


    @Override
    public Page<BulletinDto> getAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(validatePage(page), validatePageSize(pageSize), Sort.Direction.DESC, "id");
        Page<BulletinDto> bulletinDto = bulletinRepository.findAll(pageable)
                .map(bulletin -> modelMapper.map(bulletin, BulletinDto.class));
        return bulletinDto;
    }

    @Override
    public BulletinDto getById(Long id) {
        Bulletin bulletin = bulletinRepository.findById(id).orElseThrow(() -> new NotFoundException("Bulletin with current id does not exist"));
        BulletinDto bulletinDto = modelMapper.map(bulletin, BulletinDto.class);
        bulletinDto.setImage(endpointUrl + bulletinDto.getImage());
        return bulletinDto;
    }
}
