package ru.job4j.shortcut.services.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.domains.Site;
import ru.job4j.shortcut.repositpries.SiteRepository;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SiteRepository siteRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(SiteRepository siteRepository, PasswordEncoder passwordEncoder) {
        this.siteRepository = siteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        List<Site> sites = siteRepository.findByLogin(login);
        if (sites.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        Site site = sites.get(0);
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }

    public Site createSite(Site site) {
        List<Site> sites = siteRepository.findBySite(site.getSite());
        if (!sites.isEmpty()) {
            return new Site(false);
        }
        site.setLogin(site.getSite());
        String password = passwordEncoder.encode(site.getSite()).substring(0, 6);
        site.setPassword(passwordEncoder.encode(password));
        siteRepository.save(site);
        site.setPassword(password);
        site.setRegistration(true);
        return site;
    }
}
