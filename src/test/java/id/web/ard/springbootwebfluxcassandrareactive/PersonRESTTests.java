package id.web.ard.springbootwebfluxcassandrareactive;

import com.datastax.driver.core.utils.UUIDs;
import id.web.ard.springbootwebfluxcassandrareactive.model.Person;
import id.web.ard.springbootwebfluxcassandrareactive.repository.PersonRepository;
import java.util.Calendar;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonRESTTests {

	@Autowired
	private WebTestClient webTestClient;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Test
	public void testInsertPerson() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(1994, 2, 25, 0, 0, 0);
		Person p = new Person(UUIDs.timeBased(), "Ardiansyah Test", cal.getTime());
		
		webTestClient.post().uri("/person")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(p), Person.class)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.name").isEqualTo("Ardiansyah Test");
	}
	
	@Test
	public void testGetAllPerson() {
		webTestClient.get().uri("/person")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBodyList(Person.class);
	}
	
	@Test
	public void testGetSinglePerson() {
		Calendar cal = Calendar.getInstance();
		cal.set(1994, 2, 25, 0, 0, 0);
		Person p = new Person(UUIDs.timeBased(), "Ardiansyah Test", cal.getTime());
		
		Person personInserted = personRepository.save(p).block();

		webTestClient.get().uri("/person/{id}", Collections.singletonMap("id", personInserted.getId()))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.name").isEqualTo("Ardiansyah Test");
	}
	
	@Test
	public void testUpdateSinglePerson() {
		Calendar cal = Calendar.getInstance();
		cal.set(1994, 2, 25, 0, 0, 0);
		Person p = new Person(UUIDs.timeBased(), "Ardiansyah Test", cal.getTime());
		
		Person personInserted = personRepository.save(p).block();
		
		Person personUpdated = new Person(personInserted.getId(), "Ardiansyah Test Updated", personInserted.getDob());

		webTestClient.put().uri("/person/{id}", Collections.singletonMap("id", personInserted.getId()))
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.body(Mono.just(personUpdated), Person.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isEqualTo(personInserted.getId().toString())
			.jsonPath("$.name").isEqualTo("Ardiansyah Test Updated");
	}
	
	@Test
	public void testDeletePerson() {
		Calendar cal = Calendar.getInstance();
		cal.set(1994, 2, 25, 0, 0, 0);
		Person p = new Person(UUIDs.timeBased(), "Ardiansyah Test", cal.getTime());
		
		Person personInserted = personRepository.save(p).block();
		
		webTestClient.delete().uri("/person/{id}", Collections.singletonMap("id",  personInserted.getId()))
			.exchange()
			.expectStatus().isOk();
		
		webTestClient.get().uri("/person/{id}", Collections.singletonMap("id",  personInserted.getId()))
			.exchange()
			.expectStatus().isNotFound();
	}

}
