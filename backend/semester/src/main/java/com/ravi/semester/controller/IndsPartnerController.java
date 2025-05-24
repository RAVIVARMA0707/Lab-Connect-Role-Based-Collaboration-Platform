package com.ravi.semester.controller;

import com.ravi.semester.dto.FacultyDTO;
import com.ravi.semester.dto.IndsPartnerDto;
import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import com.ravi.semester.repository.IndsPartnerRepo;
import com.ravi.semester.service.IndsPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/pic/indusPartner")
public class IndsPartnerController {

    @Autowired
    private IndsPartnerService indsPartnerService;
    @Autowired
    private IndsPartnerRepo indsPartnerRepo;

    @PostMapping
    public ResponseEntity<IndsPartner> createIndsPartners(@RequestBody IndsPartner indsPartner) {
        return ResponseEntity.ok(indsPartnerService.createIndusPartner(indsPartner));
    }

    @GetMapping
    public List<IndsPartnerDto> getAllIndsPartners() {
        List<IndsPartner> indsPartners = indsPartnerRepo.findAll();
        return indsPartners.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<IndsPartnerDto> getIndsPartnerById(@PathVariable("id") String id) {
        return indsPartnerRepo.findById(id).map(this::convertToDTO);
    }

    private IndsPartnerDto convertToDTO(IndsPartner indsPartner) {
        return new IndsPartnerDto(
                indsPartner.getId(),
                indsPartner.getName(),
                indsPartner.getEmail(),
                indsPartner.getLab() != null ? indsPartner.getLab().getId() : null,
                indsPartner.getLab() != null ? indsPartner.getLab().getName() : null
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<IndsPartner> updateIndsPartner(@PathVariable String id, @RequestBody IndsPartner indsPartner) {
        return indsPartnerService.updateIndsPartner(id, indsPartner)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIndsPartner(@PathVariable String id) {
        if (indsPartnerService.deleteIndsPartner(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
