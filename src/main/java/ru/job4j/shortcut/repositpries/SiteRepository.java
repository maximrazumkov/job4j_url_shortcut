package ru.job4j.shortcut.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.shortcut.domains.Site;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    List<Site> findByLogin(String login);
    List<Site> findBySite(String site);
}
