package io.pivotal.pal.tracker;
import  io.pivotal.pal.tracker.InMemoryTimeEntryRepository;
import io.pivotal.pal.tracker.TimeEntry;
   
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
   
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    HashMap<Long, TimeEntry > timeentryobjects = new HashMap<Long, TimeEntry>();

     long currId = 0L ;
  
    @Override
    public TimeEntry create(TimeEntry timentry) {
        currId++;
        timentry.setId(currId);
        timeentryobjects.put( currId , timentry );
        return timeentryobjects.get(currId);
    }

    @Override
    public TimeEntry find(long id) {
        return timeentryobjects.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeentryobjects.values()) ;
    }

    @Override
    public TimeEntry update(long id, TimeEntry newTimeEntry) {
        if(timeentryobjects.containsKey(id))
        {
            newTimeEntry.setId(id);
            timeentryobjects.put(id,newTimeEntry);
            return timeentryobjects.get(id);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        timeentryobjects.remove(id);
    }



}


