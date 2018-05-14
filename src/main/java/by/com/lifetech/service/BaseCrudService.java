package by.com.lifetech.service;

import by.com.lifetech.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Collection;

public interface BaseCrudService<D extends BaseEntity, I extends Serializable, E extends JpaRepository> {
    D insert(D model);

    D saveOrUpdate(D model);

    D findById(I id);

    Collection<D> findAll();

    void delete(I id);

    void saveAll(Collection<D> exchangeTickers);

    E getRepository();
}
