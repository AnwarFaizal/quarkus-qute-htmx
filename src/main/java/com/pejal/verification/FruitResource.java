package com.pejal.verification;

import java.util.List;
import java.util.Optional;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


import static java.util.Objects.requireNonNull;

@Slf4j
@Path("/fruits")
public class FruitResource {

    private final FruitRepository fruitRepository;

    private final Template fruits;
    private final Template fruitRow;

    public FruitResource(FruitRepository fruitRepository, Template fruits, Template fruitRow) {
        this.fruitRepository = fruitRepository;
        this.fruits = requireNonNull(fruits, "page is required");
        this.fruitRow = requireNonNull(fruitRow, "page is required");
    }

    @GET
//    @Produces("application/json")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance findAll() {
        List<Fruit> fruitList = fruitRepository.listAll();
        log.info("Fruit listing: {}", fruitList);
        return fruits.data("fruitList", fruitList);
    }

    @Transactional
    @DELETE
    @Path("{id}")
    public String delete(long id) {
        Fruit toRemove = fruitRepository.findById(id);
        if (toRemove != null) {
            log.info("Removing {}", toRemove);
            fruitRepository.deleteById(id);
            return "";
        }
        return null;
    }

    @Transactional
    @POST
    @Path("/add")
//    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance create(@FormParam("name")String name, @FormParam("color")String color) {
        Fruit fruit = new Fruit(name, color);
        fruitRepository.persist(fruit);
        log.info("New fruit added: {}", fruit);
        return fruitRow.data("fruit", fruit);
    }

    @PUT
    @Path("/id/{id}/color/{color}")
    @Produces("application/json")
    public Fruit changeColor(Long id, String color) {
        Fruit fruit = fruitRepository.findById(id);
        if (fruit != null) {
//            Fruit fruit = optional.get();
            fruit.setColor(color);
            fruitRepository.persist(fruit);
            return fruit;
        }

        throw new IllegalArgumentException("No Fruit with id " + id + " exists");
    }

    @GET
    @Path("/color/{color}")
    @Produces("application/json")
    public List<Fruit> findByColor(String color) {
        return fruitRepository.findByColor(color);
    }
}
