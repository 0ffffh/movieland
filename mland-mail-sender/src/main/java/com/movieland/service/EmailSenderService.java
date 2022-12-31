package com.movieland.service;

import com.movieland.event.MovieEvent;

public interface EmailSenderService {
    void handleNotification(MovieEvent movie);

}
