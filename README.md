# Movie Ratings Card Game

This is a Java 11 and Spring Boot 3 project for a card game about movies. The goal of the game is to choose between two movies and determine which one has a better rating on IMDb.

## Installation

To install and run this application, you can use the provided Dockerfile. First, clone the repository:

```sh
git clone https://github.com/joaofveloso/MoviesCardGame.git
```

Then, build the Docker image:

```sh
docker build -t movie-ratings-card-game .
```

Finally, start the Docker container:

```sh
docker run -p 8080:8080 movie-ratings-card-game
```

This will start the application on port 8080.

## Usage
To use the application, simply navigate to http://localhost:8080/swagger-ui/index.html in your web browser. You will be presented with two movies and their ratings on IMDb. Choose the one you think has a better rating, and the game will continue with the next pair of movies. You can play as many rounds as you like.

## Contributing
If you'd like to contribute to this project, please follow these guidelines:

- Fork the repository and create a new branch for your changes.
- Make your changes and test them thoroughly.
- Submit a pull request with a clear description of your changes and why they are necessary.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Credits

This project was created by João Veloso for the Ada.tech teaching opportunity.
