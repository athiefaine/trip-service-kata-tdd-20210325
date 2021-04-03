package org.craftedsw.tripservicekata;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.trip.Trip;
import org.craftedsw.tripservicekata.trip.TripDAO;
import org.craftedsw.tripservicekata.user.User;

import java.util.Collections;
import java.util.List;

public class TripService {

	private UserService userService;

	public TripService(UserService userService) {

		this.userService = userService;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = userService.getLoggedUser();

		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}

		return user.getFriends().stream()
				.filter(u -> u.equals(loggedUser))
				.findFirst()
				.map(this::tripsBy)
				.orElse(empty());
	}

	private List<Trip> empty() {
		return Collections.emptyList();
	}

	List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

}
