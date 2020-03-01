package com.pikard.mail;

import com.google.common.collect.Lists;
import com.pikard.mail.model.Mail;
import com.pikard.mail.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("mails")
public class MailController {

    @Autowired
    MailService mailService;


    @GetMapping("/install")
    public Flux<Mail> initDb() {
        List<Mail> mails = new ArrayList<>(1000000);
        for (int i = 0; i < 500; i++) {
            mails.add(
                    new Mail("selfservice/"+i,"pikard86@gmail" + i + ".com", "selfservice@test.com","Test","Hi"));
        }
        return mailService.save(mails);
    }


    @GetMapping("/")
    public Flux<Mail> listAllEmails() {
        Flux<Mail> mails = mailService.getAllMails();
        return mails;
    }

}
