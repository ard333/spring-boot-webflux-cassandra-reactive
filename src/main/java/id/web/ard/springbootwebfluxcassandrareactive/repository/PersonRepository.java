package id.web.ard.springbootwebfluxcassandrareactive.repository;


import id.web.ard.springbootwebfluxcassandrareactive.model.Person;
import java.util.UUID;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ardiansyah
 */
@Repository
public interface PersonRepository extends ReactiveCassandraRepository<Person, UUID>{

}
