package com.base.recruitment.repository;

import com.base.recruitment.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    Set<ProductoEntity> findByNombreIn(List<String> nombres);
}
