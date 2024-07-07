package com.pejal.verification;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FruitRepository implements PanacheRepositoryBase<Fruit, Long> {

    public List<Fruit> findByColor(String color) {
        return this.list("color",color);
    }
}
