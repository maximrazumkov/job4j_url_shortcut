package ru.job4j.shortcut.services;

import ru.job4j.shortcut.domains.Link;

import java.util.Set;

public interface LinkService {
    Link convert(Link link, String username);
    Link redirect(String code);
    Set<Link> statistic(String username);
}
