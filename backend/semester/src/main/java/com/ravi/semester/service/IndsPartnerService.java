package com.ravi.semester.service;

import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import com.ravi.semester.repository.IndsPartnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndsPartnerService {
    @Autowired
    private IndsPartnerRepo indsPartnerRepo;
    public IndsPartner createIndusPartner(IndsPartner indsPartner) {
        return indsPartnerRepo.save(indsPartner);
    }

    public Optional<IndsPartner> updateIndsPartner(String id, IndsPartner updatedIndsPartner) {
        return indsPartnerRepo.findById(id).map(faculty -> {
            faculty.setName(updatedIndsPartner.getName());
            faculty.setEmail(updatedIndsPartner.getEmail());
//            faculty.setLab(updatedFaculty.getLab());
            return indsPartnerRepo.save(faculty);
        });
    }

    public boolean deleteIndsPartner(String id) {
        if (indsPartnerRepo.existsById(id)) {
            indsPartnerRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
