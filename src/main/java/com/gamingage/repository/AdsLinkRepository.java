package com.gamingage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.AdsLink;

@Repository
public interface AdsLinkRepository extends JpaRepository<AdsLink, Integer> {

}
