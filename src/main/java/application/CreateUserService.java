package application;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserService {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class);

    public static void createUser(CreateUserCommand createUserCommand) {
        User user = createUserCommand.convertToUser();

        DataBase.addUser(user);

        logger.info("USER 생성 완료!");
    }
}
