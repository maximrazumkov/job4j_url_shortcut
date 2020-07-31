package ru.job4j.shortcut.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.domains.Link;
import ru.job4j.shortcut.domains.Views;
import ru.job4j.shortcut.services.LinkService;
import ru.job4j.shortcut.services.impl.UserDetailsServiceImpl;

import java.util.Set;

@RestController
public class LinkController {
    private final LinkService linkService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @JsonView(Views.Converter.class)
    @PostMapping("/convert")
    public Link registration(@RequestBody Link link) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return linkService.convert(link, username);
    }

    @JsonView(Views.Redirecter.class)
    @GetMapping("/redirect/{code}")
    public ResponseEntity<Link> redirect(@PathVariable String code) {
        return new ResponseEntity<>(linkService.redirect(code), HttpStatus.FOUND);
    }

    @JsonView(Views.Statistic.class)
    @GetMapping("/statistic")
    public Set<Link> statistic() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return linkService.statistic(username);
    }
}
