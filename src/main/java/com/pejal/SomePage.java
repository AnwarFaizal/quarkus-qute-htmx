package com.pejal;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("/some-page")
public class SomePage {

    private final Template page;
    private final Template secondPage;

    public SomePage(Template page, Template secondPage) {

        this.page = requireNonNull(page, "page is required");
        this.secondPage = requireNonNull(secondPage, "page is required");

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("name") String name) {
        return page.data("name", name);
    }

    @GET
    @Path("/2")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSecondPage(@QueryParam("name") String name) {
        return secondPage.data("name", name);
    }

}
