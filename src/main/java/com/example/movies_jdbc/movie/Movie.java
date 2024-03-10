package com.example.movies_jdbc.movie;

import com.example.movies_jdbc.actor.Actor;

import java.time.LocalDate;
import java.util.List;

public record Movie(Integer id, String name, LocalDate release_date) {
}
