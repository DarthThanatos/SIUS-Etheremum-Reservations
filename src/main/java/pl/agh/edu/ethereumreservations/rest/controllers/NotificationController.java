package pl.agh.edu.ethereumreservations.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    public static SimpMessagingTemplate template;

    @Autowired
    public NotificationController(SimpMessagingTemplate template_) {
        template = template_;
    }

    @GetMapping("/notifications")
    public String notifications() {
        return "notifications";
    }

    @MessageMapping("/send/event")
    public void onReceiveMsg(String msg) {
        template.convertAndSend("/events", "msg");
    }
}

