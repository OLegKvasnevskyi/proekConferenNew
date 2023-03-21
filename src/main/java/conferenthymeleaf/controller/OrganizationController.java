package conferenthymeleaf.controller;


import conferenthymeleaf.entity.Organization;
import conferenthymeleaf.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
class OrganizationController {
    static final int DEFAULT_PAGE_SIZE = 2;
    private final OrganizationService organizationService;

    @Autowired
    OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/organizations/")
    public String index() {
        return "redirect:list";
    }

    @GetMapping("/organizations/list")
    public String getOrganizations(final Model model,
                                   @RequestParam(value = "page", defaultValue = " 0") final int pageNumber,
                                   @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE + "") final int pageSize) {
        final Page<Organization> page = organizationService.getOrganizations(pageNumber, pageSize);

        final int currentPageNumber = page.getNumber();
        final int previousPageNumber = page.hasPrevious() ? currentPageNumber - 1 : -1;
        final int nextPageNumber = page.hasNext() ? currentPageNumber + 1 : -1;

        model.addAttribute("organizations", page.getContent());
        model.addAttribute("previousPageNumber", previousPageNumber);
        model.addAttribute("nextPageNumber", nextPageNumber);
        model.addAttribute("currentPageNumber", currentPageNumber);
        model.addAttribute("pageSize", pageSize);
        return "organizations/list";
    }

    @GetMapping("/organizations/add")
    public String add(final Model model) {
        model.addAttribute("organization", new Organization());
        return "organizations/add";
    }

    @GetMapping("/organizations/view")
    public String view(final Model model, @RequestParam final Integer id) {
        final Optional<Organization> record = organizationService.getOrganization(id);
        model.addAttribute("organization", record.isPresent() ? record.get() : new Organization());
        model.addAttribute("id", id);
        return "organizations/view";
    }

    @GetMapping("/organizations/edit")
    public String edit(final Model model, @RequestParam final Integer id) {
        final Optional<Organization> record = organizationService.getOrganization(id);
        model.addAttribute("organization", record.isPresent() ? record.get() : new Organization());
        model.addAttribute("id", id);
        return "organizations/edit";
    }

    @PostMapping("/organizations/save")
    public String save(final Model model,
                       @ModelAttribute final Organization organization,
                       final BindingResult errors) {
        organizationService.save(organization);
        return "redirect:list";
    }

    @GetMapping("/organizations/delete")
    public String delete(final Model model, @RequestParam final Integer id) {
        final Optional<Organization> record = organizationService.getOrganization(id);
        model.addAttribute("organization", record.isPresent() ? record.get() : new Organization());
        model.addAttribute("id", id);
        return "organizations/delete";
    }

    @PostMapping("/organizations/delete")
    public String deletion(final Model model, @RequestParam final Integer id) {
        organizationService.delete(id);
        return "redirect:list";
    }

    //@GetMapping("/error")
    @RequestMapping(value = "/organization/error" ,method = RequestMethod.GET)
    public String goError(final Model model) {

        model.addAttribute("organization", new Organization("Malboro"));


            final Optional<Organization> record = organizationService.getOrganization(1);
            model.addAttribute("organization", name);
            model.addAttribute("id", 1);
            return "organizations/view";
        }

        return "organization/error";
    }


}
