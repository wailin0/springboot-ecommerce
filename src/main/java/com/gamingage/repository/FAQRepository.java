package com.gamingage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

}
