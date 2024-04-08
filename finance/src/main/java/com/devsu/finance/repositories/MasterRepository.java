package com.devsu.finance.repositories;

import com.devsu.finance.models.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master,Long> {
    public Optional<Master> findByParentCodeAndCode(String parentCode,String code);

}
