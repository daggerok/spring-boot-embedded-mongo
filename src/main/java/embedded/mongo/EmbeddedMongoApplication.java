package embedded.mongo;

import com.mongodb.DB;
import embedded.mongo.data.Test;
import embedded.mongo.data.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("test")
@EnableMongoRepositories(basePackageClasses = { TestRepository.class })
@SpringBootApplication
public class EmbeddedMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmbeddedMongoApplication.class, args);
	}

	@Autowired TestRepository testRepository;

	@Bean public CommandLineRunner runner() {
		return args -> testRepository.save(
				IntStream.range(0, 21)
						.mapToObj(String::valueOf)
						.map(Test::of)
						.collect(toList()));
	}

	@Autowired
	MongoTemplate mongoTemplate;

	@RequestMapping("/cfg")
	public String cfg() {

		final DB db = mongoTemplate.getDb();

		return String.format("%s | %s | %s", db.getName().toString(),
				db.getMongo().getDatabaseNames().toString(),
				db.getMongo().getConnectPoint().toString());
	}

	@RequestMapping("/")
	public List<Test> findAll(@RequestParam("val") Optional<String> val) {

		if (val.isPresent()) {
			Optional<Test> result = testRepository.findByVal(val.get());

			return result.isPresent() ? Arrays.asList(result.get()) : Collections.<Test>emptyList();
		}

		return testRepository.findAll();
	}
}
