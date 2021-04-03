package org.craftedsw.tripservicekata;

import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class UserSessionRepository implements UserService {
    public static User getStaticLoggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

    @Override
    public User getLoggedUser() {
        return getStaticLoggedUser();
    }
}
