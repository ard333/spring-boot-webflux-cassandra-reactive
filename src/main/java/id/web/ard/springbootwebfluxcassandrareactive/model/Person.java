package id.web.ard.springbootwebfluxcassandrareactive.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 *
 * @author ardiansyah
 */
@ToString @Data @NoArgsConstructor @AllArgsConstructor
@Table("person")
public class Person {
	
	@PrimaryKey
	private UUID id;
	
	private String name;
	
	private Date dob;
	
	
}
