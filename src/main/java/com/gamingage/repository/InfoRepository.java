package com.gamingage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Integer> {

}
