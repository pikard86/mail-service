package com.pikard.mail.services;

import com.pikard.mail.model.Mail;
import com.pikard.mail.repo.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MailService {

    private MailRepository mailRepository;


    @Autowired
    public MailService(MailRepository mailRepository){
        this.mailRepository = mailRepository;
    }

    public Flux<Mail> getAllMails() {
        return mailRepository.findAll();
    }

    public Flux<Mail> save(List<Mail> mails) {
        return mailRepository.saveAll(mails);
    }
}
