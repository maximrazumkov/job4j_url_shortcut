package ru.job4j.shortcut.services.impl;

import org.springframework.stereotype.Service;
import ru.job4j.shortcut.domains.Link;
import ru.job4j.shortcut.domains.Site;
import ru.job4j.shortcut.exeptions.NotFoundException;
import ru.job4j.shortcut.repositpries.LinkRepository;
import ru.job4j.shortcut.repositpries.SiteRepository;
import ru.job4j.shortcut.services.LinkService;

import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class SimpleLinkService implements LinkService {

    private final LinkRepository linkRepository;
    private final SiteRepository siteRepository;

    public SimpleLinkService(LinkRepository linkRepository, SiteRepository siteRepository) {
        this.linkRepository = linkRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public Link convert(Link link, String username) {
        List<Site> sites = siteRepository.findByLogin(username);
        String code = Base64.getEncoder().encode(link.getUrl().getBytes()).toString();
        code = code.replaceAll("[^A-Za-zА-Яа-я0-9]", "");
        return linkRepository.save(new Link(link.getUrl(), code, 1, sites.get(0)));
    }

    @Override
    public Link redirect(String code) {
        List<Link> links = linkRepository.findByCode(code);
        if (links.isEmpty()) {
            throw new NotFoundException(String.format("Ссылка по коду %s не найдена", code));
        }
        Link link = links.get(0);
        linkRepository.upsert(link.getId(), link.getUrl(), link.getCode(), link.getCount(), link.getSite().getId());
        return link;
    }

    @Override
    public Set<Link> statistic(String username) {
        List<Site> sites = siteRepository.findByLogin(username);
        return sites.get(0).getLinks();
    }
}
