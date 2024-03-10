package com.example.movies_jdbc.movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
public class MovieController {
    public final MovieService movieService;
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(){
        return movieService.getMoives();
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable int id){
        return movieService.getMovieById(id);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie){
        movieService.addMovie(movie);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteMovie(@PathVariable int id){
        movieService.deleteMovie(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updateMovie(@PathVariable int id,@RequestBody Movie movie){
        movieService.updateMovie(id, movie);
    }
}
