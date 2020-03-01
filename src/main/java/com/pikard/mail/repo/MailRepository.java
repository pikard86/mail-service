package com.pikard.mail.repo;

import com.pikard.mail.model.Mail;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface MailRepository extends ReactiveCassandraRepository<Mail,Long> {

}
