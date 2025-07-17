package com.base.recruitment.repository;

import com.base.recruitment.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    @Query(
            nativeQuery = true,
            value = """
                        SELECT CLIENTE.*
                        FROM CLIENTE INNER JOIN CLIENTE_PRODUCTO ON CLIENTE.ID = CLIENTE_PRODUCTO.CLIENTE_ID
                        INNER JOIN PRODUCTO AS P ON CLIENTE_PRODUCTO.PRODUCTO_ID = P.ID
                        WHERE P.NOMBRE = :producto
                    """
    )
    Set<ClienteEntity> findAllWithProduct(@Param("producto") String producto);
}
