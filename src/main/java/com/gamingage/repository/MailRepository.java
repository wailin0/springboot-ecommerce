package com.gamingage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamingage.model.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {


}
