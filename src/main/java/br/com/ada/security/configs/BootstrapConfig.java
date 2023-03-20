package br.com.ada.security.configs;

import br.com.ada.cardgame.repositories.MovieRepository;
import br.com.ada.cardgame.repositories.entities.MovieEntity;
import br.com.ada.security.repositories.UserRepository;
import br.com.ada.security.repositories.entities.UserEntity;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapConfig implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final MovieRepository movieRepository;

    private final RestTemplate restTemplate;

    @Value("${core.ratting.key}")
    private String rattingKey;

    @Override
    @Transactional
    public void run(String[] args) {

        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);

        List<UserEntity> userEntities = Arrays.asList(
                new UserEntity("user1", passwordEncoder.encode("password1"), true, true, true,
                        true),
                new UserEntity("user2", passwordEncoder.encode("password2"), true, true, true,
                        true));
        userRepository.saveAll(userEntities);

        List<Movie> movies = readCsvFile();
        List<MovieEntity> movieEntities = movies.stream()
                .peek(p -> log.debug("Fetching movie: {}", p.title)).map(this::fetchMovie)
                .filter(Objects::nonNull).filter(this::nonNullFields).toList();

        movieRepository.saveAll(movieEntities);

        log.info("Application loaded");
    }

    private boolean nonNullFields(MovieEntity movieEntity) {
        return !(movieEntity.getTitle() == null || movieEntity.getPoster() == null
                || movieEntity.getRating() == null);
    }

    private MovieEntity fetchMovie(Movie movie) {
        String rattingUrl = "http://www.omdbapi.com/";
        HttpResponse<MovieDto> response = Unirest.get(rattingUrl).queryString("apikey", rattingKey)
                .queryString("t", movie.title).queryString("y", movie.year)
                .header("User-Agent", "Mozilla/5.0").asObject(MovieDto.class);

        if (response.getStatus() < 200 && response.getStatus() >= 300) {
            log.error("Error: {} fetching movie {}", response.getStatus(), movie.title);
            return null;
        }
        MovieDto body = response.getBody();
        return new MovieEntity(body.getTitle(), body.getPoster(), body.getImdbRating());
    }

    private List<Movie> readCsvFile() {
        String csvFile = "movies.csv";
        boolean firstLine = true;
        String csvSeparator = ",";
        List<Movie> movies = new ArrayList<>();
        try (InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream(csvFile); BufferedReader br = new BufferedReader(
                new InputStreamReader(is))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] movieData = line.split(csvSeparator);
                movies.add(new Movie(movieData[0], movieData[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private record Movie(String title, String year) {

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovieDto {

        private String Title;
        private String Year;
        private String Director;
        private String Plot;
        private String Poster;
        private BigDecimal imdbRating;
    }
}