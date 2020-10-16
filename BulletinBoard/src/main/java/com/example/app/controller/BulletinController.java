package com.example.app.controller;

import com.example.app.dto.BulletinDto;
import com.example.app.security.UserPrincipal;
import com.example.app.service.BulletinService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BulletinController {
    private BulletinService bulletinService;

    @Autowired
    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PostMapping("/create-bulletin")
    public ResponseEntity<Long> createAnnouncement(@Valid @RequestBody BulletinDto bulletinDto,
                                                   @ApiIgnore @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bulletinService.save(principal.getUsername(), bulletinDto));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/bulletins")
    public ResponseEntity<Page<BulletinDto>> getAllBulletins(@RequestParam Optional<Integer> page,
                                                             @RequestParam Optional<Integer> pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bulletinService.getAll(page.orElseGet(() -> 1), pageSize.orElseGet(() -> 10)));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/{id}/bulletin")
    public ResponseEntity<BulletinDto> getBulletinById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bulletinService.getById(id));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/{id}/upload-photo")
    public ResponseEntity uploadPhoto(@RequestPart(value = "file") MultipartFile photo,
                                      @PathVariable Long id) {
        bulletinService.uploadPhoto(photo, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @DeleteMapping("/{id}/")
    public ResponseEntity deleteBulletin(@PathVariable Long id) {
        bulletinService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/my-bulletins")
    public ResponseEntity<Page<BulletinDto>> getAllUserBulletins(@RequestParam Optional<Integer> page,
                                                                 @RequestParam Optional<Integer> pageSize,
                                                                 @ApiIgnore @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bulletinService.getAllByUser(page.orElseGet(() -> 1), pageSize.orElseGet(() -> 10), principal.getUsername()));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("{id}/profile")
    public ResponseEntity<Page<BulletinDto>> getAllUserBulletinsById(@PathVariable Long id, @RequestParam Optional<Integer> page,
                                                                     @RequestParam Optional<Integer> pageSize) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bulletinService.getAllByUserId(id,page.orElseGet(() -> 1), pageSize.orElseGet(() -> 10)));
    }
}
