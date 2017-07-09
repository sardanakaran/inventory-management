package com.rajcorporation.tender.security.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rajcorporation.tender.security.auth.model.DeletableModel;

@NoRepositoryBean
public interface DeletableModelRepository<T extends DeletableModel>
        extends PagingAndSortingRepository<T, Long> {

    @Override
    @Query( "select m from #{#entityName} m where m.deletedAt IS NULL Order By m.id" )
    public List<T> findAll();

}
