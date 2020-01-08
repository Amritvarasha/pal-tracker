package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
   
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    TimeEntryRepository inmemoryrepo;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository inmemoryrepo,
    MeterRegistry meterRegistry) {
        this.inmemoryrepo = inmemoryrepo;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry time){
        TimeEntry createobject =  inmemoryrepo.create(time);
        actionCounter.increment();
        timeEntrySummary.record(inmemoryrepo.list().size());
         return new ResponseEntity<>(createobject, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update( @PathVariable long id,  @RequestBody TimeEntry time){
        TimeEntry updateobject =  inmemoryrepo.update(id,time);
        if(updateobject!=null) {
            actionCounter.increment();
            return new ResponseEntity<>(updateobject, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete( @PathVariable long id){
        inmemoryrepo.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(inmemoryrepo.list().size());
        return new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);

    }
    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read ( @PathVariable long id) {
        TimeEntry readobject = inmemoryrepo.find(id);
        if(readobject!=null) {
            actionCounter.increment();
            return new ResponseEntity<TimeEntry>(readobject, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list () {
        actionCounter.increment();
        List<TimeEntry> timeEntrieslist = inmemoryrepo.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntrieslist, HttpStatus.OK);


    }

}
