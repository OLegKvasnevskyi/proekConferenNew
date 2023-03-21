package conferenthymeleaf.service;


import conferenthymeleaf.entity.Organization;
import conferenthymeleaf.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Page<Organization> getOrganizations(final int pageNumber, final int size) {
        return organizationRepository.findAll(PageRequest.of(pageNumber, size));
    }


    public Optional<Organization> getOrganization(final Integer id) {
        return organizationRepository.findById(id);
    }

    public Organization save(final Organization organizationEntity) {
        return organizationRepository.save(organizationEntity);
    }

    public void delete(final Integer id) {
        organizationRepository.deleteById(id);
    }
}


