package com.devsu.operations.repositories;


import com.devsu.operations.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master,Long> {
    public Optional<Master> findByParentCodeAndCode(String parentCode,String code);

}
