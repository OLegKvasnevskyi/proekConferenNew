package io.codejournal.springboot.mvcjpathymeleaf.controller;

import io.codejournal.springboot.mvcjpathymeleaf.entity.Organization;
import io.codejournal.springboot.mvcjpathymeleaf.service.OrganizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.codejournal.springboot.mvcjpathymeleaf.controller.OrganizationController.DEFAULT_PAGE_SIZE;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
public class OrganizationControllerTest {

    private MockMvc mvc;

    @MockBean
    private OrganizationService service;

    private OrganizationController fixture;

    @BeforeEach
    public void setUp() {
        fixture = new OrganizationController(service);
        this.mvc = MockMvcBuilders.standaloneSetup(fixture).build();
    }
public int randomInteger(){
        return (int)(Math.random()*10);
}
    @Test
    public void index_RedirectsToListView_WhenOrganizationtHomeIsAccessed() throws Exception {

        // @formatter:off
        mvc.perform(get("/organizations/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"))
        ;
        // @formatter:on
        then(service).shouldHaveNoInteractions();
    }

    @Test
    public void list_ReturnsViewWithRecords_WhenOrganizationListViewIsAccessed() throws Exception {

        final int pageNumber = 0;
        final int pageSize = DEFAULT_PAGE_SIZE;
        final int totalPages = (int) (Math.random() * 100);

        final Organization organization1 = new Organization(randomInteger(), randomUUID().toString());
        final Organization organization2 = new Organization(randomInteger(), randomUUID().toString());

        final List<Organization> organizations = Arrays.asList(organization1, organization2);
        final Pageable page = PageRequest.of(pageNumber, pageSize);

        final Page<Organization> response = new PageImpl<>(organizations, page, totalPages);

        given(service.getOrganizations(pageNumber, pageSize)).willReturn(response);

        // @formatter:off
        mvc.perform(
                        get("/organizations/list")
                                .param("page", String.valueOf(pageNumber))
                                .param("size", String.valueOf(pageSize))
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organizations", hasItems(organization1, organization2)))
                .andExpect(view().name("organizations/list"))
        ;
        // @formatter:on

        then(service).should().getOrganizations(pageNumber, pageSize);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void list_ReturnsViewForFirstPage_WhenOrganizationListViewIsAccessed() throws Exception {

        final int pageNumber = 0;
        final int pageSize = DEFAULT_PAGE_SIZE;
        final int totalPages = (int) (Math.random() * 100);

        final Organization organization = new Organization(randomInteger(), randomUUID().toString());
        final Pageable page = PageRequest.of(pageNumber, pageSize);

        final Page<Organization> response = new PageImpl<>(singletonList(organization), page, totalPages);

        given(service.getOrganizations(pageNumber, pageSize)).willReturn(response);

        // @formatter:off
        mvc.perform(
                        get("/organizations/list")
                                .param("page", String.valueOf(pageNumber))
                                .param("size", String.valueOf(pageSize))
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("previousPageNumber", is(-1)))
                .andExpect(model().attribute("nextPageNumber", is(1)))
                .andExpect(view().name("organizations/list"))
        ;
        // @formatter:on

        then(service).should().getOrganizations(pageNumber, pageSize);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void list_ReturnsViewForLastPage_WhenOrganizationListViewIsAccessed() throws Exception {

        final int pageNumber = 1;
        final int pageSize = DEFAULT_PAGE_SIZE;
        final int totalPages = DEFAULT_PAGE_SIZE;

        final Organization organization = new Organization(randomInteger(), randomUUID().toString());
        final Pageable page = PageRequest.of(pageNumber, pageSize);

        final Page<Organization> response = new PageImpl<>(singletonList(organization), page, totalPages);

        given(service.getOrganizations(pageNumber, pageSize)).willReturn(response);

        // @formatter:off
        mvc.perform(
                        get("/organizations/list")
                                .param("page", String.valueOf(pageNumber))
                                .param("size", String.valueOf(pageSize))
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("previousPageNumber", is(0)))
                .andExpect(model().attribute("nextPageNumber", is(-1)))
                .andExpect(view().name("organizations/list"))
        ;
        // @formatter:on

        then(service).should().getOrganizations(pageNumber, pageSize);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void view_ReturnsViewPageWithOrganizationFromDatabase_WhenOrganizationIdExistsInDatabase() throws Exception {

        final Integer id = randomInteger();

        final Organization organization = new Organization(id, randomUUID().toString());

        given(service.getOrganization(id)).willReturn(Optional.of(organization));

        // @formatter:off
        mvc.perform(get("/organizations/view").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", is(id)))
                .andExpect(model().attribute("organization", is(notNullValue())))
                .andExpect(model().attribute("organization", hasProperty("id", is(id))))
                .andExpect(model().attribute("organization", hasProperty("firstName", is(organization.getName()))))
                .andExpect(view().name("organizations/view"))
        ;
        // @formatter:on

        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void view_ReturnsViewPageWithEmpty_WhenOrganizationIdDoesNotExist() throws Exception {

        final Integer id = randomInteger();

        given(service.getOrganization(id)).willReturn(Optional.empty());

        // @formatter:off
        mvc.perform(get("/organization/view").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", is(id)))
                .andExpect(model().attribute("organization", is(notNullValue())))
                .andExpect(model().attribute("organization", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("organization", hasProperty("Name", is(nullValue()))))
                .andExpect(view().name("organization/view"))
        ;
        // @formatter:on

        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void add_ReturnsViewPageWithEmptyOrganization() throws Exception {

        // @formatter:off
        mvc.perform(
                        get("/organizations/add")
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organization", is(notNullValue())))
                .andExpect(model().attribute("organization", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("organization", hasProperty("Name", is(nullValue()))))

                .andExpect(view().name("organizations/add"))
        ;
        // @formatter:on

        then(service).shouldHaveNoInteractions();
    }

    @Test
    public void edit_ReturnsEditView_WhenOrganizationEditViewIsAccessedAndOrganizationExists() throws Exception {

        final Integer id = randomInteger();

        final Organization organization = new Organization(id, randomUUID().toString());

        given(service.getOrganization(id)).willReturn(Optional.of(organization));

        // @formatter:off
        mvc.perform(
                        get("/organizations/edit")
                                .param("id", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organization", hasProperty("id", is(id))))
                .andExpect(model().attribute("organization", hasProperty("Name", is(organization.getName()))))
                .andExpect(model().attribute("id", is(id)))
                .andExpect(view().name("organizations/edit"))
        ;
        // @formatter:on

        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void edit_ReturnsEditView_WhenOrganizationEditViewIsAccessedAndOrganizationDoesNotExists() throws Exception {

        final Integer id = randomInteger();

        given(service.getOrganization(id)).willReturn(Optional.empty());

        // @formatter:off
        mvc.perform(
                        get("/organizations/edit")
                                .param("id", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organization", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("organization", hasProperty("name", is(nullValue()))))
                .andExpect(model().attribute("id", is(id)))
                .andExpect(view().name("organizations/edit"))
        ;
        // @formatter:on

        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void delete_ReturnsDeleteView_WhenOrganizationDeleteViewIsAccessedAndOrganizationExists() throws Exception {

        final Integer id = randomInteger();

        final Organization organization = new Organization(id, randomUUID().toString());

        given(service.getOrganization(id)).willReturn(Optional.of(organization));

        // @formatter:off
        mvc.perform(
                        get("/organizations/delete")
                                .param("id", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organization", hasProperty("id", is(id))))
                .andExpect(model().attribute("organization", hasProperty("firstName", is(organization.getName()))))

                .andExpect(model().attribute("id", is(id)))
                .andExpect(view().name("organizations/delete"))
        ;
        // @formatter:on

        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void delete_ReturnsDeleteView_WhenOrganizationDeleteViewIsAccessedAndOrganizationDoesNotExists() throws Exception {

        final Integer id = randomInteger();

        given(service.getOrganization(id)).willReturn(Optional.empty());

        // @formatter:off
        mvc.perform(
                        get("/organizations/delete")
                                .param("id", id.toString())
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("organization", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("organization", hasProperty("name", is(nullValue()))))
                .andExpect(model().attribute("id", is(id)))
                .andExpect(view().name("organizations/delete"));
        // @formatter:on
        then(service).should().getOrganization(id);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void save_SavesOrganizationRecord_WhenOrganizationRecordIsValid() throws Exception {

        final Organization organization = new Organization(randomInteger(), randomUUID().toString());

        given(service.save(organization)).willReturn(organization);

        // @formatter:off
        mvc.perform(
                        post("/organizations/save")
                                .flashAttr("organization", organization)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"))
        ;
        // @formatter:on

        then(service).should().save(organization);
        then(service).shouldHaveNoMoreInteractions();
    }

    @Test
    public void deletion_DeletesOrganizationRecord_WhenOrganizationRecordIsValid() throws Exception {

        final Integer id = randomInteger();

        willDoNothing().given(service).delete(id);

        // @formatter:off
        mvc.perform(
                        post("/organizations/delete")
                                .param("id", id.toString())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"))
        ;
        // @formatter:on

        then(service).should().delete(id);
        then(service).shouldHaveNoMoreInteractions();
    }
}

