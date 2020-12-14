package com.giuseppeSilvestro.musiclibrary.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HibernateAlbumDAO implements AlbumDAO {
    // Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // Create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Album album) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(album);
        session.close();
    }

    @Override
    public List<Album> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Album> criteria = builder.createQuery(Album.class);
        criteria.from(Album.class);
        List<Album> albums = session.createQuery(criteria).getResultList();
        session.close();

        return albums;
    }

    @Override
    public Album randomAlbum() {
        List<Album> albums = findAll();
        Random random = new Random();
        int id = random.nextInt(albums.size());

        Album album = albums.get(id);
        return album;
    }

    @Override
    public List<Album> findAlbum(String title, String composer, String orchestra, String director, String mainPerformer, String liveStudio, String year, String quality) {
        List<Album> albums = findAll();
        List<Album> result = new ArrayList<>();

        for (Album album :
                albums) {
            if (album.getTitle().toLowerCase(Locale.ROOT).contains(title.toLowerCase(Locale.ROOT)) && album.getComposer().toLowerCase(Locale.ROOT).contains(composer.toLowerCase(Locale.ROOT))
                    && album.getDirector().toLowerCase(Locale.ROOT).contains(director.toLowerCase(Locale.ROOT)) && album.getOrchestra().toLowerCase(Locale.ROOT).contains(orchestra.toLowerCase(Locale.ROOT))
                    && album.getMainPerformer().toLowerCase(Locale.ROOT).contains(mainPerformer.toLowerCase(Locale.ROOT)) && album.getLiveStudio().toLowerCase(Locale.ROOT).contains(liveStudio.toLowerCase(Locale.ROOT))
                    && album.getYear().contains(year) && album.getQuality().toLowerCase(Locale.ROOT).contains(quality.toLowerCase(Locale.ROOT))) {
                result.add(album);
            }
        }
        return result;
    }
}
