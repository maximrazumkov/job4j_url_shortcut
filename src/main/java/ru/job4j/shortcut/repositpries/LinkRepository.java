package ru.job4j.shortcut.repositpries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.shortcut.domains.Link;

import javax.transaction.Transactional;
import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Integer> {
    List<Link> findByCode(String code);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO links AS l (id, url, code, count, site_id) VALUES (?1, ?2, ?3, ?4, ?5) "
            + "ON CONFLICT (id) DO UPDATE SET count = l.count + 1", nativeQuery = true)
    void upsert(Integer id, String url, String code, Integer count, Integer siteId);
}
