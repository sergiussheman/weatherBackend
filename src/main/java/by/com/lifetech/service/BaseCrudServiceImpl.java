package by.com.lifetech.service;


import by.com.lifetech.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class BaseCrudServiceImpl<D extends BaseEntity, I extends Serializable, E extends JpaRepository<D, I>>
        implements BaseCrudService<D, I, E> {
    protected E repository;

    public BaseCrudServiceImpl(E repository) {
        this.repository = repository;
    }

    @Override
    public D insert(D model) {
        return this.repository.save(model);
    }

    @Override
    public D saveOrUpdate(D model) {
        return this.repository.save(model);
    }

    @Override
    public D findById(I id) {
        Optional<D> result = this.repository.findById(id);
        if(result.isPresent()) {
            return result.get();
        } else {
            // TODO: throw dedicated exceptions in subclasses
            throw new RuntimeException("Couldn't get entity. Invalid id");
        }
    }

    @Override
    public Collection<D> findAll() {
        // TODO: or is it better to return iterable?
        return new ArrayList<>(this.repository.findAll());
    }

    @Override
    public void delete(I id) {
        this.repository.deleteById(id);
    }

    @Override
    public void saveAll(Collection<D> exchangeTickers) {
        exchangeTickers.forEach(this::insert);
    }

    @Override
    public E getRepository() {
        return this.repository;
    }
}
