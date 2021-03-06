package cl.own.usi.dao.impl.mongo;

import cl.own.usi.model.User;
import cl.own.usi.model.util.IdHelper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DaoHelper {

	public static final String userIdField = "userId";
	public static final String passwordField = "password";
	public static final String scoreField = "score";
	public static final String isLoggedField = "isLogged";
	public static final String bonusField = "bonus";
	public static final String namesEmailField = "namesEmail";
	public static final String questionFieldPrefix = "q_";

	public static final String usersCollection = "users";

	private static final String namesEmailSeparator = "!!!";

	public static final DBObject orderByScore = new BasicDBObject().append(
			scoreField, -1);

	public static final DBObject orderByNames = new BasicDBObject().append(
			namesEmailField, -1);

	public static final DBObject orderByScoreNames = new BasicDBObject()
			.append(scoreField, -1).append(namesEmailField, 1).append(isLoggedField, 1);

	public static DBObject toDBObject(final User user) {
		DBObject dbUser = new BasicDBObject();
		dbUser.put(userIdField, IdHelper.generateUserId(user.getEmail()));
		dbUser.put(passwordField, user.getPassword());
		dbUser.put(scoreField, user.getScore());
		dbUser.put(isLoggedField, Boolean.FALSE);
		dbUser.put(bonusField, Integer.valueOf(0));

		// Special field used for ranking
		dbUser.put(namesEmailField, getNames(user));

		return dbUser;
	}

	public static String getNames(User user) {

		// Spec : les classements sont ordonnes par lastname/firstname/mail
		StringBuilder sb = new StringBuilder();
		sb.append(user.getLastname());
		sb.append(namesEmailSeparator);
		sb.append(user.getFirstname());
		sb.append(namesEmailSeparator);
		sb.append(user.getEmail());
		return sb.toString();
	}

	public static User fromDBObject(final DBObject dbUser) {
		User user = new User();
		user.setUserId((String) dbUser.get(userIdField));
		user.setPassword((String) dbUser.get(passwordField));
		user.setScore((Integer) dbUser.get(scoreField));

		String namesEmail = (String) dbUser.get(namesEmailField);
		String[] details = namesEmail.split(namesEmailSeparator);

		if (details.length != 3) {
			throw new RuntimeException(
					"Invalid namesEmail field, the DB is in bad shape");
		}

		String lastname = details[0];
		String firstname = details[1];
		String email = details[2];

		user.setEmail((String) email);
		user.setFirstname((String) firstname);
		user.setLastname((String) lastname);

		return user;
	}

	/*
	 * I'm using this to test the userId public static void main(String[] args)
	 * { System.out.println(generateUserId("brown.king@hotmail.com"));
	 * System.out.println(generateUserId("brown.hartman@yahoo.com"));
	 * System.out.println(generateUserId("brown.gardner@gmail.com"));
	 * System.out.println(generateUserId("brown.mcfarland@gmail.com"));
	 * System.out.println(generateUserId("brown.mckay@gmail.com"));
	 * System.out.println(generateUserId("brown.hurst@gmail.com")); }
	 */

}
