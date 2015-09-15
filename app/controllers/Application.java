package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.ApplicationService;
import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import views.html.index;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result getCollage() {
        return ok(views.html.collage.render());
    }

    public static List<String> getFriendsAvatarsUrls(String screenName) throws TwitterException {
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        long cursor = -1;
        PagableResponseList<User> users;
        List<String> avatarUrls = new ArrayList<>();
        do {
            users = twitter.getFriendsList(screenName, cursor);
            for (User friend : users) {
                avatarUrls.add(friend.getProfileImageURL());
            }
        } while ((cursor = users.getNextCursor()) != 0);
        return avatarUrls;
    }

    public static List<String> getFollowersAvatarsUrls(String screenName) throws TwitterException {
        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
        long cursor = -1;
        PagableResponseList<User> users;
        List<String> avatarUrls = new ArrayList<>();
        do {
            users = twitter.getFollowersList(screenName, cursor);
            for (User friend : users) {
                avatarUrls.add(friend.getProfileImageURL());
            }
        } while ((cursor = users.getNextCursor()) != 0);
        return avatarUrls;
    }

    public static Result createFriendsCollage(String screenName, int numHoriz) {
        try {
            List<String> avatarsUrls = getFriendsAvatarsUrls(screenName);
//            List<String> avatarsUrls = Stubs.getAvatarsUrlsNormal();
            ApplicationService.combineImages(avatarsUrls, 48, 48, numHoriz);
            return ok();
        } catch (TwitterException te) {
            te.printStackTrace();
            return internalServerError();
        }
    }

    public static Result createFollowersCollage(String screenName, int numHoriz) {
        try {
            List<String> avatarsUrls = getFollowersAvatarsUrls(screenName);
            ApplicationService.combineImages(avatarsUrls, 48, 48, numHoriz);
            return ok();
        } catch (TwitterException te) {
            te.printStackTrace();
            return internalServerError();
        }
    }
}
