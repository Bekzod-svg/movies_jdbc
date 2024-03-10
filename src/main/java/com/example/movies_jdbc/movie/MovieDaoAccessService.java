package com.example.movies_jdbc.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDaoAccessService implements MovieDao{

    public final JdbcTemplate jdbcTemplate;
    public MovieDaoAccessService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> selectMovies() {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                ORDER BY id
                LIMIT 100;
                """;
        List<Movie> movies = jdbcTemplate.query(sql, (res, i) -> {
            return new Movie(
                    res.getInt("id"),
                    res.getString("name"),
//                    List.of(),
                    LocalDate.parse(res.getString("release_date"))
            );
        });
        return movies;
    }

    @Override
    public int insertMovie(Movie movie) {
        var sql = """
                INSERT INTO movie (name, release_date) 
                VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, movie.name(), movie.release_date());
    }

    @Override
    public int deleteMovie(int id) {
        var sql = """
                DELETE FROM movie
                WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        var sql = """
                SELECT id, name, release_date
                FROM movie
                WHERE id = ?;
                """;
        List<Movie> list = jdbcTemplate.query(sql, (res, i) -> {
            return new Movie(
                    res.getInt("id"),
                    res.getString("name"),
//                    List.of(),
                    LocalDate.parse(res.getString("release_date"))
            );
        }, id);


        return list.stream().findFirst();
    }

    @Override
    public int updateMovie(int id, Movie movie) {
        var sql = """
                UPDATE movie
                SET name = ?, release_date = ?
                WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, movie.name(), movie.release_date(), id);
    }
}
