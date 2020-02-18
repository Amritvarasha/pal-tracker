package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timentry);
    public TimeEntry find( long id );

    public List<TimeEntry> list();
    public TimeEntry update(long id,TimeEntry newTimeEntry);

    public void delete(long id);


}
