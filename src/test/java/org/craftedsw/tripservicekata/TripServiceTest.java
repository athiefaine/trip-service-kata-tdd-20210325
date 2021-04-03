package org.craftedsw.tripservicekata;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TripServiceTest {

    private TripService getTarget(User loggedUser) {
        return new TestableTripService(loggedUser,
                new UserService() {
                    @Override
                    public User getLoggedUser() {
                        return loggedUser;
                    }
                });
    }

    @Test
    public void when_logged_user_is_null_then_throw_exception() {
        User notLoggedUser = null;

        TripService tripService = getTarget(notLoggedUser);

        assertThatExceptionOfType(UserNotLoggedInException.class)
                .isThrownBy(() -> tripService.getTripsByUser(null));
    }

    @Test
    public void when_user_has_no_friends_then_return_no_trip() {
        User loggedUser = new User();
        User userAsocial = new User();
        TripService tripService = getTarget(loggedUser);

        List<Trip> trips = tripService.getTripsByUser(userAsocial);

        assertThat(trips).isEmpty();
    }

    @Test
    public void when_user_has_friend_and_not_friend_with_logged_user_then_return_no_trip() {
        User loggedUser = new User();
        User joyeuxLuron = new User();
        User friendOfJoyeuxLuron = new User();
        joyeuxLuron.addFriend(friendOfJoyeuxLuron);
        TripService tripService = getTarget(loggedUser);

        List<Trip> trips = tripService.getTripsByUser(joyeuxLuron);

        assertThat(trips).isEmpty();
    }

    @Test
    public void when_user_has_friend_and_friend_with_logged_user_then_return_friend_trips() {
        User loggedUser = new User();
        User joyeuxLuron = new User();
        joyeuxLuron.addFriend(loggedUser);
        joyeuxLuron.addTrip(new Trip());
        TripService tripService = getTarget(loggedUser);

        List<Trip> trips = tripService.getTripsByUser(joyeuxLuron);

        assertThat(trips).hasSize(1);
    }

    @Test
    public void when_user_is_null_then_throw_nullPointerException() {
        User loggedUser = new User();
        TripService tripService = getTarget(loggedUser);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> tripService.getTripsByUser(null));
    }

    class TestableTripService extends TripService {

        private User loggedUser;

        public TestableTripService(User loggedUser, UserService userSessionRepository) {
            super(userSessionRepository);
            this.loggedUser = loggedUser;
        }

        @Override
        List<Trip> tripsBy(User user) {
            return Arrays.asList(new Trip());
        }
    }
}
