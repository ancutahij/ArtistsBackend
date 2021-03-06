package service;

import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AddressRepository;
import repository.EventPostRepository;
import repository.EventRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final EventPostRepository eventPostRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, AddressRepository addressRepository, EventPostRepository eventPostRepository) {
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.eventPostRepository = eventPostRepository;
    }

    @Override
    public Event add(EventPOJO pojo) {
        Event event = new Event();

        Address address = pojo.getAddress();
        this.addressRepository.save(address);

        //mapping from pojo to event
        event.setTitle(pojo.getTitle());
        event.setUrlPoster(pojo.getUrlPoster());
        event.setDescription(pojo.getDescription());
        event.setStartTime(pojo.getStartTime());
        event.setEndTime(pojo.getEndTime());
        event.setAddress(address);
        event.setArtists(pojo.getArtists());
        event.setOrganizers(pojo.getOrganizers());
        event.setLinksToTickets(pojo.getLinksToTickets());
        event.setUser(pojo.getUser());

        return this.eventRepository.save(event);
    }

    @Override
    public void delete(long id) {
        if (this.eventRepository.findById(id).isPresent()) {
            this.eventRepository.deleteById(id);
            return;
        }

        throw new IllegalArgumentException("There is no Event entity with id " + id + " in the database!");

    }

    @Override
    public Event update(EventPOJO pojo) {
        long eventId = pojo.getId();

        //get old event details
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    String msg = "Event entity with id "+eventId+" has not been found!";
                    return new EntityNotFoundException(msg);
                });


        //map from pojo to event
        event.setId(eventId);
        event.setTitle(pojo.getTitle());
        event.setUrlPoster(pojo.getUrlPoster());
        event.setDescription(pojo.getDescription());
        event.setStartTime(pojo.getStartTime());
        event.setEndTime(pojo.getEndTime());
        event.setAddress(pojo.getAddress());
        event.setArtists(pojo.getArtists());
        event.setOrganizers(pojo.getOrganizers());
        event.setLinksToTickets(pojo.getLinksToTickets());

        this.addressRepository.save(event.getAddress());
        return this.eventRepository.save(event);
    }

    @Override
    public Event findById(long id) {
        return this.eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " has not been found."));
    }

    @Override
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public Iterable<EventReview> findAllReviewsByEventId(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent())
            return event.get().getReviews();
        return null;
    }

    @Override
    public Iterable<EventPost> findAllPostsByEventId(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent())
            return event.get().getPosts();
        return null;
    }

    @Override
    public void addEventPost(User user, EventPost eventPost) throws Exception {
        Iterable<Event> events = eventRepository.getEventsByUserId(user.getUserId());
        for(Event e: events){
            if(e.equals(eventPost.getByEvent())){
                eventPostRepository.save(eventPost);
                return;
            }
        }
        throw new Exception("Invalid request!");
    }
}
