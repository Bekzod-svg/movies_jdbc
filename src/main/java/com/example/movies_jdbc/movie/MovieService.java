package com.example.movies_jdbc.movie;

import com.example.movies_jdbc.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    public final MovieDao movieDao;
    public MovieService(MovieDaoAccessService movieDao){
        this.movieDao = movieDao;
    }

    public List<Movie> getMoives(){
        return movieDao.selectMovies();
    }

    public Movie getMovieById(int id){
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("MOive with id %s not found", id)));
    }

    public void addMovie(Movie movie){
        int res = movieDao.insertMovie(movie);
        if(res != 1){
            throw new IllegalStateException("sth went wrong");
        }
    }

    public void deleteMovie(int id){
        Optional<Movie> movie = movieDao.selectMovieById(id);
        movie.ifPresentOrElse(mov ->{
            int res = movieDao.deleteMovie(id);
            if(res != 1){
                throw new IllegalStateException(String.format("Could not delete movie with id %s", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });

    }

    public void updateMovie(int id, Movie movie){
        Optional<Movie> mov = movieDao.selectMovieById(id);
        mov.ifPresentOrElse(movie1 -> {
            int res = movieDao.updateMovie(id, movie);
            if(res != 1){
                throw new IllegalStateException(String.format("Wasnt able to update movie with id = %s", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }
}
